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

package org.restcomm.protocols.ss7.cap.primitives;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.api.primitives.EventTypeBCSM;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LegType;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MonitorMode;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.DpSpecificCriteriaImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.BCSMEventImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.LegIDImpl;
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
public class BCSMEventTest {

    public byte[] getData1() {
        return new byte[] { 48, 11, (byte) 128, 1, 6, (byte) 129, 1, 0, (byte) 162, 3, (byte) 128, 1, 2 };
    }

    public byte[] getData2() {
        return new byte[] { 48, 19, (byte) 128, 1, 5, (byte) 129, 1, 1, (byte) 162, 3, (byte) 129, 1, 1, (byte) 190, 3,
                (byte) 129, 1, 111, (byte) 159, 50, 0 };
    }

    @Test(groups = { "functional.decode", "primitives" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BCSMEventImpl.class);
    	
    	byte[] rawData = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof BCSMEventImpl);
        
        BCSMEventImpl elem = (BCSMEventImpl)result.getResult();
        assertEquals(elem.getEventTypeBCSM(), EventTypeBCSM.oNoAnswer);
        assertEquals(elem.getMonitorMode(), MonitorMode.interrupted);
        assertEquals(elem.getLegID().getSendingSideID(), LegType.leg2);
        assertNull(elem.getDpSpecificCriteria());
        assertFalse(elem.getAutomaticRearm());

        rawData = this.getData2();
        result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof BCSMEventImpl);
        
        elem = (BCSMEventImpl)result.getResult();
        assertEquals(elem.getEventTypeBCSM(), EventTypeBCSM.oCalledPartyBusy);
        assertEquals(elem.getMonitorMode(), MonitorMode.notifyAndContinue);
        assertEquals(elem.getLegID().getReceivingSideID(), LegType.leg1);
        assertEquals((int) elem.getDpSpecificCriteria().getApplicationTimer(), 111);
        assertTrue(elem.getAutomaticRearm());
    }

    @Test(groups = { "functional.encode", "primitives" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(BCSMEventImpl.class);
    	
        LegIDImpl legID = new LegIDImpl(null, LegType.leg2);
        BCSMEventImpl elem = new BCSMEventImpl(EventTypeBCSM.oNoAnswer, MonitorMode.interrupted, legID, null, false);
        byte[] rawData = this.getData1();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));

        legID = new LegIDImpl(LegType.leg1, null);
        DpSpecificCriteriaImpl dsc = new DpSpecificCriteriaImpl(111);
        elem = new BCSMEventImpl(EventTypeBCSM.oCalledPartyBusy, MonitorMode.notifyAndContinue, legID, dsc, true);
        rawData = this.getData2();
        buffer=parser.encode(elem);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));

        // EventTypeBCSM eventTypeBCSM, MonitorMode monitorMode, LegID legID, DpSpecificCriteria dpSpecificCriteria, boolean
        // automaticRearm
    }

    /*@Test(groups = { "functional.xml.serialize", "primitives" })
    public void testXMLSerialize() throws Exception {
        LegIDImpl legID = new LegIDImpl(true, LegType.leg2);
        DpSpecificCriteriaImpl dpc = new DpSpecificCriteriaImpl(111);
        BCSMEventImpl original = new BCSMEventImpl(EventTypeBCSM.oNoAnswer, MonitorMode.interrupted, legID, dpc, true);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "bcsmEvent", BCSMEventImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        BCSMEventImpl copy = reader.read("bcsmEvent", BCSMEventImpl.class);

        assertEquals(copy.getEventTypeBCSM(), original.getEventTypeBCSM());
        assertEquals(copy.getMonitorMode(), original.getMonitorMode());
        assertEquals(copy.getLegID().getReceivingSideID(), original.getLegID().getReceivingSideID());
        assertEquals((int) copy.getDpSpecificCriteria().getApplicationTimer(), (int) original.getDpSpecificCriteria()
                .getApplicationTimer());
        assertEquals(copy.getAutomaticRearm(), original.getAutomaticRearm());

        original = new BCSMEventImpl(EventTypeBCSM.oNoAnswer, MonitorMode.interrupted, null, null, false);

        // Writes the area to a file.
        baos = new ByteArrayOutputStream();
        writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "bcsmEvent", BCSMEventImpl.class);
        writer.close();

        rawData = baos.toByteArray();
        serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        bais = new ByteArrayInputStream(rawData);
        reader = XMLObjectReader.newInstance(bais);
        copy = reader.read("bcsmEvent", BCSMEventImpl.class);

        assertEquals(copy.getEventTypeBCSM(), original.getEventTypeBCSM());
        assertEquals(copy.getMonitorMode(), original.getMonitorMode());
        assertNull(copy.getLegID());
        assertNull(copy.getDpSpecificCriteria());
        assertEquals(copy.getAutomaticRearm(), original.getAutomaticRearm());
    }*/
}
