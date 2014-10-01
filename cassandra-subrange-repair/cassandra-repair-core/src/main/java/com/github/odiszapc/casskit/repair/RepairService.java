/*
 * Copyright 2014 Alexey Plotnik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.odiszapc.casskit.repair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RepairService {

    public static final int KEYS_PER_SPLIT = 32768;
    public static final int DESCRIBE_POOL_SIZE = 10;
    ExecutorService describePool = Executors.newFixedThreadPool(DESCRIBE_POOL_SIZE);
    public static final int REPAIR_POOL_SIZE = 10;
    ExecutorService repairPool = Executors.newFixedThreadPool(REPAIR_POOL_SIZE);
    private static final int THRIFT_FRAMED_TRANSPORT_SIZE = 1 * 1024 * 1024;
    private final ICassandraClientFactory clientFactory;
    private final IProbeRunnerFactory probeRunnerFactory;
    private final IOptsAdapter optsAdapter;
    private final String host;
    private final int port;
    private int jmxPort;
    private String ks;
    private List<String> cfs;

    public RepairService(ICassandraClientFactory clientFactory,
                         IProbeRunnerFactory probeRunnerFactory,
                         IOptsAdapter optsAdapter,
                         String host, int port, int jmxPort, String ks, List<String> cfs) {
        this.clientFactory = clientFactory;
        this.probeRunnerFactory = probeRunnerFactory;
        this.optsAdapter = optsAdapter;
        this.host = host;
        this.port = port;
        this.jmxPort = jmxPort;
        this.ks = ks;
        this.cfs = cfs;
    }

    public void start() {
        try {
            ICassandraClient client = clientFactory.newClient(host, port, ks, THRIFT_FRAMED_TRANSPORT_SIZE);
            List<Range> tokenRanges = client.describeRing(ks);
            if (noCfSpecified()) {
                cfs = client.describeCfNames(ks);
            }

            List<Split> splits = calculateSplits(tokenRanges, KEYS_PER_SPLIT);
            System.out.println(String.format("%s CFs x %s nodes = %s total splits found", cfs.size(), tokenRanges.size(), splits.size()));

            Collections.shuffle(splits);

            System.out.println(String.format("Starting repair (parallelism = %s)...", REPAIR_POOL_SIZE));
            Tracker tracker = new Tracker(splits.size(), REPAIR_POOL_SIZE);
            Monitor monitor = new Monitor(tracker);
            monitor.start();

            launchRepairForSplits(splits, tracker);

            monitor.terminate();
            monitor.join();

            System.out.println("Repair completed");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean noCfSpecified() {
        return cfs.isEmpty();
    }

    private void launchRepairForSplits(List<Split> splits, Tracker tracker) throws InterruptedException, ExecutionException {
        List<Future<Long>> futures = new ArrayList<Future<Long>>();
        for (Split split : splits) {
            RepairOpts opts = optsAdapter.build()
                    .set(RepairOpt.HOST, split.host)
                    .set(RepairOpt.JMX_PORT, jmxPort)
                    .set(RepairOpt.TOKEN_FROM, split.from)
                    .set(RepairOpt.TOKEN_TO, split.to)
                    .set(RepairOpt.COLUMN_FAMILY, split.cf);


            RepairTask callable = new RepairTask(probeRunnerFactory.newRunner(opts), split, tracker);
            Future<Long> future = repairPool.submit(callable);
            futures.add(future);
        }

        for (Future<Long> future : futures) {
            future.get();
        }

        repairPool.shutdown();
    }

    private List<Split> calculateSplits(List<Range> tokenRanges, int keysPerSplit) throws Exception {
        List<Future<List<Split>>> futures = new ArrayList<Future<List<Split>>>(tokenRanges.size() * cfs.size());
        for (Range range : tokenRanges) {
            for (String cf : cfs) {
                SplitDescribeTask callable = new SplitDescribeTask(clientFactory, range, keysPerSplit, ks, cf, range.endpoints.iterator().next(), port, THRIFT_FRAMED_TRANSPORT_SIZE);
                Future<List<Split>> task = describePool.submit(callable);
                futures.add(task);
            }
        }
        describePool.shutdown();

        List<Split> result = new ArrayList<Split>();
        for (Future<List<Split>> future : futures) {
            List<Split> splits = future.get();
            result.addAll(splits);
        }
        return result;

    }
}
