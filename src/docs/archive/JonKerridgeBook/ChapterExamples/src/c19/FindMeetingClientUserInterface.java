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

package c19;

import org.jcsp.awt.ActiveClosingFrame;
import org.jcsp.awt.ActiveLabel;
import org.jcsp.awt.ActiveTextEnterField;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.Parallel;

import java.awt.*;


public class FindMeetingClientUserInterface implements CSProcess {
    private ChannelOutput meetingNameEventOutput;
    private ChannelInput registeredConfigureInput;
    private ChannelInput registeredLocationConfigureInput;
    private ChannelInput attendeesConfigureInput;

    public FindMeetingClientUserInterface(ChannelOutput meetingNameEventOutput,
                                          ChannelInput registeredConfigureInput,
                                          ChannelInput registeredLocationConfigureInput,
                                          ChannelInput attendeesConfigureInput) {
        this.meetingNameEventOutput = meetingNameEventOutput;
        this.registeredConfigureInput = registeredConfigureInput;
        this.registeredLocationConfigureInput = registeredLocationConfigureInput;
        this.attendeesConfigureInput = attendeesConfigureInput;
    }

    public void run() {
        // set up the user interface
        // an active frame with border layout containing three containers
        final ActiveClosingFrame main = new ActiveClosingFrame("Find Meeting");
        final Frame root = main.getActiveFrame();
        root.setLayout(new BorderLayout());
        root.setSize(320, 480);
        // north is the user input container
        final Container inputContainer = new Container();
        inputContainer.setLayout(new GridLayout(2, 1));
        final ActiveLabel nameLabel = new ActiveLabel("Meeting Name");
        final ActiveTextEnterField nameField = new ActiveTextEnterField(null, meetingNameEventOutput);
        inputContainer.add(nameLabel);
        inputContainer.add(nameField.getActiveTextField());
        root.add(inputContainer, BorderLayout.NORTH);
        // center is the server response container
        final Container responseContainer = new Container();
        responseContainer.setLayout(new GridLayout(2, 2));
        final ActiveLabel registeredLabel = new ActiveLabel(registeredConfigureInput);
        final ActiveLabel attendeesLabel = new ActiveLabel("Attendees");
        final ActiveLabel registeredLocationLabel = new ActiveLabel(registeredLocationConfigureInput);
        final ActiveLabel attendeeNumberLabel = new ActiveLabel(attendeesConfigureInput);
        responseContainer.add(registeredLabel);
        responseContainer.add(attendeesLabel);
        responseContainer.add(registeredLocationLabel);
        responseContainer.add(attendeeNumberLabel);
        root.add(responseContainer, BorderLayout.CENTER);
        root.pack();
        root.setVisible(true);

        final CSProcess[] network = {nameLabel,
                nameField,
                registeredLabel,
                attendeesLabel,
                registeredLocationLabel,
                attendeeNumberLabel,
                main};
        new Parallel(network).run();
    }
}