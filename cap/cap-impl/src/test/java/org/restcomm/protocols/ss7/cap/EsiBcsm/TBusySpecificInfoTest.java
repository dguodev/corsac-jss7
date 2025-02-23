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

import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.EventSpecificInformationBCSM;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.EventSpecificInformationBCSMImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.EventSpecificInformationBCSMWrapperImpl;
import org.restcomm.protocols.ss7.commonapp.api.isup.CalledPartyNumberIsup;
import org.restcomm.protocols.ss7.commonapp.isup.CalledPartyNumberIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.CauseIsupImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.CalledPartyNumberImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.CauseIndicatorsImpl;
import org.restcomm.protocols.ss7.isup.message.parameter.CalledPartyNumber;
import org.restcomm.protocols.ss7.isup.message.parameter.CauseIndicators;
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
public class TBusySpecificInfoTest {

    public byte[] getData1() {
        return new byte[] { 48, 22, -88, 20, (byte) 128, 2, (byte) 132, (byte) 144, (byte) 159, 50, 0, (byte) 159, 51, 0,
                (byte) 159, 52, 7, (byte) 128, (byte) 144, 17, 33, 34, 51, 3 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall.primitive" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(EventSpecificInformationBCSMWrapperImpl.class);
    	
    	byte[] rawData = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof EventSpecificInformationBCSMWrapperImpl);
        
        EventSpecificInformationBCSMWrapperImpl wrapper = (EventSpecificInformationBCSMWrapperImpl)result.getResult();
        EventSpecificInformationBCSM elem = wrapper.getEventSpecificInformationBCSM();
        CauseIndicators ci = elem.getTBusySpecificInfo().getBusyCause().getCauseIndicators();
        assertEquals(ci.getCauseValue(), 16);
        assertEquals(ci.getCodingStandard(), 0);
        assertEquals(ci.getLocation(), 4);
        assertTrue(elem.getTBusySpecificInfo().getCallForwarded());
        assertTrue(elem.getTBusySpecificInfo().getRouteNotPermitted());
        CalledPartyNumberIsup fdn = elem.getTBusySpecificInfo().getForwardingDestinationNumber();
        CalledPartyNumber cpn = fdn.getCalledPartyNumber();
        assertTrue(cpn.getAddress().endsWith("111222333"));
        assertEquals(cpn.getNatureOfAddressIndicator(), 0);
        assertEquals(cpn.getNumberingPlanIndicator(), 1);
        assertEquals(cpn.getInternalNetworkNumberIndicator(), 1);
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall.primitive" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(EventSpecificInformationBCSMWrapperImpl.class);
    	
        CauseIndicators causeIndicators = new CauseIndicatorsImpl(0, 4, 0, 16, null);
        CauseIsupImpl busyCause = new CauseIsupImpl(causeIndicators);
        CalledPartyNumberImpl calledPartyNumber = new CalledPartyNumberImpl(0, "111222333", 1, 1);
        CalledPartyNumberIsupImpl forwardingDestinationNumber = new CalledPartyNumberIsupImpl(calledPartyNumber);
        TBusySpecificInfoImpl tBusySpecificInfo = new TBusySpecificInfoImpl(busyCause, true, true, forwardingDestinationNumber);
        EventSpecificInformationBCSMImpl elem = new EventSpecificInformationBCSMImpl(tBusySpecificInfo);
        EventSpecificInformationBCSMWrapperImpl wrapper=new EventSpecificInformationBCSMWrapperImpl(elem);
        byte[] rawData = this.getData1();
        ByteBuf buffer=parser.encode(wrapper);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));

        // int natureOfAddresIndicator, String address, int
        // numberingPlanIndicator, int internalNetworkNumberIndicator
        // CauseCap busyCause, boolean callForwarded, boolean routeNotPermitted,
        // CalledPartyNumberCap forwardingDestinationNumber
    }

    /*@Test(groups = { "functional.xml.serialize", "circuitSwitchedCall.primitive" })
    public void testXMLSerializaion() throws Exception {
        CauseIndicators causeIndicators = new CauseIndicatorsImpl(0, 4, 0, 16, null);
        CauseCap busyCause = new CauseCapImpl(causeIndicators);
        CalledPartyNumberImpl calledPartyNumber = new CalledPartyNumberImpl(0, "111222333", 1, 1);
        CalledPartyNumberCapImpl forwardingDestinationNumber = new CalledPartyNumberCapImpl(calledPartyNumber);
        TBusySpecificInfoImpl original = new TBusySpecificInfoImpl(busyCause, true, true, forwardingDestinationNumber);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for
                                     // indentation).
        writer.write(original, "tBusySpecificInfo", TBusySpecificInfoImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        TBusySpecificInfoImpl copy = reader.read("tBusySpecificInfo", TBusySpecificInfoImpl.class);

        assertEquals(copy.getForwardingDestinationNumber().getCalledPartyNumber().getAddress(), original
                .getForwardingDestinationNumber().getCalledPartyNumber().getAddress());
        assertEquals(copy.getCallForwarded(), original.getCallForwarded());
        assertEquals(copy.getRouteNotPermitted(), original.getRouteNotPermitted());
        assertEquals(copy.getBusyCause().getCauseIndicators().getCauseValue(), original.getBusyCause().getCauseIndicators()
                .getCauseValue());
    }*/
}
