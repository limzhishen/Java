

package POMSystem.Class;


import POMSystem.Interface.Manipulation;


// Represents a SupplierItem class that implements the Manipulation interface
public class SupplierItem implements Manipulation{
    private Item item;
    private Supplier supplier;
    private double PurchasesPrice;
    
    // Default constructor
    public SupplierItem(){};
    
    // Constructor to initialize SupplierItem with Supplier ID, Item ID, and Purchase Price
    public SupplierItem(String SupplierID,String ItemID, double PurchasesPrice){
        this.supplier= new Supplier( SupplierID);
        this.item=new Item(ItemID);
        this.PurchasesPrice=PurchasesPrice;
    }
    
    // Constructor to initialize SupplierItem with detailed information
    public SupplierItem(String SupplierID,String supliername,String ItemID,String itemname, double PurchasesPrice){
        this.supplier=new Supplier(SupplierID,supliername);
        this.item=new Item(ItemID,itemname);
        this.PurchasesPrice=PurchasesPrice;
    }
    
    // Method to set the Purchase Price of the SupplierItem
    public void setPurchasesPrice(double price){
        this.PurchasesPrice=price;
    }
    
    // Method to get the Item ID of the SupplierItem
    public String getItemID(){
        return item.getID();
    }
    
    // Method to get the Supplier ID of the SupplierItem
    public String getSuppID(){
        return supplier.getID();
    }
    
    // Method to get the Purchase Price of the SupplierItem
    public double getPrice(){
        return PurchasesPrice;
    }
    
    // Method to set the Supplier name
    public void setSupplierName(String name){
        this.supplier.setName(name);
    }
    
    // Method to set the Item name
    public void setItemName(String name){
        this.item.setName(name);
        
    }
    
    // Implementation of the Add method from the Manipulation interface
    @Override
    public void Add(){
        FileHandling fh= new FileHandling();
        String saveString=String.join(",", supplier.getID(),item.getID(),Double.toString(PurchasesPrice));
        if(fh.WriteFile(saveString, "supplieritem")){
            System.out.println("Supplier item successful to added");
        }
    }
    
    // Implementation of the Edit method from the Manipulation interface
    @Override
    public void Edit(){
        FileHandling fh=new FileHandling();
        String editString=String.join(",", supplier.getID(),item.getID(),Double.toString(PurchasesPrice));
        if(fh.EditSupplieritemFile(editString, "supplieritem")){
            System.out.println("Succesful to Edit");
        }
    }
    
    // Implementation of the Delete method from the Manipulation interface
    @Override
    public void Delete(){
        FileHandling fh=new FileHandling();
        String deleteString=String.join(",", supplier.getID(),item.getID(),Double.toString(PurchasesPrice));
        if(fh.DeleteSupplierItemContent(deleteString, "supplieritem")){
            System.out.println("Successful to remove supplier item");
        }
    }
    
    // Override the toString method to provide a string representation of the SupplierItem object
    @Override
    public String toString(){
        return "Supplier ID: \t\t"+supplier.getID()+"\nSupplier Name: \t\t"+supplier.getName()+"\nItemID: \t\t"+item.getID()+"\nItem Name: \t\t"+item.getName()+"\nPurchase Price: \t"+PurchasesPrice;
    }
}
