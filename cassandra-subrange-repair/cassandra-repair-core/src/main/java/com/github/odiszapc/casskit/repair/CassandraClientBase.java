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

import java.util.ArrayList;
import java.util.List;

public abstract class CassandraClientBase implements ICassandraClient {

    protected List<Split> buildSplits(String ks, String cf, Range range, List<String> splits) {
        List<Split> result = new ArrayList<Split>();
        for (int i = 0; i < splits.size() - 1; i++) {
            String from = splits.get(i);
            String to = splits.get(i + 1);

            int j = 0;
            for (String endpoint : range.endpoints) {
                Split desc = new Split(endpoint, from, to, ks, cf, 0 == j);
                result.add(desc);
                j++;
            }
        }
        return result;
    }

}
