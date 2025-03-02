package com.mycompany.proyecto_1_progra_ii;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Controller {
    
    @FXML
    private TextField playerNameField;

    @FXML
    private Button playButton;
    
    @FXML
    private RadioButton easyDifficulty, mediumDifficulty, hardDifficulty;
    
    boolean isDifficultySelected;
    
    @FXML
    private void handleDifficultyButtons(ActionEvent event) {
        if (easyDifficulty.isSelected()) {

            isDifficultySelected = true;

            mediumDifficulty.setDisable(true);
            hardDifficulty.setDisable(true);
            
        } else if (mediumDifficulty.isSelected()) {

            isDifficultySelected = true;

            easyDifficulty.setDisable(true);
            hardDifficulty.setDisable(true);
            
        } else if (hardDifficulty.isSelected()) {

            isDifficultySelected = true;

            easyDifficulty.setDisable(true);
            mediumDifficulty.setDisable(true);
            
        } else {
            isDifficultySelected = false;
            
            easyDifficulty.setDisable(false);
            mediumDifficulty.setDisable(false);
            hardDifficulty.setDisable(false);
        }
    }
    
    @FXML
    private void handlePlayButton(ActionEvent event) {
     
       boolean isValidData = (isDifficultySelected) &&
               (playerNameField.getText().isEmpty() == false);
       
        if (isValidData) {
            System.out.println("Nombre del jugador: " + playerNameField.getText());
        }
        else {
            System.out.println("No se ha ingresado algun dato.");
        }
    }

}