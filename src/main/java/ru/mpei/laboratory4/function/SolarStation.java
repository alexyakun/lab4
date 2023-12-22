package ru.mpei.laboratory4.function;

@StationType(name = "solarStation")
public class SolarStation extends Station{


    @Override
    public Double apply(Integer time) {
        if(this.time == -1){
            this.time = time;
        } else if(this.time!=time){
            usedPower = 0;
            this.time = time;
        }
        double C0 = -94.782;
        double C1 = 24.376;
        double C2 = -1.582;
        double C3 = 0.030;
        if(time>5 && time < 19){
            return C0*Math.pow(time,0) + C1*Math.pow(time,1) + C2*Math.pow(time,2) + C3*Math.pow(time,3);
        }else {
            return 0.0;
        }

    }
    @Override
    public double getRestPower(Integer time) {
        return apply(time) - usedPower;
    }


}
