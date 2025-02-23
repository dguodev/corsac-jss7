/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
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

package org.restcomm.protocols.ss7.map.service.sms;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.api.primitives.AddressNature;
import org.restcomm.protocols.ss7.commonapp.api.primitives.NumberingPlan;
import org.restcomm.protocols.ss7.commonapp.primitives.AddressStringImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.IMSIImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerTest;
import org.restcomm.protocols.ss7.map.api.service.sms.SM_RP_DA;
import org.restcomm.protocols.ss7.map.api.service.sms.SM_RP_OA;
import org.restcomm.protocols.ss7.map.api.service.sms.SmsSignalInfo;
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
public class MtForwardShortMessageRequestTest {

    private byte[] getEncodedData() {
        return new byte[] { 48, 73, -128, 8, 16, 33, 34, 34, 17, -126, 21, -12, -124, 7, -111, -127, 33, 105, 0, -112, -10, 4,
                52, 11, 22, 33, 44, 55, 66, 77, 0, 1, 2, 3, 4, 5, 6, 7, 9, 8, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                3, 4, 4, 4, 4, 4, 4, 99, 88, 77, 66, 55, 44, 44, 33, 22, 11, 11, 0 };
    }

    private byte[] getEncodedDataFull() {
        return new byte[] { 48, 70, -128, 8, 1, -128, 56, 67, 84, 101, 118, -9, -124, 6, -111, 17, 17, 33, 34, 34, 4, 7, 11,
                22, 33, 44, 55, 66, 77, 5, 0, 48, 39, -96, 32, 48, 10, 6, 3, 42, 3, 4, 11, 12, 13, 14, 15, 48, 5, 6, 3, 42, 3,
                6, 48, 11, 6, 3, 42, 3, 5, 21, 22, 23, 24, 25, 26, -95, 3, 31, 32, 33 };
    }

    @Test(groups = { "functional.decode", "service.sms" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(MtForwardShortMessageRequestImpl.class);
                        
        byte[] rawData = getEncodedData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof MtForwardShortMessageRequestImpl);
        MtForwardShortMessageRequestImpl ind = (MtForwardShortMessageRequestImpl)result.getResult();   
        
        SM_RP_DA da = ind.getSM_RP_DA();
        SM_RP_OA oa = ind.getSM_RP_OA();
        SmsSignalInfo ui = ind.getSM_RP_UI();
        // assertEquals( (long) da.getIMSI().getMCC(),11);
        // assertEquals( (long) da.getIMSI().getMNC(),22);
        assertEquals(da.getIMSI().getData(), "011222221128514");
        assertEquals(oa.getServiceCentreAddressOA().getAddressNature(), AddressNature.international_number);
        assertEquals(oa.getServiceCentreAddressOA().getNumberingPlan(), NumberingPlan.ISDN);
        assertEquals(oa.getServiceCentreAddressOA().getAddress(), "18129600096");
        
        ByteBuf buffer=ui.getValue();
        byte[] data=new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        
        assertTrue(Arrays.equals(data, new byte[] { 11, 22, 33, 44, 55, 66, 77, 0, 1, 2, 3, 4, 5, 6, 7, 9, 8, 1, 2, 2,
                2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 99, 88, 77, 66, 55, 44, 44, 33, 22, 11, 11, 0 }));

        rawData = getEncodedDataFull();
        result=parser.decode(Unpooled.wrappedBuffer(rawData));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof MtForwardShortMessageRequestImpl);
        ind = (MtForwardShortMessageRequestImpl)result.getResult();   

        da = ind.getSM_RP_DA();
        oa = ind.getSM_RP_OA();
        ui = ind.getSM_RP_UI();
        Boolean moreMesToSend = ind.getMoreMessagesToSend();
        // assertEquals( (long) da.getIMSI().getMCC(),100);
        // assertEquals( (long) da.getIMSI().getMNC(),88);
        assertEquals(da.getIMSI().getData(), "100883344556677");
        assertEquals(oa.getServiceCentreAddressOA().getAddressNature(), AddressNature.international_number);
        assertEquals(oa.getServiceCentreAddressOA().getNumberingPlan(), NumberingPlan.ISDN);
        assertEquals(oa.getServiceCentreAddressOA().getAddress(), "1111122222");
        
        buffer=ui.getValue();
        data=new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        
        assertTrue(Arrays.equals(data, new byte[] { 11, 22, 33, 44, 55, 66, 77 }));
        assertTrue(moreMesToSend);
        assertTrue(MAPExtensionContainerTest.CheckTestExtensionContainer(ind.getExtensionContainer()));
    }

    @Test(groups = { "functional.encode", "service.sms" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(MtForwardShortMessageRequestImpl.class);
                
        IMSIImpl imsi = new IMSIImpl("011222221128514");
        SM_RP_DAImpl sm_RP_DA = new SM_RP_DAImpl(imsi);
        AddressStringImpl sca = new AddressStringImpl(AddressNature.international_number, NumberingPlan.ISDN, "18129600096");
        SM_RP_OAImpl sm_RP_OA = new SM_RP_OAImpl();
        sm_RP_OA.setServiceCentreAddressOA(sca);
        SmsSignalInfoImpl sm_RP_UI = new SmsSignalInfoImpl(new byte[] { 11, 22, 33, 44, 55, 66, 77, 0, 1, 2, 3, 4, 5, 6, 7, 9, 8,
                1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 99, 88, 77, 66, 55, 44, 44, 33, 22, 11,
                11, 0 }, null);
        MtForwardShortMessageRequestImpl ind = new MtForwardShortMessageRequestImpl(sm_RP_DA, sm_RP_OA, sm_RP_UI, false, null);

        ByteBuf buffer=parser.encode(ind);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        byte[] rawData = getEncodedData();
        assertTrue(Arrays.equals(rawData, encodedData));

        imsi = new IMSIImpl("100883344556677");
        sm_RP_DA = new SM_RP_DAImpl(imsi);
        sca = new AddressStringImpl(AddressNature.international_number, NumberingPlan.ISDN, "1111122222");
        sm_RP_OA = new SM_RP_OAImpl();
        sm_RP_OA.setServiceCentreAddressOA(sca);
        sm_RP_UI = new SmsSignalInfoImpl(new byte[] { 11, 22, 33, 44, 55, 66, 77 }, null);
        ind = new MtForwardShortMessageRequestImpl(sm_RP_DA, sm_RP_OA, sm_RP_UI, true,
                MAPExtensionContainerTest.GetTestExtensionContainer());

        buffer=parser.encode(ind);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        rawData = getEncodedDataFull();
        assertTrue(Arrays.equals(rawData, encodedData));
    }

}
