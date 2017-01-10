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

import org.jcsp.net.NetChannelLocation;

import java.io.Serializable;

/**
 * @author JM Kerridge
 */

public class MeetingData implements Serializable {

    static final long serialVersionUID = 9;

    private NetChannelLocation returnChannel;
    private int clientId;
    private String meetingName;
    private String meetingPlace;
    private int attendees;

    /**
     * @param returnChannel Channel to which any process reading an object of this type will write
     * @param clientId      identifier of the client process using thsi object
     * @param meetingName   Name of the meeting obtained from a user interface process
     * @param meetingPlace  Name of place where meeting is to tkae place; obtained from the user interface
     */
    public MeetingData(NetChannelLocation returnChannel,
                       int clientId,
                       String meetingName,
                       String meetingPlace) {
        this.returnChannel = returnChannel;
        this.clientId = clientId;
        this.meetingName = meetingName;
        this.meetingPlace = meetingPlace;
    }


    /**
     * @param returnChannel Channel to which any process reading an object of this type will write
     * @param clientId      Identifier of the client process using thsi object
     * @param meetingName   Name of the meeting obtained from a user interface process
     * @param meetingPlace  Name of place where meeting is to tkae place; obtained from the user interface
     * @param attendees     The number of attendees at this meeting
     */
    public MeetingData(NetChannelLocation returnChannel,
                       int clientId,
                       String meetingName,
                       String meetingPlace,
                       int attendees) {
        this.returnChannel = returnChannel;
        this.clientId = clientId;
        this.meetingName = meetingName;
        this.meetingPlace = meetingPlace;
        this.attendees = attendees;
    }


    public MeetingData() {
    }


// Getters and Setters

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public NetChannelLocation getReturnChannel() {
        return returnChannel;
    }

    public void setReturnChannel(NetChannelLocation returnChannel) {
        this.returnChannel = returnChannel;
    }

}

  