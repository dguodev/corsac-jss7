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

package org.restcomm.protocols.ss7.map.smstpdu;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.testng.annotations.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class SmsDeliverReportTpduTest {

    public byte[] getData1() {
        return new byte[] { 0, -56, 6, 0, 10, -56, 50, -101, -3, 6, -123, 66, -95, 16 };
    }

    public byte[] getData2() {
        return new byte[] { 0, 1, 44 };
    }

    @Test(groups = { "functional.decode", "smstpdu" })
    public void testDecode() throws Exception {

        SmsDeliverReportTpduImpl impl = new SmsDeliverReportTpduImpl(Unpooled.wrappedBuffer(this.getData1()), null);
        assertFalse(impl.getUserDataHeaderIndicator());
        assertEquals(impl.getFailureCause().getCode(), 200);
        assertEquals(impl.getParameterIndicator().getCode(), 6);
        assertNull(impl.getProtocolIdentifier());
        impl.getUserData().decode();
        assertEquals(impl.getDataCodingScheme().getCode(), 0);
        assertTrue(impl.getUserData().getDecodedMessage().equals("Hello !!!!"));

        impl = new SmsDeliverReportTpduImpl(Unpooled.wrappedBuffer(this.getData2()), null);
        assertFalse(impl.getUserDataHeaderIndicator());
        assertNull(impl.getFailureCause());
        assertEquals(impl.getParameterIndicator().getCode(), 1);
        assertEquals(impl.getProtocolIdentifier().getCode(), 44);
        assertNull(impl.getDataCodingScheme());
        assertNull(impl.getUserData());
    }

    @Test(groups = { "functional.encode", "smstpdu" })
    public void testEncode() throws Exception {

        UserDataImpl ud = new UserDataImpl("Hello !!!!", new DataCodingSchemeImpl(0), null, null);
        FailureCauseImpl failureCause = new FailureCauseImpl(200);
        SmsDeliverReportTpduImpl impl = new SmsDeliverReportTpduImpl(failureCause, null, ud);
        byte[] encData=new byte[this.getData1().length];
        ByteBuf buffer=Unpooled.wrappedBuffer(encData);
        buffer.resetWriterIndex();
        impl.encodeData(buffer);
        assertTrue(Arrays.equals(encData, this.getData1()));

        ProtocolIdentifierImpl pi = new ProtocolIdentifierImpl(44);
        impl = new SmsDeliverReportTpduImpl(null, pi, null);
        encData=new byte[this.getData2().length];
        buffer=Unpooled.wrappedBuffer(encData);
        buffer.resetWriterIndex();
        impl.encodeData(buffer);
        assertTrue(Arrays.equals(encData, this.getData2()));
    }
}
