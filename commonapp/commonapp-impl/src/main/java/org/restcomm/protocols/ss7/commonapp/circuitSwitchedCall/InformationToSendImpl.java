/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2012, Telestax Inc and individual contributors
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

package org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall;

import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.InbandInfo;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.InformationToSend;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.Tone;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
 *
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class InformationToSendImpl implements InformationToSend {
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = true,index = -1, defaultImplementation = InbandInfoImpl.class)
    private InbandInfo inbandInfo;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = true,index = -1, defaultImplementation = ToneImpl.class)
    private Tone tone;

    public InformationToSendImpl() {
    }

    public InformationToSendImpl(InbandInfo inbandInfo) {
        this.inbandInfo = inbandInfo;
    }

    public InformationToSendImpl(Tone tone) {
        this.tone = tone;
    }

    public InbandInfo getInbandInfo() {
        return inbandInfo;
    }

    public Tone getTone() {
        return tone;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("InformationToSend [");

        if (this.inbandInfo != null) {
            sb.append("inbandInfo=");
            sb.append(inbandInfo.toString());
        }
        if (this.tone != null) {
            sb.append(" tone=");
            sb.append(tone.toString());
        }

        sb.append("]");

        return sb.toString();
    }
}
