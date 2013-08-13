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

package org.mobicents.protocols.ss7.tcap.api;

import java.util.Map;
import java.util.UUID;

/**
 *
 * @author servey vetyutnev
 *
 */
public interface TCAPCounterProvider {

    /**
     * Return a unique sessionId.
     * After stack/counters restart this value will be changed,
     * all counters will be set to zero and all active campaigns will be removed
     */
    UUID getSessionId();

    /**
     * return a count of received since Stack restart TC-UNI messages
     */
    long getTcUniRecievedCount();

    /**
     * return a count of sent since Stack restart TC-UNI messages
     */
    long getTcUniSentCount();

    /**
     * return a count of received since Stack restart TC-BEGIN messages
     */
    long getTcBeginRecievedCount();

    /**
     * return a count of sent since Stack restart TC-BEGIN messages
     */
    long getTcBeginSentCount();

    /**
     * return a count of received since Stack restart TC-CONTINUE messages
     */
    long getTcContinueRecievedCount();

    /**
     * return a count of sent since Stack restart TC-CONTINUE messages
     */
    long getTcContinueSentCount();

    /**
     * return a count of received since Stack restart TC-END messages
     */
    long getTcEndRecievedCount();

    /**
     * return a count of sent since Stack restart TC-END messages
     */
    long getTcEndSentCount();

    /**
     * return a count of received since Stack restart TC-PROVIDER-ABORT messages
     */
    long getTcPAbortRecievedCount();

    /**
     * return a count of sent since Stack restart TC-PROVIDER-ABORT messages
     */
    long getTcPAbortSentCount();

    /**
     * return a count of received since Stack restart TC-USER-ABORT messages
     */
    long getTcUserAbortRecievedCount();

    /**
     * return a count of sent since Stack restart TC-USER-ABORT messages
     */
    long getTcUserAbortSentCount();

    /**
     * return a count of received since Stack restart Invoke components
     */
    long getInvokeRecievedCount();

    /**
     * return a count of sent since Stack restart Invoke components
     */
    long getInvokeSentCount();

    /**
     * return a count of received since Stack restart ReturtResult components
     */
    long getReturnResultRecievedCount();

    /**
     * return a count of sent since Stack restart ReturtResult components
     */
    long getReturnResultSentCount();

    /**
     * return a count of received since Stack restart ReturtResultLast components
     */
    long getReturnResultLastRecievedCount();

    /**
     * return a count of sent since Stack restart ReturtResultLast components
     */
    long getReturnResultLastSentCount();

    /**
     * return a count of received since Stack restart ReturnError components
     */
    long getReturnErrorRecievedCount();

    /**
     * return a count of sent since Stack restart ReturnError components
     */
    long getReturnErrorSentCount();

    /**
     * return a count of received since Stack restart Reject components
     */
    long getRejectRecievedCount();

    /**
     * return a count of sent since Stack restart Reject components
     */
    long getRejectSentCount();

    /**
     * return a count of received since Stack restart DialogRequests
     */
    long getDialogRequestCount();

    /**
     * return a count of received since Stack restart DialogTimeouts
     */
    long getDialogTimeoutCount();

    /**
     * return a count of received since Stack restart DialogReleases
     */
    long getDialogReleaseCount();


    /**
     * return a current count of established Dialogs
     */
    long getCurrentDialogsCount();

    /**
     * return a count of all established Dialogs since stack start
     */
    long getAllDialogsCount();

    /**
     * return a min count of established Dialogs
     */
    Long getMinDialogsCount(String compainName);

    /**
     * return a max current count of established Dialogs
     */
    Long getMaxDialogsCount(String compainName);

    /**
     * return a total durations of all released Dialogs since stack start (in seconds)
     * this value is updated when a dialog is released
     */
    double getAllDialogsDuration();


    /**
     * return an outgoing Dialogs count per ApplicationContextNames (in string form)
     * all MAP V1 operations will be assigned into empty string group ("")
     */
    Map<String,Long> getOutgoingDialogsPerApplicatioContextName();

    /**
     * return an incoming Dialogs count per ApplicationContextNames (in string form)
     */
    Map<String,Long> getIncomingDialogsPerApplicatioContextName();

    /**
     * return an outgoing Invokes count per OperationCodes
     */
    Map<String,Long> getOutgoingInvokesPerOperationCode();

    /**
     * return an incoming Invokes count per OperationCodes
     */
    Map<String,Long> getIncomingInvokesPerOperationCode();

    /**
     * return an outgoing ReturtError count per ErrorCodes
     */
    Map<String,Long> getOutgoingErrorsPerErrorCode();

    /**
     * return an incoming ReturtError count per ErrorCodes
     */
    Map<String,Long> getIncomingErrorsPerErrorCode();

    /**
     * return an outgoing Reject count per Problem
     */
    Map<String,Long> getOutgoingRejectPerProblem();

    /**
     * return an incoming Reject count per Problem
     */
    Map<String,Long> getIncomingRejectPerProblem();

}

