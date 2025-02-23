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

package org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.cap.primitives.CAPExtensionsTest;
import org.restcomm.protocols.ss7.commonapp.api.primitives.BothwayThroughConnectionInd;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.CarrierImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.NAOliInfoImpl;
import org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall.ServiceInteractionIndicatorsTwoImpl;
import org.restcomm.protocols.ss7.commonapp.isup.CallingPartyNumberIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.DigitsIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.LocationNumberIsupImpl;
import org.restcomm.protocols.ss7.commonapp.isup.OriginalCalledNumberIsupImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.ScfIDImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.CallingPartyNumberImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.GenericDigitsImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.GenericNumberImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.LocationNumberImpl;
import org.restcomm.protocols.ss7.isup.impl.message.parameter.OriginalCalledNumberImpl;
import org.restcomm.protocols.ss7.isup.message.parameter.CallingPartyNumber;
import org.restcomm.protocols.ss7.isup.message.parameter.LocationNumber;
import org.restcomm.protocols.ss7.isup.message.parameter.OriginalCalledNumber;
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
public class EstablishTemporaryConnectionTest {

    public byte[] getData1() {
        return new byte[] { 48, 49, (byte) 128, 5, 1, 1, 1, 17, 34, (byte) 129, 5, 64, 1, 2, 3, 4, (byte) 131, 4, 5, 6, 7, 8,
                (byte) 164, 18, 48, 5, 2, 1, 2, (byte) 129, 0, 48, 9, 2, 1, 3, 10, 1, 1, (byte) 129, 1, (byte) 255, (byte) 167,
                3, (byte) 130, 1, 1, (byte) 159, 50, 1, 11 };
    }

    public byte[] getData2() {
        return new byte[] { 48, 52, (byte) 128, 5, 1, 1, 1, 17, 34, (byte) 129, 5, 64, 1, 2, 3, 4, (byte) 131, 4, 5, 6, 7, 8,
                (byte) 164, 18, 48, 5, 2, 1, 2, (byte) 129, 0, 48, 9, 2, 1, 3, 10, 1, 1, (byte) 129, 1, (byte) 255, (byte) 166,
                3, (byte) 130, 1, 1, (byte) 135, 1, 8, (byte) 159, 50, 1, 11 };
    }

    public byte[] getData3() {
        return new byte[] { 48, 43, (byte) 128, 5, 1, 1, 1, 17, 34, (byte) 133, 4, 11, 12, 13, 14, (byte) 159, 51, 7, 4, 0, 0, 0, 112, 119, 119, (byte) 159,
                52, 7, 4, 0, 17, 17, (byte) 129, (byte) 136, (byte) 136, (byte) 159, 53, 7, 4, 0, 34, 34, (byte) 130, (byte) 136, (byte) 136 };
    }

    public byte[] getCorrelationIDDigits() {
        return new byte[] { 1, 2, 3, 4 };
    }

    public byte[] getScfIDData() {
        return new byte[] { 5, 6, 7, 8 };
    }

    public byte[] getCarrierData() {
        return new byte[] { 11, 12, 13, 14 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(EstablishTemporaryConnectionRequestImpl.class);
    	
    	byte[] rawData = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof EstablishTemporaryConnectionRequestImpl);
        
        EstablishTemporaryConnectionRequestImpl elem = (EstablishTemporaryConnectionRequestImpl)result.getResult();        
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getNatureOfAddressIndicator(), 1);
        assertTrue(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getAddress().equals("1122"));
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getNumberQualifierIndicator(), 1);
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getNumberingPlanIndicator(), 0);
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getAddressRepresentationRestrictedIndicator(), 0);
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getScreeningIndicator(), 1);
        assertEquals(elem.getCorrelationID().getGenericDigits().getEncodingScheme(), 2);
        assertEquals(elem.getCorrelationID().getGenericDigits().getTypeOfDigits(), 0);
        
        ByteBuf buffer=elem.getCorrelationID().getGenericDigits().getEncodedDigits();
        assertNotNull(buffer);
        byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        assertTrue(Arrays.equals(data, getCorrelationIDDigits()));
        assertTrue(Arrays.equals(elem.getScfID().getData(), getScfIDData()));
        assertEquals(elem.getServiceInteractionIndicatorsTwo().getBothwayThroughConnectionInd(),
                BothwayThroughConnectionInd.bothwayPathNotRequired);
        assertNull(elem.getCallSegmentID());
        assertEquals((int) elem.getNAOliInfo().getData(), 11);
        assertTrue(CAPExtensionsTest.checkTestCAPExtensions(elem.getExtensions()));
        assertNull(elem.getCarrier());
        assertNull(elem.getChargeNumber());
        assertNull(elem.getOriginalCalledPartyID());
        assertNull(elem.getCallingPartyNumber());

        rawData = this.getData2();
        result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof EstablishTemporaryConnectionRequestImpl);
        
        elem = (EstablishTemporaryConnectionRequestImpl)result.getResult();  
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getNatureOfAddressIndicator(), 1);
        assertTrue(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getAddress().equals("1122"));
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getNumberQualifierIndicator(), 1);
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getNumberingPlanIndicator(), 0);
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getAddressRepresentationRestrictedIndicator(), 0);
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getScreeningIndicator(), 1);
        assertEquals(elem.getCorrelationID().getGenericDigits().getEncodingScheme(), 2);
        assertEquals(elem.getCorrelationID().getGenericDigits().getTypeOfDigits(), 0);
        
        buffer=elem.getCorrelationID().getGenericDigits().getEncodedDigits();
        assertNotNull(buffer);
        data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        assertTrue(Arrays.equals(data, getCorrelationIDDigits()));
        assertTrue(Arrays.equals(elem.getScfID().getData(), getScfIDData()));
        assertEquals(elem.getServiceInteractionIndicatorsTwo().getBothwayThroughConnectionInd(),
                BothwayThroughConnectionInd.bothwayPathNotRequired);
        assertEquals((int) elem.getCallSegmentID(), 8);
        assertEquals((int) elem.getNAOliInfo().getData(), 11);
        assertTrue(CAPExtensionsTest.checkTestCAPExtensions(elem.getExtensions()));
        assertNull(elem.getCarrier());
        assertNull(elem.getChargeNumber());
        assertNull(elem.getOriginalCalledPartyID());
        assertNull(elem.getCallingPartyNumber());

        rawData = this.getData3();
        result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof EstablishTemporaryConnectionRequestImpl);
        
        elem = (EstablishTemporaryConnectionRequestImpl)result.getResult();  
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getNatureOfAddressIndicator(), 1);
        assertTrue(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getAddress().equals("1122"));
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getNumberQualifierIndicator(), 1);
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getNumberingPlanIndicator(), 0);
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getAddressRepresentationRestrictedIndicator(), 0);
        assertEquals(elem.getAssistingSSPIPRoutingAddress().getGenericNumber().getScreeningIndicator(), 1);
        assertNull(elem.getCorrelationID());
        assertNull(elem.getScfID());
        assertNull(elem.getCallSegmentID());
        assertNull(elem.getNAOliInfo());
        assertNull(elem.getExtensions());
        assertEquals(elem.getCarrier().getData(), getCarrierData());
        assertEquals(elem.getChargeNumber().getLocationNumber().getNatureOfAddressIndicator(), LocationNumber._NAI_INTERNATIONAL_NUMBER);
        assertEquals(elem.getChargeNumber().getLocationNumber().getAddress(), "0000077777");
        assertEquals(elem.getOriginalCalledPartyID().getOriginalCalledNumber().getAddress(), "1111188888");
        assertEquals(elem.getCallingPartyNumber().getCallingPartyNumber().getAddress(), "2222288888");
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(EstablishTemporaryConnectionRequestImpl.class);
    	
        GenericNumberImpl genericNumber = new GenericNumberImpl(1, "1122", 1, 0, 0, false, 1);
        // int natureOfAddresIndicator, String address, int numberQualifierIndicator, int numberingPlanIndicator, int
        // addressRepresentationREstrictedIndicator,
        // boolean numberIncomplete, int screeningIndicator
        DigitsIsupImpl assistingSSPIPRoutingAddress = new DigitsIsupImpl(genericNumber);
        GenericDigitsImpl genericDigits = new GenericDigitsImpl(2, 0, Unpooled.wrappedBuffer(getCorrelationIDDigits()));
        // int encodingScheme, int typeOfDigits, int[] digits
        DigitsIsupImpl correlationID = new DigitsIsupImpl(genericDigits);
        ScfIDImpl scfID = new ScfIDImpl(getScfIDData());
        ServiceInteractionIndicatorsTwoImpl serviceInteractionIndicatorsTwo = new ServiceInteractionIndicatorsTwoImpl(null,
                null, BothwayThroughConnectionInd.bothwayPathNotRequired, null, false, null, null, null);
        // ForwardServiceInteractionInd forwardServiceInteractionInd,
        // BackwardServiceInteractionInd backwardServiceInteractionInd, BothwayThroughConnectionInd bothwayThroughConnectionInd,
        // ConnectedNumberTreatmentInd connectedNumberTreatmentInd, boolean nonCUGCall, HoldTreatmentIndicator
        // holdTreatmentIndicator,
        // CwTreatmentIndicator cwTreatmentIndicator, EctTreatmentIndicator ectTreatmentIndicator
        NAOliInfoImpl naOliInfo = new NAOliInfoImpl(11);

        EstablishTemporaryConnectionRequestImpl elem = new EstablishTemporaryConnectionRequestImpl(
                assistingSSPIPRoutingAddress, correlationID, scfID, CAPExtensionsTest.createTestCAPExtensions(), null,
                serviceInteractionIndicatorsTwo, naOliInfo, null, null, null);
        byte[] rawData = this.getData1();
        ByteBuf buffer=parser.encode(elem);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));

        // Digits assistingSSPIPRoutingAddress, Digits correlationID, ScfID scfID, CAPExtensions extensions,
        // Carrier carrier, ServiceInteractionIndicatorsTwo serviceInteractionIndicatorsTwo, Integer callSegmentID, NAOliInfo
        // naOliInfo,
        // LocationNumberCap chargeNumber, OriginalCalledNumberCap originalCalledPartyID, CallingPartyNumberCap
        // callingPartyNumber,
        // boolean isCAPVersion3orLater

        elem = new EstablishTemporaryConnectionRequestImpl(assistingSSPIPRoutingAddress, correlationID, scfID,
                CAPExtensionsTest.createTestCAPExtensions(), null, serviceInteractionIndicatorsTwo, 8, naOliInfo, null, null,
                null);
        rawData = this.getData2();
        buffer=parser.encode(elem);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));

        CarrierImpl carrier = new CarrierImpl(getCarrierData());
        LocationNumber locationNumber = new LocationNumberImpl();
        locationNumber.setNatureOfAddresIndicator(LocationNumber._NAI_INTERNATIONAL_NUMBER);
        locationNumber.setAddress("0000077777");
        LocationNumberIsupImpl chargeNumber = new LocationNumberIsupImpl(locationNumber);
        OriginalCalledNumber originalCalledNumber = new OriginalCalledNumberImpl(LocationNumber._NAI_INTERNATIONAL_NUMBER, "1111188888", 0, 0);
        // int natureOfAddresIndicator, String address, int numberingPlanIndicator,
        // int addressRepresentationRestrictedIndicator
        OriginalCalledNumberIsupImpl originalCalledPartyID = new OriginalCalledNumberIsupImpl(originalCalledNumber);
        CallingPartyNumber callingPartyNumber0 = new CallingPartyNumberImpl(LocationNumber._NAI_INTERNATIONAL_NUMBER, "2222288888", 0, 0, 0, 0);
        CallingPartyNumberIsupImpl callingPartyNumber = new CallingPartyNumberIsupImpl(callingPartyNumber0);
        elem = new EstablishTemporaryConnectionRequestImpl(assistingSSPIPRoutingAddress, null, null, null, carrier, null, null, null, chargeNumber,
                originalCalledPartyID, callingPartyNumber);
        rawData = this.getData3();
        buffer=parser.encode(elem);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData)); 
    }
}
