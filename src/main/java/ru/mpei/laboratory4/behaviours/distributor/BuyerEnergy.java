package ru.mpei.laboratory4.behaviours.distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuyerEnergy extends Behaviour {
    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchProtocol("Energy"));
        if (msg != null) {
            log.info("ПОЛУЧИЛ ЗАПРОС");
            String[] split = msg.getContent().split(",");
            double needPower = Double.parseDouble(split[0]);
            double maxPrice = Double.parseDouble(split[1]);
            log.info("NEED POWER = {}, MAX PRICE = {}", needPower,maxPrice);
            myAgent.addBehaviour(new DistributorFSM(needPower,maxPrice,msg.getSender(), true));

        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
