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

package org.restcomm.protocols.ss7.tcapAnsi.asn;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNException;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

/**
 *
 * @author amit bhayani
 * @author sergey vetyutnev
 *
 */
@Test(groups = { "asn" })
public class UserInformationElementTest {

    private byte[] data = new byte[] { 40, 20, 40, 18, 6, 7, 4, 0, 0, 1, 1, 1, 1, -96, 7, 4, 5, 3, 4, 5, 6, 7 };

    private byte[] dataValue = new byte[] { 3, 4, 5, 6, 7 };

    @Test(groups = { "functional.decode" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser();
    	parser.loadClass(UserInformationElementImpl.class);
    	
    	ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(data));
    	assertTrue(result.getResult() instanceof UserInformationElementImpl);
    	UserInformationElementImpl userInformationElement = (UserInformationElementImpl)result.getResult();
        assertTrue(userInformationElement.isIDObjectIdentifier());
        assertEquals(Arrays.asList(new Long[] { 0L, 4L, 0L, 0L, 1L, 1L, 1L, 1L }), userInformationElement.getObjectIdentifier());

        assertFalse(userInformationElement.isIDIndirect());
        assertTrue(userInformationElement.isValueObject());
        assertTrue(UserInformationElementTest.byteBufEquals(Unpooled.wrappedBuffer(dataValue), ((ASNOctetString)userInformationElement.getChild()).getValue()));
    }

    @Test(groups = { "functional.encode" })
    public void testUserInformationEncode() throws ASNException {
    	ASNParser parser=new ASNParser();
    	parser.loadClass(UserInformationElementImpl.class);
    	
    	UserInformationElementImpl userInformationElement = new UserInformationElementImpl();

        userInformationElement.setIdentifier(Arrays.asList(new Long[] { 0L, 4L, 0L, 0L, 1L, 1L, 1L, 1L }));
                
        ASNOctetString octetString=new ASNOctetString();
        octetString.setValue(Unpooled.wrappedBuffer(dataValue));
        userInformationElement.setChildAsObject(octetString);        
        
        ByteBuf userInfData=parser.encode(userInformationElement);
        assertTrue(UserInformationElementTest.byteBufEquals(Unpooled.wrappedBuffer(data), userInfData));        
    }

	public static Boolean byteBufEquals(ByteBuf value1,ByteBuf value2) {
    	ByteBuf value1Wrapper=Unpooled.wrappedBuffer(value1);
    	ByteBuf value2Wrapper=Unpooled.wrappedBuffer(value2);
    	byte[] value1Arr=new byte[value1Wrapper.readableBytes()];
    	byte[] value2Arr=new byte[value2Wrapper.readableBytes()];
    	value1Wrapper.readBytes(value1Arr);
    	value2Wrapper.readBytes(value2Arr);
    	return Arrays.equals(value1Arr, value2Arr);
    } 
}
