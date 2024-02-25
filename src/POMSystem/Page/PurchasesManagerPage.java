

package POMSystem.Page;

import POMSystem.Class.InputValidation;
import POMSystem.Class.Purchases_Manager;
import POMSystem.Interface.Menu;

// Page class for the Purchases Manager
public class PurchasesManagerPage extends POUtility implements Menu{
    private final Purchases_Manager purchases_Manager;
    private boolean Logout=false;
    
    // Constructor for PurchasesManagerPage
    public PurchasesManagerPage(Purchases_Manager purchases_Manager){
        this.purchases_Manager=purchases_Manager;
    }
    
    // Method to display the Purchases Manager page
    public void pmpage(){
    while (!Logout) {         
            System.out.println("-".repeat(75));
            System.out.println("\t\tWelcome to Purchases Manager Page "+purchases_Manager.getName());
            displayMenu();
            Selection();
        }
        
    }
    
    // Override method to display the menu options
    @Override
    public void displayMenu(){
        System.out.println("1.  View Item ");
        System.out.println("2.  View Supplier ");
        System.out.println("3.  View Purchases Requsition");
        System.out.println("4.  Purchases Order Entry");
        System.out.println("5.  Edit Profile");
        System.out.println("6.  Logout");
        
    }
    
    // Method to handle user's selection
    public void Selection(){
        int select=InputValidation.getMenuSelectValid(6);
        switch (select) {
            case 1 -> ViewItem();
            case 2 -> ViewSupplier();
            case 3 -> ViewPR();
            case 4 -> PurchasesOrderEntry(purchases_Manager);
            case 5 -> EditProfile(purchases_Manager);
            case 6 -> {Logout=true;}
            default -> System.out.println("Invalid Input");
        }

    }
}
