package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;

public class Cell {

    private Rectangle cellBox;
    private int cellState;
    private String cellColor;
    
    public Cell(double pCellsPostionX, double pCellsPositionY, int pCellType){
        
        this.cellBox = new Rectangle(pCellsPostionX, pCellsPositionY, 30, 30);
        this.cellColor = "#009999";
        this.cellBox.setFill(Color.web(this.cellColor));
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
    
    public Rectangle getCellBox(){
        
        return this.cellBox;
    }
    
    public String getCellColor(){
        return this.cellColor;
    }
    
    public void drawCell(AnchorPane pCellAnchorPane){
        
        pCellAnchorPane.getChildren().add(this.cellBox);
    }
    
    public void setCellColor(String pColor){
        
        this.cellBox.setFill(Color.web(pColor));
        this.cellColor = pColor;
    } 
    
    public void setCellStrokeColor(String pColor) {

        this.cellColor = pColor;
        this.cellBox.setStroke(Color.web(this.cellColor));
    }
}
