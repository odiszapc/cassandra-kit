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

import java.util.ArrayList;
import java.util.List;

public class Args extends ArgsGeneral {
    @Parameter(names = "-par", description = "do a parallel repair")
    private Boolean par = false;

    @Parameter(names = "-inc", description = "do an incremental repair")
    private Boolean inc = false;

    @Parameter(names = "-dc", description = "restrict repair to nodes in the named data center, which must be the local data center")
    private List<String> dc = new ArrayList<String>();

}
