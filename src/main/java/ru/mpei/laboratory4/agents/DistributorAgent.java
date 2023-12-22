package ru.mpei.laboratory4.agents;

import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.behaviours.distributor.BuyerEnergy;
import ru.mpei.laboratory4.utils.DfHelper;

@Slf4j
public class DistributorAgent extends Agent {
    @Override
    protected void setup() {
        log.info("{} Distributor agent was born",getLocalName());
        DfHelper.register(this, getLocalName());
        addBehaviour(new BuyerEnergy());
    }
}
