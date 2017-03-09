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

package c12.canteen

import org.jcsp.lang.*
import org.jcsp.groovy.*


class Chef implements CSProcess {

    def ChannelOutput supply
    def ChannelOutput toConsole

    def void run() {

        def tim = new CSTimer()
        def CHICKENS = 4

        toConsole.write("Starting ... \n")
        while (true) {
            // cook 4 chickens
            toConsole.write("Cooking ... \n")
            tim.after(tim.read() + 2000)       // this takes 2 seconds to cook
            toConsole.write("${CHICKENS} chickens ready ... \n")
            supply.write(CHICKENS)
            toConsole.write("Taking chickens to Canteen ... \n")
            supply.write(0)
        }
    }

}
