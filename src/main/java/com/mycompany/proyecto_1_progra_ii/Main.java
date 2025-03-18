package com.mycompany.proyecto_1_progra_ii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    
    private static Scene scene;

    

    @Override
    public void start(Stage stage) throws IOException {
        
        scene = new Scene(loadFXML("Menu"), 554, 500);
        
        scene.getStylesheets().add(getClass().getResource("/com.css/style.css").toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("Batalla naval");
        stage.show();
    }

    static void resizeWindow(double pWidth, double pHeight) throws IOException {

        Stage stage = (Stage) scene.getWindow();
        stage.setWidth(pWidth);
        stage.setHeight(pHeight);
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}