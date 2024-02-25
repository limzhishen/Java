

package POMSystem.Page;

import POMSystem.Class.User;
import POMSystem.Class.InputValidation;
import java.util.ArrayList;
import java.util.List;
import POMSystem.Class.Tools;
import POMSystem.Class.Item;
import POMSystem.Class.SupplierItem;
import POMSystem.Class.PurchasesRequisition;


// This class handles Purchases Requisition related functionalities
public class PRUtility extends DairySalesUtility{
    
    // Display the menu for Purchases Requisition options
    public void PrMenu(){
        System.out.println("-".repeat(75));
        System.out.println("\t\tPurchases Requisition Entry");
        System.out.println("1. View Purchases Requisition ");
        System.out.println("2. Add Purchases Requisition");
        System.out.println("3. Edit Purchases Requisition");
        System.out.println("4. Delete Purchases Requisition");
        System.out.println("5. Back to Menu");
    }
    
    // Handle Purchases Requisition entry functionalities
    public void PurchasesRequisitionEntry(User user){
        while (true){
            PrMenu();
            int select=InputValidation.getMenuSelectValid(5);
            switch(select){
                case 1 -> {System.out.println("\t\t\t View Purchases Requisition");ViewPR();}
                case 2 -> AddPR( user);
                case 3 -> EditPR(user);
                case 4 -> DeletePR(user);
                case 5 -> {return;}
            }
            
        }
    }
    
    // Method to add a new Purchases Requisition
    public void AddPR(User user){
        System.out.println("-".repeat(75));
        System.out.println("\t\t Add Purchases Requisition");
        List<String> ROLitem=getRolitem();
        if(ROLitem.size()!=1){
            // Display items with Reorder Level for selection
            Tools.View(ROLitem);
            System.out.println(ROLitem.size()+"\t Exit");
            int select=InputValidation.getMenuSelectValid(ROLitem.size());
            if(select==ROLitem.size()){return;}
            String[] newitem=ROLitem.get(select).split(",");
            Item item= new Item(newitem[0],newitem[1],Integer.parseInt(newitem[2]),Double.parseDouble(newitem[3]),newitem[4],Integer.parseInt(newitem[5]));
            System.out.println("-".repeat(50));
            System.out.println(item);
            System.out.print("Enter Quantity: ");
            int Quantity=InputValidation.getInt();
            if(Quantity<=0){
                System.out.println("Quantity Cannot be 0");
                return;
            }
            // Select supplier items for the chosen item
            List<String> supplieritemList=getSupplierItem(item.getID());//from supplieritemUtility          
            ArrayList<SupplierItem> supplier=new ArrayList<>();
            if(supplieritemList.size()!=1){
                while(true){
                    System.out.println("-".repeat(50));
                    System.out.println("\t\t Select Supplier");
                    Tools.View(supplieritemList);
                    System.out.println(supplieritemList.size()+"\t Exit");
                    int choice=InputValidation.getMenuSelectValid(supplieritemList.size());
                    if(choice==supplieritemList.size()){break;}
                    String[] supplieritemline=supplieritemList.get(choice).split(",");
                    supplieritemList.remove(choice);
                    supplier.add(new SupplierItem(supplieritemline[0],supplieritemline[1],supplieritemline[2],supplieritemline[3],Double.parseDouble(supplieritemline[4])));
                    if(supplieritemList.size()==1){
                        System.out.println("No Supplier Can Add");
                        break;
                    }
                }
            }
            else{
                System.out.println("No Supplier have supplier this good");
            }
            // Create Purchases Requisition if supplier items are selected
            if(!supplier.isEmpty()){
                String date=InputValidation.getDate();
                System.out.println("-".repeat(50));
                for (SupplierItem i : supplier){
                System.out.println(i+"\n");
                }
                System.out.println("Item ID: "+item.getID()+"\nItem Name: " +item.getName()+"\nQuantity: "+Quantity+"\nExpecter Date: "+date);
                System.out.println("1. Create  Requisition");
                System.out.println("2. Cancel Requisition");
                int decision=InputValidation.getMenuSelectValid(2);
                if (decision==1){
                    String id=Tools.id_Generate("PR", "pr");
                    for(SupplierItem supplieridItem : supplier){
                        PurchasesRequisition pr=new PurchasesRequisition(id, user, Quantity, date);
                        pr.setSuppleritem(supplieridItem);
                        pr.Add();
                    }
                }
            }
            else{
                System.out.println("No Supplier Added");
            }

            
        }
        else{
            System.out.println("No Item reach the Reorder level");
        }
    }
    
    // Retrieve a list of items with Reorder Level
    private List<String> getRolitem(){
        List<String> ROLitem=new ArrayList<>();
        List<String> itemList=getItemList();
        ROLitem.add(itemList.get(0));
        itemList.remove(0);
        for (String line : itemList){
            String[] item=line.split(",");
            if(Integer.parseInt(item[item.length-1])>=Integer.parseInt(item[2])){
                ROLitem.add(line);
            }
        }
        
        // Filter out items that are already being handled by a PO
        if(ROLitem.size()!=1){
            List<String> po=getPOList();
            List<String> remove=new ArrayList<>();
            for (String poline:po){
                if(!poline.split(",")[7].equals("Arrive")){
                    String[] poStrings=poline.split(",");
                    for(String rolline:ROLitem){
                        String[] rolStrings=rolline.split(",");
                        if(rolStrings[0].equals("ItemID")){continue;}
                        if(rolStrings[0].equals(poStrings[2])){
                            if((Integer.parseInt(rolStrings[2])+Integer.parseInt(poStrings[5]))>Integer.parseInt(rolStrings[5])){
                                
                                remove.add(rolline);
                            }
                        }
                    }
                }
            }
            
            ROLitem.removeAll(remove);
        }
        return ROLitem;
    }
    
    // Method to edit a Purchases Requisition
    public void EditPR(User user){
        System.out.println("\t\t Edit Purchases Requisition");
        List<String> pr=getRespectivePRlList(user,"edit");
        if(pr.size()!=1){
            // Display available PRs for editing
            Tools.View(pr);
            System.out.println(pr.size()+"\tExit");
            System.out.println("The PRID same will Edit together");
            int select=InputValidation.getMenuSelectValid(pr.size());
            if(select==pr.size()){
                return;
            }
            List<PurchasesRequisition> PR=new ArrayList<>();
            for (String i : pr){
                String[] prStrings=i.split(",");
                if(prStrings[0].equals(pr.get(select).split(",")[0])){

                    PurchasesRequisition purchasesRequisition=new PurchasesRequisition(prStrings[0],user,Integer.parseInt(prStrings[4]),prStrings[6],prStrings[7]);
                    purchasesRequisition.setSuppleritem(new SupplierItem(prStrings[3],prStrings[2],Double.parseDouble(prStrings[5])));
                    PR.add(purchasesRequisition);
                }
            }
            OUTER:
            while (true) {
                System.out.println("\t\t Editing Purchases Requisition Info");
                System.out.println("-".repeat(100));
                for(PurchasesRequisition purchasesRequisition :PR){
                    System.out.println(purchasesRequisition);
                }
                System.out.println("1. Quantity");
                System.out.println("2. Expected date");
                System.out.println("3. Save and Exit");
                System.out.println("4. Cancel and Exit");
                int choice =InputValidation.getMenuSelectValid(4);
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter new Quantity: ");
                        int Quantity=InputValidation.getInt();
                        for(PurchasesRequisition purchasesRequisition :PR){
                            purchasesRequisition.setQuantity(Quantity);
                        }
                    }
                    case 2 -> {
                        String date=InputValidation.getDate();
                        for(PurchasesRequisition purchasesRequisition :PR){
                            purchasesRequisition.setDate(date);
                        }
                    }
                    case 3 -> {
                        for(PurchasesRequisition purchasesRequisition :PR){
                            purchasesRequisition.Edit();
                        }   break OUTER;
                    }
                    default -> {
                        break OUTER;
                    }
                }
            }
        }
        else{
            System.out.println(user.getName()+" don't have create any Purchases Requisition");
        }
    }
    public List<String> getRespectivePRlList(User user,String type){
        List<String> PrList=getPrList();
        List<String> RespectivePRList=new ArrayList<>();
        RespectivePRList.add(PrList.get(0));
        if (user.getId().substring(0, user.getId().length()-3).equals("Adm")){
            for (String i :PrList){
                if(i.split(",")[7].equals("Pending")){
                    RespectivePRList.add(i);
                }
            }
            return RespectivePRList;
        }
        else{
            if(type.equals("edit")){
                for (String i :PrList){
                    if(i.split(",")[1].equals(user.getId())&&i.split(",")[7].equals("Pending")){
                        RespectivePRList.add(i);
                    }
                }
            }
            else if(type.equals("delete")){
                for (String i :PrList){
                    if(i.split(",")[1].equals(user.getId())&&(!i.split(",")[7].equals("Approval"))){
                        RespectivePRList.add(i);
                    }
                }
            }

            return RespectivePRList;
        }
    }
    public void DeletePR(User user){
        System.out.println("\t\t\t Delete Purchases Requisition");
        List<String>pr=getRespectivePRlList(user,"delete");
        if(pr.size()!=1){
            Tools.View(pr);
            System.out.println(pr.size()+"\tExit");
            System.out.println("The PRID same will delete together");
            int select=InputValidation.getMenuSelectValid(pr.size());
            if(select==pr.size()){
                return;
            }
            List<PurchasesRequisition> PR=new ArrayList<>();
            for (String i : pr){
                String[] prStrings=i.split(",");
                if(prStrings[0].equals(pr.get(select).split(",")[0])){
                    PurchasesRequisition purchasesRequisition=new PurchasesRequisition(prStrings[0],user,Integer.parseInt(prStrings[4]),prStrings[6],prStrings[7]);
                    purchasesRequisition.setSuppleritem(new SupplierItem(prStrings[3],prStrings[2],Double.parseDouble(prStrings[5])));
                    PR.add(purchasesRequisition);
                }
            }
            System.out.println("-".repeat(75));
            for(PurchasesRequisition purchasesRequisition :PR){
                    System.out.println(purchasesRequisition);
                }
            System.out.println("1. Delete");
            System.out.println("2. Cancel ");
            int decision=InputValidation.getMenuSelectValid(2);
            if (decision==1){
                for(PurchasesRequisition purchasesRequisition :PR){
                    purchasesRequisition.Delete();
                }
            }
        }
        else{
            System.out.println(user.getName()+" don't have any Purchases Requisition");
        }
    }

}
