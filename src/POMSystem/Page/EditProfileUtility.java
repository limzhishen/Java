

package POMSystem.Page;

import POMSystem.Class.InputValidation;
import POMSystem.Class.User;
import POMSystem.Class.Admin;
import POMSystem.Class.Purchases_Manager;
import POMSystem.Class.Sales_Manager;

public class EditProfileUtility {
    // A private instance variable to temporarily store a User object for editing.
    private User TempUser=null;
    // Displays the menu for profile editing options.
    public void Profilemenu(){
        System.out.println("Please select which you want to edit");
        System.out.println("1. Username");
        System.out.println("2. Password");
        System.out.println("3. Name");
        System.out.println("4. Contact");
        System.out.println("5. Email");
        System.out.println("6. Save and Exit");
        System.out.println("7. Exit without Save");
    }
    // Creates a new instance of a user based on the existing user's role.
    private User FindUserinstance(User user){
    if (user instanceof Admin) {
        return new Admin(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getContact(), user.getEmail());
    } else if (user instanceof Sales_Manager) {
        return new Sales_Manager(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getContact(), user.getEmail());
    } else if (user instanceof Purchases_Manager) {
        return new Purchases_Manager(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getContact(), user.getEmail());
    } else {
        return null; // Return null if the user's role is unknown.
    }
    }
    // Handles the process of editing a user's profile.
    public void EditProfile(User user){
        TempUser=FindUserinstance(user);
        while(true){
            System.out.println("-".repeat(75));
            System.out.println("\t\tEdit Page");
            System.out.println(TempUser);// Display the user's information
            Profilemenu();// Display the edit menu
            int select=Userdetailchange(TempUser);// Call the method to perform profile changes
            if(select==6){
                if (TempUser.ValidUsernamePassword()){
                    TempUser.Edit(TempUser);
                    // Update the original user's information with the edited TempUser's information
                    user.setUsername(TempUser.getUsername());
                    user.setPassword(TempUser.getPassword());
                    user.setName(TempUser.getName());
                    user.setContact(TempUser.getContact());
                    user.setEmail(TempUser.getEmail());
                    break;// Exit the editing loop
                }
                else{
                    System.out.println("Conflict with username and password");              
                }  
            }
            else if (select==7){
                break; // Exit the editing loop without saving
            }
        }
    }
    
    // Handles the process of changing user details.
    //Use for editprofile and admin register
    public int Userdetailchange(User user){
        int select=InputValidation.getMenuSelectValid(7); // Get user's selection  
        switch (select) {
            case 1 -> {
                System.out.print("Enter your newusername: ");
                String newusername=InputValidation.getString();
                user.setUsername(newusername);// Set the new username
            }
            case 2 -> {
                System.out.print("Enter your newpassword: ");
                String newpassword=InputValidation.getString();
                user.setPassword(newpassword);// Set the new password
            }
            case 3 -> {
                System.out.print("Enter your new name: ");
                String newname=InputValidation.getString();
                user.setName(newname);// Set the new name
            }
            case 4 -> {
                System.out.print("Enter your new contact: ");
                String newcontact=InputValidation.getString("contact");
                user.setContact(newcontact);// Set the new contact
            }
            case 5 -> {
                System.out.print("Enter your new email: ");
                String newemail=InputValidation.getString("email");
                user.setEmail(newemail);// Set the new email
            }
        }
        return select;// Return the user's selection
    }

}
