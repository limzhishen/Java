

package POMSystem.Page;

import POMSystem.Class.FileHandling;
import POMSystem.Class.InputValidation;
import POMSystem.Class.Item;
import POMSystem.Class.SupplierItem;
import java.util.ArrayList;
import java.util.List;



public class ItemManipulationUtility extends ViewUtility{
    
    // Method to display the item entry menu
    public void ItemEntryMenu(){
        System.out.println("-".repeat(75));
        System.out.println("\t\tItem Entry");
        System.out.println("1. View Item");
        System.out.println("2. Add Item");
        System.out.println("3. Edit Item");
        System.out.println("4. Delete Item");
        System.out.println("5. Back to Menu");
    }
    
    // Method to perform item entry operations
     public void ItemEntry(){
        
        OUTER:
        while (true) {
            ItemEntryMenu();
            int select=InputValidation.getMenuSelectValid(5);
            switch (select) {
                case 1 -> {
                    System.out.println("\t\t\t Item View");
                    ViewItem();// View the list of items
                }
                case 2 -> addItem();// Add a new item
                case 3 -> editItem();// Edit an existing item
                case 4 -> deleteItem();// Delete an existing item
                case 5 -> {
                    break OUTER; // Return to the main menu
                }
                default -> {
                }
            }
        }
    }
    
    // Method to delete an item
    public void deleteItem(){
        while(true){
            System.out.println("\t\t\t Delete Item");
            ViewItem(); // View the list of items       
            System.out.println(getItemList().size()+"\t Exit");
            int deletenum=InputValidation.getMenuSelectValid(getItemList().size());
            if(deletenum==getItemList().size()){return;}// Exit the delete item operation
            Item item=itemvalid(deletenum);// Get the selected item
            List<SupplierItem> supplierItems=CheckSupplierItem(item);
            if(item!=null){
                System.out.println("-".repeat(50));
                System.out.println("Item Info: ");
                System.out.println(item);
                System.out.println("Supplier supply Quantity: "+supplierItems.size());
                System.out.println("1. Confirm Delete");
                System.out.println("2. Cancel Delete");
                int select=InputValidation.getMenuSelectValid(2);
                if (select==1){
                        item.Delete();// Delete the selected item
                        for(SupplierItem si:supplierItems){
                            si.Delete();
                        }
                        break;
                    }
                    
                
                else if(select==2){
                    return;// Cancel the delete operation
                }
            }
            else{
                System.out.println("Item is unavailable");
            }
        }
    }
         // Check if a supplier has associated supplier items
    private List<SupplierItem> CheckSupplierItem(Item item){
        List<String> supplierItemList= getSupplieritemList();
        List<SupplierItem> supplieritem=new ArrayList<>();
        for( String i :supplierItemList){
            String[] supplieritemStrings=i.split(",");
            if(supplieritemStrings[1].equals(item.getID())){
                supplieritem.add(new SupplierItem(supplieritemStrings[0],supplieritemStrings[1],Double.parseDouble(supplieritemStrings[2])));
            }
        }
        return supplieritem;
    }
    
    // Method to add a new item
    public void addItem(){
        System.out.println("-".repeat(50));
        System.out.println("\t\t Add item ");
        System.out.print("Enter item Name: ");
        String itemName=InputValidation.getString();
        if(itemName.equals("-1")){return;}// Cancel adding the item
        System.out.print("Enter item Quantity: ");
        int Quantity=InputValidation.getInt();
        Quantity=InputValidation.validnumber("Enter item Quantity: ", Quantity, 0);
        System.out.print("Enter Sales Price: ");
        double salesprice=InputValidation.getDouble();
        salesprice=InputValidation.validnumber("Enter Sales Price: ", salesprice, 0);
        System.out.print("Enter Category: ");
        String category= InputValidation.getString();
        System.out.print("Reorder level Settings: ");
        int rol=InputValidation.getInt();
        rol=InputValidation.validnumber("Reorder level Settings: ", rol, 1);
        Item newitem = new Item(itemName,Quantity,salesprice,category,rol);
        itemcheckValid(newitem);// Check if the item name is valid and add the item
        
    }
    
    // Method to check if an item is valid and handle its addition
    public void itemcheckValid(Item item){
        FileHandling fh=new FileHandling();
        List<String> itemList=fh.Readfile("item");
        OUTER:
        while (true) {
            boolean valid=true;
            for (String i:itemList){
                String[] detail=i.split(",");
                if (item.getName().equals(detail[1])){
                    valid=false;
                }
            }
            System.out.println("-".repeat(50));
            System.out.println("\tItem Info");
            System.out.println(item);
            System.out.println("1. Add Item");
            System.out.println("2. Edit item name");
            System.out.println("3. Cancel");
            if (!valid){
                System.out.println("Item name already existing");
            }
            int select=InputValidation.getMenuSelectValid(3);
            switch (select) {
                case 1 -> {
                    if(valid){
                        item.Add();// Add the item to the list
                        return;
                    }
                }
                case 2 -> {
                    System.out.print("Enter Item Name: ");
                    String name=InputValidation.getString();
                    item.setName(name);// Edit the item name
                }
                case 3 -> {
                    break OUTER; // Cancel the addition operation
                }
                default -> {
                }
            }
        }
        

    }
    
    // Method to display options for item attributes to be edited
    public void itemchange(){
        System.out.println("1. Name");
        System.out.println("2. Quantity");
        System.out.println("3. Sales Price");
        System.out.println("4. Category");
        System.out.println("5. Reorder level");
        System.out.println("6. Save");
        System.out.println("7. Cancel edit");
    }

    // Method to edit an existing item
    public void editItem(){
        while(true){
            System.out.println("\t\t\t Edit Item");
            ViewItem();// View the list of items
            System.out.println(getItemList().size()+"\t Exit");
            System.out.print("Enter the line number that item you want to edit");
            int editline=InputValidation.getMenuSelectValid(getItemList().size());
            if (editline==getItemList().size()){return;}// Exit the edit item operation
            Item item=itemvalid(editline);// Get the selected item
            if(item!=null){
                while(true){
                    System.out.println("-".repeat(50));
                    System.out.println("Item info :");
                    System.out.println(item);
                    itemchange();// Display options for item attributes to be edited
                    int select=InputValidation.getMenuSelectValid(7);
                    switch (select) {
                        case 1 -> {
                            // Edit item name
                            System.out.print("Enter item name: ");
                            String name=InputValidation.getString();
                            item.setName(name);
                        }case 2 -> {
                            System.out.print("Enter item Quantity: ");
                            int Quantity=InputValidation.getInt();
                            Quantity=InputValidation.validnumber("Enter item Quantity: ", Quantity, 0);
                            item.setQuantity(Quantity);
                        }
                        case 3 -> {
                            System.out.print("Enter item Sales Price: ");
                            double SalesPrice=InputValidation.getDouble();
                            SalesPrice=InputValidation.validnumber("Enter Sales Price: ", SalesPrice, 0);
                            item.setSalesPrice(SalesPrice);
                        }
                        case 4 -> {
                            System.out.print("Enter item Category: ");
                            String Category=InputValidation.getString();
                            item.setCategory(Category);
                        }
                        case 5 -> {
                            System.out.print("Enter item Reorder Level: ");
                            int ROL=InputValidation.getInt();
                            ROL=InputValidation.validnumber("Reorder level Settings: ", ROL, 1);
                            item.setRoL(ROL);
                        }
                        case 6 -> {item.Edit();return;}// Save the edited item
                        case 7 -> {return;}// Cancel the edit operation
                    }
                }
            }
            else{
                System.out.println("ItemId is unavailable");
            }
        }
        
    }
    
    // Method to validate and retrieve an item based on its line number
    public Item itemvalid(int editline){ 
        List<String> item=getItemList();
        String[] line=item.get(editline).split(",");
        String ItemID=line[0];
        String ItemName=line[1];
        int Quantity=Integer.parseInt(line[2]);
        double SalesPrice=Double.parseDouble(line[3]);
        String Category=line[4];
        int ROL=Integer.parseInt(line[5]);
        return new Item(ItemID,ItemName,Quantity,SalesPrice,Category,ROL);

    }
}
