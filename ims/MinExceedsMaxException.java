package ims;




public class MinExceedsMaxException extends Exception {
 private final String message;
 private final String heading;
 
 public MinExceedsMaxException(int min, int max){
     
     this.message = "Minimum " + Integer.toString(min) + " must be less than the maximum " + Integer.toString(max);
     this.heading = "Min Exceeds Max";
 }
 
 @Override
 public String getMessage(){
     return this.message;
  } 
 
 public String getHeading(){
     return this.heading;
 }
}
