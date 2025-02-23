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

package org.restcomm.protocols.ss7.map.service.mobility.authentication;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.api.primitives.AddressNature;
import org.restcomm.protocols.ss7.commonapp.api.primitives.IMSI;
import org.restcomm.protocols.ss7.commonapp.api.primitives.NumberingPlan;
import org.restcomm.protocols.ss7.commonapp.primitives.IMSIImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.ISDNAddressStringImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerTest;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.AccessType;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.FailureCause;
import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
*
* @author sergey vetyutnev
*
*/
public class AuthenticationFailureReportRequestTest {

    private byte[] getEncodedData() {
        return new byte[] { 48, 11, 4, 6, 17, 33, 34, 51, 67, 68, 10, 1, 0 };
    }

    private byte[] getEncodedData2() {
        return new byte[] { 48, 88, 4, 6, 17, 33, 34, 51, 67, 68, 10, 1, 1, 48, 39, -96, 32, 48, 10, 6, 3, 42, 3, 4, 11, 12, 13, 14, 15, 48, 5, 6, 3, 42, 3, 6,
                48, 11, 6, 3, 42, 3, 5, 21, 22, 23, 24, 25, 26, -95, 3, 31, 32, 33, 1, 1, -1, 10, 1, 2, 4, 16, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                15, 16, -128, 4, -111, 17, 17, 51, -127, 4, -111, 17, 17, 68 };
    }

    private byte[] getRandBalue() {
        return new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
    }

    @Test
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(AuthenticationFailureReportRequestImpl.class);

        byte[] rawData = getEncodedData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof AuthenticationFailureReportRequestImpl);
        AuthenticationFailureReportRequestImpl asc = (AuthenticationFailureReportRequestImpl)result.getResult();
        
        IMSI imsi = asc.getImsi();
        assertTrue(imsi.getData().equals("111222333444"));
        assertEquals(asc.getFailureCause(), FailureCause.wrongUserResponse);
        assertNull(asc.getExtensionContainer());
        assertNull(asc.getReAttempt());
        assertNull(asc.getAccessType());
        assertNull(asc.getRand());
        assertNull(asc.getVlrNumber());
        assertNull(asc.getSgsnNumber());


        rawData = getEncodedData2();
        result=parser.decode(Unpooled.wrappedBuffer(rawData));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof AuthenticationFailureReportRequestImpl);
        asc = (AuthenticationFailureReportRequestImpl)result.getResult();
        
        imsi = asc.getImsi();
        assertTrue(imsi.getData().equals("111222333444"));
        assertEquals(asc.getFailureCause(), FailureCause.wrongNetworkSignature);
        assertTrue(MAPExtensionContainerTest.CheckTestExtensionContainer(asc.getExtensionContainer()));
        assertTrue(asc.getReAttempt());
        assertEquals(asc.getAccessType(), AccessType.locationUpdating);
        assertEquals(asc.getRand(), getRandBalue());
        assertEquals(asc.getVlrNumber().getAddress(), "111133");
        assertEquals(asc.getSgsnNumber().getAddress(), "111144");

    }

    @Test(groups = { "functional.encode" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(AuthenticationFailureReportRequestImpl.class);

        IMSIImpl imsi = new IMSIImpl("111222333444");
        AuthenticationFailureReportRequestImpl asc = new AuthenticationFailureReportRequestImpl(imsi, FailureCause.wrongUserResponse, null, null, null, null,
                null, null);

        byte[] data=getEncodedData();
        ByteBuf buffer=parser.encode(asc);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));

        ISDNAddressStringImpl vlrNumber = new ISDNAddressStringImpl(AddressNature.international_number, NumberingPlan.ISDN, "111133");
        ISDNAddressStringImpl sgsnNumber = new ISDNAddressStringImpl(AddressNature.international_number, NumberingPlan.ISDN, "111144");
        asc = new AuthenticationFailureReportRequestImpl(imsi, FailureCause.wrongNetworkSignature, MAPExtensionContainerTest.GetTestExtensionContainer(), true,
                AccessType.locationUpdating, getRandBalue(), vlrNumber, sgsnNumber);
        
        data=getEncodedData2();
        buffer=parser.encode(asc);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));
    }
}