package controller;

import BDD.db_connection;
import static controller.AuthentificationController.infoBox;
import entities.Utilisateur;
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


public class GUtilisateursController implements Initializable {

    @FXML
    private TextField txt_Username;
    @FXML
    private TextField txt_Password;
    @FXML
    private ComboBox<String> CB_type;
    @FXML
    private TextField txt_nom;
    @FXML
    private TextField txt_prenom;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_tel;
    @FXML
    private TableView<Utilisateur> tableview;
    @FXML
    private TableColumn<Utilisateur, Integer> Colid;
    @FXML
    private TableColumn<Utilisateur, String> colUserName;
    @FXML
    private TableColumn<Utilisateur, String> colUserPass;
    @FXML
    private TableColumn<Utilisateur, String> colType;
    @FXML
    private TableColumn<Utilisateur, String> colNom;
    @FXML
    private TableColumn<Utilisateur, String> colPrenom;
    @FXML
    private TableColumn<Utilisateur, String> colEmail;
    @FXML
    private TableColumn<Utilisateur, String> colTel;
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
                 
        
            CB_type.getItems().addAll("admin", "cassier", "gestionnaire");
            combo_rech.getItems().addAll("Login","Type","Nom","Prenom");
        
   
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
    }
    public ObservableList<Utilisateur> getList() throws SQLException{
    	ObservableList<Utilisateur> List = FXCollections.observableArrayList();
    	
        rs = db.querySelectAll("utilisateur");
    	
	Utilisateur utilisateur;
        
	while(rs.next()) {
		utilisateur = 
                        new Utilisateur(rs.getInt("id"),rs.getString("login"), 
                        rs.getString("mdp"),rs.getString("type"),
                        rs.getString("nom"), rs.getString("prenom"), 
                        rs.getString("email") ,rs.getString("tel"));  
                
                        List.add(utilisateur);
        }
		
                        
    	return List;
    }
       
    public void showAll() throws SQLException {
    	ObservableList<Utilisateur> list = getList();
    	
    	Colid.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("login"));
        colUserPass.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
            
    	tableview.setItems(list);
    }
    void actualiser() throws SQLException {
        showAll();
        txt_Username.clear();
        txt_Password.clear();
        txt_nom.clear();
        txt_prenom.clear();
        txt_tel.clear();
        txt_email.clear();
        CB_type.getSelectionModel().select("Type");
        txt_rech.clear();
        combo_rech.getSelectionModel().select("Login");        
       }

    
    

    @FXML
    private void buttonAjouter(ActionEvent event) throws SQLException {
        
         if (txt_Password.getText().equals("") || txt_Username.getText().equals("") || 
                 CB_type.getSelectionModel().isEmpty() ||txt_nom.getText().equals("") ||
                 txt_prenom.getText().equals("") || txt_tel.getText().equals("") ||
                 txt_email.getText().equals("")){
             
              infoBox("saisir tous les champs", null, "Failed");
              
        } else {
            String[] colon = {"login", "mdp", "nom", "prenom", "tel", "email","type"};
            String[] inf = {txt_Username.getText(), txt_Password.getText(),
                            txt_nom.getText(), txt_prenom.getText(),
                            txt_tel.getText(), txt_email.getText(),
                            CB_type.getSelectionModel().getSelectedItem()};
           
            db.queryInsert("utilisateur", colon, inf);
            actualiser();
        }
         
        
        
         
    }

    @FXML
    private void buttonActualiser(ActionEvent event) throws SQLException {
        actualiser();
    }

    @FXML
    private void buttonSupprimer(ActionEvent event) throws SQLException {
        
          Utilisateur utilisateur = tableview.getSelectionModel().getSelectedItem();
       
          if(utilisateur!=null)
          {
            int id =utilisateur.getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("est ce que tu es sure que tu veux supprimer");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
            db.queryDelete("utilisateur", "id=" + Integer.toString(id));
            actualiser();
           }
        }
        
    }

    @FXML
    private void OnClick(MouseEvent event) {
        Utilisateur utilisateur = tableview.getSelectionModel().getSelectedItem();
        if(utilisateur!=null)
        {
            txt_Password.setText(utilisateur.getMdp());
            txt_Username.setText(utilisateur.getLogin());
            CB_type.getSelectionModel().select(utilisateur.getType());
            txt_nom.setText(utilisateur.getNom());
            txt_prenom.setText(utilisateur.getPrenom());
            txt_tel.setText(utilisateur.getTel());  
            txt_email.setText(utilisateur.getEmail());

        
            
        }
         
    }

    @FXML
    private void buttonModifier(ActionEvent event) throws SQLException {
        
         Utilisateur utilisateur = tableview.getSelectionModel().getSelectedItem();
        
         if (txt_Password.getText().equals("") || txt_Username.getText().equals("") || 
                 CB_type.getSelectionModel().isEmpty() ||txt_nom.getText().equals("") ||
                 txt_prenom.getText().equals("") || txt_tel.getText().equals("") ||
                 txt_email.getText().equals(""))  {
             
              infoBox("SVP entrer les informations complete", null, "Failed");
            
        } else {
            String[] colon = {"login", "mdp", "nom", "prenom", "tel", "email", "type"};
            String[] inf = {txt_Username.getText(), txt_Password.getText(),
                            txt_nom.getText(), txt_prenom.getText(),
                            txt_tel.getText(), txt_email.getText(),
                            CB_type.getSelectionModel().getSelectedItem()};
           
            int id =utilisateur.getId();
          
            db.queryUpdate("utilisateur", colon, inf, "id='" + id + "'");
            actualiser();
        }
        
        
    }

    @FXML
    private void ButtonRechercher(ActionEvent event) throws SQLException {
         if (txt_rech.getText().equals("")) {
             infoBox("SVP entrer quelque chose", null, null);  
         }
         else {
            if (combo_rech.getSelectionModel().getSelectedItem().equals("Login")) {
                rs = db.querySelectAll("utilisateur", "login LIKE '%" + txt_rech.getText() + "%' ");
        
            }else  if (combo_rech.getSelectionModel().getSelectedItem().equals("Nom")) {
                rs = db.querySelectAll("utilisateur", "nom LIKE '%" + txt_rech.getText() + "%' ");
          
                   
            }else  if (combo_rech.getSelectionModel().getSelectedItem().equals("Prenom")) {
                rs = db.querySelectAll("utilisateur", "prenom LIKE '%" + txt_rech.getText() + "%' ");
              
            }else  if (combo_rech.getSelectionModel().getSelectedItem().equals("Type")) {
                rs = db.querySelectAll("utilisateur", "type LIKE '%" + txt_rech.getText() + "%' ");
                              
            }
        ObservableList<Utilisateur> List = FXCollections.observableArrayList();
	Utilisateur utilisateur;
        
	while(rs.next()) {
		utilisateur = 
                        new Utilisateur(rs.getInt("id"),rs.getString("login"), 
                        rs.getString("mdp"),rs.getString("type"),
                        rs.getString("nom"), rs.getString("prenom"), 
                        rs.getString("email") ,rs.getString("tel"));  
                
                        List.add(utilisateur);
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
    
}
