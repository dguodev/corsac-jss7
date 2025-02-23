package org.restcomm.protocols.ss7.tcap.asn.comp;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNPostprocess;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

@ASNTag(asnClass=ASNClass.APPLICATION,tag=0x09,constructed=false,lengthIndefinite=false)
@ASNPostprocess
public class DestinationTransactionID extends ASNOctetString {
}