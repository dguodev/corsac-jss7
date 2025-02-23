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
 * Start time:08:17:06 2009-04-06<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski
 *         </a>
 *
 */
package org.restcomm.protocols.ss7.isup.impl.message.parameter;

import io.netty.buffer.ByteBuf;

import org.restcomm.protocols.ss7.isup.ParameterException;
import org.restcomm.protocols.ss7.isup.message.parameter.HTRInformation;

/**
 * Start time:08:17:06 2009-04-06<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class HTRInformationImpl extends AbstractNAINumber implements HTRInformation {
	private int numberingPlanIndicator;

    public HTRInformationImpl(ByteBuf representation) throws ParameterException {
        super(representation);
    }

    public HTRInformationImpl(int natureOfAddresIndicator, String address, int numberingPlanIndicator) {
        super(natureOfAddresIndicator, address);
        this.numberingPlanIndicator = numberingPlanIndicator;
    }

    public HTRInformationImpl() {
        super();

    }

    /*
     * (non-Javadoc)
     *
     * @seeorg.restcomm.isup.parameters.AbstractNumber#decodeBody(java.io. ByteArrayInputStream)
     */

    public void decodeBody(ByteBuf buffer) throws IllegalArgumentException, ParameterException {
    	if(buffer.readableBytes()==0) {
    		throw new ParameterException("byte[] must  not be null and length must  be greater than 0");
    	}
    	
        int b = buffer.readByte() & 0xff;
        this.numberingPlanIndicator = (b & 0x70) >> 4;        
    }

    /*
     * (non-Javadoc)
     *
     * @seeorg.restcomm.isup.parameters.AbstractNumber#encodeBody(java.io. ByteArrayOutputStream)
     */

    public void encodeBody(ByteBuf buffer) {
        int c = (this.numberingPlanIndicator & 0x07) << 4;
        buffer.writeByte(c);        
    }

    public int getNumberingPlanIndicator() {
        return numberingPlanIndicator;
    }

    public void setNumberingPlanIndicator(int numberingPlanIndicator) {
        this.numberingPlanIndicator = numberingPlanIndicator;
    }

    public int getCode() {
        return _PARAMETER_CODE;
    }
}