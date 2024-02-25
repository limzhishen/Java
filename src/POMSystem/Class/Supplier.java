

package POMSystem.Class;



import POMSystem.Interface.Manipulation;


// Represents a Supplier class that implements the Manipulation interface
public class Supplier implements Manipulation{
    private String SupplierID;
    private String Suppliername;
    private String Contact;
    private String Location;
    
    // Constructor to initialize a Supplier with provided details
    public Supplier(String Supplierid,String Suppliername,String Contact,String location){
        this.SupplierID=Supplierid;
        this.Suppliername=Suppliername;
        this.Contact=Contact;
        this.Location=location;
        //SupplierItem=new ArrayList<>();
    }
    
    // Constructor to initialize a Supplier with provided details (without Supplier ID)
    public Supplier(String Suppliername,String Contact,String location){
        this.Suppliername=Suppliername;
        this.Contact=Contact;
        this.Location=location;
    }
    public Supplier (String supplierid){
        this.SupplierID=supplierid;
    }
    // Constructor to initialize a Supplier with a Supplier ID and name
    public Supplier(String SupplierID,String Suppliername){
        this.SupplierID=SupplierID;
        this.Suppliername=Suppliername;
    }
    
    // Method to get the Supplier ID
    public String getID(){
        return SupplierID;
    }
    
    // Method to get the Supplier name
    public String getName(){
        return Suppliername;
    }
    
    // Method to set the Supplier name
    public void setName(String name){
        this.Suppliername=name;
    }
    
    // Method to set the contact information of the Supplier
    public void setContact(String contact){
        this.Contact=contact;
    }
    
    // Method to set the location of the Supplier
    public void setLocation(String location){
        this.Location=location;
    }
    
    // Implementation of the Add method from the Manipulation interface
    @Override
    public void Add(){
        FileHandling fh= new FileHandling();
        String id =Tools.id_Generate("Sup", "supplier");
        String savesString=String.join(",", id,Suppliername,Contact,Location);
        if(fh.WriteFile(savesString, "supplier")){
            System.out.println("Supplier Have been successful Added");
        }
    }

    // Implementation of the Edit method from the Manipulation interface
    @Override
    public void Edit(){
        FileHandling fh=new FileHandling();
        String editString=String.join(",", SupplierID,Suppliername,Contact,Location);
        if(fh.EditFile(editString, "supplier")){
            System.out.println("Supplier have been succesful edit");
        }
        
    }
    
    // Implementation of the Delete method from the Manipulation interface
    @Override
    public void Delete(){
        FileHandling fh=new FileHandling();
        if(fh.DeleteFilecontent(SupplierID,"supplier")){
            System.out.println("Item Successful to delete");
        }
    }

   
    // Override the toString method to provide a string representation of the Supplier object
    @Override
    public String toString(){
        return "Supplier Name: \t\t"+Suppliername+"\nContact: \t\t"+Contact+"\nLocation\t\t"+Location;
    }
}
