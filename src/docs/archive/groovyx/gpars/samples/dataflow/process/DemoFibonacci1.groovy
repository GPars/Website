// GPars - Groovy Parallel Systems
//
// Copyright © 2008-11  The original author or authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package groovyx.gpars.samples.dataflow.process

import groovyx.gpars.dataflow.DataflowChannel
import groovyx.gpars.dataflow.DataflowQueue
import groovyx.gpars.group.DefaultPGroup

group = new DefaultPGroup(6)

def fib(DataflowChannel out) {
    group.task {
        def a = new DataflowQueue()
        def b = new DataflowQueue()
        def c = new DataflowQueue()
        def d = new DataflowQueue()
        [new Prefix(d, a, 0L), new Prefix(c, d, 1L), new Copy(a, b, out), new StatePairs(b, c)].each { group.task it}
    }
}

final DataflowQueue ch = new DataflowQueue()
group.task new Print('Fibonacci numbers', ch)
fib(ch)

sleep 10000