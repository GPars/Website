// GPars - Groovy Parallel Systems
//
// Copyright © 2008-2012  The original author or authors
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

package groovyx.gpars.samples.dataflow.operators

import groovyx.gpars.dataflow.DataflowQueue
import groovyx.gpars.group.DefaultPGroup

/**
 * Dataflow operators and selectors can hold explicit state
 *
 * @author Vaclav Pech
 */

final group = new DefaultPGroup()
final DataflowQueue a = new DataflowQueue()
final DataflowQueue b = new DataflowQueue()
final DataflowQueue c = new DataflowQueue()

def op = group.operator(inputs: [a, b], outputs: [c], stateObject: [counter: 0]) {x, y ->
    stateObject.counter += x+y
    bindOutput stateObject.counter
}

a << 10
b << 20
assert 30 == c.val
a << 1
b << 2
assert 33 == c.val

op.terminate()
group.shutdown()