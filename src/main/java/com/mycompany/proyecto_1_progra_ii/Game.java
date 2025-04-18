package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;

public class Game {

    private Player player, pc;
    private String winner;
    private int difficulty;
    private int remaingPcShips = 10;
    private int remaingPlayerShips = 10;
    private Text brokenText, sunkenText, waterText, islandText;
    private final PauseTransition cellStateTextsTimer = new PauseTransition(Duration.seconds(0.5));
    private int currentRow, currentColumn;

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

        this.brokenText = new Text("¡Averiado!");
        this.brokenText.setLayoutX(0.0);
        this.brokenText.setLayoutY(0.0);
        this.brokenText.setFill(Color.BLACK);
        this.brokenText.setDisable(true);
        this.brokenText.setVisible(false);

        this.sunkenText = new Text("¡Hundido!");
        this.sunkenText.setLayoutX(0.0);
        this.sunkenText.setLayoutY(0.0);
        this.sunkenText.setFill(Color.BLACK);
        this.sunkenText.setVisible(false);

        this.waterText = new Text("¡Agua!");
        this.waterText.setLayoutX(0.0);
        this.waterText.setLayoutY(0.0);
        this.waterText.setFill(Color.BLACK);
        this.waterText.setDisable(true);
        this.waterText.setVisible(false);

        this.islandText = new Text("¡Isla!");
        this.islandText.setLayoutX(0.0);
        this.islandText.setLayoutY(0.0);
        this.islandText.setFill(Color.BLACK);
        this.islandText.setDisable(true);
        this.islandText.setVisible(false);
    }

    private void drawTexts(AnchorPane pGameAnchorPane) {

        this.pc.getBoard().getRemainingShipsText().setText("Barcos Restantes: " + this.remaingPcShips);

        this.player.getBoard().getRemainingShipsText().setText("Barcos Restantes: " + this.remaingPlayerShips);

        pGameAnchorPane.getChildren().add(this.waterText);
        pGameAnchorPane.getChildren().add(this.brokenText);
        pGameAnchorPane.getChildren().add(this.sunkenText);
        pGameAnchorPane.getChildren().add(this.islandText);
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

                playerShoot(i,j);
            }
        }
    }

    private void handleCellStateTimer(Text pText, double pCellX, double pCellY) {

        pText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        pCellX += (30 - pText.getBoundsInLocal().getWidth()) / 2;
        pCellY += (30 - pText.getBoundsInLocal().getHeight()) / 2 - 4;

        pText.setLayoutX(pCellX);
        pText.setLayoutY(pCellY);

        pText.setVisible(true);
        pText.setDisable(false);

        this.cellStateTextsTimer.setOnFinished(e -> {
            pText.setVisible(false);
            pText.setDisable(true);
        });

        this.cellStateTextsTimer.playFromStart();
    }

    private void playerShoot(int row, int column) {

        this.pc.getBoard().getCell(row, column).getCellBox().setOnMouseClicked(e -> {

            if (!this.player.getBoard().getArePlayerShipsPlaced()) {
                return;
            }
 
            double cellX = this.pc.getBoard().getCell(row, column).getCellBox().getX();
            double cellY = this.pc.getBoard().getCell(row, column).getCellBox().getY() + 15;

            boolean isWater = (this.pc.getBoard().getCell(row, column).getCellState() == 0);
            boolean isAShip = (this.pc.getBoard().getCell(row, column).getCellState() == 2);
            boolean isAnIsland = (this.pc.getBoard().getCell(row, column).getCellState() == 3);

            if (isWater) {

                handleCellStateTimer(this.waterText, cellX, cellY);

                this.pc.getBoard().getCell(row, column).setCellState(1);
                this.pc.getBoard().getCell(row, column).setCellColor("#66FFFF");

            } else if (isAShip) {

                this.pc.getBoard().getCell(row, column).setCellState(4);
                this.pc.getBoard().getCell(row, column).setCellColor("#FF0000");
                
                if (isShipDestroyed(this.pc, row, column)) {

                    this.remaingPcShips--;
                    this.pc.getBoard().getRemainingShipsText().setText("Barcos Restantes: " + this.remaingPcShips);
                    handleCellStateTimer(this.sunkenText, cellX, cellY);
                }else{
                    handleCellStateTimer(this.brokenText, cellX, cellY);
                }

            } else if (isAnIsland) {

                handleCellStateTimer(this.islandText, cellX, cellY);

                this.pc.getBoard().getCell(row, column).setCellState(1);
                this.pc.getBoard().getCell(row, column).setCellColor("#CC9600");
                counterAttack();
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

    private void counterAttack() {

        final String RED = "#FF0000";

        for (int i = 0; i < this.player.getBoard().getMatrixSizeRow(); i++) {
            for (int j = 0; j < this.player.getBoard().getMatrixSizeColumn(); j++) {

                if (this.player.getBoard().getCell(i, j).getCellState() == 2) {

                    this.player.getBoard().getCell(i, j).setCellState(4);

                    this.player.getBoard().getCell(i, j).setCellColor(RED);
                    
                    return;
                }
            }
        }
    }

    private boolean isShipDestroyed(Player pPlayer, int row, int column) {

        List<Ship> playerShips = pPlayer.getBoard().getShips();

        int brokenCellsCount = 0, shipIndex, cellIndex;

        List<int[]> shipCellsPositionList;

        Ship currentShip = playerShips.get(0);

        boolean isShipFound = false;

        for (shipIndex = 0; shipIndex < playerShips.size(); shipIndex++) {

            currentShip = playerShips.get(shipIndex);

            shipCellsPositionList = currentShip.getPosition();

            for (cellIndex = 0; cellIndex < shipCellsPositionList.size(); cellIndex++) {

                int[] cellCoordinates = shipCellsPositionList.get(cellIndex);

                if (cellCoordinates[0] == row && cellCoordinates[1] == column) {
  
                    isShipFound = true;
                    break;
                }
            }
            if (isShipFound) {
                break;
            }
        }

        int currentShipSize = currentShip.getShipType().getShipSize();

        List<int[]> currentShipPositionList = currentShip.getPosition();

        for (int i = 0; i < currentShipSize; i++) {

            int[] coordinates = currentShipPositionList.get(i);

            if (pPlayer.getBoard().getCell(coordinates[0], coordinates[1]).getCellState() == 4) {

                brokenCellsCount++;  
            }else{
                return false;
            }
        }

        if (brokenCellsCount == currentShipSize) {
            
            blockShipsDestroyed(currentShipPositionList, currentShipSize, pPlayer);
            
            return true;
        }
        return false;

    }

    private void blockShipsDestroyed(List<int[]> pCurrentShipPositionList, int pCurrentShipSize, Player pPlayer){
        
        for (int i = 0; i < pCurrentShipSize; i++) {

            int[] coordinates = pCurrentShipPositionList.get(i);

            if (pPlayer.getBoard().getCell(coordinates[0], coordinates[1]).getCellState() == 4) {

                pPlayer.getBoard().getCell(coordinates[0], coordinates[1]).setCellState(1);
            }
        }
    }
    
    public String getWinner() {
        return this.winner;
    }
}
