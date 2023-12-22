package ru.mpei.laboratory4.behaviours.producer;

import jade.core.behaviours.OneShotBehaviour;
import lombok.extern.slf4j.Slf4j;
import ru.mpei.laboratory4.function.Station;

@Slf4j
public class Success extends OneShotBehaviour {
    private Station station;
    public Success(Station station){
        this.station = station;
    }


    @Override
    public void action() {
        log.info("{} УДАЛОСЬ ПРОДАТЬ", myAgent.getLocalName());
    }
}
