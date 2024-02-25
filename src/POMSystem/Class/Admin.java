

package POMSystem.Class;

import POMSystem.Page.AdminPage;
import java.util.ArrayList;
import java.util.List;


public class Admin extends User {
    
    public Admin(String id,String username,String password, String name, String contact, String email){
        super(id,username,password,name,contact,email);
    }
    public Admin( String username,String password, String name, String contact, String email){
        super(username,password,name,contact,email);
    }
    public Admin(String id){
        super(id);
    }
    public Admin(User user){
        super(user.getId(),user.getUsername(),user.getPassword(),user.getName(),user.getContact(),user.getEmail());
    }

    @Override
    public void login(){
        System.out.println("Admin\t"+ getName()+"\tSuccesful to login");
        AdminPage adminPage= new AdminPage(this);
        adminPage.adminPage();
    }
    public void Register(String role,User register){
        register.setID(Userid_Generate(role, "user"));
        FileSerialization fs=new FileSerialization();
        List<User> userList=fs.UserRead();
        userList.add(register);
        if(fs.UserWrite(userList)){
            System.out.println(role+" have been Successful Register");
        }
        else{
            System.out.println("Something got error");
        }
    }
    public void DeleteUser(int index){
        FileSerialization fs=new FileSerialization();
        List<User> userList=fs.UserRead();
        userList.remove(index);
        if(fs.UserWrite(userList)){
            System.out.println("User have been Successful Delete");
        }
        else{
            System.out.println("Something got error");
        }
    }
    public String Userid_Generate(String role,String filetype){
        
        FileSerialization fs=new FileSerialization();
        List<User> User_detailList=fs.UserRead();
        List<Integer> Id_NumList= new ArrayList<>();
        User_detailList.remove(0);
        for (User user:User_detailList){
            if ((user.getId().substring(0,user.getId().length()-3)).equals(role)){
                Id_NumList.add(Integer.valueOf(user.getId().substring(user.getId().length()-3,user.getId().length())));
            }
        }
        int num=1;
        while(Id_NumList.contains(num)){
            num++;
        }
        String id;
        if (num<10){
            id=role+"00"+Integer.toString(num);
        }
        else if(num<100){
            id=role+"0"+Integer.toString(num);
        }
        else{
            id=role+Integer.toString(num);
        }
        return id;
    }


}
