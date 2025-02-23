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

package org.restcomm.protocols.ss7.map.service.mobility.subscriberManagement;

import java.util.List;

import org.restcomm.protocols.ss7.commonapp.api.primitives.IMSI;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MAPExtensionContainer;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.ExtBasicServiceCode;
import org.restcomm.protocols.ss7.commonapp.primitives.IMSIImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerImpl;
import org.restcomm.protocols.ss7.commonapp.subscriberManagement.ExtBasicServiceCodeListWrapperImpl;
import org.restcomm.protocols.ss7.map.api.MAPMessageType;
import org.restcomm.protocols.ss7.map.api.MAPOperationCode;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.DeleteSubscriberDataRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.EPSSubscriptionDataWithdraw;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.GPRSSubscriptionDataWithdraw;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.LSAInformationWithdraw;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.SpecificCSIWithdraw;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.ZoneCode;
import org.restcomm.protocols.ss7.map.api.service.supplementary.SSCode;
import org.restcomm.protocols.ss7.map.service.mobility.MobilityMessageImpl;
import org.restcomm.protocols.ss7.map.service.supplementary.SSCodeListWrapperImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNNull;

/**
*
* @author sergey vetyutnev
*
*/
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,lengthIndefinite=false)
public class DeleteSubscriberDataRequestImpl extends MobilityMessageImpl implements DeleteSubscriberDataRequest {
	private static final long serialVersionUID = 1L;

	@ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=0,constructed=false,index=0, defaultImplementation = IMSIImpl.class)
    private IMSI imsi;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=1,constructed=true,index=-1)
    private ExtBasicServiceCodeListWrapperImpl basicServiceList;    
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=2,constructed=true,index=-1)
    private SSCodeListWrapperImpl ssList;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=4,constructed=false,index=-1)
    private ASNNull roamingRestrictionDueToUnsupportedFeature;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=5,constructed=false,index=-1, defaultImplementation = ZoneCodeImpl.class)
    private ZoneCode regionalSubscriptionIdentifier;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=7,constructed=false,index=-1)
    private ASNNull vbsGroupIndication;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=8,constructed=false,index=-1)
    private ASNNull vgcsGroupIndication;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=9,constructed=false,index=-1)
    private ASNNull camelSubscriptionInfoWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=6,constructed=true,index=-1, defaultImplementation = MAPExtensionContainerImpl.class)
    private MAPExtensionContainer extensionContainer;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=10,constructed=true,index=-1)
    private GPRSSubscriptionDataWithdrawWrapperImpl gprsSubscriptionDataWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=11,constructed=false,index=-1)
    private ASNNull roamingRestrictedInSgsnDueToUnsuppportedFeature;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=12,constructed=true,index=-1)
    private LSAInformationWithdrawWrapperImpl lsaInformationWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=13,constructed=false,index=-1)
    private ASNNull gmlcListWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=14,constructed=false,index=-1)
    private ASNNull istInformationWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=15,constructed=false,index=-1,defaultImplementation = SpecificCSIWithdrawImpl.class)
    private SpecificCSIWithdraw specificCSIWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=16,constructed=false,index=-1)
    private ASNNull chargingCharacteristicsWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=17,constructed=false,index=-1)
    private ASNNull stnSrWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=18,constructed=true,index=-1)
    private EPSSubscriptionDataWithdrawWrapperImpl epsSubscriptionDataWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=19,constructed=false,index=-1)
    private ASNNull apnOiReplacementWithdraw;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=20,constructed=false,index=-1)
    private ASNNull csgSubscriptionDeleted;

    public DeleteSubscriberDataRequestImpl() {
    }

    public DeleteSubscriberDataRequestImpl(IMSI imsi, List<ExtBasicServiceCode> basicServiceList, List<SSCode> ssList,
            boolean roamingRestrictionDueToUnsupportedFeature, ZoneCode regionalSubscriptionIdentifier, boolean vbsGroupIndication,
            boolean vgcsGroupIndication, boolean camelSubscriptionInfoWithdraw, MAPExtensionContainer extensionContainer,
            GPRSSubscriptionDataWithdraw gprsSubscriptionDataWithdraw, boolean roamingRestrictedInSgsnDueToUnsuppportedFeature,
            LSAInformationWithdraw lsaInformationWithdraw, boolean gmlcListWithdraw, boolean istInformationWithdraw, SpecificCSIWithdraw specificCSIWithdraw,
            boolean chargingCharacteristicsWithdraw, boolean stnSrWithdraw, EPSSubscriptionDataWithdraw epsSubscriptionDataWithdraw,
            boolean apnOiReplacementWithdraw, boolean csgSubscriptionDeleted) {
        this.imsi = imsi;
        
        if(basicServiceList!=null)
        	this.basicServiceList = new ExtBasicServiceCodeListWrapperImpl(basicServiceList);
        
        if(ssList!=null)
        	this.ssList = new SSCodeListWrapperImpl(ssList);
        
        if(roamingRestrictionDueToUnsupportedFeature)
        	this.roamingRestrictionDueToUnsupportedFeature = new ASNNull();
        
        this.regionalSubscriptionIdentifier = regionalSubscriptionIdentifier;
        
        if(vbsGroupIndication)
        	this.vbsGroupIndication = new ASNNull();
        
        if(vgcsGroupIndication)
        	this.vgcsGroupIndication = new ASNNull();
        
        if(camelSubscriptionInfoWithdraw)
        	this.camelSubscriptionInfoWithdraw = new ASNNull();
        
        this.extensionContainer = extensionContainer;
        
        if(gprsSubscriptionDataWithdraw!=null)
        	this.gprsSubscriptionDataWithdraw = new GPRSSubscriptionDataWithdrawWrapperImpl(gprsSubscriptionDataWithdraw);
        
        if(roamingRestrictedInSgsnDueToUnsuppportedFeature)
        	this.roamingRestrictedInSgsnDueToUnsuppportedFeature = new ASNNull();
        
        if(lsaInformationWithdraw!=null)
        	this.lsaInformationWithdraw = new LSAInformationWithdrawWrapperImpl(lsaInformationWithdraw);
        
        if(gmlcListWithdraw)
        	this.gmlcListWithdraw = new ASNNull();
        
        if(istInformationWithdraw)
        	this.istInformationWithdraw = new ASNNull();
        
        this.specificCSIWithdraw = specificCSIWithdraw;
        
        if(chargingCharacteristicsWithdraw)
        	this.chargingCharacteristicsWithdraw = new ASNNull();
        
        if(stnSrWithdraw)
        	this.stnSrWithdraw = new ASNNull();
        
        if(epsSubscriptionDataWithdraw!=null)
        	this.epsSubscriptionDataWithdraw = new EPSSubscriptionDataWithdrawWrapperImpl(epsSubscriptionDataWithdraw);
        
        if(apnOiReplacementWithdraw)
        	this.apnOiReplacementWithdraw = new ASNNull();
        
        if(csgSubscriptionDeleted)
        	this.csgSubscriptionDeleted = new ASNNull();
    }

    public MAPMessageType getMessageType() {
        return MAPMessageType.deleteSubscriberData_Request;
    }

    public int getOperationCode() {
        return MAPOperationCode.deleteSubscriberData;
    }

    @Override
    public IMSI getImsi() {
        return imsi;
    }

    @Override
    public List<ExtBasicServiceCode> getBasicServiceList() {
    	if(basicServiceList==null)
    		return null;
    	
        return basicServiceList.getExtBasicServiceCode();
    }

    @Override
    public List<SSCode> getSsList() {
    	if(ssList==null)
    		return null;
    	
        return ssList.getSSCode();
    }

    @Override
    public boolean getRoamingRestrictionDueToUnsupportedFeature() {
        return roamingRestrictionDueToUnsupportedFeature!=null;
    }

    @Override
    public ZoneCode getRegionalSubscriptionIdentifier() {
        return regionalSubscriptionIdentifier;
    }

    @Override
    public boolean getVbsGroupIndication() {
        return vbsGroupIndication!=null;
    }

    @Override
    public boolean getVgcsGroupIndication() {
        return vgcsGroupIndication!=null;
    }

    @Override
    public boolean getCamelSubscriptionInfoWithdraw() {
        return camelSubscriptionInfoWithdraw!=null;
    }

    @Override
    public MAPExtensionContainer getExtensionContainer() {
        return extensionContainer;
    }

    @Override
    public GPRSSubscriptionDataWithdraw getGPRSSubscriptionDataWithdraw() {
    	if(gprsSubscriptionDataWithdraw==null)
    		return null;
    	
        return gprsSubscriptionDataWithdraw.getGPRSSubscriptionDataWithdraw();
    }

    @Override
    public boolean getRoamingRestrictedInSgsnDueToUnsuppportedFeature() {
        return roamingRestrictedInSgsnDueToUnsuppportedFeature!=null;
    }

    @Override
    public LSAInformationWithdraw getLSAInformationWithdraw() {
    	if(lsaInformationWithdraw==null)
    		return null;
    	
        return lsaInformationWithdraw.getLSAInformationWithdraw();
    }

    @Override
    public boolean getGmlcListWithdraw() {
        return gmlcListWithdraw!=null;
    }

    @Override
    public boolean getIstInformationWithdraw() {
        return istInformationWithdraw!=null;
    }

    @Override
    public SpecificCSIWithdraw getSpecificCSIWithdraw() {
        return specificCSIWithdraw;
    }

    @Override
    public boolean getChargingCharacteristicsWithdraw() {
        return chargingCharacteristicsWithdraw!=null;
    }

    @Override
    public boolean getStnSrWithdraw() {
        return stnSrWithdraw!=null;
    }

    @Override
    public EPSSubscriptionDataWithdraw getEPSSubscriptionDataWithdraw() {
    	if(epsSubscriptionDataWithdraw==null)
    		return null;
    	
        return epsSubscriptionDataWithdraw.getEPSSubscriptionDataWithdraw();
    }

    @Override
    public boolean getApnOiReplacementWithdraw() {
        return apnOiReplacementWithdraw!=null;
    }

    @Override
    public boolean getCsgSubscriptionDeleted() {
        return csgSubscriptionDeleted!=null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DeleteSubscriberDataRequest [");

        if (this.imsi != null) {
            sb.append("imsi=");
            sb.append(imsi.toString());
            sb.append(", ");
        }
        if (this.basicServiceList != null && this.basicServiceList.getExtBasicServiceCode()!=null) {
            sb.append("basicServiceList=[");
            boolean firstItem = true;
            for (ExtBasicServiceCode be : this.basicServiceList.getExtBasicServiceCode()) {
                if (firstItem)
                    firstItem = false;
                else
                    sb.append(", ");
                sb.append(be.toString());
            }
            sb.append("], ");
        }
        if (this.ssList != null && this.ssList.getSSCode()!=null) {
            sb.append("ssList=[");
            boolean firstItem = true;
            for (SSCode be : this.ssList.getSSCode()) {
                if (firstItem)
                    firstItem = false;
                else
                    sb.append(", ");
                sb.append(be.toString());
            }
            sb.append("], ");
        }
        if (this.roamingRestrictionDueToUnsupportedFeature!=null) {
            sb.append("roamingRestrictionDueToUnsupportedFeature, ");
        }
        if (this.regionalSubscriptionIdentifier != null) {
            sb.append("regionalSubscriptionIdentifier=");
            sb.append(regionalSubscriptionIdentifier);
            sb.append(", ");
        }
        if (this.vbsGroupIndication!=null) {
            sb.append("vbsGroupIndication, ");
        }
        if (this.vgcsGroupIndication!=null) {
            sb.append("vgcsGroupIndication, ");
        }
        if (this.camelSubscriptionInfoWithdraw!=null) {
            sb.append("camelSubscriptionInfoWithdraw, ");
        }
        if (this.extensionContainer != null) {
            sb.append("extensionContainer=");
            sb.append(extensionContainer);
            sb.append(", ");
        }
        if (this.gprsSubscriptionDataWithdraw != null && this.gprsSubscriptionDataWithdraw.getGPRSSubscriptionDataWithdraw()!=null) {
            sb.append("gprsSubscriptionDataWithdraw=");
            sb.append(gprsSubscriptionDataWithdraw.getGPRSSubscriptionDataWithdraw());
            sb.append(", ");
        }
        if (this.roamingRestrictedInSgsnDueToUnsuppportedFeature!=null) {
            sb.append("roamingRestrictedInSgsnDueToUnsuppportedFeature, ");
        }
        if (this.lsaInformationWithdraw != null && this.lsaInformationWithdraw.getLSAInformationWithdraw()!=null) {
            sb.append("lsaInformationWithdraw=");
            sb.append(lsaInformationWithdraw.getLSAInformationWithdraw());
            sb.append(", ");
        }
        if (this.gmlcListWithdraw!=null) {
            sb.append("gmlcListWithdraw, ");
        }
        if (this.istInformationWithdraw!=null) {
            sb.append("istInformationWithdraw, ");
        }
        if (this.specificCSIWithdraw != null) {
            sb.append("specificCSIWithdraw=");
            sb.append(specificCSIWithdraw);
            sb.append(", ");
        }
        if (this.chargingCharacteristicsWithdraw!=null) {
            sb.append("chargingCharacteristicsWithdraw, ");
        }
        if (this.stnSrWithdraw!=null) {
            sb.append("stnSrWithdraw, ");
        }
        if (this.epsSubscriptionDataWithdraw != null && this.epsSubscriptionDataWithdraw.getEPSSubscriptionDataWithdraw()!=null) {
            sb.append("epsSubscriptionDataWithdraw=");
            sb.append(epsSubscriptionDataWithdraw.getEPSSubscriptionDataWithdraw());
            sb.append(", ");
        }
        if (this.apnOiReplacementWithdraw!=null) {
            sb.append("apnOiReplacementWithdraw, ");
        }
        if (this.csgSubscriptionDeleted!=null) {
            sb.append("csgSubscriptionDeleted, ");
        }

        sb.append("]");

        return sb.toString();
    }
}