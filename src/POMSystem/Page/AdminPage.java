

package POMSystem.Page;

import POMSystem.Class.Admin;
import POMSystem.Interface.Menu;
import POMSystem.Class.InputValidation;
import POMSystem.Class.*;
import java.util.ArrayList;
import java.util.List;




public class AdminPage extends AcceptPO implements Menu{
    private final Admin admin;// Store the admin who is using the admin page
    private boolean Logout=false;// Track if the admin has logged out
    
    // Constructor to initialize the AdminPage with an Admin object
    public AdminPage(Admin admin) {
        this.admin =admin;
        
    }
    // Main method for the admin page functionality
    public void adminPage(){
        while (!Logout) {    // Continue as long as the admin is not logged out
            System.out.println("-".repeat(75));
            System.out.println("\t\tWelcome to Admin Page "+admin.getName());
            displayMenu();// Display menu options
            Selection();// Handle admin's menu selection
        }
        
    }
    // Display menu options from the Menu interface
    @Override
    public void displayMenu(){
        // Display menu options
        System.out.println("1.  User Registration");
        System.out.println("2.  Item Entry");
        System.out.println("3.  Supplier Entry");
        System.out.println("4.  Supplier Item Entry");
        System.out.println("5.  Dairy Sales Entry");
        System.out.println("6.  Purchases Requisition Entry");
        System.out.println("7.  Purchases Order Entry");
        System.out.println("8.  Accept Order");
        System.out.println("9.  Edit Profile");
        System.out.println("10. View User list");
        System.out.println("11. Delete User");
        System.out.println("12. Logout");
        
    }
    // Handle the admin's menu selection
    public void Selection(){
        int select=InputValidation.getMenuSelectValid(12);// Get a valid selection (1 to 12)
        switch (select) {
            case 1 -> Register();// Call the Register() method for user registration
            case 2 -> ItemEntry();// Call the ItemEntry() method for item entry
            case 3 -> SupplierEntry();// Call the SupplierEntry() method for supplier entry
            case 4 -> SupplierItemEntry();// Call the SupplierItemEntry() method for supplier item entry
            case 5 -> DairySalesEntry(admin);// Call the DairySalesEntry() method for dairy sales entry with the admin parameter
            case 6 -> PurchasesRequisitionEntry(admin);// Call the PurchasesRequisitionEntry() method for purchases requisition entry with the admin parameter
            // Call the PurchasesOrderEntry() method for purchases order entry with the admin parameter
            case 7 -> {POUtility po=new POUtility();po.PurchasesOrderEntry(admin);}
            case 8 -> AcceptItem(admin);// Call the AcceptItem() method for accepting items with the admin parameter
            case 9 -> EditProfile(admin);// Call the EditProfile() method for editing the admin's profile with the admin parameter
            // Call the ViewUserlist() method to view the list of users
            case 10 -> {System.out.println("\t\t\tUser List View");ViewUserlist();}
            case 11 -> DeleteUser();// Call the DeleteUser() method to handle user deletion
            case 12 -> {Logout=true;}// Set Logout to true to exit the loop
            default -> System.out.println("Invalid Input");
        }

    }
    // Method to handle deleting a user
    private void DeleteUser(){
        System.out.println("\t\t\t\tDelete User");
        // Create a FileSerialization object to read user data
        FileSerialization fs= new FileSerialization();
        List<User> userList=fs.UserRead();// Read the list of users from the file
        ViewUserlist();// Display the list of users
        System.out.println(userList.size()+"\t Exit");
        int select=InputValidation.getMenuSelectValid(userList.size());// Prompt the admin to select a user to delete
        if(select==userList.size()){return;}// If the admin chooses to exit, return from the method
        System.out.println("-".repeat(50));
        System.out.println(userList.get(select));// Display the selected user's information
        System.out.println("1. Delete User");
        System.out.println("2. Cancel");
        // Prompt the admin to confirm or cancel the deletion
        int decision=InputValidation.getMenuSelectValid(2);
        if (decision==1){
            // Call the admin's DeleteUser() method to delete the selected user
            admin.DeleteUser(select);
        }
    }
 
    // Method to handle user registration
    private void Register(){
        String role;// Declare a variable to store the selected role
        // Display registration options to the admin
        System.out.println("-".repeat(75));
        System.out.println("\t\tRegister Page");
        System.out.println("Please Choose which User you want to create");
        System.out.println("1. Admin");
        System.out.println("2. Sales Manager");
        System.out.println("3. Purchases Manager");
        System.out.println("4. Exit");
        
        // Get the admin's choice for the type of user to register
        int choice=InputValidation.getMenuSelectValid(4);
        switch (choice) {
            // Set the role based on the admin's choice
            case 1 -> role="Adm";
            case 2 -> role="SM";
            case 3 -> role="PM";
            case 4 -> {return;}// If the admin chooses to exit, return from the method
            default-> role=" "; // Default to an empty role  
            }
        // Prompt the admin to enter registration details
        System.out.print("Enter register Username: ");
        String username=InputValidation.getString();
        System.out.print("Enter register Password: ");
        String password=InputValidation.getString();
        System.out.print("Enter register Name: ");
        String name=InputValidation.getString();
        System.out.print("Enter register Contact: ");
        String contact=InputValidation.getString("contact");
        System.out.print("Enter register Email: ");
        String email=InputValidation.getString("email");
        User registerprofile;
        // Create a user profile based on the selected role
        registerprofile = switch (role) {
            case "Adm" -> new Admin(username, password, name, contact, email);
            case "SM" -> new Sales_Manager(username, password, name, contact, email);
            default -> new Purchases_Manager(username, password, name, contact, email);
        };
        while(true){
            System.out.println("-".repeat(75));
            System.out.println("\tRegister User info");
            System.out.println(registerprofile);
            // Check if the username and password conflict with existing users
            if(!registerprofile.ValidUsernamePassword()){
                System.out.println("\t\tConflict with username and password");  
            }
            Profilemenu();// Display options for editing the user profile
            int select=Userdetailchange(registerprofile);// Get user's detail change choice
            if (select==6){
                if(registerprofile.ValidUsernamePassword()){
                    admin.Register(role,registerprofile);// Register the user using admin's method
                    return;
                }
                else{
                    System.out.println("Conflict with username and password");  
                }
            }
            if(select==7){
                System.out.println("Decline Register");
                return;
            }
        }
    }
    
    // Method to view the list of users
    private void ViewUserlist(){
        
        // Create a FileSerialization object to read user data
        FileSerialization fs= new FileSerialization();
        List<User> userList=fs.UserRead();// Read the list of users from the file
        List<String> UserList=new ArrayList<>();// Create a list to store formatted user information
        // Format each user's information and add it to the list
        for (User user : userList) {
            // Format user information
            String userInfo = String.format("%s,%s,%s,%s,%s,%s",
                    user.getId(), user.getUsername(), user.getPassword(),
                    user.getName(), user.getContact(), user.getEmail());
            UserList.add(userInfo);
        }
        Tools.View(UserList);// Display the user list
        
    }
}
