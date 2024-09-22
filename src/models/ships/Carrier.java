package models.ships;

public class Carrier extends Ship {
    private static final String NAME = "Models.Ships.Carrier";
    private static final int LENGTH = 5;


    public Carrier(String name, int size) {
        super(name, size);
        shipColour = "\u001B[33m";
    }
}
