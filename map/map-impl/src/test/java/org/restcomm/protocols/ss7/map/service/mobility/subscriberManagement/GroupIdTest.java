/*
 * TeleStax, Open Source Cloud Communications  Copyright 2012.
 * and individual contributors
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
package org.restcomm.protocols.ss7.map.service.mobility.subscriberManagement;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
public class GroupIdTest {

    public byte[] getData() {
        return new byte[] { 4, 3, 33, 67, 101 };
    };

    public byte[] getData2() {
        return new byte[] { 4, 3, 33, 67, -11 };
    };

    public byte[] getData3() {
        return new byte[] { 4, 3, 33, 67, -1 };
    };

    public byte[] getData4() {
        return new byte[] { 4, 3, 33, -13, -1 };
    };

    public byte[] getData5() {
        return new byte[] { 4, 3, 33, -1, -1 };
    };

    public byte[] getData6() {
        return new byte[] { 4, 3, -15, -1, -1 };
    };

    public byte[] getData7() {
        return new byte[] { 4, 3, -1, -1, -1 };
    };

    @Test(groups = { "functional.decode", "primitives" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(GroupIdImpl.class);
    	
        // option 1
        byte[] data = this.getData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GroupIdImpl);
        GroupIdImpl prim = (GroupIdImpl)result.getResult();
        assertTrue(prim.getGroupId().equals("123456"));

        // option 2
        data = this.getData2();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GroupIdImpl);
        prim = (GroupIdImpl)result.getResult();
        assertTrue(prim.getGroupId().equals("12345"));

        // option 3
        data = this.getData3();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GroupIdImpl);
        prim = (GroupIdImpl)result.getResult();
        assertTrue(prim.getGroupId().equals("1234"));

        // option 4
        data = this.getData4();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GroupIdImpl);
        prim = (GroupIdImpl)result.getResult();
        assertTrue(prim.getGroupId().equals("123"));

        // option 5
        data = this.getData5();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GroupIdImpl);
        prim = (GroupIdImpl)result.getResult();
        assertTrue(prim.getGroupId().equals("12"));

        // option 6
        data = this.getData6();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GroupIdImpl);
        prim = (GroupIdImpl)result.getResult();
        assertTrue(prim.getGroupId().equals("1"));

        // option 7
        data = this.getData7();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof GroupIdImpl);
        prim = (GroupIdImpl)result.getResult();
        assertTrue(prim.getGroupId().equals(""));

    }

    @Test(groups = { "functional.encode", "primitives" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(GroupIdImpl.class);
    	
        // option 1
        GroupIdImpl prim = new GroupIdImpl("123456");
        ByteBuf buffer=parser.encode(prim);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);       
        assertTrue(Arrays.equals(encodedData, this.getData()));

        // option 2
        prim = new GroupIdImpl("12345");
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(encodedData, this.getData2()));

        // option 3
        prim = new GroupIdImpl("1234");
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(encodedData, this.getData3()));

        // option 4
        prim = new GroupIdImpl("123");
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(encodedData, this.getData4()));

        // option 5
        prim = new GroupIdImpl("12");
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(encodedData, this.getData5()));

        // option 6
        prim = new GroupIdImpl("1");
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(encodedData, this.getData6()));

        // option 7
        prim = new GroupIdImpl("");
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(encodedData, this.getData7()));
    }

}
