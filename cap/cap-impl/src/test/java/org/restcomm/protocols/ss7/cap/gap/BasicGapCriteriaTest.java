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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.gap.BasicGapCriteriaImpl;
import org.restcomm.protocols.ss7.commonapp.gap.BasicGapCriteriaWrapperImpl;
import org.restcomm.protocols.ss7.commonapp.gap.CalledAddressAndServiceImpl;
import org.restcomm.protocols.ss7.commonapp.gap.CallingAddressAndServiceImpl;
import org.restcomm.protocols.ss7.commonapp.gap.GapOnServiceImpl;
import org.restcomm.protocols.ss7.commonapp.isup.DigitsIsupImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.GenericNumberImpl;
import org.restcomm.protocols.ss7.isup.message.parameter.GenericNumber;
import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author <a href="mailto:bartosz.krok@pro-ids.com"> Bartosz Krok (ProIDS sp. z o.o.)</a>
 *
 */
public class BasicGapCriteriaTest {

    public static final int SERVICE_KEY = 821;

    // CalledAddressValue
    public byte[] getData() {
        return new byte[] { 48, 7, (byte) 128, 5, 0, 1, 64, 66, (byte) 134};
    }

    // CalledAddressAndService
    public byte[] getData1() {
        return new byte[] { 48, 12, (byte) 189, 10, (byte) 128, 4, 48, 69, 91, 84, (byte) 129, 2, 3, 53};
    }

    public byte[] getDigitsData() {
        return new byte[] {48, 69, 91, 84};
    }

    public byte[] getData2() {
        return new byte[] { 48, 5, (byte) 162, 3, (byte) 128, 1, 18 };
    }

    public byte[] getData3() {
        return new byte[] { 48, 12, (byte) 190, 10, (byte) 128, 4, 48, 69, 91, 84, (byte) 129, 2, 3, 53 };
    }

    @Test(groups = { "functional.decode", "gap" })
    public void testDecode_CalledAddressValue() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BasicGapCriteriaWrapperImpl.class);
    	
    	byte[] rawData = this.getData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof BasicGapCriteriaWrapperImpl);
        
        BasicGapCriteriaWrapperImpl elem = (BasicGapCriteriaWrapperImpl)result.getResult();        
        assertEquals(elem.getBasicGapCriteria().getCalledAddressValue().getGenericNumber().getAddress(), "2468");
        assertEquals(elem.getBasicGapCriteria().getCalledAddressValue().getGenericNumber().getNumberingPlanIndicator(), 4);
    }

    @Test(groups = { "functional.encode", "gap" })
    public void testEncode_CalledAddressValue() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BasicGapCriteriaWrapperImpl.class);
    	
        GenericNumber genericNumber = new GenericNumberImpl(1, "2468", 0, 4, 0, false, 0);
        DigitsIsupImpl digits = new DigitsIsupImpl(genericNumber);
        BasicGapCriteriaImpl elem = new BasicGapCriteriaImpl(digits);
        BasicGapCriteriaWrapperImpl prim=new BasicGapCriteriaWrapperImpl(elem);
        
        byte[] rawData = this.getData();
        ByteBuf buffer=parser.encode(prim);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    @Test(groups = { "functional.decode", "gap" })
    public void testDecode_GapOnService() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BasicGapCriteriaWrapperImpl.class);
    	
        byte[] rawData = this.getData2();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof BasicGapCriteriaWrapperImpl);
        
        BasicGapCriteriaWrapperImpl elem = (BasicGapCriteriaWrapperImpl)result.getResult();        
        assertEquals(elem.getBasicGapCriteria().getGapOnService().getServiceKey(), 18);
    }

    @Test(groups = { "functional.encode", "gap" })
    public void testEncode_GapOnService() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BasicGapCriteriaWrapperImpl.class);
    	
        GapOnServiceImpl gapOnService = new GapOnServiceImpl(18);
        BasicGapCriteriaImpl elem = new BasicGapCriteriaImpl(gapOnService);
        BasicGapCriteriaWrapperImpl prim=new BasicGapCriteriaWrapperImpl(elem);
        
        byte[] rawData = this.getData2();
        ByteBuf buffer=parser.encode(prim);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    @Test(groups = { "functional.decode", "gap" })
    public void testDecode_CalledAddressAndService() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BasicGapCriteriaWrapperImpl.class);
    	
    	byte[] rawData = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof BasicGapCriteriaWrapperImpl);
        
        BasicGapCriteriaWrapperImpl elem = (BasicGapCriteriaWrapperImpl)result.getResult();        
        assertEquals(elem.getBasicGapCriteria().getCalledAddressAndService().getServiceKey(), SERVICE_KEY);
        assertEquals(elem.getBasicGapCriteria().getCalledAddressAndService().getCalledAddressValue().getData(), getDigitsData());
    }

    @Test(groups = { "functional.encode", "gap" })
    public void testEncode_CalledAddressAndService() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BasicGapCriteriaWrapperImpl.class);
    	
        DigitsIsupImpl digits = new DigitsIsupImpl(getDigitsData());
        CalledAddressAndServiceImpl calledAddressAndService = new CalledAddressAndServiceImpl(digits, SERVICE_KEY);
        BasicGapCriteriaImpl elem = new BasicGapCriteriaImpl(calledAddressAndService);
        BasicGapCriteriaWrapperImpl prim=new BasicGapCriteriaWrapperImpl(elem);
        
        byte[] rawData = this.getData1();
        ByteBuf buffer=parser.encode(prim);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    @Test(groups = { "functional.decode", "gap" })
    public void testDecode_CallingAddressAndService() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BasicGapCriteriaWrapperImpl.class);
    	
    	byte[] rawData = this.getData3();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof BasicGapCriteriaWrapperImpl);
        
        BasicGapCriteriaWrapperImpl elem = (BasicGapCriteriaWrapperImpl)result.getResult();        
        assertEquals(elem.getBasicGapCriteria().getCallingAddressAndService().getServiceKey(), SERVICE_KEY);
        assertEquals(elem.getBasicGapCriteria().getCallingAddressAndService().getCallingAddressValue().getData(), getDigitsData());
    }

    @Test(groups = { "functional.encode", "gap" })
    public void testEncode_CallingAddressAndService() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BasicGapCriteriaWrapperImpl.class);
    	
        DigitsIsupImpl digits = new DigitsIsupImpl(getDigitsData());
        CallingAddressAndServiceImpl callingAddressAndService = new CallingAddressAndServiceImpl(digits, SERVICE_KEY);
        BasicGapCriteriaImpl elem = new BasicGapCriteriaImpl(callingAddressAndService);
        BasicGapCriteriaWrapperImpl prim=new BasicGapCriteriaWrapperImpl(elem);
        
        byte[] rawData = this.getData3();
        ByteBuf buffer=parser.encode(prim);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    /*@Test(groups = { "functional.xml.serialize", "gap" })
    public void testXMLSerialize() throws Exception {

        GenericNumberImpl gn = new GenericNumberImpl(GenericNumber._NAI_NATIONAL_SN, "12345",
                GenericNumber._NQIA_CONNECTED_NUMBER, GenericNumber._NPI_TELEX, GenericNumber._APRI_ALLOWED,
                GenericNumber._NI_INCOMPLETE, GenericNumber._SI_USER_PROVIDED_VERIFIED_FAILED);
        Digits digits = new DigitsImpl(gn);

        CalledAddressAndServiceImpl calledAddressAndService = new CalledAddressAndServiceImpl(digits, SERVICE_KEY);
        CallingAddressAndServiceImpl callingAddressAndService = new CallingAddressAndServiceImpl(digits, SERVICE_KEY);
        GapOnService gapOnService = new GapOnServiceImpl(SERVICE_KEY);

        BasicGapCriteriaImpl original;

        int i = 0;
        while(i < 4) {
            switch (i) {
                case 0:
                    original = new BasicGapCriteriaImpl(digits);
                    test(original);
                    break;
                case 1:
                    original = new BasicGapCriteriaImpl(calledAddressAndService);
                    test(original);
                    break;
                case 2:
                    original = new BasicGapCriteriaImpl(callingAddressAndService);
                    test(original);
                    break;
                case 3:
                    original = new BasicGapCriteriaImpl(gapOnService);
                    test(original);
                    break;
            }
            i++;
        }
    }

    private void test(BasicGapCriteriaImpl original) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "basicGapCriteriaArg", BasicGapCriteriaImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);

        BasicGapCriteriaImpl copy = reader.read("basicGapCriteriaArg", BasicGapCriteriaImpl.class);

        assertTrue(isEqual(original, copy));
    }

    private boolean isEqual(BasicGapCriteriaImpl o1, BasicGapCriteriaImpl o2) {
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
