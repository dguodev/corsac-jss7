package org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive;

import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNEnumerated;

public class ASNCallCompletionTreatmentIndicatorImpl extends ASNEnumerated {
	public void setType(CallCompletionTreatmentIndicator t) {
		super.setValue(Long.valueOf(t.getCode()));
	}
	
	public CallCompletionTreatmentIndicator getType() {
		Long realValue=super.getValue();
		if(realValue==null)
			return null;
		
		return CallCompletionTreatmentIndicator.getInstance(getValue().intValue());
	}
}
