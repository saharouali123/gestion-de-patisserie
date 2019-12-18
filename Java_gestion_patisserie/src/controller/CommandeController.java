package controller;

import BDD.db_connection;
import static controller.AuthentificationController.infoBox;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CommandeController implements Initializable {

    @FXML
    private TextField txt_nomClient;
    @FXML
    private TextField txt_prix;
    @FXML
    private TextField txt_accompte;
    @FXML
    private TextField txt_reste;
    @FXML
    private TextArea txt_des;
    @FXML
    private TextField txt_date;

    ResultSet rs;
    db_connection db;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db=new db_connection();
            db.connexionDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }    

    @FXML
    private void Ajouter(ActionEvent event) throws SQLException {
        if (txt_nomClient.getText().equals("") || txt_accompte.getText().equals("") || 
                txt_prix.getText().equals("") ||txt_date.getText().equals("")
                ||txt_des.getText().equals("") || 
                txt_reste.getText().equals("")){
             
              infoBox("saisir tous les champs", null, "Failed");
              
        } else {
            String[] colon = {"nom_client", "designation", "date", "prix", "accompte", "reste"};
            String[] inf = {txt_nomClient.getText(), txt_des.getText(),
                            txt_date.getText(), String.valueOf(txt_prix.getText()),
                              String.valueOf(txt_accompte.getText()), 
                              String.valueOf(txt_reste.getText())};
           
            db.queryInsert("commande", colon, inf);
            infoBox("commande ajoutée", null, "succes");
            vider(event);
        }
        
    }

    @FXML
    private void Annuler(ActionEvent event) throws IOException {
        Node node = (Node)event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml")));
        stage.setScene(scene);
        stage.setTitle("Acceuil");
        stage.show();
        
    }

    @FXML
    private void vider(ActionEvent event) {
        txt_nomClient.clear();
        txt_des.clear();
        txt_date.clear();
        txt_prix.clear();
        txt_accompte.clear();
        txt_reste.clear();
        
        
        
    }

    @FXML
    private void Reste(KeyEvent event) {
        Double a = Double.parseDouble(txt_prix.getText());
        Double b = Double.parseDouble(txt_accompte.getText());
        
        txt_reste.setText(String.valueOf(a-b));
        
        
    }
    
}
