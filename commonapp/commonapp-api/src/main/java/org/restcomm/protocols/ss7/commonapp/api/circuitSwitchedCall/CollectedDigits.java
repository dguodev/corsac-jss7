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

package org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall;

import org.restcomm.protocols.ss7.commonapp.api.primitives.ErrorTreatment;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
 *
<code>
CollectedDigits ::= SEQUENCE {
  minimumNbOfDigits   [0] INTEGER (1..30) DEFAULT 1,
  maximumNbOfDigits   [1] INTEGER (1..30),
  endOfReplyDigit     [2] OCTET STRING (SIZE (1..2)) OPTIONAL,
  cancelDigit         [3] OCTET STRING (SIZE (1..2)) OPTIONAL,
  startDigit          [4] OCTET STRING (SIZE (1..2)) OPTIONAL,
  firstDigitTimeOut   [5] INTEGER (1..127) OPTIONAL,
  interDigitTimeOut   [6] INTEGER (1..127) OPTIONAL,
  errorTreatment      [7] ErrorTreatment DEFAULT stdErrorAndInfo,
  interruptableAnnInd [8] BOOLEAN DEFAULT TRUE,
  voiceInformation    [9] BOOLEAN DEFAULT FALSE,
  voiceBack           [10] BOOLEAN DEFAULT FALSE
}
-- The use of voiceBack and the support of voice recognition via voiceInformation
-- is network operator specific.
-- The endOfReplyDigit, cancelDigit, and startDigit parameters have been
-- designated as OCTET STRING, and are to be encoded as BCD, one digit per octet
-- only, contained in the four least significant bits of each OCTET. The following encoding shall
-- be applied for the non-decimal characters:
-- 1011 (*), 1100 (#).
-- The usage is service dependent.
-- firstDigitTimeOut and interDigitTimeOut are measured in seconds.
</code>
 *
 *
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public interface CollectedDigits {

    Integer getMinimumNbOfDigits();

    int getMaximumNbOfDigits();

    byte[] getEndOfReplyDigit();

    byte[] getCancelDigit();

    byte[] getStartDigit();

    Integer getFirstDigitTimeOut();

    Integer getInterDigitTimeOut();

    ErrorTreatment getErrorTreatment();

    Boolean getInterruptableAnnInd();

    Boolean getVoiceInformation();

    Boolean getVoiceBack();
}