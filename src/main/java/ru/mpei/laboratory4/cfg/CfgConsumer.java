package ru.mpei.laboratory4.cfg;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cfg")
public class CfgConsumer {
        @XmlElementWrapper(name = "power")
        @XmlElement(name = "h")
        private List<String> power;
        @XmlElement(name = "price")
        private double price;
        @XmlElement(name = "num")
        private int num;
        @XmlElement(name = "value")
        private double powerValue;

}
