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
 * Start time:12:21:06 2009-04-23<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski
 *         </a>
 *
 */
package org.restcomm.protocols.ss7.isup.impl.message.parameter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.reflect.InvocationTargetException;

import org.restcomm.protocols.ss7.isup.ParameterException;
import org.restcomm.protocols.ss7.isup.message.parameter.InvokingPivotReason;
import org.restcomm.protocols.ss7.isup.message.parameter.PivotReason;
import org.restcomm.protocols.ss7.isup.message.parameter.ReturnToInvokingExchangeCallIdentifier;
import org.restcomm.protocols.ss7.isup.message.parameter.ReturnToInvokingExchangeDuration;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Start time:12:21:06 2009-04-23<br>
 * Project: restcomm-isup-stack<br>
 * Class to test BCI
 * 
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 */

public class PivotRoutingBackwardInformationTest {
//This one does not use harness, since this param has multiple levels of nesting ....
    public PivotRoutingBackwardInformationTest() {
        super();

    }

    private ByteBuf getBody1() {

        byte[] body = new byte[] {
          //3.100.1 duration
          0x01,
              //len
              0x02,
              (byte) 0xAA,
              (byte) 0xBB,
          //3.100.2
              0x02,
                  //len
                  0x05,
                  //body
                  (byte) 0xAA,
                  0,
                  (byte) 0xAA,
                  0x55,
                  0x15,
           //3.100.3
              0x03,
                  //len
                  0x03,
                //body
                  0x01,
                  0x02,
                  (byte)(0x80| 0x03)
        };
        return Unpooled.wrappedBuffer(body);
    }

    @Test(groups = { "functional.encode", "functional.decode", "parameter" })
    public void testBody1EncodedValues() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, ParameterException {
        PivotRoutingBackwardInformationImpl parameter = new PivotRoutingBackwardInformationImpl(getBody1());
        
        ReturnToInvokingExchangeDuration[] rtied = parameter.getReturnToInvokingExchangeDuration();
        Assert.assertNotNull(rtied);
        Assert.assertEquals(rtied.length,1);
        Assert.assertNotNull(rtied[0]);
        Assert.assertEquals(rtied[0].getDuration(),0xBBAA);

        ReturnToInvokingExchangeCallIdentifier[] callIds = parameter.getReturnToInvokingExchangeCallIdentifier();
        Assert.assertNotNull(callIds);
        Assert.assertEquals(callIds.length,1);
        ReturnToInvokingExchangeCallIdentifier id = callIds[0];
        Assert.assertNotNull(id);
        Assert.assertEquals(id.getCallIdentity(), 0xAA00AA);
        Assert.assertEquals(id.getSignalingPointCode(), 0x1555);

        InvokingPivotReason[] inrs = parameter.getInvokingPivotReason();
        Assert.assertNotNull(inrs);
        Assert.assertEquals(inrs.length,1);
        Assert.assertNotNull(inrs[0]);
        InvokingPivotReason inr = inrs[0];
        PivotReason[] rrs2 = inr.getReason();
        Assert.assertNotNull(rrs2);
        Assert.assertEquals(rrs2.length,3);
        Assert.assertNotNull(rrs2[0]);
        Assert.assertEquals(rrs2[0].getPivotReason(), 1);
        Assert.assertNotNull(rrs2[1]);
        Assert.assertEquals(rrs2[1].getPivotReason(), 2);
        Assert.assertNotNull(rrs2[2]);
        Assert.assertEquals(rrs2[2].getPivotReason(), 3);
    }

    
    @Test(groups = { "functional.encode", "functional.decode", "parameter" })
    public void testSetAndGet() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, ParameterException {
        PivotRoutingBackwardInformationImpl parameter = new PivotRoutingBackwardInformationImpl();

       ReturnToInvokingExchangeDurationImpl duration  = new ReturnToInvokingExchangeDurationImpl();
       duration.setDuration(0xAA0C);
       parameter.setReturnToInvokingExchangeDuration(duration);

        ReturnToInvokingExchangeCallIdentifierImpl callId = new ReturnToInvokingExchangeCallIdentifierImpl();
        callId.setCallIdentity(0XBB00BC);
        ReturnToInvokingExchangeCallIdentifierImpl callId2 = new ReturnToInvokingExchangeCallIdentifierImpl();
        callId2.setCallIdentity(0XCBF0BC);
        callId2.setSignalingPointCode(1);
        parameter.setReturnToInvokingExchangeCallIdentifier(callId,callId2);

        InvokingPivotReasonImpl irr = new InvokingPivotReasonImpl();
        //this differs across some params...
        irr.setTag(PivotRoutingBackwardInformationImpl.INFORMATION_INVOKING_PIVOT_REASON);
        PivotReasonImpl rr1 = new PivotReasonImpl();
        rr1.setPivotReason((byte) 1);
        PivotReasonImpl rr2 = new PivotReasonImpl();
        rr2.setPivotReason((byte) 1);
        rr2.setPivotPossibleAtPerformingExchange((byte) 2);
        irr.setReason(rr1,rr2);
        parameter.setInvokingPivotReason(irr);

        ByteBuf data = Unpooled.buffer();
        parameter.encode(data);
        parameter = new PivotRoutingBackwardInformationImpl();
        parameter.decode(data);

        Assert.assertNotNull(parameter.getReturnToInvokingExchangeDuration());
        Assert.assertEquals(parameter.getReturnToInvokingExchangeDuration().length,1);
        Assert.assertNotNull(parameter.getReturnToInvokingExchangeDuration()[0]);
        
        Assert.assertEquals(parameter.getReturnToInvokingExchangeDuration()[0].getDuration(),0xAA0C);

        Assert.assertNotNull(parameter.getReturnToInvokingExchangeCallIdentifier());
        Assert.assertEquals(parameter.getReturnToInvokingExchangeCallIdentifier().length,2);
        Assert.assertNotNull(parameter.getReturnToInvokingExchangeCallIdentifier()[0]);
        Assert.assertNotNull(parameter.getReturnToInvokingExchangeCallIdentifier()[1]);
        
        Assert.assertEquals(parameter.getReturnToInvokingExchangeCallIdentifier()[0].getCallIdentity(),0XBB00BC);
        Assert.assertEquals(parameter.getReturnToInvokingExchangeCallIdentifier()[1].getCallIdentity(),0XCBF0BC);
        Assert.assertEquals(parameter.getReturnToInvokingExchangeCallIdentifier()[1].getSignalingPointCode(),1);


        Assert.assertNotNull(parameter.getInvokingPivotReason()[0].getReason());
        Assert.assertEquals(parameter.getInvokingPivotReason()[0].getReason().length,2);
        Assert.assertNotNull(parameter.getInvokingPivotReason()[0].getReason()[0]);
        Assert.assertNotNull(parameter.getInvokingPivotReason()[0].getReason()[1]);
        Assert.assertEquals(parameter.getInvokingPivotReason()[0].getReason()[0].getPivotReason(),1);
        Assert.assertEquals(parameter.getInvokingPivotReason()[0].getReason()[1].getPivotReason(),1);
        //0 casuse this one does not have it.
        Assert.assertEquals(parameter.getInvokingPivotReason()[0].getReason()[1].getPivotPossibleAtPerformingExchange(),0);
    }

}
