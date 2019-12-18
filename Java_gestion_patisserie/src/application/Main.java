/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author sahar
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        
     Parent parent = (Parent) FXMLLoader.load(getClass().getResource(
                "/view/Authentification.fxml"));
     
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/view/style.css").toExternalForm());
        stage.setTitle("Login");
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
