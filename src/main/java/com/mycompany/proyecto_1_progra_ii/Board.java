package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.text.Text;

public class Board extends Game {

    private Cell[][] cells;

    private int matrixSizeRow, matrixSizeColumn, boardType, currentShipIndex = 0;

    public double boardWidth, boardHeight, firstCellPositionX, firstCellPositionY;

    public ShipData[] shipData = {ShipData.SUBMARINE, ShipData.DESTROYER, 
        ShipData.CRUISER, ShipData.BATTLESHIP};
    
    private Text instructionText, currentShipNameText, placedShipsText;
    
    private boolean isShipVertical = false, isShipHorizontal = false;
    
    private boolean areShipsPlaced = false;
    
    private Button verticalShipButton, horizontalShipButton;
    
    private int shipsToPlace = this.shipData[this.currentShipIndex].getShipQuantity();
    private int shipsPlaced = 0;


    public Board(Game pGame, double pFirstCellPositionX, double pFirstCellPositionY,
            int pBoardType) {

        super(pGame.difficulty);

        this.firstCellPositionX = pFirstCellPositionX;
        this.firstCellPositionY = pFirstCellPositionY;
        this.boardType = pBoardType;

        defineTexts();
        fillBoard();
    }

    private void defineTexts(){
        
        this.instructionText = new Text(" ");
        this.instructionText.setLayoutX(0);
        this.instructionText.setLayoutY(0);
        this.instructionText.setFill(Color.WHITE);
        
        this.currentShipNameText = new Text(" ");
        this.currentShipNameText.setLayoutX(0);
        this.currentShipNameText.setLayoutY(0);
        this.currentShipNameText.setFill(Color.WHITE);
        
        this.placedShipsText = new Text(" ");
        this.placedShipsText.setLayoutX(0);
        this.placedShipsText.setLayoutY(0);
        this.placedShipsText.setFill(Color.WHITE);
    }
    
    private void fillBoard() {

        if (this.difficulty == 1) {

            this.matrixSizeRow = 9;
            this.matrixSizeColumn = 9;

            this.boardWidth = this.matrixSizeColumn * 30;
            this.boardHeight = this.matrixSizeRow * 30;

            this.cells = new Cell[matrixSizeRow][matrixSizeColumn];

            fillMatrix();
        }
        if (this.difficulty == 2) {

            this.matrixSizeRow = 12;
            this.matrixSizeColumn = 9;

            this.boardWidth = this.matrixSizeColumn * 30;
            this.boardHeight = this.matrixSizeRow * 30;

            this.cells = new Cell[matrixSizeRow][matrixSizeColumn];

            fillMatrix();
        }
        if (this.difficulty == 3) {

            this.matrixSizeRow = 12;
            this.matrixSizeColumn = 12;

            this.boardWidth = this.matrixSizeColumn * 30;
            this.boardHeight = this.matrixSizeRow * 30;

            this.cells = new Cell[matrixSizeRow][matrixSizeColumn];

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

                final int row = i, column = j;
                
                if (this.boardType == 1 && !this.areShipsPlaced) {

                    this.cells[i][j].getCellBox().setOnMouseClicked(event -> 
                            manageShipsQuantity(row, column));
                    this.cells[i][j].getCellBox().setOnMouseEntered(e
                            -> this.cells[row][column].getCellBox().setCursor(Cursor.HAND));
                    this.cells[i][j].getCellBox().setOnMouseExited(e
                            -> this.cells[row][column].getCellBox().setCursor(Cursor.DEFAULT));
                }
                
                if (this.boardType == 2) {

                    this.cells[i][j].getCellBox().setOnMouseClicked(envet
                            -> this.cells[row][column].getCellBox().setFill(Color.web("66FFFF")));
                    this.cells[i][j].getCellBox().setOnMouseEntered(e
                            -> this.cells[row][column].getCellBox().setCursor(Cursor.CROSSHAIR));
                    this.cells[i][j].getCellBox().setOnMouseExited(e
                            -> this.cells[row][column].getCellBox().setCursor(Cursor.DEFAULT));
                }
            }
            auxPositionY += CELLS_SPACING;
        }
       
    }
    
    public void drawBoardComponents(AnchorPane pBoardAnchorPane) {

        for (int i = 0; i < this.matrixSizeRow; i++) {

            for (int j = 0; j < this.matrixSizeColumn; j++) {

                this.cells[i][j].drawCell(pBoardAnchorPane);
            }
        }
        
        if (this.boardType == 1) {
            
            this.instructionText.setText("Haga clic en una celda para colocar los barcos: ");

            double instructionCenter = ((this.firstCellPositionX * 2
                    + (this.boardWidth - this.instructionText.getBoundsInLocal().getWidth())) / 2);
            double instructionY = this.firstCellPositionY - 40;

            this.instructionText.setLayoutX(instructionCenter);
            this.instructionText.setLayoutY(instructionY);

            this.verticalShipButton = new Button("Vertical");

            this.horizontalShipButton = new Button("Horizontal");
            
            this.verticalShipButton.setPrefWidth(100);
            
            this.horizontalShipButton.setPrefWidth(100);
            
            double verticalButtonCenter = (this.firstCellPositionX);
            
            double verticalShipY = this.firstCellPositionY + this.boardHeight + 100;
            
            this.verticalShipButton.setLayoutX(verticalButtonCenter);
            this.verticalShipButton.setLayoutY(verticalShipY);

            this.horizontalShipButton.setLayoutX(verticalButtonCenter + 170);
            this.horizontalShipButton.setLayoutY(verticalShipY);
            
            
            pBoardAnchorPane.getChildren().add(this.instructionText);
            pBoardAnchorPane.getChildren().add(this.verticalShipButton);
            pBoardAnchorPane.getChildren().add(this.horizontalShipButton);
            
            this.verticalShipButton.setDisable(true);
            this.verticalShipButton.setVisible(false);

            this.horizontalShipButton.setDisable(true);
            this.horizontalShipButton.setVisible(false);
            
            this.horizontalShipButton.setOnAction(event->horizontalButtonPressed());
            this.verticalShipButton.setOnAction(event->verticalButtonPressed());
            
        }
        pBoardAnchorPane.getChildren().add(this.currentShipNameText);
        pBoardAnchorPane.getChildren().add(this.placedShipsText);
  
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

    public void placedShipsData() {

        this.currentShipNameText.setText("Colocando barcos de tipo: " + this.shipData[this.currentShipIndex]);

        double shipNameCenter = ((this.firstCellPositionX * 2
                + (this.boardWidth - this.currentShipNameText.getBoundsInLocal().getWidth())) / 2);

        double shipNameY = this.firstCellPositionY + this.boardHeight + 40;

        this.currentShipNameText.setLayoutX(shipNameCenter);
        this.currentShipNameText.setLayoutY(shipNameY);

        this.placedShipsText.setText("Cantidad de barcos de tipo por colocar: "
                + this.shipsToPlace);

        double placedShipsCenter = ((this.firstCellPositionX * 2
                + (this.boardWidth - this.placedShipsText.getBoundsInLocal().getWidth())) / 2);

        double placedShipsY = this.firstCellPositionY + this.boardHeight + 70;

        this.placedShipsText.setLayoutX(placedShipsCenter);
        this.placedShipsText.setLayoutY(placedShipsY);
    }

    private void manageShipsQuantity(int row, int column) {

        this.cells[row][column].getCellBox().setFill(Color.web("99FF00"));
        this.cells[row][column].setCellState(2);

        this.shipsToPlace--;

        unsignEventPosition(row, column, shipData[this.currentShipIndex]);

        if (this.shipsToPlace == 0) {

            this.currentShipIndex++;

            if (this.currentShipIndex > 3) {
                this.verticalShipButton.setDisable(true);
                this.verticalShipButton.setVisible(false);

                this.horizontalShipButton.setDisable(true);
                this.horizontalShipButton.setVisible(false);

                this.areShipsPlaced = true;

                this.placedShipsText.setText("Todos los barcos colocados.");

                double placedShipsCenter = ((this.firstCellPositionX * 2
                        + (this.boardWidth - this.placedShipsText.getBoundsInLocal().getWidth())) / 2);

                this.currentShipNameText.setDisable(true);
                this.currentShipNameText.setVisible(false);

                this.placedShipsText.setLayoutX(placedShipsCenter);

                unsignEventMatrix();

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

    private void unsignEventPosition(int row, int column, ShipData shipData) {
        int shipSize = shipData.getShipSize();

        if (this.isShipHorizontal && (column + shipSize > this.matrixSizeColumn)) {
            return;
        }
        if (this.isShipVertical && (row + shipSize > this.matrixSizeRow)) {
            return;
        }
        
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

        for (int i = start_i; i <= end_i; i++) {

            for (int j = start_j; j <= end_j; j++) {

                if (i >= 0 && i < this.matrixSizeRow && j >= 0 && j < this.matrixSizeColumn) {

                    final int ROW = i;
                    final int COLUMN = j;

                    this.cells[i][j].getCellBox().setOnMouseClicked(null);

                    this.cells[i][j].getCellBox().setOnMouseEntered(e
                            -> this.cells[ROW][COLUMN].getCellBox().setCursor(Cursor.DEFAULT));

                    this.cells[i][j].getCellBox().setOnMouseExited(e
                            -> this.cells[ROW][COLUMN].getCellBox().setCursor(Cursor.DEFAULT));

                    this.cells[ROW][COLUMN].getCellBox().setFill(Color.web("rgba(128,128,128,0.3)"));

                    if (this.cells[ROW][COLUMN].getCellState() == 0) {

                        Color original = Color.web("#66FFFF");
                        Color slightlyDarker = original.deriveColor(0, 1, 0.70, 1);
                        this.cells[i][j].getCellBox().setFill(slightlyDarker);

                    } else if (this.cells[ROW][COLUMN].getCellState() == 2) {

                        this.cells[i][j].getCellBox().setFill(Color.web("#99FF00"));
                    }
                }
            }
        }

    if (this.isShipHorizontal) {
        
        for (int k = 0; k < shipSize; k++) {
            
            if (column + k < this.matrixSizeColumn) {
                this.cells[row][column + k].getCellBox().setFill(Color.web("#99FF00"));
                this.cells[row][column + k].setCellState(2);
            }
        }
    } else {
        for (int k = 0; k < shipSize; k++) {
            
            if (row + k < this.matrixSizeRow) {
                this.cells[row + k][column].getCellBox().setFill(Color.web("#99FF00"));
                this.cells[row + k][column].setCellState(2);
            }
        }
    }
}

    
    private void unsignEventMatrix() {

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
}
