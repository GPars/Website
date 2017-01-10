// GPars - Groovy Parallel Systems
//
// Copyright © 2008-10  The original author or authors
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

/**
 * A parallel quicksort implementation based on Dierk Koenig's sample code kata.
 *
 * @author Vaclav Pech, Dierk Koenig
 * Date: Dec 11, 2008
 */

package groovyx.gpars.samples.collections

import static groovyx.gpars.GParsPool.withPool

def quicksort(list) {
    if (list.size() < 2) return list.clone()
    withPool {
        def groups = list.groupByParallel {it <=> list[list.size().intdiv(2)]}
        if (groups.size() == 1) return list.clone()
        (-1..1).collect({quicksort(groups[it] as ArrayList ?: [])}).sumParallel()
    }
}

assert quicksort([]) == []
assert quicksort([1]) == [1]
assert quicksort([1, 2, 3]) == [1, 2, 3]
assert quicksort([3, 2, 1]) == [1, 2, 3]
assert quicksort([3, 1, 2, 1]) == [1, 1, 2, 3]
final def emptyList = []
assert !quicksort(emptyList).is(emptyList)

println quicksort([3, 20, 4, 6, 5, 7, 6, 2, 4, 3, 9, 0, 8, 7, 6, 7, 234, 545, 20, 24, 37, 2, 1])