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

public class ModifyProductController implements Initializable {

    Product modProd;

    @FXML private TextField txtSearchProduct;
    @FXML private TextField txtModProdID;
    @FXML private TextField txtProdName;
    @FXML private TextField txtModProdInv;
    @FXML private TextField txtModProdPrice;
    @FXML private TextField txtModProdMax;
    @FXML private TextField txtModProdMin;

    @FXML private TableView<Part> tableAddParts;
    @FXML private TableView<Part> tableProdParts;

    @FXML TableColumn<Part,Integer> tableAddPartID;
    @FXML TableColumn<Part, String> tableAddPartName;
    @FXML TableColumn<Part, Integer> tableAddPartInv;
    @FXML TableColumn<Part, Double> tableAddPartPrice;

    @FXML TableColumn<Part,Integer> tableListPartID;
    @FXML TableColumn<Part, String> tableListPartName;
    @FXML TableColumn<Part, Integer> tableListPartInv;
    @FXML TableColumn<Part, Double> tableListPartPrice;

    @FXML
    private void btnCancelAddProduct_onAction(ActionEvent event)throws IOException{

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
    private void btnSaveProduct_onAction(ActionEvent event) throws IOException{
        
        String name;
        int id;
        int stock;
        double price;
        int max;
        int min;
        
        try{
            
        id = Integer.parseInt(txtModProdID.getText());
        
        if(txtProdName.getText().isEmpty()){
            throw new InvalidInputException("Empty String", "Name");
        }
        else{
            name = txtProdName.getText().trim();
        }
            
        if(txtModProdInv.getText().matches("^\\d+$")){
            stock = Integer.parseInt(txtModProdInv.getText().trim());
        }
        else{
             throw new InvalidInputException(txtModProdInv.getText().trim(), "Inventory");
        }
        
        if(txtModProdPrice.getText().trim().matches("^[0-9].*$")){
           price = Double.parseDouble(txtModProdPrice.getText().trim());
        }
        else{
              throw new InvalidInputException(txtModProdPrice.getText().trim(), "Price");
        }
        
        if(txtModProdMax.getText().trim().matches("^\\d+$")){
            max = Integer.parseInt(txtModProdMax.getText().trim());
        }
        else{
              throw new InvalidInputException(txtModProdMax.getText().trim(), "Max");
        }
        
        if(txtModProdMin.getText().trim().matches("^\\d+$")){
            min = Integer.parseInt(txtModProdMin.getText().trim());
        }
        else{
              throw new InvalidInputException(txtModProdMin.getText().trim(), "Min");
        }
        
            if(min > max){
             throw new MinExceedsMaxException(min, max);
            }

            Inventory.removeProduct(modProd.getId());
            modProd = new Product(id, name, price, stock, max, min);
            modProd.associatedParts = tableProdParts.getItems();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Views/InventoryManagement.fxml"));
            Parent p = loader.load();
            Scene sc = new Scene(p);
            Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Inventory Management");

            st.setScene(sc);
            st.show();

            Inventory.addProduct(modProd);
        } catch (MinExceedsMaxException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ex.getHeading()); 
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        catch (InvalidInputException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ex.getHeading()); 
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        
    }

    @FXML
    private void btnDeleteProduct(ActionEvent event){
        Part pt = tableProdParts.getSelectionModel().getSelectedItem();
        modProd.removeAssociatedPart(pt.getPartID());
    }

    @FXML
    private void btnSearchProduct_onAction(ActionEvent event){
        
        int x = Integer.parseInt(txtSearchProduct.getText());
        Product p = Inventory.lookupProduct(x);
        
        
    }

    @FXML
    private void btnAddProduct_onAction(ActionEvent event){
        if(tableAddParts.getSelectionModel().getSelectedItem() != null){
            Part p = tableAddParts.getSelectionModel().getSelectedItem();
        
            modProd.addAssociatedPart(p);
        }
    }
    
    public void populate(Product p){
        modProd = p;
        
        String id = Integer.toString(p.getId());
        String name = p.getName();
        String stock = Integer.toString(p.getStock());
        String price = Double.toString(p.getPrice());
        String max = Integer.toString(p.getMax());
        String min = Integer.toString(p.getMin());
        
        txtModProdID.setText(id);
        txtProdName.setText(name);
        txtModProdInv.setText(stock);
        txtModProdPrice.setText(price);
        txtModProdMax.setText(max);
        txtModProdMin.setText(min);
        
        tableAddPartID.setCellValueFactory(new PropertyValueFactory("partID"));
        tableAddPartName.setCellValueFactory(new PropertyValueFactory("name"));
        tableAddPartInv.setCellValueFactory(new PropertyValueFactory("inStock"));
        tableAddPartPrice.setCellValueFactory(new PropertyValueFactory("price"));
        tableAddParts.setItems(Inventory.allParts);
        
        tableListPartID.setCellValueFactory(new PropertyValueFactory("partID"));
        tableListPartName.setCellValueFactory(new PropertyValueFactory("name"));
        tableListPartInv.setCellValueFactory(new PropertyValueFactory("inStock"));
        tableListPartPrice.setCellValueFactory(new PropertyValueFactory("price"));
        tableProdParts.setItems(modProd.associatedParts);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
