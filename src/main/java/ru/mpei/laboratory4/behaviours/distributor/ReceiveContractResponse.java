package ru.mpei.laboratory4.behaviours.distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiveContractResponse extends Behaviour {
    ACLMessage contract = null;
    @Override
    public void action() {
        contract = myAgent.receive(MessageTemplate.MatchProtocol("Contract"));

        if (contract != null) {
            log.info("Получен запрос на повторный аукцион");

        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return contract!=null;
    }
}
