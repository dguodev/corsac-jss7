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
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.EpcAv;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.EpsAuthenticationSetList;
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
public class EpsAuthenticationSetListTest {

    private byte[] getEncodedData() {
        return new byte[] { 48, -127, -98, 48, 76, 4, 16, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 2, 2, 2, 2, 4,
                16, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 32, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 48, 78, 4, 16, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4,
                6, 22, 22, 22, 22, 22, 22, 4, 16, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 32, 4, 4, 4, 4, 4, 4, 4,
                4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 };
    }

    static protected byte[] getXresData() {
        return new byte[] { 22, 22, 22, 22, 22, 22 };
    }

    @Test(groups = { "functional.decode" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(EpsAuthenticationSetListImpl.class);

        byte[] rawData = getEncodedData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof EpsAuthenticationSetListImpl);
        EpsAuthenticationSetListImpl asc = (EpsAuthenticationSetListImpl)result.getResult();
        
        List<EpcAv> epcAvs = asc.getEpcAv();
        assertEquals(epcAvs.size(), 2);

        assertTrue(Arrays.equals(epcAvs.get(0).getRand(), EpcAvTest.getRandData()));
        assertTrue(Arrays.equals(epcAvs.get(0).getXres(), EpcAvTest.getXresData()));
        assertTrue(Arrays.equals(epcAvs.get(0).getAutn(), EpcAvTest.getAutnData()));
        assertTrue(Arrays.equals(epcAvs.get(0).getKasme(), EpcAvTest.getKasmeData()));

        assertTrue(Arrays.equals(epcAvs.get(1).getRand(), EpcAvTest.getRandData()));
        assertTrue(Arrays.equals(epcAvs.get(1).getXres(), getXresData()));
        assertTrue(Arrays.equals(epcAvs.get(1).getAutn(), EpcAvTest.getAutnData()));
        assertTrue(Arrays.equals(epcAvs.get(1).getKasme(), EpcAvTest.getKasmeData()));
    }

    @Test(groups = { "functional.encode" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(EpsAuthenticationSetListImpl.class);

        EpcAvImpl d1 = new EpcAvImpl(EpcAvTest.getRandData(), EpcAvTest.getXresData(), EpcAvTest.getAutnData(),EpcAvTest.getKasmeData(), null);
        EpcAvImpl d2 = new EpcAvImpl(EpcAvTest.getRandData(), getXresData(), EpcAvTest.getAutnData(), EpcAvTest.getKasmeData(),null);
        List<EpcAv> arr = new ArrayList<EpcAv>();
        arr.add(d1);
        arr.add(d2);
        EpsAuthenticationSetList asc = new EpsAuthenticationSetListImpl(arr);

        byte[] data=getEncodedData();
        ByteBuf buffer=parser.encode(asc);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));
    }
}