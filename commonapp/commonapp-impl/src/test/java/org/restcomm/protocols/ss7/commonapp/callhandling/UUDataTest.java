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

package org.restcomm.protocols.ss7.commonapp.callhandling;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerTest;
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
public class UUDataTest {

    public byte[] getData1() {
        return new byte[] { 48, 3, (byte) 128, 1, (byte) 140 };
    }

    public byte[] getData2() {
        return new byte[] { 48, 53, (byte) 128, 1, (byte) 140, (byte) 129, 5, 1, 2, 3, 4, 5, (byte) 130, 0, (byte) 163, 39, (byte) 160, 32, 48, 10, 6, 3, 42,
                3, 4, 11, 12, 13, 14, 15, 48, 5, 6, 3, 42, 3, 6, 48, 11, 6, 3, 42, 3, 5, 21, 22, 23, 24, 25, 26, (byte) 161, 3, 31, 32, 33 };
    }

    public byte[] getUUIData() {
        return new byte[] { 1, 2, 3, 4, 5 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall.primitive" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(UUDataImpl.class);

        byte[] data = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof UUDataImpl);
        UUDataImpl elem = (UUDataImpl)result.getResult();
        
        assertEquals(elem.getUUIndicator().getData(), new Integer(140));
        assertNull(elem.getUUI());
        assertFalse(elem.getUusCFInteraction());
        assertNull(elem.getExtensionContainer());


        data = this.getData2();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof UUDataImpl);
        elem = (UUDataImpl)result.getResult();

        assertEquals(elem.getUUIndicator().getData(), new Integer(140));
        assertEquals(elem.getUUI().getData(), getUUIData());
        assertTrue(elem.getUusCFInteraction());
        assertTrue(MAPExtensionContainerTest.CheckTestExtensionContainer(elem.getExtensionContainer()));
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall.primitive" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(UUDataImpl.class);

        UUIndicatorImpl uuIndicator = new UUIndicatorImpl(140);
        UUDataImpl elem = new UUDataImpl(uuIndicator, null, false, null);
        
        byte[] data=getData1();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));

        UUIImpl uuI = new UUIImpl(getUUIData());
        elem = new UUDataImpl(uuIndicator, uuI, true, MAPExtensionContainerTest.GetTestExtensionContainer());

        data=getData2();
        buffer=parser.encode(elem);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));
    }
}