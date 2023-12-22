package ru.mpei.laboratory4.function;

@StationType(name = "thermalStation1")
public class ThermalStation1 extends Station{

    @Override
    public Double apply(Integer time) {
        if(this.time == -1){
            this.time = time;
        } else if(this.time!=time){
            usedPower = 0;
            this.time = time;
        }
        return 10.1;
    }
    @Override
    public double getRestPower(Integer time) {
        return apply(time) - usedPower;
    }

}
