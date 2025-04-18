package com.mycompany.proyecto_1_progra_ii;
import java.util.List;

public class Ship {

    private Orientation shipOrientation; 
    private ShipData shipType;
    private List<int[]>position;
    
    public Ship(ShipData pShipType, List<int[]> pPosition, Orientation pOrientation) {

        this.shipOrientation = pOrientation;
        this.shipType = pShipType;
        this.position = pPosition;
    }

    public Orientation getOrientation() {
        return this.shipOrientation;
    }
    
    public ShipData getShipType(){
        return this.shipType;
    }
    
    public List<int[]> getPosition(){
        
        return this.position;   
    }
    
}
