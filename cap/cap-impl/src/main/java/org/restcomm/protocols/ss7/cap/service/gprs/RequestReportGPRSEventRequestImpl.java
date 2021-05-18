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
package org.restcomm.protocols.ss7.cap.service.gprs;

import java.util.List;

import org.restcomm.protocols.ss7.cap.api.CAPMessageType;
import org.restcomm.protocols.ss7.cap.api.CAPOperationCode;
import org.restcomm.protocols.ss7.cap.api.service.gprs.RequestReportGPRSEventRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.GPRSEventImpl;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.GprsEventWrapperImpl;
import org.restcomm.protocols.ss7.cap.api.service.gprs.primitive.PDPIDImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class RequestReportGPRSEventRequestImpl extends GprsMessageImpl implements RequestReportGPRSEventRequest {
	private static final long serialVersionUID = 1L;

	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = true,index = -1)
    private GprsEventWrapperImpl gprsEvent;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = false,index = -1)
    private PDPIDImpl pdpID;

    public RequestReportGPRSEventRequestImpl() {
        super();
    }

    public RequestReportGPRSEventRequestImpl(List<GPRSEventImpl> gprsEvent, PDPIDImpl pdpID) {
        super();
        
        if(gprsEvent!=null)
        	this.gprsEvent = new GprsEventWrapperImpl(gprsEvent);
        
        this.pdpID = pdpID;
    }

    @Override
    public List<GPRSEventImpl> getGPRSEvent() {
    	if(this.gprsEvent==null)
    		return null;
    	
        return this.gprsEvent.getGPRSEvents();
    }

    @Override
    public PDPIDImpl getPDPID() {
        return this.pdpID;
    }

    @Override
    public CAPMessageType getMessageType() {
        return CAPMessageType.requestReportGPRSEvent_Request;
    }

    @Override
    public int getOperationCode() {
        return CAPOperationCode.requestReportGPRSEvent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RequestReportGPRSEventRequest [");
        this.addInvokeIdInfo(sb);

        if (this.gprsEvent != null && this.gprsEvent.getGPRSEvents()!=null) {
            sb.append(", gprsEvent=[");
            boolean firstItem = true;
            for (GPRSEventImpl be : this.gprsEvent.getGPRSEvents()) {
                if (firstItem)
                    firstItem = false;
                else
                    sb.append(", ");
                sb.append(be.toString());
            }
            sb.append("]");
        }

        if (this.pdpID != null) {
            sb.append(", pdpID=");
            sb.append(this.pdpID.toString());
        }

        sb.append("]");

        return sb.toString();
    }

}
