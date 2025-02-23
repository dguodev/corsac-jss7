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
package org.restcomm.protocols.ss7.map.service.mobility.locationManagement;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restcomm.protocols.ss7.commonapp.api.primitives.MAPExtensionContainer;
import org.restcomm.protocols.ss7.commonapp.primitives.IMSIImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerTest;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.AuthenticationTriplet;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.GSMSecurityContextData;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.TripletList;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.UMTSSecurityContextData;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.SendIdentificationResponse;
import org.restcomm.protocols.ss7.map.service.mobility.authentication.AuthenticationSetListImpl;
import org.restcomm.protocols.ss7.map.service.mobility.authentication.AuthenticationTripletImpl;
import org.restcomm.protocols.ss7.map.service.mobility.authentication.CksnImpl;
import org.restcomm.protocols.ss7.map.service.mobility.authentication.CurrentSecurityContextImpl;
import org.restcomm.protocols.ss7.map.service.mobility.authentication.GSMSecurityContextDataImpl;
import org.restcomm.protocols.ss7.map.service.mobility.authentication.KcImpl;
import org.restcomm.protocols.ss7.map.service.mobility.authentication.TripletListImpl;
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
public class SendIdentificationResponseTest {

    public byte[] getData1() {
        return new byte[] { 48, 48, 4, 8, 16, 33, 2, 2, 16, -119, 34, -9, 48, 36, 48, 34, 4, 16, 15, -2, 18, -92, -49, 43, -35,
                -71, -78, -98, 109, 83, -76, -87, 77, -128, 4, 4, -32, 82, -17, -14, 4, 8, 31, 72, -93, 97, 78, -17, -52, 0 };
    };

    public byte[] getData2() {
        return new byte[] { -93, 106, 4, 8, 16, 33, 2, 2, 16, -119, 34, -9, -96, 36, 48, 34, 4, 16, 15, -2, 18, -92, -49, 43,
                -35, -71, -78, -98, 109, 83, -76, -87, 77, -128, 4, 4, -32, 82, -17, -14, 4, 8, 31, 72, -93, 97, 78, -17, -52,
                0, -94, 15, -96, 13, 4, 8, 31, 72, -93, 97, 78, -17, -52, 0, 4, 1, 4, -93, 39, -96, 32, 48, 10, 6, 3, 42, 3, 4,
                11, 12, 13, 14, 15, 48, 5, 6, 3, 42, 3, 6, 48, 11, 6, 3, 42, 3, 5, 21, 22, 23, 24, 25, 26, -95, 3, 31, 32, 33 };
    };

    static protected byte[] getRandData() {
        return new byte[] { 15, -2, 18, -92, -49, 43, -35, -71, -78, -98, 109, 83, -76, -87, 77, -128 };
    }

    static protected byte[] getSresData() {
        return new byte[] { -32, 82, -17, -14 };
    }

    static protected byte[] getKcData() {
        return new byte[] { 31, 72, -93, 97, 78, -17, -52, 0 };
    }

    @Test(groups = { "functional.decode" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(SendIdentificationResponseImplV1.class);
    	parser.replaceClass(SendIdentificationResponseImplV3.class);
    	
    	// version 2
        byte[] data = this.getData1();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof SendIdentificationResponse);
        SendIdentificationResponse prim = (SendIdentificationResponse)result.getResult(); 
        
        assertEquals(prim.getImsi().getData(), "011220200198227");
        assertEquals(prim.getAuthenticationSetList().getTripletList().getAuthenticationTriplets().size(), 1);
        assertNull(prim.getAuthenticationSetList().getQuintupletList());

        assertNull(prim.getCurrentSecurityContext());
        assertNull(prim.getExtensionContainer());

        // version 3
        data = this.getData2();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof SendIdentificationResponse);
        prim = (SendIdentificationResponse)result.getResult(); 
        
        assertEquals(prim.getImsi().getData(), "011220200198227");

        assertEquals(prim.getAuthenticationSetList().getTripletList().getAuthenticationTriplets().size(), 1);
        assertNull(prim.getAuthenticationSetList().getQuintupletList());

        GSMSecurityContextData gsm = prim.getCurrentSecurityContext().getGSMSecurityContextData();
        UMTSSecurityContextData umts = prim.getCurrentSecurityContext().getUMTSSecurityContextData();
        assertNull(umts);
        assertTrue(Arrays.equals(gsm.getKc().getData(), SendIdentificationResponseTest.getKcData()));
        assertEquals(gsm.getCksn().getData(), 4);

        assertTrue(MAPExtensionContainerTest.CheckTestExtensionContainer(prim.getExtensionContainer()));

    }

    @Test(groups = { "functional.encode" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.replaceClass(SendIdentificationResponseImplV1.class);
    	parser.replaceClass(SendIdentificationResponseImplV3.class);
    	
    	// version 2
        IMSIImpl imsi = new IMSIImpl("011220200198227");

        List<AuthenticationTriplet> ats = new ArrayList<AuthenticationTriplet>();
        AuthenticationTripletImpl at = new AuthenticationTripletImpl(SendIdentificationResponseTest.getRandData(),
                SendIdentificationResponseTest.getSresData(), SendIdentificationResponseTest.getKcData());
        ats.add(at);
        TripletList tl = new TripletListImpl(ats);
        AuthenticationSetListImpl authenticationSetList = new AuthenticationSetListImpl(tl,2);
        
        CurrentSecurityContextImpl currentSecurityContext = null;
        MAPExtensionContainer extensionContainer = null;
        SendIdentificationResponse prim = new SendIdentificationResponseImplV1(imsi, authenticationSetList, 2);
        byte[] data=getData1();
        ByteBuf buffer=parser.encode(prim);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));

        // version 3
        imsi = new IMSIImpl("011220200198227");

        ats = new ArrayList<AuthenticationTriplet>();
        at = new AuthenticationTripletImpl(SendIdentificationResponseTest.getRandData(),
                SendIdentificationResponseTest.getSresData(), SendIdentificationResponseTest.getKcData());
        ats.add(at);
        tl = new TripletListImpl(ats);
        authenticationSetList = new AuthenticationSetListImpl(tl,3);
        
        KcImpl kc = new KcImpl(SendIdentificationResponseTest.getKcData());
        CksnImpl cksn = new CksnImpl(4);
        GSMSecurityContextDataImpl gsm = new GSMSecurityContextDataImpl(kc, cksn);
        currentSecurityContext = new CurrentSecurityContextImpl(gsm);

        extensionContainer = MAPExtensionContainerTest.GetTestExtensionContainer();
        prim = new SendIdentificationResponseImplV3(imsi, authenticationSetList, currentSecurityContext, extensionContainer, 3);
        data=getData2();
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(data, encodedData));
    }
}