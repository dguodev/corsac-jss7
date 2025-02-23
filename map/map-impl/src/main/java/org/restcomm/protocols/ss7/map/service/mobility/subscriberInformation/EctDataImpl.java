/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2016, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.restcomm.protocols.ss7.map.service.mobility.subscriberInformation;

import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.EctData;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.ExtSSStatus;
import org.restcomm.protocols.ss7.map.service.mobility.subscriberManagement.ExtSSStatusImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNNull;

/**
 * Created by vsubbotin on 26/05/16.
 */
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,lengthIndefinite=false)
public class EctDataImpl implements EctData {
	@ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=1,constructed=false,index=-1,defaultImplementation = ExtSSStatusImpl.class)
    private ExtSSStatus ssStatus;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=2,constructed=false,index=-1)
    private ASNNull notificationToCSE;

    public EctDataImpl() {
    }

    public EctDataImpl(ExtSSStatus ssStatus, boolean notificationToCSE) {
        this.ssStatus = ssStatus;
        
        if(notificationToCSE)
        	this.notificationToCSE = new ASNNull();
    }

    public ExtSSStatus getSsStatus() {
        return this.ssStatus;
    }

    public boolean getNotificationToCSE() {
        return this.notificationToCSE!=null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EctData [");

        if (this.ssStatus != null) {
            sb.append("ssStatus=");
            sb.append(this.ssStatus);
        }
        if (this.notificationToCSE!=null) {
            sb.append(", notificationToCSE");
        }

        sb.append("]");
        return sb.toString();
    }
}
