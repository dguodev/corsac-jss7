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

package org.restcomm.protocols.ss7.map.service.mobility.subscriberManagement;

import java.util.ArrayList;
import java.util.List;

import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.EPSSubscriptionDataWithdraw;
import org.restcomm.protocols.ss7.map.primitives.ASNIntegerListWrapperImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNInteger;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNNull;

/**
*
* @author sergey vetyutnev
*
*/
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,lengthIndefinite=false)
public class EPSSubscriptionDataWithdrawImpl implements EPSSubscriptionDataWithdraw {
	private ASNNull allEpsData;
    private ASNIntegerListWrapperImpl contextIdList;

    public EPSSubscriptionDataWithdrawImpl() {
    }

    public EPSSubscriptionDataWithdrawImpl(boolean allEpsData) {
    	if(allEpsData)
    		this.allEpsData = new ASNNull();
    }

    public EPSSubscriptionDataWithdrawImpl(List<Integer> contextIdList) {
    	if(contextIdList!=null) {
    		List<ASNInteger> data=new ArrayList<ASNInteger>();
    		for(Integer curr:contextIdList) {
    			ASNInteger currData=new ASNInteger();
    			currData.setValue(curr.longValue());
    			data.add(currData);
    		}
    		
    		this.contextIdList = new ASNIntegerListWrapperImpl(data);
    	}
    }


    public boolean getAllEpsData() {
        return allEpsData!=null;
    }

    public List<Integer> getContextIdList() {
    	if(contextIdList==null || contextIdList.getIntegers()==null)
    		return null;
    	
    	List<Integer> data=new ArrayList<Integer>();
		for(ASNInteger curr:contextIdList.getIntegers()) {
			data.add(curr.getValue().intValue());
		}
		
        return data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("EPSSubscriptionDataWithdraw [");

        if (this.allEpsData!=null) {
            sb.append("allEpsData, ");
        }
        if (this.contextIdList != null && this.contextIdList.getIntegers()!=null) {
            sb.append("contextIdList=[");
            for (ASNInteger i1 : this.contextIdList.getIntegers()) {
                sb.append(i1.getValue());
                sb.append(", ");
            }
            sb.append("], ");
        }

        sb.append("]");

        return sb.toString();
    }

}
