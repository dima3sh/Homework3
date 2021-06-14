package port.runner.entity;

import java.util.concurrent.atomic.AtomicBoolean;

public class Berth {
    public AtomicBoolean occupancy = new AtomicBoolean(false);

    public boolean getOccupancy(){
        return occupancy.get();
    }

    public void setOccupancy(boolean occupancy){
        this.occupancy.set(occupancy);
    }

}
