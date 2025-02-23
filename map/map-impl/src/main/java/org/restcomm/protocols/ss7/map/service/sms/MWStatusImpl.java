/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
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

package org.restcomm.protocols.ss7.map.service.sms;

import org.restcomm.protocols.ss7.map.api.service.sms.MWStatus;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNBitString;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class MWStatusImpl extends ASNBitString implements MWStatus {
	private static final int _INDEX_ScAddressNotIncluded = 0;
    private static final int _INDEX_MnrfSet = 1;
    private static final int _INDEX_McefSet = 2;
    private static final int _INDEX_MnrgSet = 3;

    public MWStatusImpl() {
    }

    public MWStatusImpl(boolean scAddressNotIncluded, boolean mnrfSet, boolean mcefSet, boolean mnrgSet) {
        if (scAddressNotIncluded)
            this.setBit(_INDEX_ScAddressNotIncluded);
        if (mnrfSet)
            this.setBit(_INDEX_MnrfSet);
        if (mcefSet)
            this.setBit(_INDEX_McefSet);
        if (mnrgSet)
            this.setBit(_INDEX_MnrgSet);
    }

    public boolean getScAddressNotIncluded() {
        return this.isBitSet(_INDEX_ScAddressNotIncluded);
    }

    public boolean getMnrfSet() {
        return this.isBitSet(_INDEX_MnrfSet);
    }

    public boolean getMcefSet() {
        return this.isBitSet(_INDEX_McefSet);
    }

    public boolean getMnrgSet() {
        return this.isBitSet(_INDEX_MnrgSet);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MWStatus [");
        if (this.getScAddressNotIncluded())
            sb.append("ScAddressNotIncluded, ");
        if (this.getMnrfSet())
            sb.append("MnrfSet, ");
        if (this.getMcefSet())
            sb.append("McefSet, ");
        if (this.getMnrgSet())
            sb.append("MnrgSet, ");
        sb.append("]");
        return sb.toString();
    }

}
