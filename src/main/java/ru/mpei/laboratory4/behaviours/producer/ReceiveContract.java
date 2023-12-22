package ru.mpei.laboratory4.behaviours.producer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.VirtualTime;
import ru.mpei.laboratory4.function.Station;

@Slf4j
public class ReceiveContract extends Behaviour {
    private ACLMessage contract = null;
    private Station station;
    private AID topic;
    private boolean needRestart = false;
    private boolean confirm = false;
    private VirtualTime time = VirtualTime.getINSTANCE();
    public ReceiveContract(AID topic, Station station){

        this.station = station;
        this.topic = topic;
    }
    @Override
    public void action() {
        contract = myAgent.receive(MessageTemplate.MatchProtocol("Contract"));
        if(contract != null){
            if(contract.getContent().equals("FAIL")){
                log.info("ПОЛУЧЕН ОТКАЗ ОТ ПРОДАВЦА");

            } else{
                this.confirm = true;
                double needPower = Double.parseDouble(contract.getContent());
                log.info("Располагаемая мощность {}", station.getRestPower((int)time.currentHour()));
                log.info("Требуемая мощность {}", needPower);
                if(station.getRestPower((int)time.currentHour()) > needPower){
                    station.addUsedPower(needPower);
                    log.info("Остаточная мощность {}", station.getRestPower((int)time.currentHour()));
                }else{
                    log.info("Требуется перезапуск");
                    needRestart = true;
                    ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
                    msg.setContent(needPower+"");
                    msg.setProtocol("Contract");
                    msg.addReceiver(contract.getSender());
                    myAgent.send(msg);
                }

                log.info("{} продал свою мощность", myAgent.getLocalName());
            }

        } else{
            block();
        }
    }

    @Override
    public boolean done() {
        return this.contract!=null;
    }
    @Override
    public int onEnd() {
        if(confirm){
            return 1;
        }
        else{
            return 0;
        }
    }
}
