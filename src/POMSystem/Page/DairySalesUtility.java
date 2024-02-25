

package POMSystem.Page;

import POMSystem.Class.DairySales;
import POMSystem.Class.FileHandling;
import POMSystem.Class.InputValidation;
import POMSystem.Class.Item;
import POMSystem.Class.Tools;
import java.util.List;
import POMSystem.Class.User;
import java.util.Iterator;





public class DairySalesUtility extends SupplierItemUtility{
    //Displays the Dairy Sales menu options.
    private void DairySalesMenu(){
        System.out.println("-".repeat(75));
        System.out.println("\t\tDairy Sales");
        System.out.println("1. View Dairy Sales");
        System.out.println("2. Add Dairy Sales");
        System.out.println("3. Edit Dairy Sales");
        System.out.println("4. Delete Dairy Sales");
        System.out.println("5. Exit to Menu");
    }
    //Allows the user to interact with the Dairy Sales functionalities.
    public void DairySalesEntry(User user){
        OUTER:
        while (true) {
            DairySalesMenu();
            int select =InputValidation.getMenuSelectValid(5);
            switch (select) {
                case 1 -> ViewDairySales();
                case 2 -> AddDairySales(user);
                case 3 -> EditDairySales(user);
                case 4 -> DeleteDairySales();
                case 5 -> {
                    break OUTER;
                }
                default -> {
                }
            }
        }
    }
    //Retrieves the list of Dairy Sales from the "dairysales" file.
    public List<String> getDairySalesList(){
        FileHandling fh= new FileHandling();
        return fh.Readfile("dairysales");//Return the list of Dairy Sales.
        
    }
    public List<String> getvalidItem(){
        List<String> itemList= getItemList();
        Iterator<String> iterator = itemList.iterator();
        while (iterator.hasNext()) {
        String item = iterator.next();
        if (item.split(",")[2].equals("0")) {
            iterator.remove();
        }
    }

    return itemList;
    }
    //Adds a Dairy Sales entry.
    public void AddDairySales(User user){//Param user, the User object representing the current user.
        System.out.println("-".repeat(50));
        System.out.println("\t\t\t Add Dairy Sales");
        List<String> itemList= getvalidItem();
        Tools.View(itemList);
        System.out.println(itemList.size()+"\t Exit");
        int select=InputValidation.getMenuSelectValid(itemList.size());
        if(select==itemList.size()){return;}
        // Extract details of the selected item
        String[] selectedItem = itemList.get(select).split(",");
        String itemid = selectedItem[0];
        String itemname = selectedItem[1];
        int quantity = Integer.parseInt(selectedItem[2]);
        double salesprice = Double.parseDouble(selectedItem[3]);
        String category = selectedItem[4];
        int ROL = Integer.parseInt(selectedItem[5]);
        // Create an Item object
        Item item = new Item(itemid, itemname, quantity, salesprice, category, ROL);
        System.out.println(item);
        int salesQuantity;
        while(true){
            System.out.print("Enter Sales quantity: ");
            salesQuantity=InputValidation.getInt();
            if(salesQuantity>item.getQuantity()){
                System.out.println("Sales Quantity bigger than stock");
            }
            else if (salesQuantity==0){
                System.out.println("Sales Quantity Cannot be 0");
            }
            else if(salesQuantity>0){break;}
            
        }
        String date=InputValidation.getDate();
        // Display details before confirming the addition
        System.out.println(item);
        System.out.println("Quantity: \t\t"+salesQuantity);
        System.out.println("Date: \t\t"+date);
        System.out.println("1. Save");
        System.out.println("2. Exit");
        int choice=InputValidation.getMenuSelectValid(2);
        if (choice==1){
            // Create and add Dairy Sales entry
            item.setQuantity(item.getQuantity()-salesQuantity);
            item.Edit();
            DairySales dairySales = new DairySales(user,item,salesQuantity,date);
            dairySales.Add();
        }

    }
    //Allows editing of an existing Dairy Sales entry.
    public void EditDairySales(User user){
        System.out.println("\t\t Edit Dairy Sales");
        List<String> dairysales= getDairySalesList();
        Tools.View(dairysales);
        System.out.println(dairysales.size()+"\t Exit");
        int select=InputValidation.getMenuSelectValid(dairysales.size());
        if(select==dairysales.size()){return;}
        DairySales dairySales=getDairySales(dairysales.get(select));
        while(true){
            System.out.println("-".repeat(50));
            System.out.println("\t Dairy Sales Info");
            System.out.println(dairySales);
            System.out.println("Please select which you want to change");
            System.out.println("1. Quantity");
            System.out.println("2. Date");
            System.out.println("3. Save and exit");
            System.out.println("4. Cancel");
            int choice =InputValidation.getMenuSelectValid(4);
            if (choice==1){
                System.out.print("Enter New Quantity: ");
                int quantity=InputValidation.getInt();
                dairySales.setSalesQuantity(quantity);
            }
            else if (choice==2){
                String dt=InputValidation.getDate();
                dairySales.setDate(dt);
            }
            else if (choice==3){
                if(dairySales.getSalesQuanity()>0){
                    dairySales.setUser(user);
                    dairySales.Edit();
                    break;
                }else{
                    System.out.println("Please Input valid quantity");
                }
            }
            else if(select==4){
                break;
            }
        }
  
    }
    //Allows deletion of a Dairy Sales entry.
    public void DeleteDairySales( ){
        System.out.println("\t\t Delete Dairy Sales");
        List<String> dairysales= getDairySalesList();
        Tools.View(dairysales);
        System.out.println(dairysales.size()+"\t Exit");
        int select=InputValidation.getMenuSelectValid(dairysales.size());
        if(select==dairysales.size()){return;}
        DairySales dairySales=getDairySales(dairysales.get(select));
        System.out.println("-".repeat(50));
        System.out.println("\t Dairy Sales Info");
        System.out.println(dairySales);
        System.out.println("Are you sure want to delete");
        System.out.println("1. Delete");
        System.out.println("2. Cancel");
        int choice=InputValidation.getMenuSelectValid(2);
        if(choice==1){
            dairySales.Delete();
        }
    }
    //Retrieves a Dairy Sales object based on its details from the list.
    private DairySales getDairySales(String dairysales){//Param dairysales, the string representation of Dairy Sales details.
        String[] selected=dairysales.split(",");
        String salesid=selected[0];
        String userid=selected[1];
        String itemid=selected[2];
        int quantity=Integer.parseInt(selected[3]);
        String date=selected[4];
        DairySales dairySales= new DairySales(salesid,userid,itemid,quantity,date);
        return dairySales;//Return the corresponding Dairy Sales object.
    }
    //Displays the list of Dairy Sales entries.
    public void ViewDairySales(){
        System.out.println("\t\t View Dairy Sales");
        for (String i : getDairySalesList()){
            System.out.println(i);
        }
        Tools.View(getDairySalesList());
    }
}
