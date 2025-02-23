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
package org.restcomm.protocols.ss7.map.service.callhandling;

import org.restcomm.protocols.ss7.commonapp.api.primitives.MAPExtensionContainer;
import org.restcomm.protocols.ss7.commonapp.primitives.MAPExtensionContainerImpl;
import org.restcomm.protocols.ss7.map.api.service.callhandling.CCBSIndicators;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNNull;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,lengthIndefinite=false)
public class CCBSIndicatorsImpl implements CCBSIndicators {
	@ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=0,constructed=false,index=-1)
    private ASNNull ccbsPossible;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=1,constructed=false,index=-1)
    private ASNNull keepCCBSCallIndicator;
    
    @ASNProperty(asnClass=ASNClass.CONTEXT_SPECIFIC,tag=2,constructed=true,index=-1, defaultImplementation = MAPExtensionContainerImpl.class)
    private MAPExtensionContainer mapExtensionContainer;

    public CCBSIndicatorsImpl() {        
    }

    public CCBSIndicatorsImpl(boolean ccbsPossible, boolean keepCCBSCallIndicator, MAPExtensionContainer mapExtensionContainer) {
        if(ccbsPossible)
        	this.ccbsPossible = new ASNNull();
        
        if(keepCCBSCallIndicator)
        	this.keepCCBSCallIndicator = new ASNNull();
        
        this.mapExtensionContainer = mapExtensionContainer;
    }

    public boolean getCCBSPossible() {
        return this.ccbsPossible!=null;
    }

    public boolean getKeepCCBSCallIndicator() {
        return this.keepCCBSCallIndicator!=null;
    }

    public MAPExtensionContainer getMAPExtensionContainer() {
        return this.mapExtensionContainer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CCBSIndicators [");

        if (this.ccbsPossible!=null) {
            sb.append("ccbsPossible, ");
        }

        if (this.keepCCBSCallIndicator!=null) {
            sb.append("keepCCBSCallIndicator, ");
        }

        if (this.mapExtensionContainer != null) {
            sb.append("mapExtensionContainer=");
            sb.append(this.mapExtensionContainer.toString());
            sb.append(" ");
        }

        sb.append("]");

        return sb.toString();
    }

}
