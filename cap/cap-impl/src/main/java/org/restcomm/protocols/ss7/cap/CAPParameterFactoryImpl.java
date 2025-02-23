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

package org.restcomm.protocols.ss7.cap;

import java.util.List;

import org.restcomm.protocols.ss7.cap.EsiBcsm.CallAcceptedSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.ChargeIndicatorImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.CollectedInfoSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.DpSpecificInfoAltImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.MetDPCriterionAltImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.MetDPCriterionImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.MidCallEventsImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.OAbandonSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.OAnswerSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.OCalledPartyBusySpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.OChangeOfPositionSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.ODisconnectSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.OMidCallSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.ONoAnswerSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.OServiceChangeSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.OTermSeizedSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.RouteSelectFailureSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.TAnswerSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.TBusySpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.TChangeOfPositionSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.TDisconnectSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.TMidCallSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.TNoAnswerSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiBcsm.TServiceChangeSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiGprs.DetachSpecificInformationImpl;
import org.restcomm.protocols.ss7.cap.EsiGprs.DisconnectSpecificInformationImpl;
import org.restcomm.protocols.ss7.cap.EsiGprs.PDPContextEstablishmentAcknowledgementSpecificInformationImpl;
import org.restcomm.protocols.ss7.cap.EsiGprs.PDPContextEstablishmentSpecificInformationImpl;
import org.restcomm.protocols.ss7.cap.EsiGprs.PdpContextChangeOfPositionSpecificInformationImpl;
import org.restcomm.protocols.ss7.cap.EsiSms.OSmsFailureSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiSms.OSmsSubmissionSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiSms.TSmsDeliverySpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.EsiSms.TSmsFailureSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.api.CAPException;
import org.restcomm.protocols.ss7.cap.api.CAPParameterFactory;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.CallAcceptedSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.ChargeIndicator;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.ChargeIndicatorValue;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.CollectedInfoSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.DpSpecificInfoAlt;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.MetDPCriterion;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.MetDPCriterionAlt;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.MidCallEvents;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.OAbandonSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.OAnswerSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.OCalledPartyBusySpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.OChangeOfPositionSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.ODisconnectSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.OMidCallSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.ONoAnswerSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.OServiceChangeSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.OTermSeizedSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.RouteSelectFailureSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.TAnswerSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.TBusySpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.TChangeOfPositionSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.TDisconnectSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.TMidCallSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.TNoAnswerSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.TServiceChangeSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiGprs.DetachSpecificInformation;
import org.restcomm.protocols.ss7.cap.api.EsiGprs.DisconnectSpecificInformation;
import org.restcomm.protocols.ss7.cap.api.EsiGprs.PDPContextEstablishmentAcknowledgementSpecificInformation;
import org.restcomm.protocols.ss7.cap.api.EsiGprs.PDPContextEstablishmentSpecificInformation;
import org.restcomm.protocols.ss7.cap.api.EsiGprs.PdpContextChangeOfPositionSpecificInformation;
import org.restcomm.protocols.ss7.cap.api.EsiSms.OSmsFailureSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiSms.OSmsSubmissionSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiSms.TSmsDeliverySpecificInfo;
import org.restcomm.protocols.ss7.cap.api.EsiSms.TSmsFailureSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.dialog.CAPGprsReferenceNumber;
import org.restcomm.protocols.ss7.cap.api.primitives.DateAndTime;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.AOCBeforeAnswer;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.AOCSubsequent;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CAMELAChBillingChargingCharacteristics;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CAMELSCIBillingChargingCharacteristicsAlt;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.EventSpecificInformationBCSM;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.InitialDPArgExtension;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.NACarrierInformation;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.RequestedInformation;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.SCIBillingChargingCharacteristics;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.AOCGPRS;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.AccessPointName;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.CAMELFCIGPRSBillingChargingCharacteristics;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.CAMELSCIGPRSBillingChargingCharacteristics;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.ChargingCharacteristics;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.ChargingResult;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.ChargingRollOver;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.ElapsedTime;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.ElapsedTimeRollOver;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.EndUserAddress;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.FCIBCCCAMELSequence1Gprs;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.FreeFormatDataGprs;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.GPRSCause;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.GPRSEvent;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.GPRSEventSpecificInformation;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.GPRSEventType;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.GPRSQoS;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.GPRSQoSExtension;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.InitiatingEntity;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPAddress;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPID;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPInitiationType;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPTypeNumber;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPTypeNumberValue;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPTypeOrganization;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPTypeOrganizationValue;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.QualityOfService;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.ROTimeGPRSIfTariffSwitch;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.ROVolumeIfTariffSwitch;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.SGSNCapabilities;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.TimeGPRSIfTariffSwitch;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.TransferredVolume;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.TransferredVolumeRollOver;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.VolumeIfTariffSwitch;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.EventSpecificInformationSMS;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.EventTypeSMS;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.FCIBCCCAMELSequence1SMS;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.FreeFormatDataSMS;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.MOSMSCause;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.MTSMSCause;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.RPCause;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.SMSAddressString;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.SMSEvent;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.TPDataCodingScheme;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.TPProtocolIdentifier;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.TPShortMessageSpecificInfo;
import org.restcomm.protocols.ss7.cap.api.service.sms.primitive.TPValidityPeriod;
import org.restcomm.protocols.ss7.cap.dialog.CAPGprsReferenceNumberImpl;
import org.restcomm.protocols.ss7.cap.primitives.DateAndTimeImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.RequestedInformationImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.AOCBeforeAnswerImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.AOCSubsequentImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.CAMELAChBillingChargingCharacteristicsImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.CAMELSCIBillingChargingCharacteristicsAltImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.EventSpecificInformationBCSMImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.InitialDPArgExtensionImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.SCIBillingChargingCharacteristicsImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.AOCGPRSImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.AccessPointNameImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.CAMELFCIGPRSBillingChargingCharacteristicsImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.CAMELSCIGPRSBillingChargingCharacteristicsImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.ChargingCharacteristicsImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.ChargingResultImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.ChargingRollOverImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.ElapsedTimeImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.ElapsedTimeRollOverImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.EndUserAddressImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.FCIBCCCAMELSequence1GprsImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.FreeFormatDataGprsImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.GPRSCauseImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.GPRSEventImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.GPRSEventSpecificInformationImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.GPRSQoSExtensionImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.GPRSQoSImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.PDPAddressImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.PDPIDImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.PDPTypeNumberImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.PDPTypeOrganizationImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.QualityOfServiceImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.ROTimeGPRSIfTariffSwitchImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.ROVolumeIfTariffSwitchImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.SGSNCapabilitiesImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.TimeGPRSIfTariffSwitchImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.TransferredVolumeImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.TransferredVolumeRollOverImpl;
import org.restcomm.protocols.ss7.cap.service.gprs.primitive.VolumeIfTariffSwitchImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.EventSpecificInformationSMSImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.FCIBCCCAMELSequence1SMSImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.FreeFormatDataSMSImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.MTSMSCauseImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.RPCauseImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.SMSAddressStringImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.SMSEventImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.TPDataCodingSchemeImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.TPProtocolIdentifierImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.TPShortMessageSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.service.sms.primitive.TPValidityPeriodImpl;
import org.restcomm.protocols.ss7.commonapp.api.APPException;
import org.restcomm.protocols.ss7.commonapp.api.callhandling.UUData;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.AlertingPatternWrapper;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.AudibleIndicator;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.BackwardServiceInteractionInd;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.BearerCapability;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CAI_GSM0224;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CallCompletionTreatmentIndicator;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CallDiversionTreatmentIndicator;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CallSegmentToCancel;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CalledPartyBCDNumber;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CallingPartyRestrictionIndicator;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.Carrier;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ChangeOfLocation;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ChangeOfLocationAlt;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CollectedDigits;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CollectedInfo;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ConferenceTreatmentIndicator;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ConnectedNumberTreatmentInd;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CwTreatmentIndicator;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.DestinationRoutingAddress;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.DpSpecificCriteria;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.DpSpecificCriteriaAlt;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.EctTreatmentIndicator;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.FCIBCCCAMELSequence1;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ForwardServiceInteractionInd;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.FreeFormatData;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.HoldTreatmentIndicator;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.IPSSPCapabilities;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.InbandInfo;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.InformationToSend;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.LegOrCallSegment;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.LowLayerCompatibility;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.MessageID;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.MessageIDText;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.MidCallControlInfo;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.NAOliInfo;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.RequestedInformationType;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ServiceInteractionIndicatorsTwo;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.TimeDurationChargingResult;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.TimeIfTariffSwitch;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.TimeInformation;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.Tone;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.VariableMessage;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.VariablePart;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.VariablePartDate;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.VariablePartPrice;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.VariablePartTime;
import org.restcomm.protocols.ss7.commonapp.api.isup.BearerIsup;
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
import org.restcomm.protocols.ss7.commonapp.api.primitives.AddressNature;
import org.restcomm.protocols.ss7.commonapp.api.primitives.AlertingPattern;
import org.restcomm.protocols.ss7.commonapp.api.primitives.AppendFreeFormatData;
import org.restcomm.protocols.ss7.commonapp.api.primitives.BCSMEvent;
import org.restcomm.protocols.ss7.commonapp.api.primitives.BothwayThroughConnectionInd;
import org.restcomm.protocols.ss7.commonapp.api.primitives.Burst;
import org.restcomm.protocols.ss7.commonapp.api.primitives.BurstList;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CAPINAPExtensions;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CellGlobalIdOrServiceAreaIdFixedLength;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CellGlobalIdOrServiceAreaIdOrLAI;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CriticalityType;
import org.restcomm.protocols.ss7.commonapp.api.primitives.ErrorTreatment;
import org.restcomm.protocols.ss7.commonapp.api.primitives.EventTypeBCSM;
import org.restcomm.protocols.ss7.commonapp.api.primitives.ExtensionField;
import org.restcomm.protocols.ss7.commonapp.api.primitives.GSNAddress;
import org.restcomm.protocols.ss7.commonapp.api.primitives.IMEI;
import org.restcomm.protocols.ss7.commonapp.api.primitives.IMSI;
import org.restcomm.protocols.ss7.commonapp.api.primitives.ISDNAddressString;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LAIFixedLength;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LegID;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LegType;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MAPExtensionContainer;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MiscCallInfo;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MiscCallInfoDpAssignment;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MiscCallInfoMessageType;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MonitorMode;
import org.restcomm.protocols.ss7.commonapp.api.primitives.NumberingPlan;
import org.restcomm.protocols.ss7.commonapp.api.primitives.ScfID;
import org.restcomm.protocols.ss7.commonapp.api.primitives.TimeAndTimezone;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.GPRSChargingID;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.GeodeticInformation;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.GeographicalInformation;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.LocationInformation;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.LocationInformationEPS;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.LocationInformationGPRS;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.LocationNumberMap;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.MSClassmark2;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.UserCSGInformation;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.Ext2QoSSubscribed;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.ExtBasicServiceCode;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.ExtQoSSubscribed;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.LSAIdentity;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.OfferedCamel4Functionalities;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.QoSSubscribed;
import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.SupportedCamelPhases;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.AlertingPatternWrapperImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.AudibleIndicatorImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.BackwardServiceInteractionIndImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.BearerCapabilityImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.CAI_GSM0224Impl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.CallSegmentToCancelImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.CalledPartyBCDNumberImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.CarrierImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.ChangeOfLocationAltImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.ChangeOfLocationImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.CollectedDigitsImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.CollectedInfoImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.DestinationRoutingAddressImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.DpSpecificCriteriaAltImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.DpSpecificCriteriaImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.FCIBCCCAMELSequence1Impl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.ForwardServiceInteractionIndImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.FreeFormatDataImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.IPSSPCapabilitiesImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.InbandInfoImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.InformationToSendImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.LegOrCallSegmentImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.LowLayerCompatibilityImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.MessageIDImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.MessageIDTextImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.MidCallControlInfoImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.NAOliInfoImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.ServiceInteractionIndicatorsTwoImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.TimeDurationChargingResultImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.TimeIfTariffSwitchImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.TimeInformationImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.ToneImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.VariableMessageImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.VariablePartDateImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.VariablePartImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.VariablePartPriceImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.VariablePartTimeImpl;
import org.restcomm.protocols.ss7.commonapp.isup.BearerIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.CalledPartyNumberIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.CallingPartyNumberIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.CallingPartysCategoryIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.CauseIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.DigitsIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.GenericNumberIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.HighLayerCompatibilityIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.LocationNumberIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.OriginalCalledNumberIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.RedirectingPartyIDIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.RedirectionInformationIsupImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.AChChargingAddressImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.BCSMEventImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.BurstImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.BurstListImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.CAPINAPExtensionsImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.ExtensionFieldImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.IMSIImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.ISDNAddressStringImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.LegIDImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.MiscCallInfoImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.ScfIDImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.TimeAndTimezoneImpl;
import org.restcomm.protocols.ss7.commonapp.subscriberInformation.LocationInformationImpl;
import org.restcomm.protocols.ss7.commonapp.subscriberManagement.SupportedCamelPhasesImpl;
import org.restcomm.protocols.ss7.isup.message.parameter.CalledPartyNumber;
import org.restcomm.protocols.ss7.isup.message.parameter.CallingPartyCategory;
import org.restcomm.protocols.ss7.isup.message.parameter.CallingPartyNumber;
import org.restcomm.protocols.ss7.isup.message.parameter.CauseIndicators;
import org.restcomm.protocols.ss7.isup.message.parameter.GenericDigits;
import org.restcomm.protocols.ss7.isup.message.parameter.GenericNumber;
import org.restcomm.protocols.ss7.isup.message.parameter.LocationNumber;
import org.restcomm.protocols.ss7.isup.message.parameter.OriginalCalledNumber;
import org.restcomm.protocols.ss7.isup.message.parameter.RedirectingNumber;
import org.restcomm.protocols.ss7.isup.message.parameter.RedirectionInformation;
import org.restcomm.protocols.ss7.isup.message.parameter.UserServiceInformation;
import org.restcomm.protocols.ss7.isup.message.parameter.UserTeleserviceInformation;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class CAPParameterFactoryImpl implements CAPParameterFactory {
    @Override
    public CAPGprsReferenceNumber createCAPGprsReferenceNumber(Integer destinationReference, Integer originationReference) {
        return new CAPGprsReferenceNumberImpl(destinationReference, originationReference);
    }

    @Override
    public RouteSelectFailureSpecificInfo createRouteSelectFailureSpecificInfo(CauseIsup failureCause) {
        return new RouteSelectFailureSpecificInfoImpl(failureCause);
    }

    @Override
    public CauseIsup createCause(byte[] data) {
        return new CauseIsupImpl(data);
    }

    @Override
    public CauseIsup createCause(CauseIndicators causeIndicators) throws CAPException {
    	try {
    		return new CauseIsupImpl(causeIndicators);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(), ex.getCause());
    	}
    }

    @Override
    public DpSpecificCriteria createDpSpecificCriteria(Integer applicationTimer) {
        return new DpSpecificCriteriaImpl(applicationTimer);
    }

    @Override
    public DpSpecificCriteria createDpSpecificCriteria(MidCallControlInfo midCallControlInfo) {
        return new DpSpecificCriteriaImpl(midCallControlInfo);
    }

    @Override
    public DpSpecificCriteria createDpSpecificCriteria(DpSpecificCriteriaAlt dpSpecificCriteriaAlt) {
        return new DpSpecificCriteriaImpl(dpSpecificCriteriaAlt);
    }

    @Override
    public BCSMEvent createBCSMEvent(EventTypeBCSM eventTypeBCSM, MonitorMode monitorMode, LegID legID,
            DpSpecificCriteria dpSpecificCriteria, boolean automaticRearm) {
        return new BCSMEventImpl(eventTypeBCSM, monitorMode, legID, dpSpecificCriteria, automaticRearm);
    }

    @Override
    public CalledPartyBCDNumber createCalledPartyBCDNumber(AddressNature addressNature, NumberingPlan numberingPlan,
            String address) throws CAPException {
    	try {
    		return new CalledPartyBCDNumberImpl(addressNature, numberingPlan, address);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(), ex.getCause());
    	}
    }

    @Override
    public ExtensionField createExtensionField(Integer localCode, CriticalityType criticalityType, byte[] data, boolean isConstructed) {
        return new ExtensionFieldImpl(localCode, criticalityType, data, isConstructed);
    }

    @Override
    public ExtensionField createExtensionField(List<Long> globalCode, CriticalityType criticalityType, byte[] data, boolean isConstructed) {
        return new ExtensionFieldImpl(globalCode, criticalityType, data, isConstructed);
    }

    @Override
    public CAPINAPExtensions createCAPExtensions(List<ExtensionField> fieldsList) {
        return new CAPINAPExtensionsImpl(fieldsList);
    }

    @Override
    public CAMELAChBillingChargingCharacteristics createCAMELAChBillingChargingCharacteristics(long maxCallPeriodDuration, Boolean tone, CAPINAPExtensions extensions,
            Long tariffSwitchInterval) {
        return new CAMELAChBillingChargingCharacteristicsImpl(maxCallPeriodDuration,tone,extensions,tariffSwitchInterval);
    }

    @Override
    public CAMELAChBillingChargingCharacteristics createCAMELAChBillingChargingCharacteristics(long maxCallPeriodDuration, Boolean tone, boolean releaseIfdurationExceeded,
            Long tariffSwitchInterval, CAPINAPExtensions extensions) {
        return new CAMELAChBillingChargingCharacteristicsImpl(maxCallPeriodDuration,tone,releaseIfdurationExceeded,tariffSwitchInterval,extensions);
    }

    @Override
    public CAMELAChBillingChargingCharacteristics createCAMELAChBillingChargingCharacteristics(long maxCallPeriodDuration,
            boolean releaseIfdurationExceeded, Long tariffSwitchInterval, AudibleIndicator audibleIndicator,
            CAPINAPExtensions extensions) {
        return new CAMELAChBillingChargingCharacteristicsImpl(maxCallPeriodDuration, releaseIfdurationExceeded,
                tariffSwitchInterval, audibleIndicator, extensions);
    }

    @Override
    public DateAndTime createDateAndTime(int year, int month, int day, int hour, int minute, int second) {
        return new DateAndTimeImpl(year, month, day, hour, minute, second);
    }

    @Override
    public TimeAndTimezone createTimeAndTimezone(int year, int month, int day, int hour, int minute, int second, int timeZone) {
        return new TimeAndTimezoneImpl(year, month, day, hour, minute, second, timeZone);
    }

    @Override
    public BearerIsup createBearer(byte[] data) {
        return new BearerIsupImpl(data);
    }

    @Override
    public BearerIsup createBearer(UserServiceInformation userServiceInformation) throws CAPException {
    	try {
        	return new BearerIsupImpl(userServiceInformation);
	    }
		catch(APPException ex) {
			throw new CAPException(ex.getMessage(), ex.getCause());
		}
    }

    @Override
    public BearerCapability createBearerCapability(BearerIsup bearerCap) {
        return new BearerCapabilityImpl(bearerCap);
    }

    @Override
    public DigitsIsup createDigits_GenericNumber(byte[] data) {
        DigitsIsupImpl res = new DigitsIsupImpl(data);
        res.setIsGenericNumber();
        return res;
    }

    @Override
    public DigitsIsup createDigits_GenericDigits(byte[] data) {
        DigitsIsupImpl res = new DigitsIsupImpl(data);
        res.setIsGenericDigits();
        return res;
    }

    @Override
    public DigitsIsup createDigits_GenericNumber(GenericNumber genericNumber) throws CAPException {
    	try {
    		return new DigitsIsupImpl(genericNumber);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(), ex.getCause());
    	}
    }

    @Override
    public DigitsIsup createDigits_GenericDigits(GenericDigits genericDigits) throws CAPException {
        try {
        	return new DigitsIsupImpl(genericDigits);
        }
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(), ex.getCause());
    	}
    }

    @Override
    public CalledPartyNumberIsup createCalledPartyNumber(byte[] data) {
        return new CalledPartyNumberIsupImpl(data);
    }

    @Override
    public CalledPartyNumberIsup createCalledPartyNumber(CalledPartyNumber calledPartyNumber) throws CAPException {
    	try {
    		return new CalledPartyNumberIsupImpl(calledPartyNumber);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(), ex.getCause());
    	}
    }

    @Override
    public CallingPartyNumberIsup createCallingPartyNumber(byte[] data) {
        return new CallingPartyNumberIsupImpl(data);
    }

    @Override
    public CallingPartyNumberIsup createCallingPartyNumber(CallingPartyNumber callingPartyNumber) throws CAPException {
        try {
        	return new CallingPartyNumberIsupImpl(callingPartyNumber);
        }
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(), ex.getCause());
    	}
    }

    @Override
    public GenericNumberIsup createGenericNumber(byte[] data) {
        return new GenericNumberIsupImpl(data);
    }

    @Override
    public GenericNumberIsup createGenericNumber(GenericNumber genericNumber) throws CAPException {
    	try {
    		return new GenericNumberIsupImpl(genericNumber);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(), ex.getCause());
    	}
    }

    @Override
    public LocationNumberIsup createLocationNumber(byte[] data) {
        return new LocationNumberIsupImpl(data);
    }

    @Override
    public LocationNumberIsup createLocationNumber(LocationNumber locationNumber) throws CAPException {
    	try {
    		return new LocationNumberIsupImpl(locationNumber);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(), ex.getCause());
    	}
    }

    @Override
    public OriginalCalledNumberIsup createOriginalCalledNumber(byte[] data) {
        return new OriginalCalledNumberIsupImpl(data);
    }

    @Override
    public OriginalCalledNumberIsup createOriginalCalledNumber(OriginalCalledNumber originalCalledNumber) throws CAPException {
    	try {
    		return new OriginalCalledNumberIsupImpl(originalCalledNumber);
	    }
		catch(APPException ex) {
			throw new CAPException(ex.getMessage(), ex.getCause());
		}
    }

    @Override
    public RedirectingPartyIDIsup createRedirectingPartyID(byte[] data) {
        return new RedirectingPartyIDIsupImpl(data);
    }

    @Override
    public RedirectingPartyIDIsup createRedirectingPartyID(RedirectingNumber redirectingNumber) throws CAPException {
    	try {
    		return new RedirectingPartyIDIsupImpl(redirectingNumber);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(), ex.getCause());
    	}
    }

    @Override
    public OCalledPartyBusySpecificInfo createOCalledPartyBusySpecificInfo(CauseIsup busyCause) {
        return new OCalledPartyBusySpecificInfoImpl(busyCause);
    }

    @Override
    public OAbandonSpecificInfo createOAbandonSpecificInfo(boolean routeNotPermitted) {
        return new OAbandonSpecificInfoImpl(routeNotPermitted);
    }

    @Override
    public ONoAnswerSpecificInfo createONoAnswerSpecificInfo() {
        return new ONoAnswerSpecificInfoImpl();
    }

    @Override
    public OAnswerSpecificInfo createOAnswerSpecificInfo(CalledPartyNumberIsup destinationAddress, boolean orCall,
            boolean forwardedCall, ChargeIndicator chargeIndicator, ExtBasicServiceCode extBasicServiceCode,
            ExtBasicServiceCode extBasicServiceCode2) {
        return new OAnswerSpecificInfoImpl(destinationAddress, orCall, forwardedCall, chargeIndicator, extBasicServiceCode,
                extBasicServiceCode2);
    }

    @Override
    public ODisconnectSpecificInfo createODisconnectSpecificInfo(CauseIsup releaseCause) {
        return new ODisconnectSpecificInfoImpl(releaseCause);
    }

    @Override
    public TBusySpecificInfo createTBusySpecificInfo(CauseIsup busyCause, boolean callForwarded, boolean routeNotPermitted,
            CalledPartyNumberIsup forwardingDestinationNumber) {
        return new TBusySpecificInfoImpl(busyCause, callForwarded, routeNotPermitted, forwardingDestinationNumber);
    }

    @Override
    public TNoAnswerSpecificInfo createTNoAnswerSpecificInfo(boolean callForwarded,
            CalledPartyNumberIsup forwardingDestinationNumber) {
        return new TNoAnswerSpecificInfoImpl(callForwarded, forwardingDestinationNumber);
    }

    @Override
    public TAnswerSpecificInfo createTAnswerSpecificInfo(CalledPartyNumberIsup destinationAddress, boolean orCall,
            boolean forwardedCall, ChargeIndicator chargeIndicator, ExtBasicServiceCode extBasicServiceCode,
            ExtBasicServiceCode extBasicServiceCode2) {
        return new TAnswerSpecificInfoImpl(destinationAddress, orCall, forwardedCall, chargeIndicator, extBasicServiceCode,
                extBasicServiceCode2);
    }

    @Override
    public TDisconnectSpecificInfo createTDisconnectSpecificInfo(CauseIsup releaseCause) {
        return new TDisconnectSpecificInfoImpl(releaseCause);
    }

    @Override
    public DestinationRoutingAddress createDestinationRoutingAddress(List<CalledPartyNumberIsup> calledPartyNumber) {
        return new DestinationRoutingAddressImpl(calledPartyNumber);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(
            RouteSelectFailureSpecificInfo routeSelectFailureSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(routeSelectFailureSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(
            OCalledPartyBusySpecificInfo oCalledPartyBusySpecificInfo) {
        return new EventSpecificInformationBCSMImpl(oCalledPartyBusySpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(ONoAnswerSpecificInfo oNoAnswerSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(oNoAnswerSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(OAnswerSpecificInfo oAnswerSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(oAnswerSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(OMidCallSpecificInfo oMidCallSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(oMidCallSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(ODisconnectSpecificInfo oDisconnectSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(oDisconnectSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(TBusySpecificInfo tBusySpecificInfo) {
        return new EventSpecificInformationBCSMImpl(tBusySpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(TNoAnswerSpecificInfo tNoAnswerSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(tNoAnswerSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(TAnswerSpecificInfo tAnswerSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(tAnswerSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(TMidCallSpecificInfo tMidCallSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(tMidCallSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(TDisconnectSpecificInfo tDisconnectSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(tDisconnectSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(OTermSeizedSpecificInfo oTermSeizedSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(oTermSeizedSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(CallAcceptedSpecificInfo callAcceptedSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(callAcceptedSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(OAbandonSpecificInfo oAbandonSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(oAbandonSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(
            OChangeOfPositionSpecificInfo oChangeOfPositionSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(oChangeOfPositionSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(
            TChangeOfPositionSpecificInfo tChangeOfPositionSpecificInfo) {
        return new EventSpecificInformationBCSMImpl(tChangeOfPositionSpecificInfo);
    }

    @Override
    public EventSpecificInformationBCSM createEventSpecificInformationBCSM(DpSpecificInfoAlt dpSpecificInfoAlt) {
        return new EventSpecificInformationBCSMImpl(dpSpecificInfoAlt);
    }

    @Override
    public RequestedInformation createRequestedInformation_CallAttemptElapsedTime(int callAttemptElapsedTimeValue) {
        return new RequestedInformationImpl(RequestedInformationType.callAttemptElapsedTime, callAttemptElapsedTimeValue);
    }

    @Override
    public RequestedInformation createRequestedInformation_CallConnectedElapsedTime(int callConnectedElapsedTimeValue) {
        return new RequestedInformationImpl(RequestedInformationType.callConnectedElapsedTime, callConnectedElapsedTimeValue);
    }

    @Override
    public RequestedInformation createRequestedInformation_CallStopTime(DateAndTime callStopTimeValue) {
        return new RequestedInformationImpl(callStopTimeValue);
    }

    @Override
    public RequestedInformation createRequestedInformation_ReleaseCause(CauseIsup releaseCauseValue) {
        return new RequestedInformationImpl(releaseCauseValue);
    }

    @Override
    public TimeDurationChargingResult createTimeDurationChargingResult(LegType partyToCharge,
            TimeInformation timeInformation, boolean legActive, boolean callLegReleasedAtTcpExpiry, CAPINAPExtensions extensions,
            AChChargingAddress aChChargingAddress) {
        return new TimeDurationChargingResultImpl(partyToCharge, timeInformation, legActive, callLegReleasedAtTcpExpiry,
                extensions, aChChargingAddress);
    }

    @Override
    public TimeIfTariffSwitch createTimeIfTariffSwitch(int timeSinceTariffSwitch, Integer tariffSwitchInterval) {
        return new TimeIfTariffSwitchImpl(timeSinceTariffSwitch, tariffSwitchInterval);
    }

    @Override
    public TimeInformation createTimeInformation(int timeIfNoTariffSwitch) {
        return new TimeInformationImpl(timeIfNoTariffSwitch);
    }

    @Override
    public TimeInformation createTimeInformation(TimeIfTariffSwitch timeIfTariffSwitch) {
        return new TimeInformationImpl(timeIfTariffSwitch);
    }

    @Override
    public IPSSPCapabilities createIPSSPCapabilities(boolean IPRoutingAddressSupported, boolean VoiceBackSupported,
            boolean VoiceInformationSupportedViaSpeechRecognition, boolean VoiceInformationSupportedViaVoiceRecognition,
            boolean GenerationOfVoiceAnnouncementsFromTextSupported, byte[] extraData) {
        return new IPSSPCapabilitiesImpl(IPRoutingAddressSupported, VoiceBackSupported,
                VoiceInformationSupportedViaSpeechRecognition, VoiceInformationSupportedViaVoiceRecognition,
                GenerationOfVoiceAnnouncementsFromTextSupported, extraData);
    }

    @Override
    public InitialDPArgExtension createInitialDPArgExtension(NACarrierInformation naCarrierInformation, ISDNAddressString gmscAddress) {
        return new InitialDPArgExtensionImpl(naCarrierInformation, gmscAddress);
    }

    @Override
    public InitialDPArgExtension createInitialDPArgExtension(ISDNAddressString gmscAddress,
            CalledPartyNumberIsup forwardingDestinationNumber, MSClassmark2 msClassmark2, IMEI imei,
            SupportedCamelPhases supportedCamelPhases, OfferedCamel4Functionalities offeredCamel4Functionalities,
            BearerCapability bearerCapability2, ExtBasicServiceCode extBasicServiceCode2,
            HighLayerCompatibilityIsup highLayerCompatibility2, LowLayerCompatibility lowLayerCompatibility,
            LowLayerCompatibility lowLayerCompatibility2, boolean enhancedDialledServicesAllowed, UUData uuData,
            boolean collectInformationAllowed, boolean releaseCallArgExtensionAllowed) {
        return new InitialDPArgExtensionImpl(gmscAddress, forwardingDestinationNumber, msClassmark2, imei,
                supportedCamelPhases, offeredCamel4Functionalities, bearerCapability2, extBasicServiceCode2,
                highLayerCompatibility2, lowLayerCompatibility, lowLayerCompatibility2, enhancedDialledServicesAllowed, uuData,
                collectInformationAllowed, releaseCallArgExtensionAllowed);
    }

    @Override
    public AlertingPatternWrapper createAlertingPattern(AlertingPattern alertingPattern) {
        return new AlertingPatternWrapperImpl(alertingPattern);
    }

    @Override
    public AlertingPatternWrapper createAlertingPattern(byte[] data) {
        return new AlertingPatternWrapperImpl(data);
    }

    @Override
    public NAOliInfo createNAOliInfo(int value) {
        return new NAOliInfoImpl(value);
    }

    @Override
    public ScfID createScfID(byte[] data) {
        return new ScfIDImpl(data);
    }

    @Override
    public ServiceInteractionIndicatorsTwo createServiceInteractionIndicatorsTwo(
            ForwardServiceInteractionInd forwardServiceInteractionInd,
            BackwardServiceInteractionInd backwardServiceInteractionInd,
            BothwayThroughConnectionInd bothwayThroughConnectionInd, ConnectedNumberTreatmentInd connectedNumberTreatmentInd,
            boolean nonCUGCall, HoldTreatmentIndicator holdTreatmentIndicator, CwTreatmentIndicator cwTreatmentIndicator,
            EctTreatmentIndicator ectTreatmentIndicator) {
        return new ServiceInteractionIndicatorsTwoImpl(forwardServiceInteractionInd, backwardServiceInteractionInd,
                bothwayThroughConnectionInd, connectedNumberTreatmentInd, nonCUGCall, holdTreatmentIndicator,
                cwTreatmentIndicator, ectTreatmentIndicator);
    }

    @Override
    public FCIBCCCAMELSequence1 createFCIBCCCAMELsequence1(FreeFormatData freeFormatData, LegType partyToCharge,
            AppendFreeFormatData appendFreeFormatData) {
        return new FCIBCCCAMELSequence1Impl(freeFormatData, partyToCharge, appendFreeFormatData);
    }

    @Override
    public CAMELSCIBillingChargingCharacteristicsAlt createCAMELSCIBillingChargingCharacteristicsAlt() {
        return new CAMELSCIBillingChargingCharacteristicsAltImpl();
    }

    @Override
    public CAI_GSM0224 createCAI_GSM0224(Integer e1, Integer e2, Integer e3, Integer e4, Integer e5, Integer e6, Integer e7) {
        return new CAI_GSM0224Impl(e1, e2, e3, e4, e5, e6, e7);
    }

    @Override
    public AOCSubsequent createAOCSubsequent(CAI_GSM0224 cai_GSM0224, Integer tariffSwitchInterval) {
        return new AOCSubsequentImpl(cai_GSM0224, tariffSwitchInterval);
    }

    @Override
    public AOCBeforeAnswer createAOCBeforeAnswer(CAI_GSM0224 aocInitial, AOCSubsequent aocSubsequent) {
        return new AOCBeforeAnswerImpl(aocInitial, aocSubsequent);
    }

    @Override
    public SCIBillingChargingCharacteristics createSCIBillingChargingCharacteristics(AOCBeforeAnswer aocBeforeAnswer) {
        return new SCIBillingChargingCharacteristicsImpl(aocBeforeAnswer);
    }

    @Override
    public SCIBillingChargingCharacteristics createSCIBillingChargingCharacteristics(AOCSubsequent aocSubsequent) {
        return new SCIBillingChargingCharacteristicsImpl(aocSubsequent);
    }

    @Override
    public SCIBillingChargingCharacteristics createSCIBillingChargingCharacteristics(
            CAMELSCIBillingChargingCharacteristicsAlt aocExtension) {
        return new SCIBillingChargingCharacteristicsImpl(aocExtension);
    }

    @Override
    public VariablePartPrice createVariablePartPrice(byte[] data) {
        return new VariablePartPriceImpl(data);
    }

    @Override
    public VariablePartPrice createVariablePartPrice(double price) {
        return new VariablePartPriceImpl(price);
    }

    @Override
    public VariablePartPrice createVariablePartPrice(int integerPart, int hundredthPart) {
        return new VariablePartPriceImpl(integerPart, hundredthPart);
    }

    @Override
    public VariablePartDate createVariablePartDate(byte[] data) {
        return new VariablePartDateImpl(data);
    }

    @Override
    public VariablePartDate createVariablePartDate(int year, int month, int day) {
        return new VariablePartDateImpl(year, month, day);
    }

    @Override
    public VariablePartTime createVariablePartTime(byte[] data) {
        return new VariablePartTimeImpl(data);
    }

    @Override
    public VariablePartTime createVariablePartTime(int hour, int minute) {
        return new VariablePartTimeImpl(hour, minute);
    }

    @Override
    public VariablePart createVariablePart(Integer integer) {
        return new VariablePartImpl(integer);
    }

    @Override
    public VariablePart createVariablePart(DigitsIsup number) {
        return new VariablePartImpl(number);
    }

    @Override
    public VariablePart createVariablePart(VariablePartTime time) {
        return new VariablePartImpl(time);
    }

    @Override
    public VariablePart createVariablePart(VariablePartDate date) {
        return new VariablePartImpl(date);
    }

    @Override
    public VariablePart createVariablePart(VariablePartPrice price) {
        return new VariablePartImpl(price);
    }

    @Override
    public MessageIDText createMessageIDText(String messageContent, byte[] attributes) {
        return new MessageIDTextImpl(messageContent, attributes);
    }

    @Override
    public VariableMessage createVariableMessage(int elementaryMessageID, List<VariablePart> variableParts) {
        return new VariableMessageImpl(elementaryMessageID, variableParts);
    }

    @Override
    public MessageID createMessageID(Integer elementaryMessageID) {
        return new MessageIDImpl(elementaryMessageID);
    }

    @Override
    public MessageID createMessageID(MessageIDText text) {
        return new MessageIDImpl(text);
    }

    @Override
    public MessageID createMessageID(List<Integer> elementaryMessageIDs) {
        return new MessageIDImpl(elementaryMessageIDs);
    }

    @Override
    public MessageID createMessageID(VariableMessage variableMessage) {
        return new MessageIDImpl(variableMessage);
    }

    @Override
    public InbandInfo createInbandInfo(MessageID messageID, Integer numberOfRepetitions, Integer duration, Integer interval) {
        return new InbandInfoImpl(messageID, numberOfRepetitions, duration, interval);
    }

    @Override
    public Tone createTone(int toneID, Integer duration) {
        return new ToneImpl(toneID, duration);
    }

    @Override
    public InformationToSend createInformationToSend(InbandInfo inbandInfo) {
        return new InformationToSendImpl(inbandInfo);
    }

    @Override
    public InformationToSend createInformationToSend(Tone tone) {
        return new InformationToSendImpl(tone);
    }

    @Override
    public CollectedDigits createCollectedDigits(Integer minimumNbOfDigits, int maximumNbOfDigits, byte[] endOfReplyDigit,
            byte[] cancelDigit, byte[] startDigit, Integer firstDigitTimeOut, Integer interDigitTimeOut,
            ErrorTreatment errorTreatment, Boolean interruptableAnnInd, Boolean voiceInformation, Boolean voiceBack) {
        return new CollectedDigitsImpl(minimumNbOfDigits, maximumNbOfDigits, endOfReplyDigit, cancelDigit, startDigit,
                firstDigitTimeOut, interDigitTimeOut, errorTreatment, interruptableAnnInd, voiceInformation, voiceBack);
    }

    @Override
    public CollectedInfo createCollectedInfo(CollectedDigits collectedDigits) {
        return new CollectedInfoImpl(collectedDigits);
    }

    @Override
    public CallSegmentToCancel createCallSegmentToCancel(Integer invokeID, Integer callSegmentID) {
        return new CallSegmentToCancelImpl(invokeID, callSegmentID);
    }

    @Override
    public AccessPointName createAccessPointName(byte[] data) {
        return new AccessPointNameImpl(data);
    }

    @Override
    public AOCGPRS createAOCGPRS(CAI_GSM0224 aocInitial, AOCSubsequent aocSubsequent) {
        return new AOCGPRSImpl(aocInitial, aocSubsequent);
    }

    @Override
    public CAMELFCIGPRSBillingChargingCharacteristics createCAMELFCIGPRSBillingChargingCharacteristics(
            FCIBCCCAMELSequence1Gprs fcIBCCCAMELsequence1) {

        return new CAMELFCIGPRSBillingChargingCharacteristicsImpl(fcIBCCCAMELsequence1);
    }

    @Override
    public CAMELSCIGPRSBillingChargingCharacteristics createCAMELSCIGPRSBillingChargingCharacteristics(AOCGPRS aocGPRS,
            PDPID pdpID) {

        return new CAMELSCIGPRSBillingChargingCharacteristicsImpl(aocGPRS, pdpID);
    }

    @Override
    public ChargingCharacteristics createChargingCharacteristics(long maxTransferredVolume) {
        return new ChargingCharacteristicsImpl(maxTransferredVolume);
    }

    @Override
    public ChargingCharacteristics createChargingCharacteristics(int maxElapsedTime) {
        return new ChargingCharacteristicsImpl(maxElapsedTime);
    }

    @Override
    public ChargingResult createChargingResult(TransferredVolume transferredVolume) {
        return new ChargingResultImpl(transferredVolume);
    }

    @Override
    public ChargingResult createChargingResult(ElapsedTime elapsedTime) {
        return new ChargingResultImpl(elapsedTime);
    }

    @Override
    public ChargingRollOver createChargingRollOver(ElapsedTimeRollOver elapsedTimeRollOver) {
        return new ChargingRollOverImpl(elapsedTimeRollOver);
    }

    @Override
    public ChargingRollOver createChargingRollOver(TransferredVolumeRollOver transferredVolumeRollOver) {
        return new ChargingRollOverImpl(transferredVolumeRollOver);
    }

    @Override
    public ElapsedTime createElapsedTime(Integer timeGPRSIfNoTariffSwitch) {
        return new ElapsedTimeImpl(timeGPRSIfNoTariffSwitch);
    }

    @Override
    public ElapsedTime createElapsedTime(TimeGPRSIfTariffSwitch timeGPRSIfTariffSwitch) {
        return new ElapsedTimeImpl(timeGPRSIfTariffSwitch);
    }

    @Override
    public ElapsedTimeRollOver createElapsedTimeRollOver(Integer roTimeGPRSIfNoTariffSwitch) {
        return new ElapsedTimeRollOverImpl(roTimeGPRSIfNoTariffSwitch);
    }

    @Override
    public ElapsedTimeRollOver createElapsedTimeRollOver(ROTimeGPRSIfTariffSwitch roTimeGPRSIfTariffSwitch) {
        return new ElapsedTimeRollOverImpl(roTimeGPRSIfTariffSwitch);
    }

    @Override
    public EndUserAddress createEndUserAddress(PDPTypeOrganization pdpTypeOrganization, PDPTypeNumber pdpTypeNumber,
            PDPAddress pdpAddress) {
        return new EndUserAddressImpl(pdpTypeOrganization, pdpTypeNumber, pdpAddress);
    }

    @Override
    public FCIBCCCAMELSequence1Gprs createFCIBCCCAMELsequence1(
            FreeFormatDataGprs freeFormatData, PDPID pdpID, AppendFreeFormatData appendFreeFormatData) {
        return new FCIBCCCAMELSequence1GprsImpl(freeFormatData, pdpID,
                appendFreeFormatData);
    }

    @Override
    public FreeFormatDataGprs createFreeFormatDataGprs(byte[] data) {
        return new FreeFormatDataGprsImpl(data);
    }

    @Override
    public GPRSCause createGPRSCause(int data) {
        return new GPRSCauseImpl(data);
    }

    @Override
    public GPRSEvent createGPRSEvent(GPRSEventType gprsEventType, MonitorMode monitorMode) {
        return new GPRSEventImpl(gprsEventType, monitorMode);
    }

    @Override
    public GPRSEventSpecificInformation createGPRSEventSpecificInformation(LocationInformationGPRS locationInformationGPRS) {
        return new GPRSEventSpecificInformationImpl(locationInformationGPRS);
    }

    @Override
    public GPRSEventSpecificInformation createGPRSEventSpecificInformation(
            PdpContextChangeOfPositionSpecificInformation pdpContextchangeOfPositionSpecificInformation) {
        return new GPRSEventSpecificInformationImpl(pdpContextchangeOfPositionSpecificInformation);
    }

    @Override
    public GPRSEventSpecificInformation createGPRSEventSpecificInformation(DetachSpecificInformation detachSpecificInformation) {
        return new GPRSEventSpecificInformationImpl(detachSpecificInformation);
    }

    @Override
    public GPRSEventSpecificInformation createGPRSEventSpecificInformation(
            DisconnectSpecificInformation disconnectSpecificInformation) {
        return new GPRSEventSpecificInformationImpl(disconnectSpecificInformation);
    }

    @Override
    public GPRSEventSpecificInformation createGPRSEventSpecificInformation(
            PDPContextEstablishmentSpecificInformation pdpContextEstablishmentSpecificInformation) {
        return new GPRSEventSpecificInformationImpl(pdpContextEstablishmentSpecificInformation);
    }

    @Override
    public GPRSEventSpecificInformation createGPRSEventSpecificInformation(
            PDPContextEstablishmentAcknowledgementSpecificInformation pdpContextEstablishmentAcknowledgementSpecificInformation) {
        return new GPRSEventSpecificInformationImpl(pdpContextEstablishmentAcknowledgementSpecificInformation);
    }

    @Override
    public GPRSQoSExtension createGPRSQoSExtension(Ext2QoSSubscribed supplementToLongQoSFormat) {
        return new GPRSQoSExtensionImpl(supplementToLongQoSFormat);
    }

    @Override
    public GPRSQoS createGPRSQoS(QoSSubscribed shortQoSFormat) {
        return new GPRSQoSImpl(shortQoSFormat);
    }

    @Override
    public GPRSQoS createGPRSQoS(ExtQoSSubscribed longQoSFormat) {
        return new GPRSQoSImpl(longQoSFormat);
    }

    @Override
    public PDPAddress createPDPAddress(byte[] data) {
        return new PDPAddressImpl(data);
    }

    @Override
    public PDPID createPDPID(int data) {
        return new PDPIDImpl(data);
    }

    @Override
    public PDPTypeNumber createPDPTypeNumber(int data) {
        return new PDPTypeNumberImpl(data);
    }

    @Override
    public PDPTypeNumber createPDPTypeNumber(PDPTypeNumberValue value) {
        return new PDPTypeNumberImpl(value);
    }

    @Override
    public PDPTypeOrganization createPDPTypeOrganization(int data) {
        return new PDPTypeOrganizationImpl(data);
    }

    @Override
    public PDPTypeOrganization createPDPTypeOrganization(PDPTypeOrganizationValue value) {
        return new PDPTypeOrganizationImpl(value);
    }

    @Override
    public QualityOfService createQualityOfService(GPRSQoS requestedQoS, GPRSQoS subscribedQoS, GPRSQoS negotiatedQoS,
            GPRSQoSExtension requestedQoSExtension, GPRSQoSExtension subscribedQoSExtension,
            GPRSQoSExtension negotiatedQoSExtension) {
        return new QualityOfServiceImpl(requestedQoS, subscribedQoS, negotiatedQoS, requestedQoSExtension,
                subscribedQoSExtension, negotiatedQoSExtension);
    }

    @Override
    public ROTimeGPRSIfTariffSwitch createROTimeGPRSIfTariffSwitch(Integer roTimeGPRSSinceLastTariffSwitch,
            Integer roTimeGPRSTariffSwitchInterval) {
        return new ROTimeGPRSIfTariffSwitchImpl(roTimeGPRSSinceLastTariffSwitch, roTimeGPRSTariffSwitchInterval);
    }

    @Override
    public ROVolumeIfTariffSwitch createROVolumeIfTariffSwitch(Integer roVolumeSinceLastTariffSwitch,
            Integer roVolumeTariffSwitchInterval) {
        return new ROVolumeIfTariffSwitchImpl(roVolumeSinceLastTariffSwitch, roVolumeTariffSwitchInterval);
    }

    @Override
    public SGSNCapabilities createSGSNCapabilities(int data) {
        return new SGSNCapabilitiesImpl(data);
    }

    @Override
    public SGSNCapabilities createSGSNCapabilities(boolean aoCSupportedBySGSN) {
        return new SGSNCapabilitiesImpl(aoCSupportedBySGSN);
    }

    @Override
    public TimeGPRSIfTariffSwitch createTimeGPRSIfTariffSwitch(int timeGPRSSinceLastTariffSwitch,
            Integer timeGPRSTariffSwitchInterval) {
        return new TimeGPRSIfTariffSwitchImpl(timeGPRSSinceLastTariffSwitch, timeGPRSTariffSwitchInterval);
    }

    @Override
    public TransferredVolume createTransferredVolume(Long volumeIfNoTariffSwitch) {
        return new TransferredVolumeImpl(volumeIfNoTariffSwitch);
    }

    @Override
    public TransferredVolume createTransferredVolume(VolumeIfTariffSwitch volumeIfTariffSwitch) {
        return new TransferredVolumeImpl(volumeIfTariffSwitch);
    }

    @Override
    public TransferredVolumeRollOver createTransferredVolumeRollOver(Integer roVolumeIfNoTariffSwitch) {
        return new TransferredVolumeRollOverImpl(roVolumeIfNoTariffSwitch);
    }

    @Override
    public TransferredVolumeRollOver createTransferredVolumeRollOver(ROVolumeIfTariffSwitch roVolumeIfTariffSwitch) {
        return new TransferredVolumeRollOverImpl(roVolumeIfTariffSwitch);
    }

    @Override
    public VolumeIfTariffSwitch createVolumeIfTariffSwitch(long volumeSinceLastTariffSwitch, Long volumeTariffSwitchInterval) {
        return new VolumeIfTariffSwitchImpl(volumeSinceLastTariffSwitch, volumeTariffSwitchInterval);
    }

    @Override
    public DetachSpecificInformation createDetachSpecificInformation(InitiatingEntity initiatingEntity,
            boolean routeingAreaUpdate) {
        return new DetachSpecificInformationImpl(initiatingEntity, routeingAreaUpdate);
    }

    @Override
    public DisconnectSpecificInformation createDisconnectSpecificInformation(InitiatingEntity initiatingEntity,
            boolean routeingAreaUpdate) {
        return new DisconnectSpecificInformationImpl(initiatingEntity, routeingAreaUpdate);
    }

    @Override
    public PdpContextChangeOfPositionSpecificInformation createPdpContextchangeOfPositionSpecificInformation(
            AccessPointName accessPointName, GPRSChargingID chargingID, LocationInformationGPRS locationInformationGPRS,
            EndUserAddress endUserAddress, QualityOfService qualityOfService, TimeAndTimezone timeAndTimezone,
            GSNAddress gsnAddress) {
        return new PdpContextChangeOfPositionSpecificInformationImpl(accessPointName, chargingID, locationInformationGPRS,
                endUserAddress, qualityOfService, timeAndTimezone, gsnAddress);
    }

    @Override
    public PDPContextEstablishmentAcknowledgementSpecificInformation createPDPContextEstablishmentAcknowledgementSpecificInformation(
            AccessPointName accessPointName, GPRSChargingID chargingID, LocationInformationGPRS locationInformationGPRS,
            EndUserAddress endUserAddress, QualityOfService qualityOfService, TimeAndTimezone timeAndTimezone,
            GSNAddress gsnAddress) {
        return new PDPContextEstablishmentAcknowledgementSpecificInformationImpl(accessPointName, chargingID,
                locationInformationGPRS, endUserAddress, qualityOfService, timeAndTimezone, gsnAddress);
    }

    @Override
    public PDPContextEstablishmentSpecificInformation createPDPContextEstablishmentSpecificInformation(
            AccessPointName accessPointName, EndUserAddress endUserAddress, QualityOfService qualityOfService,
            LocationInformationGPRS locationInformationGPRS, TimeAndTimezone timeAndTimezone,
            PDPInitiationType pdpInitiationType, boolean secondaryPDPContext) {
        return new PDPContextEstablishmentSpecificInformationImpl(accessPointName, endUserAddress, qualityOfService,
                locationInformationGPRS, timeAndTimezone, pdpInitiationType, secondaryPDPContext);
    }

    @Override
    public TPValidityPeriod createTPValidityPeriod(byte[] data) {
        return new TPValidityPeriodImpl(data);
    }

    @Override
    public TPShortMessageSpecificInfo createTPShortMessageSpecificInfo(int data) {
        return new TPShortMessageSpecificInfoImpl(data);
    }

    @Override
    public TPProtocolIdentifier createTPProtocolIdentifier(int data) {
        return new TPProtocolIdentifierImpl(data);
    }

    @Override
    public TPDataCodingScheme createTPDataCodingScheme(int data) {
        return new TPDataCodingSchemeImpl(data);
    }

    @Override
    public SMSEvent createSMSEvent(EventTypeSMS eventTypeSMS, MonitorMode monitorMode) {
        return new SMSEventImpl(eventTypeSMS, monitorMode);
    }

    @Override
    public SMSAddressString createSMSAddressString(AddressNature addressNature, NumberingPlan numberingPlan, String address) {
        return new SMSAddressStringImpl(addressNature, numberingPlan, address);
    }

    @Override
    public RPCause createRPCause(int data) {
        return new RPCauseImpl(data);
    }

    @Override
    public MTSMSCause createMTSMSCause(int data) {
        return new MTSMSCauseImpl(data);
    }

    @Override
    public FreeFormatData createFreeFormatData(byte[] data) {
        return new FreeFormatDataImpl(data);
    }

    @Override
    public FCIBCCCAMELSequence1SMS createFCIBCCCAMELsequence1(
            FreeFormatDataSMS freeFormatData,
            AppendFreeFormatData appendFreeFormatData) {
        return new FCIBCCCAMELSequence1SMSImpl(freeFormatData,
                appendFreeFormatData);
    }

    @Override
    public EventSpecificInformationSMS createEventSpecificInformationSMS(
            OSmsFailureSpecificInfo oSmsFailureSpecificInfo) {
        return new EventSpecificInformationSMSImpl(oSmsFailureSpecificInfo);
    }

    @Override
    public EventSpecificInformationSMS createEventSpecificInformationSMS(
            OSmsSubmissionSpecificInfo oSmsSubmissionSpecificInfo) {
        return new EventSpecificInformationSMSImpl(oSmsSubmissionSpecificInfo);
    }

    @Override
    public EventSpecificInformationSMS createEventSpecificInformationSMS(
            TSmsFailureSpecificInfo tSmsFailureSpecificInfo) {
        return new EventSpecificInformationSMSImpl(tSmsFailureSpecificInfo);
    }

    @Override
    public EventSpecificInformationSMS createEventSpecificInformationSMS(
            TSmsDeliverySpecificInfo tSmsDeliverySpecificInfo) {
        return new EventSpecificInformationSMSImpl(tSmsDeliverySpecificInfo);
    }

    @Override
    public FreeFormatDataSMS createFreeFormatDataSMS(byte[] data) {
        return new FreeFormatDataSMSImpl(data);
    }

    @Override
    public OSmsFailureSpecificInfo createOSmsFailureSpecificInfo(MOSMSCause failureCause) {
        return new OSmsFailureSpecificInfoImpl(failureCause);
    }

    @Override
    public OSmsSubmissionSpecificInfo createOSmsSubmissionSpecificInfo() {
        return new OSmsSubmissionSpecificInfoImpl();
    }

    @Override
    public TSmsFailureSpecificInfo createTSmsFailureSpecificInfo(MTSMSCause failureCause) {
        return new TSmsFailureSpecificInfoImpl(failureCause);
    }

    @Override
    public TSmsDeliverySpecificInfo createTSmsDeliverySpecificInfo() {
        return new TSmsDeliverySpecificInfoImpl();
    }

    @Override
    public LegOrCallSegment createLegOrCallSegment(Integer callSegmentID) {
        return new LegOrCallSegmentImpl(callSegmentID);
    }

    @Override
    public LegOrCallSegment createLegOrCallSegment(LegID legID) {
        return new LegOrCallSegmentImpl(legID);
    }

    @Override
    public ChargeIndicator createChargeIndicator(int data) {
        return new ChargeIndicatorImpl(data);
    }

    @Override
    public ChargeIndicator createChargeIndicator(ChargeIndicatorValue value) {
        return new ChargeIndicatorImpl(value);
    }

    @Override
    public BackwardServiceInteractionInd createBackwardServiceInteractionInd(ConferenceTreatmentIndicator conferenceTreatmentIndicator,
            CallCompletionTreatmentIndicator callCompletionTreatmentIndicator) {
        return new BackwardServiceInteractionIndImpl(conferenceTreatmentIndicator, callCompletionTreatmentIndicator);
    }

    @Override
    public Carrier createCarrier(byte[] data) {
        return new CarrierImpl(data);
    }

    @Override
    public ForwardServiceInteractionInd createForwardServiceInteractionInd(ConferenceTreatmentIndicator conferenceTreatmentIndicator,
            CallDiversionTreatmentIndicator callDiversionTreatmentIndicator, CallingPartyRestrictionIndicator callingPartyRestrictionIndicator) {
        return new ForwardServiceInteractionIndImpl(conferenceTreatmentIndicator, callDiversionTreatmentIndicator, callingPartyRestrictionIndicator);
    }

    @Override
    public LowLayerCompatibility createLowLayerCompatibility(byte[] data) {
        return new LowLayerCompatibilityImpl(data);
    }

    @Override
    public MidCallEvents createMidCallEvents_Completed(DigitsIsup dtmfDigits) {
        return new MidCallEventsImpl(dtmfDigits, true);
    }

    @Override
    public MidCallEvents createMidCallEvents_TimeOut(DigitsIsup dtmfDigits) {
        return new MidCallEventsImpl(dtmfDigits, false);
    }

    @Override
    public OMidCallSpecificInfo createOMidCallSpecificInfo(MidCallEvents midCallEvents) {
        return new OMidCallSpecificInfoImpl(midCallEvents);
    }

    @Override
    public TMidCallSpecificInfo createTMidCallSpecificInfo(MidCallEvents midCallEvents) {
        return new TMidCallSpecificInfoImpl(midCallEvents);
    }

    @Override
    public OTermSeizedSpecificInfo createOTermSeizedSpecificInfo(LocationInformation locationInformation) {
        return new OTermSeizedSpecificInfoImpl(locationInformation);
    }

    @Override
    public CallAcceptedSpecificInfo createCallAcceptedSpecificInfo(LocationInformation locationInformation) {
        return new CallAcceptedSpecificInfoImpl(locationInformation);
    }

    @Override
    public MetDPCriterionAlt createMetDPCriterionAlt() {
        return new MetDPCriterionAltImpl();
    }

    @Override
    public MetDPCriterion createMetDPCriterion_enteringCellGlobalId(CellGlobalIdOrServiceAreaIdFixedLength value) {
        return new MetDPCriterionImpl(value, MetDPCriterion.CellGlobalIdOrServiceAreaIdFixedLength_Option.enteringCellGlobalId);
    }

    @Override
    public MetDPCriterion createMetDPCriterion_leavingCellGlobalId(CellGlobalIdOrServiceAreaIdFixedLength value) {
        return new MetDPCriterionImpl(value, MetDPCriterion.CellGlobalIdOrServiceAreaIdFixedLength_Option.leavingCellGlobalId);
    }

    @Override
    public MetDPCriterion createMetDPCriterion_enteringServiceAreaId(CellGlobalIdOrServiceAreaIdFixedLength value) {
        return new MetDPCriterionImpl(value, MetDPCriterion.CellGlobalIdOrServiceAreaIdFixedLength_Option.enteringServiceAreaId);
    }

    @Override
    public MetDPCriterion createMetDPCriterion_leavingServiceAreaId(CellGlobalIdOrServiceAreaIdFixedLength value) {
        return new MetDPCriterionImpl(value, MetDPCriterion.CellGlobalIdOrServiceAreaIdFixedLength_Option.leavingServiceAreaId);
    }

    @Override
    public MetDPCriterion createMetDPCriterion_enteringLocationAreaId(LAIFixedLength value) {
        return new MetDPCriterionImpl(value, MetDPCriterion.LAIFixedLength_Option.enteringLocationAreaId);
    }

    @Override
    public MetDPCriterion createMetDPCriterion_leavingLocationAreaId(LAIFixedLength value) {
        return new MetDPCriterionImpl(value, MetDPCriterion.LAIFixedLength_Option.leavingLocationAreaId);
    }

    @Override
    public MetDPCriterion createMetDPCriterion_interSystemHandOverToUMTS() {
        return new MetDPCriterionImpl(MetDPCriterion.Boolean_Option.interSystemHandOverToUMTS);
    }

    @Override
    public MetDPCriterion createMetDPCriterion_interSystemHandOverToGSM() {
        return new MetDPCriterionImpl(MetDPCriterion.Boolean_Option.interSystemHandOverToGSM);
    }

    @Override
    public MetDPCriterion createMetDPCriterion_interPLMNHandOver() {
        return new MetDPCriterionImpl(MetDPCriterion.Boolean_Option.interPLMNHandOver);
    }

    @Override
    public MetDPCriterion createMetDPCriterion_interMSCHandOver() {
        return new MetDPCriterionImpl(MetDPCriterion.Boolean_Option.interMSCHandOver);
    }

    @Override
    public MetDPCriterion createMetDPCriterion(MetDPCriterionAlt metDPCriterionAlt) {
        return new MetDPCriterionImpl(metDPCriterionAlt);
    }

    @Override
    public OChangeOfPositionSpecificInfo createOChangeOfPositionSpecificInfo(LocationInformation locationInformation,
            List<MetDPCriterion> metDPCriteriaList) {
        return new OChangeOfPositionSpecificInfoImpl(locationInformation, metDPCriteriaList);
    }

    @Override
    public TChangeOfPositionSpecificInfo createTChangeOfPositionSpecificInfo(LocationInformation locationInformation,
            List<MetDPCriterion> metDPCriteriaList) {
        return new TChangeOfPositionSpecificInfoImpl(locationInformation, metDPCriteriaList);
    }

    @Override
    public OServiceChangeSpecificInfo createOServiceChangeSpecificInfo(ExtBasicServiceCode extBasicServiceCode) {
        return new OServiceChangeSpecificInfoImpl(extBasicServiceCode);
    }

    @Override
    public TServiceChangeSpecificInfo createTServiceChangeSpecificInfo(ExtBasicServiceCode extBasicServiceCode) {
        return new TServiceChangeSpecificInfoImpl(extBasicServiceCode);
    }

    @Override
    public CollectedInfoSpecificInfo createCollectedInfoSpecificInfo(CalledPartyNumberIsup calledPartyNumber) {
        return new CollectedInfoSpecificInfoImpl(calledPartyNumber);
    }

    @Override
    public DpSpecificInfoAlt createDpSpecificInfoAlt(OServiceChangeSpecificInfo oServiceChangeSpecificInfo,
            CollectedInfoSpecificInfo collectedInfoSpecificInfo, TServiceChangeSpecificInfo tServiceChangeSpecificInfo) {
        return new DpSpecificInfoAltImpl(oServiceChangeSpecificInfo, collectedInfoSpecificInfo, tServiceChangeSpecificInfo);
    }

    @Override
    public ChangeOfLocationAlt createChangeOfLocationAlt() {
        return new ChangeOfLocationAltImpl();
    }

    @Override
    public ChangeOfLocation createChangeOfLocation_cellGlobalId(CellGlobalIdOrServiceAreaIdFixedLength value) {
        return new ChangeOfLocationImpl(value, ChangeOfLocation.CellGlobalIdOrServiceAreaIdFixedLength_Option.cellGlobalId);
    }

    @Override
    public ChangeOfLocation createChangeOfLocation_serviceAreaId(CellGlobalIdOrServiceAreaIdFixedLength value) {
        return new ChangeOfLocationImpl(value, ChangeOfLocation.CellGlobalIdOrServiceAreaIdFixedLength_Option.serviceAreaId);
    }

    @Override
    public ChangeOfLocation createChangeOfLocation(LAIFixedLength locationAreaId) {
        return new ChangeOfLocationImpl(locationAreaId);
    }

    @Override
    public ChangeOfLocation createChangeOfLocation_interSystemHandOver() {
        return new ChangeOfLocationImpl(ChangeOfLocation.Boolean_Option.interSystemHandOver);
    }

    @Override
    public ChangeOfLocation createChangeOfLocation_interPLMNHandOver() {
        return new ChangeOfLocationImpl(ChangeOfLocation.Boolean_Option.interPLMNHandOver);
    }

    @Override
    public ChangeOfLocation createChangeOfLocation_interMSCHandOver() {
        return new ChangeOfLocationImpl(ChangeOfLocation.Boolean_Option.interMSCHandOver);
    }

    @Override
    public ChangeOfLocation createChangeOfLocation(ChangeOfLocationAlt changeOfLocationAlt) {
        return new ChangeOfLocationImpl(changeOfLocationAlt);
    }

    @Override
    public DpSpecificCriteriaAlt createDpSpecificCriteriaAlt(List<ChangeOfLocation> changeOfPositionControlInfo, Integer numberOfDigits) {
        return new DpSpecificCriteriaAltImpl(changeOfPositionControlInfo, numberOfDigits);
    }

    @Override
    public MidCallControlInfo createMidCallControlInfo(Integer minimumNumberOfDigits, Integer maximumNumberOfDigits, String endOfReplyDigit,
            String cancelDigit, String startDigit, Integer interDigitTimeout) {
        return new MidCallControlInfoImpl(minimumNumberOfDigits, maximumNumberOfDigits, endOfReplyDigit, cancelDigit, startDigit, interDigitTimeout);
    }

    @Override
    public Burst createBurst(Integer numberOfBursts, Integer burstInterval, Integer numberOfTonesInBurst, Integer toneDuration, Integer toneInterval) {
        return new BurstImpl(numberOfBursts, burstInterval, numberOfTonesInBurst, toneDuration, toneInterval);
    }

    @Override
    public BurstList createBurstList(Integer warningPeriod, Burst burst) {
        return new BurstListImpl(warningPeriod, burst);
    }

    @Override
    public AudibleIndicator createAudibleIndicator(Boolean tone) {
        return new AudibleIndicatorImpl(tone);
    }

    @Override
    public AudibleIndicator createAudibleIndicator(BurstList burstList) {
        return new AudibleIndicatorImpl(burstList);
    }

    @Override
    public AChChargingAddress createAChChargingAddress(LegID legID) {
        return new AChChargingAddressImpl(legID);
    }

    @Override
    public AChChargingAddress createAChChargingAddress(int srfConnection) {
        return new AChChargingAddressImpl(srfConnection);
    }
    
    @Override
    public CallingPartysCategoryIsup createCallingPartysCategoryInap(CallingPartyCategory callingPartyCategory)
            throws CAPException {
    	try {
    		return new CallingPartysCategoryIsupImpl(callingPartyCategory);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(),ex.getCause());
    	}    	
    }

    @Override
    public HighLayerCompatibilityIsup createHighLayerCompatibilityInap(UserTeleserviceInformation highLayerCompatibility)
            throws CAPException {
    	try {
    		return new HighLayerCompatibilityIsupImpl(highLayerCompatibility);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(),ex.getCause());
    	} 
    }

    @Override
    public RedirectionInformationIsup createRedirectionInformationInap(RedirectionInformation redirectionInformation)
            throws CAPException {
    	try {
    		return new RedirectionInformationIsupImpl(redirectionInformation);
    	}
    	catch(APPException ex) {
    		throw new CAPException(ex.getMessage(),ex.getCause());
    	} 
    }

    @Override
    public LegID createLegID(LegType receivingLeg,LegType sendingLeg) {
    	return new LegIDImpl(receivingLeg, sendingLeg);
    }
    
    @Override
    public MiscCallInfo createMiscCallInfo(MiscCallInfoMessageType messageType, MiscCallInfoDpAssignment dpAssignment) {
        return new MiscCallInfoImpl(messageType, dpAssignment);
    }

    @Override
    public IMSI createIMSI(String data) {
        return new IMSIImpl(data);
    }

    @Override
    public ISDNAddressString createISDNAddressString(AddressNature addNature, NumberingPlan numPlan, String address) {
        return new ISDNAddressStringImpl(addNature, numPlan, address);
    }

    @Override
    public ISDNAddressString createISDNAddressString(boolean extension, AddressNature addNature, NumberingPlan numPlan, String address) {
        return new ISDNAddressStringImpl(extension, addNature, numPlan, address);
    }

    @Override
    public SupportedCamelPhases createSupportedCamelPhases(boolean phase1, boolean phase2, boolean phase3, boolean phase4) {
        return new SupportedCamelPhasesImpl(phase1, phase2, phase3, phase4);
    }
    
    @Override
    public LocationInformation createLocationInformation(Integer ageOfLocationInformation,
    		GeographicalInformation geographicalInformation, ISDNAddressString vlrNumber, LocationNumberMap locationNumber,
            CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI, MAPExtensionContainer extensionContainer,
            LSAIdentity selectedLSAId, ISDNAddressString mscNumber, GeodeticInformation geodeticInformation,
            boolean currentLocationRetrieved, boolean saiPresent, LocationInformationEPS locationInformationEPS,
            UserCSGInformation userCSGInformation) {
        return new LocationInformationImpl(ageOfLocationInformation, geographicalInformation, vlrNumber, locationNumber,
                cellGlobalIdOrServiceAreaIdOrLAI, extensionContainer, selectedLSAId, mscNumber, geodeticInformation,
                currentLocationRetrieved, saiPresent, locationInformationEPS, userCSGInformation);
    }
}
