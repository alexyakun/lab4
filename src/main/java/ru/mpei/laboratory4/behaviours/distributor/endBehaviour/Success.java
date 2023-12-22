package ru.mpei.laboratory4.behaviours.distributor.endBehaviour;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.behaviours.distributor.SendContractProducer;

@Slf4j
public class Success extends OneShotBehaviour {
    private AID consumerAID;
    private double needPower;
    private SendContractProducer sendContractProducer;

    public Success(SendContractProducer sendContractProducer, AID consumerAID, double needPower) {
        this.sendContractProducer = sendContractProducer;
        this.consumerAID = consumerAID;
        this.needPower = needPower;
    }
    @Override
    public void action() {
        double price = sendContractProducer.getProdPrice();
        log.info("Контракт успешно заключен");
        ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
        msg.setContent(needPower+","+price);
        msg.setProtocol("Energy");
        msg.addReceiver(consumerAID);
        myAgent.send(msg);
    }
}
