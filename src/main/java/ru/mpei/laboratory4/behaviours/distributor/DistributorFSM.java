package ru.mpei.laboratory4.behaviours.distributor;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import ru.mpei.laboratory4.behaviours.distributor.endBehaviour.*;


public class DistributorFSM extends FSMBehaviour {
    private double needPower;
    private double maxPrice;
    private boolean firstIter;
    private AID consumerAID;

    private static final String START_AUCTION ="startAuction",
            WAIT_RESPONSE ="waitResponse",
            WAIT_CONTRACT ="waitContract",
            SEND_CONTRACT ="sendContract",
            RESTART="restart",
            GOOD_END="goodEnd",
            SPLIT="split",
            BAD_END ="badEnd";
    public DistributorFSM(double needPower, double maxPrice, AID consumerAID, boolean firstIter){
        this.needPower = needPower;
        this.maxPrice = maxPrice;
        this.firstIter = firstIter;
        this.consumerAID = consumerAID;
    }
    @Override
    public void onStart() {
        StartAuction startAuction = new StartAuction(needPower);
        this.registerFirstState(startAuction, START_AUCTION);
        WaitForResponse waitForResponse = new WaitForResponse(startAuction);
        this.registerState(waitForResponse, WAIT_RESPONSE);
        this.registerState(new WaitForContractResponse(),WAIT_CONTRACT);
        SendContractProducer sendContractProducer = new SendContractProducer(waitForResponse, maxPrice, needPower,startAuction);
        this.registerState(sendContractProducer, SEND_CONTRACT);
        this.registerLastState(new Success(sendContractProducer, consumerAID, needPower),GOOD_END);
        this.registerLastState(new Fail(consumerAID), BAD_END);
        this.registerLastState(new Restart(needPower, maxPrice,consumerAID,firstIter),RESTART);
        this.registerLastState(new Split(needPower, maxPrice,consumerAID,firstIter),SPLIT);
        this.registerLastState(new FixPrice(consumerAID, sendContractProducer), BAD_END);
        this.registerDefaultTransition(START_AUCTION, WAIT_RESPONSE);
        this.registerTransition( WAIT_RESPONSE,  SEND_CONTRACT, 1);
        this.registerTransition( WAIT_RESPONSE,SPLIT, 0);
        this.registerTransition(SEND_CONTRACT,WAIT_CONTRACT,1);
        this.registerTransition(SEND_CONTRACT,BAD_END,0);
        this.registerTransition(WAIT_CONTRACT,GOOD_END,1);
        this.registerTransition(WAIT_CONTRACT,RESTART,0);

    }
}
