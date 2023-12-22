package ru.mpei.laboratory4.behaviours.distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import ru.mpei.laboratory4.utils.DfHelper;
import ru.mpei.laboratory4.utils.TopicHelper;

import java.util.Random;

public class StartAuction extends OneShotBehaviour {

    private AID topic;
    private double needPower;
    public StartAuction(double needPower){
        this.needPower = needPower;
    }
    @Override
    public void action() {
        var ags = DfHelper.search(myAgent, "producer");
        String topicName = "topic_"+new Random().nextLong(1000000000);
        topic = TopicHelper.register(myAgent, topicName);
        ACLMessage msg =new ACLMessage(ACLMessage.PROPOSE);
        msg.setContent(topicName+","+needPower);
        msg.setProtocol("Auction");
        ags.forEach(e -> msg.addReceiver(e));
        myAgent.send(msg);
    }
    public AID getTopic(){
        return topic;
    }
}
