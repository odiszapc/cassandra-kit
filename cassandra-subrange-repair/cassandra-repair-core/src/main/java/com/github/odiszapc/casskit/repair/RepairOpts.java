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

import java.util.HashMap;
import java.util.Map;

public class RepairOpts {
    Map<RepairOpt<?>, Object> opts = new HashMap<RepairOpt<?>, Object>();

    public <T> RepairOpts set(RepairOpt<T> opt, T value) {
        opts.put(opt, value);
        return this;
    }

    public <T> T get(RepairOpt<T> opt) {
        T val = (T) opts.get(opt);
        if (null == val) throw new NullPointerException("value: " + opt);
        return val;
    }
}
