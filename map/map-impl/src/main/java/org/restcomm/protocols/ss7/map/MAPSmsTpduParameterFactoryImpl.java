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

package org.restcomm.protocols.ss7.map;

import java.nio.charset.Charset;

import org.restcomm.protocols.ss7.commonapp.api.datacoding.NationalLanguageIdentifier;
import org.restcomm.protocols.ss7.commonapp.api.smstpdu.AbsoluteTimeStamp;
import org.restcomm.protocols.ss7.commonapp.api.smstpdu.ValidityEnhancedFormatData;
import org.restcomm.protocols.ss7.commonapp.api.smstpdu.ValidityPeriod;
import org.restcomm.protocols.ss7.commonapp.smstpu.AbsoluteTimeStampImpl;
import org.restcomm.protocols.ss7.commonapp.smstpu.ValidityEnhancedFormatDataImpl;
import org.restcomm.protocols.ss7.commonapp.smstpu.ValidityPeriodImpl;
import org.restcomm.protocols.ss7.map.api.MAPSmsTpduParameterFactory;
import org.restcomm.protocols.ss7.map.api.smstpdu.AddressField;
import org.restcomm.protocols.ss7.map.api.smstpdu.CharacterSet;
import org.restcomm.protocols.ss7.map.api.smstpdu.CommandData;
import org.restcomm.protocols.ss7.map.api.smstpdu.CommandType;
import org.restcomm.protocols.ss7.map.api.smstpdu.CommandTypeValue;
import org.restcomm.protocols.ss7.map.api.smstpdu.ConcatenatedShortMessagesIdentifier;
import org.restcomm.protocols.ss7.map.api.smstpdu.DataCodingGroup;
import org.restcomm.protocols.ss7.map.api.smstpdu.DataCodingSchemaIndicationType;
import org.restcomm.protocols.ss7.map.api.smstpdu.DataCodingSchemaMessageClass;
import org.restcomm.protocols.ss7.map.api.smstpdu.DataCodingScheme;
import org.restcomm.protocols.ss7.map.api.smstpdu.FailureCause;
import org.restcomm.protocols.ss7.map.api.smstpdu.NationalLanguageLockingShiftIdentifier;
import org.restcomm.protocols.ss7.map.api.smstpdu.NationalLanguageSingleShiftIdentifier;
import org.restcomm.protocols.ss7.map.api.smstpdu.NumberingPlanIdentification;
import org.restcomm.protocols.ss7.map.api.smstpdu.ParameterIndicator;
import org.restcomm.protocols.ss7.map.api.smstpdu.ProtocolIdentifier;
import org.restcomm.protocols.ss7.map.api.smstpdu.SmsCommandTpdu;
import org.restcomm.protocols.ss7.map.api.smstpdu.SmsDeliverReportTpdu;
import org.restcomm.protocols.ss7.map.api.smstpdu.SmsDeliverTpdu;
import org.restcomm.protocols.ss7.map.api.smstpdu.SmsStatusReportTpdu;
import org.restcomm.protocols.ss7.map.api.smstpdu.SmsSubmitReportTpdu;
import org.restcomm.protocols.ss7.map.api.smstpdu.SmsSubmitTpdu;
import org.restcomm.protocols.ss7.map.api.smstpdu.Status;
import org.restcomm.protocols.ss7.map.api.smstpdu.StatusReportQualifier;
import org.restcomm.protocols.ss7.map.api.smstpdu.TypeOfNumber;
import org.restcomm.protocols.ss7.map.api.smstpdu.UserData;
import org.restcomm.protocols.ss7.map.api.smstpdu.UserDataHeader;
import org.restcomm.protocols.ss7.map.smstpdu.AddressFieldImpl;
import org.restcomm.protocols.ss7.map.smstpdu.CommandDataImpl;
import org.restcomm.protocols.ss7.map.smstpdu.CommandTypeImpl;
import org.restcomm.protocols.ss7.map.smstpdu.ConcatenatedShortMessagesIdentifierImpl;
import org.restcomm.protocols.ss7.map.smstpdu.DataCodingSchemeImpl;
import org.restcomm.protocols.ss7.map.smstpdu.FailureCauseImpl;
import org.restcomm.protocols.ss7.map.smstpdu.NationalLanguageLockingShiftIdentifierImpl;
import org.restcomm.protocols.ss7.map.smstpdu.NationalLanguageSingleShiftIdentifierImpl;
import org.restcomm.protocols.ss7.map.smstpdu.ParameterIndicatorImpl;
import org.restcomm.protocols.ss7.map.smstpdu.ProtocolIdentifierImpl;
import org.restcomm.protocols.ss7.map.smstpdu.SmsCommandTpduImpl;
import org.restcomm.protocols.ss7.map.smstpdu.SmsDeliverReportTpduImpl;
import org.restcomm.protocols.ss7.map.smstpdu.SmsDeliverTpduImpl;
import org.restcomm.protocols.ss7.map.smstpdu.SmsStatusReportTpduImpl;
import org.restcomm.protocols.ss7.map.smstpdu.SmsSubmitReportTpduImpl;
import org.restcomm.protocols.ss7.map.smstpdu.SmsSubmitTpduImpl;
import org.restcomm.protocols.ss7.map.smstpdu.StatusImpl;
import org.restcomm.protocols.ss7.map.smstpdu.UserDataHeaderImpl;
import org.restcomm.protocols.ss7.map.smstpdu.UserDataImpl;

/**
 *
 * @author amit bhayani
 *
 */
public class MAPSmsTpduParameterFactoryImpl implements MAPSmsTpduParameterFactory {

    public SmsCommandTpdu createSmsCommandTpdu(boolean statusReportRequest, int messageReference,
            ProtocolIdentifier protocolIdentifier, CommandType commandType, int messageNumber, AddressField destinationAddress,
            CommandData commandData) {
        return new SmsCommandTpduImpl(statusReportRequest, messageReference, protocolIdentifier, commandType, messageNumber,
                destinationAddress, commandData);
    }

    public SmsDeliverReportTpdu createSmsDeliverReportTpdu(FailureCause failureCause, ProtocolIdentifier protocolIdentifier,
            UserData userData) {
        return new SmsDeliverReportTpduImpl(failureCause, protocolIdentifier, userData);
    }

    public SmsDeliverTpdu createSmsDeliverTpdu(boolean moreMessagesToSend, boolean forwardedOrSpawned, boolean replyPathExists,
            boolean statusReportIndication, AddressField originatingAddress, ProtocolIdentifier protocolIdentifier,
            AbsoluteTimeStamp serviceCentreTimeStamp, UserData userData) {
        return new SmsDeliverTpduImpl(moreMessagesToSend, forwardedOrSpawned, replyPathExists, statusReportIndication,
                originatingAddress, protocolIdentifier, serviceCentreTimeStamp, userData);
    }

    public SmsStatusReportTpdu createSmsStatusReportTpdu(boolean moreMessagesToSend, boolean forwardedOrSpawned,
            StatusReportQualifier statusReportQualifier, int messageReference, AddressField recipientAddress,
            AbsoluteTimeStamp serviceCentreTimeStamp, AbsoluteTimeStamp dischargeTime, Status status,
            ProtocolIdentifier protocolIdentifier, UserData userData) {
        return new SmsStatusReportTpduImpl(moreMessagesToSend, forwardedOrSpawned, statusReportQualifier, messageReference,
                recipientAddress, serviceCentreTimeStamp, dischargeTime, status, protocolIdentifier, userData);
    }

    public SmsSubmitReportTpdu createSmsSubmitReportTpdu(FailureCause failureCause, AbsoluteTimeStamp serviceCentreTimeStamp,
            ProtocolIdentifier protocolIdentifier, UserData userData) {
        return new SmsSubmitReportTpduImpl(failureCause, serviceCentreTimeStamp, protocolIdentifier, userData);
    }

    public SmsSubmitTpdu createSmsSubmitTpdu(boolean rejectDuplicates, boolean replyPathExists, boolean statusReportRequest,
            int messageReference, AddressField destinationAddress, ProtocolIdentifier protocolIdentifier,
            ValidityPeriod validityPeriod, UserData userData) {
        return new SmsSubmitTpduImpl(rejectDuplicates, replyPathExists, statusReportRequest, messageReference,
                destinationAddress, protocolIdentifier, validityPeriod, userData);
    }

    public AbsoluteTimeStamp createAbsoluteTimeStamp(int year, int month, int day, int hour, int minute, int second,
            int timeZone) {
        return new AbsoluteTimeStampImpl(year, month, day, hour, minute, second, timeZone);
    }

    public AddressField createAddressField(TypeOfNumber typeOfNumber, NumberingPlanIdentification numberingPlanIdentification,
            String addressValue) {
        return new AddressFieldImpl(typeOfNumber, numberingPlanIdentification, addressValue);
    }

    public CommandType createCommandType(int code) {
        return new CommandTypeImpl(code);
    }

    public CommandType createCommandType(CommandTypeValue value) {
        return new CommandTypeImpl(value);
    }

    public DataCodingScheme createDataCodingScheme(int code) {
        return new DataCodingSchemeImpl(code);
    }

    public DataCodingScheme createDataCodingScheme(DataCodingGroup dataCodingGroup, DataCodingSchemaMessageClass messageClass,
            DataCodingSchemaIndicationType dataCodingSchemaIndicationType, Boolean setIndicationActive,
            CharacterSet characterSet, boolean isCompressed) {
        return new DataCodingSchemeImpl(dataCodingGroup, messageClass, dataCodingSchemaIndicationType, setIndicationActive,
                characterSet, isCompressed);
    }

    public FailureCause createFailureCause(int code) {
        return new FailureCauseImpl(code);
    }

    public ParameterIndicator createParameterIndicator(boolean TP_UDLPresence, boolean getTP_DCSPresence,
            boolean getTP_PIDPresence) {
        return new ParameterIndicatorImpl(TP_UDLPresence, getTP_DCSPresence, getTP_PIDPresence);
    }

    public ProtocolIdentifier createProtocolIdentifier(int code) {
        return new ProtocolIdentifierImpl(code);
    }

    public Status createStatus(int code) {
        return new StatusImpl(code);
    }

    public ValidityEnhancedFormatData createValidityEnhancedFormatData(byte[] data) {
        return new ValidityEnhancedFormatDataImpl(data);
    }

    public ValidityPeriod createValidityPeriod(int relativeFormatValue) {
        return new ValidityPeriodImpl(relativeFormatValue);
    }

    public ValidityPeriod createValidityPeriod(AbsoluteTimeStamp absoluteFormatValue) {
        return new ValidityPeriodImpl(absoluteFormatValue);
    }

    public ValidityPeriod createValidityPeriod(ValidityEnhancedFormatData enhancedFormatValue) {
        return new ValidityPeriodImpl(enhancedFormatValue);
    }

    public UserDataHeader createUserDataHeader() {
        return new UserDataHeaderImpl();
    }

    public UserDataHeader createUserDataHeader(byte[] encodedData) {
        return new UserDataHeaderImpl(encodedData);
    }

    public UserData createUserData(byte[] encodedData, DataCodingScheme dataCodingScheme, int encodedUserDataLength,
            boolean encodedUserDataHeaderIndicator, Charset gsm8Charset) {
        return new UserDataImpl(encodedData, dataCodingScheme, encodedUserDataLength, encodedUserDataHeaderIndicator,
                gsm8Charset);
    }

    public UserData createUserData(String decodedMessage, DataCodingScheme dataCodingScheme,
            UserDataHeader decodedUserDataHeader, Charset gsm8Charset) {
        return new UserDataImpl(decodedMessage, dataCodingScheme, decodedUserDataHeader, gsm8Charset);
    }

    public CommandData createCommandData(byte[] data) {
        return new CommandDataImpl(data);
    }

    public CommandData createCommandData(String decodedMessage) {
        return new CommandDataImpl(decodedMessage);
    }

    public ConcatenatedShortMessagesIdentifier createConcatenatedShortMessagesIdentifier(boolean referenceIs16bit,
            int reference, int mesageSegmentCount, int mesageSegmentNumber) {
        return new ConcatenatedShortMessagesIdentifierImpl(referenceIs16bit, reference, mesageSegmentCount, mesageSegmentNumber);
    }

    public NationalLanguageLockingShiftIdentifier createNationalLanguageLockingShiftIdentifier(
            NationalLanguageIdentifier nationalLanguageCode) {
        return new NationalLanguageLockingShiftIdentifierImpl(nationalLanguageCode);
    }

    public NationalLanguageSingleShiftIdentifier createNationalLanguageSingleShiftIdentifier(
            NationalLanguageIdentifier nationalLanguageCode) {
        return new NationalLanguageSingleShiftIdentifierImpl(nationalLanguageCode);
    }
}
