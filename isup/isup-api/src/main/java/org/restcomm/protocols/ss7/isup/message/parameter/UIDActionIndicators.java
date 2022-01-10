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
 * Start time:14:17:18 2009-07-23<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 */
package org.restcomm.protocols.ss7.isup.message.parameter;

import io.netty.buffer.ByteBuf;

/**
 * Start time:14:17:18 2009-07-23<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 */
public interface UIDActionIndicators extends ISUPParameter {

    int _PARAMETER_CODE = 0x74;
    // FIXME: add C defs
    /**
     * See Q.763 3.78 Through-connection instruction indicator : no indication
     */
    boolean _TCII_NO_INDICATION = false;

    /**
     * See Q.763 3.78 Through-connection instruction indicator : through-connect in both directions
     */
    boolean _TCII_TCIBD = true;

    /**
     * See Q.763 3.78 T9 timer instruction indicator : no indication
     */
    boolean _T9_TII_NO_INDICATION = false;

    /**
     * See Q.763 3.78 T9 timer instruction indicator : stop or do not start T9 timer
     */
    boolean _T9_TII_SDNST9T = false;

    ByteBuf getUdiActionIndicators();

    void setUdiActionIndicators(ByteBuf udiActionIndicators);

    byte createUIDAction(boolean TCII, boolean T9);

    boolean getT9Indicator(byte b);

    boolean getTCIIndicator(byte b);
}
