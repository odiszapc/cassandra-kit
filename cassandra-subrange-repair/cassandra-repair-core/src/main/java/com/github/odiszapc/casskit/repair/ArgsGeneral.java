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

import com.beust.jcommander.Parameter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArgsGeneral {

    @Parameter(names = "-ks", required = true, description = "keyspace")
    private String ks;

    @Parameter(names = "-cf", required = false, variableArity = true, description = "column families separated by spaces. Default: all CFs will be repaired")
    private List<String> cf = new ArrayList<String>();

    @Parameter(names = "-pr", description = "only repair the first range returned by the partitioner for the node")
    private Boolean pr = false;

    @Parameter(names = "-kps", description = "keys per split")
    private Integer kps = 32768;

    @Parameter(names = "-host", required = true, description = "hostname of any Cassandra node to describe topology")
    private String host;

    @Parameter(names = "-port", description = "Cassandra port")
    private int port = 9160;

    @Parameter(names = "-jmx-port", description = "Cassandra JMX port")
    private int jmxPort = 7199;
}
