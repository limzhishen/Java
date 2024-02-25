

package POMSystem.Page;
import POMSystem.Class.InputValidation;
import POMSystem.Class.User;


public class LoginPage {
    public void Show(){
        // Display a welcome message and login page header
        System.out.println("-".repeat(75));
        System.out.println("\t\tWelcome to Login Page");
        // Initialize the number of allowed login attempts
        int change=3;
        
        // Loop for handling login attempts
        while (change>0){
            // Prompt the user to enter their username
            System.out.print("Please enter your Username :");
            String Username=InputValidation.getString();
            // Check if the user wants to cancel the login process
            if(Username.equals("-1")){return;}// Exit the login process
            System.out.print("Please enter your Password :");
            
            // Prompt the user to enter their password
            String Password=InputValidation.getString();
            // Attempt to authenticate the user's credentials
            User user=User.Login(Username, Password);
            if (user!=null){
                user.login();// Perform user login
               break;// Exit the login loop
            }
            else{
                change--;// Reduce the remaining login attempts
                System.out.println("Invalid username or Password!!!");
                System.out.println("Remaining attempts available ( "+change+"/3)");
                System.out.println("-".repeat(40));
               
            }
       }
    } 
}
