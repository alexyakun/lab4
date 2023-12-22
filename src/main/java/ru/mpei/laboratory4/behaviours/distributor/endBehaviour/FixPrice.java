package ru.mpei.laboratory4.behaviours.distributor.endBehaviour;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.behaviours.distributor.SendContractProducer;

@Slf4j
public class FixPrice extends OneShotBehaviour {
    private AID consumerAID;
    private SendContractProducer sendContractProducer;

    public FixPrice(AID consumerAID, SendContractProducer sendContractProducer) {

        this.consumerAID = consumerAID;
        this.sendContractProducer = sendContractProducer;
    }

    @Override
    public void action() {
        log.info("ЗАПРОС У ПОТРЕБИТЕЛЯ НОВОЙ ЦЕНЫ");
        ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
        msg.setContent("FIX"+","+sendContractProducer.getProdPrice());
        msg.setProtocol("Energy");
        msg.addReceiver(consumerAID);
        myAgent.send(msg);
    }

}
