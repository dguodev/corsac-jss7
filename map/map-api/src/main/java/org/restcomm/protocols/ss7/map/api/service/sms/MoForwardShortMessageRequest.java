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

package org.restcomm.protocols.ss7.map.api.service.sms;

import org.restcomm.protocols.ss7.commonapp.api.primitives.IMSI;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MAPExtensionContainer;

/**
 *
 MAP V3:
 *
 * mo-ForwardSM OPERATION ::= { --Timer ml ARGUMENT MO-ForwardSM-Arg RESULT MO-ForwardSM-Res -- optional ERRORS { systemFailure
 * | unexpectedDataValue | facilityNotSupported | sm-DeliveryFailure} CODE local:46 }
 *
 * MO-ForwardSM-Arg ::= SEQUENCE { sm-RP-DA SM-RP-DA, sm-RP-OA SM-RP-OA, sm-RP-UI SignalInfo, extensionContainer
 * ExtensionContainer OPTIONAL, ... , imsi IMSI OPTIONAL }
 *
 *
 *
 *
 * @author sergey vetyutnev
 *
 */
public interface MoForwardShortMessageRequest extends SmsMessage {

    SM_RP_DA getSM_RP_DA();

    SM_RP_OA getSM_RP_OA();

    SmsSignalInfo getSM_RP_UI();

    MAPExtensionContainer getExtensionContainer();

    IMSI getIMSI();
}