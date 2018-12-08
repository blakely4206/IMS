package ims;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    
    public Inventory inv = new Inventory();
    @Override
    public void start(Stage stage) throws Exception {
        try{
        System.out.println(inv.allParts.size());
        Parent root = FXMLLoader.load(getClass().getResource("Views/InventoryManagement.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        }
        catch(IOException ex){
             Logger.getLogger(InventoryManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
