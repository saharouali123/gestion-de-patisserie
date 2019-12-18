
package controller;

import BDD.db_connection;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static controller.AuthentificationController.infoBox;
import entities.Produit;
import entities.Vente;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.print.DocFlavor;



public class VenteController implements Initializable {

    @FXML
    private TableView<Produit> tableview;
    @FXML
    private TableColumn colCodeProduit;
    @FXML
    private TableColumn colDesignation;
    @FXML
    private TableColumn colRemise;
    @FXML
    private TableColumn colprix;
    @FXML
    private TableColumn colquantite;
    @FXML
    private ComboBox<String> combo_rech;
    @FXML
    private TextField txt_rech;
    @FXML
    private TableView <Vente>tableVente;

    ResultSet rs;
    db_connection db;
    Produit produit_from_table;
    @FXML
    private TableColumn<?, ?> colProduit;
    @FXML
    private TableColumn<?, ?> colPu;
    @FXML
    private TableColumn<?, ?> colQte;
    @FXML
    private TextField txt_qte;
    @FXML
    private TextField txt_total;
    @FXML
    private TableColumn<?, ?> colSubTotal;
    @FXML
    private TextField txt_esp;
    @FXML
    private TextField txt_rendre;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            db=new db_connection();
            db.connexionDatabase();
            
            showAll();
            combo_rech.getItems().addAll("code_Produit","designation","remise","quantite");
        } catch (SQLException ex) {
            Logger.getLogger(VenteController.class.getName()).log(Level.SEVERE, null, ex);
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
    	
        
        colCodeProduit.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colRemise.setCellValueFactory(new PropertyValueFactory<>("remise"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));   
    	tableview.setItems(list);
        
    }

    

    @FXML
    private void OnClick(MouseEvent event) {
           produit_from_table = tableview.getSelectionModel().getSelectedItem();
           
           
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
      int old, dec, now;
    public boolean test_stock() throws SQLException {
        boolean teststock;
        rs = db.querySelectAll("produit","code_Produit='" + produit_from_table.getCode()+ "'");
        while (rs.next()) {
            old = rs.getInt("quantite");
        }
        dec = Integer.parseInt(txt_qte.getText());
        if (old < dec) {
            teststock = false;
        } else {
            teststock = true;
        }
        return teststock;
    }
    
    public void def() throws SQLException {
        rs = db.querySelectAll("produit", "code_Produit='" + produit_from_table.getCode()+ "'");
        while (rs.next()) {
            old = rs.getInt("quantite");
        }
        dec = Integer.parseInt(txt_qte.getText());
        now = old - dec;
        String nvstock = Integer.toString(now);

        String a = String.valueOf(nvstock);
        String[] colon = {"quantite"};
        String[] isi = {a};
        System.out.println(db.queryUpdate("produit", colon, isi, "code_Produit='" +  produit_from_table.getCode() + "'"));
    }
    
    public float subtotal() {
        float a = produit_from_table.getPrix();
        int b = Integer.valueOf(txt_qte.getText());
        double remise = produit_from_table.getRemise();
        float c = (float) (a - a * (remise / 100));
        return   (c * b);
    }
       
       
    ObservableList<Vente> data = FXCollections.observableArrayList();
    float total=0;
    @FXML
    private void buttonOK(ActionEvent event) throws SQLException {
        
        colProduit.setCellValueFactory(new PropertyValueFactory<>("produit"));
        colPu.setCellValueFactory(new PropertyValueFactory<>("pu"));
        colQte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
       
         if (!test_stock()) {   
                    System.out.println("le stock est Limiter");
                } 
         else {
             if(Integer.parseInt(txt_qte.getText())>0)
             {
                        def(); //true
                        showAll();
                    Vente v =new Vente(Integer.parseInt(produit_from_table.getCode()),
                produit_from_table.getPrix(), Integer.parseInt(txt_qte.getText()), (float) subtotal());
        
                 data.add(v);
        
                tableVente.setItems(data);
                total=total+(float) subtotal();
                txt_total.setText(String.valueOf(total));
               
             }
        
        
                }
         
        
     
    }

    @FXML
    private void Buttonvalider(ActionEvent event) throws IOException, DocumentException, SQLException {
        
     
        String[] colon = {"produit","pu", "qte", "subTotale"};
         
        for(int i=0;i<data.size();i++)
        {
            Vente v =data.get(i);
            String[] inf = {String.valueOf(v.getProduit())                   
                           ,String.valueOf(v.getPu())
                           ,String.valueOf(v.getQte()),
                           String.valueOf(v.getSubtotal())};
            db.queryInsert("vente", colon, inf);
        }
        
        PdfPTable table = new PdfPTable(1);
        
        table.getDefaultCell().setBorderWidth(0.5f);
        table.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
        
        table.addCell("produit   pu   qte   subtot");
        for(int i=0;i<data.size();i++)
        {
            table.addCell(data.get(i).toString());
        }
        table.addCell("-----------------------------------\n");
        table.addCell("                                   Total  "+String.valueOf(total));
        table.addCell("                                   Expèce  "+txt_esp.getText());
        table.addCell("                                   A rendre  "+txt_rendre.getText());
        
       
        Document doc = new Document();
        PdfWriter.getInstance(doc,new FileOutputStream("FACTURE.pdf"));
        doc.open();
        doc.add(new Paragraph("FACTURE ICI"));
        doc.add(table);
        
        doc.close();
        
        
    }
 Vente vente_from_table_vente=null;
    @FXML
    private void OnClickVente(MouseEvent event) {
       vente_from_table_vente = tableVente.getSelectionModel().getSelectedItem();
           
    }

    @FXML
    private void buttonAnnuler(ActionEvent event) throws SQLException {
        for(int i=0;i<data.size();i++)
        {
            Vente vente = data.get(i);
            if(vente.equals(vente_from_table_vente))
            {
                
                total-=vente.getSubtotal();
                data.remove(i);
                txt_total.setText(String.valueOf(total));
                txt_esp.clear();
                txt_rendre.clear();
                rs = db.querySelectAll("produit", "code_Produit='" + vente_from_table_vente.getProduit()+ "'");
                while (rs.next()) {
                    old = rs.getInt("quantite");
                }
               
                String nvstock = Integer.toString(old+vente_from_table_vente.getQte());

                String a = String.valueOf(nvstock);
                String[] colon = {"quantite"};
                String[] isi = {a};
                db.queryUpdate("produit", colon, isi, "code_Produit='" +  vente_from_table_vente.getProduit()+ "'");
                showAll();
        
        
            }
        }
        System.out.println("controller.VenteController.buttonAnnuler()");
        
        
    }

    

    @FXML
    private void calculArendre(KeyEvent event) {
        Double a = Double.parseDouble(txt_esp.getText());
        Double b = Double.parseDouble(txt_total.getText());
        
        txt_rendre.setText(String.valueOf(a-b));
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
    
    
}
