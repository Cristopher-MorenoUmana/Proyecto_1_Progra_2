package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Board {

    private Cell[][] cells; 
    
    private int difficulty;
    
    private int matrixSizeRow, matrixSizeColumn, boardType, currentShipIndex = 0;

    private int remainingShips = 10, placedShips = 0;
    
    public double boardWidth, boardHeight, firstCellPositionX, firstCellPositionY;

    public ShipData[] shipData = {ShipData.SUBMARINE, ShipData.DESTROYER,
        ShipData.CRUISER, ShipData.BATTLESHIP};

    private Text instructionText, currentShipNameText, placedShipsText, remainingShipsText;

    private boolean isShipVertical = true, isShipHorizontal = false;

    private boolean areShipsPlaced = false;

    private boolean isShowEnemyShipsActive = false, isShowIslansActive = false;
    
    private Button verticalShipButton, horizontalShipButton, showEnemyShipsButton;

    private Button showEnemyIslandsButton;
    
    private int shipsToPlace = this.shipData[this.currentShipIndex].getShipQuantity();

    private int[] subMatrixSizes = {0, 0, 0, 0};
    
    private int islansQuantity = 0;
    
    private List<Ship> shipsList = new ArrayList<>();
    
    public Board(int pDifficulty, double pFirstCellPositionX, double pFirstCellPositionY,
            int pBoardType) {

        this.difficulty = pDifficulty;

        this.firstCellPositionX = pFirstCellPositionX;
        this.firstCellPositionY = pFirstCellPositionY;
        this.boardType = pBoardType;

        defineTexts();
        fillBoard();
    }

    private void defineTexts() {

        this.instructionText = new Text(" ");
        this.instructionText.setLayoutX(0.0);
        this.instructionText.setLayoutY(0.0);
        this.instructionText.setFill(Color.WHITE);

        this.currentShipNameText = new Text(" ");
        this.currentShipNameText.setLayoutX(0.0);
        this.currentShipNameText.setLayoutY(0.0);
        this.currentShipNameText.setFill(Color.WHITE);

        this.placedShipsText = new Text(" ");
        this.placedShipsText.setLayoutX(0.0);
        this.placedShipsText.setLayoutY(0.0);
        this.placedShipsText.setFill(Color.WHITE);
        
        this.remainingShipsText = new Text("Barcos restantes: ");
        this.remainingShipsText.setLayoutX(0.0);
        this.remainingShipsText.setLayoutY(0.0);
        this.remainingShipsText.setFill(Color.WHITE);
        this.remainingShipsText.setDisable(true);
        this.remainingShipsText.setVisible(false);
    }

    private void fillBoard() {

        if (this.difficulty == 1) {

            this.matrixSizeRow = 12;
            this.matrixSizeColumn = 9;

            this.boardWidth = this.matrixSizeColumn * 30;
            this.boardHeight = this.matrixSizeRow * 30;

            this.cells = new Cell[matrixSizeRow][matrixSizeColumn];

            this.islansQuantity = 10;
            
            fillMatrix();
        }
        if (this.difficulty == 2) {

            this.matrixSizeRow = 12;
            this.matrixSizeColumn = 11;

            this.boardWidth = this.matrixSizeColumn * 30;
            this.boardHeight = this.matrixSizeRow * 30;

            this.cells = new Cell[matrixSizeRow][matrixSizeColumn];

            this.islansQuantity = 15;
            
            fillMatrix();
        }
        if (this.difficulty == 3) {

            this.matrixSizeRow = 12;
            this.matrixSizeColumn = 13;

            this.boardWidth = this.matrixSizeColumn * 30;
            this.boardHeight = this.matrixSizeRow * 30;

            this.cells = new Cell[matrixSizeRow][matrixSizeColumn];

            this.islansQuantity = 20;
            
            fillMatrix();
        }
    }

    private void fillMatrix() {

        final int CELLS_SPACING = 30;

        double auxPositionX = this.firstCellPositionX, auxPositionY = this.firstCellPositionY;

        for (int i = 0; i < this.matrixSizeRow; i++) {

            auxPositionX = this.firstCellPositionX;

            for (int j = 0; j < this.matrixSizeColumn; j++) {

                this.cells[i][j] = new Cell(auxPositionX, auxPositionY, this.boardType);

                auxPositionX += CELLS_SPACING;

                final int ROW = i, COLUMN = j;

                if (this.boardType == 1 && !this.areShipsPlaced) {

                    this.cells[i][j].getCellBox().setOnMouseClicked(event
                            -> manageShipsQuantity(ROW, COLUMN));

                    this.cells[i][j].getCellBox().setOnMouseEntered(e
                            -> this.cells[ROW][COLUMN].getCellBox().setCursor(Cursor.HAND));

                    this.cells[i][j].getCellBox().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                        shipPreview(ROW, COLUMN);
                    });

                    this.cells[i][j].getCellBox().setOnMouseExited(e
                            -> this.cells[ROW][COLUMN].getCellBox().setCursor(Cursor.DEFAULT));
                }

            }
            auxPositionY += CELLS_SPACING;
        }

    }

    //Formula: (2z+(x-y))/2 , z = primera celda, y = tamaño en pixeles de la figura x = ancho del tablero.
    public double boardXCenterText(Text pTextToCenter){
 
        return ((this.firstCellPositionX * 2
                + (this.boardWidth - pTextToCenter.getBoundsInLocal().getWidth())) / 2);
    }

    public double boardXCenterButton(Button pButtonToCenter) {

        return ((this.firstCellPositionX * 2
                + (this.boardWidth -  pButtonToCenter.getPrefWidth())) / 2);
    }

    public void drawBoardComponents(AnchorPane pBoardAnchorPane) {

        for (int i = 0; i < this.matrixSizeRow; i++) {

            for (int j = 0; j < this.matrixSizeColumn; j++) {

                this.cells[i][j].drawCell(pBoardAnchorPane);
            }
        }

        if (this.boardType == 1) {

            this.instructionText.setText("Haga clic en una celda para colocar los barcos: ");

            double instructionY = this.firstCellPositionY - 40;

            this.instructionText.setLayoutX(boardXCenterText(this.instructionText));
            this.instructionText.setLayoutY(instructionY);

            this.verticalShipButton = new Button("Vertical");

            this.horizontalShipButton = new Button("Horizontal");

            this.verticalShipButton.setPrefWidth(100);

            this.horizontalShipButton.setPrefWidth(100);

            double verticalShipY = this.firstCellPositionY + this.boardHeight + 100;

            this.verticalShipButton.setLayoutX(this.firstCellPositionX);
            this.verticalShipButton.setLayoutY(verticalShipY);
            this.verticalShipButton.setStyle("-fx-background-color: #66FFFF;");

            this.horizontalShipButton.setLayoutX(this.firstCellPositionX + this.boardWidth - 100);
            this.horizontalShipButton.setLayoutY(verticalShipY);

            pBoardAnchorPane.getChildren().add(this.instructionText);
            pBoardAnchorPane.getChildren().add(this.verticalShipButton);
            pBoardAnchorPane.getChildren().add(this.horizontalShipButton);

            this.verticalShipButton.setDisable(true);
            this.verticalShipButton.setVisible(false);

            this.horizontalShipButton.setDisable(true);
            this.horizontalShipButton.setVisible(false);

            this.horizontalShipButton.setOnAction(event -> horizontalButtonPressed());
            this.verticalShipButton.setOnAction(event -> verticalButtonPressed());

        }
        if(this.boardType == 2){
            
            this.showEnemyShipsButton = new Button("Mostrar Barcos");
            this.showEnemyShipsButton.setPrefWidth(100);
            
            this.showEnemyShipsButton.setLayoutX(this.firstCellPositionX);
            
            this.showEnemyShipsButton.setOnAction(e -> showEnemyShipsPressed());
            
            this.showEnemyIslandsButton = new Button("Mostrar Islas");
            this.showEnemyIslandsButton.setPrefWidth(100);
            
            this.showEnemyIslandsButton.setLayoutX(this.firstCellPositionX + this.boardWidth - 100);           
            this.showEnemyIslandsButton.setOnAction(e ->  showIslandsPressed());
            
            
            pBoardAnchorPane.getChildren().add(this.showEnemyShipsButton);
            pBoardAnchorPane.getChildren().add(this.showEnemyIslandsButton);
        }
        pBoardAnchorPane.getChildren().add(this.currentShipNameText);
        pBoardAnchorPane.getChildren().add(this.placedShipsText);
        pBoardAnchorPane.getChildren().add(this.remainingShipsText);
    }

    private void verticalButtonPressed() {

        this.isShipVertical = true;

        this.isShipHorizontal = false;
        
        this.horizontalShipButton.setStyle("-fx-background-color: #4DB3CC;");

        this.verticalShipButton.setStyle("-fx-background-color: #66FFFF;");
    }

    private void horizontalButtonPressed() {

        this.isShipHorizontal = true;

        this.isShipVertical = false;

        this.horizontalShipButton.setStyle("-fx-background-color: #66FFFF;");

        this.verticalShipButton.setStyle("-fx-background-color: #4DB3CC;");
    }

    private void showEnemyShipsPressed() {

        this.isShowEnemyShipsActive = !this.isShowEnemyShipsActive;
        
        final String ORANGE = "#FF6C00", TEAL = "#009999";

        for (int i = 0; i < this.matrixSizeRow; i++) {
            for (int j = 0; j < this.matrixSizeColumn; j++) {

                if (this.isShowEnemyShipsActive) {

                    if (this.cells[i][j].getCellState() == 2) {
                        this.cells[i][j].setCellColor(ORANGE);
                    }
                } else {

                    if (this.cells[i][j].getCellState() == 2) {
                        this.cells[i][j].setCellColor(TEAL);
                    }
                }
            }
        }
        
        if (this.isShowEnemyShipsActive) {
            this.showEnemyShipsButton.setStyle("-fx-background-color: #66FFFF;");
        } else {
            this.showEnemyShipsButton.setStyle("-fx-background-color: #4DB3CC;");
        }
    }

    private void showIslandsPressed(){
        
        this.isShowIslansActive = !this.isShowIslansActive;
        
        final String YELLOW = "#FFFF00", TEAL = "#009999";

        for (int i = 0; i < this.matrixSizeRow; i++) {
            for (int j = 0; j < this.matrixSizeColumn; j++) {

                if (this.isShowIslansActive) {

                    if (this.cells[i][j].getCellState() == 3) {
                        this.cells[i][j].setCellColor(YELLOW);
                    }
                } else {

                    if (this.cells[i][j].getCellState() == 3) {
                        this.cells[i][j].setCellColor(TEAL);
                    }
                }
            }
        }
        
        if (this.isShowIslansActive) {
            this.showEnemyIslandsButton.setStyle("-fx-background-color: #66FFFF;");
        } else {
            this.showEnemyIslandsButton.setStyle("-fx-background-color: #4DB3CC;");
        }
    }
            
    public void placedShipsData() {

        this.currentShipNameText.setText("Colocando barcos de tipo: " + this.shipData[this.currentShipIndex]);

        double shipNameY = this.firstCellPositionY + this.boardHeight + 40;

        this.currentShipNameText.setLayoutX(boardXCenterText(this.currentShipNameText));
        this.currentShipNameText.setLayoutY(shipNameY);

        this.placedShipsText.setText("Cantidad de barcos de tipo por colocar: "
                + this.shipsToPlace);

        double placedShipsY = this.firstCellPositionY + this.boardHeight + 70;

        this.placedShipsText.setLayoutX(boardXCenterText(this.placedShipsText));
        this.placedShipsText.setLayoutY(placedShipsY);
    }

    private void manageShipsQuantity(int row, int column) {

        this.shipsToPlace--;
        
        buildSubMatrix(row, column);
        
        if (!isSubMatrixEmpty()) {

            this.shipsToPlace++;
            return;
        }

        if (!placeShips(row, column)) {

            this.shipsToPlace++;
            return;
        }
        
        unsignEventSubMatrix();

        if (this.shipsToPlace == 0) {

            this.currentShipIndex++;

            if (this.currentShipIndex > 3) {
                
                this.verticalShipButton.setDisable(true);
                this.verticalShipButton.setVisible(false);

                this.horizontalShipButton.setDisable(true);
                this.horizontalShipButton.setVisible(false);

                this.areShipsPlaced = true;

                this.placedShipsText.setText("Todos los barcos colocados.");
                
                this.remainingShipsText.setLayoutX(boardXCenterText(this.remainingShipsText));
                this.remainingShipsText.setLayoutY(this.firstCellPositionY + this.boardHeight + 20);
                
                this.remainingShipsText.setDisable(false);
                this.remainingShipsText.setVisible(true);
                
                this.currentShipNameText.setDisable(true);
                this.currentShipNameText.setVisible(false);

                this.placedShipsText.setLayoutX(boardXCenterText(this.placedShipsText));

                this.instructionText.setDisable(true);
                this.instructionText.setVisible(false);
                
                unsignEventCompleteMatrix();

                return;
            }

            this.shipsToPlace = this.shipData[this.currentShipIndex].getShipQuantity();
        }

        if (this.currentShipIndex > 0 && this.currentShipIndex <= 3) {
            
            this.verticalShipButton.setDisable(false);
            this.verticalShipButton.setVisible(true);

            this.horizontalShipButton.setDisable(false);
            this.horizontalShipButton.setVisible(true);
        }

        placedShipsData();
    }

    public void placePCShips() {
        
        Random random = new Random();
        
        int randomRow;
        int randomColumn;
        int randomShipOrientation;
        
        while (!this.areShipsPlaced) {

            randomShipOrientation = random.nextInt(2);

            if (randomShipOrientation == 0) {
                this.isShipHorizontal = true;
                this.isShipVertical = false;
            } else {
                this.isShipVertical = true;
                this.isShipHorizontal = false;
            }

            randomRow = random.nextInt(this.matrixSizeRow);
            randomColumn = random.nextInt(this.matrixSizeColumn);

            buildSubMatrix(randomRow, randomColumn);


            if (isSubMatrixEmpty()) {
                 if(placeShips(randomRow, randomColumn)){
                     this.shipsToPlace--;
                 }
            } 

            if (this.shipsToPlace == 0) {

                this.currentShipIndex++;

                if (this.currentShipIndex > 3) {

                    this.areShipsPlaced = true;

                    double remainingShipsCenter = ((this.firstCellPositionX * 2
                            + (this.boardWidth - this.remainingShipsText.getBoundsInLocal().getWidth())) / 2);

                    this.remainingShipsText.setLayoutX(remainingShipsCenter);
                    this.remainingShipsText.setLayoutY(this.firstCellPositionY + this.boardHeight + 20);

                    this.remainingShipsText.setDisable(false);
                    this.remainingShipsText.setVisible(true);

                    break;
                }
                this.shipsToPlace = this.shipData[this.currentShipIndex].getShipQuantity();
            }
        }
    }
   
    public void placeIslands(){

        if (this.boardType == 1) {
            return;
        }

        while (this.islansQuantity != 0) {
            
            Random random = new Random();
            int randomRow = random.nextInt(this.matrixSizeRow);
            int randomColumn = random.nextInt(this.matrixSizeColumn);

            boolean isValidCell = (this.cells[randomRow][randomColumn].getCellState() == 0
                    || this.cells[randomRow][randomColumn].getCellState() == 1);

            if (isValidCell) {
                this.cells[randomRow][randomColumn].setCellState(3);
                this.islansQuantity--;
            }
        }
    }
    
    private void buildSubMatrix(int row, int column) {

        int shipSize = shipData[this.currentShipIndex].getShipSize();

        int start_i = row - 1;
        int start_j = column - 1;
        int end_i, end_j;

        if (this.isShipHorizontal) {

            end_i = row + 1;
            end_j = column + shipSize;
        } else {

            end_i = row + shipSize;
            end_j = column + 1;
        }

        if (this.currentShipIndex == 0) {

            end_i = row + 1;
            end_j = column + 1;
        }

        this.subMatrixSizes[0] = start_i;
        this.subMatrixSizes[1] = start_j;
        this.subMatrixSizes[2] = end_i;
        this.subMatrixSizes[3] = end_j;
    }

    private void unsignEventSubMatrix() {

        for (int i = this.subMatrixSizes[0]; i <= this.subMatrixSizes[2]; i++) {

            for (int j = this.subMatrixSizes[1]; j <= this.subMatrixSizes[3]; j++) {

                if (i >= 0 && i < this.matrixSizeRow && j >= 0 && j < this.matrixSizeColumn) {

                    this.cells[i][j].getCellBox().setOnMouseClicked(null);

                    if (this.cells[i][j].getCellState() == 0) {

                        this.cells[i][j].setCellColor("#48B2B2");
                        this.cells[i][j].setCellState(1);

                    } else if (this.cells[i][j].getCellState() == 2) {

                        this.cells[i][j].getCellBox().setOnMouseEntered(null);
                        this.cells[i][j].getCellBox().setOnMouseExited(null);
                    }
                }
            }
        }
    }

    private boolean isSubMatrixEmpty() {

        for (int i = this.subMatrixSizes[0]; i <= this.subMatrixSizes[2]; i++) {

            for (int j = this.subMatrixSizes[1]; j <= this.subMatrixSizes[3]; j++) {
                
                if (i >= 0 && i < this.matrixSizeRow && j >= 0 && j < this.matrixSizeColumn) {

                    if (this.cells[i][j].getCellState() == 2) {

                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    private boolean placeShips(int row, int column) {

        int shipSize = shipData[this.currentShipIndex].getShipSize();
        
        boolean isOutOfRange = (this.isShipHorizontal) && (column + shipSize > this.matrixSizeColumn)
                || (this.isShipVertical) && (row + shipSize > this.matrixSizeRow);

        if (isOutOfRange) {
            return false;
        }
        
        List<int[]> position = new ArrayList<>();
        
        Orientation shipOrientation = null;
        
        if(this.isShipHorizontal){
            shipOrientation = Orientation.HORIZONTAL;
        }else{
            shipOrientation = Orientation.VERTICAL;
        }
        
        for (int i = 0; i < shipSize; i++) {

            int currentRow = row;
            int currentColumn = column;

            if (this.isShipHorizontal) {
                currentColumn += i;
            } else {
                currentRow += i;
            }
      
            if (this.boardType == 1) {
                this.cells[currentRow][currentColumn].setCellColor("#99FF00");
            }

            this.cells[currentRow][currentColumn].setCellState(2);
            position.add(new int[]{currentRow, currentColumn});
        }
        
        Ship newShip = new Ship(this.shipData[this.currentShipIndex], position, shipOrientation);
        this.shipsList.add(newShip);
        this.placedShips++;
        
        return true;
    }

    private String getDefaultColorForState(int state) {

        final String green = "#99FF00", teal = "#009999", lightTeal = "#48B2B2";

        if (state == 0) {
            return teal;
        }
        if (state == 1) {
            return lightTeal;
        }
        if (state == 2) {
            return green;
        }
        return teal;
    }

    private void shipPreview(int row, int column) {
        
        if (this.areShipsPlaced) {
            return;
        }

        final String RED = "#FF0000";
        int shipSize = this.shipData[this.currentShipIndex].getShipSize();

        boolean isOutOfRange = (this.isShipHorizontal && (column + shipSize > this.matrixSizeColumn)) 
                || (this.isShipVertical && (row + shipSize > this.matrixSizeRow));

        if (isOutOfRange) {
            this.cells[row][column].setCellColor(RED);

            this.cells[row][column].getCellBox().setOnMouseExited(e -> {
                String previousColor = getDefaultColorForState(this.cells[row][column].getCellState());
                this.cells[row][column].setCellColor(previousColor);
            });
            return;
        }

        String[] originalColors = new String[shipSize];

        for (int i = 0; i < shipSize; i++) {
            
            int currentRow = row;
            int currentColumn = column;

            if (this.isShipHorizontal) {
                currentColumn += i;
            } else {
                currentRow += i;
            }

            originalColors[i] = this.cells[currentRow][currentColumn].getCellColor();

            if (this.cells[currentRow][currentColumn].getCellState() == 0) {
                
                this.cells[currentRow][currentColumn].setCellColor("#99FF00");
                
            } else {
                
                this.cells[currentRow][currentColumn].setCellColor(RED);
            }
        }

        this.cells[row][column].getCellBox().setOnMouseExited(e -> {
            
            for (int i = 0; i < shipSize; i++) {
                int currentRow = row;
                int currentCol = column;

                if (this.isShipHorizontal) {
                    currentCol += i;
                } else {
                    currentRow += i;
                }

                this.cells[currentRow][currentCol].setCellColor(originalColors[i]);
            }
        });
    }

    private void unsignEventCompleteMatrix() {

        for (int i = 0; i < this.matrixSizeRow; i++) {

            for (int j = 0; j < this.matrixSizeColumn; j++) {

                final int ROW = i, COLUMN = j;

                this.cells[i][j].getCellBox().setOnMouseClicked(null);

                this.cells[i][j].getCellBox().setOnMouseEntered(e
                        -> this.cells[ROW][COLUMN].getCellBox().setCursor(Cursor.DEFAULT));

                this.cells[i][j].getCellBox().setOnMouseExited(e
                        -> this.cells[ROW][COLUMN].getCellBox().setCursor(Cursor.DEFAULT));
            }
        }
    }
 
    public boolean getArePlayerShipsPlaced(){
        
        return this.areShipsPlaced;
    }
    
    public double getMatrixSizeRow() {

        return this.matrixSizeRow;
    }

    public double getMatrixSizeColumn() {

        return this.matrixSizeColumn;
    }

    public void setFirstCellPostionX(double pFirstCellpositionX) {

        this.firstCellPositionX = pFirstCellpositionX;
    }

    public void setFirstCellPostionY(double pFirstCellpositionY) {

        this.firstCellPositionY = pFirstCellpositionY;
    }

    public double getFirstCellPostionX() {

        return this.firstCellPositionX;
    }

    public double getFirstCellPostionY() {

        return this.firstCellPositionY;
    }
    
    public Cell getCell(int row, int column){
        return this.cells[row][column];
    }
    
    public Text getRemainingShipsText(){
        return this.remainingShipsText;
    }
    
    public double getBoardWidth(){
        return this.boardWidth;
    }
    
    public double getBoardHeight(){
        return this.boardHeight;
    }
    
    public int getRemainingShips(){
        return this.remainingShips;
    }
    
    public void setRemainingShips(int pRemainingShips){
        this.remainingShips = pRemainingShips;
    }
    
    public List<Ship> getShips(){
        return this.shipsList;
    } 

    public void setShips(List<Ship> pShips){
        this.shipsList = pShips;
    }
}
