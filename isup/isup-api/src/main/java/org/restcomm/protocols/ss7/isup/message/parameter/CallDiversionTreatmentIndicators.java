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

/**
 * Start time:11:09:03 2009-07-23<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 */
package org.restcomm.protocols.ss7.isup.message.parameter;

import io.netty.buffer.ByteBuf;

/**
 * Start time:11:09:03 2009-07-23<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 */
public interface CallDiversionTreatmentIndicators extends ISUPParameter {
    int _PARAMETER_CODE = 0x6E;
    /**
     * See Q.763 3.72 Call to be diverted indicator : no indication
     */
    int _NO_INDICATION = 0;

    /**
     * See Q.763 3.72 Call to be diverted indicator : call diversion allowed
     */
    int _CD_ALLOWED = 1;

    /**
     * See Q.763 3.72 Call to be diverted indicator : call diversion not allowed
     */
    int _CD_NOT_ALLOWED = 2;

    ByteBuf getCallDivertedIndicators();

    void setCallDivertedIndicators(ByteBuf callDivertedIndicators);

    int getDiversionIndicator(byte b);
}
