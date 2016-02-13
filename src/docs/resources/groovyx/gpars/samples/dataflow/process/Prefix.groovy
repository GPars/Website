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
import java.util.concurrent.Callable

final class Prefix implements Callable {
    private final DataflowChannel inChannel
    private final DataflowChannel outChannel
    private final def prefix

    def Prefix(final inChannel, final outChannel, final prefix) {
        this.inChannel = inChannel;
        this.outChannel = outChannel;
        this.prefix = prefix
    }

    public def call() {
        outChannel << prefix
        while (true) {
            sleep 200
            outChannel << inChannel.val
        }
    }
}
