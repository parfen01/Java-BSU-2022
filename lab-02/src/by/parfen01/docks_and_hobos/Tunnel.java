package by.parfen01.docks_and_hobos;

import java.util.ArrayDeque;
import java.util.logging.Level;

public class Tunnel {
    private final ArrayDeque<Ship> shipQueue;
    private final int maxShipCount;

    public Tunnel(int maxShipCount) {
        this.maxShipCount = maxShipCount;
        this.shipQueue = new ArrayDeque<>();
    }

    public synchronized void addShip(Ship ship) {
        if (shipQueue.size() == maxShipCount) {
            Controller.getController().getConsoleLogger().log(
                    Level.INFO, "The ship number " + ship.getId() + " sank");
            return;
        }
        shipQueue.add(ship);
        if (shipQueue.size() == 1) {
            notify();
        }
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "The ship number " + ship.getId() + " entered tunnel");
    }

    public synchronized Ship getShip() throws InterruptedException {
        if (shipQueue.isEmpty()) {
            wait();
        }
        Ship result = shipQueue.poll();
        assert(result != null);
        Controller.getController().getConsoleLogger().log(
                Level.INFO, "The ship number " + result.getId() + " leaved tunnel");
        return result;
    }
}