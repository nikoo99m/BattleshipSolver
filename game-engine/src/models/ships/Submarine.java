package models.ships;

public class Submarine extends Ship {
    private static final String NAME = "Models.Ships.Submarine";
    private static final int LENGTH = 3;



    public Submarine(String name, int size) {
        super(name, size);
        shipColour = "\u001B[30;1m";
    }
}
