package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.layout.AnchorPane;

public class Board extends Game {

    private Cell[][] cells;
    private int matrixSizeRow, matrixSizeColumn;

    public Board(Game pGame) {

        super(pGame.difficulty);

        fillBoard();
    }

    private void fillBoard() {

        if (this.difficulty == 1) {

            this.matrixSizeRow = 12;
            this.matrixSizeColumn = 9;

            this.cells = new Cell[matrixSizeRow][matrixSizeColumn];

            fillMatrix();
        }
    }

    private void fillMatrix() {

        final int CELLS_SPACING = 30;
        int cellsPositionX, cellsPositionY = 40;

        for (int i = 0; i < this.matrixSizeRow - 1; i++) {

            cellsPositionX = 15;

            for (int j = 0; j < this.matrixSizeColumn - 1; j++) {

                this.cells[i][j] = new Cell(cellsPositionX, cellsPositionY);
                this.cells[i][j].setCellState(0);

                cellsPositionX += CELLS_SPACING;
            }
            cellsPositionY += CELLS_SPACING;
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
}
