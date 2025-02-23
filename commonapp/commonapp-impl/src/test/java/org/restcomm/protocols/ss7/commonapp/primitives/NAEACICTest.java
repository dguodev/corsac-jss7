package org.restcomm.protocols.ss7.commonapp.primitives;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.restcomm.protocols.ss7.commonapp.api.primitives.NetworkIdentificationPlanValue;
import org.restcomm.protocols.ss7.commonapp.api.primitives.NetworkIdentificationTypeValue;
import org.testng.annotations.Test;

import com.mobius.software.telco.protocols.ss7.asn.ASNDecodeResult;
import com.mobius.software.telco.protocols.ss7.asn.ASNParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NAEACICTest {

    public byte[] getData() {
        return new byte[] { 4, 3, 34, 33, 67 };
    };

    public byte[] getData2() {
        return new byte[] { 4, 3, 33, 33, 3 };
    };

    @Test(groups = { "functional.decode", "primitives" })
    public void testDecode() throws Exception {
    	ASNParser parser=new ASNParser(false);
    	parser.replaceClass(NAEACICImpl.class);
    	
    	byte[] data = this.getData();
    	ASNDecodeResult result=parser.decode(Unpooled.wrappedBuffer(data));
        
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof NAEACICImpl);
        NAEACICImpl prim = (NAEACICImpl)result.getResult();
        
        assertTrue(prim.getCarrierCode().equals("1234"));
        assertEquals(prim.getNetworkIdentificationPlanValue(), NetworkIdentificationPlanValue.fourDigitCarrierIdentification);
        assertEquals(prim.getNetworkIdentificationTypeValue(), NetworkIdentificationTypeValue.nationalNetworkIdentification);

        data = this.getData2();
        result=parser.decode(Unpooled.wrappedBuffer(data));
        
        assertFalse(result.getHadErrors());
        assertTrue(result.getResult() instanceof NAEACICImpl);
        prim = (NAEACICImpl)result.getResult();
        
        assertTrue(prim.getCarrierCode().equals("123"));
        assertEquals(prim.getNetworkIdentificationPlanValue(), NetworkIdentificationPlanValue.threeDigitCarrierIdentification);
        assertEquals(prim.getNetworkIdentificationTypeValue(), NetworkIdentificationTypeValue.nationalNetworkIdentification);
    }

    @Test(groups = { "functional.encode", "primitives" })
    public void testEncode() throws Exception {
    	ASNParser parser=new ASNParser(false);
    	parser.replaceClass(NAEACICImpl.class);
    	
    	// option 1
        NAEACICImpl prim = new NAEACICImpl("1234", NetworkIdentificationPlanValue.fourDigitCarrierIdentification, NetworkIdentificationTypeValue.nationalNetworkIdentification);
        ByteBuf buffer=parser.encode(prim);
        byte[] encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        
        assertTrue(Arrays.equals(encodedData, this.getData()));

        // option 2
        prim = new NAEACICImpl("123", NetworkIdentificationPlanValue.threeDigitCarrierIdentification, NetworkIdentificationTypeValue.nationalNetworkIdentification);
        buffer=parser.encode(prim);
        encodedData = new byte[buffer.readableBytes()];
        buffer.readBytes(encodedData);
        
        assertTrue(Arrays.equals(encodedData, this.getData2()));
    }
}