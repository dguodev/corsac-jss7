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

import org.restcomm.protocols.ss7.commonapp.isup.GenericNumberIsupImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.GenericNumberImpl;
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
public class GenericNumberCapTest {

    public byte[] getData() {
        return new byte[] { 4, 7, 1, (byte) 131, 20, 7, 1, 9, 0 };
    }

    public byte[] getIntData() {
        return new byte[] { 1, -125, 20, 7, 1, 9, 0 };
    }

    @Test(groups = { "functional.decode", "isup" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(GenericNumberIsupImpl.class);
    	
    	byte[] rawData = this.getData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GenericNumberIsupImpl);
        
        GenericNumberIsupImpl elem = (GenericNumberIsupImpl)result.getResult();        
        GenericNumber gn = elem.getGenericNumber();
        assertTrue(Arrays.equals(elem.getData(), this.getIntData()));
        assertEquals(gn.getNatureOfAddressIndicator(), 3);
        assertTrue(gn.getAddress().equals("7010900"));
        assertEquals(gn.getNumberingPlanIndicator(), 1);
        assertEquals(gn.getAddressRepresentationRestrictedIndicator(), 1);
        assertEquals(gn.getNumberQualifierIndicator(), 1);
        assertEquals(gn.getScreeningIndicator(), 0);
    }

    @Test(groups = { "functional.encode", "isup" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(GenericNumberIsupImpl.class);
    	
        GenericNumberIsupImpl elem = new GenericNumberIsupImpl(this.getIntData());
        byte[] rawData = this.getData();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));

        GenericNumber rn = new GenericNumberImpl(3, "7010900", 1, 1, 1, false, 0);
        elem = new GenericNumberIsupImpl(rn);
        rawData = this.getData();
        buffer=parser.encode(elem);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));

        // int natureOfAddresIndicator, String address, int numberQualifierIndicator, int numberingPlanIndicator, int
        // addressRepresentationREstrictedIndicator,
        // boolean numberIncomplete, int screeningIndicator
    }

    /*@Test(groups = { "functional.xml.serialize", "isup" })
    public void testXMLSerialize() throws Exception {

        GenericNumberImpl gn = new GenericNumberImpl(GenericNumber._NAI_NATIONAL_SN, "12345",
                GenericNumber._NQIA_CONNECTED_NUMBER, GenericNumber._NPI_TELEX, GenericNumber._APRI_ALLOWED,
                GenericNumber._NI_INCOMPLETE, GenericNumber._SI_USER_PROVIDED_VERIFIED_FAILED);
        GenericNumberCapImpl original = new GenericNumberCapImpl(gn);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "genericNumberCap", GenericNumberCapImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        GenericNumberCapImpl copy = reader.read("genericNumberCap", GenericNumberCapImpl.class);

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
