package ru.mpei.laboratory4.behaviours.producer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.function.Station;

@Slf4j
public class WaitForContract extends ParallelBehaviour {
    private Behaviour wakeUpBehaviour;
    private ReceiveContract receiveContract;
    private Station station;
    private AID topic;
    public WaitForContract(AID topic, Station station){

        super(WHEN_ANY);
        this.station = station;
        this.topic = topic;

    }

    @Override
    public void onStart() {
        receiveContract = new ReceiveContract(topic,station);
        wakeUpBehaviour = new WakerBehaviour(myAgent, 3000) {
            @Override
            protected void onWake() {

//                log.info("Time is over in WaitForContract");
            }
        };
        this.addSubBehaviour(receiveContract);
        this.addSubBehaviour(wakeUpBehaviour);
    }

    @Override
    public int onEnd() {
        if (receiveContract.done()) {
            if (receiveContract.onEnd() == 1) {
                log.info("Wait for Contract Agree");
                return 1;
            } else {
                log.info("Wait for Contract Fail");
                return 0;
            }
        } else {
            log.info("Сообщение от продaвца не получено");
            return 0;
        }
    }
}
