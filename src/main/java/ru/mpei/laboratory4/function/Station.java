package ru.mpei.laboratory4.function;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;


public abstract class Station implements Function<Integer, Double> {

    @Getter
    @Setter
    protected double usedPower = 0;
    protected int time = -1;
    public abstract double getRestPower(Integer time);
    public void addUsedPower(double power){
        usedPower+=power;
    }


    public double getPrice(Double currentPower) {
        return 1000/(currentPower+0.01);
    }



}
