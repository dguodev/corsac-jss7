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

package org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.cap.primitives.CAPExtensionsTest;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LegType;
import org.restcomm.protocols.ss7.commonapp.primitives.LegIDImpl;
import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author kostianyn nosach
 *
 */
public class SplitLegRequestTest {
    
    public byte[] getData() {
        return new byte[] { 48, 28, -96, 3, -128, 1, 1, -127, 1, 1, -94, 18, 
                48, 5, 2, 1, 2, -127, 0, 48, 9, 2, 1, 3, 10, 1, 1, -127, 1, -1};
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(SplitLegRequestImpl.class);
    	
    	byte[] rawData = this.getData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof SplitLegRequestImpl);
        
        SplitLegRequestImpl elem = (SplitLegRequestImpl)result.getResult();                
        assertTrue(elem.getLegToBeSplit().getSendingSideID().equals(LegType.leg1));
        assertEquals(elem.getNewCallSegment(), new Integer(1));
        assertTrue(CAPExtensionsTest.checkTestCAPExtensions(elem.getExtensions()));
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(SplitLegRequestImpl.class);
    	
        LegIDImpl legIDToMove = new LegIDImpl(null,LegType.leg1);

        SplitLegRequestImpl elem = new SplitLegRequestImpl(legIDToMove, 1, CAPExtensionsTest.createTestCAPExtensions());
        byte[] rawData = this.getData();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

    /*@Test(groups = { "functional.xml.serialize", "circuitSwitchedCall" })
    public void testXMLSerialize() throws Exception {

        LegID legIDToMove = new LegIDImpl(true, LegType.leg1);
        SplitLegRequestImpl original = new SplitLegRequestImpl(legIDToMove, 1, CAPExtensionsTest.createTestCAPExtensions());

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for indentation).
        writer.write(original, "splitLegRequest", SplitLegRequestImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        SplitLegRequestImpl copy = reader.read("splitLegRequest", SplitLegRequestImpl.class);

        assertEquals(copy.getInvokeId(), original.getInvokeId());
        assertEquals(copy.getExtensions().getExtensionFields().get(0).getLocalCode(), original.getExtensions().getExtensionFields().get(0).getLocalCode());
        assertEquals(copy.getExtensions().getExtensionFields().get(1).getCriticalityType(), original.getExtensions().getExtensionFields().get(1).getCriticalityType());
        assertEquals(copy.getLegToBeSplit().getSendingSideID(), original.getLegToBeSplit().getSendingSideID());
        assertEquals(copy.getNewCallSegment(), original.getNewCallSegment());
    }*/
}
