package ru.mpei.laboratory4.function;

import java.util.Random;
@StationType(name = "windStation")
public class WindStation extends Station{
    private final double price =100;
    private int time = -1;
    private double currentPower;


    @Override
    public Double apply(Integer time) {
        if(this.time == -1){
            this.time = time;
            this.currentPower = new Random().nextGaussian(9.7,5.0);
            return this.currentPower;
        } else if(this.time!=time){
            usedPower = 0;
            this.time = time;
            this.currentPower = new Random().nextGaussian(9.7,5.0);
            return this.currentPower;
        } else {
            return currentPower;
        }

    }

    @Override
    public double getRestPower(Integer time) {
        return apply(time) - usedPower;
    }
}
