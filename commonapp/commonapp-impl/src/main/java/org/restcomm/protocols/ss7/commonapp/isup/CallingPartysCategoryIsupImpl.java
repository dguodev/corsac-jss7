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

package org.restcomm.protocols.ss7.commonapp.isup;

import org.restcomm.protocols.ss7.commonapp.api.APPException;
import org.restcomm.protocols.ss7.commonapp.api.isup.CallingPartysCategoryIsup;
import org.restcomm.protocols.ss7.isup.ParameterException;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.CallingPartyCategoryImpl;
import org.restcomm.protocols.ss7.isup.message.parameter.CallingPartyCategory;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNDecode;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNEncode;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNLength;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=0x05,constructed=false,lengthIndefinite=false)
public class CallingPartysCategoryIsupImpl implements CallingPartysCategoryIsup {
	private CallingPartyCategoryImpl category;

    public CallingPartysCategoryIsupImpl() {
    }

    public CallingPartysCategoryIsupImpl(CallingPartyCategoryImpl category) {
        this.category = category;
    }

    public CallingPartysCategoryIsupImpl(CallingPartyCategory callingPartyCategory) throws APPException {
        setCallingPartysCategory(callingPartyCategory);
    }

    public void setCallingPartysCategory(CallingPartyCategory callingPartyCategory) throws APPException {
        if (callingPartyCategory == null)
            throw new APPException("The callingPartyCategory parameter must not be null");
        
        this.category = (CallingPartyCategoryImpl) callingPartyCategory;
    }

    public CallingPartyCategory getCallingPartyCategory() {
    	return category;
    }

    @ASNLength
    public Integer getLength(ASNParser parser) {
		return 1;
	}
	
	@ASNEncode
	public void encode(ASNParser parser, ByteBuf buffer) {
		this.category.encode(buffer);
	}
	
	@ASNDecode
	public Boolean decode(ASNParser parser,Object parent,ByteBuf buffer,Boolean skipErrors) {
		try {
			this.category=new CallingPartyCategoryImpl(buffer);
		} catch (ParameterException e) {
			e.printStackTrace();
		}
		
		return false;
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CallingPartysCategoryInap [");

        if (this.getCallingPartyCategory() != null) {
        	CallingPartyCategory cpc = this.getCallingPartyCategory();
            sb.append(", ");
            sb.append(cpc.toString());
        }

        sb.append("]");

        return sb.toString();
    }
}
