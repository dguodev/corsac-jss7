package org.restcomm.protocols.ss7.commonapp.circuitSwitchedCall;

import org.restcomm.protocols.ss7.commonapp.api.circuitSwitchedCall.ConferenceTreatmentIndicator;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNEnumerated;

public class ASNConferenceTreatmentIndicatorImpl extends ASNEnumerated {
	public void setType(ConferenceTreatmentIndicator t) {
		super.setValue(Long.valueOf(t.getCode()));
	}
	
	public ConferenceTreatmentIndicator getType() {
		Long realValue=super.getValue();
		if(realValue==null)
			return null;
		
		return ConferenceTreatmentIndicator.getInstance(getValue().intValue());
	}
}
