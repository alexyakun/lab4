package ru.mpei.laboratory4.behaviours.consumer;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.VirtualTime;
import ru.mpei.laboratory4.cfg.CfgConsumer;
import ru.mpei.laboratory4.utils.DfHelper;
@Slf4j
public class PurchaseElectricity extends OneShotBehaviour {
    private CfgConsumer cfg;
    private VirtualTime time = VirtualTime.getINSTANCE();
    @Setter
    private boolean testMod = false;
    @Setter
    private double maxPrice;

    public PurchaseElectricity(CfgConsumer cfg, double maxPrice) {

        this.cfg = cfg;
        this.maxPrice = maxPrice;
    }

    @Override
    public void onStart() {
        log.info("START PURCHASE");
    }
    @Override
    public void action() {
        double power = Double.parseDouble(cfg.getPower().get((int)time.currentHour()));
        double needPower = power/100*cfg.getPowerValue();
        ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
        msg.setContent(needPower+","+maxPrice);
        msg.setProtocol("Energy");
        var ags = DfHelper.search(myAgent, "distributor"+cfg.getNum());
        ags.forEach(e -> msg.addReceiver(e));
        if(!testMod) {
            myAgent.addBehaviour(new WaitResult(needPower, maxPrice, cfg));
        }
        myAgent.send(msg);
    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }
}
