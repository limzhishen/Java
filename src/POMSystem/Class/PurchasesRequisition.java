

package POMSystem.Class;

import POMSystem.Interface.Manipulation;



// Enum to represent the status of a PurchasesRequisition
enum Status{
        Pending,
        Approval,
        Reject;
    }

// Represents a PurchasesRequisition class that implements the Manipulation interface
public class PurchasesRequisition implements Manipulation{
    // Private instance variables to store information about a PurchasesRequisition
    private String PRid;
    private SupplierItem supplierItem;
    private int Quantity;
    private String Date;
    private Status status;
    private String userid;
    
    // Constructor to initialize a PurchasesRequisition with provided details
    public PurchasesRequisition(String id, User user,int Quantity ,String date){
        this.PRid=id;
        this.userid=user.getId();
        this.supplierItem=new SupplierItem();
        this.Quantity=Quantity;
        this.Date=date;
    }
    
    // Constructor to initialize a PurchasesRequisition with provided details including status
    public PurchasesRequisition(String id, String userid,int Quantity ,String date,String status){
        this.PRid=id;
        this.userid=userid;
        this.supplierItem=new SupplierItem();
        this.Quantity=Quantity;
        this.Date=date;
        this.status=Status.valueOf(status);// Convert status string to enum
    }
    
    // Constructor to initialize a PurchasesRequisition with provided details including user and status
    public PurchasesRequisition(String id, User user,int Quantity ,String date,String status){
        this.PRid=id;
        this.userid=user.getId();
        this.supplierItem=new SupplierItem();
        this.Quantity=Quantity;
        this.Date=date;
        this.status=Status.valueOf(status);// Convert status string to enum
    }
    public void setSuppleritem(SupplierItem supplierItem){
        this.supplierItem=supplierItem;
    }
    
    // Setter method to set the quantity of the requisition
    public void setQuantity(int quantity){
        this.Quantity=quantity;
    }
    
    // Setter method to set the date of the requisition
    public void setDate(String date){
        this.Date=date;
    }
    
    // Getter method to retrieve the PRID of the requisition
    public String getPRID(){
        return PRid;
    }
    
    // Getter method to retrieve the SupplierID of the requisition
    public String getSuppid(){
        return supplierItem.getSuppID();
    }
    
    // Getter method to retrieve the ItemID of the requisition
    public String getItemid(){
        return supplierItem.getItemID();
    }
    
    // Setter method to set the status of the requisition
    public void setStatus(String status){
        this.status=Status.valueOf(status);
    }
    
    // Getter method to retrieve the quantity of the requisition
    public int getQuantity(){
        return Quantity;
    }
    
    // Getter method to retrieve the price of the requisition
    public double getPrice(){
        return supplierItem.getPrice();
    }
    
    
    @Override
    // Method to add a PurchasesRequisition to the system
    public void Add(){
        String saveString= String.join(",", PRid,userid,supplierItem.getItemID(),supplierItem.getSuppID(),Integer.toString(Quantity),Double.toString(supplierItem.getPrice()),Date,Status.Pending.toString());
        FileHandling fh=new FileHandling();
        if(fh.WriteFile(saveString, "pr")){
            System.out.println("Purchases Requisition successfull to add");
        }
    }
    
    // Method to edit a PurchasesRequisition in the system
    @Override
    public void Edit(){
        String editString= String.join(",", PRid,userid,supplierItem.getItemID(),supplierItem.getSuppID(),Integer.toString(Quantity),Double.toString(supplierItem.getPrice()),Date,status.toString());
        FileHandling fh=new FileHandling();
        if(fh.EditPR(editString, "pr")){
            System.out.println("Purchases Requisition successfull to Edit");
        }
    }
    
    // Method to delete a PurchasesRequisition from the system
    @Override
    public void Delete(){
        FileHandling fh=new FileHandling();
        if(fh.DeletePR(PRid, supplierItem.getSuppID(),"pr")){
            System.out.println(PRid+" Purchases Requisition successfull to delete");
        }
    }
    
    // Override the toString method to display PurchasesRequisition information
    @Override
    public String toString(){
        return "PRID: "+PRid+"\tUserid: "+userid+"\tItemID: "+supplierItem.getItemID()+"\tSuppID: "+supplierItem.getSuppID()+"\tQuantity: "+Quantity+"\tPrice: "+supplierItem.getPrice()+"\tDate: "+Date+"\tStatus: "+status.toString();
    }
    
}
