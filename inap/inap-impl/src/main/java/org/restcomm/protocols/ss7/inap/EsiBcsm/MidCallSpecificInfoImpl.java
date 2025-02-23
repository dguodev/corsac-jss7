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

package org.restcomm.protocols.ss7.inap.EsiBcsm;

import org.restcomm.protocols.ss7.inap.api.EsiBcsm.MidCallEvents;
import org.restcomm.protocols.ss7.inap.api.EsiBcsm.MidCallSpecificInfo;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNInteger;

/**
*
* @author yulian.oifa
*
*/
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class MidCallSpecificInfoImpl implements MidCallSpecificInfo {
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = false,index = -1)
    private ASNInteger connectTime;

	@ASNProperty(asnClass = ASNClass.PRIVATE,tag = 1,constructed = true,index = -1)
    private MidCallEventsWrapperImpl midCallEvents;

    public MidCallSpecificInfoImpl() {
    }

    public MidCallSpecificInfoImpl(Integer connectTime) {
        if(connectTime!=null) {
        	this.connectTime = new ASNInteger();
        	this.connectTime.setValue(connectTime.longValue());
        }
    }

    public MidCallSpecificInfoImpl(MidCallEvents midCallEvents) {
        if(midCallEvents!=null)
        	this.midCallEvents = new MidCallEventsWrapperImpl(midCallEvents);
    }

    public Integer getConnectTime() {
    	if(connectTime==null || connectTime.getValue()==null)
    		return null;
    	
        return connectTime.getValue().intValue();
    }

    public MidCallEvents getMidCallEvents() {
    	if(midCallEvents==null)
    		return null;
    	
        return midCallEvents.getMidCallEventsWrapper();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("MidCallSpecificInfo [");

        if (this.connectTime != null) {
            sb.append("connectTime=");
            sb.append(connectTime.getValue().toString());
            sb.append(", ");
        }
        
        if (this.midCallEvents != null) {
            sb.append("midCallEvents=");
            sb.append(midCallEvents.toString());
            sb.append(", ");
        }

        sb.append("]");

        return sb.toString();
    }
}
