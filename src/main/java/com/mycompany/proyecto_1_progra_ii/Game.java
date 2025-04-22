package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;
import javafx.scene.control.Button;
import java.util.Random;

public class Game {

    private Player player, pc;
    private String winner = null;
    private int difficulty;
    private int remaingPcShips = 10;
    private int remaingPlayerShips = 10;
    private Text brokenText, sunkenText, waterText, islandText, turnText;
    private final PauseTransition cellStateTextsTimer = new PauseTransition(Duration.seconds(0.5));
    private final PauseTransition turnTimer = new PauseTransition(Duration.seconds(1.5));
    private double shootProbability = 0.0;
    private int failedShootsCount = 0;
    private boolean isPlayerTurn = true;
    private int pcShootsQuantity = 0;
    private int playerShootsQuantity = 0;
    private Text playerShootsText, pcShootsText, winnerText;
    private boolean isPcTurnTimerRunning = false;
    private Button restartButton, exitButton;
    
    public Game(String pPlayerName, int pDifficulty, AnchorPane pGameAnchorPane) {

        this.difficulty = pDifficulty;

        Board playerBoard, pcBoard;

        defineTextsAndButtons();

        final double WINDOW_SPACING_X = 15, WINDOW_SPACING_Y = 60;

        playerBoard = new Board(this.difficulty, WINDOW_SPACING_X, WINDOW_SPACING_Y, 1);
        pcBoard = new Board(this.difficulty, pcBoardSpacing(), WINDOW_SPACING_Y, 2);

        this.player = new Player(pPlayerName, playerBoard);
        this.pc = new Player("PC", pcBoard);

        this.player.getBoard().drawBoardComponents(pGameAnchorPane);

        this.player.drawPlayerTexts(pGameAnchorPane);

        this.pc.getBoard().drawBoardComponents(pGameAnchorPane);

        this.pc.drawPlayerTexts(pGameAnchorPane);

        this.pc.getBoard().placePCShips();

        drawComponents(pGameAnchorPane);

        this.pc.getBoard().placeIslands();
    
        assignShootEvent(this.pc);
    }

    private void defineTextsAndButtons() {

        this.restartButton = new Button("Volver a jugar");        
        this.restartButton.setPrefWidth(100);
        this.restartButton.setLayoutX(0);
        this.restartButton.setLayoutY(0);
        this.restartButton.setVisible(false);
        this.restartButton.setDisable(true);        

        this.exitButton = new Button("Salir");
        this.exitButton.setPrefWidth(100);
        this.exitButton.setLayoutX(0);
        this.exitButton.setLayoutY(0);
        this.exitButton.setVisible(false);
        this.exitButton.setDisable(true);
        
        this.winnerText = new Text("");
        this.winnerText.setLayoutX(0.0);
        this.winnerText.setLayoutY(0.0);
        this.winnerText.setFill(Color.WHITE);
        this.winnerText.setDisable(true);
        this.winnerText.setVisible(false);
        this.winnerText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        this.playerShootsText = new Text("");
        this.playerShootsText.setLayoutX(0.0);
        this.playerShootsText.setLayoutY(0.0);
        this.playerShootsText.setFill(Color.WHITE);
        this.playerShootsText.setDisable(true);
        this.playerShootsText.setVisible(false);
        
        this.pcShootsText = new Text("");
        this.pcShootsText.setLayoutX(0.0);
        this.pcShootsText.setLayoutY(0.0);
        this.pcShootsText.setFill(Color.WHITE);
        this.pcShootsText.setDisable(true);
        this.pcShootsText.setVisible(false);

        this.turnText = new Text("");
        this.turnText.setLayoutX(0.0);
        this.turnText.setLayoutY(0.0);
        this.turnText.setFill(Color.WHITE);
        this.turnText.setDisable(true);
        this.turnText.setVisible(false);
        this.turnText.setFont(Font.font("Arial", FontWeight.BOLD, 30));

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
        this.sunkenText.setDisable(true);

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

    private void handleShootsQuantity() {
        
        this.playerShootsText.setLayoutX(this.player.getBoard().boardXCenterText(this.playerShootsText));
        this.playerShootsText.setLayoutY(this.player.getBoard().getBoardHeight() + 100);
        this.playerShootsText.setVisible(true);
        this.playerShootsText.setDisable(false);
        
        this.pcShootsText.setLayoutX(this.pc.getBoard().boardXCenterText(this.pcShootsText));
        this.pcShootsText.setLayoutY(this.pc.getBoard().getBoardHeight() + 100);
        this.pcShootsText.setVisible(true);
        this.pcShootsText.setDisable(false);
        
        this.playerShootsText.setText("Cantidad de tiros: " + this.playerShootsQuantity);
        
        this.pcShootsText.setText("Cantidad de tiros: " + this.pcShootsQuantity);
    }

    private void disableTextsAndButtons() {
        
        this.playerShootsText.setDisable(true);
        this.playerShootsText.setVisible(false);
        
        this.pcShootsText.setDisable(true);
        this.pcShootsText.setVisible(false);
        
        this.turnText.setDisable(true);
        this.turnText.setVisible(false);
        
        this.brokenText.setDisable(true);
        this.brokenText.setVisible(false);
        
        this.sunkenText.setVisible(false);
        this.sunkenText.setDisable(true);
        
        this.waterText.setDisable(true);
        this.waterText.setVisible(false);
        
        this.islandText.setDisable(true);
        this.islandText.setVisible(false);
    }
    
    private void drawComponents(AnchorPane pGameAnchorPane) {

        this.pc.getBoard().getRemainingShipsText().setText("Barcos Restantes: " + this.remaingPcShips);

        this.player.getBoard().getRemainingShipsText().setText("Barcos Restantes: " + this.remaingPlayerShips);

        this.pcShootsText.setText("Cantidad de tiros: " + this.pcShootsQuantity);
        
        this.playerShootsText.setText("Cantidad de tiros: " + this.playerShootsQuantity);
        
        pGameAnchorPane.getChildren().add(this.waterText);
        pGameAnchorPane.getChildren().add(this.brokenText);
        pGameAnchorPane.getChildren().add(this.sunkenText);
        pGameAnchorPane.getChildren().add(this.islandText);
        pGameAnchorPane.getChildren().add(this.turnText);
        pGameAnchorPane.getChildren().add(this.playerShootsText);
        pGameAnchorPane.getChildren().add(this.pcShootsText);
        pGameAnchorPane.getChildren().add(this.winnerText);
        pGameAnchorPane.getChildren().add(this.restartButton);
        pGameAnchorPane.getChildren().add(this.exitButton);

        this.restartButton.setOnAction(e -> restartButtonPressed());
        this.exitButton.setOnAction(e -> exitButtonPressed());
    }

    private void restartButtonPressed(){
        Main.resizeWindow(554, 500);
        Main.restartApp();
        this.restartButton.setDisable(true);
        this.restartButton.setVisible(false);
    }
    
    private void exitButtonPressed(){
        
        Main.closeApp();
        this.exitButton.setVisible(false);
        this.exitButton.setDisable(true);
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

                playerShoot(i, j);
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

    private void handleTurnText(Player pPlayer) {

        this.turnText.setText("Turno de: " + pPlayer.getPlayerName());

        double windowWidth = Main.getWindowWidth();
        double windowHeight = Main.getWindowHeight();

        double xCenter = (windowWidth - this.turnText.getBoundsInLocal().getWidth()) / 2;
        double yCenter = (windowHeight - this.turnText.getBoundsInLocal().getHeight()) / 2;

        this.turnText.setLayoutX(xCenter);
        this.turnText.setLayoutY(yCenter - 20);

        this.turnText.setVisible(true);
        this.turnText.setDisable(false);
    }
    
    private void playerShoot(int row, int column) {

        this.pc.getBoard().getCell(row, column).getCellBox().setOnMouseClicked(e -> {

            if (this.remaingPcShips == 0) {
                defineWinner();
                return;
            }

            if (!this.player.getBoard().getArePlayerShipsPlaced()) {
                return;
            }

            if (this.isPlayerTurn) {
          
              shootAtPc(row, column);
            }  
            
            if(!this.isPlayerTurn && !this.isPcTurnTimerRunning) {
                
                this.isPcTurnTimerRunning = true;
                
                handleTurnText(this.pc);
                this.turnTimer.setOnFinished(eh -> {
                    this.turnText.setDisable(true);
                    this.turnText.setVisible(false);
                    
                    this.isPcTurnTimerRunning = false;
                    pcShoot();

                });
                this.turnTimer.playFromStart();
                this.pc.getBoard().blockClicks(false);
            }
      
        });
    }

    private void shootAtPc(int row, int column) {

        double cellX = this.pc.getBoard().getCell(row, column).getCellBox().getX();
        double cellY = this.pc.getBoard().getCell(row, column).getCellBox().getY() + 15;

        boolean isWater = (this.pc.getBoard().getCell(row, column).getCellState() == 0);
        boolean isAShip = (this.pc.getBoard().getCell(row, column).getCellState() == 2);
        boolean isAnIsland = (this.pc.getBoard().getCell(row, column).getCellState() == 3);

        if (isWater) {

            handleCellStateTimer(this.waterText, cellX, cellY);

            this.pc.getBoard().getCell(row, column).setCellState(1);
            this.pc.getBoard().getCell(row, column).setCellColor("#66FFFF");

            blockCell(this.pc, row, column);

        }
        if (isAShip) {

            this.pc.getBoard().getCell(row, column).setCellState(4);
            this.pc.getBoard().getCell(row, column).setCellColor("#FF0000");

            if (isShipDestroyed(this.pc, row, column)) {

                this.remaingPcShips--;
                this.pc.getBoard().getRemainingShipsText().setText("Barcos Restantes: " + this.remaingPcShips);
                handleCellStateTimer(this.sunkenText, cellX, cellY);
            } else {
                handleCellStateTimer(this.brokenText, cellX, cellY);

            }

        }
        if (isAnIsland) {

            this.pc.getBoard().getCell(row, column).setCellState(1);
            this.pc.getBoard().getCell(row, column).setCellColor("#CC9600");

            shootAtPlayer();

            handleCellStateTimer(this.islandText, cellX, cellY);
            blockCell(this.pc, row, column);
        }
        this.isPlayerTurn = false;
        this.pc.getBoard().blockClicks(true);

        this.playerShootsQuantity++;
        handleShootsQuantity();
    }
    
    private void pcShoot() {
 
        this.isPlayerTurn = true;
        
        double probabilityIncrease = 0;

        if (this.difficulty == 1) {
            this.shootProbability = 0.3;
            probabilityIncrease = 0.20;
        }
        if (this.difficulty == 2) {
            this.shootProbability = 0.4;
            probabilityIncrease = 0.20;
        }
        if (this.difficulty == 3) {
            this.shootProbability = 0.5;
            probabilityIncrease = 0.2;
        }

        if (Math.random() < this.shootProbability) {

            shootAtPlayer();
            this.failedShootsCount = 0;
        } else {
            
            int[] randomPosition = randomPlayerWaterCell();

            if (randomPosition[0] != -1 && randomPosition[1] != -1) {
                this.player.getBoard().getCell(randomPosition[0], randomPosition[1]).setCellState(4);
                this.player.getBoard().getCell(randomPosition[0], randomPosition[1]).setCellColor("#66FFFF");

                this.failedShootsCount++;
                this.shootProbability += probabilityIncrease * this.failedShootsCount;
                this.shootProbability = Math.min(this.shootProbability, 1.0);
            }else{
                shootAtPlayer();
                this.failedShootsCount = 0;
            }
        }     
        this.pcShootsQuantity++;
        handleShootsQuantity();

        if(this.remaingPcShips == 0 && this.pcShootsQuantity == this.playerShootsQuantity){
            defineWinner();
        }
        
        if(this.remaingPlayerShips == 0){
            defineWinner();
        }
    }
    
    private int[] randomPlayerWaterCell(){
        
        Random random = new Random();
        
        int randomRow;
        int randomColumn;
        
        int[] position = new int[] {-1,-1};
        
        boolean isWaterCell = false;
        
        while(!isWaterCell){
            
            randomRow = random.nextInt(this.player.getBoard().getMatrixSizeRow());
            randomColumn = random.nextInt(this.player.getBoard().getMatrixSizeColumn());
            
            if(this.player.getBoard().getCell(randomRow, randomColumn).getCellState() == 0){
                position[0] = randomRow;
                position[1] = randomColumn;
                isWaterCell = true;
            }
        }
        double cellX = this.player.getBoard().getCell(position[0], position[1]).getCellBox().getX();
        double cellY = this.player.getBoard().getCell(position[0], position[1]).getCellBox().getY() + 15;

        handleCellStateTimer(this.waterText, cellX, cellY);
        
        return position;
    }
    
    private int[] randomPlayerShip() {

        Random random = new Random();

        int randomIndexShipList;
        int randomIndexPositionList;
        int[] position = new int[] {-1,-1};
        boolean isAFreeShip = false;

        while (!isAFreeShip) {

            randomIndexShipList = random.nextInt(10);

            int shipCellsQuantity
                    = this.player.getBoard().getShips().get(randomIndexShipList).getShipType().getShipSize();

            randomIndexPositionList = random.nextInt(shipCellsQuantity);

            List<int[]> positionList
                    = this.player.getBoard().getShips().get(randomIndexShipList).getPosition();

            position = positionList.get(randomIndexPositionList);

            if (this.player.getBoard().getCell(position[0], position[1]).getCellState() == 2) {
                isAFreeShip = true;
            }
        }
        return position;
    }
    
    private void shootAtPlayer() {

        final String RED = "#FF0000";

        int[] shipPosition = randomPlayerShip();

        this.player.getBoard().getCell(shipPosition[0], shipPosition[1]).setCellState(4);

        this.player.getBoard().getCell(shipPosition[0], shipPosition[1]).setCellColor(RED);

        double cellX = this.player.getBoard().getCell(shipPosition[0], shipPosition[1]).getCellBox().getX();
        double cellY = this.player.getBoard().getCell(shipPosition[0], shipPosition[1]).getCellBox().getY() + 15;
        
        if (isShipDestroyed(this.player, shipPosition[0], shipPosition[1])) {
            
            this.remaingPlayerShips--;

            this.player.getBoard().getRemainingShipsText().setText("Barcos Restantes: " + this.remaingPlayerShips);

            handleCellStateTimer(this.sunkenText, cellX, cellY);
            return;
        }

        handleCellStateTimer(this.brokenText, cellX, cellY);
    }
       
    private void handleButtons(){

       double windowWidth = Main.getWindowWidth();
       double windowHeight = Main.getWindowHeight();
       
       double xCenter = ((windowWidth - this.restartButton.getPrefWidth()) / 2 );
       double yCenter = ((windowHeight - this.restartButton.getPrefWidth()) / 2);

        this.restartButton.setLayoutX(this.player.getBoard().boardXCenterButton(this.restartButton));
        this.restartButton.setLayoutY(this.player.getBoard().getBoardHeight() + 100);

        this.exitButton.setLayoutX(this.pc.getBoard().boardXCenterButton(this.exitButton));
        this.exitButton.setLayoutY(this.pc.getBoard().getBoardHeight() + 100);

        this.restartButton.setVisible(true);
        this.restartButton.setDisable(false);

        this.exitButton.setVisible(true);
        this.exitButton.setDisable(false);
    }

    private void defineWinner(){
  
        boolean drawnGame = false;

        //this.remaingPlayerShips = 0; //comprobar si funciona el empate

        drawnGame = ((this.remaingPcShips == 0 && this.remaingPlayerShips == 0)
                && this.pcShootsQuantity == this.playerShootsQuantity);

        if (drawnGame) {

            this.winner = "Juego empatado";
            this.winnerText.setText(this.winner);
            this.winner = "Juego empatado";
            this.winnerText.setText(this.winner);
            
        } else if (this.remaingPcShips == 0) {

            if (!drawnGame) {
                this.winner = this.player.getPlayerName();
                this.winnerText.setText("El jugador: " + this.winner + " gana la partida");
            }

        } else if (this.remaingPlayerShips == 0) {

            this.winner = this.pc.getPlayerName();
            this.winnerText.setText("El jugador: " + this.winner + " gana la partida");
        }

        if (this.winner != null) {
            
            this.player.getBoard().disableBoardAndComponents();
            this.player.disablePlayerBoardText();

            this.pc.getBoard().disableBoardAndComponents();
            this.pc.disablePlayerBoardText();

            disableTextsAndButtons();

            double windowWidth = Main.getWindowWidth();

            double xCenter = (windowWidth - this.winnerText.getBoundsInLocal().getWidth()) / 2;

            this.winnerText.setLayoutX(xCenter);
            this.winnerText.setLayoutY(30);

            this.winnerText.setVisible(true);
            this.winnerText.setDisable(false);
            
            handleButtons();
        }
    }
    
    private boolean isShipDestroyed(Player pPlayer, int row, int column) {

        List<Ship> playerShips = pPlayer.getBoard().getShips();

        int brokenCellsCount = 0, shipIndex, cellIndex;

        List<int[]> shipCellsPositionList;

        Ship currentShip = playerShips.get(0);

        boolean isShipFound = false;

        pPlayer.getBoard().getCell(row, column).getCellBox().setOnMouseClicked(null);

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
                
                blockCell(pPlayer, coordinates[0], coordinates[1]);
            }
        }
    }
    
    private void blockCell(Player pPlayer, int row, int column){
        
        pPlayer.getBoard().getCell(row, column).setCellState(1);
        pPlayer.getBoard().getCell(row, column).getCellBox().setOnMouseClicked(null);
    }
}
