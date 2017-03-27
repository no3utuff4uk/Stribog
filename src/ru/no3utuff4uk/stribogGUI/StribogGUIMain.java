/*
 * Implementation of hash function Stribog (GOST R 34.11.2012)
 */
package ru.no3utuff4uk.stribogGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author no3utuff4uk
 */
public class StribogGUIMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent)loader.load(getClass().getResource("MainWindow.fxml"));
        
        
        
        primaryStage.setTitle("ГОСТ 34.11-2012");
        Scene mainScene = new Scene(root);
        
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
