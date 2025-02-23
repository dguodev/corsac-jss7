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
 * Start time:15:52:32 2009-04-05<br>
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
import org.restcomm.protocols.ss7.isup.message.parameter.PivotCapability;

/**
 * Start time:15:52:32 2009-04-05<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class PivotCapabilityImpl extends AbstractISUPParameter implements PivotCapability {
	private static final int _TURN_ON = 1;
    private static final int _TURN_OFF = 0;

    private ByteBuf pivotCapabilities;

    public PivotCapabilityImpl() {
        super();

    }

    public void encode(ByteBuf buffer) throws ParameterException {
    	ByteBuf curr=getPivotCapabilities();
        while(curr.readableBytes()>1) {
        	buffer.writeByte(curr.readByte() & 0x7F);            
        }

        if(curr.readableBytes()>0)
        	buffer.writeByte(curr.readByte() | (0x01 << 7));        
    }

    public void decode(ByteBuf b) throws ParameterException {
        setPivotCapabilities(b);
    }

    public ByteBuf getPivotCapabilities() {
    	if(pivotCapabilities==null)
    		return null;
    	
        return Unpooled.wrappedBuffer(pivotCapabilities);
    }

    public void setPivotCapabilities(ByteBuf pivotCapabilities) {
        if (pivotCapabilities == null || pivotCapabilities.readableBytes() == 0) {
            throw new IllegalArgumentException("byte[] must not be null and length must be greater than 0");
        }

        this.pivotCapabilities = pivotCapabilities;
    }

    public byte createPivotCapabilityByte(boolean itriNotAllowed, int pivotPossibility) {
        int b = (itriNotAllowed ? _TURN_ON : _TURN_OFF) << 6;
        b |= pivotPossibility & 0x07;

        return (byte) b;
    }

    public boolean getITRINotAllowed(byte b) {
        return ((b >> 6) & 0x01) == _TURN_ON;
    }

    public int getPivotCapability(byte b) {
        return b & 0x07;
    }

    public int getCode() {
        return _PARAMETER_CODE;
    }
}