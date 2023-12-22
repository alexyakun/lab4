package ru.mpei.laboratory4.behaviours.producer;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import ru.mpei.laboratory4.function.Station;


public class ProducerFSM extends FSMBehaviour {
    private static final

            String TRADE_BEH ="tradeBehaviour",
            WAIT_CONTRACT = "waitContract",
            GOOD_END="goodEnd",
            BAD_END ="badEnd";
    private double myMinPrice;
    private AID topic;
    private Station station;
    public ProducerFSM(AID topic, double myMinPrice, Station station){
        this.myMinPrice = myMinPrice;
        this.topic = topic;
        this.station = station;
    }
    @Override
    public void onStart() {
        this.registerFirstState(new WaitForTrade(topic,myMinPrice), TRADE_BEH);
        this.registerState(new WaitForContract(topic,station), WAIT_CONTRACT);
        this.registerLastState(new Success(station),GOOD_END);
        this.registerLastState(new Fail(), BAD_END);

        this.registerTransition(TRADE_BEH, BAD_END,0);
        this.registerTransition(TRADE_BEH, WAIT_CONTRACT, 1);
        this.registerTransition(WAIT_CONTRACT, GOOD_END, 1);
        this.registerTransition(WAIT_CONTRACT, BAD_END, 0);
    }

}
