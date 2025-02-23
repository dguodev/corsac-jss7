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

package org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall;

import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.Carrier;
import org.restcomm.protocols.ss7.commonapp.api.isup.CauseIsup;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CAPINAPExtensions;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.CarrierImpl;
import org.restcomm.protocols.ss7.commonapp.isup.CauseIsupImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.CAPINAPExtensionsImpl;
import org.restcomm.protocols.ss7.inap.api.INAPMessageType;
import org.restcomm.protocols.ss7.inap.api.INAPOperationCode;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.ODisconnectRequest;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive.CallingPartyBusinessGroupID;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive.CallingPartySubaddress;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive.DpSpecificCommonParameters;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive.FacilityGroup;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive.RouteList;
import org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.primitives.CallingPartyBusinessGroupIDImpl;
import org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.primitives.DpSpecificCommonParametersImpl;
import org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.primitives.FacilityGroupWrapperImpl;
import org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.primitives.RouteListImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNInteger;

/**
 *
 * @author yulian.oifa
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class ODisconnectRequestImpl extends CircuitSwitchedCallMessageImpl implements ODisconnectRequest {
	private static final long serialVersionUID = 1L;

	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = true,index = -1,defaultImplementation = DpSpecificCommonParametersImpl.class)
	private DpSpecificCommonParameters dpSpecificCommonParameters;
	
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = false,index = -1,defaultImplementation = CallingPartyBusinessGroupIDImpl.class)
	private CallingPartyBusinessGroupID callingPartyBusinessGroupID;
    
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 2,constructed = false,index = -1,defaultImplementation = CallingPartySubaddress.class)
	private CallingPartySubaddress callingPartySubaddress;
	
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 3,constructed = true,index = -1)
	private FacilityGroupWrapperImpl callingFacilityGroup;
	
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 4,constructed = false,index = -1)
	private ASNInteger callingFacilityGroupMember;
	
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 5,constructed = false,index = -1, defaultImplementation = CauseIsupImpl.class)
	private CauseIsup releaseCause;
	
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 6,constructed = true,index = -1, defaultImplementation = RouteListImpl.class)
	private RouteList routeList;
    
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 7,constructed = true,index = -1,defaultImplementation = CAPINAPExtensionsImpl.class)
	private CAPINAPExtensions extensions;
	
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 8,constructed = false,index = -1, defaultImplementation = CarrierImpl.class)
	private Carrier carrier;

	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 9,constructed = false,index = -1)
	private ASNInteger connectTime;
	
	public ODisconnectRequestImpl() {
    }

    public ODisconnectRequestImpl(DpSpecificCommonParameters dpSpecificCommonParameters,CallingPartyBusinessGroupID callingPartyBusinessGroupID,
    		CallingPartySubaddress callingPartySubaddress,FacilityGroup callingFacilityGroup,Integer callingFacilityGroupMember,
    		CauseIsup releaseCause,RouteList routeList,CAPINAPExtensions extensions,Carrier carrier,Integer connectTime) {
        this.dpSpecificCommonParameters = dpSpecificCommonParameters;
        this.callingPartyBusinessGroupID = callingPartyBusinessGroupID;
        this.callingPartySubaddress=callingPartySubaddress;
        
        if(callingFacilityGroup!=null)
        	this.callingFacilityGroup=new FacilityGroupWrapperImpl(callingFacilityGroup);
        
        if(callingFacilityGroupMember!=null) {
        	this.callingFacilityGroupMember=new ASNInteger();
        	this.callingFacilityGroupMember.setValue(callingFacilityGroupMember.longValue());
        }
        
        this.releaseCause=releaseCause;
        this.routeList=routeList;
        this.extensions = extensions;
        this.carrier=carrier;
        
        if(connectTime!=null) {
        	this.connectTime=new ASNInteger();
        	this.connectTime.setValue(connectTime.longValue());
        }
    }

    @Override
    public INAPMessageType getMessageType() {
        return INAPMessageType.oDisconnect_Request;
    }

    @Override
    public int getOperationCode() {
        return INAPOperationCode.oDisconnect;
    }

    @Override
    public DpSpecificCommonParameters getDpSpecificCommonParameters() {
		return dpSpecificCommonParameters;
	}

    @Override
    public CallingPartyBusinessGroupID getCallingPartyBusinessGroupID() {
		return callingPartyBusinessGroupID;
	}

    @Override
    public CallingPartySubaddress getCallingPartySubaddress() {
		return callingPartySubaddress;
	}

    @Override
    public FacilityGroup getCallingFacilityGroup() {
    	if(callingFacilityGroup==null)
    		return null;
    	
		return callingFacilityGroup.getFacilityGroup();
	}

    @Override
    public Integer getCallingFacilityGroupMember() {
    	if(callingFacilityGroupMember==null || callingFacilityGroupMember.getValue()==null)
    		return null;
    	
		return callingFacilityGroupMember.getValue().intValue();
	}

    @Override
    public CauseIsup getReleaseCause() {
    	return releaseCause;
	}

    @Override
    public RouteList getRouteList() {
		return routeList;
	}

    @Override
    public CAPINAPExtensions getExtensions() {
		return extensions;
	}

    @Override
    public Carrier getCarrier() {
		return carrier;
	}

    @Override
    public Integer getConnectTime() {
    	if(connectTime==null || connectTime.getValue()==null)
    		return null;
    	
		return connectTime.getValue().intValue();
	}

	@Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("ODisconnectRequestIndication [");
        this.addInvokeIdInfo(sb);

        if (this.dpSpecificCommonParameters != null) {
            sb.append(", dpSpecificCommonParameters=");
            sb.append(dpSpecificCommonParameters.toString());
        }
        if (this.callingPartyBusinessGroupID != null) {
            sb.append(", callingPartyBusinessGroupID=");
            sb.append(callingPartyBusinessGroupID.toString());
        }
        if (this.callingPartySubaddress != null) {
            sb.append(", callingPartySubaddress=");
            sb.append(callingPartySubaddress.toString());
        }
        if (this.callingFacilityGroup != null && this.callingFacilityGroup.getFacilityGroup()!=null) {
            sb.append(", callingFacilityGroup=");
            sb.append(callingFacilityGroup.getFacilityGroup().toString());
        }
        if (this.callingFacilityGroupMember != null && this.callingFacilityGroupMember.getValue()!=null) {
            sb.append(", callingFacilityGroupMember=");
            sb.append(callingFacilityGroupMember.getValue().toString());
        }
        if (this.releaseCause != null) {
            sb.append(", releaseCause=");
            sb.append(releaseCause.toString());
        }
        if (this.routeList != null) {
            sb.append(", routeList=");
            sb.append(routeList.toString());
        }
        if (this.extensions != null) {
            sb.append(", extensions=");
            sb.append(extensions.toString());
        }
        if (this.carrier != null) {
            sb.append(", carrier=");
            sb.append(carrier.toString());
        }
        if (this.connectTime != null && this.connectTime.getValue()!=null) {
            sb.append(", connectTime=");
            sb.append(connectTime.getValue().toString());
        }

        sb.append("]");

        return sb.toString();
    }
}
