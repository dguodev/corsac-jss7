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

package org.restcomm.protocols.ss7.map.service.supplementary;

import org.restcomm.protocols.ss7.map.api.MAPMessageType;
import org.restcomm.protocols.ss7.map.api.MAPOperationCode;
import org.restcomm.protocols.ss7.map.api.service.supplementary.EraseSSResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.SSInfo;

import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNChoise;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNWrappedTag;

/**
*
* @author yulian.oifa
*
*/
@ASNWrappedTag
public class EraseSSResponseImpl extends SupplementaryMessageImpl implements EraseSSResponse {
	private static final long serialVersionUID = 1L;

	@ASNChoise(defaultImplementation = SSInfoImpl.class)
    private SSInfo ssInfo;

    public EraseSSResponseImpl() {
    }

    public EraseSSResponseImpl(SSInfo ssInfo) {
    	this.ssInfo=ssInfo;    	
    }

    @Override
    public MAPMessageType getMessageType() {
        return MAPMessageType.eraseSS_Response;
    }

    @Override
    public int getOperationCode() {
        return MAPOperationCode.eraseSS;
    }

    @Override
    public SSInfo getSsInfo() {
        return ssInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EraseSSResponse [");

        if (this.ssInfo != null) {
            sb.append("ssInfo=");
            sb.append(this.ssInfo);
        }

        sb.append("]");

        return sb.toString();
    }

}
