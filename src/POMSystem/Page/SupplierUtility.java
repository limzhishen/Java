

package POMSystem.Page;

import POMSystem.Class.InputValidation;
import POMSystem.Class.Supplier;
import POMSystem.Class.SupplierItem;
import java.util.ArrayList;
import java.util.List;
import POMSystem.Class.PurchasesRequisition;

// The SupplierUtility class handles supplier-related functionalities
public class SupplierUtility extends ItemManipulationUtility{
    
    // Display the menu for Supplier entry options
    public void SupplierMenu(){
        System.out.println("\t\tSupplier Entry");
        System.out.println("1. View Supplier");
        System.out.println("2. Add Supplier");
        System.out.println("3. Edit Supplier");
        System.out.println("4. Delete Supplier");
        System.out.println("5. Back to Menu");
    }

    // Handle Supplier entry functionalities
    public void SupplierEntry(){
        while (true) {
            System.out.println("-".repeat(75));
            SupplierMenu();
            int select=InputValidation.getMenuSelectValid(5);
            switch (select) {
                case 1 -> {System.out.println("\t\tSupplier View");ViewSupplier();}
                case 2 -> AddSupplier();
                case 3 -> EditSupplier();
                case 4 -> DeleteSupplier();
                case 5 -> {return;}
                default->{System.out.println("Invalid choice");}
            }
            
        }
    }
    
    // Method to add a new supplier
    public void AddSupplier(){
        System.out.println("-".repeat(50));
        System.out.println("\t\t Add Suplier");
        System.out.print("Enter supplier Name: ");
        String name=InputValidation.getString();
        System.out.print("Enter supplier Contact: ");
        String contact=InputValidation.getString("contact");
        System.out.print("Enter supplier location: ");
        String location=InputValidation.getString();
        Supplier newsuSupplier= new Supplier(name,contact,location);
        SuppliercheckValid(newsuSupplier);
    }
    
    // Check the validity of a new supplier and add if valid
    public void SuppliercheckValid(Supplier supplier){     
        List<String> SupplierList=getsupplierList();
        while(true){
            boolean valid=true;
            // Check if the supplier name is already existing
            for (String line :SupplierList){
                String[] suppliername=line.split(",");
                if(supplier.getName().equals(suppliername[1])){
                    valid=false;
                    break;
                }
            }
            System.out.println("-".repeat(50));
            System.out.println("\tSupplier Info");
            System.out.println(supplier);
            System.out.println("1. Add Supplier");
            System.out.println("2. Edit Supplier name");
            System.out.println("3. Cancel");
            // Display a message if the supplier name already exists
            if (!valid){
                System.out.println("Supplier already existing");
            }
            int select=InputValidation.getMenuSelectValid(3);
            switch (select) {
                case 1 -> {
                    if(valid){
                        supplier.Add();
                        return;
                    }
                }
                case 2 -> {
                    System.out.print("Enter Supplier Name: ");
                    String name=InputValidation.getString();
                    supplier.setName(name);
                }
                case 3 -> {
                    return;
                }
                default -> {
                }
            }
        }
    }
    
    // Display the menu for Supplier editing options
    public void SupplierEditmenu(){
        System.out.println("1. Name");
        System.out.println("2. Contact");
        System.out.println("3. Location");
        System.out.println("4. Save");
        System.out.println("5. Cancel Edit");
    }
    
    // Method to edit an existing supplier's details
    public void EditSupplier(){
        while(true){
            System.out.println("-".repeat(75));
            System.out.println("\t\t Edit Supplier");
            ViewSupplier();
            List<String> supplierList=getsupplierList();
            System.out.println(supplierList.size()+"\t Exit");
            int editid=InputValidation.getMenuSelectValid(supplierList.size());
            if(editid==supplierList.size()){break;}
            Supplier supplier =suppliervalid(editid);
            if(supplier!=null){
                while(true){
                    System.out.println("-".repeat(50));
                    System.out.println("\tSupplier Info");
                    System.out.println(supplier);
                    SupplierEditmenu();
                    int select=InputValidation.getMenuSelectValid(5);
                    switch(select){
                        case 1 ->{
                            System.out.print("Enter Supplier Name: ");
                            String name=InputValidation.getString();
                            supplier.setName(name);
                        }
                        case 2 ->{
                            System.out.print("Enter Supplier Contact: ");
                            String Contact=InputValidation.getString("contact");
                            supplier.setContact(Contact);
                        }
                        case 3 ->{
                            System.out.print("Enter Supplier Location: ");
                            String Location=InputValidation.getString();
                            supplier.setLocation(Location);
                        }
                        case 4 ->{
                            //if(valid){
                            supplier.Edit();return;
                        }
                        case 5 ->{return;}
                    }
                }
            }else{
                System.out.println("SupplierId is unavailable");
            }
        }
    }
    
    // Validate the supplier data for editing
    public Supplier suppliervalid(int editid){
        List<String>supplier=getsupplierList();
        String[]supp=supplier.get(editid).split(",");
            String SupplierID=supp[0];
            String Suppliername=supp[1];
            String Contact=supp[2];
            String Location=supp[3];
            return new Supplier(SupplierID,Suppliername,Contact,Location);
    }
    
    // Method to delete an existing supplier
     public void DeleteSupplier(){
        while(true){
            ViewSupplier();
            System.out.println(getsupplierList().size()+"\t Exit");
            int deletenum=InputValidation.getMenuSelectValid(getsupplierList().size());
            if (deletenum==getsupplierList().size()){return;}
            Supplier supplier=suppliervalid(deletenum);
            if(supplier!=null){
                List<SupplierItem> supplieritem=CheckSupplierItem(supplier);
                List<PurchasesRequisition> pr=CheckPurchasesRequisition(supplier);
                System.out.println(supplier);
                System.out.println("Supplier Support Item: "+supplieritem.size());
                System.out.println("Purchases Requisition on Pending: "+pr.size());
                System.out.println("1. Confirm Delete");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int select=InputValidation.getMenuSelectValid(2);
                if (select==1){
                        supplier.Delete();
                        for(SupplierItem si : supplieritem){
                            si.Delete();
                        }
                        for(PurchasesRequisition pritem : pr){
                            pritem.Delete();
                        }
                        break;
                }
                else if(select==2){
                    System.out.println("Cancel delete");
                    break;
                }
            }
            else {System.out.println("Invalid Input");}
        }
    }
     
     // Check if a supplier has associated supplier items
    private List<SupplierItem> CheckSupplierItem(Supplier supplier){
        List<String> supplierItemList= getSupplieritemList();
        List<SupplierItem> supplieritem=new ArrayList<>();
        for( String i :supplierItemList){
            String[] supplieritemStrings=i.split(",");
            if(supplieritemStrings[0].equals(supplier.getID())){
                supplieritem.add(new SupplierItem(supplieritemStrings[0],supplieritemStrings[1],Double.parseDouble(supplieritemStrings[2])));
            }
        }
        return supplieritem;
    }
    
    // Check if a supplier has pending purchases requisitions
    private List<PurchasesRequisition> CheckPurchasesRequisition(Supplier supplier){
        List<PurchasesRequisition> pr=new ArrayList<>();
        for (String i : getPrList()){
            String[] prStrings=i.split(",");
            if(prStrings[3].equals(supplier.getID())&&prStrings[7].equals("Pending")){
                PurchasesRequisition purchasesRequisition=new PurchasesRequisition(prStrings[0],prStrings[1],Integer.parseInt(prStrings[4]),prStrings[6],prStrings[7]);
                purchasesRequisition.setSuppleritem(new SupplierItem(prStrings[3],prStrings[2],Double.parseDouble(prStrings[5])));
                pr.add(purchasesRequisition);
            }
        }
        return pr;
    }
}
