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

package org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.cs1plus;

import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.GlobalTitle;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.GlobalTitleAndSSN;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.PointCodeAndSSN;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.PointCodeAndSSNANSI;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.SCPAddress;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNNull;

/**
 *
 * @author yulian.oifa
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class SCPAddressImpl implements SCPAddress {

	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = false, index=-1)
    private ASNNull collocated;
    
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = false, index=-1, defaultImplementation = PointCodeAndSSNImpl.class)
    private PointCodeAndSSN pointCodeAndSubSystemNumber;

    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 2,constructed = false, index=-1, defaultImplementation = GlobalTitleImpl.class)
    private GlobalTitle globalTitle;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 3,constructed = false, index=-1, defaultImplementation = GlobalTitleAndSSNImpl.class)
    private GlobalTitleAndSSN globalTitleAndSSN;

    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 4,constructed = false, index=-1, defaultImplementation = PointCodeAndSSNANSIImpl.class)
    private PointCodeAndSSNANSI pointCodeAndSSNANSI;

    public SCPAddressImpl() {
    }

    public SCPAddressImpl(boolean collocated) {
    	if(collocated)
    		this.collocated=new ASNNull();
    }
    
    public SCPAddressImpl(PointCodeAndSSN pointCodeAndSubSystemNumber) {
    	this.pointCodeAndSubSystemNumber=pointCodeAndSubSystemNumber;
    }
    
    public SCPAddressImpl(GlobalTitle globalTitle) {
    	this.globalTitle=globalTitle;
    }
    
    public SCPAddressImpl(GlobalTitleAndSSN globalTitleAndSSN) {
    	this.globalTitleAndSSN=globalTitleAndSSN;
    }
    
    public SCPAddressImpl(PointCodeAndSSNANSI pointCodeAndSSNANSI) {
    	this.pointCodeAndSSNANSI=pointCodeAndSSNANSI;
    }

    public boolean getIsColocated() {
    	return collocated!=null;
    }

    public PointCodeAndSSN getPCAndSSN() {
    	return pointCodeAndSubSystemNumber;
    }

    public GlobalTitle getGlobalTitle() {
    	return globalTitle;
    }

    public GlobalTitleAndSSN getGlobalTitleAndSSN() {
    	return globalTitleAndSSN;
    }  
    
    public PointCodeAndSSNANSI getPointCodeAndSubSystemNumberANSI() {
    	return pointCodeAndSSNANSI;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("SCPAddress [");

        if (this.collocated != null)
            sb.append(", collocated");            
        
        if (this.pointCodeAndSubSystemNumber != null) {
            sb.append(", pointCodeAndSubSystemNumber=");
            sb.append(pointCodeAndSubSystemNumber);
        }
        
        if (this.globalTitle != null) {
            sb.append(", globalTitle=");
            sb.append(globalTitle);
        }
        
        if (this.globalTitleAndSSN != null) {
            sb.append(", globalTitleAndSSN=");
            sb.append(globalTitleAndSSN);
        }
        
        if (this.pointCodeAndSSNANSI != null) {
            sb.append(", pointCodeAndSSNANSI=");
            sb.append(pointCodeAndSSNANSI);
        }
        
        sb.append("]");

        return sb.toString();
    }
}
