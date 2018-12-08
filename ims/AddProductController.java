package ims;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

public class AddProductController implements Initializable {
    
    Product theNewProduct = new Product();
    
    @FXML private TextField txtAddProdID;
    @FXML private TextField txtAddProdName;
    @FXML private TextField txtAddProdInv;
    @FXML private TextField txtAddProdPrice;
    @FXML private TextField txtAddProdMax;
    @FXML private TextField txtAddProdMin;
    @FXML private TextField txtAddProdSearch;
    
    @FXML private TableView<Part> tablePartsList;
    @FXML private TableView<Part> tableAddedParts;
     
    @FXML private TableColumn<Part, Integer> tablePartID;
    @FXML private TableColumn<Part, String> tablePartName;
    @FXML private TableColumn<Part, Integer> tablePartInv;
    @FXML private TableColumn<Part, Double> tablePartPrice;
    
    @FXML private TableColumn<Part, Integer> tableAddedID;
    @FXML private TableColumn<Part, String> tableAddedName;
    @FXML private TableColumn<Part, Integer> tableAddedInv;
    @FXML private TableColumn<Part, Double> tableAddedPrice;
    
    @FXML
    private void btnCancelProduct_onAction(ActionEvent event) throws IOException{
     
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Views/InventoryManagement.fxml"));
        Parent p = loader.load();
        Scene sc = new Scene(p);
        Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("Inventory Management");
        st.setScene(sc);
        st.show();  
    }
    
    @FXML
    private void btnAddProduct_onAction(ActionEvent event){
        if(tablePartsList.getSelectionModel().getSelectedItem() != null){
            Part p = tablePartsList.getSelectionModel().getSelectedItem();
        
            theNewProduct.addAssociatedPart(p);
        }

    }
    
    @FXML
    private void btnSaveProduct_onAction(ActionEvent event) throws IOException{
        String name;
        int inv;
        int max;
        int min;
        double price;
         try{
             if(txtAddProdName.getText().isEmpty()){
            throw new InvalidInputException("Empty String", "Name");
        }
        else{
            name = txtAddProdName.getText().trim();
        }
            
        if(txtAddProdInv.getText().trim().matches("^\\d+$")){
            inv = Integer.parseInt(txtAddProdInv.getText().trim());
        }
        else{
             throw new InvalidInputException(txtAddProdInv.getText().trim(), "Inventory");
        }
        
        if(txtAddProdPrice.getText().trim().matches("^[0-9].*$")){
           price = Double.parseDouble(txtAddProdPrice.getText().trim());
        }
        else{
              throw new InvalidInputException(txtAddProdPrice.getText().trim(), "Price");
        }
        
        if(txtAddProdMax.getText().trim().matches("^\\d+$")){
            max = Integer.parseInt(txtAddProdMax.getText().trim());
        }
        else{
              throw new InvalidInputException(txtAddProdMax.getText().trim(), "Max");
        }
        
        if(txtAddProdMin.getText().trim().matches("^\\d+$")){
            min = Integer.parseInt(txtAddProdMin.getText().trim());
        }
        else{
              throw new InvalidInputException(txtAddProdMin.getText().trim(), "Min");
        }
        
        if(min > max){
         throw new MinExceedsMaxException(min, max);
        }
                 
                 theNewProduct.setId(Inventory.products.size()+1);
                 theNewProduct.setName(name);
                 theNewProduct.setStock(inv);
                 theNewProduct.setPrice(price);
                 theNewProduct.setMax(max);
                 theNewProduct.setMin(min);
                 
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Views/InventoryManagement.fxml"));
                Parent p = loader.load();
                Scene sc = new Scene(p);
                Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
                st.setTitle("Inventory Management");
                st.setScene(sc);
                st.show();  
                Inventory.addProduct(theNewProduct);
         }
         catch(MinExceedsMaxException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ex.getHeading()); 
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            
            
         } catch (InvalidInputException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ex.getHeading()); 
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
    
    public void populate(){
        tablePartID.setCellValueFactory(new PropertyValueFactory("partID"));
        tablePartName.setCellValueFactory(new PropertyValueFactory("name"));
        tablePartInv.setCellValueFactory(new PropertyValueFactory("inStock"));
        tablePartPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        tablePartsList.setItems(Inventory.allParts);
        
        tableAddedID.setCellValueFactory(new PropertyValueFactory("partID"));
        tableAddedName.setCellValueFactory(new PropertyValueFactory("name"));
        tableAddedInv.setCellValueFactory(new PropertyValueFactory("inStock"));
        tableAddedPrice.setCellValueFactory(new PropertyValueFactory("price"));
         
        tableAddedParts.setItems(theNewProduct.associatedParts);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        populate();
    }    
    
}
