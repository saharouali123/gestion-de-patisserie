/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import BDD.db_connection;
import static controller.AuthentificationController.infoBox;
import entities.Produit;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class GProduitsController implements  Initializable {

    @FXML
    private TextField txt_code_produit;
    @FXML
    private TextField txt_designation;
    @FXML
    private TextField txt_remise;
    @FXML
    private TextField txt_prix;
    @FXML
    private TextField txt_quantite;
    @FXML
    private TableView<Produit> tableview;
    @FXML
    private TableColumn<Produit, Integer > Colid;
    @FXML
    private TableColumn colCodeProduit;
    @FXML
    private TableColumn colDesignation;
    @FXML
    private TableColumn<Produit, Integer> colRemise;
    @FXML
    private TableColumn<Produit, Float> colprix;
    @FXML
    private TableColumn<Produit, Integer> colquantite;
    @FXML
    private ComboBox<String> combo_rech;
    @FXML
    private TextField txt_rech;

  
    ResultSet rs;
    db_connection db;
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            db=new db_connection();
            db.connexionDatabase();
            
            showAll();            
            combo_rech.getItems().addAll("code_Produit","designation","remise","quantite");
        } catch (SQLException ex) {
            Logger.getLogger(GProduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }    
public ObservableList<Produit> getList() throws SQLException{
    	ObservableList<Produit> List = FXCollections.observableArrayList();
    	
        rs = db.querySelectAll("produit");
    	
	Produit produit;
        
	while(rs.next()) {
		produit = 
                        new Produit(rs.getInt("id"),rs.getString("code_Produit"), 
                        rs.getString("designation"),rs.getInt("remise"),
                        rs.getFloat("prix"), rs.getInt("quantite"));
                        List.add(produit);
        }
		
                        
    	return List;
    }
       
    public void showAll() throws SQLException {
    	ObservableList<Produit> list = getList();
    	
    	Colid.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colCodeProduit.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colRemise.setCellValueFactory(new PropertyValueFactory<>("remise"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));   
    	tableview.setItems(list);
        
    }
    void actualiser() throws SQLException {
        showAll();
        txt_code_produit.clear();
        txt_designation.clear();
        txt_prix.clear();
        txt_quantite.clear();
        txt_remise.clear();
        txt_rech.clear();
        combo_rech.getSelectionModel().select("");        
       }

    
    

    
    @FXML
    private void buttonAjouter(ActionEvent event) throws SQLException {
 
        if (txt_code_produit.getText().equals("") || txt_designation.getText().equals("") || 
                txt_prix.getText().equals("") ||txt_remise.getText().equals("") || 
                txt_quantite.getText().equals("")){
             
              infoBox("saisir tous les champs", null, "Failed");
              
        } else {
            String[] colon = {"code_Produit","designation", "remise", "prix", "quantite"};
            String[] inf = {txt_code_produit.getText(), txt_designation.getText(),
                            txt_remise.getText(), txt_prix.getText(),
                             txt_quantite.getText()};
           
            db.queryInsert("produit", colon, inf);
            actualiser();
        }
         

    }

    @FXML
    private void buttonActualiser(ActionEvent event) throws SQLException {
        actualiser();
    }

    @FXML
    private void buttonSupprimer(ActionEvent event) throws SQLException {
          
        Produit produit = tableview.getSelectionModel().getSelectedItem();
        int id =produit.getId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("est ce que tu es sure que tu veux supprimer");
           
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
         db.queryDelete("produit", "id=" + Integer.toString(id));
         actualiser();
        }
    }

    
    
    @FXML
    private void buttonModifier(ActionEvent event) throws SQLException {
         if (txt_code_produit.getText().equals("") || txt_designation.getText().equals("") || 
                txt_prix.getText().equals("") ||txt_remise.getText().equals("") || 
                txt_quantite.getText().equals("")){
             
              infoBox("saisir tous les champs", null, "Failed");
              
        } else {
            String[] colon = {"code_Produit","designation", "remise", "prix", "quantite"};
            String[] inf = {txt_code_produit.getText(), txt_designation.getText(),
                            txt_remise.getText(), txt_prix.getText(),
                             txt_quantite.getText()};
           Produit produit = tableview.getSelectionModel().getSelectedItem();
       
           int id =produit.getId();
          
            db.queryUpdate("produit", colon, inf, "id='" + id + "'");
            actualiser();
            
        }
         
         
    }

    @FXML
    private void ButtonRechercher(ActionEvent event) throws SQLException {
         if (txt_rech.getText().equals("")) {
             infoBox("SVP entrer quelque chose", null, null);  
         }
         else {
            if (combo_rech.getSelectionModel().getSelectedItem().equals("code_Produit")) {
                rs = db.querySelectAll("produit", "code_Produit LIKE '%" + txt_rech.getText() + "%' ");
        
            }
        ObservableList<Produit> List = FXCollections.observableArrayList();
	Produit produit;
        
	while(rs.next()) {
		produit = 
                        new Produit(rs.getInt("id"),rs.getString("code_Produit"), 
                        rs.getString("designation"),rs.getInt("remise"),
                        rs.getFloat("prix"), rs.getInt("quantite")); 
                
                        List.add(produit);
        }
        tableview.setItems(List);
        
        }
        
         
         
    }

    @FXML
    private void Deconnexion(ActionEvent event) throws IOException {
         Node node = (Node)event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Authentification.fxml")));
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        
    }

    @FXML
    private void OnClick(MouseEvent event) {
        
        Produit produit = tableview.getSelectionModel().getSelectedItem();
        txt_code_produit.setText(produit.getCode());
        txt_designation.setText(produit.getDesignation());
        txt_prix.setText(Float.toString(produit.getPrix()));
        txt_quantite.setText(Integer. toString(produit.getQuantite()));
        txt_remise.setText(Integer. toString(produit.getRemise()));  
        
    }
    
}
