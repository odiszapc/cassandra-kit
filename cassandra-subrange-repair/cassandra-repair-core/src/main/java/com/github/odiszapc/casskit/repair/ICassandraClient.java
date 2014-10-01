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

import java.util.List;

public interface ICassandraClient {
    public List<Range> describeRing(String ks) throws Exception;

    public List<Split> describeSplits(String cf, Range range, int keysPerSplit) throws Exception;

    public List<String> describeCfNames(String ks) throws Exception;

    public void close();
}
