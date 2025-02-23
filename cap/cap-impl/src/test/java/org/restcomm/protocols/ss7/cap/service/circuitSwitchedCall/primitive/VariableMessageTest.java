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

package org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.VariablePart;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.VariableMessageImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.VariablePartImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.VariablePartTimeImpl;
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
public class VariableMessageTest {

    public byte[] getData1() {
        return new byte[] { 48, 13, (byte) 128, 2, 3, 32, (byte) 161, 7, (byte) 128, 1, 111, (byte) 130, 2, 50, (byte) 149 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall.primitive" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(VariableMessageImpl.class);
    	
    	byte[] rawData = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof VariableMessageImpl);
        
        VariableMessageImpl elem = (VariableMessageImpl)result.getResult();                
        assertEquals(elem.getElementaryMessageID(), 800);
        assertEquals(elem.getVariableParts().size(), 2);
        assertEquals((int) elem.getVariableParts().get(0).getInteger(), 111);
        assertEquals((int) elem.getVariableParts().get(1).getTime().getHour(), 23);
        assertEquals((int) elem.getVariableParts().get(1).getTime().getMinute(), 59);
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall.primitive" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(VariableMessageImpl.class);
    	
        List<VariablePart> variableParts = new ArrayList<VariablePart>();
        VariablePartImpl vp = new VariablePartImpl(111);
        variableParts.add(vp);
        VariablePartTimeImpl time = new VariablePartTimeImpl(23, 59);
        vp = new VariablePartImpl(time);
        variableParts.add(vp);

        VariableMessageImpl elem = new VariableMessageImpl(800, variableParts);
        byte[] rawData = this.getData1();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));

        // int elementaryMessageID, ArrayList<VariablePart> variableParts
    }

    /*@Test(groups = { "functional.xml.serialize", "circuitSwitchedCall" })
    public void testXMLSerialize() throws Exception {

        int elementaryMessageID = 2;
        ArrayList<VariablePart> variableParts = new ArrayList<VariablePart>();
        int integer = 14;
        VariablePart vp = new VariablePartImpl(integer);
        variableParts.add(vp);
        int hour = 22;
        int minute = 31;
        VariablePartTime time = new VariablePartTimeImpl(hour, minute);
        vp = new VariablePartImpl(time);
        variableParts.add(vp);
        VariableMessageImpl original = new VariableMessageImpl(elementaryMessageID, variableParts);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        writer.setIndentation("\t");
        writer.write(original, "variableMessage", VariableMessageImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        VariableMessageImpl copy = reader.read("variableMessage", VariableMessageImpl.class);

        assertEquals(copy.getElementaryMessageID(), elementaryMessageID);
        ArrayList<VariablePart> vps = copy.getVariableParts();
        assertEquals(vps.size(), 2);
        VariablePart vp1 = vps.get(0);
        assertEquals((int)vp1.getInteger(), integer);
        VariablePart vp2 = vps.get(1);
        assertEquals(vp2.getTime().getHour(), hour);
        assertEquals(vp2.getTime().getMinute(), minute);
    }*/
}
