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

package org.restcomm.protocols.ss7.map.service.oam;

import org.restcomm.protocols.ss7.map.api.service.oam.BssRecordType;
import org.restcomm.protocols.ss7.map.api.service.oam.HlrRecordType;
import org.restcomm.protocols.ss7.map.api.service.oam.MscRecordType;
import org.restcomm.protocols.ss7.map.api.service.oam.TraceType;
import org.restcomm.protocols.ss7.map.api.service.oam.TraceTypeInvokingEvent;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNSingleByte;

/**
*
* @author sergey vetyutnev
*
*/
public class TraceTypeImpl extends ASNSingleByte implements TraceType {
	public TraceTypeImpl() {       
    }

    public TraceTypeImpl(int data) {
        setValue(data);
    }

    public TraceTypeImpl(BssRecordType bssRecordType, MscRecordType mscRecordType, TraceTypeInvokingEvent traceTypeInvokingEvent, boolean priorityIndication) {
        int mscRecordTypeInt = 3;
        if (mscRecordType != null)
            mscRecordTypeInt = mscRecordType.getCode();
        int bssRecordTypeInt = 3;
        if (bssRecordType != null)
            bssRecordTypeInt = bssRecordType.getCode();
        int traceTypeInvokingEventInt = 0;
        if (traceTypeInvokingEvent != null)
            traceTypeInvokingEventInt = traceTypeInvokingEvent.getCode();
        setValue(((bssRecordTypeInt << 4) | (mscRecordTypeInt << 2) | traceTypeInvokingEventInt | (priorityIndication ? 0x80 : 0x00)));
    }

    public TraceTypeImpl(HlrRecordType hlrRecordType, TraceTypeInvokingEvent traceTypeInvokingEvent, boolean priorityIndication) {
        int hlrRecordTypeInt = 3;
        if (hlrRecordType != null)
            hlrRecordTypeInt = hlrRecordType.getCode();
        int traceTypeInvokingEventInt = 0;
        if (traceTypeInvokingEvent != null)
            traceTypeInvokingEventInt = traceTypeInvokingEvent.getCode();
        setValue(((hlrRecordTypeInt << 2) | traceTypeInvokingEventInt | (priorityIndication ? 0x80 : 0x00)));
    }


    public int getData() {
        return getValue();
    }

    public boolean isPriorityIndication() {
        if ((this.getData() & 0x80) != 0)
            return true;
        else
            return false;
    }

    public BssRecordType getBssRecordType() {
        int code = (this.getData() >> 4) & 0x03;
        return BssRecordType.getInstance(code);
    }

    public MscRecordType getMscRecordType() {
        int code = (this.getData() >> 2) & 0x03;
        return MscRecordType.getInstance(code);
    }

    public HlrRecordType getHlrRecordType() {
        int code = (this.getData() >> 2) & 0x03;
        return HlrRecordType.getInstance(code);
    }

    public TraceTypeInvokingEvent getTraceTypeInvokingEvent() {
        int code = this.getData() & 0x03;
        return TraceTypeInvokingEvent.getInstance(code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TraceType");
        sb.append(" [");

        sb.append("bssRecordType=");
        sb.append(this.getBssRecordType());
        sb.append(", ");

        sb.append("mscRecordType=");
        sb.append(this.getMscRecordType());
        sb.append(", ");

        sb.append("hlrRecordType=");
        sb.append(this.getHlrRecordType());
        sb.append(", ");

        sb.append("traceTypeInvokingEvent=");
        sb.append(this.getTraceTypeInvokingEvent());
        sb.append(", ");

        if (isPriorityIndication()) {
            sb.append("priorityIndication, ");
        }

        sb.append("]");

        return sb.toString();
    }

}
