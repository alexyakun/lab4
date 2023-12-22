package ru.mpei.laboratory4;

import lombok.SneakyThrows;
import ru.mpei.laboratory4.function.SolarStation;
import ru.mpei.laboratory4.function.Station;

import java.util.Random;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
//        VirtualTime virtualTime = new VirtualTime(1800);
//        while (true){
//            System.out.println(virtualTime.currentHour());
//            System.out.println(virtualTime.timeToNextHour());
//            Thread.sleep(2000);
//        }
//        CfgConsumer cfg = null;
//        try {
//            JAXBContext context =
//                    JAXBContext.newInstance(CfgConsumer.class);
//            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
//            cfg = (CfgConsumer) jaxbUnmarshaller.unmarshal(new
//                    File("src/main/resources/config2/consumer1.xml"));
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//        System.out.println(cfg.getPower());
        Station station = new SolarStation();

        Random random = new Random();
        System.out.println(random.nextGaussian(9.7,5.0));


    }
}
