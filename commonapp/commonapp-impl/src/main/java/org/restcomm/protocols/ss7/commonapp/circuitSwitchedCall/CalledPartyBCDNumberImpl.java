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

import org.restcomm.protocols.ss7.commonapp.api.APPException;
import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.CalledPartyBCDNumber;
import org.restcomm.protocols.ss7.commonapp.api.primitives.AddressNature;
import org.restcomm.protocols.ss7.commonapp.api.primitives.NumberingPlan;
import org.restcomm.protocols.ss7.commonapp.primitives.AddressStringImpl;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class CalledPartyBCDNumberImpl extends AddressStringImpl implements CalledPartyBCDNumber {
	protected static final int NO_EXTENSION_MASK = 0x80;
    protected static final int NATURE_OF_ADD_IND_MASK = 0x70;
    protected static final int NUMBERING_PLAN_IND_MASK = 0x0F;

    public CalledPartyBCDNumberImpl() {
        super(41);
    }

    public CalledPartyBCDNumberImpl(AddressNature addressNature, NumberingPlan numberingPlan, String address)
            throws APPException {
        super(41, addressNature, numberingPlan, address);
    }
}