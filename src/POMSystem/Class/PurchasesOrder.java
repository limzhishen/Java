

package POMSystem.Class;
import POMSystem.Interface.Manipulation;

// Represents a PurchasesOrder class that implements the Manipulation interface
public class PurchasesOrder implements Manipulation{
    // Private instance variables to store information about a PurchasesOrder
    private PurchasesRequisition purchasesRequisition;
    private User user;
    private String POid;
    enum Status{
        Delivery,
        Arrive;
    }
    
    private String userid;
    private String Itemid;
    private String Suppid;
    private String Prid;
    private int Quantity;
    private double Price;
    
    // Constructor to initialize a PurchasesOrder with a PurchasesRequisition and User
    public PurchasesOrder(PurchasesRequisition purchasesRequisition,User user){
        this.purchasesRequisition=purchasesRequisition;
        this.user=user;
    }
    
    // Constructor to initialize a PurchasesOrder with provided details
    public PurchasesOrder(String poid,String userid,String itemid,String suppid,String prid,String quantity,String price,String status,User user){
        this.POid=poid;
        this.userid=userid;
        this.Itemid=itemid;
        this.Suppid=suppid;
        this.Prid=prid;
        this.Quantity=Integer.parseInt(quantity);
        this.Price=Double.parseDouble(price);
        this.status=Status.valueOf(status);// Convert status string to enum
        this.user=user;
    }
    
    // Getter methods for retrieving specific information
    public String getitemid(){
        return Itemid;
    }
    public int getQuantity(){
        return Quantity;
    }
    public String getPrid(){
        return Prid;
    }
    private Status status;// Enum representing the status of the order
    
    // Setter method to set the status of the order
    public void setStatus(String status){
        this.status=Status.valueOf(status);
    }
    
    // Method to add a PurchasesOrder to the system
    @Override
    public void Add(){
        POid=Tools.id_Generate("PO", "po");
        String saveString=String.join(",", POid,user.getId(),purchasesRequisition.getItemid(),purchasesRequisition.getSuppid(),purchasesRequisition.getPRID(),Integer.toString(purchasesRequisition.getQuantity()),Double.toString(purchasesRequisition.getPrice()),Status.Delivery.toString(),"NA");
        FileHandling fh=new FileHandling();
        if (fh.WriteFile(saveString, "po")){
            System.out.println("Successful to add Purchases Order");
        }
    }
    
    // Method to edit a PurchasesOrder in the system
    @Override
    public void Edit(){
        String acceptuser="-";
        if(user!=null){
            acceptuser=user.getId();
        }
        String editString=String.join(",", POid,userid,Itemid,Suppid,Prid,Integer.toString( Quantity),Double.toString(Price),status.toString(),acceptuser);
        FileHandling fh=new FileHandling();
        if (fh.EditFile(editString, "po")){
            System.out.println("Successful to Edit Purchases Order");
        }
    }
    
    // Method to delete a PurchasesOrder from the system
    @Override
    public void Delete(){
        FileHandling fh=new FileHandling();
        if (fh.DeleteFilecontent(POid, "po")){
            System.out.println("Successful to Delete Purchases Order");
        }
    }
    
    // Override the toString method to display PurchasesOrder information
    @Override
    public String toString(){
        return String.join(" \n","POid: \t\t"+ POid,"Userid: \t"+userid,"Itemid: \t"+Itemid,"Supplierid: \t"+Suppid,"PRid: \t\t" +Prid,"Quantity: \t"+Integer.toString( Quantity),"Price: \t\t"+Double.toString(Price),"Status: \t"+status.toString());
        
    }
    
}
