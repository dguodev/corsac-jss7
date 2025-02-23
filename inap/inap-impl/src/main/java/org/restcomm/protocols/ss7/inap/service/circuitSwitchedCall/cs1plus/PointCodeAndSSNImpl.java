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

package org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.cs1plus;

import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.PointCodeAndSSN;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 *
 * @author yulian.oifa
 *
 */
public class PointCodeAndSSNImpl extends ASNOctetString implements PointCodeAndSSN {
	public PointCodeAndSSNImpl() {
    }

    public PointCodeAndSSNImpl(Integer spc,Integer ssn) {
    	if(spc!=null || ssn!=null) {
    		byte[] value=new byte[3];
    		ByteBuf result=Unpooled.wrappedBuffer(value);
    		if(spc!=null)
    			result.writeShort(spc);
    		else
    			result.writeShort(0);
    		
    		if(ssn!=null)
    			result.writeByte(ssn);
    		
    		setValue(Unpooled.wrappedBuffer(value));    	
    	}
    }

    public Integer getSPC() {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() != 3)
            return null;

        return data.readUnsignedShort();
    }

    public Integer getSSN() {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() != 3)
            return null;

        data.skipBytes(2);
        return data.readByte() & 0x0FF;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("PointCodeAndSSN [");
        
        Integer spc=getSPC();
        if (spc != null) {
            sb.append("spc=");
            sb.append(spc);            
        }
        
        Integer ssn=getSSN();
        if (ssn != null) {
            sb.append("ssn=");
            sb.append(ssn);            
        }
        
        sb.append("]");

        return sb.toString();
    }
}
