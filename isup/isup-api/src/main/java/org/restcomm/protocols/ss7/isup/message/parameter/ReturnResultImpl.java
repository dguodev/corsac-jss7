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

package org.restcomm.protocols.ss7.isup.message.parameter;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNInteger;

/**
 * @author baranowb
 * @author amit bhayani
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=2,constructed=true,lengthIndefinite=false)
public class ReturnResultImpl implements RemoteOperation {
	// mandatory
	@ASNProperty(asnClass=ASNClass.UNIVERSAL,tag=0x02,constructed=false,index=0)
	private ASNInteger invokeId;

	// mandatory
	private ReturnResultInnerImpl inner;
	
	/*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.asn.comp.Invoke#getInvokeId()
     */
    public Long getInvokeId() {
    	if(this.invokeId==null)
    		return null;
    	
        return this.invokeId.getValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.asn.comp.Invoke#getOperationCode()
     */
    public OperationCodeImpl getOperationCode() {
    	if(inner==null)
    		return null;
    	
    	return inner.getOperationCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.asn.comp.Invoke#getParameteR()
     */
    public Object getParameter() {
    	if(inner==null)
    		return null;
    	
    	return inner.getParameter();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.asn.comp.Invoke#setInvokeId(java.lang .Integer)
     */
    public void setInvokeId(Long i) {
        if ((i == null) || (i < -128 || i > 127)) {
            throw new IllegalArgumentException("Invoke ID our of range: <-128,127>: " + i);
        }
        this.invokeId = new ASNInteger();
        this.invokeId.setValue(i);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.asn.comp.Invoke#setOperationCode(org
     * .restcomm.protocols.ss7.tcap.asn.comp.OperationCode)
     */
    public void setOperationCode(OperationCodeImpl i) {
    	if(inner==null)
    		inner=new ReturnResultInnerImpl();
    	
    	inner.setOperationCode(i);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.asn.comp.Invoke#setParameter(org.restcomm.protocols.ss7.tcap.asn.comp.Parameter)
     */
    public void setParameter(Object p) {
    	if(inner==null)
    		inner=new ReturnResultInnerImpl();
    	
    	inner.setParameter(p);
    }

    public OperationType getType() {

        return OperationType.ReturnResult;
    }

    @Override
    public String toString() {
    	Long invokeIdValue=null;
    	if(this.invokeId!=null)
    		invokeIdValue=this.invokeId.getValue();
    	
    	return "ReturnResult[invokeId=" + invokeIdValue + ", inner=" + inner + " ]";
    }
}
