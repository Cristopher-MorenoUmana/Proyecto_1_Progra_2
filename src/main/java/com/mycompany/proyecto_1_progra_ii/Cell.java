package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Cursor;
public class Cell {

    private Rectangle cellBox;
    private int cellState;
    
    
    public Cell(int pCellsPostionX, int pCellsPositionY, int pCellType){
        
        this.cellBox = new Rectangle(pCellsPostionX, pCellsPositionY, 30, 30);
        this.cellBox.setFill(Color.web("009999"));
        this.cellBox.setStroke(Color.BLACK);
        this.cellBox.setStrokeWidth(1);
        
        if(pCellType == 1){
            
            
        }
        
        if(pCellType == 2){
            
          this.cellBox.setOnMouseClicked(envet ->cellBox.setFill(Color.web("66FFFF")));
          this.cellBox.setOnMouseEntered(e ->cellBox.setCursor(Cursor.CROSSHAIR));
          this.cellBox.setOnMouseExited(e ->cellBox.setCursor(Cursor.DEFAULT));
        }
        
        this.cellState = 0;
    }
    
    public void setCellState(int pCellState){
        
        this.cellState = pCellState;
    }
    
    public int getCellState(){
        
        return this.cellState;
    }
    
    public Rectangle getCellBox(){
        
        return this.cellBox;
    }
    
    public void drawCell(AnchorPane pCellAnchorPane){
        
        pCellAnchorPane.getChildren().add(this.cellBox);
    }
    
    public void changeCellColor(String pColor){
        
        this.cellBox.setFill(Color.web(pColor));
    }   
}
