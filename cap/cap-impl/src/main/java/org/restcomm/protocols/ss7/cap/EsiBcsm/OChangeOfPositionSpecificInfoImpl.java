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

package org.restcomm.protocols.ss7.cap.EsiBcsm;

import java.util.List;

import org.restcomm.protocols.ss7.cap.api.EsiBcsm.MetDPCriterion;
import org.restcomm.protocols.ss7.cap.api.EsiBcsm.OChangeOfPositionSpecificInfo;
import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.LocationInformation;
import org.restcomm.protocols.ss7.commonapp.subscriberInformation.LocationInformationImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
*
* @author sergey vetyutnev
*
*/
@ASNTag(asnClass = ASNClass.UNIVERSAL, tag = 16,constructed = true,lengthIndefinite = false)
public class OChangeOfPositionSpecificInfoImpl implements OChangeOfPositionSpecificInfo {
	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC, tag = 50, constructed = true,index = -1, defaultImplementation = LocationInformationImpl.class)
    private LocationInformation locationInformation;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC, tag = 51, constructed = true,index = -1)
    private MetDPCriterionWrapperImpl metDPCriteriaList;

    public OChangeOfPositionSpecificInfoImpl() {        
    }

    public OChangeOfPositionSpecificInfoImpl(LocationInformation locationInformation, List<MetDPCriterion> metDPCriteriaList) {
        this.locationInformation = locationInformation;
        
        if(metDPCriteriaList!=null)
        	this.metDPCriteriaList = new MetDPCriterionWrapperImpl(metDPCriteriaList);
    }

    public LocationInformation getLocationInformation() {
        return locationInformation;
    }

    public List<MetDPCriterion> getMetDPCriteriaList() {
    	if(metDPCriteriaList==null)
    			return null;
    	
        return metDPCriteriaList.getMetDPCriterion();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("OChangeOfPositionSpecificInfo [");

        if (this.locationInformation != null) {
            sb.append("locationInformation=");
            sb.append(locationInformation);
            sb.append(", ");
        }
        if (this.metDPCriteriaList != null && this.metDPCriteriaList.getMetDPCriterion()!=null) {
            sb.append("metDPCriteriaList=[");
            boolean firstItem = true;
            for (MetDPCriterion be : this.metDPCriteriaList.getMetDPCriterion()) {
                if (firstItem)
                    firstItem = false;
                else
                    sb.append(", ");
                sb.append(be.toString());
            }
            sb.append("], ");
        }

        sb.append("]");

        return sb.toString();
    }
}
