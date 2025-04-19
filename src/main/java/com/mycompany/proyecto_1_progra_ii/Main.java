package com.mycompany.proyecto_1_progra_ii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    
    private static Scene scene;
    private static Stage stage;
    
    @Override
    public void start(Stage stage) throws IOException {
        
        Main.stage = stage;
        scene = new Scene(loadFXML("Menu"), 554, 500);
        
        scene.getStylesheets().add(getClass().getResource("/com.css/style.css").toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("Batalla naval");
        stage.show();
    }

    static void resizeWindow(double pWidth, double pHeight) throws IOException {

       if (stage != null) {
            stage.setWidth(pWidth);
            stage.setHeight(pHeight);
        }
    }

    static double getWindowWidth() {
        return stage.getWidth();
    }

    static double getWindowHeight() {
        return stage.getHeight();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Scene getScene() {
        return scene;
    }

}