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

package org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall;

import java.util.List;

import org.restcomm.protocols.ss7.cap.api.isup.GenericNumberCapImpl;
import org.restcomm.protocols.ss7.cap.api.isup.LocationNumberCapImpl;
import org.restcomm.protocols.ss7.cap.api.isup.OriginalCalledNumberCapImpl;
import org.restcomm.protocols.ss7.cap.api.isup.RedirectingPartyIDCapImpl;
import org.restcomm.protocols.ss7.cap.api.primitives.CAPExtensionsImpl;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.AlertingPatternCapImpl;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CarrierImpl;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.DestinationRoutingAddressImpl;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.NAOliInfoImpl;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.ServiceInteractionIndicatorsTwoImpl;
import org.restcomm.protocols.ss7.inap.api.isup.CallingPartysCategoryInapImpl;
import org.restcomm.protocols.ss7.inap.api.isup.RedirectionInformationInapImpl;
import org.restcomm.protocols.ss7.inap.api.primitives.LegIDImpl;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.CUGInterlockImpl;

/**
 *
<code>
connect {PARAMETERS-BOUND : bound} OPERATION ::= {
  ARGUMENT ConnectArg {bound}
  RETURN RESULT FALSE
  ERRORS {missingParameter | parameterOutOfRange | systemFailure | taskRefused | unexpectedComponentSequence | unexpectedDataValue | unexpectedParameter | unknownLegID}
  CODE opcode-connect
}
-- Direction: gsmSCF-> gsmSSF, Timer: Tcon
-- This operation is used to request the gsmSSF to perform the call processing actions
-- to route or forward a call to a specified destination.

ConnectArg {PARAMETERS-BOUND : bound} ::= SEQUENCE {
  destinationRoutingAddress     [0] DestinationRoutingAddress {bound},
  alertingPattern               [1] AlertingPattern OPTIONAL,
  originalCalledPartyID         [6] OriginalCalledPartyID {bound} OPTIONAL,
  extensions                    [10] Extensions {bound} OPTIONAL,
  carrier                       [11] Carrier {bound} OPTIONAL,
  callingPartysCategory         [28] CallingPartysCategory OPTIONAL,
  redirectingPartyID            [29] RedirectingPartyID {bound} OPTIONAL,
  redirectionInformation        [30] RedirectionInformation OPTIONAL,
  genericNumbers                [14] GenericNumbers {bound} OPTIONAL,
  serviceInteractionIndicatorsTwo [15] ServiceInteractionIndicatorsTwo OPTIONAL,
  chargeNumber                  [19] ChargeNumber {bound} OPTIONAL,
  legToBeConnected              [21] LegID OPTIONAL,
  cug-Interlock                 [31] CUG-Interlock OPTIONAL,
  cug-OutgoingAccess            [32] NULL OPTIONAL,
  suppressionOfAnnouncement     [55] SuppressionOfAnnouncement OPTIONAL,
  oCSIApplicable                [56] OCSIApplicable OPTIONAL,
  naOliInfo                     [57] NAOliInfo OPTIONAL,
  bor-InterrogationRequested    [58] NULL OPTIONAL,
  ...
  suppress-N-CSI                [59] NULL OPTIONAL
}
-- na-Info is included at the discretion of the gsmSCF operator.

SuppressionOfAnnouncement ::= NULL

OCSIApplicable ::= NULL
-- Indicates that the Originating CAMEL Subscription Information, if present, shall be
-- applied on the outgoing call leg created with a Connect operation. For the use of this
-- parameter see 3GPP TS 23.078 [7].
</code>
 *
 *
 * @author sergey vetyutnev
 * @author tamas gyorgyey
 */
public interface ConnectRequest extends CircuitSwitchedCallMessage {

    DestinationRoutingAddressImpl getDestinationRoutingAddress();

    AlertingPatternCapImpl getAlertingPattern();

    OriginalCalledNumberCapImpl getOriginalCalledPartyID();

    CAPExtensionsImpl getExtensions();

    CarrierImpl getCarrier();

    CallingPartysCategoryInapImpl getCallingPartysCategory();

    RedirectingPartyIDCapImpl getRedirectingPartyID();

    RedirectionInformationInapImpl getRedirectionInformation();

    List<GenericNumberCapImpl> getGenericNumbers();

    ServiceInteractionIndicatorsTwoImpl getServiceInteractionIndicatorsTwo();

    LocationNumberCapImpl getChargeNumber();

    LegIDImpl getLegToBeConnected();

    CUGInterlockImpl getCUGInterlock();

    boolean getCugOutgoingAccess();

    boolean getSuppressionOfAnnouncement();

    boolean getOCSIApplicable();

    NAOliInfoImpl getNAOliInfo();

    boolean getBorInterrogationRequested();

    boolean getSuppressNCSI();

}
