/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

/**
 *
 */
package org.restcomm.protocols.ss7.tcap.tc.dialog.events;

import org.restcomm.protocols.ss7.sccp.parameter.SccpAddress;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.EventType;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCUniIndication;
import org.restcomm.protocols.ss7.tcap.asn.ApplicationContextName;
import org.restcomm.protocols.ss7.tcap.asn.UserInformation;

import io.netty.buffer.ByteBuf;

/**
 * @author baranowb
 *
 */
public class TCUniIndicationImpl extends DialogIndicationImpl implements TCUniIndication {
	// private Byte qos;
    private SccpAddress originatingAddress, destinationAddress;

    // fields
    private ApplicationContextName applicationContextName;
    private UserInformation userInformation;

    TCUniIndicationImpl(ByteBuf originalBuffer) {
        super(EventType.Uni, originalBuffer);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest# getApplicationContextName()
     */
    public ApplicationContextName getApplicationContextName() {
        return applicationContextName;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest# getDestinationAddress()
     */
    public SccpAddress getDestinationAddress() {

        return this.destinationAddress;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest# getOriginatingAddress()
     */
    public SccpAddress getOriginatingAddress() {

        return this.originatingAddress;
    }

    // /*
    // * (non-Javadoc)
    // *
    // * @see
    // * org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest#
    // * getQOS()
    // */
    // public Byte getQOS() {
    //
    // return this.qos;
    // }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest# getUserInformation()
     */
    public UserInformation getUserInformation() {

        return this.userInformation;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest# setApplicationContextName
     * (org.restcomm.protocols.ss7.tcap.asn.ApplicationContextName)
     */
    public void setApplicationContextName(ApplicationContextName acn) {
        this.applicationContextName = acn;

    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest# setDestinationAddress
     * (org.restcomm.protocols.ss7.sccp.parameter.SccpAddress)
     */
    public void setDestinationAddress(SccpAddress dest) {
        this.destinationAddress = dest;

    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest# setOriginatingAddress
     * (org.restcomm.protocols.ss7.sccp.parameter.SccpAddress)
     */
    public void setOriginatingAddress(SccpAddress dest) {
        this.originatingAddress = dest;

    }

    // /*
    // * (non-Javadoc)
    // *
    // * @see
    // * org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest#
    // * setQOS(java.lang.Byte)
    // */
    // public void setQOS(Byte b) throws IllegalArgumentException {
    // this.qos = b;
    //
    // }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest#
     * setUserInformation(org.restcomm.protocols.ss7.tcap.asn.UserInformation)
     */
    public void setUserInformation(UserInformation acn) {
        this.userInformation = acn;

    }

}
