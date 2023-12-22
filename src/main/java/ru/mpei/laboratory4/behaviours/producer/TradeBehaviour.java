package ru.mpei.laboratory4.behaviours.producer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeBehaviour extends Behaviour {
    private final AID topic;
    private final double minPrice;
    private double currentPrice;

    private boolean finishAuction = false;
    public TradeBehaviour(AID topic, double minPrice) {
        this.minPrice = minPrice;
        this.topic = topic;
    }

    @Override
    public void onStart() {

        sendBet(minPrice*2);
    }


    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topic));
        if (msg != null ){
            if(!msg.getSender().equals(myAgent.getAID())){
                double otherBet = Double.parseDouble(msg.getContent());
                if (otherBet <= currentPrice) {
                    if ((otherBet - minPrice) < 0.01) {
                        log.info("{} PASS MIN price. Quit the auction. in {}",myAgent.getLocalName(), topic.getLocalName());
                        finishAuction = true;

                    } else {
                        double myNewBet = minPrice + (otherBet - minPrice) * Math.random();

                        log.info("{} BET {} in {}",myAgent.getLocalName(),myNewBet,topic.getLocalName());
                        currentPrice = myNewBet;
                        sendBet(myNewBet);

                    }
                }
//                else {
//                    log.info("Received bet {} from {} but the msg is ignored cause my prev bet {} is less", otherBet, msg.getSender().getLocalName(), currentPrice);
//                }
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return finishAuction;
    }

    private void sendBet(double price) {
        ACLMessage firstMsg = new ACLMessage(ACLMessage.INFORM);
        firstMsg.setContent(price+"");
        currentPrice = price;
        firstMsg.addReceiver(topic);
        myAgent.send(firstMsg);
    }

}