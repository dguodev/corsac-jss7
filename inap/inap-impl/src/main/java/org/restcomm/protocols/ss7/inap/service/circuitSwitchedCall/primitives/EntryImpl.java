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

package org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.primitives;

import java.util.List;

import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive.Entry;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNInteger;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNObjectIdentifier;

/**
 *
 * @author yulian.oifa
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class EntryImpl implements Entry {

	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = false, index=-1)
    private ASNObjectIdentifier agreements;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = false, index=-1)
    private ASNInteger networkSpecific;

    public EntryImpl() {
    }

    public EntryImpl(List<Long> agreements) {
    	if(agreements!=null) {
    		this.agreements = new ASNObjectIdentifier();
    		this.agreements.setValue(agreements);    	
    	}
    }

    public EntryImpl(Integer networkSpecific) {
    	if(networkSpecific!=null) {
    		this.networkSpecific = new ASNInteger();
    		this.networkSpecific.setValue(networkSpecific.longValue());
    	}
    }

    public List<Long> getAgreements() {
    	if(agreements==null)
    		return null;
    	
        return agreements.getValue();
    }

    public Integer getNetworkSpecific() {
    	if(networkSpecific==null || networkSpecific.getValue()==null)
    		return null;
    	
        return networkSpecific.getValue().intValue();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Entry [");

        if (this.agreements!= null && this.agreements.getValue()!=null) {
            sb.append("agreements=[");
            sb.append(ASNObjectIdentifier.printDataArrLong(this.agreements.getValue()));
            sb.append("]");
        }
        if (this.networkSpecific != null && this.networkSpecific.getValue()!=null) {
            sb.append(", networkSpecific=");
            sb.append(networkSpecific.getValue());
        }

        sb.append("]");

        return sb.toString();
    }
}
