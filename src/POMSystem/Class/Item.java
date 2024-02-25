

package POMSystem.Class;

import POMSystem.Interface.Manipulation;



// The Item class implements the Manipulation interface
public class Item implements Manipulation{
    // Private instance variables to store item information
    private String ItemID;
    private String ItemName;
    private int Quantity;
    private double SalesPrice;
    private String Category;
    private int ROL;
    
    // Constructor to initialize an item with full details
    public Item(String itemid,String itemname,int quantity,double salesprice,String category,int ROL){
        this.ItemID=itemid;
        this.ItemName=itemname;
        this.Quantity=quantity;
        this.SalesPrice=salesprice;
        this.Category=category;
        this.ROL=ROL;
    }
    
    
    // Constructor to initialize an item with ID and name
    public Item(String itemId,String itemName){
        this.ItemID=itemId;
        this.ItemName=itemName;
    }
    
    // Constructor to initialize an item with basic details
    public Item(String itemname,int quantity,double salesprice,String category,int ROL){
        this.ItemName=itemname;
        this.Quantity=quantity;
        this.SalesPrice=salesprice;
        this.Category=category;
        this.ROL=ROL;
    }
    
    // Default constructor
    public Item(){}
    
    // Constructor to initialize an item with ID
    public Item(String id){
        this.ItemID=id;
    }
    
    // Getter method for retrieving item ID
    public String getID(){
        return ItemID;
    }
    
    // Getter method for retrieving item name
    public String getName(){
        return ItemName;
    }
    
    // Setter method for updating item name
    public void setName(String name){
        this.ItemName=name;
    }
    
    // Setter method for updating item quantity
    public void setQuantity(int quantity){
        this.Quantity=quantity;
    }
    
    // Setter method for updating item sales price
    public void setSalesPrice(double price){
        this.SalesPrice=price;
    }
    
    // Setter method for updating item category
    public void setCategory(String category){
        this.Category=category;
    }
    
    // Setter method for updating item reorder level
    public void setRoL(int ROL){
        this.ROL=ROL;
    }
    
    // Getter method for retrieving item quantity
    public int getQuantity(){
        return Quantity;
    }


    // Method to add an item to the system
    @Override
    public void Add(){
        FileHandling fh=new FileHandling();
        String id=Tools.id_Generate("Item", "item");
        System.out.println("ItemID\t\t"+id);
        String savestring=String.join(",", id,ItemName,Integer.toString(Quantity),Double.toString(SalesPrice),Category,Integer.toString(ROL));
        if(fh.WriteFile(savestring, "item")){
            System.out.println("Item have been susccessful Added");
        }
    }
    
    // Method to edit an item in the system
    @Override
    public void Edit(){
        FileHandling fh=new FileHandling();
        String edititem=String.join(",", ItemID,ItemName,Integer.toString(Quantity),Double.toString(SalesPrice),Category,Integer.toString(ROL));
        if(fh.EditFile(edititem, "item")){
            System.out.println("Succeful to edit item");
        }
    }
    
    // Method to delete an item from the system
    @Override
    public void Delete(){
        FileHandling fh=new FileHandling();
        if(fh.DeleteFilecontent(ItemID, "item")){
            System.out.println("Item Successful to delete");
        }
    }
    
    // Override the toString method to display item information
    @Override
    public String toString(){
        return "Item Name: \t\t"+ItemName+"\nQuantity: \t\t"+Quantity+"\nSales Price: \t\t"+SalesPrice+"\nCategory: \t\t"+Category+"\nReorder level: \t\t"+ROL;
    }
}
