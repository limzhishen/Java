

package POMSystem.Class;
import POMSystem.Page.SalesManagerPage;


// Represents a Sales Manager class that extends the User class
public class Sales_Manager extends User{
    
    // Constructor to initialize a Sales Manager with provided details
    public Sales_Manager(String userid,String username,String password, String name, String contact, String email){
        super(userid,username,password,name,contact,email);
    }
    
    // Constructor to initialize a Sales Manager with provided details (without user ID)
    public Sales_Manager( String username,String password, String name, String contact, String email){
        super(username,password,name,contact,email);
    }
    
    // Constructor to initialize a Sales Manager with a user ID
    public Sales_Manager (String id){
        super(id);
    }
    
    // Constructor to initialize a Sales Manager from an existing User object
    public Sales_Manager(User user){
        super(user.getId(),user.getUsername(),user.getPassword(),user.getName(),user.getContact(),user.getEmail());
    }

   // Override the login method for Sales Manager
    @Override
    public void login(){
        SalesManagerPage SM = new SalesManagerPage(this);// Create a SalesManagerPage instance
        SM.smpage();// Call the smpage method to display the Sales Manager page
        System.out.println("Sales Manager\t"+ getName()+"\tSuccesful to login");
    }
    

}
