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

package org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.isup.DigitsIsupImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.GenericDigitsImpl;
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
public class PromptAndCollectUserInformationResponseTest {

    public byte[] getData1() {
        return new byte[] { (byte) 128, 4, 65, 44, 55, 66 };
    }

    public byte[] getDigits() {
        return new byte[] { 44, 55, 66 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(PromptAndCollectUserInformationResponseImpl.class);
    	
    	byte[] rawData = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof PromptAndCollectUserInformationResponseImpl);
        
        PromptAndCollectUserInformationResponseImpl elem = (PromptAndCollectUserInformationResponseImpl)result.getResult();                
        assertEquals(elem.getDigitsResponse().getGenericDigits().getEncodingScheme(), 2);
        assertEquals(elem.getDigitsResponse().getGenericDigits().getTypeOfDigits(), 1);
        
        ByteBuf buffer=elem.getDigitsResponse().getGenericDigits().getEncodedDigits();
        assertNotNull(buffer);
        byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        assertTrue(Arrays.equals(data, this.getDigits()));
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(PromptAndCollectUserInformationResponseImpl.class);
    	
        GenericDigitsImpl genericDigits = new GenericDigitsImpl(2, 1, Unpooled.wrappedBuffer(getDigits()));
        // int encodingScheme, int typeOfDigits, int[] digits
        DigitsIsupImpl digitsResponse = new DigitsIsupImpl(genericDigits);

        PromptAndCollectUserInformationResponseImpl elem = new PromptAndCollectUserInformationResponseImpl(digitsResponse);
        byte[] rawData = this.getData1();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    /*@Test(groups = { "functional.xml.serialize", "circuitSwitchedCall" })
    public void testXMLSerialize() throws Exception {

        GenericNumber genericNumber = new GenericNumberImpl(1, "987", 0, 2, 3, true, 0);
//        int natureOfAddresIndicator, String address, int numberQualifierIndicator,
//        int numberingPlanIndicator, int addressRepresentationREstrictedIndicator, boolean numberIncomplete,
//        int screeningIndicator
        Digits digitsResponse = new DigitsImpl(genericNumber);
        PromptAndCollectUserInformationResponseImpl original = new PromptAndCollectUserInformationResponseImpl(digitsResponse);
        original.setInvokeId(21);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "promptAndCollectUserInformationResponse", PromptAndCollectUserInformationResponseImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        PromptAndCollectUserInformationResponseImpl copy = reader.read("promptAndCollectUserInformationResponse", PromptAndCollectUserInformationResponseImpl.class);

        assertEquals(copy.getInvokeId(), original.getInvokeId());
        assertEquals(copy.getDigitsResponse().getGenericNumber().getNatureOfAddressIndicator(), 1);
        assertEquals(copy.getDigitsResponse().getGenericNumber().getAddress(), "987");
        assertEquals(copy.getDigitsResponse().getGenericNumber().getNumberQualifierIndicator(), 0);
        assertEquals(copy.getDigitsResponse().getGenericNumber().getNumberingPlanIndicator(), 2);
    }*/
}
