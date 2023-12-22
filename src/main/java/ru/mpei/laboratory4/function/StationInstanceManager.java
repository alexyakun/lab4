package ru.mpei.laboratory4.function;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.*;

public class StationInstanceManager {
    @SneakyThrows
    public Map<String, Station> findStation(){
        Map<String, Station>  stations = new HashMap<>();
        Reflections r = new Reflections(Station.class);
        Set<Class<?>> typesAnnotatedWith = r.getTypesAnnotatedWith(StationType.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            if(!Station.class.isAssignableFrom(aClass)){
                continue;
            }
            StationType annotation = aClass.getAnnotation(StationType.class);
            String name = annotation.name();
            Station station = (Station) aClass.getDeclaredConstructor().newInstance();
            stations.put(name,station);
        }
        return stations;
    }
}
