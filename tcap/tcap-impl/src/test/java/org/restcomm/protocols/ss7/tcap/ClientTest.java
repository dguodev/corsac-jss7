package org.restcomm.protocols.ss7.tcap;

import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.restcomm.protocols.ss7.indicator.RoutingIndicator;
import org.restcomm.protocols.ss7.sccp.impl.parameter.SccpAddressImpl;
import org.restcomm.protocols.ss7.sccp.parameter.SccpAddress;
import org.restcomm.protocols.ss7.tcap.api.TCAPException;
import org.restcomm.protocols.ss7.tcap.api.TCAPProvider;
import org.restcomm.protocols.ss7.tcap.api.TCAPSendException;
import org.restcomm.protocols.ss7.tcap.api.TCListener;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.Dialog;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginIndication;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCBeginRequest;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCContinueIndication;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCEndIndication;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCEndRequest;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCNoticeIndication;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCPAbortIndication;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCUniIndication;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TCUserAbortIndication;
import org.restcomm.protocols.ss7.tcap.api.tc.dialog.events.TerminationType;
import org.restcomm.protocols.ss7.tcap.asn.ApplicationContextName;
import org.restcomm.protocols.ss7.tcap.asn.TcapFactory;
import org.restcomm.protocols.ss7.tcap.asn.comp.Invoke;
import org.restcomm.protocols.ss7.tcap.asn.comp.OperationCode;

/**
 * Simple example demonstrates how to use TCAP Stack
 *
 * @author Amit Bhayani
 *
 */
public class ClientTest implements TCListener {
    // encoded Application Context Name
    public static final List<Long> _ACN_ = Arrays.asList(new Long[] { 0L, 4L, 0L, 0L, 1L, 0L, 19L, 2L });
    private TCAPProvider tcapProvider;
    private Dialog clientDialog;

    ClientTest() throws NamingException {

        InitialContext ctx = new InitialContext();
        try {
            String providerJndiName = "java:/restcomm/ss7/tcap";
            this.tcapProvider = ((TCAPProvider) ctx.lookup(providerJndiName));
        } finally {
            ctx.close();
        }

        this.tcapProvider.addTCListener(this);
    }

    public void sendInvoke() throws TCAPException, TCAPSendException {
        SccpAddress localAddress = new SccpAddressImpl(RoutingIndicator.ROUTING_BASED_ON_DPC_AND_SSN, null, 1, 8);
        SccpAddress remoteAddress = new SccpAddressImpl(RoutingIndicator.ROUTING_BASED_ON_DPC_AND_SSN, null, 2, 8);

        clientDialog = this.tcapProvider.getNewDialog(localAddress, remoteAddress);
        
        // create some INVOKE
        OperationCode oc = TcapFactory.createLocalOperationCode(12L);
        // no parameter
        this.clientDialog.sendData(null, null, null, null, oc, null, true, false);
        ApplicationContextName acn = this.tcapProvider.getDialogPrimitiveFactory().createApplicationContextName(_ACN_);
        // UI is optional!
        TCBeginRequest tcbr = this.tcapProvider.getDialogPrimitiveFactory().createBegin(this.clientDialog);
        tcbr.setApplicationContextName(acn);
        this.clientDialog.send(tcbr);
    }

    public void onDialogReleased(Dialog d) {
    }

    public void onInvokeTimeout(Invoke tcInvokeRequest) {
    }

    public void onDialogTimeout(Dialog d) {
        d.keepAlive();
    }

    public void onTCBegin(TCBeginIndication ind) {
    }

    public void onTCContinue(TCContinueIndication ind) {
        // send end
        TCEndRequest end = this.tcapProvider.getDialogPrimitiveFactory().createEnd(ind.getDialog());
        end.setTermination(TerminationType.Basic);
        try {
            ind.getDialog().send(end);
        } catch (TCAPSendException e) {
            throw new RuntimeException(e);
        }
    }

    public void onTCEnd(TCEndIndication ind) {
        // should not happen, in this scenario, we send data.
    }

    public void onTCUni(TCUniIndication ind) {
        // not going to happen
    }

    public void onTCPAbort(TCPAbortIndication ind) {
        // TODO Auto-generated method stub
    }

    public void onTCUserAbort(TCUserAbortIndication ind) {
        // TODO Auto-generated method stub
    }

    public void onTCNotice(TCNoticeIndication ind) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {

        try {
            ClientTest c = new ClientTest();
            c.sendInvoke();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TCAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TCAPSendException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
