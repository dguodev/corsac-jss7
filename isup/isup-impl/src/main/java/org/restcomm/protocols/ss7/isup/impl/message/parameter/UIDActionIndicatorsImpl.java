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
 * Start time:13:49:42 2009-04-05<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski
 *         </a>
 *
 */
package org.restcomm.protocols.ss7.isup.impl.message.parameter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.restcomm.protocols.ss7.isup.ParameterException;
import org.restcomm.protocols.ss7.isup.message.parameter.UIDActionIndicators;

/**
 * Start time:13:49:42 2009-04-05<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 *
 */
public class UIDActionIndicatorsImpl extends AbstractISUPParameter implements UIDActionIndicators {
	private static final int _TURN_ON = 1;
    private static final int _TURN_OFF = 0;

    private ByteBuf udiActionIndicators = null;

    public UIDActionIndicatorsImpl(ByteBuf udiActionIndicators) throws ParameterException {
        super();
        decode(udiActionIndicators);
    }

    public UIDActionIndicatorsImpl() {
        super();

    }

    public void decode(ByteBuf b) throws ParameterException {
    	setUdiActionIndicators(b);
    }

    public void encode(ByteBuf buffer) throws ParameterException {
    	ByteBuf curr=getUdiActionIndicators();
        while(curr.readableBytes()>1) {
        	buffer.writeByte((byte) (curr.readByte() & 0x7F));
        }

        if(curr.readableBytes()>0)
        	buffer.writeByte((byte) ((curr.readByte()) | (0x01 << 7)));
    }

    public ByteBuf getUdiActionIndicators() {
    	if(udiActionIndicators==null)
        	return null;
        
        return Unpooled.wrappedBuffer(udiActionIndicators);
    }

    public void setUdiActionIndicators(ByteBuf udiActionIndicators) {
        if (udiActionIndicators == null || udiActionIndicators.readableBytes() == 0) {
            throw new IllegalArgumentException("byte[] must not be null and length must be greater than 0");
        }
        this.udiActionIndicators = udiActionIndicators;
    }

    public byte createUIDAction(boolean TCII, boolean T9) {
        byte b = (byte) (TCII ? _TURN_ON : _TURN_OFF);
        b |= (T9 ? _TURN_ON : _TURN_OFF) << 1;
        return b;
    }

    public boolean getT9Indicator(byte b) {
        return ((b >> 1) & 0x01) == _TURN_ON;
    }

    public boolean getTCIIndicator(byte b) {
        return ((b >> 1) & 0x01) == _TURN_ON;
    }

    public int getCode() {

        return _PARAMETER_CODE;
    }
}
