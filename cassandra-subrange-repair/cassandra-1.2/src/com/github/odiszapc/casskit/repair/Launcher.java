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

public class Launcher extends LauncherAbstract<Args> {

    public Launcher(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher(args);
        launcher.start();
    }

    IOptsAdapter getOptsAdapter(Args arguments) {
        return new OptsAdapter(
                arguments.getKs(),
                arguments.getSnapshot(),
                arguments.getLocal());
    }

    @Override
    Class<Args> getArgsType() {
        return Args.class;
    }

    @Override
    IProbeRunnerFactory getProbeFactory() {
        return new ProbeRunnerFactory();
    }

    @Override
    ICassandraClientFactory getClientFactory() {
        return new CassandraClientFactory();
    }

}
