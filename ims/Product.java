package ims;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    
        public ObservableList<Part> associatedParts  = FXCollections.observableArrayList();;

        private  SimpleStringProperty name;
        private  SimpleIntegerProperty id;
        private  SimpleDoubleProperty price;
        private  SimpleIntegerProperty stock;
        private  SimpleIntegerProperty min;
        private  SimpleIntegerProperty max;
    
    public Product(){
       this.name = new SimpleStringProperty("");
       this.id = new SimpleIntegerProperty(0);
       this.price = new SimpleDoubleProperty(0);
       this.stock = new SimpleIntegerProperty(0);
       this.min = new SimpleIntegerProperty(0);
       this.max = new SimpleIntegerProperty(0);
    }    
    
    public Product(int productID, String name, double price, int inStock, int min, int max){
       this.name = new SimpleStringProperty(name);
       this.id = new SimpleIntegerProperty(productID);
       this.price = new SimpleDoubleProperty(price);
       this.stock = new SimpleIntegerProperty(inStock);
       this.min = new SimpleIntegerProperty(min);
       this.max = new SimpleIntegerProperty(max);
    }
	
	public void addAssociatedPart(Part p) {
            associatedParts.add(p);
	}

	public boolean removeAssociatedPart(int partNumber) {
        
            if(lookupAssociatedPart(partNumber) != null){
                Part p = lookupAssociatedPart(partNumber);
                associatedParts.remove(p);
                
                return true;
            }
                    return false;
	}

	public Part lookupAssociatedPart(int partNumber) {
		for(Part p : associatedParts){
                   System.out.println(p.getPartID());
                    if(p.getPartID() == partNumber){
                       
                        return p;
                   }              
                }
                
                return null;
	}
        
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public Double getPrice() {
        return price.get();
    }

    public void setPrice(Double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public Integer getStock() {
        return stock.get();
    }

    public void setStock(Integer stock) {
        this.stock = new SimpleIntegerProperty(stock);
    }

    public Integer getMin() {
        return min.get();
    }

    public void setMin(Integer min) {
        this.min = new SimpleIntegerProperty(min);
    }

    public Integer getMax() {
        return max.get();
    }

    public void setMax(Integer max) {
        this.max = new SimpleIntegerProperty(max);
    }
}

