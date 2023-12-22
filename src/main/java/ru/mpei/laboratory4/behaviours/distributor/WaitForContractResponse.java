package ru.mpei.laboratory4.behaviours.distributor;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitForContractResponse extends ParallelBehaviour {
    private Behaviour wakeUpBehaviour;
    private ReceiveContractResponse receiveContractResponse;
    public WaitForContractResponse(){
        super(WHEN_ANY);
    }

    @Override
    public void onStart() {
        receiveContractResponse = new ReceiveContractResponse();
        wakeUpBehaviour = new WakerBehaviour(myAgent, 2000) {
            @Override
            protected void onWake() {

//                log.info("Time is over in WaitForContractResponse");
            }
        };
        this.addSubBehaviour(receiveContractResponse);
        this.addSubBehaviour(wakeUpBehaviour);

    }

    @Override
    public int onEnd() {
        if(receiveContractResponse.done()){
            return 0;
        }else{
            return 1;
        }
    }
}
