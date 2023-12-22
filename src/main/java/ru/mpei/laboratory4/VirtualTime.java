package ru.mpei.laboratory4;

public class VirtualTime {
    private final long startTime;
    private final int speedModeling;
    private VirtualTime(int speedModeling){
        this.startTime = System.currentTimeMillis();
        this.speedModeling = speedModeling;
    }
    private static class VirtualTimeHolder{
        public static final VirtualTime HOLDER_INSTANCE = new VirtualTime(48);
    }
    public static VirtualTime getINSTANCE(){
        return VirtualTimeHolder.HOLDER_INSTANCE;
    }

    public long  currentHour(){
        long currentTime = (System.currentTimeMillis() - startTime) * speedModeling;
        long currentDayTime = currentTime % ( 1000*60*60*24);
        return currentDayTime / (1000*60*60);
    }
    public long timeToNextHour(){
        long currentTime = (System.currentTimeMillis() - startTime) * speedModeling;
        long currentDayTime = currentTime % ( 1000*60*60*24);
        return 1000*60*60 - currentDayTime % (1000*60*60);

    }
}
