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

package org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive;

/**
 *
<code>
ForwardingCondition ::= ENUMERATED {
busy(0),
noanswer(1),
any(2)
}
-- Indicates the condition that must be met to complete the connect.
-- ReportCondition specifies the cause of sending “StatusReport” operation to the SCF
</code>
 *
 *
 * @author yulian.oifa
 *
 */
public enum ForwardingCondition {
	busy(0),
	noanswer(1),
	any(2);

    private int code;

    private ForwardingCondition(int code) {
        this.code = code;
    }

    public static ForwardingCondition getInstance(int code) {
        switch (code) {
            case 0:
                return ForwardingCondition.busy;
            case 1:
                return ForwardingCondition.noanswer; 
            case 2:
                return ForwardingCondition.any; 
        }

        return null;
    }

    public int getCode() {
        return code;
    }
}
