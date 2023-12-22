package ru.mpei.laboratory4.behaviours.producer;

import jade.core.behaviours.OneShotBehaviour;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Fail extends OneShotBehaviour {

    @Override
    public void action() {

        log.info("{} НЕ УДАЛОСЬ ПРОДАТЬ", myAgent.getLocalName());
    }
}
