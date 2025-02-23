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

package org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall;

import java.util.List;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
 *
<code>
DpSpecificCriteriaAlt {PARAMETERS-BOUND : bound} ::= SEQUENCE {
  ...,
  changeOfPositionControlInfo  [0] ChangeOfPositionControlInfo {bound}
  numberOfDigits               [1] NumberOfDigits OPTIONAL,
}
ChangeOfPositionControlInfo {PARAMETERS-BOUND : bound} ::= SEQUENCE SIZE (1..bound.&numOfChangeOfPositionControlInfo) OF ChangeOfLocation {bound}
numOfChangeOfPositionControlInfo ::= 10
NumberOfDigits ::= INTEGER (1..255)
-- Indicates the number of digits to be collected.
</code>
 *
 *
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public interface DpSpecificCriteriaAlt {

    List<ChangeOfLocation> getChangeOfPositionControlInfo();

    Integer getNumberOfDigits();

}