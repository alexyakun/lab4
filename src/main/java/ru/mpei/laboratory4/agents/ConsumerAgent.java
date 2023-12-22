package ru.mpei.laboratory4.agents;

import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.behaviours.consumer.GetPowerBeh;
import ru.mpei.laboratory4.cfg.CfgConsumer;
import ru.mpei.laboratory4.utils.XmlUtils;

import java.util.Optional;
@Slf4j
public class ConsumerAgent extends Agent {
    @Override
    protected void setup() {
        Optional<CfgConsumer> parse = XmlUtils.parse("src/main/resources/config2/" + getLocalName() + ".xml", CfgConsumer.class);
        if(parse.isPresent()){
            addBehaviour(new GetPowerBeh(this,1000, parse.get()));
        }else {
            log.error("Cant parse config file");
        }
    }
}
