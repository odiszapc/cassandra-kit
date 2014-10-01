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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.lang.reflect.Constructor;

public abstract class LauncherAbstract<T extends ArgsGeneral> {

    protected RepairService service;

    protected LauncherAbstract(String[] args) {
        T arguments = readArgs(args);
        IOptsAdapter optsAdapter = getOptsAdapter(arguments);
        ICassandraClientFactory clientFactory = getClientFactory();
        IProbeRunnerFactory probeRunnerFactory = getProbeFactory();

        service = new RepairService(
                clientFactory,
                probeRunnerFactory,
                optsAdapter,
                arguments.getHost(),
                arguments.getPort(),
                arguments.getJmxPort(),
                arguments.getKs(),
                arguments.getCf());
    }

    protected T readArgs(String[] args) {

        JCommander jCommander = null;
        try {
            Class<T> clazz = getArgsType();
            Constructor<T> constructor = clazz.getConstructor();
            T argObj = constructor.newInstance();
            jCommander = new JCommander(argObj);
            jCommander.parse(args);
            return argObj;
        } catch (ParameterException e) {
            if (null != jCommander)
                jCommander.usage();
            System.exit(1);
            return null;
        } catch (Exception e) {
            System.out.printf("Unexpected error");
            System.exit(1);
            return null;
        }
    }

    abstract Class<T> getArgsType();

    abstract IProbeRunnerFactory getProbeFactory();

    abstract ICassandraClientFactory getClientFactory();

    abstract IOptsAdapter getOptsAdapter(T arguments);

    public void start() {
        service.start();
    }


}

