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

import org.restcomm.protocols.ss7.commonapp.api.APPException;
import org.restcomm.protocols.ss7.commonapp.api.APPParsingComponentException;
import org.restcomm.protocols.ss7.commonapp.api.APPParsingComponentExceptionReason;
import org.restcomm.protocols.ss7.commonapp.primitives.TbcdStringImpl;
import org.restcomm.protocols.ss7.inap.api.INAPException;
import org.restcomm.protocols.ss7.inap.api.INAPParsingComponentException;
import org.restcomm.protocols.ss7.inap.api.INAPParsingComponentExceptionReason;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.GlobalTitleAndSSN;
import org.restcomm.protocols.ss7.indicator.NatureOfAddress;
import org.restcomm.protocols.ss7.indicator.NumberingPlan;
import org.restcomm.protocols.ss7.sccp.impl.parameter.GlobalTitle0100Impl;
import org.restcomm.protocols.ss7.sccp.parameter.EncodingScheme;
import org.restcomm.protocols.ss7.sccp.parameter.GlobalTitle0100;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 *
 * @author yulian.oifa
 *
 */
public class GlobalTitleAndSSNImpl extends ASNOctetString implements GlobalTitleAndSSN {
	public GlobalTitleAndSSNImpl() {
    }

	public GlobalTitleAndSSNImpl(GlobalTitle0100 globalTitle,Integer ssn) throws INAPException {
		super(translate(globalTitle, ssn));
	}
	
    public static ByteBuf translate(GlobalTitle0100 globalTitle,Integer ssn) throws INAPException {
    	if(globalTitle!=null || ssn!=null) {
    		int length=4;
    		
    		if(globalTitle.getDigits()!=null)
    			length+=TbcdStringImpl.getLength(false, null, globalTitle.getDigits());
    		
    		ByteBuf result=Unpooled.buffer(length);
    		if(ssn!=null)
    			result.writeByte(ssn);
    		else
    			result.writeByte(0);
    		
    		result.writeByte(globalTitle.getTranslationType());
    		int currByte=0;
    		if(globalTitle.getNumberingPlan()!=null)
    			currByte=(globalTitle.getNumberingPlan().getValue()<<4);
    		
    		if(globalTitle.getEncodingScheme()!=null)
    			currByte+=globalTitle.getEncodingScheme().getSchemeCode();
    		
    		result.writeByte(currByte);
    		if(globalTitle.getNatureOfAddress()!=null)
    			result.writeByte(globalTitle.getNatureOfAddress().getValue());
    		else
    			result.writeByte(0);
    		
    		if(globalTitle.getDigits()!=null) {
    			try {
    				TbcdStringImpl.encodeString(result, globalTitle.getDigits());
    			}
    			catch(APPException ex) {
    				throw new INAPException(ex.getMessage(), ex.getCause());
    			}
    		}

    		return result;	
    	}
    	
    	return null;
    }

    public GlobalTitle0100 getTitle() throws INAPParsingComponentException {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() < 4)
            return null;

        data.skipBytes(1);
        int tt=data.readByte();
        
        int currByte=data.readByte() & 0x0FF;
        NumberingPlan np=NumberingPlan.valueOf((currByte>>4) & 0x00F);        
        EncodingScheme es=GlobalTitleImpl.createEncodingScheme((byte)(currByte & 0x00F));
        
        NatureOfAddress noa=NatureOfAddress.valueOf(data.readByte());
        String digits = null;
        try {
        	digits = TbcdStringImpl.decodeString(data);
        }
        catch(APPParsingComponentException ex) {
        	INAPParsingComponentExceptionReason reason=null;
        	if(ex.getReason()!=null) {
        		if(ex.getReason()==APPParsingComponentExceptionReason.MistypedParameter)
        			reason=INAPParsingComponentExceptionReason.MistypedParameter;
        		else
        			reason=INAPParsingComponentExceptionReason.UnrecognizedOperation;
        	}
        	
        	throw new INAPParsingComponentException(ex.getMessage(), ex.getCause(), reason);
        }
        
        return new GlobalTitle0100Impl(digits, tt, es, np, noa);
    }

    public Integer getSSN() {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() == 0)
            return null;

        return data.readByte() & 0x0FF;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("GlobalTitleAndSSN [");
        
        GlobalTitle0100 gt=null;
        try {
        	gt=getTitle();
        }
        catch(INAPParsingComponentException ex) {
        	
        }
        
        if (gt != null) {
            sb.append("gt=");
            sb.append(gt);            
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
