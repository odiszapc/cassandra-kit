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

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.TokenRange;

import java.util.ArrayList;
import java.util.List;

public class CassandraClient extends CassandraClientBase {

    private final Cassandra.Client client;
    private String ks;

    public CassandraClient(Cassandra.Client client, String ks) {

        this.client = client;
        this.ks = ks;
    }

    @Override
    public List<Range> describeRing(String ks) throws Exception {
        List<TokenRange> tokenRanges = client.describe_ring(ks);

        List<Range> result = new ArrayList<Range>(tokenRanges.size());
        for (TokenRange tr : tokenRanges) {
            result.add(new Range(tr.endpoints, tr.start_token, tr.end_token));
        }

        return result;
    }

    @Override
    public List<Split> describeSplits(String cf, Range range, int keysPerSplit) throws Exception {
        List<String> splits = client.describe_splits(cf,
                range.startToken,
                range.endToken,
                keysPerSplit);

        return buildSplits(ks, cf, range, splits);
    }

    @Override
    public List<String> describeCfNames(String ks) throws Exception {
        KsDef def = client.describe_keyspace(ks);
        List<String> result = new ArrayList<String>();
        for (CfDef cf_def : def.cf_defs) {
            result.add(cf_def.name);
        }
        return result;
    }

    @Override
    public void close() {
        client.getOutputProtocol().getTransport().close();
    }


}
