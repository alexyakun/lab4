package ru.mpei.laboratory4.behaviours.distributor;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendContractProducer extends OneShotBehaviour {
    private WaitForResponse waitForResponse;
    private StartAuction startAuction;
    private double maxPrice;

    private boolean contract;
    private double needPower;
    @Getter
    private double prodPrice;
    public SendContractProducer(WaitForResponse waitForResponse, double maxPrice, double needPower, StartAuction startAuction){
        this.waitForResponse = waitForResponse;
        this.maxPrice = maxPrice;
        this.needPower = needPower;
        this.startAuction = startAuction;
    }
    @Override
    public void action() {
        ACLMessage winner = waitForResponse.findWinner();
        prodPrice = Double.parseDouble(winner.getContent());
        if(maxPrice > prodPrice) {
            this.contract = true;
            ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
            msg.setContent(needPower+"");
            msg.setProtocol("Contract");
            msg.addReceiver(winner.getSender());
            myAgent.send(msg);
            log.info("Отправлен контракт {} с ценой {}",winner.getSender().getLocalName(),prodPrice);
        } else {
            this.contract = false;
            log.info("Контракт отклонен предлагаемая цена {} больше максимальной {}",prodPrice,maxPrice);
            ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
            msg.setContent("FAIL");
            msg.setProtocol("Contract");
            msg.addReceiver(winner.getSender());
            myAgent.send(msg);
        }
    }

    @Override
    public int onEnd() {
        if(this.contract){
            return 1;
        } else{
            return 0;
        }
    }
}
