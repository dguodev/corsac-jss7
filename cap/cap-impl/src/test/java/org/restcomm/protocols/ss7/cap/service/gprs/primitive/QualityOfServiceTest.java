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
package org.restcomm.protocols.ss7.cap.service.gprs.primitive;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.subscriberManagement.Ext2QoSSubscribedImpl;
import org.restcomm.protocols.ss7.commonapp.subscriberManagement.ExtQoSSubscribedImpl;
import org.restcomm.protocols.ss7.commonapp.subscriberManagement.QoSSubscribedImpl;
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
public class QualityOfServiceTest {

    public byte[] getData() {
        return new byte[] { 48, 35, -96, 5, -128, 3, 4, 7, 7, -95, 4, -127, 2, 1, 7, -94, 5, -128, 3, 4, 7, 7, -93, 3, -128, 1,
                52, -92, 3, -128, 1, 53, -91, 3, -128, 1, 54 };
    };

    private byte[] getEncodedqos2Subscribed1() {
        return new byte[] { 52 };
    }

    private byte[] getEncodedqos2Subscribed2() {
        return new byte[] { 53 };
    }

    private byte[] getEncodedqos2Subscribed3() {
        return new byte[] { 54 };
    }

    public byte[] getQoSSubscribedData() {
        return new byte[] { 4, 7, 7 };
    };

    public byte[] getExtQoSSubscribedData() {
        return new byte[] { 1, 7 };
    };

    @Test(groups = { "functional.decode", "primitives" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(QualityOfServiceImpl.class);
    	
    	byte[] rawData = this.getData();
        ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof QualityOfServiceImpl);
        
        QualityOfServiceImpl prim = (QualityOfServiceImpl)result.getResult();        
        assertTrue(Arrays.equals(prim.getRequestedQoS().getShortQoSFormat().getData(), this.getQoSSubscribedData()));
        assertNull(prim.getRequestedQoS().getLongQoSFormat());

        assertTrue(Arrays.equals(prim.getSubscribedQoS().getLongQoSFormat().getData(), this.getExtQoSSubscribedData()));
        assertNull(prim.getSubscribedQoS().getShortQoSFormat());

        assertTrue(Arrays.equals(prim.getNegotiatedQoS().getShortQoSFormat().getData(), this.getQoSSubscribedData()));
        assertNull(prim.getNegotiatedQoS().getLongQoSFormat());

        assertTrue(Arrays.equals(prim.getRequestedQoSExtension().getSupplementToLongQoSFormat().getData(),
                this.getEncodedqos2Subscribed1()));

        assertTrue(Arrays.equals(prim.getSubscribedQoSExtension().getSupplementToLongQoSFormat().getData(),
                this.getEncodedqos2Subscribed2()));

        assertTrue(Arrays.equals(prim.getNegotiatedQoSExtension().getSupplementToLongQoSFormat().getData(),
                this.getEncodedqos2Subscribed3()));
    }

    @Test(groups = { "functional.encode", "primitives" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(true);
    	parser.replaceClass(QualityOfServiceImpl.class);
    	
        QoSSubscribedImpl qosSubscribed = new QoSSubscribedImpl(this.getQoSSubscribedData());
        GPRSQoSImpl requestedQoS = new GPRSQoSImpl(qosSubscribed);

        ExtQoSSubscribedImpl extQoSSubscribed = new ExtQoSSubscribedImpl(this.getExtQoSSubscribedData());
        GPRSQoSImpl subscribedQoS = new GPRSQoSImpl(extQoSSubscribed);

        GPRSQoSImpl negotiatedQoS = new GPRSQoSImpl(qosSubscribed);

        Ext2QoSSubscribedImpl qos2Subscribed1 = new Ext2QoSSubscribedImpl(this.getEncodedqos2Subscribed1());
        GPRSQoSExtensionImpl requestedQoSExtension = new GPRSQoSExtensionImpl(qos2Subscribed1);

        Ext2QoSSubscribedImpl qos2Subscribed2 = new Ext2QoSSubscribedImpl(this.getEncodedqos2Subscribed2());
        GPRSQoSExtensionImpl subscribedQoSExtension = new GPRSQoSExtensionImpl(qos2Subscribed2);

        Ext2QoSSubscribedImpl qos2Subscribed3 = new Ext2QoSSubscribedImpl(this.getEncodedqos2Subscribed3());
        GPRSQoSExtensionImpl negotiatedQoSExtension = new GPRSQoSExtensionImpl(qos2Subscribed3);

        QualityOfServiceImpl prim = new QualityOfServiceImpl(requestedQoS, subscribedQoS, negotiatedQoS, requestedQoSExtension,
                subscribedQoSExtension, negotiatedQoSExtension);
        byte[] rawData = this.getData();
        ByteBuf buffer=parser.encode(prim);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        assertTrue(Arrays.equals(rawData, encodedData));
    }

}
