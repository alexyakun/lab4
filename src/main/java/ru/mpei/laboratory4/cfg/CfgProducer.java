package ru.mpei.laboratory4.cfg;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.function.Function;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cfg")
public class CfgProducer {
    @XmlElement(name = "power")
    private double power;
    private Function<Integer, Double> function;
}
