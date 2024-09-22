package models.Ships;

import java.util.ArrayList;
import java.util.List;

    public class DefaultShip {
        private final List<Ship> ships;

        public DefaultShip() {
            this.ships = new ArrayList<>();
        }

        public List<Ship> initializeDefaultShip() {
            ships.add(new Destroyer("Models.Ships.Destroyer" , 2));
            ships.add(new BattleShip("Models.Ships.BattleShip" , 4));
            ships.add(new Carrier("Models.Ships.Carrier" , 5));
            ships.add(new Submarine("Models.Ships.Submarine" , 3));
            ships.add(new Cruiser("Models.Ships.Cruiser" , 3));
            return ships;
        }

    }
