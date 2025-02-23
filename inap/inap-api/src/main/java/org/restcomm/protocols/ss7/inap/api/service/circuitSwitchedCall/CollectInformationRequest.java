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

package org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall;

import org.restcomm.protocols.ss7.commonapp.api.isup.CalledPartyNumberIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.CallingPartyNumberIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.LocationNumberIsup;
import org.restcomm.protocols.ss7.commonapp.api.isup.OriginalCalledPartyIDIsup;
import org.restcomm.protocols.ss7.commonapp.api.primitives.AlertingPattern;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CAPINAPExtensions;
import org.restcomm.protocols.ss7.commonapp.api.primitives.NumberingPlan;

/**
 *
<code>
CollectInformation ::= OPERATION
ARGUMENT CollectInformationArg
ERRORS {
	MissingParameter,
	SystemFailure,
	TaskRefused,
	UnexpectedComponentSequence,
	UnexpectedDataValue,
	UnexpectedParameter
}
-- Direction: SCF -> SSF, Timer: Tci
-- This operation is used to request the SSF to perform the originating basic call processing
-- actions to prompt a calling party for destination information, then collect destination information
-- according to a specified numbering plan (e.g., for virtual private networks).

CollectInformationArg ::= SEQUENCE {
	extensions [4] SEQUENCE SIZE(1..numOfExtensions) OF ExtensionField OPTIONAL
-- ...
}

--- From Q.1218 CS1
CollectInformationArg ::= SEQUENCE {
	alertingPattern [0] AlertingPattern OPTIONAL,
	numberingPlan [1] NumberingPlan OPTIONAL,
	originalCalledPartyID [2] OriginalCalledPartyID OPTIONAL,
	travellingClassMark [3] TravellingClassMark OPTIONAL,
	extensions [4] SEQUENCE SIZE(1..numOfExtensions) OF ExtensionField OPTIONAL,
	callingPartyNumber [5] CallingPartyNumber OPTIONAL,
	dialledDigits [6] CalledPartyNumber OPTIONAL
-- ...
}
</code>
 *
 * @author yulian.oifa
 *
 */
public interface CollectInformationRequest extends CircuitSwitchedCallMessage {
	AlertingPattern getAlertingPattern();
	
	NumberingPlan getNumberingPlan();
	
	OriginalCalledPartyIDIsup getOriginalCalledPartyID();
	
	LocationNumberIsup getTravellingClassMark();
    
	CAPINAPExtensions getExtensions();
	
	CallingPartyNumberIsup getCallingPartyNumber();
	
	CalledPartyNumberIsup getDialedDigits();
}