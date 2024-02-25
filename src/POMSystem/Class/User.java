

package POMSystem.Class;


import java.util.List;
import java.io.Serializable;

// Abstract base class representing a User with common attributes and methods
public abstract class User implements Serializable {
    private static final long serialVersionUID=7167049544095828233L;//Final the serialUID to make sure the encryption and decrypt UID no change
    private String Userid;
    private String Username;
    private String Password;
    private String Name;
    private String Contact;
    private String Email;

    // Constructor with detailed user information
    public User(String userid,String username,String password, String name, String contact, String email){
        this.Userid=userid;
        this.Username=username;
        this.Password=password;
        this.Name=name;
        this.Contact=contact;
        this.Email=email;
    }
    
    // Constructor for simple user authentication
    public User(String username,String password){
        this.Username=username;
        this.Password=password;
    }
    
    // Constructor for creating a user with basic information
    public User(String username,String password, String name, String contact, String email){
        Userid=null;
        this.Username=username;
        this.Password=password;
        this.Name=name;
        this.Contact=contact;
        this.Email=email;
    }
    
    // Abstract method to be implemented by subclasses for user login behavior
    public abstract void login();
    
    // Constructor to create a user based on ID
    public User(String id){
        this.Userid=id;
    }
    
    // Getter methods for various user attributes
    public String getPassword(){
        return Password;
    }
    public String getUsername(){
        return Username;
    }
    public String getName(){
        return Name;
    }
    public String getContact(){
        return Contact;
    }
    public String getEmail(){
        return Email;
    }
    
    public String getId(){
        return Userid;
    }
    
    // Setter methods for various user attributes
        public void setUsername(String username) {
        this.Username = username;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setName(String name) {
        this.Name = name;

    }


    public void setContact(String contact) {
        this.Contact = contact;
    }


    public void setEmail(String email) {
        this.Email = email;
    }
    public void setID(String id){
        this.Userid=id;
    }

    // Static method for user login authentication
    public static User Login(String Username,String Password){
        FileSerialization fs= new FileSerialization();
        List<User> User_detailList=fs.UserRead();
        if(User_detailList!=null){
            for(User user: User_detailList){
                if(user.getUsername().equals(Username)&&user.getPassword().equals(Password)){
                    if(user.getId().substring(0,user.getId().length()-3).equals("Adm")){
                        return new Admin(user);
                    }
                    else if(user.getId().substring(0,user.getId().length()-3).equals("SM")){
                        return new Sales_Manager(user);
                    }
                    else if(user.getId().substring(0,user.getId().length()-3).equals("PM")){
                        return new Purchases_Manager(user);
                    }
                }
            }
        }
        else{
            System.out.println("User List is null");
        }
        return null;
    }
    
    // Method to validate if a username and password are unique
    public boolean ValidUsernamePassword(){
        FileSerialization fs= new FileSerialization();
        List<User> User_detailList=fs.UserRead();
        for (User user : User_detailList){
            if(user.getUsername().equals(Username) && user.getPassword().equals(Password)&&!user.getId().equals(Userid)){
                return false;
            }
        }
        return true;
    }
    
    // Method to edit a user's profile information
    public void Edit(User user){
        FileSerialization fs= new FileSerialization();
        List<User> User_detailList=fs.UserRead();
        for (int i=0; i<User_detailList.size();i++){
            if(User_detailList.get(i).getId().equals(user.getId())){
                User_detailList.set(i,user);
                break;
            }
        }
        if(fs.UserWrite(User_detailList)){
            System.out.println("Successful to Upload new profile");
        }
        else{
            System.out.println("Unsucces to edit");
        }
    }
    
    // Method to represent user information as a string
    @Override
    public String toString(){
        return ("ID\t\t\t"+Userid+"\nUsername: \t\t"+Username+"\nPassword: \t\t"+Password+"\nName: \t\t\t"+Name+"\nContact: \t\t"+Contact+"\nEmail: \t\t\t"+Email);
    }
    
}



