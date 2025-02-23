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

/**
 * Start time:14:11:03 2009-04-23<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski
 *         </a>
 *
 */
package org.restcomm.protocols.ss7.isup.impl.message.parameter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.restcomm.protocols.ss7.isup.ParameterException;
import org.testng.annotations.Test;

/**
 * Start time:14:11:03 2009-04-23<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 */
public class ConnectionRequestTest extends ParameterHarness {

    /**
     * @throws IOException
     */
    public ConnectionRequestTest() throws IOException {
        super.badBodies.add(Unpooled.wrappedBuffer(new byte[1]));

        super.goodBodies.add(Unpooled.wrappedBuffer(getBody1()));
        // This will fail, cause this body has APRI allowed, so hardcoded body
        // does nto match encoded body :)
        // super.goodBodies.add(getBody2());
    }

    private ByteBuf getBody1() throws IOException {
    	ByteBuf bos = Unpooled.buffer();
        // we will use odd number of digits, so we leave zero as MSB
        // Local reference
        bos.writeByte(12);
        bos.writeByte(120);
        bos.writeByte(38);

        // Signaling point code
        bos.writeByte(120);
        bos.writeByte(45);
        // protocol class
        bos.writeByte(120);
        // credit
        bos.writeByte(69);
        return bos;
    }

    private ByteBuf getBody2() throws IOException {
    	ByteBuf bos = Unpooled.buffer();

        // Local reference
        bos.writeByte(12);
        bos.writeByte(120);
        bos.writeByte(38);

        // Signaling point code
        bos.writeByte(120);
        bos.writeByte(12);
        return bos;
    }

    @Test(groups = { "functional.encode", "functional.decode", "parameter" })
    public void testBody1EncodedValues() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, IOException, ParameterException {
        ConnectionRequestImpl bci = new ConnectionRequestImpl(getBody1());
        int localRef = 12;
        localRef |= 120 << 8;
        localRef |= 38 << 16;
        int signalingPointCode = 120;
        signalingPointCode |= 45 << 8;

        int protocolClass = 120;
        int credit = 69;
        String[] methodNames = { "getLocalReference", "getSignalingPointCode", "getProtocolClass", "getCredit" };
        Object[] expectedValues = { localRef, signalingPointCode, protocolClass, credit };
        super.testValues(bci, methodNames, expectedValues);
    }

    @Test(groups = { "functional.encode", "functional.decode", "parameter" })
    public void testBody2EncodedValues() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, IOException, ParameterException {
        ConnectionRequestImpl bci = new ConnectionRequestImpl(getBody2());

        int localRef = 12;
        localRef |= 120 << 8;
        localRef |= 38 << 16;
        int signalingPointCode = 120;
        signalingPointCode |= 12 << 8;

        String[] methodNames = { "getLocalReference", "getSignalingPointCode" };
        Object[] expectedValues = { localRef, signalingPointCode };
        super.testValues(bci, methodNames, expectedValues);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.isup.messages.parameters.ParameterHarness#getTestedComponent ()
     */

    public AbstractISUPParameter getTestedComponent() throws ParameterException {
        return new ConnectionRequestImpl(Unpooled.wrappedBuffer(new byte[5]));
    }

}
