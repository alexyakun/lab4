package ru.mpei.laboratory4.function;

@StationType(name = "thermalStation")
public class ThermalStation extends Station{

    @Override
    public Double apply(Integer time) {
        if(this.time == -1){
            this.time = time;
        } else if(this.time!=time){
            usedPower = 0;
            this.time = time;
        }
        return 11.3;
    }
    @Override
    public double getRestPower(Integer time) {
        return apply(time) - usedPower;
    }

}
