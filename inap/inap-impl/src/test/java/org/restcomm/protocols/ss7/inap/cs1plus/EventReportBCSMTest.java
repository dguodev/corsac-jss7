package org.restcomm.protocols.ss7.inap.cs1plus;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.restcomm.protocols.ss7.commonapp.api.isup.CauseIsup;
import org.restcomm.protocols.ss7.commonapp.api.primitives.EventTypeBCSM;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LegID;
import org.restcomm.protocols.ss7.commonapp.api.primitives.LegType;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MiscCallInfo;
import org.restcomm.protocols.ss7.commonapp.api.primitives.MiscCallInfoMessageType;
import org.restcomm.protocols.ss7.commonapp.isup.CauseIsupImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.LegIDImpl;
import org.restcomm.protocols.ss7.commonapp.primitives.MiscCallInfoImpl;
import org.restcomm.protocols.ss7.inap.EsiBcsm.DisconnectSpecificInfoImpl;
import org.restcomm.protocols.ss7.inap.api.EsiBcsm.DisconnectSpecificInfo;
import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive.EventSpecificInformationBCSM;
import org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.EventReportBCSMRequestImpl;
import org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.primitives.EventSpecificInformationBCSMImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class EventReportBCSMTest {
	protected final transient Logger logger=Logger.getLogger(ApplyChargingReportTest.class);

	private byte[] message1=new byte[] {  0x30,0x15,(byte)0x80,0x01,0x09,(byte)0xa2,0x06,(byte)0xa7,
			0x04,(byte)0x80,0x02,(byte)0x80,(byte)0x90,(byte)0xa3,0x03,(byte)0x81,0x01,0x01,(byte)0xa4,0x03,(byte)0x80,0x01,0x00 };
	 
	private byte[] causeInd1=new byte[] {(byte)-128, (byte)-112};
	
	@BeforeClass
	public static void initTests()
	{
		BasicConfigurator.configure();
	}
	
	@Test(groups = { "functional.decode", "circuitSwitchedCall" })
	public void testDecode() throws Exception {
		ASNParser parser=new ASNParser(true);
		parser.replaceClass(EventReportBCSMRequestImpl.class);
	    	
		byte[] rawData = this.message1;
		ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(rawData));

		assertFalse(result.getHadErrors());
		assertTrue(result.getResult() instanceof EventReportBCSMRequestImpl);
	        
		EventReportBCSMRequestImpl elem = (EventReportBCSMRequestImpl)result.getResult();
		assertEquals(elem.getEventTypeBCSM(),EventTypeBCSM.oDisconnect);
		assertNotNull(elem.getEventSpecificInformationBCSM());
		assertNotNull(elem.getEventSpecificInformationBCSM().getODisconnectSpecificInfo());
		assertNotNull(elem.getEventSpecificInformationBCSM().getODisconnectSpecificInfo().getReleaseCause());
		assertTrue(Arrays.equals(elem.getEventSpecificInformationBCSM().getODisconnectSpecificInfo().getReleaseCause().getData(),causeInd1));
		assertEquals(elem.getEventSpecificInformationBCSM().getODisconnectSpecificInfo().getReleaseCause().getCauseIndicators().getCauseValue(),16);
		assertEquals(elem.getEventSpecificInformationBCSM().getODisconnectSpecificInfo().getReleaseCause().getCauseIndicators().getCodingStandard(),0);
		assertEquals(elem.getEventSpecificInformationBCSM().getODisconnectSpecificInfo().getReleaseCause().getCauseIndicators().getLocation(),0);
		assertEquals(elem.getEventSpecificInformationBCSM().getODisconnectSpecificInfo().getReleaseCause().getCauseIndicators().getLocation(),0);
		assertNotNull(elem.getLegID());
		assertNotNull(elem.getLegID().getReceivingSideID());
		assertEquals(elem.getLegID().getReceivingSideID(),LegType.leg1);
		assertNotNull(elem.getMiscCallInfo());
		assertEquals(elem.getMiscCallInfo().getMessageType(),MiscCallInfoMessageType.request);
		assertNull(elem.getMiscCallInfo().getDpAssignment());		
		logger.info(elem);		
	}
	
	@Test(groups = { "functional.encode", "circuitSwitchedCall" })
	public void testEncode() throws Exception {
		ASNParser parser=new ASNParser(true);
		parser.replaceClass(EventReportBCSMRequestImpl.class);
	    	
		CauseIsup cause=new CauseIsupImpl(causeInd1);
		DisconnectSpecificInfo oDisconnectInfo=new DisconnectSpecificInfoImpl(cause,null);
		EventSpecificInformationBCSM eventInformation=new EventSpecificInformationBCSMImpl(oDisconnectInfo,false);
		LegID legID=new LegIDImpl(LegType.leg1, null);
		MiscCallInfo miscCallInfo=new MiscCallInfoImpl(MiscCallInfoMessageType.request, null);
		EventReportBCSMRequestImpl elem = new EventReportBCSMRequestImpl(EventTypeBCSM.oDisconnect,null,
				eventInformation,legID, miscCallInfo, null);
	    byte[] rawData = this.message1;
	    ByteBuf buffer=parser.encode(elem);
	    byte[] encodedData = new byte[buffer.readableBytes()];
	    buffer.readBytes(encodedData);
	    assertTrue(Arrays.equals(rawData, encodedData));
	}
}
