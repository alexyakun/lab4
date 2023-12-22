package ru.mpei.laboratory4.behaviours.consumer;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.cfg.CfgConsumer;

@Slf4j
public class WaitResult extends ParallelBehaviour {
    private Behaviour wakeUpBehaviour;
    private ReceiveResult receiveResult;
    private double needPower;

    private double maxPrice;
    private CfgConsumer cfg;


    public WaitResult(double needPower, double maxPrice, CfgConsumer cfg){

        super(WHEN_ANY);
        this.needPower = needPower;
        this.maxPrice = maxPrice;
        this.cfg = cfg;
    }

    @Override
    public void onStart() {

        receiveResult = new ReceiveResult(cfg);
        wakeUpBehaviour = new WakerBehaviour(myAgent, 15000) {
            @Override
            protected void onWake() {
                log.info("Time is over in WaitResult over");
            }
        };
        this.addSubBehaviour(receiveResult);
        this.addSubBehaviour(wakeUpBehaviour);
    }

    @Override
    public int onEnd() {

        log.info("ТОРГИ ЗАВЕРШИЛИСЬ\n" +
                        "###################################################\n"+
                        "Требуемая мощность: {}\n Максимальная цена {} \n Купленная мощность {}\n Закупочная цена: {}\n"
                +"###################################################\n",
                needPower,receiveResult.getMaxPrice(), receiveResult.getAllPurchasedPower(),receiveResult.getAllPrice());
        return 1;
    }
}
