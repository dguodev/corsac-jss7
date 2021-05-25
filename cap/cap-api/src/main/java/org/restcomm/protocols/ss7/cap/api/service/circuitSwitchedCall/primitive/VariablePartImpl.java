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

package org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive;

import org.restcomm.protocols.ss7.cap.api.isup.DigitsImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNInteger;

/**
 *
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 4,constructed = false,lengthIndefinite = false)
public class VariablePartImpl {
	public static final String _PrimitiveName = "VariablePart";

    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = false,index = -1)
    private ASNInteger integer;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = false,index = -1)
    private DigitsImpl number;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 2,constructed = false,index = -1)
    private VariablePartTimeImpl time;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 3,constructed = false,index = -1)
    private VariablePartDateImpl date;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 4,constructed = false,index = -1)
    private VariablePartPriceImpl price;

    public VariablePartImpl() {
    }

    public VariablePartImpl(Integer integer) {
    	if(integer!=null) {
    		this.integer = new ASNInteger();
    		this.integer.setValue(integer.longValue());
    	}
    }

    public VariablePartImpl(DigitsImpl number) {
        this.number = number;
    }

    public VariablePartImpl(VariablePartTimeImpl time) {
        this.time = time;
    }

    public VariablePartImpl(VariablePartDateImpl date) {
        this.date = date;
    }

    public VariablePartImpl(VariablePartPriceImpl price) {
        this.price = price;
    }

    public Integer getInteger() {
    	if(integer==null || integer.getValue()==null)
    		return null;
    	
        return integer.getValue().intValue();
    }

    public DigitsImpl getNumber() {
    	if(number!=null)
    		number.setIsGenericDigits();
    	
        return number;
    }

    public VariablePartTimeImpl getTime() {
        return time;
    }

    public VariablePartDateImpl getDate() {
        return date;
    }

    public VariablePartPriceImpl getPrice() {
        return price;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("VariablePart [");

        if (this.integer != null) {
            sb.append("integer=");
            sb.append(integer.getValue());
        }
        if (this.number != null) {
            sb.append(" number=");
            sb.append(number.toString());
        }
        if (this.time != null) {
            sb.append(" time=");
            sb.append(time.toString());
        }
        if (this.date != null) {
            sb.append(" date=");
            sb.append(date.toString());
        }
        if (this.price != null) {
            sb.append(" price=");
            sb.append(price.toString());
        }

        sb.append("]");

        return sb.toString();
    }
}
