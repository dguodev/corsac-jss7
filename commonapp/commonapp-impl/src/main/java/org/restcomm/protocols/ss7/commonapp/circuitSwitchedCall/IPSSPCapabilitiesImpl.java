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

package org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall;

import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.IPSSPCapabilities;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class IPSSPCapabilitiesImpl extends ASNOctetString implements IPSSPCapabilities {
	public static int _Mask_IPRoutingAddress = 0x01;
    public static int _Mask_VoiceBack = 0x02;
    public static int _Mask_VoiceInformation_SpeechRecognition = 0x04;
    public static int _Mask_VoiceInformation_VoiceRecognition = 0x08;
    public static int _Mask_GenerationOfVoiceAnnouncementsFromTextSupported = 0x10;

    public static final String _PrimitiveName = "IPSSPCapabilities";

    public IPSSPCapabilitiesImpl() {
    }

    public IPSSPCapabilitiesImpl(byte[] data) {
        setValue(Unpooled.wrappedBuffer(data));
    }

    public IPSSPCapabilitiesImpl(boolean IPRoutingAddressSupported, boolean VoiceBackSupported,
            boolean VoiceInformationSupportedViaSpeechRecognition, boolean VoiceInformationSupportedViaVoiceRecognition,
            boolean GenerationOfVoiceAnnouncementsFromTextSupported, byte[] extraData) {
        setData(IPRoutingAddressSupported, VoiceBackSupported, VoiceInformationSupportedViaSpeechRecognition,
                VoiceInformationSupportedViaVoiceRecognition, GenerationOfVoiceAnnouncementsFromTextSupported, extraData);
    }

    public void setData(boolean IPRoutingAddressSupported, boolean VoiceBackSupported,
            boolean VoiceInformationSupportedViaSpeechRecognition, boolean VoiceInformationSupportedViaVoiceRecognition,
            boolean GenerationOfVoiceAnnouncementsFromTextSupported, byte[] extraData) {
        int firstByte = (IPRoutingAddressSupported ? _Mask_IPRoutingAddress : 0) | (VoiceBackSupported ? _Mask_VoiceBack : 0)
                | (VoiceInformationSupportedViaSpeechRecognition ? _Mask_VoiceInformation_SpeechRecognition : 0)
                | (VoiceInformationSupportedViaVoiceRecognition ? _Mask_VoiceInformation_VoiceRecognition : 0)
                | (GenerationOfVoiceAnnouncementsFromTextSupported ? _Mask_GenerationOfVoiceAnnouncementsFromTextSupported : 0);
        int extraCnt = 0;
        if (extraData != null)
            extraCnt = extraData.length;
        if (extraCnt > 3)
            extraCnt = 3;

        byte[] data = new byte[1 + extraCnt];
        data[0] = (byte) firstByte;
        if (extraCnt > 0)
            System.arraycopy(extraData, 0, data, 1, extraCnt);
        
        setValue(Unpooled.wrappedBuffer(data));
    }

    public byte[] getData() {
    	ByteBuf value=getValue();
    	if(value==null)
    		return null;
    	
    	byte[] data=new byte[value.readableBytes()];
    	value.readBytes(data);
        return data;
    }

    public boolean getIPRoutingAddressSupported() {
    	ByteBuf buffer=getValue();
        if (buffer == null || buffer.readableBytes() == 0)
            return false;

        return (((int) buffer.readByte()) & _Mask_IPRoutingAddress) != 0;
    }

    public boolean getVoiceBackSupported() {
    	ByteBuf buffer=getValue();
        if (buffer == null || buffer.readableBytes() == 0)
            return false;

        return (((int) buffer.readByte()) & _Mask_VoiceBack) != 0;
    }

    public boolean getVoiceInformationSupportedViaSpeechRecognition() {
    	ByteBuf buffer=getValue();
        if (buffer == null || buffer.readableBytes() == 0)
            return false;

        return (((int) buffer.readByte()) & _Mask_VoiceInformation_SpeechRecognition) != 0;
    }

    public boolean getVoiceInformationSupportedViaVoiceRecognition() {
    	ByteBuf buffer=getValue();
        if (buffer == null || buffer.readableBytes() == 0)
            return false;

        return (((int) buffer.readByte()) & _Mask_VoiceInformation_VoiceRecognition) != 0;
    }

    public boolean getGenerationOfVoiceAnnouncementsFromTextSupported() {
    	ByteBuf buffer=getValue();
        if (buffer == null || buffer.readableBytes() == 0)
            return false;

        return (((int) buffer.readByte()) & _Mask_GenerationOfVoiceAnnouncementsFromTextSupported) != 0;
    }

    public byte[] getExtraData() {
    	ByteBuf buffer=getValue();
        if (buffer == null || buffer.readableBytes() < 2)
            return null;

        int extraCount = buffer.readableBytes()-1;
        if (extraCount > 3)
            extraCount = 3;
        
        byte[] res = new byte[extraCount];
        System.arraycopy(this.getData(), 1, res, 0, extraCount);
        return res;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("IPSSPCapabilities [");
        if (getValue() != null) {
            if (this.getIPRoutingAddressSupported())
                sb.append("IPRoutingAddressSupported, ");
            if (this.getVoiceBackSupported())
                sb.append("VoiceBackSupported, ");
            if (this.getVoiceInformationSupportedViaSpeechRecognition())
                sb.append("VoiceInformationSupportedViaSpeechRecognition, ");
            if (this.getVoiceInformationSupportedViaVoiceRecognition())
                sb.append("VoiceInformationSupportedViaVoiceRecognition, ");
            if (this.getGenerationOfVoiceAnnouncementsFromTextSupported())
                sb.append("GenerationOfVoiceAnnouncementsFromTextSupported, ");
            byte[] eArr = this.getExtraData();
            if (eArr != null) {
                sb.append("ExtraData=");
                for (int i1 = 0; i1 < eArr.length; i1++) {
                    sb.append(eArr[i1]);
                    sb.append(", ");
                }
            }
        }
        sb.append("]");

        return sb.toString();
    }
}
