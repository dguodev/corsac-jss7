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

package org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall;

import org.restcomm.protocols.ss7.commonapp.api.primitives.LegType;
import org.restcomm.protocols.ss7.commonapp.primitives.SendingLegIDImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.SendingLegIDWrapperImpl;
import org.restcomm.protocols.ss7.inap.api.INAPMessageType;
import org.restcomm.protocols.ss7.inap.api.INAPOperationCode;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.ReconnectRequest;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
 *
 * @author yulian.oifa
 *
 */
@ASNTag(asnClass = ASNClass.PRIVATE,tag = 1,constructed = true,lengthIndefinite = false)
public class ReconnectRequestImpl extends CircuitSwitchedCallMessageImpl implements ReconnectRequest {
	private static final long serialVersionUID = 1L;

	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = true,index = -1)
	private SendingLegIDWrapperImpl legID;
	
	public ReconnectRequestImpl() {
    }

    public ReconnectRequestImpl(LegType legID) {
        if(legID!=null)
        	this.legID=new SendingLegIDWrapperImpl(new SendingLegIDImpl(legID));
    }

    @Override
    public INAPMessageType getMessageType() {
        return INAPMessageType.reconnect_Request;
    }

    @Override
    public int getOperationCode() {
        return INAPOperationCode.reconnect;
    }

    @Override
    public LegType getLegID() {
		if(legID==null || legID.getSendingLegID()==null)
			return null;
		
		return legID.getSendingLegID().getSendingSideID();			
	}

	@Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("ReconnectRequestIndication [");
        this.addInvokeIdInfo(sb);

        if (this.legID != null && this.legID.getSendingLegID()!=null && this.legID.getSendingLegID().getSendingSideID()!=null) {
            sb.append(", legID=");
            sb.append(this.legID.getSendingLegID().getSendingSideID());
        }

        sb.append("]");

        return sb.toString();
    }
}
