package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.layout.AnchorPane;

public class Game {

    private int gameDifficulty;
    private String playerName;
    private Cell[][] board;
    private int matrixSizeRow, matrixSizeColumn;
    
    public Game(int pGameDifficulty, String pPlayerName) {

        this.gameDifficulty = pGameDifficulty;
        this.playerName = pPlayerName;
        
        fillBoard();

    }

    private void fillBoard() {
        
        if (gameDifficulty == 1) {
            
            this.matrixSizeRow = 12;
            this.matrixSizeColumn = 9;
            
            this.board = new Cell[matrixSizeRow][matrixSizeColumn];
            
            fillMatrix();
        }
    }

    private void fillMatrix() {
        
        final int CELLS_SPACING = 30;
        int cellsPositionX, cellsPositionY = 40;
        
        for(int i = 0; i < this.matrixSizeRow - 1; i++){
            
            cellsPositionX = 15;
            
            for(int j = 0; j < this.matrixSizeColumn - 1; j++){
                
                this.board[i][j] = new Cell(cellsPositionX, cellsPositionY);
                this.board[i][j].setCellState(0);
                
                cellsPositionX += CELLS_SPACING;
            }
            cellsPositionY += CELLS_SPACING;
        }
    }
    
    public void drawBoard(AnchorPane pBoardAnchorPane) {

        for (int i = 0; i < this.matrixSizeRow - 1; i++) {

            for (int j = 0; j < this.matrixSizeColumn - 1; j++) {
                
                this.board[i][j].drawCell(pBoardAnchorPane);
            }
        }
    }
}
