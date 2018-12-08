package ims;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

	public static ObservableList<Product> products = FXCollections.observableArrayList();
	public static ObservableList<Part> allParts =  FXCollections.observableArrayList();

	public static void addProduct(Product p) {
		products.add(p);
	}

	public static boolean removeProduct(int i) {
            if(lookupProduct(i) != null){
                products.remove(lookupProduct(i));
                return true;
            }
            return false;
	}

	public static Product lookupProduct(int id) {
		for(int i = 0; i < products.size(); i++){
                   
                    if(products.get(i).getId() == id){                       
                        return products.get(i);
                   }              
                }
                return null;
	}

	public static void updateProduct(int i) {
            
	}

	public static void addPart(Part p) {
		allParts.add(p);
	}

	public static boolean deletePart(Part p) {
            if(allParts.contains(p)){
                
                allParts.remove(p);
                return true;
            }
            return false;
	}

	public static Part lookupPart(int partID) {
		for(int i = 0; partID < allParts.size(); i++){
                   
                    if(allParts.get(i).getPartID()== partID){
                       
                        return allParts.get(i);
                   }              
                }
                return null;
	}

	public static void updatePart(int i) {
            
	}

}
