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

package org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.primitives;

import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive.DisplayInformation;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNIA5String;

/**
 *
 *
 * @author yulian.oifa
 *
 */
public class DisplayInformationImpl extends ASNIA5String implements DisplayInformation {
	public DisplayInformationImpl() {
    }

    public DisplayInformationImpl(String value) {
    	if(value!=null)
    		setValue(value);    		
    }

    public String getString() {
    	return getValue();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("DisplayInformation [");
        String data=getString();
        if (data != null) {
            sb.append(data);            
        }
        sb.append("]");

        return sb.toString();
    }
}
