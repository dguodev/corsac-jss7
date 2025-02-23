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

import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ServiceInteractionIndicatorsTwo;
import org.restcomm.protocols.ss7.commonapp.api.isup.CalledPartyNumberIsup;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CAPINAPExtensions;

/**
 *
<code>
connectToResource {PARAMETERS-BOUND : bound} OPERATION ::= {
  ARGUMENT ConnectToResourceArg {bound}
  RETURN RESULT FALSE
  ERRORS {missingParameter | systemFailure | taskRefused | unexpectedComponentSequence | unexpectedDataValue | unexpectedParameter | unknownCSID}
  CODE opcode-connectToResource
}
-- Direction: gsmSCF -> gsmSSF, Timer: T ctr
-- This operation is used to connect a call segment from the gsmSSF to the
-- gsmSRF.

ConnectToResourceArg {PARAMETERS-BOUND : bound} ::= SEQUENCE {
  resourceAddress CHOICE {
    ipRoutingAddress  [0] IPRoutingAddress {bound},
    none              [3] NULL
  },
  extensions                        [4] Extensions {bound} OPTIONAL,
  serviceInteractionIndicatorsTwo   [7] ServiceInteractionIndicatorsTwo OPTIONAL,
  callSegmentID                     [50] CallSegmentID {bound} OPTIONAL,
  ...
}

IPRoutingAddress {PARAMETERS-BOUND : bound} ::= CalledPartyNumber {bound}
-- Indicates the routing address for the IP.

CallSegmentID {PARAMETERS-BOUND : bound} ::= INTEGER (1..bound.&numOfCSs)
numOfCSs ::= 127
</code>
 *
 *
 * @author sergey vetyutnev
 *
 */
public interface ConnectToResourceRequest extends CircuitSwitchedCallMessage {

    CalledPartyNumberIsup getResourceAddress_IPRoutingAddress();

    boolean getResourceAddress_Null();

    CAPINAPExtensions getExtensions();

    ServiceInteractionIndicatorsTwo getServiceInteractionIndicatorsTwo();

    Integer getCallSegmentID();

}