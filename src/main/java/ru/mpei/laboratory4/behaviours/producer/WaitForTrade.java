package ru.mpei.laboratory4.behaviours.producer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitForTrade extends ParallelBehaviour {
    private Behaviour wakeUpBehaviour;
    private TradeBehaviour tradeBehaviour;
    private AID topic;
    private double minPrice;

    public WaitForTrade(AID topic, double minPrice){
        super(WHEN_ANY);
        this.topic = topic;
        this.minPrice = minPrice;
    }

    @Override
    public void onStart() {
        tradeBehaviour = new TradeBehaviour(topic, minPrice);
        wakeUpBehaviour = new WakerBehaviour(myAgent, 2000) {
            @Override
            protected void onWake() {

//                log.info("Time is over in WaitForTrade");
            }
        };
        this.addSubBehaviour(tradeBehaviour);
        this.addSubBehaviour(wakeUpBehaviour);
    }

    @Override
    public int onEnd() {
        if (tradeBehaviour.done()) {
            return 0;
        } else {
            return 1;
        }
    }
}
