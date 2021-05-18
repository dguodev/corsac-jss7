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

package org.restcomm.protocols.ss7.cap.api.service.sms.primitive;

import java.nio.charset.CharacterCodingException;

import org.restcomm.protocols.ss7.map.api.MAPException;
import org.restcomm.protocols.ss7.map.api.MAPParsingComponentException;
import org.restcomm.protocols.ss7.map.api.MAPParsingComponentExceptionReason;
import org.restcomm.protocols.ss7.map.api.datacoding.GSMCharset;
import org.restcomm.protocols.ss7.map.api.datacoding.GSMCharsetDecoder;
import org.restcomm.protocols.ss7.map.api.datacoding.GSMCharsetDecodingData;
import org.restcomm.protocols.ss7.map.api.datacoding.GSMCharsetEncoder;
import org.restcomm.protocols.ss7.map.api.datacoding.Gsm7EncodingStyle;
import org.restcomm.protocols.ss7.map.api.primitives.AddressNature;
import org.restcomm.protocols.ss7.map.api.primitives.AddressStringImpl;
import org.restcomm.protocols.ss7.map.api.primitives.NumberingPlan;

import com.mobius.software.telco.protocols.ss7.asn.ASNParser;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNDecode;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNEncode;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
public class SMSAddressStringImpl extends AddressStringImpl {
	public SMSAddressStringImpl() {
    }

    public SMSAddressStringImpl(AddressNature addressNature, NumberingPlan numberingPlan, String address) {
        super(addressNature, numberingPlan, address);
    }

    @ASNDecode
	public Boolean decode(ASNParser parser,Object parent,ByteBuf buffer,Boolean skipErrors) throws MAPParsingComponentException {
        buffer.markReaderIndex();
        int nature = buffer.readByte();        
        AddressNature an = AddressNature.getInstance((nature & NATURE_OF_ADD_IND_MASK) >> 4);

        if (an == AddressNature.reserved) {
            int natureOfAddInd = ((nature & NATURE_OF_ADD_IND_MASK) >> 4);
            this.addressNature = AddressNature.getInstance(natureOfAddInd);
            int numbPlanInd = (nature & NUMBERING_PLAN_IND_MASK);
            this.numberingPlan = NumberingPlan.getInstance(numbPlanInd);

            GSMCharset cs = new GSMCharset();
            GSMCharsetDecoder decoder = (GSMCharsetDecoder) cs.newDecoder();
            int totalSeptetCount = (buffer.readableBytes() < 7 ? buffer.readableBytes() : buffer.readableBytes() + 1);
            GSMCharsetDecodingData encodingData = new GSMCharsetDecodingData(Gsm7EncodingStyle.bit7_sms_style,totalSeptetCount, 0);
            decoder.setGSMCharsetDecodingData(encodingData);
            try {
	            this.address = decoder.decode(buffer);   
            }
            catch(CharacterCodingException ex) {
            	throw new MAPParsingComponentException(ex,MAPParsingComponentExceptionReason.MistypedParameter);
            }
        } else {
        	buffer.resetReaderIndex();
            super.decode(parser, parent, buffer, skipErrors);
        }
        
        return false;
    }

    @ASNEncode
	public void encode(ASNParser parser,ByteBuf buffer) throws MAPException {
        if (this.addressNature == AddressNature.reserved) {
            int tpOfAddr = 0x80 + (this.addressNature.getIndicator() << 4) + this.numberingPlan.getIndicator();
            buffer.writeByte(tpOfAddr);

            GSMCharset cs = new GSMCharset();
            GSMCharsetEncoder encoder = (GSMCharsetEncoder) cs.newEncoder();
            try {
                encoder.encode(address,buffer);                
            } catch (CharacterCodingException e) {
                throw new MAPException(e);
            }
        } else {
            super.encode(parser,buffer);
        }
    }

    @Override
    public String toString() {
        return "SMSAddressString [AddressNature=" + this.addressNature.toString() + ", NumberingPlan="
                + this.numberingPlan.toString() + ", Address=" + this.address + "]";
    }

}
