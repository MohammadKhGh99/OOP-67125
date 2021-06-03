import oop.ex2.*;

public class SpaceShipFactory {
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] shipsArray = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("h")) {
                shipsArray[i] = new Human();
            } else if (args[i].equals("r")) {
                shipsArray[i] = new Runner();
            } else if (args[i].equals("b")) {
                shipsArray[i] = new Basher();
            } else if (args[i].equals("a")) {
                shipsArray[i] = new Aggressive();
            } else if (args[i].equals("d")) {
                shipsArray[i] = new Drunkard();
            } else if (args[i].equals("s")) {
                shipsArray[i] = new Special();
            }
        }
        return shipsArray;
    }
}
