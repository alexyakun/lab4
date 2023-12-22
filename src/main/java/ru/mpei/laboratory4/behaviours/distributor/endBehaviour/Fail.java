package ru.mpei.laboratory4.behaviours.distributor.endBehaviour;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Fail extends OneShotBehaviour {
    private AID consumerAID;

    public Fail(AID consumerAID) {

        this.consumerAID = consumerAID;
    }

    @Override
    public void action() {
        log.info("FAIL");
        ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
        msg.setContent("Fail");
        msg.setProtocol("Energy");
        msg.addReceiver(consumerAID);
        myAgent.send(msg);
    }
}
