package models.ships;

public class BattleShip extends Ship {
    private static final String NAME = "Models.Ships.BattleShip";
    private static final int LENGTH = 4;


    public BattleShip(String name, int size) {
        super(name, size);
        shipColour = "\u001B[34m";
    }
}
