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

package c20

import org.jcsp.lang.*
import org.jcsp.net.*
import org.jcsp.net.tcpip.*
import org.jcsp.net.cns.*
import org.jcsp.groovy.*
import phw.util.*

Node.getInstance().init(new TCPIPNodeFactory())

def int nodeId = Ask.Int("Node identification? ", 1, 9)
def boolean last = Ask.Boolean("Is this the last node? - ( y or n):")
def int sentMessages = Ask.Int("Number of messages to be sent by a Sender? ", 10, 2000)
def int nodes = Ask.Int("Number of nodes? ", 1, 9)

def fromRingName = "ring" + nodeId
def toRingName = (last) ? "ring0" : "ring" + (nodeId + 1)

println " Node $nodeId: connection from $fromRingName to $toRingName "

def fromRing = CNS.createNet2One(fromRingName)
def toRing = CNS.createAny2Net(toRingName)

def processNode = new AgentElement(fromRing: fromRing,
        toRing: toRing,
        element: nodeId,
        iterations: sentMessages,
        nodes: nodes)

new PAR([processNode]).run()
