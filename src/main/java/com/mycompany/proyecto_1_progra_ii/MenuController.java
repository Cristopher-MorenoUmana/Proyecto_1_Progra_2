package com.mycompany.proyecto_1_progra_ii;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;


public class MenuController {

    @FXML 
    private Label dataErrorLabel;
    @FXML
    private Text titleText;
    
    @FXML
    private TextField playerNameField;

    @FXML
    private Button playButton;

    @FXML
    private RadioButton easyDifficulty, mediumDifficulty, hardDifficulty;
    
    @FXML
    private AnchorPane gameAnchorPane;
    
    @FXML
    private ButtonBar difficultyButtonBar;
    
    @FXML
    private Label nameInstruction;
    
    @FXML 
    private Label difficultyInstruction;
    
    private boolean isDifficultySelected;

    private int difficultyNumber = 0;

    private Player player1, player2;
    
    private Game game;

    private Board player1Board;
    
    private Board player2Board;
    
    private Text playerBoardText, pcBoardText;
    
    @FXML
    private void handleDifficultyButtons(ActionEvent event) {

        if (easyDifficulty.isSelected()) {

            this.isDifficultySelected = true;

            this.mediumDifficulty.setDisable(true);
            this.hardDifficulty.setDisable(true);

            this.difficultyNumber = 1;

        } else if (mediumDifficulty.isSelected()) {

            this.isDifficultySelected = true;

            this.easyDifficulty.setDisable(true);
            this.hardDifficulty.setDisable(true);

            this.difficultyNumber = 2;

        } else if (hardDifficulty.isSelected()) {

            this.isDifficultySelected = true;

            this.easyDifficulty.setDisable(true);
            this.mediumDifficulty.setDisable(true);

            this.difficultyNumber = 3;

        } else {
            this.isDifficultySelected = false;

            this.easyDifficulty.setDisable(false);
            this.mediumDifficulty.setDisable(false);
            this.hardDifficulty.setDisable(false);
        }
    }

    @FXML
    private void handlePlayButton(ActionEvent event) throws IOException {

        boolean isValidData = (isDifficultySelected)
                && (playerNameField.getText().isEmpty() == false);
       

        if (isValidData) {

            this.dataErrorLabel.setVisible(false);

            this.game = new Game(this.difficultyNumber);

            player1Board = new Board(game, 15, 60, 1);

            player2Board = new Board(game, 450, 60, 2);

            this.player1 = new Player(this.playerNameField.getText(), this.player1Board);

            this.player2 = new Player("PC", this.player2Board);

            disableMenuComponents();

            double windowWidth = 850, windowHeight = 600;
            
            Main.resizeWindow(windowWidth, windowHeight);
            this.gameAnchorPane.setPrefWidth(windowWidth);
            this.gameAnchorPane.setPrefHeight(windowHeight);
            
            this.playerBoardText = new Text("Tablero de: " + this.player1.getPlayerName());

            this.pcBoardText = new Text("Tablero de: " + this.player2.getPlayerName());

            this.playerBoardText.setFill(Color.WHITE);
            this.pcBoardText.setFill(Color.WHITE);
            
            this.gameAnchorPane.getChildren().add(playerBoardText);
            this.gameAnchorPane.getChildren().add(pcBoardText);

            player1Board.drawBoardComponents(gameAnchorPane);
            player2Board.drawBoardComponents(gameAnchorPane);

            player2Board.placePCShips();
            
            double board1Center = ((this.player1Board.firstCellPositionX * 2 +
                    (this.player1Board.boardWidth - this.playerBoardText.getBoundsInLocal().getWidth()))/2);
            
            double board2Center = ((this.player2Board.firstCellPositionX * 2 +
                    (this.player2Board.boardWidth - this.pcBoardText.getBoundsInLocal().getWidth()))/2);
            
            this.playerBoardText.setLayoutX(board1Center);
            this.pcBoardText.setLayoutX(board2Center);
            
 
            this.playerBoardText.setLayoutY(this.player1Board.getFirstCellPostionY() - 10);
            this.pcBoardText.setLayoutY(this.player2Board.getFirstCellPostionY() - 10);

                       

        } else {
            this.dataErrorLabel.setVisible(true);
        }
    }
    
    private void disableMenuComponents ()
    {
        this.playButton.setVisible(false);
        this.playButton.setDisable(true);
        
        this.difficultyButtonBar.setVisible(false);
        this.difficultyButtonBar.setDisable(true);
        
        this.playerNameField.setVisible(false);
        this.playerNameField.setDisable(true);
        
        this.nameInstruction.setVisible(false);
        this.nameInstruction.setDisable(true);
        
        this.difficultyInstruction.setVisible(false);
        this.difficultyInstruction.setDisable(true);
        
        this.titleText.setVisible(false);
        this.titleText.setDisable(true);
    }
}
