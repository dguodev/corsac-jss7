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

package org.restcomm.protocols.ss7.cap.isup;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.isup.DigitsIsupImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.GenericDigitsImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.GenericNumberImpl;
import org.restcomm.protocols.ss7.isup.message.parameter.GenericDigits;
import org.restcomm.protocols.ss7.isup.message.parameter.GenericNumber;
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
public class DigitsTest {

    public byte[] getData1() {
        return new byte[] { 4, 5, 65, 5, 6, 7, 8 };
    }

    public byte[] getData2() {
        return new byte[] { 4, 7, 3, (byte) 132, 33, 7, 1, 9, 0 };
    }

    public byte[] getGenericDigitsInt() {
        return new byte[] { 5, 6, 7, 8 };
    }

    @Test(groups = { "functional.decode", "isup" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(DigitsIsupImpl.class);
    	
    	byte[] rawData = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof DigitsIsupImpl);
        
        DigitsIsupImpl elem = (DigitsIsupImpl)result.getResult();                
        elem.setIsGenericDigits();
        GenericDigits gd = elem.getGenericDigits();
        assertEquals(gd.getEncodingScheme(), 2);
        assertEquals(gd.getTypeOfDigits(), 1);
        
        ByteBuf value=gd.getEncodedDigits();
        byte[] data=new byte[value.readableBytes()];
        value.readBytes(data);
        assertTrue(Arrays.equals(data, getGenericDigitsInt()));

        rawData = this.getData2();
        result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof DigitsIsupImpl);
        
        elem = (DigitsIsupImpl)result.getResult();                
        elem.setIsGenericNumber();
        GenericNumber gn = elem.getGenericNumber();
        assertEquals(gn.getNatureOfAddressIndicator(), 4);
        assertTrue(gn.getAddress().equals("7010900"));
        assertEquals(gn.getNumberQualifierIndicator(), 3);
        assertEquals(gn.getNumberingPlanIndicator(), 2);
        assertEquals(gn.getAddressRepresentationRestrictedIndicator(), 0);
        assertEquals(gn.getScreeningIndicator(), 1);
    }

    @Test(groups = { "functional.encode", "isup" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(DigitsIsupImpl.class);
    	
        GenericDigitsImpl genericDigits = new GenericDigitsImpl(2, 1, Unpooled.wrappedBuffer(getGenericDigitsInt()));
        DigitsIsupImpl elem = new DigitsIsupImpl(genericDigits);
        byte[] rawData = this.getData1();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
        // int encodingScheme, int typeOfDigits, int[] digits

        GenericNumber rn = new GenericNumberImpl(4, "7010900", 3, 2, 0, false, 1);
        elem = new DigitsIsupImpl(rn);
        rawData = this.getData2();
        buffer=parser.encode(elem);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
        // int natureOfAddresIndicator, String address, int numberQualifierIndicator, int numberingPlanIndicator, int
        // addressRepresentationREstrictedIndicator,
        // boolean numberIncomplete, int screeningIndicator
    }

    /*private byte[] getEncodedData() {
        return new byte[] { 0x21, 0x43, 0x65 };
    }

    @Test(groups = { "functional.xml.serialize", "isup" })
    public void testXMLSerialize() throws Exception {

        GenericDigitsImpl gd = new GenericDigitsImpl(GenericDigits._ENCODING_SCHEME_BCD_ODD, GenericDigits._TOD_BGCI,
                getEncodedData());
        DigitsImpl original = new DigitsImpl(gd);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "digits", DigitsImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        DigitsImpl copy = reader.read("digits", DigitsImpl.class);

        assertEquals(copy.getIsGenericDigits(), original.getIsGenericDigits());
        assertEquals(copy.getIsGenericNumber(), original.getIsGenericNumber());

        assertEquals(copy.getGenericDigits().getEncodingScheme(), original.getGenericDigits().getEncodingScheme());
        assertEquals(copy.getGenericDigits().getTypeOfDigits(), original.getGenericDigits().getTypeOfDigits());
        assertEquals(copy.getGenericDigits().getEncodedDigits(), original.getGenericDigits().getEncodedDigits());

        GenericNumberImpl gn = new GenericNumberImpl(GenericNumber._NAI_NATIONAL_SN, "12345",
                GenericNumber._NQIA_CONNECTED_NUMBER, GenericNumber._NPI_TELEX, GenericNumber._APRI_ALLOWED,
                GenericNumber._NI_INCOMPLETE, GenericNumber._SI_USER_PROVIDED_VERIFIED_FAILED);
        original = new DigitsImpl(gn);

        // Writes the area to a file.
        baos = new ByteArrayOutputStream();
        writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "digits", DigitsImpl.class);
        writer.close();

        rawData = baos.toByteArray();
        serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        bais = new ByteArrayInputStream(rawData);
        reader = XMLObjectReader.newInstance(bais);
        copy = reader.read("digits", DigitsImpl.class);

        assertEquals(copy.getIsGenericDigits(), original.getIsGenericDigits());
        assertEquals(copy.getIsGenericNumber(), original.getIsGenericNumber());

        assertEquals(copy.getGenericNumber().getNatureOfAddressIndicator(), original.getGenericNumber()
                .getNatureOfAddressIndicator());
        assertEquals(copy.getGenericNumber().getAddress(), original.getGenericNumber().getAddress());
        assertEquals(copy.getGenericNumber().getNumberQualifierIndicator(), original.getGenericNumber()
                .getNumberQualifierIndicator());
        assertEquals(copy.getGenericNumber().getNumberingPlanIndicator(), original.getGenericNumber()
                .getNumberingPlanIndicator());
        assertEquals(copy.getGenericNumber().isNumberIncomplete(), original.getGenericNumber().isNumberIncomplete());
        assertEquals(copy.getGenericNumber().getAddressRepresentationRestrictedIndicator(), original.getGenericNumber()
                .getAddressRepresentationRestrictedIndicator());
        assertEquals(copy.getGenericNumber().getScreeningIndicator(), original.getGenericNumber().getScreeningIndicator());
        assertEquals(copy.getGenericNumber().isOddFlag(), original.getGenericNumber().isOddFlag());

    }*/
}
