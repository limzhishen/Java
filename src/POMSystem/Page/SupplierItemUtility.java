

package POMSystem.Page;

import POMSystem.Class.FileHandling;
import POMSystem.Class.InputValidation;
import POMSystem.Class.Item;
import POMSystem.Class.Supplier;
import POMSystem.Class.SupplierItem;
import POMSystem.Class.Tools;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


// The SupplierItemUtility class is used for managing supplier items
public class SupplierItemUtility extends SupplierUtility{
    
    // Display the menu for Supplier Item entry options
    public void SupplierItemMenu(){
        System.out.println("-".repeat(75));
        System.out.println("\t\tSuppleir Item Entry");
        System.out.println("1. View Supplier item");
        System.out.println("2. Add Supplier item");
        System.out.println("3. Edit Supplier item");
        System.out.println("4. Delete Supplier item");
        System.out.println("5. Back to Menu");
    }
    
    // Handle Supplier Item entry functionalities
    public void SupplierItemEntry(){
        OUTER:
        while (true) {
            SupplierItemMenu();
            int select=InputValidation.getMenuSelectValid(5);
            // Handle user's selection based on the menu options
            switch (select) {
                case 1 -> {
                    // View Supplier items
                    System.out.println("\t\t\tSupplier Item View");
                    Tools.View(getSupplierItem(""));
                }
                case 2 -> // Add Supplier item
                    AddSupplierItem();
                case 3 -> // Edit Supplier item
                    EditSuppleirItem();
                case 4 -> // Delete Supplier item
                    DeleteSupplierItem();
                case 5 -> {
                    // Back to Menu
                    break OUTER;
                }
                default -> {
                }
            }
        }
    }
    
    // Method to add a new supplier item
    public void AddSupplierItem(){
        while (true){
            System.out.println("-".repeat(75));
            System.out.println("\t\tAdd Supplier Item");
            // Get the list of suppliers
            List<String> Supplier= getsupplierList();
            // Remove unnecessary columns from the supplier list
            Tools.removeColumns(Supplier, 2); //Remove the last two Column
            Tools.View(Supplier);// Display the supplier list
            System.out.println(Supplier.size()+"\t Exit");
            int select=InputValidation.getMenuSelectValid(Supplier.size());// Get the user's choice of supplier
            if (select==Supplier.size()){return;}
            String supplierId=Supplier.get(select).split(",")[0];
            String suppliername=Supplier.get(select).split(",")[1];
            List<String> item =getItemList();// Get the list of items
            Tools.removeColumns(item, 4);//Remove the last four Column
            List<String> supplieritem=getSupplieritemList();// Get the list of existing supplier items
            List<String> Additem= new ArrayList<>();
            for( int i=0 ;i< item.size();i++){//Find which item havn't add by this Supplier
                boolean have=false;
                for(int j=0;j<supplieritem.size();j++){
                    if(item.get(i).split(",")[0].equals(supplieritem.get(j).split(",")[1])&&(supplieritem.get(j).split(",")[0].equals(supplierId))){
                        have=true;
                        break;
                    }
                }   
                if (!have){
                    Additem.add(item.get(i));
                }
            }
            // If only one item can be added, proceed to add it
            if(Additem.size()!=1){
                Tools.View(Additem);
                System.out.println(Additem.size()+"\t Exit");
                int choice=InputValidation.getMenuSelectValid(Additem.size());
                if(choice==Additem.size())return;
                String itemid=Additem.get(choice).split(",")[0];
                String itemName=Additem.get(choice).split(",")[1];
                while(true){
                    System.out.print("Enter Purchases Price: ");
                    double price=InputValidation.getDouble();
                    if (price<=0){System.out.println("Price must bigger than 0");continue;}
                    // Display the information entered and ask for confirmation
                    System.out.println("Supplier ID:\t\t"+supplierId+"\nSupplier Name: \t\t"+suppliername+"\nItem ID: \t\t"+itemid+"\nItem Name: \t\t"+itemName+"\nPurchases Price: \t" +price);
                    System.out.println("1. Save");
                    System.out.println("2. Cancel");
                    System.out.print("Please selected your choice");
                    int decision=InputValidation.getMenuSelectValid(2);
                    if (decision==1){
                        // Create a new SupplierItem object and add it
                        SupplierItem supplierItem=new SupplierItem(supplierId,itemid,price);
                        supplierItem.Add();
                        return;
                    }
                    else if (decision==2){
                        return;
                    }
                }
            }
            else{
                System.out.println("No more Item can add with this supplier");
            }
        }
    }
    
    // Method to edit an existing supplier item
    public void EditSuppleirItem(){
        System.out.println("-".repeat(75));
        System.out.println("\t\t\t Edit Supplier Item");
        List<String> supplieritem=getSupplieritemList();
        Tools.View(getSupplierItem(""));
        System.out.println(supplieritem.size()+"\t Exit");
        int select=InputValidation.getMenuSelectValid(supplieritem.size());
        if(select==supplieritem.size()){return;}
        String[] selected=supplieritem.get(select).split(",");
        SupplierItem supplierItem=new SupplierItem(selected[0],selected[1],Double.parseDouble(selected[2]));
        Setname(supplierItem);
        OUTER:
        while (true) {
            System.out.println("-".repeat(50));
            System.out.println("\tSupplier Item Info");
            System.out.println(supplierItem);
            System.out.println("1. Edit Purchases Price");
            System.out.println("2. Save");
            System.out.println("3. Cancel edit");
            int decision=InputValidation.getMenuSelectValid(3);
            switch (decision) {
                case 1 -> {
                    System.out.print("Enter the new Purchases price: ");
                    double price=InputValidation.getDouble();
                    if (price>=0){
                        supplierItem.setPurchasesPrice(price);
                    }
                    else{
                        System.out.println("Purchases price cannot be negative");
                    }
                }
                case 2 -> {
                    supplierItem.Edit();
                    break OUTER;
                }
                case 3 -> {
                    break OUTER;
                }
                default -> {
                }
            }
        }
    }
    
    // Method to delete an existing supplier item
    public void DeleteSupplierItem(){
        System.out.println("-".repeat(75));
        System.out.println("\t\t Delete Supplier Item");
        List<String> supplieritem=getSupplieritemList();
        Tools.View(getSupplierItem(""));
        System.out.println(supplieritem.size()+"\t Exit");
        int select=InputValidation.getMenuSelectValid(supplieritem.size());
        if(select==supplieritem.size()){return;}
        String[] suppitem=supplieritem.get(select).split(",");
        SupplierItem supplierItem=new SupplierItem(suppitem[0], suppitem[1], Double.parseDouble(suppitem[2]));
        Setname(supplierItem);
        System.out.println("-".repeat(50));
        System.out.println(supplierItem);
        System.out.println();
        System.out.println("1. Delete");
        System.out.println("2. Cancel");
        System.out.print("Please select your choice: ");
        int choice=InputValidation.getMenuSelectValid(2);
        if (choice==1){
            supplierItem.Delete();
        }
        
    }
    
    // Retrieve and format supplier item data from files
    public List<String> getSupplierItem(String inputitemID){//combine the three text file item
        boolean hasInputitemId=!inputitemID.isEmpty();
        FileHandling fh=new FileHandling();
        Map<String, Supplier> suppliers = new HashMap<>();
        Map<String, Item> items = new HashMap<>();
        List<String> Supplieritemlist=fh.Readfile("supplieritem");
        List<String> SupplierList=fh.Readfile("supplier");
        List<String> ItemList=fh.Readfile("item");
        Supplieritemlist.remove(0);
        // Populate suppliers map
        for (String line : SupplierList){
            String[] data = line.split(",");
            String supplierId = data[0];
            String supplierName = data[1];
            suppliers.put(supplierId, new Supplier(supplierId,supplierName));
        }
        // Populate items map
        for (String line : ItemList) {
            String[] data = line.split(",");
            String itemId = data[0];
            String itemName = data[1];
            items.put(itemId, new Item(itemId,itemName));
        }
        
        Map<String, Map<String, Double>> supplierItemPurchases = new TreeMap<>();
        // Construct a map of supplier-item purchases
        for (String line:Supplieritemlist){
            String[]data=line.split(",");
            String supplierId=data[0];
            String itemId=data[1];
            double purchaseprice=Double.parseDouble(data[2]);
            Supplier supplier=suppliers.get(supplierId);
            Item item=items.get(itemId);
            if (supplier != null && item != null ) {
                if(!hasInputitemId||(hasInputitemId&&inputitemID.equals(itemId))){
                    // Check if the supplier already has item data
                    Map<String, Double> itemPurchases = supplierItemPurchases.getOrDefault(supplierId, new HashMap<>());
                    // Add the purchase price for the item
                    itemPurchases.put(itemId, purchaseprice);
                    // Update the purchases data for the supplier
                    supplierItemPurchases.put(supplierId, itemPurchases);
                }
            }
        }
        
        // Format the output for display
        List<String> formattedOutput = new ArrayList<>();
        formattedOutput.add("SupplierID,Suppliername,Item_ID,ItemName,PuchasesPrice");
        for (String supplierId : supplierItemPurchases.keySet()) {
            Supplier supplier = suppliers.get(supplierId);
            Map<String, Double> itemPurchases = supplierItemPurchases.get(supplierId);
            for (String itemId : itemPurchases.keySet()) {
                Item item = items.get(itemId);
                double purchasePrice = itemPurchases.get(itemId);
                String output = String.join(",",supplier.getID(),supplier.getName(),item.getID(),item.getName(),Double.toString(purchasePrice));
                formattedOutput.add(output);
            }
        }
        return formattedOutput;
    }
    
    // Set supplier and item names for a SupplierItem
    private void Setname(SupplierItem supplierItem){
        for (String i : getsupplierList()){
            if(i.split(",")[0].equals(supplierItem.getSuppID())){
                supplierItem.setSupplierName(i.split(",")[1]);
            }
        }
        for (String i : getItemList()){
            if(i.split(",")[0].equals(supplierItem.getItemID())){
                supplierItem.setItemName(i.split(",")[1]);
            }
        }
    }
}
