package port.runner.entity.manager;

import port.runner.entity.Port;
import port.runner.entity.Ship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class PortManager{
    private Port port;
    private final Semaphore semaphore;
    private AtomicInteger platform = new AtomicInteger(0);
    private static final Logger LOGGER = LoggerFactory.getLogger(PortManager.class);
    private ReentrantLock lock = new ReentrantLock();

    public PortManager(Port port){
        this.port = port;
        semaphore = new Semaphore(Port.getCountBerths());
    }

    public void takeShip(Ship ship){
        try{
            semaphore.acquire();
        }catch (InterruptedException e){
            LOGGER.error("acquire error");
            Thread.currentThread().interrupt();
        }

        int i = 0;

        while(i < Port.getCountBerths()){
            if(!port.getBerths()[i].getOccupancy()){
                port.getBerths()[i].setOccupancy(true);
                ship.setBerthNumber(i);
                break;
            }
            i++;
        }
        LOGGER.info("Ship "+Thread.currentThread().getName()+" get berth"+ship.getBerthNumber());

        try{
            TimeUnit.MILLISECONDS.sleep((int)Math.random() * 100);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            LOGGER.error("Ship not received");
        }

    }

    public void sendShip(Ship ship){
        lock.lock();
        int product = 0;
        if(Port.getStorageCapacity()*0.8 < ship.getCargo() + port.getStorage()){
            if(platform.get() != 0 && ship.getShipCapacity() - ship.getCargo() >  platform.get()){
                product = port.getStorage();
                platform.set(0);
                LOGGER.info("Take from platform to ship " + Thread.currentThread().getName() + "; berth number : "+ ship.getBerthNumber());
            }else if (platform.get() == 0){
                product = port.getStorage();
                platform.set(ship.getCargo());
                LOGGER.info("Take from ship " + Thread.currentThread().getName() + " to platform; berth number : "+ ship.getBerthNumber());
            }else{
                product = port.getStorage() - (ship.getShipCapacity() - ship.getCargo());
            }
            LOGGER.info("Ship " + Thread.currentThread().getName() + " leave berth" + ship.getBerthNumber());
            port.setStorage(product);
            port.getBerths()[ship.getBerthNumber()].setOccupancy(false);
            lock.unlock();
            semaphore.release();
            try{
                TimeUnit.MILLISECONDS.sleep((int)Math.random() * 100);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                LOGGER.error(e.getMessage());
            }

        }else {
            product = port.getStorage() + ship.getCargo();
            LOGGER.info("Ship " + Thread.currentThread().getName() + " leave berth" + ship.getBerthNumber());
            port.setStorage(product);
            port.getBerths()[ship.getBerthNumber()].setOccupancy(false);
            lock.unlock();
            semaphore.release();
        }
    }
}
