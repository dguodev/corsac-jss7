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
import org.restcomm.protocols.ss7.commonapp.api.primitives.CAPINAPExtensions;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.BackwardGVNS;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.BackwardSuppressionIndicators;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.cs1plus.ForwardSuppressionIndicators;

/**
<code>
SignallingInformation ::= OPERATION
ARGUMENT SignallingInformationArg
ERRORS {
	MissingParameter,
	TaskRefused,
	UnexpectedComponentSequence,
	UnexpectedDataValue,
	UnexpectedParameter
}
‐‐ Direction SCF ‐> SSF, Timer: Tsgi
‐‐ Used to send additional signalling related information
‐‐ for the call.

SignallingInformationArg ::= SEQUENCE {
	backwardSuppressIndicators [01] BackwardSuppressionIndicators OPTIONAL,
	connectedNumber [02] Number OPTIONAL,
	forwardSuppressionIndicators [03] ForwardSuppressionIndicators OPTIONAL,
	backwardGVNSIndicator [04] BackwardGVNSIndicator OPTIONAL,
	extensions [05] SEQUENCE SIZE (1..7) OF ExtensionField OPTIONAL
‐‐ ...
</code>
*
 * @author yulian.oifa
 *
 */
public interface SignallingInformationRequest extends CircuitSwitchedCallMessage {

	BackwardSuppressionIndicators getBackwardSuppressionIndicators();

	CalledPartyNumberIsup getConnectedNumber();

    ForwardSuppressionIndicators getForwardSuppressionIndicators();
    
    BackwardGVNS getBackwardGVNSIndicator();
    
    CAPINAPExtensions getExtensions();
}
