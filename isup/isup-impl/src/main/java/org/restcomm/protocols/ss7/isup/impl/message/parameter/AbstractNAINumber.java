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

import org.restcomm.protocols.ss7.isup.ParameterException;
import org.restcomm.protocols.ss7.isup.message.parameter.NAINumber;

/**
 * Start time:14:02:37 2009-04-04<br>
 * Project: restcomm-isup-stack<br>
 * This is number representation that has NAI field
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public abstract class AbstractNAINumber extends AbstractNumber implements NAINumber {
	/**
     * Holds nature of address indicator bits - those are 7 first bits from ususaly top byte (first bit is even/odd flag.)
     */
    protected int natureOfAddresIndicator;

    public AbstractNAINumber(ByteBuf buffer) throws ParameterException {
        super(buffer);

    }

    public AbstractNAINumber(int natureOfAddresIndicator, String address) {
        super(address);
        this.natureOfAddresIndicator = natureOfAddresIndicator;
    }

    public AbstractNAINumber() {

    }

    public void decode(ByteBuf b) throws ParameterException {
        super.decode(b);
    }

    public int getNatureOfAddressIndicator() {
        return natureOfAddresIndicator;
    }

    public void setNatureOfAddresIndicator(int natureOfAddresIndicator) {
        this.natureOfAddresIndicator = natureOfAddresIndicator;
    }

    /**
     * This method is used in encode method. It encodes header part (1 or 2 bytes usually.)
     *
     * @param bis
     * @return - number of bytes encoded.
     */
    public void encodeHeader(ByteBuf buffer) {
        int b = this.natureOfAddresIndicator & 0x7f;
        // Even is 000000000 == 0
        boolean isOdd = this.oddFlag == _FLAG_ODD;

        if (isOdd)
            b |= 0x80;

        buffer.writeByte(b);
    }

    /**
     * This method is used in constructor that takes byte[] or ByteArrayInputStream as parameter. Decodes header part (its 1 or
     * 2 bytes usually.) Default implemetnation decodes header of one byte - where most significant bit is O/E indicator and
     * bits 7-1 are NAI. This method should be over
     *
     * @param bis
     * @return - number of bytes reads
     * @throws IllegalArgumentException - thrown if read error is encountered.
     */
    public void decodeHeader(ByteBuf buffer) throws ParameterException {
        if (buffer.readableBytes() == 0) {
            throw new ParameterException("No more data to read.");
        }
        int b = buffer.readByte() & 0xff;

        this.oddFlag = (b & 0x80) >> 7;
        this.natureOfAddresIndicator = b & 0x7f;
    }
}
