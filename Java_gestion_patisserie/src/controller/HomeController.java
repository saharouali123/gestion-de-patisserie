/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sahar
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void venteButton(ActionEvent event) throws IOException {
        
        Node node = (Node)event.getSource();
         Stage stage = (Stage) node.getScene().getWindow();
         stage.close();
         Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Vente.fxml")));
         stage.setScene(scene);
         stage.setTitle("Gestion des vente");
         stage.show();
         
         
    }

    @FXML
    private void CmdButton(ActionEvent event) throws IOException {
        
         Node node = (Node)event.getSource();
         Stage stage = (Stage) node.getScene().getWindow();
         stage.close();
         Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Commande.fxml")));
         stage.setScene(scene);
         stage.setTitle("Gestion des commandes");
         stage.show();
         
         
         
    }
    
    
}
