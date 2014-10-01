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

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
class SplitDescribeTask implements Callable<List<Split>> {

    final ICassandraClientFactory factory;
    final Range range;
    final int keysPerSplit;
    final String ks;
    final String cf;
    final String host;
    final int port;
    final int frameSize;


    @Override
    public List<Split> call() throws Exception {
        ICassandraClient client = null;
        try {
            client = factory.newClient(host, port, ks, frameSize);

            List<Split> splits = client.describeSplits(cf, range, keysPerSplit);

            int RF = range.endpoints.size();
            System.out.println(String.format("%s x %s splits found for %s, ks=%s, cf=%s",
                    RF, splits.size(), host, ks, cf));


            return splits;
        } finally {
            if (null != client)
                client.close();
        }
    }
}
