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

import java.util.concurrent.Callable;

@AllArgsConstructor
class RepairTask implements Callable<Long> {
    final IProbeRunner runner;
    final Split split;
    final Tracker tracker;


    @Override
    public Long call() {
        try {
            long start = System.currentTimeMillis();

            runRepair();

            long duration = (System.currentTimeMillis() - start) / 1000;
            logFinished(duration);

            tracker.onComplete(duration);
            return duration;
        } catch (Exception e) {
            tracker.onError();
            return -1l;
        }
    }

    private void logFinished(long duration) {
        System.out.println(
                String.format("Finished task: %s " + (split.primary ? "(partitioner) " : "") + "of %s, %s, duration: %s sec",
                        split.host, split.ks, split.cf, duration));
    }

    private void runRepair() {
        runner.run();
    }
}
