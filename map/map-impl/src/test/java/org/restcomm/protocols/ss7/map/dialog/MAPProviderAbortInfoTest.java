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

package org.restcomm.protocols.ss7.map.dialog;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerTest;
import org.restcomm.protocols.ss7.map.api.dialog.MAPProviderAbortReason;
import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author amit bhayani
 *
 */
public class MAPProviderAbortInfoTest {

	private byte[] getDataFull() {
		return new byte[] { -91, 44, 10, 1, 1, 48, 39, -96, 32, 48, 10, 6, 3, 42, 3, 4, 11, 12, 13, 14, 15, 48, 5, 6, 3,
				42, 3, 6, 48, 11, 6, 3, 42, 3, 5, 21, 22, 23, 24, 25, 26, -95, 3, 31, 32, 33 };
	}

	@Test(groups = { "functional.decode", "dialog" })
	public void testDecode() throws Exception {
		ASNParser parser = new ASNParser();
		parser.loadClass(MAPProviderAbortInfoImpl.class);

		// The raw data is from last packet of long ussd-abort from msc2.txt
		byte[] data = new byte[] { (byte) 0xA5, 0x03, (byte) 0x0A, 0x01, 0x00 };

		ASNDecodeResult result = parser.decode(Unpooled.wrappedBuffer(data));
		assertFalse(result.getHadErrors());
		assertTrue(result.getResult() instanceof MAPProviderAbortInfoImpl);
		MAPProviderAbortInfoImpl mapProviderAbortInfo = (MAPProviderAbortInfoImpl) result.getResult();

		MAPProviderAbortReason reason = mapProviderAbortInfo.getMAPProviderAbortReason();
		assertNotNull(reason);
		assertEquals(reason, MAPProviderAbortReason.abnormalDialogue);

		data = this.getDataFull();
		result = parser.decode(Unpooled.wrappedBuffer(data));
		assertFalse(result.getHadErrors());
		assertTrue(result.getResult() instanceof MAPProviderAbortInfoImpl);
		mapProviderAbortInfo = (MAPProviderAbortInfoImpl) result.getResult();

		reason = mapProviderAbortInfo.getMAPProviderAbortReason();

		assertNotNull(reason);
		assertEquals(reason, MAPProviderAbortReason.invalidPDU);
		assertTrue(MAPExtensionContainerTest.CheckTestExtensionContainer(mapProviderAbortInfo.getExtensionContainer()));

	}

	@Test(groups = { "functional.encode", "dialog" })
	public void testEncode() throws Exception {
		ASNParser parser = new ASNParser();
		parser.loadClass(MAPProviderAbortInfoImpl.class);

		MAPProviderAbortInfoImpl mapProviderAbortInfo = new MAPProviderAbortInfoImpl();
		mapProviderAbortInfo.setMAPProviderAbortReason(MAPProviderAbortReason.invalidPDU);

		ByteBuf buffer = parser.encode(mapProviderAbortInfo);
		byte[] data = new byte[buffer.readableBytes()];
		buffer.readBytes(data);

		assertTrue(Arrays.equals(new byte[] { (byte) 0xA5, 0x03, (byte) 0x0A, 0x01, 0x01 }, data));

		mapProviderAbortInfo = new MAPProviderAbortInfoImpl();
		mapProviderAbortInfo.setMAPProviderAbortReason(MAPProviderAbortReason.invalidPDU);
		mapProviderAbortInfo.setExtensionContainer(MAPExtensionContainerTest.GetTestExtensionContainer());

		buffer = parser.encode(mapProviderAbortInfo);
		data = new byte[buffer.readableBytes()];
		buffer.readBytes(data);
		assertTrue(Arrays.equals(this.getDataFull(), data));
	}
}