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

import java.util.List;

import org.restcomm.protocols.ss7.commonapp.api.subscriberManagement.LSAIdentity;
import org.restcomm.protocols.ss7.commonapp.subscriberManagement.LSAIdentityListWrapperImpl;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.LSAInformationWithdraw;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNNull;

/**
*
* @author sergey vetyutnev
*
*/
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,lengthIndefinite=false)
public class LSAInformationWithdrawImpl implements LSAInformationWithdraw {
	private ASNNull allLSAData;
    private LSAIdentityListWrapperImpl lsaIdentityList;

    public LSAInformationWithdrawImpl() {
    }

    public LSAInformationWithdrawImpl(boolean allLSAData) {
    	if(allLSAData)
    		this.allLSAData = new ASNNull();
    }

    public LSAInformationWithdrawImpl(List<LSAIdentity> lsaIdentityList) {
    	if(lsaIdentityList!=null)
    		this.lsaIdentityList = new LSAIdentityListWrapperImpl(lsaIdentityList);
    }


    public boolean getAllLSAData() {
        return this.allLSAData!=null;
    }

    public List<LSAIdentity> getLSAIdentityList() {
    	if(this.lsaIdentityList==null)
    		return null;
    	
        return this.lsaIdentityList.getLSAIdentity();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("LSAInformationWithdraw [");

        if (this.allLSAData!=null) {
            sb.append("allLSAData, ");
        }
        if (this.lsaIdentityList != null && this.lsaIdentityList.getLSAIdentity()!=null) {
            sb.append("lsaIdentityList=[");
            for (LSAIdentity lsaId : this.lsaIdentityList.getLSAIdentity()) {
                sb.append(lsaId);
                sb.append(", ");
            }
            sb.append("], ");
        }

        sb.append("]");

        return sb.toString();
    }
}
