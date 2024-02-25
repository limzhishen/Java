

package POMSystem.Class;

import POMSystem.Page.PurchasesManagerPage;


// Represents a Purchases Manager class that extends the User class
public class Purchases_Manager extends User{
    
    // Constructor to initialize a Purchases Manager with provided details
    public Purchases_Manager(String userid,String username,String password, String name, String contact, String email){
        super(userid,username,password,name,contact,email);
    }
    
    // Constructor to initialize a Purchases Manager with provided details (without user ID)
    public Purchases_Manager( String username,String password, String name, String contact, String email){
        super(username,password,name,contact,email);
    }
    
    // Constructor to initialize a Purchases Manager with a user ID
    public Purchases_Manager(String id){
        super(id);
    }
    
    // Constructor to initialize a Purchases Manager from an existing User object
    public Purchases_Manager(User user){
        super(user.getId(),user.getUsername(),user.getPassword(),user.getName(),user.getContact(),user.getEmail());
    }
    
    // Override the login method for Purchases Manager
    @Override
    public void login(){
        PurchasesManagerPage pm=new PurchasesManagerPage(this);// Create a PurchasesManagerPage instance
        pm.pmpage();// Call the pmpage method to display the Purchases Manager page
        System.out.println("Purchases Manager\t"+ getName()+"\tSuccesful to login");
    
    }
}
