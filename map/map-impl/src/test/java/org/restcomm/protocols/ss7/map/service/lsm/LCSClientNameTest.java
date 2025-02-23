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

import org.restcomm.protocols.ss7.map.MAPParameterFactoryImpl;
import org.restcomm.protocols.ss7.map.api.MAPParameterFactory;
import org.restcomm.protocols.ss7.map.api.primitives.USSDString;
import org.restcomm.protocols.ss7.map.api.service.lsm.LCSFormatIndicator;
import org.restcomm.protocols.ss7.map.datacoding.CBSDataCodingSchemeImpl;
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
public class LCSClientNameTest {

    MAPParameterFactory MAPParameterFactory = new MAPParameterFactoryImpl();

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
        return new byte[] { 0x30, 0x13, (byte) 0x80, 0x01, 0x0f, (byte) 0x82, 0x0e, 0x6e, 0x72, (byte) 0xfb, 0x1c, (byte) 0x86,
                (byte) 0xc3, 0x65, 0x6e, 0x72, (byte) 0xfb, 0x1c, (byte) 0x86, (byte) 0xc3, 0x65 };
    }

    public byte[] getDataFull() {
        return new byte[] { 48, 22, -128, 1, 15, -126, 14, 110, 114, -5, 28, -122, -61, 101, 110, 114, -5, 28, -122, -61, 101,
                -125, 1, 2 };
    }

    @Test(groups = { "functional.decode", "service.lsm" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(LCSClientNameImpl.class);
    	
        byte[] data = getData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof LCSClientNameImpl);
        LCSClientNameImpl lcsClientName = (LCSClientNameImpl)result.getResult();

        assertEquals(lcsClientName.getDataCodingScheme().getCode(), 0x0f);
        assertNotNull(lcsClientName.getNameString());
        assertTrue(lcsClientName.getNameString().getString(null).equals("ndmgapp2ndmgapp2"));

        assertNull(lcsClientName.getLCSFormatIndicator());

        data = getDataFull();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof LCSClientNameImpl);
        lcsClientName = (LCSClientNameImpl)result.getResult();

        assertEquals(lcsClientName.getDataCodingScheme().getCode(), 0x0f);
        assertNotNull(lcsClientName.getNameString());
        assertTrue(lcsClientName.getNameString().getString(null).equals("ndmgapp2ndmgapp2"));

        assertEquals(lcsClientName.getLCSFormatIndicator(), LCSFormatIndicator.msisdn);
    }

    @Test(groups = { "functional.encode", "service.lsm" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(LCSClientNameImpl.class);
    	
        byte[] data = getData();

        USSDString nameString = MAPParameterFactory.createUSSDString("ndmgapp2ndmgapp2");
        LCSClientNameImpl lcsClientName = new LCSClientNameImpl(new CBSDataCodingSchemeImpl(0x0f), nameString, null);
        ByteBuf buffer=parser.encode(lcsClientName);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));

        data = getDataFull();

        lcsClientName = new LCSClientNameImpl(new CBSDataCodingSchemeImpl(0x0f), nameString, LCSFormatIndicator.msisdn);
        buffer=parser.encode(lcsClientName);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));
    }
}