package ru.mpei.laboratory4;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import ru.mpei.laboratory4.agents.ConsumerAgent;
import ru.mpei.laboratory4.agents.DistributorAgent;
import ru.mpei.laboratory4.agents.ProducerAgent;

import java.util.List;
import java.util.Map;

public class JadeStarter {
    public static void main(String[] args) {
        VirtualTime.getINSTANCE();
        Map<String, Class<?>> agents = Map.of(
                "consumer1", ConsumerAgent.class,
                "consumer2", ConsumerAgent.class,
                "consumer3", ConsumerAgent.class,
                "distributor1", DistributorAgent.class,
                "distributor2", DistributorAgent.class,
                "distributor3", DistributorAgent.class,
                "thermalStation", ProducerAgent.class,
                "solarStation", ProducerAgent.class,
                "windStation", ProducerAgent.class
        );

        ExtendedProperties props = new ExtendedProperties();
        props.setProperty("gui", "true");
        props.setProperty("agents", addAgents(agents));
        props.setProperty("services", addServices(List.of("jade.core.messaging.TopicManagementService")));
        ProfileImpl p = new ProfileImpl(props);

        Runtime.instance().setCloseVM(true);
        Runtime.instance().createMainContainer(p);
    }

    private static String addAgents(Map<String, Class<?>> createAgents){
        StringBuilder outString = new StringBuilder();
        for (Map.Entry<String, Class<?>> entry : createAgents.entrySet()) {
            outString.append(entry.getKey()).append(":").append(entry.getValue().getName()).append(";");
        }
        System.out.println(outString);
        return outString.toString();
    }

    private static String addServices(List<String> services){
        StringBuilder outString = new StringBuilder();
        for (String service : services) {
            outString.append(service).append(";");
        }
        return outString.toString();
    }
}

