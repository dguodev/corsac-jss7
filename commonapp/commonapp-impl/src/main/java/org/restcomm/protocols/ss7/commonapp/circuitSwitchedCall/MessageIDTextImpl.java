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

import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.MessageIDText;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNIA5String;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class MessageIDTextImpl implements MessageIDText {
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = false,index = -1)
    private ASNIA5String messageContent;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = false,index = -1)
    private ASNOctetString attributes;

    public MessageIDTextImpl() {
    }

    public MessageIDTextImpl(String messageContent, byte[] attributes) {
        if(messageContent!=null) {
        	this.messageContent = new ASNIA5String();
        	this.messageContent.setValue(messageContent);
        }
        
        if(attributes!=null) {
        	this.attributes = new ASNOctetString();
        	this.attributes.setValue(Unpooled.wrappedBuffer(attributes));
        }
    }

    public String getMessageContent() {
    	if(messageContent==null)
    		return null;
    	
        return messageContent.getValue();
    }

    public byte[] getAttributes() {
    	if(attributes==null)
    		return null;
    	
    	ByteBuf value=attributes.getValue();
    	if(value==null)
    		return null;
    	
    	byte[] data=new byte[value.readableBytes()];
    	value.readBytes(data);
        return data;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("MessageIDText [");

        if (this.messageContent != null) {
            sb.append("messageContent=[");
            sb.append(messageContent);
            sb.append("]");
        }
        
        byte[] data=this.getAttributes();
        if (data != null) {
            sb.append(", attributes=");
            sb.append(ASNOctetString.printDataArr(data));
        }

        sb.append("]");

        return sb.toString();
    }
}
