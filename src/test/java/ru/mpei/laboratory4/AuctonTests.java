package ru.mpei.laboratory4;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mpei.kit.MasStarterKit;
import ru.mpei.laboratory4.behaviours.consumer.GetPowerBeh;
import ru.mpei.laboratory4.behaviours.consumer.PurchaseElectricity;
import ru.mpei.laboratory4.behaviours.consumer.WaitResult;
import ru.mpei.laboratory4.behaviours.distributor.BuyerEnergy;
import ru.mpei.laboratory4.behaviours.producer.WaitForInvite;
import ru.mpei.laboratory4.cfg.CfgConsumer;
import ru.mpei.laboratory4.function.Station;
import ru.mpei.laboratory4.function.StationInstanceManager;
import ru.mpei.laboratory4.utils.DfHelper;
import ru.mpei.laboratory4.utils.XmlUtils;

import java.util.List;
import java.util.Map;

public class AuctonTests {
    private MasStarterKit kit = new MasStarterKit();
    @Test
    @SneakyThrows
    void successfulAuction(){
        VirtualTime time = VirtualTime.getINSTANCE();
        kit.startJade(List.of("jade.core.messaging.TopicManagementService"));
        CfgConsumer cfg = XmlUtils.parse("src/main/resources/config2/testconsumer1.xml", CfgConsumer.class).get();
        StationInstanceManager stationAnnotation = new StationInstanceManager();
        Map<String, Station> stations = stationAnnotation.findStation();
        Station station = stations.get("thermalStation");
        kit.createAgent("thermalStation", registerInDf("producer"),new WaitForInvite(station));
        kit.createAgent("distributor1", registerInDf("distributor1"),new BuyerEnergy());
        Thread.sleep(1000);
        PurchaseElectricity purchaseElectricity = new PurchaseElectricity(cfg, cfg.getPrice());
        purchaseElectricity.setTestMod(true);
        double power = Double.parseDouble(cfg.getPower().get((int)time.currentHour()));
        double maxPrice = cfg.getPrice();
        double needPower = power/100*cfg.getPowerValue();
        WaitResult waitResult = new WaitResult(needPower, maxPrice,cfg);
        kit.createAgent("consumer1", purchaseElectricity,waitResult);
        Thread.sleep(15000);
        Assertions.assertEquals(1, waitResult.onEnd());
    }

    @Test
    @SneakyThrows
    void successfulAuction2(){
        VirtualTime time = VirtualTime.getINSTANCE();
        kit.startJade(List.of("jade.core.messaging.TopicManagementService"));
        CfgConsumer cfg = XmlUtils.parse("src/main/resources/config2/testconsumer2.xml", CfgConsumer.class).get();
        StationInstanceManager stationAnnotation = new StationInstanceManager();
        Map<String, Station> stations = stationAnnotation.findStation();
        Station station = stations.get("thermalStation");
        StationInstanceManager stationAnnotation2 = new StationInstanceManager();
        Map<String, Station> stations2 = stationAnnotation2.findStation();
        Station station2 = stations2.get("thermalStation1");
        kit.createAgent("thermalStation", registerInDf("producer"),new WaitForInvite(station));
        kit.createAgent("thermalStation2", registerInDf("producer"),new WaitForInvite(station2));
        kit.createAgent("distributor1", registerInDf("distributor1"),new BuyerEnergy());
        Thread.sleep(1000);
        PurchaseElectricity purchaseElectricity = new PurchaseElectricity(cfg, cfg.getPrice());
        purchaseElectricity.setTestMod(true);
        double power = Double.parseDouble(cfg.getPower().get((int)time.currentHour()));
        double maxPrice = cfg.getPrice();
        double needPower = power/100*cfg.getPowerValue();
        WaitResult waitResult = new WaitResult(needPower, maxPrice,cfg);
        kit.createAgent("consumer1", purchaseElectricity,waitResult);
        Thread.sleep(20000);
        Assertions.assertEquals(1, waitResult.onEnd());

    }

    @Test
    @SneakyThrows
    void successfulAuction3(){
        VirtualTime time = VirtualTime.getINSTANCE();
        kit.startJade(List.of("jade.core.messaging.TopicManagementService"));
        CfgConsumer cfg = XmlUtils.parse("src/main/resources/config2/testconsumer3.xml", CfgConsumer.class).get();
        StationInstanceManager stationAnnotation = new StationInstanceManager();
        Map<String, Station> stations = stationAnnotation.findStation();
        Station station = stations.get("thermalStation");
        StationInstanceManager stationAnnotation2 = new StationInstanceManager();
        Map<String, Station> stations2 = stationAnnotation2.findStation();
        Station station2 = stations2.get("thermalStation1");
        kit.createAgent("thermalStation", registerInDf("producer"),new WaitForInvite(station));
        kit.createAgent("thermalStation2", registerInDf("producer"),new WaitForInvite(station2));
        kit.createAgent("distributor1", registerInDf("distributor1"),new BuyerEnergy());
        Thread.sleep(1000);
        PurchaseElectricity purchaseElectricity = new PurchaseElectricity(cfg, cfg.getPrice());
        purchaseElectricity.setTestMod(true);
        double power = Double.parseDouble(cfg.getPower().get((int)time.currentHour()));
        double maxPrice = cfg.getPrice();
        double needPower = power/100*cfg.getPowerValue();
        WaitResult waitResult = new WaitResult(needPower, maxPrice,cfg);
        kit.createAgent("consumer1", purchaseElectricity,waitResult);
        Thread.sleep(20000);
        Assertions.assertEquals(1, waitResult.onEnd());

    }

    private Behaviour registerInDf(String serviceToReg){
        return new OneShotBehaviour() {
            @Override
            public void action() {
                DfHelper.register(this.getAgent(), serviceToReg);
            }
        };
    }

    private Behaviour createGetPowerBeh(long delayToStart, CfgConsumer cfg){
        return new OneShotBehaviour(){
            Behaviour subBeh;
            @Override
            public void action() {
                subBeh =new GetPowerBeh(this.getAgent(),1000, cfg);
                getAgent().addBehaviour(subBeh);
            }

            @Override
            public int onEnd() {
                return subBeh.onEnd();
            }
        };
    }

}