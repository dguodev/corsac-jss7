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

package org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall;

/**
 *
<code>
conferenceTreatmentIndicator [1] OCTET STRING (SIZE(1)) OPTIONAL,
-- acceptConferenceRequest 'xxxx xx01'B
-- rejectConferenceRequest 'xxxx xx10'B
-- if absent from Connect or ContinueWithArgument,
-- then CAMEL service does not affect conference treatment
</code>
 *
 * @author sergey vetyutnev
 *
 */
public enum ConferenceTreatmentIndicator {

    acceptConferenceRequest(1), rejectConferenceRequest(2);

    private int code;

    private ConferenceTreatmentIndicator(int code) {
        this.code = code;
    }

    public static ConferenceTreatmentIndicator getInstance(int code) {
        switch (code & 0x03) {
            case 1:
                return ConferenceTreatmentIndicator.acceptConferenceRequest;
            case 2:
                return ConferenceTreatmentIndicator.rejectConferenceRequest;
            default:
                return null;
        }
    }

    public int getCode() {
        return this.code;
    }
}
