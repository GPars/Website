// GPars (formerly GParallelizer)
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

package c12.fork

import org.jcsp.lang.*
import org.jcsp.groovy.*

class LazyButler implements CSProcess {

    def ChannelInputList enters
    def ChannelInputList exits

    def void run() {
        def seats = enters.size()
        def allChans = []

        for (i in 0..<seats) { allChans << exits[i] }
        for (i in 0..<seats) { allChans << enters[i] }

        def eitherAlt = new ALT(allChans)

        while (true) {
            def i = eitherAlt.select()
            allChans[i].read()
        } // end while
    } //end run
} // end class