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

package org.restcomm.protocols.ss7.commonapp.api.primitives;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
<code>
IMSI ::= TBCD-STRING (SIZE (3..8))
-- digits of MCC, MNC, MSIN are concatenated in this order.
TBCD-STRING ::= OCTET STRING
-- This type (Telephony Binary Coded Decimal String) is used to
-- represent several digits from 0 through 9, *, #, a, b, c, two
-- digits per octet, each digit encoded 0000 to 1001 (0 to 9),
-- 1010 (*), 1011 (#), 1100 (a), 1101 (b) or 1110 (c); 1111 used
-- as filler when there is an odd number of digits.
-- bits 8765 of octet n encoding digit 2n -- bits 4321 of octet n encoding digit 2(n-1) +1
</code>
 *
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=4,constructed=false,lengthIndefinite=false)
public interface IMSI {

    /**
     * Returns all digits of IMSI (MCC+MNC+MSIN)
     *
     * @return
     */
    String getData();

}