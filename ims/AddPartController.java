package ims;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPartController {
    
    private boolean outsourced = true;
    
    @FXML private RadioButton btnInHouse;
    @FXML private RadioButton btnOutsourced;
      
    @FXML private TextField txtAddPartName;
    @FXML private TextField txtAddPartInv;
    @FXML private TextField txtAddPartPrice;
    @FXML private TextField txtAddPartMin;
    @FXML private TextField txtAddPartMax;
    @FXML private TextField txtAddPartCompMach;
    
    @FXML private Label lblMacComp;
    
    @FXML
    private void btnSavePart_onAction(ActionEvent event)throws IOException {

        String name;
        int inv;
        double price;
        int min;
        int max;
        
        try{
            if(txtAddPartName.getText().isEmpty()){
                throw new InvalidInputException("Empty String", "Name");
            }
            else{
                name = txtAddPartName.getText().trim();
            }
            if(txtAddPartInv.getText().matches("^\\d+$")){
                inv = Integer.parseInt(txtAddPartInv.getText().trim());
            }
            else{
                throw new InvalidInputException(txtAddPartInv.getText().trim(), "Inventory");
            } 
            if(txtAddPartPrice.getText().matches("^[0-9].*$")){
               price = Double.parseDouble(txtAddPartPrice.getText().trim());
            }
            else{
                  throw new InvalidInputException(txtAddPartPrice.getText().trim(), "Price");
            } 
            if(txtAddPartMin.getText().matches("^\\d+$")){
                min = Integer.parseInt(txtAddPartMin.getText().trim());
            }
            else{
                  throw new InvalidInputException(txtAddPartMin.getText().trim(), "Min");
            }
             if(txtAddPartMax.getText().matches("^\\d+$")){
                max = Integer.parseInt(txtAddPartMax.getText().trim());
            }
            else{
                  throw new InvalidInputException(txtAddPartMax.getText().trim(), "Max");
            }
            if(min > max){
                throw new MinExceedsMaxException(min, max);
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Views/InventoryManagement.fxml"));
            Parent p = loader.load();
            Scene sc = new Scene(p);
            Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Inventory Management");

            
            if(outsourced){
                if(txtAddPartCompMach.getText().isEmpty()){
                    throw new InvalidInputException("Empty", "Company Name");
                }
                else{
                    String comp = txtAddPartCompMach.getText().trim();
                    Outsourced o = new Outsourced(Inventory.allParts.size()+1, name, price, inv, min, max, comp);
                    Inventory.addPart(o);
                }
            }
            else if(!outsourced){
                if(txtAddPartCompMach.getText().trim().matches("^\\d+$")){
                    int mach = Integer.parseInt(txtAddPartCompMach.getText().trim());
                    Inhouse in = new Inhouse(Inventory.allParts.size()+1, name, price, inv, min, max, mach);
                    Inventory.addPart(in);
                }
                else{
                  throw new InvalidInputException(txtAddPartCompMach.getText().trim(), "Machine ID");
                }
            }

            st.setScene(sc);
            st.show();   
        } 
        catch (MinExceedsMaxException ex) {
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
    private void btnCancelPart_onAction(ActionEvent event) throws IOException{
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
    private void btnInhouse_onAction(ActionEvent event){
        
        Radio_Switcher(btnInHouse,btnOutsourced, lblMacComp, "Machine ID", txtAddPartCompMach, "Machine ID");
        
        outsourced = false;
    }
    
    @FXML
    private void btnOutsourced_onAction(ActionEvent event){
        
        Radio_Switcher(btnOutsourced, btnInHouse,lblMacComp, "Company Name", txtAddPartCompMach, "Company Name");
      
        outsourced = true;
    }
    
    private void Radio_Switcher(RadioButton a, RadioButton b, Label label, String labelText, TextField text, String promptText){
        
        a.setSelected(true);
        b.setSelected(false);
        label.setText(labelText);
        text.setPromptText(promptText);
    }
}
