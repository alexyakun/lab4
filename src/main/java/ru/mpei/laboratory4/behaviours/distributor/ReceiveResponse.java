package ru.mpei.laboratory4.behaviours.distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ReceiveResponse extends Behaviour {
    private StartAuction startAuction;
    private Map<String, ACLMessage> agentBets = new HashMap<>();
    public ReceiveResponse(StartAuction startAuction){

        this.startAuction = startAuction;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(startAuction.getTopic()));
        if (msg != null){
            agentBets.put(msg.getSender().getLocalName(), msg);
        } else {
            block();
        }
    }
    @Override
    public boolean done() {
        return false;
    }
    public Map<String, ACLMessage> getBets(){
        return agentBets;
    }

}
