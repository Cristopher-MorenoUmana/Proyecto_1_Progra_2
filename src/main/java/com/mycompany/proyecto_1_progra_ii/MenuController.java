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
    
    private Game game;
        
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

        if (!isValidData) {
            this.dataErrorLabel.setVisible(true);
            return;
        }

        this.dataErrorLabel.setVisible(false);

        disableMenuComponents();

        double windowWidth = 850, windowHeight = 600;

        if (this.difficultyNumber == 1) {

            windowWidth = 630;
            windowHeight = 600;
        }
        if (this.difficultyNumber == 2) {

            windowWidth = 750;
            windowHeight = 600;
        }
        if (this.difficultyNumber == 3) {

            windowWidth = 870;
            windowHeight = 600;
        }
        
        Main.resizeWindow(windowWidth, windowHeight);
        this.gameAnchorPane.setPrefWidth(windowWidth);
        this.gameAnchorPane.setPrefHeight(windowHeight);

        this.game = new Game(this.playerNameField.getText(), this.difficultyNumber,
                this.gameAnchorPane);

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
