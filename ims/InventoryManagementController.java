package ims;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InventoryManagementController implements Initializable {
    
    @FXML TableView<Product> tableProduct;
    @FXML TableView<Part> tablePart;
    
    @FXML TableColumn<Part,Integer> tablePartID;
    @FXML TableColumn<Part, String> tablePartName;
    @FXML TableColumn<Part, Integer> tablePartInv;
    @FXML TableColumn<Part, Double> tablePartPrice;
    @FXML TableColumn<Product, Integer> tableProdID;
    @FXML TableColumn<Product, String> tableProdName;
    @FXML TableColumn<Product, Integer> tableProdInv;
    @FXML TableColumn<Product, Double> tableProdPrice;
    
    @FXML TextField txtParts;
    @FXML TextField txtProducts;
  
    @FXML
    private void btnAddPart_onAction(ActionEvent event)throws IOException {

            FXMLLoader loader = new FXMLLoader();
        
            loader.setLocation(getClass().getResource("Views/AddPart.fxml"));
            Parent p = loader.load();
            Scene sc = new Scene(p);
            
            Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Add Part");
            st.setScene(sc);
            st.show();                
    }
    
    @FXML
    private void btnAddProduct_onAction(ActionEvent event)throws IOException {
       
            FXMLLoader loader = new FXMLLoader();
        
            loader.setLocation(getClass().getResource("Views/AddProduct.fxml"));
            Parent p = loader.load();
            Scene sc = new Scene(p);
            
            Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Add Product");
            st.setScene(sc);
            st.show();       
    }
    
    @FXML
    private void btnModPart_onAction(ActionEvent event)throws IOException {
      
            if(tablePart.getSelectionModel().getSelectedItem() != null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Views/ModifyPart.fxml"));
                Parent p = loader.load();
                Scene sc = new Scene(p);
                Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
                st.setTitle("Modify Part");
                st.setScene(sc);
                ModifyPartController controller = loader.getController();
                Part pt = tablePart.getSelectionModel().getSelectedItem();
                controller.populate(pt);
                st.show();    
            }            
    }
    
    @FXML
    private void btnModProduct_onAction(ActionEvent event) throws IOException{
      
        if(tableProduct.getSelectionModel().getSelectedItem() != null){
          
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Views/ModifyProduct.fxml"));
            Parent p = loader.load();
            Scene sc = new Scene(p);
            Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Modify Product");
            st.setScene(sc);

            ModifyProductController controller = loader.getController();
            Product prod = tableProduct.getSelectionModel().getSelectedItem();
            controller.populate(prod);
            st.show(); 
        }
    }
    
    @FXML
    private void btnDelPart_onAction(ActionEvent event) {
     Part p = tablePart.getSelectionModel().getSelectedItem();
     Inventory.deletePart(p);
    }
    
    @FXML
    private void btnDelProduct_onAction(ActionEvent event) {
     Product prod = tableProduct.getSelectionModel().getSelectedItem();
     Inventory.removeProduct(prod.getId());
    }
    
    @FXML
    private void btnSearchPart_onAction(ActionEvent event) {
    
        boolean found = false;
        
    try{
        int id;
        
        if(txtParts.getText().trim().matches("^\\d+$")){
            id = Integer.parseInt(txtParts.getText());
        }
        else{
             throw new InvalidInputException(txtParts.getText().trim(), "Search Part");
        }
        
        for(int i = 0; i < tablePart.getItems().size(); i++){
            if(tablePart.getItems().get(i).getPartID() == id){
                tablePart.getSelectionModel().select(i);
                found = true;
            }
        }
        
        if(found == false){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search"); 
            alert.setContentText("The search returned no results");
            alert.showAndWait();
        }
    }
     catch(InvalidInputException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ex.getHeading()); 
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
     }
    }
    
    @FXML
    private void btnSearchProduct_onAction(ActionEvent event) {
           boolean found = false;
        
    try{
        int id;
        
        if(txtProducts.getText().trim().matches("^\\d+$")){
            id = Integer.parseInt(txtProducts.getText());
        }
        else{
             throw new InvalidInputException(txtProducts.getText().trim(), "Search Product");
        }
        
        for(int i = 0; i < tableProduct.getItems().size(); i++){
            if(tableProduct.getItems().get(i).getId() == id){
                tableProduct.getSelectionModel().select(i);
                found = true;
            }
        }
        
        if(found == false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search"); 
            alert.setContentText("The search returned no results");
            alert.showAndWait();
        }
    }
     catch(InvalidInputException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ex.getHeading()); 
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
     }
    }
    
    @FXML
    private void btnExit_onAction(ActionEvent event) {
       Platform.exit();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tablePartID.setCellValueFactory(new PropertyValueFactory("partID"));
        tablePartName.setCellValueFactory(new PropertyValueFactory("name"));
        tablePartInv.setCellValueFactory(new PropertyValueFactory("inStock"));
        tablePartPrice.setCellValueFactory(new PropertyValueFactory("price"));

        tableProdID.setCellValueFactory(new PropertyValueFactory("id"));
        tableProdName.setCellValueFactory(new PropertyValueFactory("name"));
        tableProdInv.setCellValueFactory(new PropertyValueFactory("stock"));
        tableProdPrice.setCellValueFactory(new PropertyValueFactory("price"));

        tableProduct.setItems(Inventory.products);
        tablePart.setItems(Inventory.allParts);
    }    
}
