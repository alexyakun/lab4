package ru.mpei.laboratory4.behaviours.consumer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.cfg.CfgConsumer;

@Getter
@Slf4j
public class ReceiveResult extends Behaviour {
    private double allPurchasedPower = 0;
    private double allPrice = 0;
    private CfgConsumer cfg;
    @Getter
    private double maxPrice;
    public ReceiveResult(CfgConsumer cfg){
        this.cfg = cfg;
        this.maxPrice = cfg.getPrice();
    }
    @Override
    public void onStart() {
        log.info("СТАРТ");
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchProtocol("Energy"));
        if (msg != null) {
            log.info("ПОЛУЧИЛ ОТВЕТ");
            if(msg.getContent().equals("Fail")){
                log.info("ПРИШЕЛ ОТКАЗ");
            } else if(msg.getContent().split(",")[0].equals("FIX")){
                double prodPrice = Double.parseDouble(msg.getContent().split(",")[1]);
                log.info("цена Producer {}", prodPrice);
                if(prodPrice < 1000){
                    double newPrice = prodPrice*1.5;
                    this.maxPrice = newPrice;
                    myAgent.addBehaviour(new PurchaseElectricity(cfg,newPrice));
                }
                else{
                    log.info("слишком большая цена");
                }


            }
            else{
                String[] split = msg.getContent().split(",");
                double power = Double.parseDouble(split[0]);
                double price = Double.parseDouble(split[1]);
                allPurchasedPower += power;
                allPrice += price;
            }

        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
