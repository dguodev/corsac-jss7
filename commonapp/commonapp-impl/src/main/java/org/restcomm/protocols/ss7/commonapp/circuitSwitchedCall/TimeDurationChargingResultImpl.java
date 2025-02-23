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

import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.TimeDurationChargingResult;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.TimeInformation;
import org.restcomm.protocols.ss7.commonapp.api.primitives.AChChargingAddress;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CAPINAPExtensions;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LegType;
import org.restcomm.protocols.ss7.commonapp.primitives.AChChargingAddressWrapperImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.CAPINAPExtensionsImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.ReceivingLegIDImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.ReceivingLegIDWrapperImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNBoolean;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNNull;

/**
 *
 * @author sergey vetyutnev
 * @author Amit Bhayani
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class TimeDurationChargingResultImpl implements TimeDurationChargingResult {
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = true,index = -1)
    private ReceivingLegIDWrapperImpl partyToCharge;

    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = true,index = -1)
    private TimeInformationWrapperImpl timeInformation;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 2,constructed = false,index = -1)
    private ASNBoolean legActive;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 3,constructed = false,index = -1)
    private ASNNull callLegReleasedAtTcpExpiry;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 4,constructed = true,index = -1,defaultImplementation = CAPINAPExtensionsImpl.class)
    private CAPINAPExtensions extensions;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 5,constructed = true,index = -1)
    private AChChargingAddressWrapperImpl aChChargingAddress;

    public TimeDurationChargingResultImpl() {
    }

    public TimeDurationChargingResultImpl(LegType partyToCharge, TimeInformation timeInformation, boolean legActive,
            boolean callLegReleasedAtTcpExpiry, CAPINAPExtensions extensions, AChChargingAddress aChChargingAddress) {
        if(partyToCharge!=null)
        	this.partyToCharge = new ReceivingLegIDWrapperImpl(new ReceivingLegIDImpl(partyToCharge));
        
        if(timeInformation!=null)
        	this.timeInformation = new TimeInformationWrapperImpl(timeInformation);
        
        if(!legActive) {
        	this.legActive = new ASNBoolean();
        	this.legActive.setValue(legActive);
        }
        
        if(callLegReleasedAtTcpExpiry)
        	this.callLegReleasedAtTcpExpiry = new ASNNull();
        
        this.extensions = extensions;
        
        if(aChChargingAddress!=null)
        	this.aChChargingAddress = new AChChargingAddressWrapperImpl(aChChargingAddress);
    }

    public LegType getPartyToCharge() {
    	if(partyToCharge==null || partyToCharge.getReceivingLegID()==null)
    		return null;
    	
        return partyToCharge.getReceivingLegID().getReceivingSideID();
    }

    public TimeInformation getTimeInformation() {
    	if(timeInformation==null)
    		return null;
    	
        return timeInformation.getTimeInformation();
    }

    public boolean getLegActive() {
    	if(legActive==null || legActive.getValue()==null)
    		return true;
    	
        return legActive.getValue();
    }

    public boolean getCallLegReleasedAtTcpExpiry() {
        return callLegReleasedAtTcpExpiry!=null;
    }

    public CAPINAPExtensions getExtensions() {
        return extensions;
    }

    public AChChargingAddress getAChChargingAddress() {
    	if(aChChargingAddress==null)
    		return null;
    	
        return aChChargingAddress.getAChChargingAddress();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("TimeDurationChargingResult [");

        if (this.partyToCharge != null && this.partyToCharge.getReceivingLegID()!=null) {
            sb.append("partyToCharge=");
            sb.append(partyToCharge.getReceivingLegID());
        }
        if (this.timeInformation != null && this.timeInformation.getTimeInformation()!=null) {
            sb.append(", timeInformation=");
            sb.append(timeInformation.getTimeInformation());
        }
        if (this.legActive==null || this.legActive.getValue()==null || this.legActive.getValue()) {
            sb.append(", legActive");
        }
        if (this.callLegReleasedAtTcpExpiry!=null) {
            sb.append(", callLegReleasedAtTcpExpiry");
        }
        if (this.extensions != null) {
            sb.append(", extensions=");
            sb.append(extensions.toString());
        }
        if (this.aChChargingAddress != null) {
            sb.append(", aChChargingAddress=");
            sb.append(aChChargingAddress.toString());
        }

        sb.append("]");

        return sb.toString();
    }
}
