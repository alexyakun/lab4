package ru.mpei.laboratory4.behaviours.distributor.endBehaviour;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.behaviours.distributor.DistributorFSM;

@Slf4j
public class Split extends OneShotBehaviour {
    private double needPower;
    private double maxPrice;
    private boolean firstIter;
    private AID consumerAID;

    public Split(double needPower, double maxPrice, AID consumerAID, boolean firstIter) {
        this.needPower = needPower;
        this.maxPrice = maxPrice;
        this.firstIter = firstIter;
        this.consumerAID = consumerAID;
    }
    @Override
    public void action() {
        if(firstIter){
            firstIter = false;
            log.info("SPLIT НА ДВА");
            myAgent.addBehaviour(new DistributorFSM(this.needPower/2, this.maxPrice/2,consumerAID, firstIter));
            myAgent.addBehaviour(new DistributorFSM(this.needPower/2, this.maxPrice/2,consumerAID, firstIter));
        }
        else {
            log.info("SPLIT БОЛЬШЕ НЕЛЬЗЯ");
            myAgent.addBehaviour(new Fail(consumerAID));
        }
    }

}
