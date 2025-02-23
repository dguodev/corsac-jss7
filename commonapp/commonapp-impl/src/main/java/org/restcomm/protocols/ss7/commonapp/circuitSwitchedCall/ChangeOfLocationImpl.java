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

package org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall;

import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ChangeOfLocation;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ChangeOfLocationAlt;
import org.restcomm.protocols.ss7.commonapp.api.primitives.CellGlobalIdOrServiceAreaIdFixedLength;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LAIFixedLength;
import org.restcomm.protocols.ss7.commonapp.primitives.CellGlobalIdOrServiceAreaIdFixedLengthImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.LAIFixedLengthImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNNull;

/**
*
* @author sergey vetyutnev
*
*/
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class ChangeOfLocationImpl implements ChangeOfLocation {
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = false,index = -1, defaultImplementation = CellGlobalIdOrServiceAreaIdFixedLengthImpl.class)
    private CellGlobalIdOrServiceAreaIdFixedLength cellGlobalId;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = false,index = -1, defaultImplementation = CellGlobalIdOrServiceAreaIdFixedLengthImpl.class)
    private CellGlobalIdOrServiceAreaIdFixedLength serviceAreaId;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 2,constructed = false,index = -1, defaultImplementation = LAIFixedLengthImpl.class)
    private LAIFixedLength locationAreaId;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 3,constructed = false,index = -1)
    private ASNNull interSystemHandOver;

    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 4,constructed = false,index = -1)
    private ASNNull interPLMNHandOver;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 5,constructed = false,index = -1)
    private ASNNull interMSCHandOver;

    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 6,constructed = true,index = -1, defaultImplementation = ChangeOfLocationAltImpl.class)
    private ChangeOfLocationAlt changeOfLocationAlt;

    public ChangeOfLocationImpl() {
    }

    public ChangeOfLocationImpl(CellGlobalIdOrServiceAreaIdFixedLength value, CellGlobalIdOrServiceAreaIdFixedLength_Option option) {
        switch (option) {
        case cellGlobalId:
            this.cellGlobalId = value;
            break;
        case serviceAreaId:
            this.serviceAreaId = value;
            break;
        }
    }

    public ChangeOfLocationImpl(LAIFixedLength locationAreaId) {
        this.locationAreaId = locationAreaId;
    }

    public ChangeOfLocationImpl(Boolean_Option option) {
        switch (option) {
        case interSystemHandOver:
            this.interSystemHandOver = new ASNNull();
            break;
        case interPLMNHandOver:
            this.interPLMNHandOver = new ASNNull();
            break;
        case interMSCHandOver:
            this.interMSCHandOver = new ASNNull();
            break;
        }
    }

    public ChangeOfLocationImpl(ChangeOfLocationAlt changeOfLocationAlt) {
        this.changeOfLocationAlt = changeOfLocationAlt;
    }

    public CellGlobalIdOrServiceAreaIdFixedLength getCellGlobalId() {
        return cellGlobalId;
    }

    public CellGlobalIdOrServiceAreaIdFixedLength getServiceAreaId() {
        return serviceAreaId;
    }

    public LAIFixedLength getLocationAreaId() {
        return locationAreaId;
    }

    public boolean isInterSystemHandOver() {
        return interSystemHandOver!=null;
    }

    public boolean isInterPLMNHandOver() {
        return interPLMNHandOver!=null;
    }

    public boolean isInterMSCHandOver() {
        return interMSCHandOver!=null;
    }

    public ChangeOfLocationAlt getChangeOfLocationAlt() {
        return changeOfLocationAlt;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("ChangeOfLocation [");

        if (cellGlobalId != null) {
            sb.append("cellGlobalId=");
            sb.append(cellGlobalId);
        } else if (serviceAreaId != null) {
            sb.append("serviceAreaId=");
            sb.append(serviceAreaId);
        } else if (locationAreaId != null) {
            sb.append("locationAreaId=");
            sb.append(locationAreaId);
        } else if (interSystemHandOver!=null) {
            sb.append("interSystemHandOver");
        } else if (interPLMNHandOver!=null) {
            sb.append("interPLMNHandOver");
        } else if (interMSCHandOver!=null) {
            sb.append("interMSCHandOver");
        } else if (changeOfLocationAlt != null) {
            sb.append("changeOfLocationAlt=");
            sb.append(changeOfLocationAlt);
        }

        sb.append("]");

        return sb.toString();
    }

}
