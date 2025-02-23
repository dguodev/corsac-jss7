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

package org.restcomm.protocols.ss7.cap;

import org.restcomm.protocols.ss7.cap.api.CAPApplicationContext;
import org.restcomm.protocols.ss7.cap.api.CAPDialog;
import org.restcomm.protocols.ss7.cap.api.CAPException;
import org.restcomm.protocols.ss7.cap.api.CAPMessage;
import org.restcomm.protocols.ss7.cap.api.CAPServiceBase;
import org.restcomm.protocols.ss7.cap.api.dialog.CAPDialogState;
import org.restcomm.protocols.ss7.cap.api.dialog.CAPGeneralAbortReason;
import org.restcomm.protocols.ss7.cap.api.dialog.CAPGprsReferenceNumber;
import org.restcomm.protocols.ss7.cap.api.dialog.CAPUserAbortReason;
import org.restcomm.protocols.ss7.cap.api.errors.CAPErrorMessage;
import org.restcomm.protocols.ss7.cap.api.errors.CAPErrorMessageParameterless;
import org.restcomm.protocols.ss7.sccp.parameter.SccpAddress;
import org.restcomm.protocols.ss7.tcap.api.MessageType;
import org.restcomm.protocols.ss7.tcap.api.TCAPException;
import org.restcomm.protocols.ss7.tcap.api.TCAPSendException;
import org.restcomm.protocols.ss7.tcap.api.tc.component.InvokeClass;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.Dialog;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCContinueRequest;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCEndRequest;
import org.restcomm.protocols.ss7.tcap.asn.ApplicationContextName;
import org.restcomm.protocols.ss7.tcap.asn.TcapFactory;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

/**
 *
 * @author sergey vetyutnev
 */
public abstract class CAPDialogImpl implements CAPDialog {
	private static final long serialVersionUID = 1L;

	protected Dialog tcapDialog = null;
    protected CAPProviderImpl capProviderImpl = null;
    protected CAPServiceBase capService = null;

    // Application Context of this Dialog
    protected CAPApplicationContext appCntx;

    protected CAPGprsReferenceNumber gprsReferenceNumber = null;
    protected CAPGprsReferenceNumber receivedGprsReferenceNumber;

    protected CAPDialogState state = CAPDialogState.Idle;

    // protected boolean normalDialogShutDown = false;

    // private Set<Long> incomingInvokeList = new HashSet<Long>();

    boolean returnMessageOnError = false;
    protected MessageType tcapMessageType;
    protected DelayedAreaState delayedAreaState;
    private CAPStackConfigurationManagement capStackConfigurationManagement;

    protected CAPDialogImpl(CAPApplicationContext appCntx, Dialog tcapDialog, CAPProviderImpl capProviderImpl,
            CAPServiceBase capService) {
        this.appCntx = appCntx;
        this.tcapDialog = tcapDialog;
        this.capProviderImpl = capProviderImpl;
        this.capService = capService;
        this.capStackConfigurationManagement = new CAPStackConfigurationManagement();
    }

    public SccpAddress getLocalAddress() {
        return this.tcapDialog.getLocalAddress();
    }

    public void setLocalAddress(SccpAddress localAddress) {
        this.tcapDialog.setLocalAddress(localAddress);
    }

    public SccpAddress getRemoteAddress() {
        return this.tcapDialog.getRemoteAddress();
    }

    public void setRemoteAddress(SccpAddress remoteAddress) {
        this.tcapDialog.setRemoteAddress(remoteAddress);
    }

    @Override
    public void setReturnMessageOnError(boolean val) {
        returnMessageOnError = val;
    }

    @Override
    public boolean getReturnMessageOnError() {
        return returnMessageOnError;
    }

    public MessageType getTCAPMessageType() {
        return tcapMessageType;
    }

    public int getNetworkId() {
        return this.tcapDialog.getNetworkId();
    }

    public void setNetworkId(int networkId) {
        this.tcapDialog.setNetworkId(networkId);
    }

    @Override
    public void keepAlive() {
        this.tcapDialog.keepAlive();
    }

    public Long getLocalDialogId() {
        return tcapDialog.getLocalDialogId();
    }

    public Long getRemoteDialogId() {
        return tcapDialog.getRemoteDialogId();
    }

    public CAPServiceBase getService() {
        return this.capService;
    }

    public Dialog getTcapDialog() {
        return tcapDialog;
    }

    public void release() {
        // this.setNormalDialogShutDown();
        this.setState(CAPDialogState.Expunged);

        if (this.tcapDialog != null)
            this.tcapDialog.release();
    }

    /**
     * Setting that the CAP Dialog is normally shutting down - to prevent performing onDialogReleased()
     */
    // protected void setNormalDialogShutDown() {
    // this.normalDialogShutDown = true;
    // }
    //
    // protected Boolean getNormalDialogShutDown() {
    // return this.normalDialogShutDown;
    // }

    /**
     * Adding the new incoming invokeId into incomingInvokeList list
     *
     * @param invokeId
     * @return false: failure - this invokeId already present in the list
     */
    // public boolean addIncomingInvokeId(Long invokeId) {
    // if (this.incomingInvokeList.contains(invokeId))
    // return false;
    // else {
    // this.incomingInvokeList.add(invokeId);
    // return true;
    // }
    // }
    //
    // public void removeIncomingInvokeId(Long invokeId) {
    // this.incomingInvokeList.remove(invokeId);
    // }
    //
    // public Boolean checkIncomingInvokeIdExists(Long invokeId) {
    // return this.incomingInvokeList.contains(invokeId);
    // }

    public CAPDialogState getState() {
        return state;
    }

    protected void setState(CAPDialogState newState) {
        if (this.state == CAPDialogState.Expunged) {
            return;
        }

        this.state = newState;
    }

    public void setGprsReferenceNumber(CAPGprsReferenceNumber gprsReferenceNumber) {
        this.gprsReferenceNumber = gprsReferenceNumber;
    }

    public CAPGprsReferenceNumber getGprsReferenceNumber() {
        return this.gprsReferenceNumber;
    }

    public CAPGprsReferenceNumber getReceivedGprsReferenceNumber() {
        return receivedGprsReferenceNumber;
    }

    public void send() throws CAPException {

        switch (this.tcapDialog.getState()) {
	        case Idle:
	        	ApplicationContextName acn = this.capProviderImpl.getTCAPProvider().getDialogPrimitiveFactory()
	                    .createApplicationContextName(this.appCntx.getOID());
	
	            this.setState(CAPDialogState.InitialSent);
	
	            this.capProviderImpl.fireTCBegin(this.getTcapDialog(), acn, this.gprsReferenceNumber,
	                    this.getReturnMessageOnError());
	            this.gprsReferenceNumber = null;
	            break;
	
	        case Active:
	            // Its Active send TC-CONTINUE
	
	            this.capProviderImpl.fireTCContinue(this.getTcapDialog(), null, null, this.getReturnMessageOnError());
	            break;
	
	        case InitialReceived:
	            // Its first Reply to TC-Begin
	
	            ApplicationContextName acn1 = this.capProviderImpl.getTCAPProvider().getDialogPrimitiveFactory()
	                    .createApplicationContextName(this.appCntx.getOID());
	
	            this.capProviderImpl.fireTCContinue(this.getTcapDialog(), acn1, this.gprsReferenceNumber,
	                    this.getReturnMessageOnError());
	            this.gprsReferenceNumber = null;
	
	            this.setState(CAPDialogState.Active);
	            break;
	
	        case InitialSent: // we have sent TC-BEGIN already, need to wait
	            throw new CAPException("Awaiting TC-BEGIN response, can not send another dialog initiating primitive!");
	        case Expunged: // dialog has been terminated on TC level, cant send
	            throw new CAPException("Dialog has been terminated, can not send primitives!");
	    }
    }

    public void sendDelayed() throws CAPException {

        if (this.delayedAreaState == null) {
            this.send();
        } else {
            switch (this.delayedAreaState) {
                case No:
                    this.delayedAreaState = CAPDialogImpl.DelayedAreaState.Continue;
                    break;
				default:
					break;
            }
        }
    }

    public void close(boolean prearrangedEnd) throws CAPException {

        switch (this.tcapDialog.getState()) {
	        case InitialReceived:
	            ApplicationContextName acn = this.capProviderImpl.getTCAPProvider().getDialogPrimitiveFactory()
	                    .createApplicationContextName(this.appCntx.getOID());
	
	            if (prearrangedEnd) {
	                // we do not send any data in a prearrangedEnd case
	                if (this.tcapDialog != null)
	                    this.tcapDialog.release();
	            } else {
	                this.capProviderImpl.fireTCEnd(this.getTcapDialog(), prearrangedEnd, acn, this.gprsReferenceNumber,
	                        this.getReturnMessageOnError());
	                this.gprsReferenceNumber = null;
	            }
	
	            this.setState(CAPDialogState.Expunged);
	            break;
	
	        case Active:
	            if (prearrangedEnd) {
	                // we do not send any data in a prearrangedEnd case
	                if (this.tcapDialog != null)
	                    this.tcapDialog.release();
	            } else {
	                this.capProviderImpl.fireTCEnd(this.getTcapDialog(), prearrangedEnd, null, null,
	                        this.getReturnMessageOnError());
	            }
	
	            this.setState(CAPDialogState.Expunged);
	            break;
	
	        case Idle:
	            throw new CAPException("Awaiting TC-BEGIN to be sent, can not send another dialog initiating primitive!");
	        case InitialSent: // we have sent TC-BEGIN already, need to wait
	            if (prearrangedEnd) {
	                // we do not send any data in a prearrangedEnd case
	                if (this.tcapDialog != null)
	                    this.tcapDialog.release();
	                this.setState(CAPDialogState.Expunged);
	                return;
	            } else {
	                throw new CAPException("Awaiting TC-BEGIN response, can not send another dialog initiating primitive!");
	            }
	        case Expunged: // dialog has been terminated on TC level, cant send
	            throw new CAPException("Dialog has been terminated, can not send primitives!");
	    }
    }

    public void closeDelayed(boolean prearrangedEnd) throws CAPException {

        if (this.delayedAreaState == null) {
            this.close(prearrangedEnd);
        } else {
            if (prearrangedEnd) {
                switch (this.delayedAreaState) {
                    case No:
                    case Continue:
                    case End:
                        this.delayedAreaState = CAPDialogImpl.DelayedAreaState.PrearrangedEnd;
                        break;
					default:
						break;
                }
            } else {
                switch (this.delayedAreaState) {
                    case No:
                    case Continue:
                        this.delayedAreaState = CAPDialogImpl.DelayedAreaState.End;
                        break;
					default:
						break;
                }
            }
        }
    }

    @Override
    public void abort(CAPUserAbortReason abortReason) throws CAPException {

        // Dialog is not started or has expunged - we need not send
        // TC-U-ABORT,
        // only Dialog removing
        if (this.getState() == CAPDialogState.Expunged || this.getState() == CAPDialogState.Idle) {
            this.setState(CAPDialogState.Expunged);
            return;
        }

        // this.setNormalDialogShutDown();
        this.capProviderImpl.fireTCAbort(this.getTcapDialog(), CAPGeneralAbortReason.UserSpecific, abortReason,
                this.getReturnMessageOnError());

        this.setState(CAPDialogState.Expunged);
    }

    @Override
    public void processInvokeWithoutAnswer(Long invokeId) {
        this.tcapDialog.processInvokeWithoutAnswer(invokeId);
    }

    @Override
    public Long sendDataComponent(Long invokeId,Long linkedId,InvokeClass invokeClass,Long customTimeout,Long operationCode,CAPMessage param,Boolean isRequest,Boolean isLastResponse) throws CAPException {
        try {
        	if(operationCode!=null)
        		return this.tcapDialog.sendData(invokeId, linkedId, invokeClass, customTimeout, TcapFactory.createLocalOperationCode(operationCode), param, isRequest, isLastResponse);
        	else
        		return this.tcapDialog.sendData(invokeId, linkedId, invokeClass, customTimeout, null, param, isRequest, isLastResponse);
        } catch (TCAPSendException | TCAPException e) {
            throw new CAPException(e.getMessage(), e);
        }
    }

    @Override
    public void sendErrorComponent(Long invokeId, CAPErrorMessage mem) throws CAPException {
    	try {
        	if(mem instanceof CAPErrorMessageParameterless)
        		this.tcapDialog.sendError(invokeId, TcapFactory.createLocalErrorCode(mem.getErrorCode()), null);
        	else
        		this.tcapDialog.sendError(invokeId, TcapFactory.createLocalErrorCode(mem.getErrorCode()), mem);

        } catch (TCAPSendException e) {
            throw new CAPException(e.getMessage(), e);
        }
    }

    public void sendRejectComponent(Long invokeId, Problem problem) throws CAPException {
        try {
            this.tcapDialog.sendReject(invokeId, problem);

        } catch (TCAPSendException e) {
            throw new CAPException(e.getMessage(), e);
        }
    }

    @Override
    public void resetInvokeTimer(Long invokeId) throws CAPException {

        try {
            this.getTcapDialog().resetTimer(invokeId);
        } catch (TCAPException e) {
            throw new CAPException("TCAPException occure: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean cancelInvocation(Long invokeId) throws CAPException {
        try {
            return this.getTcapDialog().cancelInvocation(invokeId);
        } catch (TCAPException e) {
            throw new CAPException("TCAPException occure: " + e.getMessage(), e);
        }
    }

    public Object getUserObject() {
        return this.tcapDialog.getUserObject();
    }

    public void setUserObject(Object userObject) {
        this.tcapDialog.setUserObject(userObject);
    }

    @Override
    public CAPApplicationContext getApplicationContext() {
        return appCntx;
    }

    @Override
    public int getMaxUserDataLength() {
        return this.getTcapDialog().getMaxUserDataLength();
    }

    @Override
    public int getMessageUserDataLengthOnSend() throws CAPException {

        try {
            switch (this.tcapDialog.getState()) {
                case Idle:
                    ApplicationContextName acn = this.capProviderImpl.getTCAPProvider().getDialogPrimitiveFactory()
                            .createApplicationContextName(this.appCntx.getOID());

                    TCBeginRequest tb = this.capProviderImpl.encodeTCBegin(this.getTcapDialog(), acn, this.gprsReferenceNumber);
                    return tcapDialog.getDataLength(tb);

                case Active:
                    // Its Active send TC-CONTINUE

                    TCContinueRequest tc = this.capProviderImpl.encodeTCContinue(this.getTcapDialog(), null, null);
                    return tcapDialog.getDataLength(tc);

                case InitialReceived:
                    // Its first Reply to TC-Begin

                    ApplicationContextName acn1 = this.capProviderImpl.getTCAPProvider().getDialogPrimitiveFactory()
                            .createApplicationContextName(this.appCntx.getOID());

                    tc = this.capProviderImpl.encodeTCContinue(this.getTcapDialog(), acn1, this.gprsReferenceNumber);
                    return tcapDialog.getDataLength(tc);
				default:
					break;
            }
        } catch (TCAPSendException e) {
            throw new CAPException("TCAPSendException when getMessageUserDataLengthOnSend", e);
        }

        throw new CAPException("Bad TCAP Dialog state: " + this.tcapDialog.getState());
    }

    @Override
    public int getMessageUserDataLengthOnClose(boolean prearrangedEnd) throws CAPException {
        if (prearrangedEnd)
            // we do not send any data in prearrangedEnd dialog termination
            return 0;

        try {
            switch (this.tcapDialog.getState()) {
                case InitialReceived:
                    ApplicationContextName acn = this.capProviderImpl.getTCAPProvider().getDialogPrimitiveFactory()
                            .createApplicationContextName(this.appCntx.getOID());

                    TCEndRequest te = this.capProviderImpl.encodeTCEnd(this.getTcapDialog(), prearrangedEnd, acn,
                            this.gprsReferenceNumber);
                    return tcapDialog.getDataLength(te);

                case Active:
                    te = this.capProviderImpl.encodeTCEnd(this.getTcapDialog(), prearrangedEnd, null, null);
                    return tcapDialog.getDataLength(te);
				default:
					break;
            }
        } catch (TCAPSendException e) {
            throw new CAPException("TCAPSendException when getMessageUserDataLengthOnSend", e);
        }

        throw new CAPException("Bad TCAP Dialog state: " + this.tcapDialog.getState());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("CAPDialog: LocalDialogId=").append(this.getLocalDialogId()).append(" RemoteDialogId=")
                .append(this.getRemoteDialogId()).append(" CAPDialogState=").append(this.getState())
                .append(" CAPApplicationContext=").append(this.appCntx).append(" TCAPDialogState=")
                .append(this.tcapDialog.getState());
        return sb.toString();
    }

    protected enum DelayedAreaState {
        No, Continue, End, PrearrangedEnd;
    }

    public long getIdleTaskTimeout() {
        return tcapDialog.getIdleTaskTimeout();
    }

    public void setIdleTaskTimeout(long idleTaskTimeoutMs) {
        tcapDialog.setIdleTaskTimeout(idleTaskTimeoutMs);
    }

    @Override
    public int getTimerCircuitSwitchedCallControlShort() {
        return capStackConfigurationManagement.getTimerCircuitSwitchedCallControlShort();
    }

    @Override
    public int getTimerCircuitSwitchedCallControlMedium() {
        return capStackConfigurationManagement.getTimerCircuitSwitchedCallControlMedium();
    }

    @Override
    public int getTimerCircuitSwitchedCallControlLong() {
        return capStackConfigurationManagement.getTimerCircuitSwitchedCallControlLong();
    }

    @Override
    public int getTimerSmsShort() {
        return capStackConfigurationManagement.getTimerSmsShort();
    }

    @Override
    public int getTimerGprsShort() {
        return capStackConfigurationManagement.getTimerGprsShort();
    }
}
