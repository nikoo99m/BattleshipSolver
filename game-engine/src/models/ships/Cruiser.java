package models.ships;

public class Cruiser extends Ship {
    private static final String NAME = "Models.Ships.Cruiser";
    private static final int LENGTH = 3;



    public Cruiser(String name, int size) {
        super(name, size);
        shipColour = "\u001B[32m";

    }
}
