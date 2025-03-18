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

public class MenuController {

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
    
    private Label playerBoardLabel, pcBoardLabel;
    
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

            this.game = new Game(this.difficultyNumber);
            
            player1Board = new Board(game, 15,60,1);
            
            player2Board = new Board(game,270,60,2);
            
            this.player1 = new Player(this.playerNameField.getText(), this.player1Board);
            
            this.player2= new Player("PC", this.player2Board);
            
            disableMenuComponents();
            
            Main.resizeWindow(540, 440);
            
            this.playerBoardLabel = new Label("Tablero de: " + this.player1.getPlayerName());
            this.pcBoardLabel = new Label("Tablero de: " + this.player2.getPlayerName());
            
            this.playerBoardLabel.setLayoutX(102);
            this.playerBoardLabel.setLayoutY(30);
            
            this.pcBoardLabel.setLayoutX(358);
            this.pcBoardLabel.setLayoutY(30);
            
            this.gameAnchorPane.getChildren().add(playerBoardLabel);
            this.gameAnchorPane.getChildren().add(pcBoardLabel);
            
            player1Board.drawBoard(gameAnchorPane);
            player2Board.drawBoard(gameAnchorPane);
            
        } else {
            System.out.println("No se ha ingresado algun dato.");
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
    }
}
