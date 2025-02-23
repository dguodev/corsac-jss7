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

package org.restcomm.protocols.ss7.map.service.callhandling;

import org.restcomm.protocols.ss7.map.api.service.callhandling.AllowedServices;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNBitString;

/*
 * AllowedServices ::= BIT STRING { firstServiceAllowed (0), secondServiceAllowed (1) } (SIZE (2..8)) -- firstService is the
 * service indicated in the networkSignalInfo -- secondService is the service indicated in the networkSignalInfo2 -- Other bits
 * than listed above shall be discarded
 * @author cristian veliscu
 *
 */
public class AllowedServicesImpl extends ASNBitString implements AllowedServices {
	/**
     *
     */
    public AllowedServicesImpl() {
    }

    public AllowedServicesImpl(boolean suppressCUG, boolean suppressCCBS) {    	
        if (suppressCUG)
        	super.setBit(0);            
        if (suppressCCBS)
        	super.setBit(1);
    }

    public boolean getFirstServiceAllowed() {
    	return super.isBitSet(0);        
    }

    public boolean getSecondServiceAllowed() {
        return super.isBitSet(1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AllowedServices [");

        if (getFirstServiceAllowed())
            sb.append("FirstServiceAllowed, ");
        if (getSecondServiceAllowed())
            sb.append("SecondServiceAllowed, ");

        sb.append("]");
        return sb.toString();
    }
}
