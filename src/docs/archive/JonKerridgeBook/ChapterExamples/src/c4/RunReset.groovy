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

package c4

import org.jcsp.lang.*
import org.jcsp.groovy.*
import org.jcsp.groovy.plugAndPlay.*

One2OneChannel RU2RN = Channel.createOne2One()

One2OneChannel RN2Conv = Channel.createOne2One()
One2OneChannel Conv2FD = Channel.createOne2One()
One2OneChannel FD2GC = Channel.createOne2One()

def RNprocList = [new ResetNumbers(resetChannel: RU2RN.in(),
        initialValue: 1000,
        outChannel: RN2Conv.out()),
        new GObjectToConsoleString(inChannel: RN2Conv.in(),
                outChannel: Conv2FD.out()),
        new GFixedDelay(delay: 200,
                inChannel: Conv2FD.in(),
                outChannel: FD2GC.out()),
        new GConsole(toConsole: FD2GC.in(),
                frameLabel: "Reset Numbers Console")
]

def One2OneChannel RU2GC = Channel.createOne2One()
def One2OneChannel GC2Conv = Channel.createOne2One()
def One2OneChannel Conv2RU = Channel.createOne2One()
def One2OneChannel RU2GCClear = Channel.createOne2One()

def RUprocList = [new ResetUser(resetValue: RU2RN.out(),
        toConsole: RU2GC.out(),
        fromConverter: Conv2RU.in(),
        toClearOutput: RU2GCClear.out()),
        new GConsoleStringToInteger(inChannel: GC2Conv.in(),
                outChannel: Conv2RU.out()),
        new GConsole(toConsole: RU2GC.in(),
                fromConsole: GC2Conv.out(),
                clearInputArea: RU2GCClear.in(),
                frameLabel: "Reset Value Generator")
]
new PAR(RNprocList + RUprocList).run()
