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

package org.restcomm.protocols.ss7.inap.service.circuitSwitchedCall.primitives;

import java.util.ArrayList;
import java.util.List;

import org.restcomm.protocols.ss7.inap.api.service.circuitSwitchedCall.primitive.RouteList;

import com.mobius.software.telco.protocols.ss7.asn.ASNClass;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNProperty;
import com.mobius.software.telco.protocols.ss7.asn.annotations.ASNTag;
import com.mobius.software.telco.protocols.ss7.asn.primitives.ASNOctetString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author yulian.oifa
 *
 */
@ASNTag(asnClass=ASNClass.UNIVERSAL,tag=16,constructed=true,lengthIndefinite=false)
public class RouteListImpl implements RouteList {
	
	@ASNProperty(asnClass = ASNClass.UNIVERSAL,tag = 4,constructed = false,index = -1)
	private List<ASNOctetString> dataList;

    public RouteListImpl() {
    }

    public RouteListImpl(List<byte[]> dataList) {
    	if(dataList!=null) {
    		this.dataList=new ArrayList<ASNOctetString>();
    		for(byte[] curr:dataList) {
    			ASNOctetString currStr=new ASNOctetString();
    			currStr.setValue(Unpooled.wrappedBuffer(curr));
    			this.dataList.add(currStr);
    		}
    	}    	  	
    }

    public List<byte[]> getDataList() {
    	if(dataList==null)
    		return null;
    	
    	List<byte[]> result=new ArrayList<byte[]>();
    	for(ASNOctetString curr:dataList) {
    		ByteBuf value=curr.getValue();
    		if(value!=null) {
    			byte[] data=new byte[value.readableBytes()];
    			value.readBytes(data);
    			result.add(data);
    		}
    	}
    	return result;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("RouteList [");
        
        List<byte[]> items=getDataList();
        if (items != null && items.size()!=0) {
            sb.append("dataList=");
            boolean isFirst=false;
            for(byte[] curr:items) {
            	if(!isFirst)
            		sb.append(",");
            	
            	sb.append(ASNOctetString.printDataArr(curr));
            	isFirst=false;
            }         
        }
        
        sb.append("]");

        return sb.toString();
    }
}