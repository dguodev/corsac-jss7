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

import org.restcomm.protocols.ss7.commonapp.api.primitives.LegType;

/**
 *
HoldCallPartyConnection ::= OPERATION
ARGUMENT HoldCallPartyConnectionArg
RESULT
ERRORS {
	SystemFailure,
	TaskRefused,
	UnexpectedComponentSequence,
	UnexpectedDataValue,
	UnknownLegID
}
‐‐ Direction SCF ‐> SSF, Timer: Thcp
‐‐ Based on Q1218 Appendix I.

HoldCallPartyConnectionArg ::= SEQUENCE {
	legID [01] SendingSideID
‐‐ ...
}
 *
 * @author yulian.oifa
 *
 */
public interface HoldCallPartyConnectionRequest extends CircuitSwitchedCallMessage {

	LegType getLegID();
}