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

package org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.primitives;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author yulian.oifa
 *
 */
@ASNTag(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = false,lengthIndefinite = false)
public class UpdateResultChoiseImpl {
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = false,index = -1)
	private ASNOctetString operationReturnID;
    
    public UpdateResultChoiseImpl() {
    }

    public UpdateResultChoiseImpl(byte[] operationReturnID) {
    	if(operationReturnID!=null) {
    		this.operationReturnID=new ASNOctetString();
    		this.operationReturnID.setValue(Unpooled.wrappedBuffer(operationReturnID));
    	}
    }

    public byte[] getOperationReturnID() {
    	if(operationReturnID==null || operationReturnID.getValue()==null)
    		return null;
    	
    	ByteBuf value=operationReturnID.getValue();
    	byte[] data=new byte[value.readableBytes()];
    	value.readBytes(data);
    	return data;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("UpdateResultRequest [");
        if (this.operationReturnID != null && this.operationReturnID.getValue()!=null) {
            sb.append("operationReturnID=");
            sb.append(ASNOctetString.printDataArr(getOperationReturnID()));
        }
        
        sb.append("]");

        return sb.toString();
    }
}
