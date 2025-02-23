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

package org.restcomm.protocols.ss7.map.service.mobility.authentication;

import org.restcomm.protocols.ss7.commonapp.api.primitives.IMSI;
import org.restcomm.protocols.ss7.commonapp.api.primitives.ISDNAddressString;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MAPExtensionContainer;
import org.restcomm.protocols.ss7.commonapp.primitives.IMSIImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.ISDNAddressStringImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerImpl;
import org.restcomm.protocols.ss7.map.api.MAPMessageType;
import org.restcomm.protocols.ss7.map.api.MAPOperationCode;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.AccessType;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.AuthenticationFailureReportRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.FailureCause;
import org.restcomm.protocols.ss7.map.service.mobility.MobilityMessageImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNBoolean;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
*
* @author sergey vetyutnev
*
*/
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,lengthIndefinite=false)
public class AuthenticationFailureReportRequestImpl extends MobilityMessageImpl implements AuthenticationFailureReportRequest {
	private static final long serialVersionUID = 1L;

	@ASNProperty(asnClass=ASNClass.UNIVERSAL,tag=4,constructed=false,index=0, defaultImplementation = IMSIImpl.class)
    private IMSI imsi;
    
    @ASNProperty(asnClass=ASNClass.UNIVERSAL,tag=10,constructed=false,index=1)
    private ASNFailureCause failureCause;
    
    @ASNProperty(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,index=-1, defaultImplementation = MAPExtensionContainerImpl.class)
    private MAPExtensionContainer extensionContainer;
    
    private ASNBoolean reAttempt;
    private ASNAccessType accessType;
    private ASNOctetString rand;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=0,constructed=false,index=-1, defaultImplementation = ISDNAddressStringImpl.class)
    private ISDNAddressString vlrNumber;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=1,constructed=false,index=-1, defaultImplementation = ISDNAddressStringImpl.class)
    private ISDNAddressString sgsnNumber;

    public AuthenticationFailureReportRequestImpl() {
    }

    public AuthenticationFailureReportRequestImpl(IMSI imsi, FailureCause failureCause, MAPExtensionContainer extensionContainer, Boolean reAttempt,
            AccessType accessType, byte[] rand, ISDNAddressString vlrNumber, ISDNAddressString sgsnNumber) {
        this.imsi = imsi;
        
        if(failureCause!=null) {
        	this.failureCause = new ASNFailureCause();
        	this.failureCause.setType(failureCause);
        }
        
        this.extensionContainer = extensionContainer;
        
        if(reAttempt!=null) {
        	this.reAttempt = new ASNBoolean();
        	this.reAttempt.setValue(reAttempt);
        }
        
        if(accessType!=null) {
        	this.accessType = new ASNAccessType();
        	this.accessType.setType(accessType);
        }
        
        if(rand!=null) {
        	this.rand = new ASNOctetString();
        	this.rand.setValue(Unpooled.wrappedBuffer(rand));
        }
        
        this.vlrNumber = vlrNumber;
        this.sgsnNumber = sgsnNumber;
    }

    @Override
    public MAPMessageType getMessageType() {
        return MAPMessageType.authenticationFailureReport_Request;
    }

    @Override
    public int getOperationCode() {
        return MAPOperationCode.authenticationFailureReport;
    }

    @Override
    public IMSI getImsi() {
        return imsi;
    }

    @Override
    public FailureCause getFailureCause() {
    	if(failureCause==null)
    		return null;
    	
        return failureCause.getType();
    }

    @Override
    public MAPExtensionContainer getExtensionContainer() {
        return extensionContainer;
    }

    @Override
    public Boolean getReAttempt() {
    	if(reAttempt==null)
    		return null;
    	
        return reAttempt.getValue();        
    }

    @Override
    public AccessType getAccessType() {
    	if(accessType==null)
    		return null;
    	
        return accessType.getType();
    }

    @Override
    public byte[] getRand() {
    	if(rand==null)
    		return null;
    		
    	ByteBuf value=rand.getValue();
    	if(value==null)
    		return null;
    	
    	byte[] data=new byte[value.readableBytes()];
    	value.readBytes(data);
        return data;
    }

    @Override
    public ISDNAddressString getVlrNumber() {
        return vlrNumber;
    }

    @Override
    public ISDNAddressString getSgsnNumber() {
        return sgsnNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AuthenticationFailureReportRequest [");

        if (this.imsi != null) {
            sb.append("imsi=");
            sb.append(imsi.toString());
            sb.append(", ");
        }
        if (this.failureCause != null) {
            sb.append("failureCause=");
            sb.append(failureCause.toString());
            sb.append(", ");
        }
        if (this.extensionContainer != null) {
            sb.append("extensionContainer=");
            sb.append(extensionContainer.toString());
            sb.append(", ");
        }
        if (this.reAttempt != null) {
            sb.append("reAttempt=");
            sb.append(reAttempt.toString());
            sb.append(", ");
        }
        if (this.accessType != null) {
            sb.append("accessType=");
            sb.append(accessType.toString());
            sb.append(", ");
        }
        if (this.rand != null) {
            sb.append("rand=[");
            sb.append(ASNOctetString.printDataArr(getRand()));
            sb.append("], ");
        }
        if (this.vlrNumber != null) {
            sb.append("vlrNumber=");
            sb.append(vlrNumber.toString());
            sb.append(", ");
        }
        if (this.sgsnNumber != null) {
            sb.append("sgsnNumber=");
            sb.append(sgsnNumber.toString());
            sb.append(", ");
        }

        sb.append("]");

        return sb.toString();
    }
}