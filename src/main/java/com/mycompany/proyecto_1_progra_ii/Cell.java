package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;

public class Cell {

    private Rectangle cellBox;
    private int cellState;
  
    public Cell(int pCellsPostionX, int pCellsPositionY){
        
        this.cellBox = new Rectangle(pCellsPostionX, pCellsPositionY, 30, 30);
        this.cellBox.setFill(Color.web("93f5ae"));
        this.cellBox.setStroke(Color.BLACK);
        this.cellBox.setStrokeWidth(1);
        
        this.cellState = 0;
    }
    
    public void setCellState(int pCellState){
        
        this.cellState = pCellState;
    }
    
    public int getCellState(){
        
        return this.cellState;
    }
    
    public boolean isCellClicked(){
        
        return this.cellBox.isPressed();
    }
    
    public void drawCell(AnchorPane pCellAnchorPane){
        
        pCellAnchorPane.getChildren().add(this.cellBox);
    }
}
