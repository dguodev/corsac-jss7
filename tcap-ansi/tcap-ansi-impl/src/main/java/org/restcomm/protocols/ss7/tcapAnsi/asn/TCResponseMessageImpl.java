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

package org.restcomm.protocols.ss7.tcapAnsi.asn;

import java.util.Arrays;

import org.restcomm.protocols.ss7.tcapAnsi.api.asn.comp.ComponentPortion;
import org.restcomm.protocols.ss7.tcapAnsi.api.asn.comp.TCResponseMessage;
import org.restcomm.protocols.ss7.tcapAnsi.api.asn.comp.WrappedComponent;
import org.restcomm.protocols.ss7.tcapAnsi.asn.comp.ComponentPortionImpl;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
 * @author baranowb
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass=ASNClass.PRIVATE,tag=4,constructed=true,lengthIndefinite=false)
public class TCResponseMessageImpl extends TCUnifiedMessageImpl implements TCResponseMessage {
	
	@ASNProperty(asnClass=ASNClass.PRIVATE,tag=0x08,constructed=true,index=-1,defaultImplementation = ComponentPortionImpl.class)
	private ComponentPortion component;

    
    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.asn.comp.TCBeginMessage#getComponent()
     */
    public ComponentPortion getComponent() {

        return this.component;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.protocols.ss7.tcap.asn.comp.TCBeginMessage#setComponent
     * (org.restcomm.protocols.ss7.tcap.asn.comp.Component[])
     */
    public void setComponent(ComponentPortion c) {
        this.component = c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TCResponseMessage [");

        if (this.getDestinationTransactionId() != null) {
            sb.append("destinationTransactionId=[");
            sb.append(Arrays.toString(this.getDestinationTransactionId()));
            sb.append("], ");
        }
        if (this.getDialogPortion() != null) {
            sb.append("DialogPortion=");
            sb.append(this.getDialogPortion());
            sb.append(", ");
        }
        if (this.component != null && this.component.getComponents()!=null && this.component.getComponents().size() > 0) {
            sb.append("Components=[");
            int i1 = 0;
            for (WrappedComponent comp : this.component.getComponents()) {
                if (i1 == 0)
                    i1 = 1;
                else
                    sb.append(", ");
                sb.append(comp.toString());
            }
            sb.append("], ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public byte[] getOriginatingTransactionId() {
        return super.getDestinationTransactionId();
    }

    @Override
    public byte[] getDestinationTransactionId() {
        return super.getOriginatingTransactionId();
    }

    @Override
	public void setOriginatingTransactionId(byte[] txID) {
		super.setDestinationTransactionId(txID);
	}

	@Override
	public void setDestinationTransactionId(byte[] txID) {
		super.setOriginatingTransactionId(txID);
	}
	
	@Override
	public boolean validate() {
		return getOriginatingTransactionId()==null && getDestinationTransactionId()!=null;
	}
}
