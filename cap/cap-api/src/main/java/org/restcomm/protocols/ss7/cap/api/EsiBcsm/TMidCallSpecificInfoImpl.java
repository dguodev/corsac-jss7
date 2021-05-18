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

package org.restcomm.protocols.ss7.cap.api.EsiBcsm;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
*
* @author sergey vetyutnev
*
*/
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class TMidCallSpecificInfoImpl {
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = true,index = -1)
    private MidCallEventsWrapperImpl midCallEvents;

    public TMidCallSpecificInfoImpl() {        
    }

    public TMidCallSpecificInfoImpl(MidCallEventsImpl midCallEvents) {
        if(midCallEvents!=null)
        	this.midCallEvents = new MidCallEventsWrapperImpl(midCallEvents);
    }

    public MidCallEventsImpl getMidCallEvents() {
    	if(midCallEvents==null)
    		return null;
    	
        return midCallEvents.getMidCallEventsWrapper();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("TMidCallSpecificInfo [");

        if (this.midCallEvents != null) {
            sb.append("midCallEvents=");
            sb.append(midCallEvents.toString());
            sb.append(", ");
        }

        sb.append("]");

        return sb.toString();
    }
}
