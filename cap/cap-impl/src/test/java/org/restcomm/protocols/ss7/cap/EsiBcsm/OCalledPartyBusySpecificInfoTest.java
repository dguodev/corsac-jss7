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

package org.restcomm.protocols.ss7.cap.EsiBcsm;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.isup.CauseIsupImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.CauseIndicatorsImpl;
import org.restcomm.protocols.ss7.isup.message.parameter.CauseIndicators;
import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Amit Bhayani
 *
 */
public class OCalledPartyBusySpecificInfoTest {

    public byte[] getData() {
        return new byte[] { 48, 4, (byte) 128, 2, (byte) 195, (byte) 128 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall.primitive" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(OCalledPartyBusySpecificInfoImpl.class);
    	
    	byte[] rawData = this.getData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof OCalledPartyBusySpecificInfoImpl);
        
        OCalledPartyBusySpecificInfoImpl elem = (OCalledPartyBusySpecificInfoImpl)result.getResult();        
        assertEquals(elem.getBusyCause().getCauseIndicators().getCodingStandard(), CauseIndicators._CODING_STANDARD_NATIONAL);
        assertEquals(elem.getBusyCause().getCauseIndicators().getLocation(), CauseIndicators._LOCATION_TRANSIT_NETWORK);
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall.primitive" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(OCalledPartyBusySpecificInfoImpl.class);
    	
    	CauseIndicators causeIndicators = new CauseIndicatorsImpl(CauseIndicators._CODING_STANDARD_NATIONAL, CauseIndicators._LOCATION_TRANSIT_NETWORK, 0, 0,
                null);
//        int codingStandard, int location, int recommendation, int causeValue, byte[] diagnostics
        CauseIsupImpl cause = new CauseIsupImpl(causeIndicators);
        OCalledPartyBusySpecificInfoImpl elem = new OCalledPartyBusySpecificInfoImpl(cause);
        byte[] rawData = this.getData();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));

    }

    /*@Test(groups = { "functional.xml.serialize", "circuitSwitchedCall.primitive" })
    public void testXMLSerializaion() throws Exception {

        CauseIndicators causeIndicators = new CauseIndicatorsImpl(CauseIndicators._CODING_STANDARD_NATIONAL, CauseIndicators._LOCATION_TRANSIT_NETWORK, 0, 0,
                null);
        CauseCapImpl cause = new CauseCapImpl(causeIndicators);
        OCalledPartyBusySpecificInfoImpl original = new OCalledPartyBusySpecificInfoImpl(cause);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for
                                     // indentation).
        writer.write(original, "OCalledPartyBusySpecificInfoImpl", OCalledPartyBusySpecificInfoImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        OCalledPartyBusySpecificInfoImpl copy = reader.read("OCalledPartyBusySpecificInfoImpl",
                OCalledPartyBusySpecificInfoImpl.class);

        assertEquals(copy.getBusyCause().getCauseIndicators().getLocation(), original.getBusyCause().getCauseIndicators()
                .getLocation());
        assertEquals(copy.getBusyCause().getCauseIndicators().getCauseValue(), original.getBusyCause().getCauseIndicators()
                .getCauseValue());
        assertEquals(copy.getBusyCause().getCauseIndicators().getCodingStandard(), original.getBusyCause().getCauseIndicators()
                .getCodingStandard());

    }*/
}
