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

package org.restcomm.protocols.ss7.map.smstpdu;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
*
* @author sergey vetyutnev
*
*/
public class ApplicationPortAddressing16BitAddressTest {

    private byte[] getData() {
        return new byte[] { 0x3e, (byte) 0x94, 0, 1 };
    }

    @Test(groups = { "functional.decode", "smstpdu" })
    public void testDecode() throws Exception {

        ApplicationPortAddressing16BitAddressImpl dcs = new ApplicationPortAddressing16BitAddressImpl(getData());
        assertEquals(dcs.getDestinationPort(), 16020);
        assertEquals(dcs.getOriginatorPort(), 1);
    }

    @Test(groups = { "functional.encode", "smstpdu" })
    public void testEncode() throws Exception {

        ApplicationPortAddressing16BitAddressImpl dcs = new ApplicationPortAddressing16BitAddressImpl(16020, 1);
        assertEquals(dcs.getEncodedInformationElementData(), getData());
    }

}
