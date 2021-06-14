import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import port.runner.entity.Port;
import port.runner.entity.Ship;
import port.runner.entity.manager.PortManager;

import java.util.ArrayList;
public class PortManagerTest {
    private static Port port = new Port();
    private static PortManager portManager = new PortManager(port);
    private static ArrayList<Ship> ships = new ArrayList<>();

    @BeforeAll
    public static void init(){
        for(int i =0; i < 3; i++){
            ships.add(new Ship(10 * (i+1), 50, portManager));
        }
    }

    @Test
    public void takeShip(){
        int i = 0;
        for(Ship ship : ships) {
            portManager.takeShip(ship);
            Assertions.assertTrue(port.getBerths()[i].getOccupancy());
            i++;
        }
    }

   @Test
    public void sendShip(){
       int i = 0;
       for(Ship ship : ships) {
           portManager.sendShip(ship);
           Assertions.assertFalse(port.getBerths()[i].getOccupancy());
           i++;
       }
    }
}
