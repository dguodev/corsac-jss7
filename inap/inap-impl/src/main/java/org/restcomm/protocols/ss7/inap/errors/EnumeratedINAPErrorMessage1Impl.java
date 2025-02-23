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

package org.restcomm.protocols.ss7.inap.errors;

import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNWrappedTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNEnumerated;

/**
 * Base class of INAP ReturnError messages
 *
 * @author yulian.oifa
 *
 */
@ASNWrappedTag
public abstract class EnumeratedINAPErrorMessage1Impl extends INAPErrorMessageImpl {
	protected Long errorCode;

	private ASNEnumerated value;
	
    protected EnumeratedINAPErrorMessage1Impl(Long errorCode) {
        this.errorCode = errorCode;
    }

    public EnumeratedINAPErrorMessage1Impl() {
    }

    protected Long getValue() {
    	if(this.value==null)
    		return null;
    	
		return value.getValue();
	}

    protected void setValue(Long value) {
    	if(this.value==null)
    		this.value=new ASNEnumerated();
    	
		this.value.setValue(value);
	}
	
    public Long getErrorCode() {
        return errorCode;
    }
}
