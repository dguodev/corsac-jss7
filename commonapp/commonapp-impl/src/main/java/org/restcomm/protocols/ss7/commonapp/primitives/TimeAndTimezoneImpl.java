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

package org.restcomm.protocols.ss7.commonapp.primitives;

import org.restcomm.protocols.ss7.commonapp.api.primitives.TimeAndTimezone;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class TimeAndTimezoneImpl extends ASNOctetString implements TimeAndTimezone {
	public TimeAndTimezoneImpl() {
    }

    public TimeAndTimezoneImpl(int year, int month, int day, int hour, int minute, int second, int timeZone) {
        byte[] data = new byte[8];
        data[0] = (byte) encodeByte(year / 100);
        data[1] = (byte) encodeByte(year % 100);
        data[2] = (byte) encodeByte(month);
        data[3] = (byte) encodeByte(day);
        data[4] = (byte) encodeByte(hour);
        data[5] = (byte) encodeByte(minute);
        data[6] = (byte) encodeByte(second);
        if (timeZone >= 0)
            data[7] = (byte) encodeByte(timeZone);
        else
            data[7] = (byte) (encodeByte(-timeZone) | 0x08);
        
        setValue(Unpooled.wrappedBuffer(data));
    }

    public int getYear() {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() != 8)
            return 0;

        return this.decodeByte((int) data.readByte()) * 100 + (int) this.decodeByte(data.readByte());
    }

    public int getMonth() {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() != 8)
            return 0;

        data.skipBytes(2);
        return this.decodeByte((int) data.readByte());
    }

    public int getDay() {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() != 8)
            return 0;

        data.skipBytes(3);
        return this.decodeByte((int) data.readByte());
    }

    public int getHour() {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() != 8)
            return 0;

        data.skipBytes(4);
        return this.decodeByte((int) data.readByte());
    }

    public int getMinute() {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() != 8)
            return 0;

        data.skipBytes(5);
        return this.decodeByte((int) data.readByte());
    }

    public int getSecond() {
    	ByteBuf data=getValue();
        if (data == null || data.readableBytes() != 8)
            return 0;

        data.skipBytes(6);
        return this.decodeByte((int) data.readByte());
    }

    public int getTimeZone() {
    	ByteBuf data=getValue();
    	if (data == null || data.readableBytes() != 8)
            return 0;

    	data.skipBytes(7);
    	byte currByte=data.readByte();
        int res = decodeByte((byte) (currByte & 0xF7));
        if ((currByte & 0x08) != 0)
            res = -res;
        return res;
    }

    private int decodeByte(int bt) {
        return (bt & 0x0F) * 10 + ((bt & 0xF0) >> 4);
    }

    private int encodeByte(int val) {
        return (val / 10) | (val % 10) << 4;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("TimeAndTimezone [");
        if (getValue() != null) {
            sb.append("year=");
            sb.append(this.getYear());
            sb.append(", month=");
            sb.append(this.getMonth());
            sb.append(", day=");
            sb.append(this.getDay());
            sb.append(", hour=");
            sb.append(this.getHour());
            sb.append(", minite=");
            sb.append(this.getMinute());
            sb.append(", second=");
            sb.append(this.getSecond());
            sb.append(", timeZone=");
            sb.append(this.getTimeZone());
        }
        sb.append("]");

        return sb.toString();
    }
}
