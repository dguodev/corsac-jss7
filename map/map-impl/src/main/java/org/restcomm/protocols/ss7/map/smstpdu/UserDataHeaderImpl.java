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

import java.util.HashMap;
import java.util.Map;

import org.restcomm.protocols.ss7.map.api.smstpdu.UserDataHeader;
import org.restcomm.protocols.ss7.map.api.smstpdu.UserDataHeaderElement;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class UserDataHeaderImpl implements UserDataHeader {
    private Map<Integer, byte[]> data = new HashMap<Integer, byte[]>();

    public UserDataHeaderImpl() {
    }

    public UserDataHeaderImpl(byte[] encodedData) {
        if (encodedData == null || encodedData.length < 1)
            return;
        int udhl = encodedData[0] & 0xFF;
        if (udhl > encodedData.length)
            udhl = encodedData.length;
        int ind = 1;
        while (ind <= udhl - 1) {
            int id = encodedData[ind++];
            int len = encodedData[ind++];
            if (len <= udhl + 1 - ind) {
                byte[] buf = new byte[len];
                System.arraycopy(encodedData, ind, buf, 0, len);
                ind += len;

                data.put(id, buf);
            }
        }
    }

    public void getEncodedData(ByteBuf buf) {

        if (data.size() == 0)
            return;
        
        int index=buf.writerIndex();
        buf.writeByte(0);
        for (int id : data.keySet()) {
            byte[] innerData = data.get(id);

            buf.writeByte(id);
            if (innerData == null)
            	buf.writeByte(0);
            else {
            	buf.writeByte(innerData.length);
            	buf.writeBytes(innerData);
            }
        }

        int newIndex=buf.writerIndex();
        buf.setByte(index, newIndex-index-1);        
    }

    public Map<Integer, byte[]> getAllData() {
        return data;
    }

    public void addInformationElement(int informationElementIdentifier, byte[] encodedData) {
        this.data.put(informationElementIdentifier, encodedData);
    }

    public void addInformationElement(UserDataHeaderElement informationElement) {
        this.data.put(informationElement.getEncodedInformationElementIdentifier(),
                informationElement.getEncodedInformationElementData());
    }

    public byte[] getInformationElementData(int informationElementIdentifier) {
        return this.data.get(informationElementIdentifier);
    }

    public NationalLanguageLockingShiftIdentifierImpl getNationalLanguageLockingShift() {
        byte[] buf = this.data.get(_InformationElementIdentifier_NationalLanguageLockingShift);
        if (buf != null && buf.length == 1)
            return new NationalLanguageLockingShiftIdentifierImpl(buf);
        else
            return null;
    }

    public NationalLanguageSingleShiftIdentifierImpl getNationalLanguageSingleShift() {
        byte[] buf = this.data.get(_InformationElementIdentifier_NationalLanguageSingleShift);
        if (buf != null && buf.length == 1)
            return new NationalLanguageSingleShiftIdentifierImpl(buf);
        else
            return null;
    }

    public ConcatenatedShortMessagesIdentifierImpl getConcatenatedShortMessagesIdentifier() {
        byte[] buf = this.data.get(_InformationElementIdentifier_ConcatenatedShortMessages16bit);
        if (buf != null && buf.length == 4)
            return new ConcatenatedShortMessagesIdentifierImpl(buf);
        else {
            buf = this.data.get(_InformationElementIdentifier_ConcatenatedShortMessages8bit);
            if (buf != null && buf.length == 3)
                return new ConcatenatedShortMessagesIdentifierImpl(buf);
            else
                return null;
        }
    }

    public ApplicationPortAddressing16BitAddressImpl getApplicationPortAddressing16BitAddress() {
        byte[] buf = this.data.get(_InformationElementIdentifier_ApplicationPortAddressingScheme16BitAddress);
        if (buf != null && buf.length == 4)
            return new ApplicationPortAddressing16BitAddressImpl(buf);
        else
            return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserDataHeader [");
        boolean isFirst = true;
        for (int id : data.keySet()) {
            byte[] buf = data.get(id);

            if (isFirst)
                isFirst = false;
            else
                sb.append("\n\t");
            sb.append(id);
            sb.append(" = ");
            sb.append(printDataArr(buf));
        }

        NationalLanguageLockingShiftIdentifierImpl nllsi = this.getNationalLanguageLockingShift();
        NationalLanguageSingleShiftIdentifierImpl nlssi = this.getNationalLanguageSingleShift();
        ConcatenatedShortMessagesIdentifierImpl csmi = this.getConcatenatedShortMessagesIdentifier();
        ApplicationPortAddressing16BitAddressImpl apa16 = this.getApplicationPortAddressing16BitAddress();
        if (nllsi != null) {
            sb.append(", NationalLanguageLockingShiftIdentifier = [");
            sb.append(nllsi);
            sb.append("]");
        }
        if (nlssi != null) {
            sb.append(", NationalLanguageSingleShiftIdentifier = [");
            sb.append(nlssi);
            sb.append("]");
        }
        if (csmi != null) {
            sb.append(", ConcatenatedShortMessagesIdentifier = [");
            sb.append(csmi);
            sb.append("]");
        }
        if (apa16 != null) {
            sb.append(", ApplicationPortAddressing16BitAddress = [");
            sb.append(apa16);
            sb.append("]");
        }

        sb.append("]");

        return sb.toString();
    }

    private String printDataArr(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int b : arr) {
            sb.append(b);
            sb.append(", ");
        }

        return sb.toString();
    }
}
