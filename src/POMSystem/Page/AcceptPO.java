

package POMSystem.Page;

import POMSystem.Class.FileHandling;
import POMSystem.Class.InputValidation;
import POMSystem.Class.Item;
import java.util.ArrayList;
import java.util.List;
import POMSystem.Class.Tools;
import POMSystem.Class.PurchasesOrder;
import POMSystem.Class.User;


public class AcceptPO extends PRUtility{
    // This method handles the acceptance of purchased items (Purchase Orders) by a user.
    public void AcceptItem(User user){
        // Get the list of Purchase Orders that are on delivery
        List<String> po=getPOStatusList();
        
        // Check if there are Purchase Orders on delivery
        if(po.size()!=1){
            System.out.println("\t\t Accept Arrived Purchases Order");
            Tools.View(po);// Display the list of Purchase Orders
            System.out.println(po.size()+"\t Exit");
            
            // Prompt the user to select a Purchase Order to accept
            int select=InputValidation.getMenuSelectValid(po.size());
            if (select==po.size()){return;}// If user chooses to exit, return from the method
            
            // Retrieve Purchase Order details from the selected option
            String[] poStrings=po.get(select).split(",");
            PurchasesOrder purchasesOrder=new PurchasesOrder(poStrings[0],poStrings[1],poStrings[2],poStrings[3],poStrings[4],poStrings[5],poStrings[6],poStrings[7],user);
            System.out.println("-".repeat(50));
            System.out.println(purchasesOrder);// Display Purchase Order details
            System.out.println("-".repeat(50));
            System.out.println("1. Arrive this Purchase Order");
            System.out.println("2. Cancel");
            
            // Prompt the user to confirm or cancel the arrival of the Purchase Order
            int decision=InputValidation.getMenuSelectValid(2);
            if (decision==1){
                // Set the status of the Purchase Order to "Arrive"
                purchasesOrder.setStatus("Arrive");
                purchasesOrder.Edit();// Save the changes
                Item item=getItem(purchasesOrder);// Retrieve the corresponding Item
                item.setQuantity(item.getQuantity()+purchasesOrder.getQuantity());
                item.Edit();// Update the Item's quantity
            }
        }
        else{
            System.out.println("No item is on delivery");
        }
    }
    
    // This method retrieves the list of Purchase Orders that are on delivery status.
    public List<String> getPOStatusList(){
        FileHandling fh=new FileHandling();
        List<String> po=fh.Readfile("po");
        List<String> postatus=new ArrayList<>();
        postatus.add(po.get(0));// Add header line
        // Iterate through the Purchase Orders and add those with "Delivery" status
        for (String i : po){
            if(i.split(",")[7].equals("Delivery")){
                postatus.add(i);
            }
        }
        return postatus;
    }
    
    // This method retrieves the corresponding Item of a given Purchase Order.
    public Item getItem(PurchasesOrder purchasesOrder){
        List<String>item=getItemList();// Get the list of Items
        // Iterate through the Items and find the matching Item
        for(String i : item){
            if (i.split(",")[0].equals(purchasesOrder.getitemid())){
                String[] itemString=i.split(",");
                // Create and return an Item object
                return(new Item(itemString[0],itemString[1],Integer.parseInt(itemString[2]),Double.parseDouble(itemString[3]),itemString[4],Integer.parseInt(itemString[5])));
            }
        }
        return null;// Return null if no matching Item is found
    }
}
