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

import org.apache.cassandra.tools.NodeProbe;

import java.io.PrintStream;

import static com.github.odiszapc.casskit.repair.RepairOpt.*;
import static org.apache.commons.io.output.NullOutputStream.NULL_OUTPUT_STREAM;

public class ProbeRunner extends ProbeRunnerAbstract {

    public ProbeRunner(RepairOpts opts) {
        super(opts);
    }

    @Override
    public void execute() throws Exception {
        NodeProbe probe = new NodeProbe(opt(HOST), opt(JMX_PORT));
        PrintStream devNull = new PrintStream(NULL_OUTPUT_STREAM);
        probe.forceRepairRangeAsync(
                devNull,
                opt(KEYSPACE),
                opt(SEQ),
                opt(LOCAL),
                opt(TOKEN_FROM),
                opt(TOKEN_TO),
                opt(COLUMN_FAMILY));
    }
}
