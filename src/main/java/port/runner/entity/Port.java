package port.runner.entity;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final int COUNT_BERTHS = 10;
    private static final int STORAGE_CAPACITY = 150;
    private Berth[] berths = new Berth[COUNT_BERTHS];
    private AtomicInteger storage = new AtomicInteger(0);
    private ReentrantLock lock = new ReentrantLock();

    public Port(){
        for(int i = 0; i < COUNT_BERTHS; i++){
            berths[i] = new Berth();
        }
    }

    public static int getCountBerths(){
        return COUNT_BERTHS;
    }

    public Berth[] getBerths(){
        return berths;
    }

    public void addShip(Ship ship){

    }

    public static int getStorageCapacity() {
        return STORAGE_CAPACITY;
    }

    public int getStorage(){
        return storage.get();
    }

    public void setStorage(int storage){
        this.storage.set(storage);
    }
}
