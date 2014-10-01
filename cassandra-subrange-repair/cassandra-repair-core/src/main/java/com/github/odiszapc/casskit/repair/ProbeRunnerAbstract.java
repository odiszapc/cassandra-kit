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

public abstract class ProbeRunnerAbstract implements IProbeRunner {

    protected RepairOpts opts;

    protected ProbeRunnerAbstract(RepairOpts opts) {
        this.opts = opts;
    }

    public <T> T opt(RepairOpt<T> opt) {
        return opts.get(opt);
    }

    @Override
    final public void run() {
        try {
            execute();
        } catch (Exception e) {
            // TODO:
            System.err.println("Repair task failed!");
            e.printStackTrace();
        }
    }

    protected abstract void execute() throws Exception;
}
