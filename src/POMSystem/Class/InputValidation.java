

package POMSystem.Class;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
public class InputValidation { 
    // Create a Scanner object for general input
    private static Scanner sc= new Scanner(System.in);
    static Scanner inputScanner= new Scanner(System.in);
    static Scanner menuinput = new Scanner(System.in);//open more is because next conflict
    
    // Method to get a valid selection from a menu with a maximum option number
    // Handle user input and validation logic to ensure that the selected value is within the valid range
    public static int getMenuSelectValid(int max){
        int select;
        while (true){
            System.out.print("Please enter your selection number: ");
            //Starts a try block to handle exceptions that might occur during input and validation
            try{
                select= menuinput.nextInt();// Reads an integer from user input
                // Check if the entered selection is out of range
                if (select<0 || select>max){
                    throw new ArithmeticException("Invalid selection");
                }
                break;// Exit the loop if input is valid
            }
            catch(InputMismatchException ex){
                menuinput.nextLine();// Clear the input buffer
                System.out.println("Invalid input. Please enter a valid number");
            }
            catch(ArithmeticException ex){
                System.out.println(ex.getMessage());// Display error message for invalid selection
            }
   
        }
        return select;  // Return the validated selection
    }
    
    // Method to get a valid string input
    public static String getString(){
        return sc.nextLine();
    }
    // Method to get a valid string input based on a specific type (contact or email)
    public static String getString(String type){  
        String input;
        while(true){
            // Read the string input from the scanner
            input=inputScanner.nextLine();
            try{
                if(type.equals("contact")){
                    // Validate contact number format
                    if(input.matches("\\d+")){
                        if(input.length()<3){throw new Exception("Please fill Contact number");}
                        if(input.substring(0,3).equals("011")){
                            if(input.length()!=11){
                                throw new Exception("Contact number length is wrong");
                            }
                        }
                        else if(input.substring(0,2).equals("01")){
                            if(input.length()!=10){
                                throw  new Exception("Contact number length is wrong");
                            }
                        }
                        else{
                            throw new Exception("Please fill in Malaysia telefon type");
                        }
                    }
                    else{
                        throw new Exception("Contact only can contant number");
                    }
                }
                else if (type.equals("email")){
                    // Validate email format
                    if(!input.contains("@")&& !input.contains(".")){
                        throw new Exception("Invalid email. Email must contain '@' and '.' symbols.");
                    }
                }
                
                return input;
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
                if(type.equals("contact")){
                    System.out.print("Please refill contact number: ");
                }
                else if(type.equals("email")){
                    System.out.print("Please refill email: ");
                }
            }
        }
        
    }
    
    // Method to get a valid integer input
    public static int getInt(){
        int input;
        while(true){
            try{
                input=menuinput.nextInt();
                    return input;
            }
            catch(Exception ex){
                menuinput.nextLine();
                System.out.print("Please Input valid number: ");
            }
        }
        
    }
    
    // Method to get a valid double input
    public static double getDouble(){
        double input;
        while(true){
            try{
                input=menuinput.nextDouble();
                return input;
            }
            catch(Exception ex){
                menuinput.nextLine();
                System.out.print("Please Input valid number: ");
            }
        }
    }
    

    
    // Method to get a valid date input
    public static String getDate(){
        while(true){
            try {
                System.out.print("Please input date like dd/mm/yyyy: ");
                String date=getString();
                String dateformat="dd/mm/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
                sdf.setLenient(false);
                Date dt = sdf.parse(date);
                return date;
                
            } catch (ParseException e) {
                System.out.println("Invalid date format. Date should be in dd/mm/yyyy format.");
                
            }
            
        }
    }
    public static int validnumber(String prompt,int number, int limit){
        if(number<limit){
            System.out.println("Cannot less than "+limit);
            System.out.print(prompt);
            number=getInt();
            validnumber(prompt,number, limit);
        }
        return number;
    }
    public static double validnumber(String prompt,double number, int limit){
        if(number<=limit){
            System.out.println("Cannot be "+limit+" or lessthan");
            System.out.print(prompt);
            number=getDouble();
            validnumber(prompt,number, limit);
        }
        return number;
    }
    
    // Method to close the Scanner objects
    public static void closeScanner() {
        sc.close(); 
        inputScanner.close();
        menuinput.close();
    }
    
    
}
