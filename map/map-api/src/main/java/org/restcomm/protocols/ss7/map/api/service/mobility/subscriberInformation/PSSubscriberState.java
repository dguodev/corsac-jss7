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

package org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation;

import java.util.List;

import org.restcomm.protocols.ss7.commonapp.api.subscriberInformation.NotReachableReason;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;

/**
<code>
PS-SubscriberState ::= CHOICE {
  notProvidedFromSGSNorMME            [0] NULL,
  ps-Detached                         [1] NULL,
  ps-AttachedNotReachableForPaging    [2] NULL,
  ps-AttachedReachableForPaging       [3] NULL,
  ps-PDP-ActiveNotReachableForPaging  [4] PDP-ContextInfoList,
  ps-PDP-ActiveReachableForPaging     [5] PDP-ContextInfoList,
  netDetNotReachable                  NotReachableReason
}
PDP-ContextInfoList ::= SEQUENCE SIZE (1..50) OF PDP-ContextInfo
</code>
 *
 *
 * @author amit bhayani
 * @author sergey vetyutnev
 *
 */
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,lengthIndefinite=false)
public interface PSSubscriberState {

    PSSubscriberStateChoise getChoice();

    List<PDPContextInfo> getPDPContextInfoList();

    NotReachableReason getNetDetNotReachable();

}