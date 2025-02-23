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

package org.restcomm.protocols.ss7.inap.charging;

import org.restcomm.protocols.ss7.inap.api.charging.TariffCurrency;
import org.restcomm.protocols.ss7.inap.api.charging.TariffCurrencyFormat;
import org.restcomm.protocols.ss7.inap.api.charging.TariffSwitchCurrency;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
 *
 * @author yulian.oifa
 *
 */
@ASNTag(asnClass = ASNClass.UNIVERSAL,tag = 16,constructed = true,lengthIndefinite = false)
public class TariffCurrencyImpl implements TariffCurrency {

	@ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 0,constructed = true, index=-1, defaultImplementation = TariffCurrencyFormatImpl.class)
    private TariffCurrencyFormat tariffCurrencyFormat;
    
    @ASNProperty(asnClass = ASNClass.CONTEXT_SPECIFIC,tag = 1,constructed = true, index=-1, defaultImplementation = TariffSwitchCurrencyImpl.class)
    private TariffSwitchCurrency tariffSwitchCurrency;

    public TariffCurrencyImpl() {
    }

    public TariffCurrencyImpl(TariffCurrencyFormat tariffCurrencyFormat,TariffSwitchCurrency tariffSwitchCurrency) {
    	this.tariffCurrencyFormat=tariffCurrencyFormat; 
    	this.tariffSwitchCurrency=tariffSwitchCurrency;
    }

    public TariffCurrencyFormat getTariffCurrencyFormat() {
    	return tariffCurrencyFormat;
    }

    public TariffSwitchCurrency getTariffSwitchCurrency() {
    	return tariffSwitchCurrency;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("TariffCurrency [");

        if (this.tariffCurrencyFormat != null) {
            sb.append(", tariffCurrencyFormat=");
            sb.append(tariffCurrencyFormat);
        }

        if (this.tariffSwitchCurrency != null) {
            sb.append(", tariffSwitchCurrency=");
            sb.append(tariffSwitchCurrency);
        }
        
        sb.append("]");

        return sb.toString();
    }
}