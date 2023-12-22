package ru.mpei.laboratory4.cfg;

import lombok.Data;

import javax.xml.bind.annotation.*;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cfg")
public class CfgDistributor {

    @XmlElement(name = "num")
    private int num;

}