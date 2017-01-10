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

package groovyx.gpars.samples.csp

import groovyx.gpars.csp.PAR
import groovyx.gpars.csp.plugAndPlay.GNumbers
import groovyx.gpars.csp.plugAndPlay.GPrint
import org.jcsp.lang.Channel
import org.jcsp.lang.One2OneChannel

One2OneChannel N2P = Channel.createOne2One()

def testList = [
        new GNumbers(outChannel: N2P.out()),
        new GPrint(inChannel: N2P.in(), heading: "Numbers")
]

final def par = new PAR(testList)
par.run()
