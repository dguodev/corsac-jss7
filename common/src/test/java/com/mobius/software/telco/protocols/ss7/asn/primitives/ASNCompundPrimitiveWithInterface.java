package com.mobius.software.telco.protocols.ss7.asn.primitives;

/*
 * Mobius Software LTD
 * Copyright 2019, Mobius Software LTD and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

/**
*
* @author yulian oifa
*
*/

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

@ASNTag(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=121,constructed=true,lengthIndefinite=false)
public class ASNCompundPrimitiveWithInterface {
	
	private ASNUTF8String field2;
	
	@ASNProperty(tag=102,asnClass=ASNClass.PRIVATE,constructed=true,index=-1,defaultImplementation = ASNCompundPrimitive5.class )
	private ASNCompundPrimitiveInterface field1;
	
	public ASNCompundPrimitiveWithInterface() {
		
	}
	
	public ASNCompundPrimitiveWithInterface(ASNCompundPrimitiveInterface field1,String field2) {
		this.field1=field1;
				
		if(field2!=null) {
			this.field2=new ASNUTF8String();
			this.field2.setValue(field2);
		}
	}

	public ASNCompundPrimitiveInterface getField1() {
		return field1;
	}

	public void setField1(ASNCompundPrimitiveInterface field1) {
		this.field1 = field1;
	}

	public String getField2() {
		if(field2==null)
			return null;
		
		return field2.getValue();
	}

	public void setField2(String field2) {
		if(field2==null) {
			this.field2=null;
			return;
		}
		
		if(this.field2==null)
			this.field2=new ASNUTF8String();
		
		this.field2.setValue(field2);
	}
}