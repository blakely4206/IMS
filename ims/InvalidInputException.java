package ims;



 
public class InvalidInputException extends Exception {
    private final String value;
    private final String field;
    private final String heading;
    private final String message;
    
    
    public InvalidInputException(String value, String field){
        this.value = value;
        this.field = field;
        
        this.heading = "Invalid Input";
        this.message = field + " contains invalid input: " + value ;
    }
 
    public String getValue(){
        return this.value;
    }
    
    public String getField(){
        return this.field;
    }
    
    public String getHeading(){
        return this.heading;
    }
    
    @Override
    public String getMessage(){
        return this.message;
    }
}
