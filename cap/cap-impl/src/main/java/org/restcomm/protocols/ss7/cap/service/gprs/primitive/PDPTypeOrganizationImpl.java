/*
 * TeleStax, Open Source Cloud Communications  Copyright 2012.
 * and individual contributors
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
package org.restcomm.protocols.ss7.cap.service.gprs.primitive;

import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPTypeOrganization;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPTypeOrganizationValue;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNSingleByte;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
public class PDPTypeOrganizationImpl extends ASNSingleByte implements PDPTypeOrganization {
	public PDPTypeOrganizationImpl() {        
    }

    public PDPTypeOrganizationImpl(int data) {
        setValue(data);
    }

    public PDPTypeOrganizationImpl(PDPTypeOrganizationValue value) {
    	if(value==null)
    		setValue(0);
    	else
    		setValue(value.getCode() | 0xF0);        
    }

    public PDPTypeOrganizationValue getPDPTypeOrganizationValue() {
        return PDPTypeOrganizationValue.getInstance(getData() & 0x0F);
    }

    public int getData() {
    	Integer result=getValue();
    	if(result==null)
    		return 0;
    	
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PDPTypeOrganization [");

        if (this.getPDPTypeOrganizationValue() != null) {
            sb.append("PDPTypeOrganizationValue=");
            sb.append(this.getPDPTypeOrganizationValue());
        }

        sb.append("]");

        return sb.toString();
    }
}
