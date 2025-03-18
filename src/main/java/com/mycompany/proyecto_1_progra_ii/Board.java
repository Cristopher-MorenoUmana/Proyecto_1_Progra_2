package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.layout.AnchorPane;

public class Board extends Game {

    private Cell[][] cells;
    private int matrixSizeRow, matrixSizeColumn, firstCellPositionX, 
            firstCellPositionY, boardType;
    
    public Board(Game pGame, int pFirstCellPositionX, int pFirstCellPositionY, 
            int pBoardType) {

        super(pGame.difficulty);
        
        this.firstCellPositionX = pFirstCellPositionX;
        this.firstCellPositionY = pFirstCellPositionY;
        this.boardType = pBoardType;    
        
        fillBoard();
    }

    private void fillBoard() {

        if (this.difficulty == 1) {

            this.matrixSizeRow = 12;
            this.matrixSizeColumn = 9;

            this.cells = new Cell[matrixSizeRow][matrixSizeColumn];
            
            fillMatrix();
        }else{ //FIX:THIS BLOCK IS TEMPORARY
            System.out.println("Proximamente");
            
            this.matrixSizeRow = 12;
            this.matrixSizeColumn = 9;

            this.cells = new Cell[matrixSizeRow][matrixSizeColumn];
            
            fillMatrix();
        }
    }
    
    private void fillMatrix() {

        final int CELLS_SPACING = 30;
        
        int auxPositionX = this.firstCellPositionX;
        
        for (int i = 0; i < this.matrixSizeRow - 1; i++) {
            
            this.firstCellPositionX = auxPositionX;
            
            for (int j = 0; j < this.matrixSizeColumn - 1; j++) {

                this.cells[i][j] = new Cell(this.firstCellPositionX,
                        this.firstCellPositionY, this.boardType);

                this.firstCellPositionX += CELLS_SPACING;
            }
            this.firstCellPositionY += CELLS_SPACING;
        }
    }

    public void drawBoard(AnchorPane pBoardAnchorPane) {

        for (int i = 0; i < this.matrixSizeRow - 1; i++) {

            for (int j = 0; j < this.matrixSizeColumn - 1; j++) {

                this.cells[i][j].drawCell(pBoardAnchorPane);
            }
        }
    }

    public double getMatrixSizeRow() {

        return this.matrixSizeRow;
    }

    public double getMatrixSizeColumn() {

        return this.matrixSizeColumn;
    }
    
    public void setFirstCellPostionX(int pFirstCellpositionX){
        
        this.firstCellPositionX = pFirstCellpositionX;
    }
    
     public void setFirstCellPostionY(int pFirstCellpositionY){
        
        this.firstCellPositionY = pFirstCellpositionY;
    }
}
