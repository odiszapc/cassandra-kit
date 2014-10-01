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
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class CassandraClientFactory implements ICassandraClientFactory {
    @Override
    public ICassandraClient newClient(String host, int port, String ks, int frameSize) throws Exception {

        TTransport transport = new TFramedTransport(new TSocket(host, port), frameSize);
        TBinaryProtocol protocol = new TBinaryProtocol(transport, true, true);
        Cassandra.Client client = new Cassandra.Client(protocol);
        transport.open();
        client.set_keyspace(ks);

        return new CassandraClient(client, ks);
    }
}
