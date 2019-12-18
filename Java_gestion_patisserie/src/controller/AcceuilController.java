package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AcceuilController implements Initializable {

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void utilisateursButton(ActionEvent event) throws IOException {
        
          Node node = (Node)event.getSource();
          Stage stage = (Stage) node.getScene().getWindow();
          stage.close();
          Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/GUtilisateurs.fxml")));
          stage.setScene(scene);
          stage.setTitle("Gestion des utilisateurs");
          stage.show();
             
             
    }

    @FXML
    private void produitsButton(ActionEvent event) throws IOException {
         Node node = (Node)event.getSource();
         Stage stage = (Stage) node.getScene().getWindow();
         stage.close();
         Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/GProduits.fxml")));
         stage.setScene(scene);
         stage.setTitle("Gestion des produits");
         stage.show();
          
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
