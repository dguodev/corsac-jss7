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

package org.restcomm.protocols.ss7.isup.impl.message.parameter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


/**
 *
 * Start time:13:47:44 2009-04-23<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 */
public class CallDiversionTreatmentIndicatorsTest extends ParameterHarness {

    public CallDiversionTreatmentIndicatorsTest() {
        super();

        // super.badBodies.add(new byte[0]);

        super.goodBodies.add(Unpooled.wrappedBuffer(getBody1()));

    }

    private ByteBuf getBody1() {
        byte[] b = new byte[10];
        b[9] = (byte) (b[9] | (0x01 << 7));
        return Unpooled.wrappedBuffer(b);
    }

    public AbstractISUPParameter getTestedComponent() {
        return new CallDiversionTreatmentIndicatorsImpl();
    }
}