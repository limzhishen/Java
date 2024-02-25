

package POMSystem.Page;

import POMSystem.Class.InputValidation;
import POMSystem.Interface.Menu;
import POMSystem.Class.Sales_Manager;


// Page class for the Sales Manager
public class SalesManagerPage extends AcceptPO implements Menu{
    private final Sales_Manager sales_Manager;
    
    // Constructor for SalesManagerPage
    public SalesManagerPage(Sales_Manager sales_Manager){
        this.sales_Manager=sales_Manager;
    }
    private boolean Logout=false;
    
    // Method to display the Sales Manager page
    public void smpage(){
        while (!Logout) {            
                System.out.println("-".repeat(75));
                System.out.println("\t\tWelcome to Sales Manager Page "+sales_Manager.getName());
                displayMenu();
                Selection();
        }
        
    }
    
    // Override method to display the menu options
    @Override
    public void displayMenu(){
        System.out.println("1.  Item Entry");
        System.out.println("2.  Supplier Entry");
        System.out.println("3.  Supplier item Entry");
        System.out.println("4.  Dairy Sales Entry");
        System.out.println("5.  Purchases Requsition Entry");
        System.out.println("6.  View Purchases Order");
        System.out.println("7.  Accept Order");
        System.out.println("8.  Edit Profile");
        System.out.println("9.  Logout");
        
    }
    
    // Method to handle user's selection
    public void Selection(){
        int select=InputValidation.getMenuSelectValid(9);
        switch (select) {
            case 1 -> ItemEntry();
            case 2 -> SupplierEntry();
            case 3 -> SupplierItemEntry();
            case 4 -> DairySalesEntry(sales_Manager);
            case 5 -> PurchasesRequisitionEntry(sales_Manager);
            case 6 -> ViewPo();
            case 7 -> AcceptItem(sales_Manager);
            case 8 -> EditProfile(sales_Manager);
            case 9 -> {Logout=true;}
            default -> System.out.println("Invalid Input");
        }

    }
}
