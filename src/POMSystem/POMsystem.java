

package POMSystem;

import POMSystem.Class.InputValidation;
import POMSystem.Page.LoginPage;




public class POMsystem {

    public static void main(String[] args) {
        // Infinite loop to display the menu and process user input
        while(true){
            // Print a line of dashes for visual separation
            System.out.println("-".repeat(75));
            // Display the welcome message
            System.out.println("\tWelcome to Purchases Order Manager System (POM)");//'\t' inserts enough spaces to align the text 
            // Display menu options
            System.out.println("1. Login");
            System.out.println("2. Exit the program");
            System.out.print("Please enter your selection: ");
            // Get a valid menu selection from the user
            int select=InputValidation.getMenuSelectValid(2);
            // Process the user's selection using a switch statement
            switch(select){
                // If user selects 1 (Login)
                case 1 ->  {
                    // Create an instance of LoginPage
                    LoginPage lg= new LoginPage();
                    // Call the Show() method to display the login page
                    lg.Show();
                }
                // If user selects 2 (Exit)
                case 2 -> {InputValidation.closeScanner();// Close any open scanner (if used)
                    return;}// Exit the program
            }
        }
    }
    
}
