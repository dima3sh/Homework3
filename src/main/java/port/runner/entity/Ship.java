package port.runner.entity;

import port.runner.entity.manager.PortManager;

import java.util.concurrent.atomic.AtomicInteger;

public class Ship extends Thread {
    private AtomicInteger cargo = new AtomicInteger();
    private final int shipCapacity;
    private boolean fullness = false;
    private PortManager portManager;
    private int berthNumber;

    public Ship(int cargo, int shipCapacity, PortManager portManager){
        this.cargo.set(cargo);
        this.fullness = false;
        this.shipCapacity = shipCapacity;
        this.portManager = portManager;
    }

    public int getCargo(){
        return cargo.get();
    }

    public void setCargo(int cargo){
        this.cargo.set(cargo);
    }

    public int getShipCapacity(){
        return shipCapacity;
    }

    public void setBerthNumber(int berthNumber){
        this.berthNumber = berthNumber;
    }

    public int getBerthNumber() {
        return berthNumber;
    }

    @Override
    public void run() {
        portManager.takeShip(this);
        portManager.sendShip(this);
    }
}

