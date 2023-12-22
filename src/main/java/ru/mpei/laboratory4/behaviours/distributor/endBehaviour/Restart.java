package ru.mpei.laboratory4.behaviours.distributor.endBehaviour;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.behaviours.distributor.DistributorFSM;


@Slf4j
public class Restart extends OneShotBehaviour {
    private double needPower;
    private double maxPrice;
    private boolean firstIter;
    private AID consumerAID;

    public Restart(double needPower, double maxPrice,AID consumerAID, boolean firstIter) {
        this.needPower = needPower;
        this.maxPrice = maxPrice;
        this.firstIter = firstIter;
        this.consumerAID = consumerAID;
    }

    @Override
    public void action() {
        log.info("ПЕРЕЗАПУСК АУКЦИОНА");
        myAgent.addBehaviour(new DistributorFSM(this.needPower, this.maxPrice,consumerAID, firstIter));
    }
}
