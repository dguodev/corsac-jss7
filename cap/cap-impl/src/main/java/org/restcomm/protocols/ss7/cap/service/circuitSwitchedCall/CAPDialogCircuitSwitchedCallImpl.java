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

package org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall;

import java.util.List;

import org.restcomm.protocols.ss7.cap.CAPDialogImpl;
import org.restcomm.protocols.ss7.cap.CAPProviderImpl;
import org.restcomm.protocols.ss7.cap.api.CAPApplicationContext;
import org.restcomm.protocols.ss7.cap.api.CAPException;
import org.restcomm.protocols.ss7.cap.api.CAPOperationCode;
import org.restcomm.protocols.ss7.cap.api.CAPServiceBase;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.CAPDialogCircuitSwitchedCall;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.CancelRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.SpecializedResourceReportRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CAMELAChBillingChargingCharacteristics;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.EventSpecificInformationBCSM;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.InitialDPArgExtension;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.RequestedInformation;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.SCIBillingChargingCharacteristics;
import org.restcomm.protocols.ss7.commonapp.api.callhandling.CallReferenceNumber;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.BearerCapability;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CGEncountered;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CallSegmentToCancel;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CalledPartyBCDNumber;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.Carrier;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CollectedInfo;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ContinueWithArgumentArgExtension;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ControlType;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.DestinationRoutingAddress;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.FCIBCCCAMELSequence1;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.IPSSPCapabilities;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.InformationToSend;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.NAOliInfo;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.RequestedInformationType;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ServiceInteractionIndicatorsTwo;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.TimeDurationChargingResult;
import org.restcomm.protocols.ss7.commonapp.api.gap.GapCriteria;
import org.restcomm.protocols.ss7.commonapp.api.gap.GapIndicators;
import org.restcomm.protocols.ss7.commonapp.api.gap.GapTreatment;
import org.restcomm.protocols.ss7.commonapp.api.isup.CalledPartyNumberIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.CallingPartyNumberIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.CallingPartysCategoryIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.CauseIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.DigitsIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.GenericNumberIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.HighLayerCompatibilityIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.LocationNumberIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.OriginalCalledNumberIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.RedirectingPartyIDIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.RedirectionInformationIsup;
import org.restcomm.protocols.ss7.commonapp.api.primitives.AChChargingAddress;
import org.restcomm.protocols.ss7.commonapp.api.primitives.AlertingPattern;
import org.restcomm.protocols.ss7.commonapp.api.primitives.BCSMEvent;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CAPINAPExtensions;
import org.restcomm.protocols.ss7.commonapp.api.primitives.EventTypeBCSM;
import org.restcomm.protocols.ss7.commonapp.api.primitives.IMSI;
import org.restcomm.protocols.ss7.commonapp.api.primitives.ISDNAddressString;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LegID;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LegType;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MiscCallInfo;
import org.restcomm.protocols.ss7.commonapp.api.primitives.ScfID;
import org.restcomm.protocols.ss7.commonapp.api.primitives.TimeAndTimezone;
import org.restcomm.protocols.ss7.commonapp.api.primitives.TimerID;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.LocationInformation;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.SubscriberState;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.CUGIndex;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.CUGInterlock;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.ExtBasicServiceCode;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.OfferedCamel4Functionalities;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.SupportedCamelPhases;
import org.restcomm.protocols.ss7.tcap.api.tc.component.InvokeClass;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.Dialog;

/**
 *
 * @author sergey vetyutnev
 * @author alerant appngin
 * @author <a href="mailto:bartosz.krok@pro-ids.com"> Bartosz Krok (ProIDS sp. z o.o.)</a>
 *
 */
public class CAPDialogCircuitSwitchedCallImpl extends CAPDialogImpl implements CAPDialogCircuitSwitchedCall {
	private static final long serialVersionUID = 1L;

	protected CAPDialogCircuitSwitchedCallImpl(CAPApplicationContext appCntx, Dialog tcapDialog,
            CAPProviderImpl capProviderImpl, CAPServiceBase capService) {
        super(appCntx, tcapDialog, capProviderImpl, capService);
    }

    @Override
    public Long addInitialDPRequest(int serviceKey, CalledPartyNumberIsup calledPartyNumber,
            CallingPartyNumberIsup callingPartyNumber, CallingPartysCategoryIsup callingPartysCategory,
            CGEncountered CGEncountered, IPSSPCapabilities IPSSPCapabilities, LocationNumberIsup locationNumber,
            OriginalCalledNumberIsup originalCalledPartyID, CAPINAPExtensions extensions,
            HighLayerCompatibilityIsup highLayerCompatibility, DigitsIsup additionalCallingPartyNumber,
            BearerCapability bearerCapability, EventTypeBCSM eventTypeBCSM, RedirectingPartyIDIsup redirectingPartyID,
            RedirectionInformationIsup redirectionInformation, CauseIsup cause,
            ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo, Carrier carrier, CUGIndex cugIndex,
            CUGInterlock cugInterlock, boolean cugOutgoingAccess, IMSI imsi, SubscriberState subscriberState,
            LocationInformation locationInformation, ExtBasicServiceCode extBasicServiceCode,
            CallReferenceNumber callReferenceNumber, ISDNAddressString mscAddress, CalledPartyBCDNumber calledPartyBCDNumber,
            TimeAndTimezone timeAndTimezone, boolean callForwardingSSPending, InitialDPArgExtension initialDPArgExtension)
            throws CAPException {

        return addInitialDPRequest(_Timer_Default, serviceKey, calledPartyNumber, callingPartyNumber, callingPartysCategory,
                CGEncountered, IPSSPCapabilities, locationNumber, originalCalledPartyID, extensions, highLayerCompatibility,
                additionalCallingPartyNumber, bearerCapability, eventTypeBCSM, redirectingPartyID, redirectionInformation,
                cause, serviceInteractionIndicatorsTwo, carrier, cugIndex, cugInterlock, cugOutgoingAccess, imsi,
                subscriberState, locationInformation, extBasicServiceCode, callReferenceNumber, mscAddress,
                calledPartyBCDNumber, timeAndTimezone, callForwardingSSPending, initialDPArgExtension);
    }

    @Override
    public Long addInitialDPRequest(int customInvokeTimeout, int serviceKey, CalledPartyNumberIsup calledPartyNumber,
            CallingPartyNumberIsup  callingPartyNumber, CallingPartysCategoryIsup callingPartysCategory,
            CGEncountered CGEncountered, IPSSPCapabilities IPSSPCapabilities, LocationNumberIsup locationNumber,
            OriginalCalledNumberIsup  originalCalledPartyID, CAPINAPExtensions extensions,
            HighLayerCompatibilityIsup highLayerCompatibility, DigitsIsup  additionalCallingPartyNumber,
            BearerCapability  bearerCapability, EventTypeBCSM eventTypeBCSM, RedirectingPartyIDIsup redirectingPartyID,
            RedirectionInformationIsup redirectionInformation, CauseIsup cause,
            ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo, Carrier carrier, CUGIndex cugIndex,
            CUGInterlock cugInterlock, boolean cugOutgoingAccess, IMSI  imsi, SubscriberState subscriberState,
            LocationInformation locationInformation, ExtBasicServiceCode extBasicServiceCode,
            CallReferenceNumber  callReferenceNumber, ISDNAddressString    mscAddress, CalledPartyBCDNumber  calledPartyBCDNumber,
            TimeAndTimezone   timeAndTimezone, boolean callForwardingSSPending, InitialDPArgExtension initialDPArgExtension)
            throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV1_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric)
            throw new CAPException(
                    "Bad application context name for addInitialDPRequest: must be CapV1_gsmSSF_to_gsmSCF, CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric or CapV4_gsmSSF_scfGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        InitialDPRequestImpl req = new InitialDPRequestImpl(serviceKey, calledPartyNumber, callingPartyNumber,
                callingPartysCategory, CGEncountered, IPSSPCapabilities, locationNumber, originalCalledPartyID, extensions,
                highLayerCompatibility, additionalCallingPartyNumber, bearerCapability, eventTypeBCSM, redirectingPartyID,
                redirectionInformation, cause, serviceInteractionIndicatorsTwo, carrier, cugIndex, cugInterlock,
                cugOutgoingAccess, imsi, subscriberState, locationInformation, extBasicServiceCode, callReferenceNumber,
                mscAddress, calledPartyBCDNumber, timeAndTimezone, callForwardingSSPending, initialDPArgExtension);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.initialDP, req, true, false);        
    }

    @Override
    public Long addApplyChargingReportRequest(TimeDurationChargingResult timeDurationChargingResult) throws CAPException {
        return addApplyChargingReportRequest(_Timer_Default, timeDurationChargingResult);
    }

    @Override
    public Long addApplyChargingReportRequest(int customInvokeTimeout, TimeDurationChargingResult timeDurationChargingResult)
            throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addApplyChargingReportRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_gsmSSF_scfGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        ApplyChargingReportRequestImpl req = new ApplyChargingReportRequestImpl(timeDurationChargingResult);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.applyChargingReport, req, true, false);        
    }

    @Override
    public Long addApplyChargingRequest(CAMELAChBillingChargingCharacteristics aChBillingChargingCharacteristics,
            LegType partyToCharge, CAPINAPExtensions extensions, AChChargingAddress aChChargingAddress) throws CAPException {

        return addApplyChargingRequest(_Timer_Default, aChBillingChargingCharacteristics, partyToCharge, extensions,
                aChChargingAddress);
    }

    @Override
    public Long addApplyChargingRequest(int customInvokeTimeout,
            CAMELAChBillingChargingCharacteristics aChBillingChargingCharacteristics, LegType partyToCharge,
            CAPINAPExtensions extensions, AChChargingAddress aChChargingAddress) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addApplyChargingRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        ApplyChargingRequestImpl req = new ApplyChargingRequestImpl(aChBillingChargingCharacteristics, partyToCharge,
                extensions, aChChargingAddress);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.applyCharging, req, true, false);        
    }

    @Override
    public Long addCallInformationReportRequest(List<RequestedInformation> requestedInformationList,
            CAPINAPExtensions extensions, LegType legID) throws CAPException {

        return addCallInformationReportRequest(_Timer_Default, requestedInformationList, extensions, legID);
    }

    @Override
    public Long addCallInformationReportRequest(int customInvokeTimeout,
            List<RequestedInformation> requestedInformationList, CAPINAPExtensions extensions, LegType legID)
            throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addCallInformationReportRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_gsmSSF_scfGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        CallInformationReportRequestImpl req = new CallInformationReportRequestImpl(requestedInformationList, extensions, legID);
        return this.sendDataComponent(null, null, InvokeClass.Class4, customTimeout.longValue(), (long) CAPOperationCode.callInformationReport, req, true, false);        
    }

    @Override
    public Long addCallInformationRequestRequest(List<RequestedInformationType> requestedInformationTypeList,
            CAPINAPExtensions extensions, LegType legID) throws CAPException {

        return addCallInformationRequestRequest(_Timer_Default, requestedInformationTypeList, extensions, legID);
    }

    @Override
    public Long addCallInformationRequestRequest(int customInvokeTimeout,
            List<RequestedInformationType> requestedInformationTypeList, CAPINAPExtensions extensions, LegType legID)
            throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addCallInformationRequestRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_gsmSSF_scfGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        CallInformationRequestRequestImpl req = new CallInformationRequestRequestImpl(requestedInformationTypeList, extensions,
                legID);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.callInformationRequest, req, true, false);        
    }

    @Override
    public Long addConnectRequest(DestinationRoutingAddress   destinationRoutingAddress, AlertingPattern alertingPattern,
            OriginalCalledNumberIsup  originalCalledPartyID, CAPINAPExtensions extensions, Carrier carrier,
            CallingPartysCategoryIsup callingPartysCategory, RedirectingPartyIDIsup redirectingPartyID,
            RedirectionInformationIsup redirectionInformation, List<GenericNumberIsup> genericNumbers,
            ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo, LocationNumberIsup chargeNumber,
            LegID legToBeConnected, CUGInterlock cugInterlock, boolean cugOutgoingAccess, boolean suppressionOfAnnouncement,
            boolean ocsIApplicable, NAOliInfo naoliInfo, boolean borInterrogationRequested, boolean suppressNCSI) throws CAPException {

        return addConnectRequest(_Timer_Default, destinationRoutingAddress, alertingPattern, originalCalledPartyID, extensions,
                carrier, callingPartysCategory, redirectingPartyID, redirectionInformation, genericNumbers,
                serviceInteractionIndicatorsTwo, chargeNumber, legToBeConnected, cugInterlock, cugOutgoingAccess,
                suppressionOfAnnouncement, ocsIApplicable, naoliInfo, borInterrogationRequested, suppressNCSI);
    }

    @Override
    public Long addConnectRequest(int customInvokeTimeout, DestinationRoutingAddress   destinationRoutingAddress,
    		AlertingPattern alertingPattern, OriginalCalledNumberIsup  originalCalledPartyID, CAPINAPExtensions extensions,
            Carrier carrier, CallingPartysCategoryIsup callingPartysCategory, RedirectingPartyIDIsup redirectingPartyID,
            RedirectionInformationIsup redirectionInformation, List<GenericNumberIsup> genericNumbers,
            ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo, LocationNumberIsup chargeNumber,
            LegID legToBeConnected, CUGInterlock cugInterlock, boolean cugOutgoingAccess, boolean suppressionOfAnnouncement,
            boolean ocsIApplicable, NAOliInfo naoliInfo, boolean borInterrogationRequested, boolean suppressNCSI) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV1_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addConnectRequest: must be CapV1_gsmSSF_to_gsmSCF, CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        ConnectRequestImpl req = new ConnectRequestImpl(destinationRoutingAddress, alertingPattern, originalCalledPartyID,
                extensions, carrier, callingPartysCategory, redirectingPartyID, redirectionInformation, genericNumbers,
                serviceInteractionIndicatorsTwo, chargeNumber, legToBeConnected, cugInterlock, cugOutgoingAccess,
                suppressionOfAnnouncement, ocsIApplicable, naoliInfo, borInterrogationRequested, suppressNCSI);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.connect, req, true, false);        
    }

    @Override
    public Long addContinueRequest() throws CAPException {

        return addContinueRequest(_Timer_Default);
    }

    @Override
    public Long addContinueRequest(int customInvokeTimeout) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV1_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addContinueRequest: must be CapV1_gsmSSF_to_gsmSCF, CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        return this.sendDataComponent(null, null, InvokeClass.Class4, customTimeout.longValue(), (long) CAPOperationCode.continueCode, null, true, false);        
    }

    @Override
    public Long addContinueWithArgumentRequest(AlertingPattern alertingPattern, CAPINAPExtensions extensions,
            ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo,
            CallingPartysCategoryIsup callingPartysCategory, List<GenericNumberIsup> genericNumbers,
            CUGInterlock cugInterlock, boolean cugOutgoingAccess, LocationNumberIsup chargeNumber, Carrier carrier,
            boolean suppressionOfAnnouncement, NAOliInfo naOliInfo, boolean borInterrogationRequested,
            boolean suppressOCsi, ContinueWithArgumentArgExtension continueWithArgumentArgExtension)
            throws CAPException {

        return addContinueWithArgumentRequest(_Timer_Default, alertingPattern, extensions,
                serviceInteractionIndicatorsTwo, callingPartysCategory, genericNumbers, cugInterlock,
                cugOutgoingAccess, chargeNumber, carrier, suppressionOfAnnouncement, naOliInfo,
                borInterrogationRequested, suppressOCsi, continueWithArgumentArgExtension);
    }

    @Override
    public Long addContinueWithArgumentRequest(int customInvokeTimeout, AlertingPattern alertingPattern,
            CAPINAPExtensions extensions, ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo,
            CallingPartysCategoryIsup callingPartysCategory, List<GenericNumberIsup> genericNumbers,
            CUGInterlock cugInterlock, boolean cugOutgoingAccess, LocationNumberIsup chargeNumber, Carrier carrier,
            boolean suppressionOfAnnouncement, NAOliInfo naOliInfo, boolean borInterrogationRequested,
            boolean suppressOCsi, ContinueWithArgumentArgExtension continueWithArgumentArgExtension)
            throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addContinueWithArgumentRequest: must be CapV3_gsmSSF_scfGeneric or CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        ContinueWithArgumentRequestImpl req = new ContinueWithArgumentRequestImpl(alertingPattern, extensions,
                serviceInteractionIndicatorsTwo, callingPartysCategory, genericNumbers, cugInterlock,
                cugOutgoingAccess, chargeNumber, carrier, suppressionOfAnnouncement, naOliInfo,
                borInterrogationRequested, suppressOCsi, continueWithArgumentArgExtension);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.continueWithArgument, req, true, false);        
    }

    @Override
    public Long addEventReportBCSMRequest(EventTypeBCSM eventTypeBCSM,
            EventSpecificInformationBCSM eventSpecificInformationBCSM, LegType legID, MiscCallInfo miscCallInfo,
            CAPINAPExtensions extensions) throws CAPException {

        return addEventReportBCSMRequest(_Timer_Default, eventTypeBCSM, eventSpecificInformationBCSM, legID, miscCallInfo,
                extensions);
    }

    @Override
    public Long addEventReportBCSMRequest(int customInvokeTimeout, EventTypeBCSM eventTypeBCSM,
            EventSpecificInformationBCSM eventSpecificInformationBCSM, LegType legID, MiscCallInfo miscCallInfo,
            CAPINAPExtensions extensions) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV1_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addEventReportBCSMRequest: must be CapV1_gsmSSF_to_gsmSCF, CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        EventReportBCSMRequestImpl req = new EventReportBCSMRequestImpl(eventTypeBCSM, eventSpecificInformationBCSM, legID,
                miscCallInfo, extensions);
        return this.sendDataComponent(null, null, InvokeClass.Class4, customTimeout.longValue(), (long) CAPOperationCode.eventReportBCSM, req, true, false);        
    }

    @Override
    public Long addRequestReportBCSMEventRequest(List<BCSMEvent> bcsmEventList, CAPINAPExtensions extensions)
            throws CAPException {

        return addRequestReportBCSMEventRequest(_Timer_Default, bcsmEventList, extensions);
    }

    @Override
    public Long addRequestReportBCSMEventRequest(int customInvokeTimeout, List<BCSMEvent> bcsmEventList,
            CAPINAPExtensions extensions) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV1_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addRequestReportBCSMEventRequest: must be CapV1_gsmSSF_to_gsmSCF, CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        RequestReportBCSMEventRequestImpl req = new RequestReportBCSMEventRequestImpl(bcsmEventList, extensions);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.requestReportBCSMEvent, req, true, false);        
    }

    @Override
    public Long addReleaseCallRequest(CauseIsup cause) throws CAPException {

        return addReleaseCallRequest(_Timer_Default, cause);
    }

    @Override
    public Long addReleaseCallRequest(int customInvokeTimeout, CauseIsup cause) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV1_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addReleaseCallRequest: must be CapV1_gsmSSF_to_gsmSCF, CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        ReleaseCallRequestImpl req = new ReleaseCallRequestImpl(cause);
        return this.sendDataComponent(null, null, InvokeClass.Class4, customTimeout.longValue(), (long) CAPOperationCode.releaseCall, req, true, false);        
    }

    @Override
    public Long addActivityTestRequest() throws CAPException {

        return addActivityTestRequest(_Timer_Default);
    }

    @Override
    public Long addActivityTestRequest(int customInvokeTimeout) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV1_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV2_gsmSRF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSRF_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV4_gsmSRF_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addActivityTestRequest: must be CapV1_gsmSSF_to_gsmSCF, CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff, CapV2_gsmSRF_to_gsmSCF, CapV3_gsmSRF_gsmSCF, CapV4_gsmSRF_gsmSCF "
                            + "or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        return this.sendDataComponent(null, null, InvokeClass.Class3, customTimeout.longValue(), (long) CAPOperationCode.activityTest, null, true, false);        
    }

    public void addActivityTestResponse(long invokeId) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV1_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV2_gsmSRF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSRF_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV4_gsmSRF_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addActivityTestResponse: must be CapV1_gsmSSF_to_gsmSCF, CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff, CapV2_gsmSRF_to_gsmSCF, CapV3_gsmSRF_gsmSCF, CapV4_gsmSRF_gsmSCF "
                            + "or CapV4_scf_gsmSSFGeneric");

        this.sendDataComponent(invokeId, null, null, null, null, null, false, true);
    }

    @Override
    public Long addAssistRequestInstructionsRequest(DigitsIsup  correlationID, IPSSPCapabilities ipSSPCapabilities,
            CAPINAPExtensions extensions) throws CAPException {

        return addAssistRequestInstructionsRequest(_Timer_Default, correlationID, ipSSPCapabilities, extensions);
    }

    @Override
    public Long addAssistRequestInstructionsRequest(int customInvokeTimeout, DigitsIsup  correlationID,
            IPSSPCapabilities ipSSPCapabilities, CAPINAPExtensions extensions) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV2_gsmSRF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSRF_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV4_gsmSRF_gsmSCF)
            throw new CAPException(
                    "Bad application context name for addAssistRequestInstructionsRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff, CapV2_gsmSRF_to_gsmSCF, CapV3_gsmSRF_gsmSCF  "
                            + "or CapV4_gsmSRF_gsmSCF");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        AssistRequestInstructionsRequestImpl req = new AssistRequestInstructionsRequestImpl(correlationID, ipSSPCapabilities,
                extensions);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.assistRequestInstructions, req, true, false);        
    }

    @Override
    public Long addEstablishTemporaryConnectionRequest(DigitsIsup  assistingSSPIPRoutingAddress, DigitsIsup  correlationID, ScfID scfID,
            CAPINAPExtensions extensions, Carrier carrier, ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo,
            Integer callSegmentID, NAOliInfo naOliInfo, LocationNumberIsup chargeNumber,
            OriginalCalledNumberIsup  originalCalledPartyID, CallingPartyNumberIsup  callingPartyNumber) throws CAPException {

        return addEstablishTemporaryConnectionRequest(_Timer_Default, assistingSSPIPRoutingAddress, correlationID, scfID,
                extensions, carrier, serviceInteractionIndicatorsTwo, callSegmentID, naOliInfo, chargeNumber,
                originalCalledPartyID, callingPartyNumber);
    }

    @Override
    public Long addEstablishTemporaryConnectionRequest(int customInvokeTimeout, DigitsIsup  assistingSSPIPRoutingAddress,
            DigitsIsup  correlationID, ScfID scfID, CAPINAPExtensions extensions, Carrier carrier,
            ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo, Integer callSegmentID, NAOliInfo naOliInfo,
            LocationNumberIsup chargeNumber, OriginalCalledNumberIsup  originalCalledPartyID,
            CallingPartyNumberIsup  callingPartyNumber) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addEstablishTemporaryConnectionRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        EstablishTemporaryConnectionRequestImpl req = new EstablishTemporaryConnectionRequestImpl(assistingSSPIPRoutingAddress,
                correlationID, scfID, extensions, carrier, serviceInteractionIndicatorsTwo, callSegmentID, naOliInfo,
                chargeNumber, originalCalledPartyID, callingPartyNumber);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.establishTemporaryConnection, req, true, false);        
    }
    


    @Override
    public Long addEstablishTemporaryConnectionRequest(DigitsIsup  assistingSSPIPRoutingAddress, DigitsIsup  correlationID, ScfID scfID,
            CAPINAPExtensions extensions, Carrier carrier, ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo,
            NAOliInfo naOliInfo, LocationNumberIsup chargeNumber,
            OriginalCalledNumberIsup  originalCalledPartyID, CallingPartyNumberIsup  callingPartyNumber) throws CAPException {

        return addEstablishTemporaryConnectionRequest(_Timer_Default, assistingSSPIPRoutingAddress, correlationID, scfID,
                extensions, carrier, serviceInteractionIndicatorsTwo, naOliInfo, chargeNumber,
                originalCalledPartyID, callingPartyNumber);
    }

    @Override
    public Long addEstablishTemporaryConnectionRequest(int customInvokeTimeout, DigitsIsup  assistingSSPIPRoutingAddress,
            DigitsIsup  correlationID, ScfID scfID, CAPINAPExtensions extensions, Carrier carrier,
            ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo, NAOliInfo naOliInfo,
            LocationNumberIsup chargeNumber, OriginalCalledNumberIsup  originalCalledPartyID,
            CallingPartyNumberIsup  callingPartyNumber) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addEstablishTemporaryConnectionRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        EstablishTemporaryConnectionRequestImpl req = new EstablishTemporaryConnectionRequestImpl(assistingSSPIPRoutingAddress,
                correlationID, scfID, extensions, carrier, serviceInteractionIndicatorsTwo, naOliInfo,
                chargeNumber, originalCalledPartyID, callingPartyNumber);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.establishTemporaryConnection, req, true, false);        
    }

    @Override
    public Long addDisconnectForwardConnectionRequest() throws CAPException {

        return addDisconnectForwardConnectionRequest(_Timer_Default);
    }

    @Override
    public Long addDisconnectForwardConnectionRequest(int customInvokeTimeout) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addDisconnectForwardConnectionRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.disconnectForwardConnection, null, true, false);        
    }

    @Override
    public Long addDisconnectForwardConnectionWithArgumentRequest(Integer callSegmentID, CAPINAPExtensions extensions)
            throws CAPException {
        return addDisconnectForwardConnectionWithArgumentRequest(_Timer_Default, callSegmentID, extensions);
    }

    @Override
    public Long addDisconnectForwardConnectionWithArgumentRequest(int customInvokeTimeout, Integer callSegmentID,
            CAPINAPExtensions extensions) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addDisconnectForwardConnectionWithArgumentRequest: must be CapV4_gsmSSF_scfGeneric, " +
                    "CapV4_gsmSSF_scfAssistHandoff or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        DisconnectForwardConnectionWithArgumentRequestImpl req = new DisconnectForwardConnectionWithArgumentRequestImpl(
                callSegmentID, extensions);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.dFCWithArgument, req, true, false);        
    }

    @Override
    public Long addConnectToResourceRequest(CalledPartyNumberIsup resourceAddress_IPRoutingAddress,
            boolean resourceAddress_Null, CAPINAPExtensions extensions,
            ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo, Integer callSegmentID) throws CAPException {

        return addConnectToResourceRequest(_Timer_Default, resourceAddress_IPRoutingAddress, resourceAddress_Null, extensions,
                serviceInteractionIndicatorsTwo, callSegmentID);
    }

    @Override
    public Long addConnectToResourceRequest(int customInvokeTimeout, CalledPartyNumberIsup resourceAddress_IPRoutingAddress,
            boolean resourceAddress_Null, CAPINAPExtensions extensions,
            ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo, Integer callSegmentID) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addConnectToResourceRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        ConnectToResourceRequestImpl req = new ConnectToResourceRequestImpl(resourceAddress_IPRoutingAddress,
                resourceAddress_Null, extensions, serviceInteractionIndicatorsTwo, callSegmentID);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.connectToResource, req, true, false);        
    }

    @Override
    public Long addResetTimerRequest(TimerID timerID, int timerValue, CAPINAPExtensions extensions, Integer callSegmentID)
            throws CAPException {

        return addResetTimerRequest(_Timer_Default, timerID, timerValue, extensions, callSegmentID);
    }

    @Override
    public Long addResetTimerRequest(int customInvokeTimeout, TimerID timerID, int timerValue, CAPINAPExtensions extensions,
            Integer callSegmentID) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addResetTimerRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        ResetTimerRequestImpl req = new ResetTimerRequestImpl(timerID, timerValue, extensions, callSegmentID);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.resetTimer, req, true, false);        
    }

    @Override
    public Long addFurnishChargingInformationRequest(FCIBCCCAMELSequence1 FCIBCCCAMELsequence1) throws CAPException {

        return addFurnishChargingInformationRequest(_Timer_Default, FCIBCCCAMELsequence1);
    }

    @Override
    public Long addFurnishChargingInformationRequest(int customInvokeTimeout, FCIBCCCAMELSequence1 FCIBCCCAMELsequence1)
            throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addFurnishChargingInformationRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        FurnishChargingInformationRequestImpl req = new FurnishChargingInformationRequestImpl(FCIBCCCAMELsequence1);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.furnishChargingInformation, req, true, false);        
    }

    @Override
    public Long addSendChargingInformationRequest(SCIBillingChargingCharacteristics sciBillingChargingCharacteristics,
            LegType partyToCharge, CAPINAPExtensions extensions) throws CAPException {

        return addSendChargingInformationRequest(_Timer_Default, sciBillingChargingCharacteristics, partyToCharge, extensions);
    }

    @Override
    public Long addSendChargingInformationRequest(int customInvokeTimeout,
            SCIBillingChargingCharacteristics sciBillingChargingCharacteristics, LegType partyToCharge,
            CAPINAPExtensions extensions) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric)
            throw new CAPException(
                    "Bad application context name for addSendChargingInformationRequest: must be CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        SendChargingInformationRequestImpl req = new SendChargingInformationRequestImpl(sciBillingChargingCharacteristics,
                partyToCharge, extensions);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.sendChargingInformation, req, true, false);        
    }

    @Override
    public Long addSpecializedResourceReportRequest_CapV23(Long linkedId) throws CAPException {

        return addSpecializedResourceReportRequest_CapV23(linkedId, _Timer_Default);
    }

    @Override
    public Long addSpecializedResourceReportRequest_CapV4(Long linkedId, boolean isAllAnnouncementsComplete,
            boolean isFirstAnnouncementStarted) throws CAPException {

        return addSpecializedResourceReportRequest_CapV4(linkedId, _Timer_Default, isAllAnnouncementsComplete,
                isFirstAnnouncementStarted);
    }

    @Override
    public Long addSpecializedResourceReportRequest_CapV23(Long linkedId, int customInvokeTimeout) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV2_gsmSRF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSRF_gsmSCF)
            throw new CAPException(
                    "Bad application context name for addSpecializedResourceReportRequest_CapV23: must be "
                            + "CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV2_gsmSRF_to_gsmSCF or CapV3_gsmSRF_gsmSCF");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        SpecializedResourceReportRequest req = new SpecializedResourceReportRequestImpl(false,false);
        return this.sendDataComponent(null, linkedId, InvokeClass.Class4, customTimeout.longValue(), (long) CAPOperationCode.specializedResourceReport, req, true, false);        
    }

    @Override
    public Long addSpecializedResourceReportRequest_CapV4(Long linkedId, int customInvokeTimeout,
            boolean isAllAnnouncementsComplete, boolean isFirstAnnouncementStarted) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSRF_gsmSCF)
            throw new CAPException(
                    "Bad application context name for addSpecializedResourceReportRequest_CapV4: "
                            + "must be CapV4_gsmSSF_scfGeneric, CapV4_gsmSSF_scfAssistHandoff, CapV4_scf_gsmSSFGeneric or CapV4_gsmSRF_gsmSCF");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        SpecializedResourceReportRequestImpl req = new SpecializedResourceReportRequestImpl(isAllAnnouncementsComplete,
                isFirstAnnouncementStarted);
        return this.sendDataComponent(null, linkedId, InvokeClass.Class4, customTimeout.longValue(), (long) CAPOperationCode.specializedResourceReport, req, true, false);        
    }

    @Override
    public Long addPlayAnnouncementRequest(InformationToSend informationToSend, Boolean disconnectFromIPForbidden,
            Boolean requestAnnouncementCompleteNotification, CAPINAPExtensions extensions, Integer callSegmentID,
            Boolean requestAnnouncementStartedNotification) throws CAPException {

        return addPlayAnnouncementRequest(_Timer_Default, informationToSend, disconnectFromIPForbidden,
                requestAnnouncementCompleteNotification, extensions, callSegmentID, requestAnnouncementStartedNotification);
    }

    @Override
    public Long addPlayAnnouncementRequest(int customInvokeTimeout, InformationToSend informationToSend,
            Boolean disconnectFromIPForbidden, Boolean requestAnnouncementCompleteNotification, CAPINAPExtensions extensions,
            Integer callSegmentID, Boolean requestAnnouncementStartedNotification) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric
                && this.appCntx != CAPApplicationContext.CapV2_gsmSRF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSRF_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV4_gsmSRF_gsmSCF)
            throw new CAPException(
                    "Bad application context name for addPlayAnnouncementRequest: must be "
                            + "CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff, CapV4_scf_gsmSSFGeneric"
                            + ", CapV2_gsmSRF_to_gsmSCF, CapV3_gsmSRF_gsmSCF or CapV4_gsmSRF_gsmSCF");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        PlayAnnouncementRequestImpl req = new PlayAnnouncementRequestImpl(informationToSend, disconnectFromIPForbidden,
                requestAnnouncementCompleteNotification, extensions, callSegmentID, requestAnnouncementStartedNotification);
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.playAnnouncement, req, true, false);        
    }

    @Override
    public Long addPromptAndCollectUserInformationRequest(CollectedInfo collectedInfo, Boolean disconnectFromIPForbidden,
            InformationToSend informationToSend, CAPINAPExtensions extensions, Integer callSegmentID,
            Boolean requestAnnouncementStartedNotification) throws CAPException {

        return addPromptAndCollectUserInformationRequest(_Timer_Default, collectedInfo, disconnectFromIPForbidden,
                informationToSend, extensions, callSegmentID, requestAnnouncementStartedNotification);
    }

    @Override
    public Long addPromptAndCollectUserInformationRequest(int customInvokeTimeout, CollectedInfo collectedInfo,
            Boolean disconnectFromIPForbidden, InformationToSend informationToSend, CAPINAPExtensions extensions,
            Integer callSegmentID, Boolean requestAnnouncementStartedNotification) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric
                && this.appCntx != CAPApplicationContext.CapV2_gsmSRF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSRF_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV4_gsmSRF_gsmSCF)
            throw new CAPException(
                    "Bad application context name for addPromptAndCollectUserInformationRequest: must be "
                            + "CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff, CapV4_scf_gsmSSFGeneric"
                            + ", CapV2_gsmSRF_to_gsmSCF, CapV3_gsmSRF_gsmSCF or CapV4_gsmSRF_gsmSCF");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        PromptAndCollectUserInformationRequestImpl req = new PromptAndCollectUserInformationRequestImpl(collectedInfo,
                disconnectFromIPForbidden, informationToSend, extensions, callSegmentID, requestAnnouncementStartedNotification);
        return this.sendDataComponent(null, null, InvokeClass.Class1, customTimeout.longValue(), (long) CAPOperationCode.promptAndCollectUserInformation, req, true, false);        
    }

    @Override
    public void addPromptAndCollectUserInformationResponse_DigitsResponse(long invokeId, DigitsIsup  digitsResponse)
            throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric
                && this.appCntx != CAPApplicationContext.CapV2_gsmSRF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSRF_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV4_gsmSRF_gsmSCF)
            throw new CAPException(
                    "Bad application context name for addPromptAndCollectUserInformationResponse: must be "
                            + "CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff, CapV4_scf_gsmSSFGeneric"
                            + ", CapV2_gsmSRF_to_gsmSCF, CapV3_gsmSRF_gsmSCF or CapV4_gsmSRF_gsmSCF");

        PromptAndCollectUserInformationResponseImpl res = new PromptAndCollectUserInformationResponseImpl(digitsResponse);
        this.sendDataComponent(invokeId, null, null, null, (long) CAPOperationCode.promptAndCollectUserInformation, res, false, true);        
    }

    @Override
    public Long addCancelRequest_InvokeId(Integer invokeID) throws CAPException {

        return addCancelRequest_InvokeId(_Timer_Default, invokeID);
    }

    @Override
    public Long addCancelRequest_AllRequests() throws CAPException {

        return addCancelRequest_AllRequests(_Timer_Default);
    }

    @Override
    public Long addCancelRequest_CallSegmentToCancel(CallSegmentToCancel callSegmentToCancel) throws CAPException {

        return addCancelRequest_CallSegmentToCancel(_Timer_Default, callSegmentToCancel);
    }

    @Override
    public Long addCancelRequest_InvokeId(int customInvokeTimeout, Integer invokeID) throws CAPException {

        CancelRequestImpl req = new CancelRequestImpl(invokeID);
        return addCancelRequest(customInvokeTimeout, req);
    }

    @Override
    public Long addCancelRequest_AllRequests(int customInvokeTimeout) throws CAPException {

        CancelRequestImpl req = new CancelRequestImpl(true);
        return addCancelRequest(customInvokeTimeout, req);
    }

    @Override
    public Long addCancelRequest_CallSegmentToCancel(int customInvokeTimeout, CallSegmentToCancel callSegmentToCancel)
            throws CAPException {

        CancelRequestImpl req = new CancelRequestImpl(callSegmentToCancel);
        return addCancelRequest(customInvokeTimeout, req);
    }

    private Long addCancelRequest(int customInvokeTimeout, CancelRequest req) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV2_gsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV2_assistGsmSSF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfAssistHandoff
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric
                && this.appCntx != CAPApplicationContext.CapV2_gsmSRF_to_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV3_gsmSRF_gsmSCF
                && this.appCntx != CAPApplicationContext.CapV4_gsmSRF_gsmSCF)
            throw new CAPException(
                    "Bad application context name for addCancelRequest: must be "
                            + "CapV2_gsmSSF_to_gsmSCF, CapV3_gsmSSF_scfGeneric, CapV4_gsmSSF_scfGeneric, "
                            + "CapV2_assistGsmSSF_to_gsmSCF, CapV3_gsmSSF_scfAssistHandoff, CapV4_gsmSSF_scfAssistHandoff, CapV4_scf_gsmSSFGeneric"
                            + ", CapV2_gsmSRF_to_gsmSCF, CapV3_gsmSRF_gsmSCF or CapV4_gsmSRF_gsmSCF");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        return this.sendDataComponent(null, null, InvokeClass.Class2, customTimeout.longValue(), (long) CAPOperationCode.cancelCode, req, true, false);        
    }

    @Override
    public Long addDisconnectLegRequest(LegID logToBeReleased, CauseIsup releaseCause, CAPINAPExtensions extensions)
            throws CAPException {
        return addDisconnectLegRequest(_Timer_Default, logToBeReleased, releaseCause, extensions);
    }

    @Override
    public Long addDisconnectLegRequest(int customInvokeTimeout, LegID logToBeReleased, CauseIsup releaseCause,
            CAPINAPExtensions extensions) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addDisconnectLegRequest: must be CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        DisconnectLegRequestImpl req = new DisconnectLegRequestImpl(logToBeReleased, releaseCause, extensions);
        return this.sendDataComponent(null, null, InvokeClass.Class1, customTimeout.longValue(), (long) CAPOperationCode.disconnectLeg, req, true, false);        
    }

    @Override
    public void addDisconnectLegResponse(long invokeId) throws CAPException {
        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException("Bad application context name for addDisconnectLegResponse: must be " + "CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        this.sendDataComponent(invokeId, null, null, null, (long) CAPOperationCode.disconnectLeg, null, false, true);        
    }

    @Override
    public Long addInitiateCallAttemptRequest(DestinationRoutingAddress   destinationRoutingAddress,
            CAPINAPExtensions extensions, LegID legToBeCreated, Integer newCallSegment,
            CallingPartyNumberIsup  callingPartyNumber, CallReferenceNumber  callReferenceNumber,
            ISDNAddressString    gsmSCFAddress, boolean suppressTCsi) throws CAPException {
        return addInitiateCallAttemptRequest(_Timer_Default, destinationRoutingAddress, extensions, legToBeCreated,
                newCallSegment, callingPartyNumber, callReferenceNumber, gsmSCFAddress, suppressTCsi);
    }

    @Override
    public Long addInitiateCallAttemptRequest(int customInvokeTimeout,
            DestinationRoutingAddress   destinationRoutingAddress, CAPINAPExtensions extensions, LegID legToBeCreated,
            Integer newCallSegment, CallingPartyNumberIsup  callingPartyNumber, CallReferenceNumber  callReferenceNumber,
            ISDNAddressString    gsmSCFAddress, boolean suppressTCsi) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addInitiateCallAttemptRequest: must be CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        InitiateCallAttemptRequestImpl req = new InitiateCallAttemptRequestImpl(destinationRoutingAddress, extensions,
                legToBeCreated, newCallSegment, callingPartyNumber, callReferenceNumber, gsmSCFAddress, suppressTCsi);
        return this.sendDataComponent(null, null, InvokeClass.Class1, customTimeout.longValue(), (long) CAPOperationCode.initiateCallAttempt, req, true, false);        
    }

    @Override
    public void addInitiateCallAttemptResponse(long invokeId, SupportedCamelPhases supportedCamelPhases,
            OfferedCamel4Functionalities offeredCamel4Functionalities, CAPINAPExtensions extensions,
            boolean releaseCallArgExtensionAllowed) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addInitiateCallAttemptResponse: must be CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        InitiateCallAttemptResponseImpl res = new InitiateCallAttemptResponseImpl(supportedCamelPhases,
                offeredCamel4Functionalities, extensions, releaseCallArgExtensionAllowed);
        this.sendDataComponent(invokeId, null, null, null, (long) CAPOperationCode.initiateCallAttempt, res, false, true);
    }

    @Override
    public Long addMoveLegRequest(LegID logIDToMove, CAPINAPExtensions extensions) throws CAPException {
        return addMoveLegRequest(_Timer_Default, logIDToMove, extensions);
    }

    @Override
    public Long addMoveLegRequest(int customInvokeTimeout, LegID logIDToMove, CAPINAPExtensions extensions)
            throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addMoveLegRequest: must be CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        MoveLegRequestImpl req = new MoveLegRequestImpl(logIDToMove, extensions);
        return this.sendDataComponent(null, null, InvokeClass.Class1, customTimeout.longValue(), (long) CAPOperationCode.moveLeg, req, true, false);        
    }

    @Override
    public void addMoveLegResponse(long invokeId) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addMoveLegResponse: must be CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        this.sendDataComponent(invokeId, null, null, null, (long) CAPOperationCode.moveLeg, null, false, true);        
    }

    @Override
    public Long addCollectInformationRequest() throws CAPException {

        return addCollectInformationRequest(_Timer_Default);
    }

    @Override
    public Long addCollectInformationRequest(int customInvokeTimeout) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context name for addContinueRequest: must be CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        return this.sendDataComponent(null, null, InvokeClass.Class4, customTimeout.longValue(), (long) CAPOperationCode.collectInformation, null, true, false);        
    }

    public Long addSplitLegRequest(LegID legIDToSplit, Integer newCallSegmentId, CAPINAPExtensions extensions)
            throws CAPException {
        return addSplitLegRequest(_Timer_Default, legIDToSplit, newCallSegmentId, extensions);
    }

    public Long addSplitLegRequest(int customInvokeTimeout, LegID legIDToSplit, Integer newCallSegmentId,
            CAPINAPExtensions extensions) throws CAPException {
        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context for addSplitLegRequest: must be CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        SplitLegRequestImpl req = new SplitLegRequestImpl(legIDToSplit, newCallSegmentId, extensions);
        return this.sendDataComponent(null, null, InvokeClass.Class1, customTimeout.longValue(), (long) CAPOperationCode.splitLeg, req, true, false);        
    }

    public void addSplitLegResponse(long invokeId) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_scf_gsmSSFGeneric)
            throw new CAPException(
                    "Bad application context for addSplitLegResponse: must be CapV4_gsmSSF_scfGeneric or CapV4_scf_gsmSSFGeneric");

        this.sendDataComponent(invokeId, null, null, null, (long) CAPOperationCode.splitLeg, null, false, true);        
    }

    @Override
    public Long addCallGapRequest(GapCriteria gapCriteria, GapIndicators gapIndicators, ControlType controlType, GapTreatment gapTreatment, CAPINAPExtensions capExtension) throws CAPException {
        return addCallGapRequest(_Timer_Default, gapCriteria, gapIndicators, controlType, gapTreatment, capExtension);
    }

    @Override
    public Long addCallGapRequest(int customInvokeTimeout, GapCriteria gapCriteria, GapIndicators gapIndicators, ControlType controlType, GapTreatment gapTreatment, CAPINAPExtensions capExtension) throws CAPException {

        if (this.appCntx != CAPApplicationContext.CapV3_gsmSSF_scfGeneric
                && this.appCntx != CAPApplicationContext.CapV4_gsmSSF_scfGeneric) {
            throw new CAPException(
                    "Bad application context name for addApplyChargingReportRequest: must be CapV3_gsmSSF_scfGeneric or CapV4_gsmSSF_scfGeneric");
        }

        Integer customTimeout;
        if (customInvokeTimeout == _Timer_Default)
        	customTimeout = getTimerCircuitSwitchedCallControlShort();
        else
        	customTimeout = customInvokeTimeout;
        
        CallGapRequestImpl req = new CallGapRequestImpl(gapCriteria, gapIndicators, controlType, gapTreatment, capExtension);
        return this.sendDataComponent(null, null, InvokeClass.Class4, customTimeout.longValue(), (long) CAPOperationCode.callGap, req, true, false);        
    }
}
