package com.mycompany.proyecto_1_progra_ii;

public enum ShipData {

    SUBMARINE("Submarine", 1, 4),
    DESTROYER("Destroyer", 2, 3),
    CRUISER("Cruiser", 3, 2),
    BATTLESHIP("Battleship", 4, 1);

    private int shipSize;
    private int shipQuantity;
    private String shipName;

    ShipData(String name, int size, int quantity) {

        this.shipName = name;
        this.shipSize = size;
        this.shipQuantity = quantity;
    }

    public int getShipSize() {
        return this.shipSize;
    }

    public int getShipQuantity() {
        return this.shipQuantity;
    }

    public String getShipName() {
        return this.shipName;
    }
}
