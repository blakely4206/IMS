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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyPartController implements Initializable {
    
    Outsourced out;
    Inhouse in;
    Part deletePart;
    
    private boolean outsourced = false;
    
    @FXML private TextField txtModPartID;
    @FXML private TextField txtModPartName;
    @FXML private TextField txtModPartInv;
    @FXML private TextField txtModPartPrice;
    @FXML private TextField txtModPartMax;
    @FXML private TextField txtModPartMin;
    @FXML private TextField txtModPartCompMach;
    
    @FXML private RadioButton btnRadioInHousePart;
    @FXML private RadioButton btnRadioOutsourcedPart;
    
    @FXML private Label labelCompMach;
    
    @FXML
    private void btnRadioInHousePart(ActionEvent event){
        
        Radio_Switcher_Inhouse();
       if(btnRadioInHousePart.isSelected()){
           outsourced = false;
       }
    }
    
    @FXML
    private void btnRadioInHousePart1(ActionEvent event){
        
        Radio_Switcher_Outsourced();
        if(btnRadioOutsourcedPart.isSelected()){
            outsourced = true;
        }
    }
    
    @FXML
    private void btnSavePart_onAction(ActionEvent event)throws IOException, InvalidInputException {
      
      String name;
      int id;
      int inv;
      int max;
      int min;
      double price; 
      
        try{
            
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Views/InventoryManagement.fxml"));
        Parent p = loader.load();
        Scene sc = new Scene(p);
        Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("Inventory Management");

         if(txtModPartName.getText().isEmpty()){
             throw new InvalidInputException("Empty String", "Name");
         }
          else{
             name = txtModPartName.getText().trim();
         }
          
        id = Integer.parseInt(txtModPartID.getText().trim());
        
        if(txtModPartInv.getText().trim().matches("^\\d+$")){
            inv = Integer.parseInt(txtModPartInv.getText().trim());
        }
        else{
             throw new InvalidInputException(txtModPartInv.getText().trim(), "Inventory");
        }
        
        if(txtModPartPrice.getText().trim().matches("^[0-9].*$")){
           price = Double.parseDouble(txtModPartPrice.getText().trim());
        }
        else{
              throw new InvalidInputException(txtModPartPrice.getText().trim(), "Price");
        }
        
        if(txtModPartMax.getText().trim().matches("^\\d+$")){
            max = Integer.parseInt(txtModPartMax.getText().trim());
        }
        else{
              throw new InvalidInputException(txtModPartMax.getText().trim(), "Max");
        }

        if(txtModPartMin.getText().trim().matches("^\\d+$")){
            min = Integer.parseInt(txtModPartMin.getText());
        }
        else{
              throw new InvalidInputException(txtModPartMin.getText().trim(), "Min");
        }
        if(min > max){
            throw new MinExceedsMaxException(min, max);
        }
        
        
        if(outsourced){
            String compName = txtModPartCompMach.getText().trim();
            if(txtModPartCompMach.getText().isEmpty()){
                throw new InvalidInputException("Empty", "Company Name");
            }
            else{
                out = new Outsourced(id, name, price, inv, max, min, compName);
                Inventory.deletePart(deletePart);
                Inventory.addPart(out);
            }
        }

        else if(!outsourced){
            
            if(!txtModPartCompMach.getText().trim().matches("^\\d+$")){
                throw new InvalidInputException(txtModPartCompMach.getText().trim(), "Machine Number");
            }
            else{
                int machNumber = Integer.parseInt(txtModPartCompMach.getText().trim());
                in = new Inhouse(id, name, price, inv, max, min, machNumber);
                Inventory.deletePart(deletePart);
                Inventory.addPart(in);
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
    private void btnCancelPart_onAction(ActionEvent event)throws IOException{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Views/InventoryManagement.fxml"));
            Parent p = loader.load();
            Scene sc = new Scene(p);
            Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Inventory Management");
          
            st.setScene(sc);
            st.show();
    }
    
    public void populate(Part p){
        
        deletePart = p;
        
        String partID = Integer.toString(p.getPartID());
        String name = p.getName();
        String inv = Integer.toString(p.getInStock());
        String price = Double.toString(p.getPrice());
        String max = Integer.toString(p.getMax());
        String min = Integer.toString(p.getMin());

        txtModPartName.setText(name);
        txtModPartInv.setText(inv);
        txtModPartID.setText(partID);
        txtModPartPrice.setText(price);
        txtModPartMax.setText(max);
        txtModPartMin.setText(min);

            if(p.getClass().getName().equals("ims.Inhouse")){
                
                Radio_Switcher_Inhouse();
                in = (Inhouse) p;
                txtModPartCompMach.setText(Integer.toString(in.getMachineID()));
                outsourced = false;
            }
            else if(p.getClass().getName().equals("ims.Outsourced")){
              
                Radio_Switcher_Outsourced();
                out = (Outsourced)p;
                txtModPartCompMach.setText(out.getCompanyName());
                outsourced = true;
            }
         
    }
    
    private void Radio_Switcher_Outsourced(){
        btnRadioInHousePart.setSelected(false);
        btnRadioOutsourcedPart.setSelected(true);
        
        labelCompMach.setText("Company Name");
        
        txtModPartCompMach.setPromptText("Company");
        outsourced = true;
    }
    
    private void Radio_Switcher_Inhouse(){
        
        btnRadioInHousePart.setSelected(true);
        btnRadioOutsourcedPart.setSelected(false);
        
        labelCompMach.setText("Machine ID");
        
        txtModPartCompMach.setPromptText("Machine ID");
        outsourced = false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }    
    
}
