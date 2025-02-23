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
package org.restcomm.protocols.ss7.map.errors;

import org.restcomm.protocols.ss7.commonapp.api.primitives.MAPExtensionContainer;
import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerImpl;
import org.restcomm.protocols.ss7.map.api.MAPException;
import org.restcomm.protocols.ss7.map.api.errors.MAPErrorCode;
import org.restcomm.protocols.ss7.map.api.errors.MAPErrorMessageSMDeliveryFailure;
import org.restcomm.protocols.ss7.map.api.errors.SMEnumeratedDeliveryFailureCause;
import org.restcomm.protocols.ss7.map.api.smstpdu.SmsDeliverReportTpdu;
import org.restcomm.protocols.ss7.map.api.smstpdu.SmsTpduType;
import org.restcomm.protocols.ss7.map.smstpdu.SmsDeliverReportTpduImpl;
import org.restcomm.protocols.ss7.map.smstpdu.SmsTpduImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author sergey vetyutnev
 * @author amit bhayani
 */
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,lengthIndefinite=false)
public class MAPErrorMessageSMDeliveryFailureImpl extends MAPErrorMessageImpl implements MAPErrorMessageSMDeliveryFailure {
	private long mapProtocolVersion = 3;
    private ASNSMEnumeratedDeliveryFailureCauseImpl sMEnumeratedDeliveryFailureCause;
    private ASNOctetString signalInfo;
   
    @ASNProperty(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,index=-1,defaultImplementation = MAPExtensionContainerImpl.class)
    private MAPExtensionContainer extensionContainer;

    public MAPErrorMessageSMDeliveryFailureImpl(long mapProtocolVersion,
            SMEnumeratedDeliveryFailureCause smEnumeratedDeliveryFailureCause, byte[] signalInfo,
            MAPExtensionContainer extensionContainer) {
        super((long) MAPErrorCode.smDeliveryFailure);

        this.mapProtocolVersion = mapProtocolVersion;
        if(smEnumeratedDeliveryFailureCause!=null) {
        	this.sMEnumeratedDeliveryFailureCause = new ASNSMEnumeratedDeliveryFailureCauseImpl();
        	this.sMEnumeratedDeliveryFailureCause.setType(smEnumeratedDeliveryFailureCause);
        }
        
        if(signalInfo!=null) {	
        	this.signalInfo = new ASNOctetString();
            this.signalInfo.setValue(Unpooled.wrappedBuffer(signalInfo));
        }
        
        this.extensionContainer = extensionContainer;
    }

    public MAPErrorMessageSMDeliveryFailureImpl() {
        super((long) MAPErrorCode.smDeliveryFailure);
    }

    public SMEnumeratedDeliveryFailureCause getSMEnumeratedDeliveryFailureCause() {
    	if(this.sMEnumeratedDeliveryFailureCause==null)
    		return null;
    	
        return this.sMEnumeratedDeliveryFailureCause.getType();
    }

    public byte[] getSignalInfo() {
    	if(this.signalInfo==null)
    		return null;
    	
        ByteBuf buf=this.signalInfo.getValue();
        byte[] output=new byte[buf.readableBytes()];
        buf.readBytes(output);
        return output;
    }

    public long getMapProtocolVersion() {
        return this.mapProtocolVersion;
    }

    public MAPExtensionContainer getExtensionContainer() {
        return this.extensionContainer;
    }

    public void setSMEnumeratedDeliveryFailureCause(SMEnumeratedDeliveryFailureCause sMEnumeratedDeliveryFailureCause) {
    	if(sMEnumeratedDeliveryFailureCause==null)
    		this.sMEnumeratedDeliveryFailureCause=null;
    	else {
    		this.sMEnumeratedDeliveryFailureCause=new ASNSMEnumeratedDeliveryFailureCauseImpl();
    		this.sMEnumeratedDeliveryFailureCause.setType(sMEnumeratedDeliveryFailureCause);
    	}
    }

    public void setSignalInfo(byte[] signalInfo) {
    	if(signalInfo==null)
    		this.signalInfo=null;
    	else {
    		this.signalInfo = new ASNOctetString();
    		this.signalInfo.setValue(Unpooled.wrappedBuffer(signalInfo));
    	}
    }

    public void setExtensionContainer(MAPExtensionContainer extensionContainer) {
        this.extensionContainer = extensionContainer;
    }

    public void setMapProtocolVersion(long mapProtocolVersion) {
        this.mapProtocolVersion = mapProtocolVersion;
    }

    public boolean isEmSMDeliveryFailure() {
        return true;
    }

    public MAPErrorMessageSMDeliveryFailure getEmSMDeliveryFailure() {
        return this;
    }

    public SmsDeliverReportTpdu getSmsDeliverReportTpdu() throws MAPException {
        if (this.signalInfo != null) {
            SmsTpduImpl smsTpdu = SmsTpduImpl.createInstance(this.signalInfo.getValue(), true, null);
            if (smsTpdu.getSmsTpduType() == SmsTpduType.SMS_DELIVER_REPORT) {
                SmsDeliverReportTpduImpl drTpdu = (SmsDeliverReportTpduImpl) smsTpdu;
                return drTpdu;
            }
        }
        return null;
    }

    public void setSmsDeliverReportTpdu(SmsDeliverReportTpdu tpdu) throws MAPException {
    	ByteBuf buf=Unpooled.buffer();
    	tpdu.encodeData(buf);
    	byte[] data=new byte[buf.readableBytes()];
    	buf.readBytes(data);
        setSignalInfo(data);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("MAPErrorMessageSMDeliveryFailure [");
        if (this.sMEnumeratedDeliveryFailureCause != null)
            sb.append("sMEnumeratedDeliveryFailureCause=" + this.sMEnumeratedDeliveryFailureCause.toString());
        if (this.signalInfo != null)
            sb.append(", signalInfo=" + this.printDataArr(getSignalInfo()));
        if (this.extensionContainer != null)
            sb.append(", extensionContainer=" + this.extensionContainer.toString());
        sb.append("]");

        return sb.toString();
    }

    private String printDataArr(byte[] data) {
        StringBuilder sb = new StringBuilder();
        if (data != null) {
            for (int b : data) {
                sb.append(b);
                sb.append(", ");
            }
        }

        return sb.toString();
    }
}
