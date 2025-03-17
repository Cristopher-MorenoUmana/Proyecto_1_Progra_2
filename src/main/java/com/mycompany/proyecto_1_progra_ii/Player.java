package com.mycompany.proyecto_1_progra_ii;

public class Player {
    
    private String name;
    private Board playerBoard;

    public Player(String pName, Board pBoard){
        
        this.name = pName;
        
        this.playerBoard = pBoard;
    }
    
    public Player(){
        
        this.name = " ";
    }
}
