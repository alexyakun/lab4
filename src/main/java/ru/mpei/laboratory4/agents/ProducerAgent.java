package ru.mpei.laboratory4.agents;

import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.behaviours.producer.WaitForInvite;
import ru.mpei.laboratory4.function.Station;
import ru.mpei.laboratory4.function.StationInstanceManager;
import ru.mpei.laboratory4.utils.DfHelper;

import java.util.Map;

@Slf4j
public class ProducerAgent extends Agent {
    @Override
    protected void setup() {
        DfHelper.register(this, "producer");
        StationInstanceManager stationAnnotation = new StationInstanceManager();
        Map<String, Station> stations = stationAnnotation.findStation();
        Station station = stations.get(getLocalName());
        this.addBehaviour(new WaitForInvite(station));
    }
}
