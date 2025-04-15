package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;

public class Game {

   private Player player, pc;
   private String winner;
   private int difficulty;
   
    public Game(String pPlayerName, int pDifficulty, AnchorPane pGameAnchorPane) {

        this.difficulty = pDifficulty;

        Board playerBoard, pcBoard;

        playerBoard = new Board(this.difficulty, 15, 60, 1);
        pcBoard = new Board(this.difficulty, 450, 60, 2);

        this.player = new Player(pPlayerName, playerBoard);
        this.pc = new Player("PC", pcBoard);

        assignShootEvent(this.pc);

        this.player.getBoard().drawBoardComponents(pGameAnchorPane);
        System.out.println("Tablero del jugador dibujado con exito.");
        this.player.drawPlayerTexts(pGameAnchorPane);
        System.out.println("Letreros del jugador dibujados con exito.");

        this.pc.getBoard().drawBoardComponents(pGameAnchorPane);
        System.out.println("Tablero de pc dibujado con exito.");
        this.pc.drawPlayerTexts(pGameAnchorPane);
        System.out.println("Letreros de pc dibujados con exito.");

        this.pc.getBoard().placePCShips();
        System.out.println("Todos los barcos de la PC colocados con exito.");
    }

   private void assignShootEvent(Player pPlayer) {

        for (int i = 0; i < pPlayer.getBoard().getMatrixSizeRow(); i++) {
            for (int j = 0; j < pPlayer.getBoard().getMatrixSizeColumn(); j++) {

                shoot(i, j);
            }
        }
    }
   
    private void shoot(int row, int column) {

        String color;

        boolean isCellFree = (this.pc.getBoard().getCell(row, column).getCellState() == 0);
        boolean isAShip = (this.pc.getBoard().getCell(row, column).getCellState() == 2);
        boolean isAnIsland = (this.pc.getBoard().getCell(row, column).getCellState() == 3);
        
        if (isCellFree) {

            color = "#66FFFF";
            this.pc.getBoard().getCell(row, column).setCellState(1);
        } else if (isAShip) {

            color = "#FF0000";
            this.pc.getBoard().getCell(row, column).setCellState(1);
        } else if (isAnIsland) {

            color = "#CC9600";
            this.pc.getBoard().getCell(row, column).setCellState(1);
        } else {
            color = "#009999";
        }

        this.pc.getBoard().getCell(row, column).getCellBox().setOnMouseClicked(e -> {

            if (!this.player.getBoard().getArePlayerShipsPlaced()) {
                return;
            }
            this.pc.getBoard().getCell(row, column).setCellColor(color);

        });

        this.pc.getBoard().getCell(row, column).getCellBox().setOnMouseEntered(e -> {
            if (!this.player.getBoard().getArePlayerShipsPlaced()) {
                return;
            }
            this.pc.getBoard().getCell(row, column).getCellBox().setCursor(Cursor.CROSSHAIR);
        });

        this.pc.getBoard().getCell(row, column).getCellBox().setOnMouseExited(e
                -> this.pc.getBoard().getCell(row, column).getCellBox().setCursor(Cursor.DEFAULT)
        );

    }

    public String getWinner() {
        return this.winner;
    }
}
