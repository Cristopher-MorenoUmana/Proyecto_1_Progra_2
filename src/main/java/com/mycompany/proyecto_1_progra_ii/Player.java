package com.mycompany.proyecto_1_progra_ii;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;

public class Player {
    
    private String name;
    private Board playerBoard;
    private Text playerBoardText;   
    
    public Player(String pName, Board pBoard){
        
        this.name = pName;
        
        this.playerBoard = pBoard;
    }
    
    public void drawPlayerTexts(AnchorPane pGameAnchorPane) {
        
        this.playerBoardText = new Text("Tablero de: " + this.name);

        this.playerBoardText.setFill(Color.WHITE);

        double board1Center = ((this.playerBoard.firstCellPositionX * 2
                + (this.playerBoard.boardWidth - this.playerBoardText.getBoundsInLocal().getWidth())) / 2);

        this.playerBoardText.setLayoutX(board1Center);

        this.playerBoardText.setLayoutY(this.playerBoard.getFirstCellPostionY() - 10);

        pGameAnchorPane.getChildren().add(this.playerBoardText);
    }
    
    public void disablePlayerBoardText(){
        this.playerBoardText.setVisible(false);
        this.playerBoardText.setDisable(true);
    }
    
    public String getPlayerName(){
        
        return this.name;
    }
    public Board getBoard(){
        
        return this.playerBoard;
    }
}
