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

package org.restcomm.protocols.ss7.cap.EsiBcsm;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.isup.CalledPartyNumberIsupImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.CalledPartyNumberImpl;
import org.restcomm.protocols.ss7.isup.message.parameter.CalledPartyNumber;
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
public class CollectedInfoSpecificInfoTest {

    public byte[] getData1() {
        return new byte[] { 48, 10, (byte) 128, 8, (byte) 132, 16, 34, 34, 34, 33, 67, 5 };
    }

    @Test(groups = { "functional.decode", "EsiBcsm" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(CollectedInfoSpecificInfoImpl.class);
    	
    	byte[] rawData = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof CollectedInfoSpecificInfoImpl);
        
        CollectedInfoSpecificInfoImpl elem = (CollectedInfoSpecificInfoImpl)result.getResult();        
        assertEquals(elem.getCalledPartyNumber().getCalledPartyNumber().getAddress(), "22222212345");
        assertEquals(elem.getCalledPartyNumber().getCalledPartyNumber().getNatureOfAddressIndicator(), CalledPartyNumberImpl._NAI_INTERNATIONAL_NUMBER);
        assertEquals(elem.getCalledPartyNumber().getCalledPartyNumber().getNumberingPlanIndicator(), CalledPartyNumberImpl._NPI_ISDN);
    }

    @Test(groups = { "functional.encode", "EsiBcsm" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(CollectedInfoSpecificInfoImpl.class);
    	
        CalledPartyNumber calledPartyNumber = new CalledPartyNumberImpl();
        calledPartyNumber.setAddress("22222212345");
        calledPartyNumber.setNatureOfAddresIndicator(CalledPartyNumberImpl._NAI_INTERNATIONAL_NUMBER);
        calledPartyNumber.setNumberingPlanIndicator(CalledPartyNumberImpl._NPI_ISDN);
        CalledPartyNumberIsupImpl calledPartyNumberCap = new CalledPartyNumberIsupImpl(calledPartyNumber);
        CollectedInfoSpecificInfoImpl elem = new CollectedInfoSpecificInfoImpl(calledPartyNumberCap);
        byte[] rawData = this.getData1();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    /*@Test(groups = { "functional.xml.serialize", "EsiBcsm" })
    public void testXMLSerializaion() throws Exception {
        CalledPartyNumber calledPartyNumber = new CalledPartyNumberImpl();
        calledPartyNumber.setAddress("22222212345");
        calledPartyNumber.setNatureOfAddresIndicator(CalledPartyNumberImpl._NAI_INTERNATIONAL_NUMBER);
        calledPartyNumber.setNumberingPlanIndicator(CalledPartyNumberImpl._NPI_ISDN);
        CalledPartyNumberCap calledPartyNumberCap = new CalledPartyNumberCapImpl(calledPartyNumber);
        CollectedInfoSpecificInfoImpl original = new CollectedInfoSpecificInfoImpl(calledPartyNumberCap);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        writer.setIndentation("\t");
        writer.write(original, "collectedInfoSpecificInfo", CollectedInfoSpecificInfoImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        CollectedInfoSpecificInfoImpl copy = reader.read("collectedInfoSpecificInfo", CollectedInfoSpecificInfoImpl.class);

        assertEquals(copy.getCalledPartyNumber().getCalledPartyNumber().getAddress(), original.getCalledPartyNumber().getCalledPartyNumber().getAddress());
        assertEquals(copy.getCalledPartyNumber().getCalledPartyNumber().getNatureOfAddressIndicator(), original.getCalledPartyNumber().getCalledPartyNumber()
                .getNatureOfAddressIndicator());
        assertEquals(copy.getCalledPartyNumber().getCalledPartyNumber().getNumberingPlanIndicator(), original.getCalledPartyNumber().getCalledPartyNumber()
                .getNumberingPlanIndicator());
    }*/
}
