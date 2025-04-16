package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Game {

   private Player player, pc;
   private String winner;
   private int difficulty;
   private int remaingPcShips = 10;
   private int remaingPlayerShips = 10;
   
    public Game(String pPlayerName, int pDifficulty, AnchorPane pGameAnchorPane) {

        this.difficulty = pDifficulty;

        Board playerBoard, pcBoard;

        defineTexts();
        
        final double WINDOW_SPACING_X = 15, WINDOW_SPACING_Y = 60;
        
        playerBoard = new Board(this.difficulty, WINDOW_SPACING_X, WINDOW_SPACING_Y, 1);
        pcBoard = new Board(this.difficulty, pcBoardSpacing(), WINDOW_SPACING_Y, 2);
        
        this.player = new Player(pPlayerName, playerBoard);
        this.pc = new Player("PC", pcBoard);

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
        
        drawTexts(pGameAnchorPane);
        System.out.println("Letreros del juego dibujados con exito.");
        
        this.pc.getBoard().placeIslands();
        System.out.println("Islas colocadas con exito.");
        assignShootEvent(this.pc);
    }

    private void defineTexts() {
        /*
        this.remainingShipsText = new Text(" ");
        this.remainingShipsText.setLayoutX(0);
        this.remainingShipsText.setLayoutY(0);
        this.remainingShipsText.setFill(Color.WHITE);
        this.remainingShipsText.setDisable(true);
        this.remainingShipsText.setVisible(false);
        */
    }
    
    private void drawTexts(AnchorPane pGameAnchorPane){
        
        final String INITIAL_MESSAGE = "Barcos restantes: ";
        String remainingShipsString = INITIAL_MESSAGE;
        remainingShipsString += this.remaingPcShips;
        
        this.pc.getBoard().getRemainingShipsText().setText(remainingShipsString);
        
        remainingShipsString = INITIAL_MESSAGE;
        remainingShipsString += this.remaingPlayerShips;
        
        this.player.getBoard().getRemainingShipsText().setText(remainingShipsString);
        
        //pGameAnchorPane.getChildren().add(this.remainingShipsText);
    }
    
    private double pcBoardSpacing() {

        double spacing = 0;

        if (this.difficulty == 1) {

            spacing = 330;

        }
        if (this.difficulty == 2) {

            spacing = 390;
        }
        if (this.difficulty == 3) {

            spacing = 450;
        }
        return spacing;
    }
    
   private void assignShootEvent(Player pPlayer) {

        for (int i = 0; i < pPlayer.getBoard().getMatrixSizeRow(); i++) {
            for (int j = 0; j < pPlayer.getBoard().getMatrixSizeColumn(); j++) {

                shoot(i, j);
            }
        }
    }
   
    private void shoot(int row, int column) {

        boolean isCellFree = (this.pc.getBoard().getCell(row, column).getCellState() == 0);
        boolean isAShip = (this.pc.getBoard().getCell(row, column).getCellState() == 2);
        boolean isAnIsland = (this.pc.getBoard().getCell(row, column).getCellState() == 3);

        this.pc.getBoard().getCell(row, column).getCellBox().setOnMouseClicked(e -> {

            if (!this.player.getBoard().getArePlayerShipsPlaced()) {
                return;
            }
            if (isCellFree) {

                this.pc.getBoard().getCell(row, column).setCellState(1);
                this.pc.getBoard().getCell(row, column).setCellColor("#66FFFF");
            } else if (isAShip) {

                this.pc.getBoard().getCell(row, column).setCellState(1);
                this.pc.getBoard().getCell(row, column).setCellColor("#FF0000");
            } else if (isAnIsland) {

                this.pc.getBoard().getCell(row, column).setCellState(1);
                this.pc.getBoard().getCell(row, column).setCellColor("#CC9600");
            } else {
                this.pc.getBoard().getCell(row, column).setCellColor("#009999");
            }

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
