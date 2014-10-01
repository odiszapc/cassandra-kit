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

public class RepairOpt<T> {

    public static final RepairOpt<String> HOST = valueOf("HOST");
    public static final RepairOpt<Integer> JMX_PORT = valueOf("JMX_HOST");
    public static final RepairOpt<String> KEYSPACE = valueOf("KEYSPACE");
    public static final RepairOpt<String> COLUMN_FAMILY = valueOf("COLUMN_FAMILY");
    public static final RepairOpt<String> TOKEN_FROM = valueOf("TOKEN_FROM");
    public static final RepairOpt<String> TOKEN_TO = valueOf("TOKEN_TO");
    public static final RepairOpt<String> DC = valueOf("DC");
    public static final RepairOpt<Boolean> SEQ = valueOf("SEQ"); // false
    public static final RepairOpt<Boolean> FULL = valueOf("FULL"); // true
    public static final RepairOpt<Boolean> LOCAL = valueOf("LOCAL"); // true

    private String name;

    public RepairOpt(String name) {
        this.name = name;
    }

    private static <T> RepairOpt<T> valueOf(String name) {
        return new RepairOpt<T>(name);
    }

    public final String name() {
        return name;
    }

    @Override
    public String toString() {
        return name();
    }
}
