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

package org.restcomm.protocols.ss7.map.service.lsm;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Arrays;

import org.restcomm.protocols.ss7.map.api.MAPException;
import org.restcomm.protocols.ss7.map.api.service.lsm.AreaType;
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
public class AreaIdentificationTest {

    public byte[] getData1() {
        return new byte[] { 4, 2, 82, (byte) 240 };
    };

    public byte[] getData1Val() {
        return new byte[] { 82, (byte) 240 };
    };

    public byte[] getData2() {
        return new byte[] { 4, 3, 82, (byte) 240, 112 };
    };

    public byte[] getData3() {
        return new byte[] { 4, 5, 82, (byte) 128, 118, 17, 92 };
    };

    public byte[] getData4() {
        return new byte[] { 4, 6, 82, (byte) 128, 118, 17, 92, (byte) 200 };
    };

    public byte[] getData5() {
        return new byte[] { 4, 7, 82, (byte) 128, 118, 17, 92, (byte) 214, (byte) 216 };
    };

    public byte[] getData6() {
        return new byte[] { 4, 7, 82, (byte) 128, 118, (byte) 248, 0, 0, 1 };
    };

    @Test(groups = { "functional.decode", "service.lsm" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(AreaIdentificationImpl.class);
    	
        byte[] data = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof AreaIdentificationImpl);
        AreaIdentificationImpl prim = (AreaIdentificationImpl)result.getResult();
        
        assertNotNull(prim.getData());
        assertTrue(Arrays.equals(getData1Val(), prim.getData()));

        assertEquals(prim.getMCC(), 250);
        try {
            prim.getMNC();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getLac();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getCellId();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getRac();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getUtranCellId();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        // assertEquals(prim.getMNC(), 1);
        // assertEquals(prim.getLac(), 4444);
        // assertEquals(prim.getCellId(), 3333);
        // prim.getRac();
        // prim.getUtranCellId();

        data = this.getData2();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof AreaIdentificationImpl);
        prim = (AreaIdentificationImpl)result.getResult();

        assertNotNull(prim.getData());

        assertEquals(prim.getMCC(), 250);
        assertEquals(prim.getMNC(), 7);
        try {
            prim.getLac();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getCellId();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getRac();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getUtranCellId();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        // assertEquals(prim.getLac(), 4444);
        // assertEquals(prim.getCellId(), 3333);
        // prim.getRac();
        // prim.getUtranCellId();

        data = this.getData3();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof AreaIdentificationImpl);
        prim = (AreaIdentificationImpl)result.getResult();
        
        assertNotNull(prim.getData());

        assertEquals(prim.getMCC(), 250);
        assertEquals(prim.getMNC(), 678);
        assertEquals(prim.getLac(), 4444);
        try {
            prim.getCellId();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getRac();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getUtranCellId();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        // assertEquals(prim.getCellId(), 3333);
        // prim.getRac();
        // prim.getUtranCellId();

        data = this.getData4();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof AreaIdentificationImpl);
        prim = (AreaIdentificationImpl)result.getResult();

        assertNotNull(prim.getData());

        assertEquals(prim.getMCC(), 250);
        assertEquals(prim.getMNC(), 678);
        assertEquals(prim.getLac(), 4444);
        assertEquals(prim.getRac(), 200);
        try {
            prim.getCellId();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        try {
            prim.getUtranCellId();
            fail("Must be exeption");
        } catch (MAPException ee) {
        }
        // assertEquals(prim.getCellId(), 3333);
        // prim.getUtranCellId();

        data = this.getData5();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof AreaIdentificationImpl);
        prim = (AreaIdentificationImpl)result.getResult();

        assertNotNull(prim.getData());

        assertEquals(prim.getMCC(), 250);
        assertEquals(prim.getMNC(), 678);
        assertEquals(prim.getLac(), 4444);
        assertEquals(prim.getCellId(), 55000);

        data = this.getData6();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof AreaIdentificationImpl);
        prim = (AreaIdentificationImpl)result.getResult();

        assertNotNull(prim.getData());

        assertEquals(prim.getMCC(), 250);
        assertEquals(prim.getMNC(), 678);
        assertEquals(prim.getUtranCellId(), (int) (4160749569L));
    }

    @Test(groups = { "functional.decode", "service.lsm" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(AreaIdentificationImpl.class);
    	
        AreaIdentificationImpl prim = new AreaIdentificationImpl(AreaType.countryCode, 250, 0, 0, 0);
        byte[] data=getData1();
        ByteBuf buffer=parser.encode(prim);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));

        prim = new AreaIdentificationImpl(getData1Val());
        data=getData1();
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));

        prim = new AreaIdentificationImpl(AreaType.plmnId, 250, 7, 0, 0);
        data=getData2();
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));       

        prim = new AreaIdentificationImpl(AreaType.locationAreaId, 250, 678, 4444, 0);
        data=getData3();
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));       

        prim = new AreaIdentificationImpl(AreaType.routingAreaId, 250, 678, 4444, 200);
        data=getData4();
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));       

        prim = new AreaIdentificationImpl(AreaType.cellGlobalId, 250, 678, 4444, 55000);
        data=getData5();
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));       

        prim = new AreaIdentificationImpl(AreaType.utranCellId, 250, 678, 0, (int) (4160749569L));
        data=getData6();
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));       
    }
}
