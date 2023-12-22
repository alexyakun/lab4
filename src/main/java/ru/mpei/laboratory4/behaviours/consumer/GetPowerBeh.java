package ru.mpei.laboratory4.behaviours.consumer;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.VirtualTime;
import ru.mpei.laboratory4.cfg.CfgConsumer;

@Slf4j
public class GetPowerBeh extends TickerBehaviour {

    private long hour = -1;
    private CfgConsumer cfg;
    VirtualTime time = VirtualTime.getINSTANCE();

    public GetPowerBeh(Agent a, long period, CfgConsumer cfg) {

        super(a, period);
        this.cfg = cfg;
    }

    @Override
    public void onStart() {
        log.info("СТАРТ");
    }

    @Override
    protected void onTick() {
        if(hour != time.currentHour()){
            hour = time.currentHour();
            myAgent.addBehaviour(new PurchaseElectricity(cfg, cfg.getPrice()));
        }
//        log.info("Осталось до следующего часа {} c",time.timeToNextHour()/1000/48);
    }
}
