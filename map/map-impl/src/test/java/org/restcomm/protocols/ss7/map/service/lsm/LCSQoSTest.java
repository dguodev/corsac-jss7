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

package org.restcomm.protocols.ss7.map.service.lsm;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerTest;
import org.restcomm.protocols.ss7.map.api.service.lsm.ResponseTime;
import org.restcomm.protocols.ss7.map.api.service.lsm.ResponseTimeCategory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author amit bhayani
 *
 */
public class LCSQoSTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeTest
    public void setUp() {
    }

    @AfterTest
    public void tearDown() {
    }

    public byte[] getData() {
        return new byte[] { 0x30, 0x05, (byte) 0xa3, 0x03, 0x0a, 0x01, 0x00 };
    }

    public byte[] getDataFull() {
        return new byte[] { 48, 54, -128, 1, 10, -127, 0, -126, 1, 20, -93, 3, 10, 1, 0, -92, 39, -96, 32, 48, 10, 6, 3, 42, 3,
                4, 11, 12, 13, 14, 15, 48, 5, 6, 3, 42, 3, 6, 48, 11, 6, 3, 42, 3, 5, 21, 22, 23, 24, 25, 26, -95, 3, 31, 32,
                33 };
    }

    @Test(groups = { "functional.decode", "service.lsm" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(LCSQoSImpl.class);
    	
        byte[] data = getData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof LCSQoSImpl);
        LCSQoSImpl lcsQos = (LCSQoSImpl)result.getResult();

        assertNotNull(lcsQos.getResponseTime());
        ResponseTime resTime = lcsQos.getResponseTime();
        assertNotNull(resTime.getResponseTimeCategory());
        assertEquals(resTime.getResponseTimeCategory(), ResponseTimeCategory.lowdelay);

        assertNull(lcsQos.getHorizontalAccuracy());
        assertFalse(lcsQos.getVerticalCoordinateRequest());
        assertNull(lcsQos.getVerticalAccuracy());
        assertNull(lcsQos.getExtensionContainer());

        data = getDataFull();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof LCSQoSImpl);
        lcsQos = (LCSQoSImpl)result.getResult();

        assertNotNull(lcsQos.getResponseTime());
        resTime = lcsQos.getResponseTime();
        assertNotNull(resTime.getResponseTimeCategory());
        assertEquals(resTime.getResponseTimeCategory(), ResponseTimeCategory.lowdelay);

        assertEquals((int) lcsQos.getHorizontalAccuracy(), 10);
        assertTrue(lcsQos.getVerticalCoordinateRequest());
        assertEquals((int) lcsQos.getVerticalAccuracy(), 20);
        assertTrue(MAPExtensionContainerTest.CheckTestExtensionContainer(lcsQos.getExtensionContainer()));
    }

    @Test(groups = { "functional.encode", "service.lsm" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(LCSQoSImpl.class);
    	
        byte[] data = getData();

        LCSQoSImpl lcsQos = new LCSQoSImpl(null, null, false, new ResponseTimeImpl(ResponseTimeCategory.lowdelay), null);
        ByteBuf buffer=parser.encode(lcsQos);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));

        data = getDataFull();

        lcsQos = new LCSQoSImpl(10, 20, true, new ResponseTimeImpl(ResponseTimeCategory.lowdelay),
                MAPExtensionContainerTest.GetTestExtensionContainer());
        buffer=parser.encode(lcsQos);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));
    }
}