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
        
       handleWindow();
    }
    
    static void handleWindow() throws IOException {

        scene = new Scene(loadFXML("Menu"), 554, 500);

        stage.setScene(scene);
        stage.setTitle("Batalla naval");
        stage.show();

    }

    public static void restartApp() {
        
        try {
            handleWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static void resizeWindow(double pWidth, double pHeight){

       if (stage != null) {
            stage.setWidth(pWidth);
            stage.setHeight(pHeight);
        }
    }

    static double getWindowWidth() {
        return stage.getWidth();
    }

    public static void closeApp(){
        if(stage != null){
            stage.close();
        }
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