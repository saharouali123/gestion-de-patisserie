/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import BDD.db_connection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * FXML Controller class
 *
 * @author sahar
 */
public class AuthentificationController implements Initializable {

    @FXML
    private Button BtnOk;
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextField userName;

    ResultSet rs;
    db_connection db;
    String username, password, type;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db=new db_connection();
            db.connexionDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
     @FXML
    private void Login(ActionEvent event) throws SQLException, IOException {
        rs = db.querySelectAll("utilisateur", "login='" + userName.getText() + "' and mdp='" + userPassword.getText() + "'");
         while (rs.next()) {
                username = rs.getString("login");
                password = rs.getString("mdp");
                type= rs.getString("type");
            }
        
         if (username == null && password == null) {
             infoBox("Please enter correct username and Password", null, "Failed");            
            }
         else {
             Node node = (Node)event.getSource();
             Stage stage = (Stage) node.getScene().getWindow();
             stage.close();
             Scene scene ;
             if(type.equals("admin"))
             {scene=new Scene(FXMLLoader.load(getClass().getResource("/view/GUtilisateurs.fxml")));
             stage.setScene(scene);}
             else if (type.equals("gestionnaire"))
             {scene=new Scene(FXMLLoader.load(getClass().getResource("/view/GProduits.fxml")));
             stage.setScene(scene);}
                     
             else if (type.equals("caissier"))
             {scene=new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml")));
             stage.setScene(scene);}
                

             
             stage.setTitle("Acceuil");
             stage.show();
                 
                
         }
   
        
    }
     
   public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

   
    
    
    
}
