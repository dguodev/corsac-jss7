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

package org.restcomm.protocols.ss7.cap.gap;

import org.restcomm.protocols.ss7.cap.api.gap.*;
import org.restcomm.protocols.ss7.cap.api.isup.DigitsImpl;
import org.restcomm.protocols.ss7.cap.api.primitives.ScfIDImpl;
import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 *
 * @author <a href="mailto:bartosz.krok@pro-ids.com"> Bartosz Krok (ProIDS sp. z o.o.)</a>
 *
 */
public class GapCriteriaTest {

    public static final int SERVICE_KEY = 821;

    // choice BasicGapCriteria -> CalledAddressValue
    public byte[] getData() {
        return new byte[] { 48, 6, (byte) 128, 4, 48, 69, 91, 84 };
    }

    // choice BasicGapCriteria -> GapOnService
    public byte[] getData1() {
        return new byte[] { 48, 6, (byte) 162, 4, (byte) 128, 2, 3, 53 };
    }

    public byte[] getData2() {
        return new byte[] { 48, 16, 48, 14, (byte) 160, 6, (byte) 162, 4, (byte) 128, 2, 3, 53, (byte) 129, 4, 12, 32, 23, 56 };
    }

    // calledAddressValue
    public byte[] getDigitsData() {
        return new byte[] {48, 69, 91, 84};
    }

    public byte[] getScfIDData() {
        return new byte[] { 12, 32, 23, 56 };
    }

    @Test(groups = { "functional.decode", "gap" })
    public void testDecode_CalledAddressValue() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(GapCriteriaWrapperImpl.class);
    	
    	byte[] rawData = this.getData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GapCriteriaWrapperImpl);
        
        GapCriteriaWrapperImpl elem = (GapCriteriaWrapperImpl)result.getResult();        
        assertEquals(elem.getGapCriteria().getBasicGapCriteria().getCalledAddressValue().getData(), getDigitsData());
    }

    @Test(groups = { "functional.encode", "gap" })
    public void testEncode_CalledAddressValue() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(GapCriteriaWrapperImpl.class);
    	
        DigitsImpl calledAddressValue = new DigitsImpl(getDigitsData());
        BasicGapCriteriaImpl basicGapCriteria = new BasicGapCriteriaImpl(calledAddressValue);
        GapCriteriaImpl elem = new GapCriteriaImpl(basicGapCriteria);
        GapCriteriaWrapperImpl param = new GapCriteriaWrapperImpl(elem);
        byte[] rawData = this.getData();
        ByteBuf buffer=parser.encode(param);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    @Test(groups = { "functional.decode", "gap" })
    public void testDecode_GapOnService() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(GapCriteriaWrapperImpl.class);
    	
    	byte[] rawData = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GapCriteriaWrapperImpl);
        
        GapCriteriaWrapperImpl elem = (GapCriteriaWrapperImpl)result.getResult();        
        assertEquals(elem.getGapCriteria().getBasicGapCriteria().getGapOnService().getServiceKey(), SERVICE_KEY);
    }

    @Test(groups = { "functional.encode", "gap" })
    public void testEncode_GapOnService() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(GapCriteriaWrapperImpl.class);
    	
        GapOnServiceImpl gapOnService = new GapOnServiceImpl(SERVICE_KEY);
        BasicGapCriteriaImpl basicGapCriteria = new BasicGapCriteriaImpl(gapOnService);
        GapCriteriaImpl elem = new GapCriteriaImpl(basicGapCriteria);
        GapCriteriaWrapperImpl param = new GapCriteriaWrapperImpl(elem);
        byte[] rawData = this.getData1();
        ByteBuf buffer=parser.encode(param);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    @Test(groups = { "functional.decode", "gap" })
    public void testDecode_CompoundCriteria() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(GapCriteriaWrapperImpl.class);
    	
    	byte[] rawData = this.getData2();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GapCriteriaWrapperImpl);
        
        GapCriteriaWrapperImpl elem = (GapCriteriaWrapperImpl)result.getResult();        
        assertEquals(elem.getGapCriteria().getCompoundGapCriteria().getBasicGapCriteria().getGapOnService().getServiceKey(), SERVICE_KEY);
        assertEquals(elem.getGapCriteria().getCompoundGapCriteria().getScfID().getData(), getScfIDData());
    }

    @Test(groups = { "functional.encode", "gap" })
    public void testEncode_CompoundCriteria() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(GapCriteriaWrapperImpl.class);
    	
        GapOnServiceImpl gapOnService = new GapOnServiceImpl(SERVICE_KEY);
        BasicGapCriteriaImpl basicGapCriteria = new BasicGapCriteriaImpl(gapOnService);
        // BasicGapCriteria basicGapCriteria, ScfID scfId
        ScfIDImpl scfId = new ScfIDImpl(getScfIDData());
        CompoundCriteriaImpl compoundCriteria = new CompoundCriteriaImpl(basicGapCriteria, scfId);
        GapCriteriaImpl elem = new GapCriteriaImpl(compoundCriteria);
        GapCriteriaWrapperImpl param = new GapCriteriaWrapperImpl(elem);
        byte[] rawData = this.getData2();
        ByteBuf buffer=parser.encode(param);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    /*@Test(groups = { "functional.xml.serialize", "circuitSwitchedCall" })
    public void testXMLSerialize() throws Exception {

        GenericNumberImpl gn = new GenericNumberImpl(GenericNumber._NAI_NATIONAL_SN, "12345",
                GenericNumber._NQIA_CONNECTED_NUMBER, GenericNumber._NPI_TELEX, GenericNumber._APRI_ALLOWED,
                GenericNumber._NI_INCOMPLETE, GenericNumber._SI_USER_PROVIDED_VERIFIED_FAILED);
        Digits digits = new DigitsImpl(gn);

        CalledAddressAndServiceImpl calledAddressAndService = new CalledAddressAndServiceImpl(digits, SERVICE_KEY);
        BasicGapCriteriaImpl basicGapCriteria = new BasicGapCriteriaImpl(calledAddressAndService);
        ScfIDImpl scfId = new ScfIDImpl(getScfIDData());
        CompoundCriteriaImpl compoundCriteria = new CompoundCriteriaImpl(basicGapCriteria, scfId);

        GapCriteriaImpl original = new GapCriteriaImpl(basicGapCriteria);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "gapCriteriaArg", GapCriteriaImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);

        GapCriteriaImpl copy = reader.read("gapCriteriaArg", GapCriteriaImpl.class);

        assertTrue(isEqual(original, copy));


        original = new GapCriteriaImpl(compoundCriteria);

        baos = new ByteArrayOutputStream();
        writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "gapCriteriaArg", GapCriteriaImpl.class);
        writer.close();

        rawData = baos.toByteArray();
        serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        bais = new ByteArrayInputStream(rawData);
        reader = XMLObjectReader.newInstance(bais);

        copy = reader.read("gapCriteriaArg", GapCriteriaImpl.class);

        assertTrue(isEqual(original, copy));
    }

    private boolean isEqual(GapCriteriaImpl o1, GapCriteriaImpl o2) {
        if (o1 == o2)
            return true;
        if (o1 == null && o2 != null || o1 != null && o2 == null)
            return false;
        if (o1 == null && o2 == null)
            return true;
        if (!o1.toString().equals(o2.toString()))
            return false;
        return true;
    }*/
}
