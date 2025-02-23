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

package org.restcomm.protocols.ss7.map.service.callhandling;

import java.util.List;

import org.restcomm.protocols.ss7.commonapp.api.callhandling.CallReferenceNumber;
import org.restcomm.protocols.ss7.commonapp.api.primitives.AddressString;
import org.restcomm.protocols.ss7.commonapp.api.primitives.AlertingPattern;
import org.restcomm.protocols.ss7.commonapp.api.primitives.IMSI;
import org.restcomm.protocols.ss7.commonapp.api.primitives.ISDNAddressString;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MAPExtensionContainer;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.ExtBasicServiceCode;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.SupportedCamelPhases;
import org.restcomm.protocols.ss7.map.MAPDialogImpl;
import org.restcomm.protocols.ss7.map.MAPProviderImpl;
import org.restcomm.protocols.ss7.map.api.MAPApplicationContext;
import org.restcomm.protocols.ss7.map.api.MAPApplicationContextName;
import org.restcomm.protocols.ss7.map.api.MAPApplicationContextVersion;
import org.restcomm.protocols.ss7.map.api.MAPException;
import org.restcomm.protocols.ss7.map.api.MAPOperationCode;
import org.restcomm.protocols.ss7.map.api.MAPServiceBase;
import org.restcomm.protocols.ss7.map.api.primitives.EMLPPPriority;
import org.restcomm.protocols.ss7.map.api.primitives.ExtExternalSignalInfo;
import org.restcomm.protocols.ss7.map.api.primitives.ExternalSignalInfo;
import org.restcomm.protocols.ss7.map.api.primitives.LMSI;
import org.restcomm.protocols.ss7.map.api.primitives.NAEAPreferredCI;
import org.restcomm.protocols.ss7.map.api.service.callhandling.AllowedServices;
import org.restcomm.protocols.ss7.map.api.service.callhandling.CCBSIndicators;
import org.restcomm.protocols.ss7.map.api.service.callhandling.CUGCheckInfo;
import org.restcomm.protocols.ss7.map.api.service.callhandling.CallDiversionTreatmentIndicator;
import org.restcomm.protocols.ss7.map.api.service.callhandling.CamelInfo;
import org.restcomm.protocols.ss7.map.api.service.callhandling.ExtendedRoutingInfo;
import org.restcomm.protocols.ss7.map.api.service.callhandling.InterrogationType;
import org.restcomm.protocols.ss7.map.api.service.callhandling.MAPDialogCallHandling;
import org.restcomm.protocols.ss7.map.api.service.callhandling.RoutingInfo;
import org.restcomm.protocols.ss7.map.api.service.callhandling.SendRoutingInformationResponse;
import org.restcomm.protocols.ss7.map.api.service.callhandling.SuppressMTSS;
import org.restcomm.protocols.ss7.map.api.service.callhandling.UnavailabilityCause;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.ISTSupportIndicator;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.PagingArea;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.NumberPortabilityStatus;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.SubscriberInfo;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.OfferedCamel4CSIs;
import org.restcomm.protocols.ss7.map.api.service.supplementary.ForwardingReason;
import org.restcomm.protocols.ss7.map.api.service.supplementary.SSCode;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.Dialog;

/*
 *
 * @author cristian veliscu
 * @author eva ogallar
 *
 */
public class MAPDialogCallHandlingImpl extends MAPDialogImpl implements MAPDialogCallHandling {
	private static final long serialVersionUID = 1L;

	// Include these constants in MAPApplicationContextName and MAPOperationCode
    // sendRoutingInfo_Request: add constant to MAPMessageType
    // sendRoutingInfo_Response: add constant to MAPMessageType
    protected static final int locationInfoRetrievalContext = 5;
    protected static final int sendRoutingInfo = 22;
    protected static final int version = 3;

    protected MAPDialogCallHandlingImpl(MAPApplicationContext appCntx, Dialog tcapDialog, MAPProviderImpl mapProviderImpl,
            MAPServiceBase mapService, AddressString origReference, AddressString destReference) {
        super(appCntx, tcapDialog, mapProviderImpl, mapService, origReference, destReference);
    }

    public Long addSendRoutingInformationRequest(ISDNAddressString msisdn, CUGCheckInfo cugCheckInfo,
            Integer numberOfForwarding, ExternalSignalInfo networkSignalInfo) throws MAPException {
        return this.addSendRoutingInformationRequest(_Timer_Default, msisdn, cugCheckInfo, numberOfForwarding, null, false,
                null, null, null, null, null, networkSignalInfo, null, false, null, null, false, null, null, null, false, null,
                false, false, false, false, null, null, null, false, null);
    }

    public Long addSendRoutingInformationRequest(int customInvokeTimeout, ISDNAddressString msisdn, CUGCheckInfo cugCheckInfo,
            Integer numberOfForwarding, ExternalSignalInfo networkSignalInfo) throws MAPException {

        return this.addSendRoutingInformationRequest(customInvokeTimeout, msisdn, cugCheckInfo, numberOfForwarding, null,
                false, null, null, null, null, null, networkSignalInfo, null, false, null, null, false, null, null, null,
                false, null, false, false, false, false, null, null, null, false, null);

    }

    @Override
    public Long addSendRoutingInformationRequest(ISDNAddressString msisdn, CUGCheckInfo cugCheckInfo,
            Integer numberOfForwarding, InterrogationType interrogationType, boolean orInterrogation, Integer orCapability,
            ISDNAddressString gmscAddress, CallReferenceNumber callReferenceNumber, ForwardingReason forwardingReason,
            ExtBasicServiceCode basicServiceGroup, ExternalSignalInfo networkSignalInfo, CamelInfo camelInfo,
            boolean suppressionOfAnnouncement, MAPExtensionContainer extensionContainer, AlertingPattern alertingPattern,
            boolean ccbsCall, Integer supportedCCBSPhase, ExtExternalSignalInfo additionalSignalInfo,
            ISTSupportIndicator istSupportIndicator, boolean prePagingSupported,
            CallDiversionTreatmentIndicator callDiversionTreatmentIndicator, boolean longFTNSupported, boolean suppressVtCSI,
            boolean suppressIncomingCallBarring, boolean gsmSCFInitiatedCall, ExtBasicServiceCode basicServiceGroup2,
            ExternalSignalInfo networkSignalInfo2, SuppressMTSS supressMTSS, boolean mtRoamingRetrySupported,
            EMLPPPriority callPriority) throws MAPException {

        return this
                .addSendRoutingInformationRequest(_Timer_Default, msisdn, cugCheckInfo, numberOfForwarding, interrogationType,
                        orInterrogation, orCapability, gmscAddress, callReferenceNumber, forwardingReason, basicServiceGroup,
                        networkSignalInfo, camelInfo, suppressionOfAnnouncement, extensionContainer, alertingPattern, ccbsCall,
                        supportedCCBSPhase, additionalSignalInfo, istSupportIndicator, prePagingSupported,
                        callDiversionTreatmentIndicator, longFTNSupported, suppressVtCSI, suppressIncomingCallBarring,
                        gsmSCFInitiatedCall, basicServiceGroup2, networkSignalInfo2, supressMTSS, mtRoamingRetrySupported,
                        callPriority);
    }

    @Override
    public Long addSendRoutingInformationRequest(int customInvokeTimeout, ISDNAddressString msisdn, CUGCheckInfo cugCheckInfo,
            Integer numberOfForwarding, InterrogationType interrogationType, boolean orInterrogation, Integer orCapability,
            ISDNAddressString gmscAddress, CallReferenceNumber callReferenceNumber, ForwardingReason forwardingReason,
            ExtBasicServiceCode basicServiceGroup, ExternalSignalInfo networkSignalInfo, CamelInfo camelInfo,
            boolean suppressionOfAnnouncement, MAPExtensionContainer extensionContainer, AlertingPattern alertingPattern,
            boolean ccbsCall, Integer supportedCCBSPhase, ExtExternalSignalInfo additionalSignalInfo,
            ISTSupportIndicator istSupportIndicator, boolean prePagingSupported,
            CallDiversionTreatmentIndicator callDiversionTreatmentIndicator, boolean longFTNSupported, boolean suppressVtCSI,
            boolean suppressIncomingCallBarring, boolean gsmSCFInitiatedCall, ExtBasicServiceCode basicServiceGroup2,
            ExternalSignalInfo networkSignalInfo2, SuppressMTSS supressMTSS, boolean mtRoamingRetrySupported,
            EMLPPPriority callPriority) throws MAPException {

        MAPApplicationContextVersion vers = this.appCntx.getApplicationContextVersion();
        if ((this.appCntx.getApplicationContextName() != MAPApplicationContextName.locationInfoRetrievalContext)
                || (vers != MAPApplicationContextVersion.version1 && vers != MAPApplicationContextVersion.version2 && vers != MAPApplicationContextVersion.version3))
            throw new MAPException(
                    "Bad application context name for addSendRoutingInformationRequest: must be locationInfoRetrievalContext_V1, V2 or V3");

        Integer timeout=null;
        if (customInvokeTimeout == _Timer_Default)
        	timeout=getMediumTimer();
        else
        	timeout=customInvokeTimeout;

        SendRoutingInformationRequestImpl req = new SendRoutingInformationRequestImpl(this.appCntx
                .getApplicationContextVersion().getVersion(), msisdn, cugCheckInfo, numberOfForwarding, interrogationType,
                orInterrogation, orCapability, gmscAddress, callReferenceNumber, forwardingReason, basicServiceGroup,
                networkSignalInfo, camelInfo, suppressionOfAnnouncement, extensionContainer, alertingPattern, ccbsCall,
                supportedCCBSPhase, additionalSignalInfo, istSupportIndicator, prePagingSupported,
                callDiversionTreatmentIndicator, longFTNSupported, suppressVtCSI, suppressIncomingCallBarring,
                gsmSCFInitiatedCall, basicServiceGroup2, networkSignalInfo2, supressMTSS, mtRoamingRetrySupported,
                callPriority);
        
        return this.sendDataComponent(null, null, null, timeout.longValue(), (long)MAPOperationCode.sendRoutingInfo,req, true, false);        
    }

    public void addSendRoutingInformationResponse(long invokeId, IMSI imsi, CUGCheckInfo cugCheckInfo, RoutingInfo routingInfo2)
            throws MAPException {
    	this.doAddSendRoutingInformationResponse(invokeId, imsi, cugCheckInfo, routingInfo2);
    }
    
    protected void doAddSendRoutingInformationResponse(long invokeId, IMSI imsi, CUGCheckInfo cugCheckInfo, RoutingInfo routingInfo2) throws MAPException {

        MAPApplicationContextVersion vers = this.appCntx.getApplicationContextVersion();
        if ((this.appCntx.getApplicationContextName() != MAPApplicationContextName.locationInfoRetrievalContext)
                || (vers != MAPApplicationContextVersion.version1 && vers != MAPApplicationContextVersion.version2))
            throw new MAPException(
                    "Bad application context name for addSendRoutingInformationResponse: must be locationInfoRetrievalContext_V1 or V2");
        
        SendRoutingInformationResponse res;
        res = new SendRoutingInformationResponseImplV1(this.appCntx.getApplicationContextVersion().getVersion(),imsi,routingInfo2,cugCheckInfo);
        
        this.sendDataComponent(invokeId, null, null, null, (long)MAPOperationCode.sendRoutingInfo, res, false, true);        
    }

    @Override
    public void addSendRoutingInformationResponse(long invokeId, IMSI imsi, ExtendedRoutingInfo extRoutingInfo,
            CUGCheckInfo cugCheckInfo, boolean cugSubscriptionFlag, SubscriberInfo subscriberInfo, List<SSCode> ssList,
            ExtBasicServiceCode basicService, boolean forwardingInterrogationRequired, ISDNAddressString vmscAddress,
            MAPExtensionContainer extensionContainer, NAEAPreferredCI naeaPreferredCI, CCBSIndicators ccbsIndicators,
            ISDNAddressString msisdn, NumberPortabilityStatus nrPortabilityStatus, Integer istAlertTimer,
            SupportedCamelPhases supportedCamelPhases, OfferedCamel4CSIs offeredCamel4CSIs, RoutingInfo routingInfo2,
            List<SSCode> ssList2, ExtBasicServiceCode basicService2, AllowedServices allowedServices,
            UnavailabilityCause unavailabilityCause, boolean releaseResourcesSupported, ExternalSignalInfo gsmBearerCapability)
            throws MAPException {
        doAddSendRoutingInformationResponse(false, invokeId, imsi, extRoutingInfo, cugCheckInfo, cugSubscriptionFlag,
                subscriberInfo, ssList, basicService, forwardingInterrogationRequired, vmscAddress, extensionContainer,
                naeaPreferredCI, ccbsIndicators, msisdn, nrPortabilityStatus, istAlertTimer, supportedCamelPhases,
                offeredCamel4CSIs, routingInfo2, ssList2, basicService2, allowedServices, unavailabilityCause,
                releaseResourcesSupported, gsmBearerCapability);
    }

    @Override
    public void addSendRoutingInformationResponse_NonLast(long invokeId, IMSI imsi, ExtendedRoutingInfo extRoutingInfo,
            CUGCheckInfo cugCheckInfo, boolean cugSubscriptionFlag, SubscriberInfo subscriberInfo, List<SSCode> ssList,
            ExtBasicServiceCode basicService, boolean forwardingInterrogationRequired, ISDNAddressString vmscAddress,
            MAPExtensionContainer extensionContainer, NAEAPreferredCI naeaPreferredCI, CCBSIndicators ccbsIndicators,
            ISDNAddressString msisdn, NumberPortabilityStatus nrPortabilityStatus, Integer istAlertTimer,
            SupportedCamelPhases supportedCamelPhases, OfferedCamel4CSIs offeredCamel4CSIs, RoutingInfo routingInfo2,
            List<SSCode> ssList2, ExtBasicServiceCode basicService2, AllowedServices allowedServices,
            UnavailabilityCause unavailabilityCause, boolean releaseResourcesSupported, ExternalSignalInfo gsmBearerCapability)
            throws MAPException {
        doAddSendRoutingInformationResponse(true, invokeId, imsi, extRoutingInfo, cugCheckInfo, cugSubscriptionFlag,
                subscriberInfo, ssList, basicService, forwardingInterrogationRequired, vmscAddress, extensionContainer,
                naeaPreferredCI, ccbsIndicators, msisdn, nrPortabilityStatus, istAlertTimer, supportedCamelPhases,
                offeredCamel4CSIs, routingInfo2, ssList2, basicService2, allowedServices, unavailabilityCause,
                releaseResourcesSupported, gsmBearerCapability);
    }

    protected void doAddSendRoutingInformationResponse(boolean nonLast, long invokeId, IMSI imsi,
            ExtendedRoutingInfo extRoutingInfo, CUGCheckInfo cugCheckInfo, boolean cugSubscriptionFlag,
            SubscriberInfo subscriberInfo, List<SSCode> ssList, ExtBasicServiceCode basicService,
            boolean forwardingInterrogationRequired, ISDNAddressString vmscAddress, MAPExtensionContainer extensionContainer,
            NAEAPreferredCI naeaPreferredCI, CCBSIndicators ccbsIndicators, ISDNAddressString msisdn,
            NumberPortabilityStatus nrPortabilityStatus, Integer istAlertTimer, SupportedCamelPhases supportedCamelPhases,
            OfferedCamel4CSIs offeredCamel4CSIs, RoutingInfo routingInfo2, List<SSCode> ssList2,
            ExtBasicServiceCode basicService2, AllowedServices allowedServices, UnavailabilityCause unavailabilityCause,
            boolean releaseResourcesSupported, ExternalSignalInfo gsmBearerCapability) throws MAPException {

        MAPApplicationContextVersion vers = this.appCntx.getApplicationContextVersion();
        if ((this.appCntx.getApplicationContextName() != MAPApplicationContextName.locationInfoRetrievalContext)
                || vers != MAPApplicationContextVersion.version3)
            throw new MAPException(
                    "Bad application context name for addSendRoutingInformationResponse: must be locationInfoRetrievalContext_V3");
        
        SendRoutingInformationResponse res;
        res = new SendRoutingInformationResponseImplV3(this.appCntx
                .getApplicationContextVersion().getVersion(), imsi, extRoutingInfo, cugCheckInfo, cugSubscriptionFlag,
                subscriberInfo, ssList, basicService, forwardingInterrogationRequired, vmscAddress, extensionContainer,
                naeaPreferredCI, ccbsIndicators, msisdn, nrPortabilityStatus, istAlertTimer, supportedCamelPhases,
                offeredCamel4CSIs, routingInfo2, ssList2, basicService2, allowedServices, unavailabilityCause,
                releaseResourcesSupported, gsmBearerCapability);
        
        this.sendDataComponent(invokeId, null, null, null, (long)MAPOperationCode.sendRoutingInfo, res, false, !nonLast);        
    }

    @Override
    public Long addProvideRoamingNumberRequest(IMSI imsi, ISDNAddressString mscNumber, ISDNAddressString msisdn, LMSI lmsi,
            ExternalSignalInfo gsmBearerCapability, ExternalSignalInfo networkSignalInfo, boolean suppressionOfAnnouncement,
            ISDNAddressString gmscAddress, CallReferenceNumber callReferenceNumber, boolean orInterrogation,
            MAPExtensionContainer extensionContainer, AlertingPattern alertingPattern, boolean ccbsCall,
            SupportedCamelPhases supportedCamelPhasesInInterrogatingNode, ExtExternalSignalInfo additionalSignalInfo,
            boolean orNotSupportedInGMSC, boolean prePagingSupported, boolean longFTNSupported, boolean suppressVtCsi,
            OfferedCamel4CSIs offeredCamel4CSIsInInterrogatingNode, boolean mtRoamingRetrySupported, PagingArea pagingArea,
            EMLPPPriority callPriority, boolean mtrfIndicator, ISDNAddressString oldMSCNumber) throws MAPException {
        return this.addProvideRoamingNumberRequest(_Timer_Default, imsi, mscNumber, msisdn, lmsi, gsmBearerCapability,
                networkSignalInfo, suppressionOfAnnouncement, gmscAddress, callReferenceNumber, orInterrogation,
                extensionContainer, alertingPattern, ccbsCall, supportedCamelPhasesInInterrogatingNode, additionalSignalInfo,
                orNotSupportedInGMSC, prePagingSupported, longFTNSupported, suppressVtCsi,
                offeredCamel4CSIsInInterrogatingNode, mtRoamingRetrySupported, pagingArea, callPriority, mtrfIndicator,
                oldMSCNumber);
    }

    @Override
    public Long addProvideRoamingNumberRequest(int customInvokeTimeout, IMSI imsi, ISDNAddressString mscNumber,
    		ISDNAddressString msisdn, LMSI lmsi, ExternalSignalInfo gsmBearerCapability, ExternalSignalInfo networkSignalInfo,
            boolean suppressionOfAnnouncement, ISDNAddressString gmscAddress, CallReferenceNumber callReferenceNumber,
            boolean orInterrogation, MAPExtensionContainer extensionContainer, AlertingPattern alertingPattern,
            boolean ccbsCall, SupportedCamelPhases supportedCamelPhasesInInterrogatingNode,
            ExtExternalSignalInfo additionalSignalInfo, boolean orNotSupportedInGMSC, boolean prePagingSupported,
            boolean longFTNSupported, boolean suppressVtCsi, OfferedCamel4CSIs offeredCamel4CSIsInInterrogatingNode,
            boolean mtRoamingRetrySupported, PagingArea pagingArea, EMLPPPriority callPriority, boolean mtrfIndicator,
            ISDNAddressString oldMSCNumber) throws MAPException {

        MAPApplicationContextVersion vers = this.appCntx.getApplicationContextVersion();
        if ((this.appCntx.getApplicationContextName() != MAPApplicationContextName.roamingNumberEnquiryContext)
                || (vers != MAPApplicationContextVersion.version1 && vers != MAPApplicationContextVersion.version2 && vers != MAPApplicationContextVersion.version3))
            throw new MAPException(
                    "Bad application context name for addProvideRoamingNumberRequest: must be roamingNumberEnquiryContext _V1, V2 or V3");

        Integer customTimeout=null;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout=getMediumTimer();
        else
        	customTimeout=customInvokeTimeout;

        
        ProvideRoamingNumberRequestImpl req = new ProvideRoamingNumberRequestImpl(imsi, mscNumber, msisdn, lmsi,
                gsmBearerCapability, networkSignalInfo, suppressionOfAnnouncement, gmscAddress, callReferenceNumber,
                orInterrogation, extensionContainer, alertingPattern, ccbsCall, supportedCamelPhasesInInterrogatingNode,
                additionalSignalInfo, orNotSupportedInGMSC, prePagingSupported, longFTNSupported, suppressVtCsi,
                offeredCamel4CSIsInInterrogatingNode, mtRoamingRetrySupported, pagingArea, callPriority, mtrfIndicator,
                oldMSCNumber, this.appCntx.getApplicationContextVersion().getVersion());
        
        return this.sendDataComponent(null, null, null, customTimeout.longValue(), (long)MAPOperationCode.provideRoamingNumber, req, true, false);
    }

    @Override
    public void addProvideRoamingNumberResponse(long invokeId, ISDNAddressString roamingNumber)
            throws MAPException {

        MAPApplicationContextVersion vers = this.appCntx.getApplicationContextVersion();
        if ((this.appCntx.getApplicationContextName() != MAPApplicationContextName.roamingNumberEnquiryContext)
                || (vers != MAPApplicationContextVersion.version1 && vers != MAPApplicationContextVersion.version2))
            throw new MAPException(
                    "Bad application context name for addProvideRoamingNumberResponse: must be roamingNumberEnquiryContext_V1 or V2");

        ProvideRoamingNumberResponseImplV1 res = new ProvideRoamingNumberResponseImplV1(roamingNumber, this.appCntx.getApplicationContextVersion().getVersion());
        this.sendDataComponent(invokeId, null, null, null, (long)MAPOperationCode.provideRoamingNumber, res, false, true);
    }
    
    @Override
    public void addProvideRoamingNumberResponse(long invokeId, ISDNAddressString roamingNumber,
    		MAPExtensionContainer extensionContainer, boolean releaseResourcesSupported, ISDNAddressString vmscAddress)
            throws MAPException {

        MAPApplicationContextVersion vers = this.appCntx.getApplicationContextVersion();
        if ((this.appCntx.getApplicationContextName() != MAPApplicationContextName.roamingNumberEnquiryContext)
                || vers != MAPApplicationContextVersion.version3)
            throw new MAPException(
                    "Bad application context name for addProvideRoamingNumberResponse: must be roamingNumberEnquiryContext_V3");

        ProvideRoamingNumberResponseImplV3 res = new ProvideRoamingNumberResponseImplV3(roamingNumber, extensionContainer,
                releaseResourcesSupported, vmscAddress, this.appCntx.getApplicationContextVersion().getVersion());
        this.sendDataComponent(invokeId, null, null, null, (long)MAPOperationCode.provideRoamingNumber, res, false, true);
    }

    @Override
    public Long addIstCommandRequest(IMSI imsi, MAPExtensionContainer extensionContainer) throws MAPException {
        return this.addIstCommandRequest(_Timer_Default, imsi, extensionContainer);
    }

    @Override
    public Long addIstCommandRequest(int customInvokeTimeout, IMSI imsi, MAPExtensionContainer extensionContainer) throws MAPException {
        MAPApplicationContextVersion vers = this.appCntx.getApplicationContextVersion();
        if ((this.appCntx.getApplicationContextName() != MAPApplicationContextName.ServiceTerminationContext)
                || (vers != MAPApplicationContextVersion.version3))
            throw new MAPException(
                    "Bad application context name for addIstCommandRequest: must be ServiceTerminationContext_V3");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout=getMediumTimer();
        else
        	customTimeout=customInvokeTimeout;

        IstCommandRequestImpl req = new IstCommandRequestImpl(imsi, extensionContainer);
        return this.sendDataComponent(null, null, null, customTimeout.longValue(), (long)MAPOperationCode.istCommand, req, true, false);
    }

    @Override
    public void addIstCommandResponse(long invokeId, MAPExtensionContainer extensionContainer) throws MAPException {

        MAPApplicationContextVersion vers = this.appCntx.getApplicationContextVersion();
        if ((this.appCntx.getApplicationContextName() != MAPApplicationContextName.ServiceTerminationContext)
                || (vers != MAPApplicationContextVersion.version3))
            throw new MAPException(
                    "Bad application context name for addIstCommandResponse: must be ServiceTerminationContext_V3");

        IstCommandResponseImpl res=null;
        if (extensionContainer!=null)
            res = new IstCommandResponseImpl(extensionContainer);
            
        this.sendDataComponent(invokeId, null, null, null, (long)MAPOperationCode.istCommand, res, false, true);
    }
}