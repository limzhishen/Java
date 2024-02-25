
package POMSystem.Class;


import java.io.FileInputStream;
import java.io.*;
import java.util.List;


public class FileSerialization extends FileLocation {
    public List<User> UserRead(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(Filepath.user.getPath()))) {
            return(List<User>)in.readObject();
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.toString());
        }
        return null;
    }
    public boolean UserWrite(List<User> userList){
        try(ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(Filepath.user.getPath()))) {
            out.writeObject(userList);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }
}
