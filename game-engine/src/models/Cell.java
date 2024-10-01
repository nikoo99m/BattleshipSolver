package models;

import models.ships.Ship;

public class Cell {
    private String status;

    public static final String WATER = "\uD83D\uDFE6";//""~";//"\uD83C\uDF0A";//(char) 176 ;
    public static final String HIT = "\uD83D\uDCA5"; // "●";
    public static final String MISS = "\u274C";
    private String shipShape;
    public static final String SHIP = "⛵";//"⛴";//(char) 154;

    public static final String SUNK = "⬛";//"\uD83D\uDFB3";
    public String colourCode;
    private Ship ship;

    public Cell() {
        this.status = WATER;
    }
    public Ship getShip() {
        return ship;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

