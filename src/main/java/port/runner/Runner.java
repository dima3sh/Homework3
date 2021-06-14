package port.runner;

import port.runner.entity.Port;
import port.runner.entity.manager.PortManager;
import port.runner.entity.Ship;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private static final Port PORT = new Port();
    private static final PortManager portManager = new PortManager(PORT);



    public static void main(String[] args) {
        List<Runnable> runner = new ArrayList<>();
        for (int i = 0; i<30; i++) {
            //runner.add(new Ship((int) (Math.random()*40+10), true, portManager));
            Ship ship = new Ship((int) (Math.random()*40+10), 50, portManager);
            ship.start();
        }

    }


}