package ru.mpei.laboratory4.behaviours.producer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.VirtualTime;
import ru.mpei.laboratory4.function.Station;
import ru.mpei.laboratory4.utils.TopicHelper;


@Slf4j
public class WaitForInvite extends Behaviour {
    Station station;
    VirtualTime time = VirtualTime.getINSTANCE();
    private boolean invited = false;
    public  WaitForInvite(Station station){
        this.station = station;
    }
    @Override
    public void action() {
        ACLMessage auction = myAgent.receive(MessageTemplate.MatchProtocol("Auction"));

        if (auction != null) {
            String[] split = auction.getContent().split(",");
            String topicName = split[0];
            double needPower = Double.parseDouble(split[1]);
            double power = station.getRestPower((int) time.currentHour());
            double minPrice = station.getPrice(station.apply((int) time.currentHour()));
            AID topic = TopicHelper.register(myAgent,topicName);
            log.info("{} get invite от {} in {}\n"+
                    "minimal price: {}\n"+
                    "Power {} ", myAgent.getLocalName(),auction.getSender().getLocalName(),topicName, minPrice,power);

            if(power > needPower) {
                myAgent.addBehaviour(new ProducerFSM(topic, minPrice, station));
            }
            else{
                log.info("Требуемая мощность {} больше располагаемой {}",needPower,power);
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
