package ru.mpei.laboratory4.behaviours.distributor;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class WaitForResponse extends ParallelBehaviour {
    private Behaviour wakeUpBehaviour;
    private ReceiveResponse receiveResponse;
    private AID topic;
    private StartAuction startAuction;

    public WaitForResponse(StartAuction startAuction){
        super(WHEN_ANY);
        this.startAuction = startAuction;

    }

    @Override
    public void onStart() {

        receiveResponse = new ReceiveResponse(startAuction);
        wakeUpBehaviour = new WakerBehaviour(myAgent, 4000) {
            @Override
            protected void onWake() {

//                log.info("Time is over in WaitForResonse");
            }
        };
        this.addSubBehaviour(receiveResponse);
        this.addSubBehaviour(wakeUpBehaviour);
    }

    @Override
    public int onEnd() {
        if (receiveResponse.getBets().isEmpty()) {
            log.info("Недостаточно мощности");
            return 0;
        } else {
            log.info("Мощности достаточно");
            return 1;
        }
    }
    public ACLMessage findWinner(){
        Map<String, ACLMessage> bets = this.receiveResponse.getBets();
        ACLMessage message = bets.entrySet().stream().map(x -> x.getValue()).min((o1, o2) -> {
            Double d1 = Double.parseDouble(o1.getContent());
            Double d2 = Double.parseDouble(o2.getContent());
            if (d1 < d2) {
                return -1;
            } else {
                return 1;
            }
        }).get();
        return message;

    }
}
