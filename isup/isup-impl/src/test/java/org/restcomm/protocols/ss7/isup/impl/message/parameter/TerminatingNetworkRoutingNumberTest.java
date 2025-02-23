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
 * Start time:21:30:13 2009-04-26<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski
 *         </a>
 *
 */
package org.restcomm.protocols.ss7.isup.impl.message.parameter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.restcomm.protocols.ss7.isup.ParameterException;
import org.testng.annotations.Test;

/**
 * Start time:21:30:13 2009-04-26<br>
 * Project: restcomm-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 */
public class TerminatingNetworkRoutingNumberTest extends ParameterHarness {

    public TerminatingNetworkRoutingNumberTest() throws IOException {
        super();
        super.goodBodies.add(getBody(false, TerminatingNetworkRoutingNumberImpl._NPI_ISDN,
                TerminatingNetworkRoutingNumberImpl._NAI_NATIONAL_SN, getSixDigits(), getSixDigits().length));
        super.goodBodies.add(getBody(false, TerminatingNetworkRoutingNumberImpl._NPI_ISDN,
                TerminatingNetworkRoutingNumberImpl._NAI_NATIONAL_SN, null, -1));
        super.goodBodies.add(getBody(false, TerminatingNetworkRoutingNumberImpl._NPI_ISDN,
                TerminatingNetworkRoutingNumberImpl._NAI_NATIONAL_SN, null, 0));

        super.badBodies.add(getBody(false, TerminatingNetworkRoutingNumberImpl._NPI_ISDN,
                TerminatingNetworkRoutingNumberImpl._NAI_NATIONAL_SN, new byte[8], 8));
        // The diff is that this is odd - only 15 digits :)
        super.goodBodies.add(getBody(true, TerminatingNetworkRoutingNumberImpl._NPI_ISDN,
                TerminatingNetworkRoutingNumberImpl._NAI_NATIONAL_SN, new byte[8], 8));
    }

    @Test(groups = { "functional.encode", "functional.decode", "parameter" })
    public void testBody1EncodedValues() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, IOException, ParameterException {
        TerminatingNetworkRoutingNumberImpl bci = new TerminatingNetworkRoutingNumberImpl(getBody(false,
                TerminatingNetworkRoutingNumberImpl._NPI_ISDN, TerminatingNetworkRoutingNumberImpl._NAI_NATIONAL_SN,
                getSixDigits(), getSixDigits().length));

        String[] methodNames = { "isOddFlag", "getNumberingPlanIndicator", "getNatureOfAddressIndicator", "getAddress",
                "getTnrnLengthIndicator" };
        Object[] expectedValues = { false, TerminatingNetworkRoutingNumberImpl._NPI_ISDN,
                TerminatingNetworkRoutingNumberImpl._NAI_NATIONAL_SN, getSixDigitsString(), 4 };
        super.testValues(bci, methodNames, expectedValues);
    }

    private ByteBuf getBody(boolean isODD, int npiIsdn, int naiNationalSn, byte[] sixDigits, int tnrL) throws IOException {
    	ByteBuf bos = Unpooled.buffer();
        int v = 0;
        if (isODD)
            v |= 0x80;
        v |= npiIsdn << 4;
        // v |= sixDigits.length + 1;
        v |= tnrL + 1;

        bos.writeByte(v);
        if (tnrL != -1)
            bos.writeByte(naiNationalSn);
        if (sixDigits != null && sixDigits.length > 0)
            bos.writeBytes(sixDigits);

        return bos;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.restcomm.isup.messages.parameters.ParameterHarness#getTestedComponent ()
     */

    public AbstractISUPParameter getTestedComponent() {
        return new TerminatingNetworkRoutingNumberImpl("10", 1, 1);
    }

}
