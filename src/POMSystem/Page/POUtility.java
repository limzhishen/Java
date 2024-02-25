

package POMSystem.Page;

import POMSystem.Class.FileHandling;
import POMSystem.Class.InputValidation;
import POMSystem.Class.User;
import java.util.ArrayList;
import java.util.List;
import POMSystem.Class.Tools;
import POMSystem.Class.PurchasesRequisition;
import POMSystem.Class.SupplierItem;
import POMSystem.Class.PurchasesOrder;

// Utility class for handling Purchases Order related operations
public class POUtility extends ViewUtility{
    
    // Display the Purchases Order menu options
    public void PoMenu(){
        System.out.println("-".repeat(75));
        System.out.println("\t\tPurchases Order Entry");
        System.out.println("1. View Purchases Order");
        System.out.println("2. Generate Purchases Order ");
        System.out.println("3. Edit Rejected Purchases Requisition");
        System.out.println("4. Reject Purchases Requisition");
        System.out.println("5. Delete Fail Purchases Order");
        System.out.println("6. Back to Menu");
    }
    
    // Main method for Purchases Order Entry functionality
    public void PurchasesOrderEntry(User user){
        while (true){
            PoMenu();
            int select=InputValidation.getMenuSelectValid(6);
            switch(select){
                case 1 -> {System.out.println("\t\t\tView Purchases Order");ViewPo();}
                case 2 -> GeneratePO( user);
                case 3 -> EditRejectedPO(user);
                case 4 -> RejectPR(); 
                case 5 -> DeleteFailPO(user);
                case 6 -> {return;}
            }
            
        }
    }
    
    // Edit Rejected Purchases Requisitions
    public void EditRejectedPO(User user){
        List<String>pr =getPRstatus("Reject");
        if(pr.size()!=1){
            Tools.View(pr);
            System.out.println(pr.size()+"\t Exit");
            int select=InputValidation.getMenuSelectValid(pr.size());
            if(select==pr.size()){return;}
            CreatePO(pr, select, user);
        }
        else{
            System.out.println("No Rejected Purchases Requisition");
        }
    }
    
    // Generate Purchases Order based on Pending PRs
    public void GeneratePO(User user){
        System.out.println("\t\t\tGenerate Purchases Order");
        List<String>pr=getPRstatus("Pending");
        if(pr.size()!=1){
            Tools.View(pr);
            System.out.println(pr.size()+"\t Exit");
            int select=InputValidation.getMenuSelectValid(pr.size());
            if(select==pr.size()){return;}
            CreatePO(pr, select, user);
        }
        else{System.out.println("No Purchases Requisition");}

    }
    
    // Delete Failed Purchases Orders
    public void DeleteFailPO(User user){
        List<String> po=getRespectivePO(user,"Delivery");
        if(po.size()!=1){
            Tools.View(po);
            System.out.println(po.size()+"\tExit");
            int select=InputValidation.getMenuSelectValid(po.size());
            if (select==po.size()){return;}
            String[] poStrings=po.get(select).split(",");
            PurchasesOrder purchasesOrder=new PurchasesOrder(poStrings[0],poStrings[1],poStrings[2],poStrings[3],poStrings[4],poStrings[5],poStrings[6],poStrings[7],user);
            System.out.println("-".repeat(50));
            System.out.println(purchasesOrder);
            System.out.println("1. Delete");
            System.out.println("2. Cancel");
            int decision=InputValidation.getMenuSelectValid(2);
            if (decision==1){
                List<String> pr=getPrList();
                for (String i:pr){
                    if(i.split(",")[0].equals(purchasesOrder.getPrid())){
                        String[] prStrings=i.split(",");
                        PurchasesRequisition purchasesRequisition=new PurchasesRequisition(prStrings[0],user,Integer.parseInt(prStrings[4]),prStrings[6],prStrings[7]);
                        purchasesRequisition.setSuppleritem(new SupplierItem(prStrings[3],prStrings[2],Double.parseDouble(prStrings[5])));
                        purchasesRequisition.Delete();
                        purchasesOrder.Delete();
                    }
                }
            }
        }
        else{
            System.out.println("No have any Delivery Purchases Order");
        }
    }
    
    // Get a list of Respective Purchases Orders based on status
    public List<String> getRespectivePO(User user,String status){
        List<String> PoList=getPOList();
        List<String> StatusPolist=new ArrayList<>();
        StatusPolist.add(PoList.get(0));
        if (user.getId().substring(0,user.getId().length()-3).equals("Adm")){
            
            for (String i : PoList){
                if(i.split(",")[7].equals(status)){
                    StatusPolist.add(i);
                    
                }
            }
        }else {
            for (String i : PoList){
                if(i.split(",")[7].equals(status)&&user.getId().equals(i.split(",")[1])){
                    StatusPolist.add(i);
                }
            }
        }
        return StatusPolist;
        
    }
    
    // Create Purchases Order based on selected PR
    private void CreatePO(List<String> pr,int select,User user){
        String[] prStrings=pr.get(select).split(",");
        
        PurchasesRequisition purchasesRequisition=new PurchasesRequisition(prStrings[0],prStrings[1],Integer.parseInt(prStrings[4]),prStrings[6],prStrings[7]);
        purchasesRequisition.setSuppleritem(new SupplierItem(prStrings[3],prStrings[2],Double.parseDouble(prStrings[5])));
        System.out.println("-".repeat(50));
        System.out.println(purchasesRequisition);
        System.out.println("1. Generate PO");
        System.out.println("2. Cancel");
        int decision=InputValidation.getMenuSelectValid(2);
        if (decision==1){
            //List<PurchasesRequisition> deletepr=new ArrayList<>();
            for (String i : pr){
                String[]prString=i.split(",");
                if(i.split(",")[0].equals(purchasesRequisition.getPRID())&&!i.split(",")[3].equals(purchasesRequisition.getSuppid())){               
                    PurchasesRequisition prdelete=new PurchasesRequisition(prString[0],prString[1],Integer.parseInt(prString[4]),prString[6],prString[7]);
                    prdelete.setSuppleritem(new SupplierItem(prString[3],prString[2],Double.parseDouble(prString[5])));
                    prdelete.Delete();
                }
            }
            purchasesRequisition.setStatus("Approval");
            purchasesRequisition.Edit();
            PurchasesOrder po=new PurchasesOrder(purchasesRequisition,user);
            po.Add();
            
        }
        else if (decision==2){System.out.println("Succesful to cancel");}
    }
    
    // Get PR status based on a given status
    public List<String> getPRstatus(String status){
        FileHandling fh=new FileHandling();
        List<String> pr=fh.Readfile("pr");
        List<String> pendingpr=new ArrayList<>();
        pendingpr.add(pr.get(0));
        for (String pending: pr){
            if(pending.split(",")[7].equals(status)){
                pendingpr.add(pending);
            }
        }
        return pendingpr;
    }
    
    // Reject a Purchases Requisition
    public void  RejectPR(){
        System.out.println("\t\t Reject Purchases Requisition");
        List<String>pr=getPRstatus("Pending");
        if(pr.size()!=1){
            Tools.View(pr);
            System.out.println(pr.size()+"\t Exit");
            int select=InputValidation.getMenuSelectValid(pr.size());
            if(select==pr.size()){return;}
            String[] prStrings=pr.get(select).split(",");
            PurchasesRequisition purchasesRequisition=new PurchasesRequisition(prStrings[0],prStrings[1],Integer.parseInt(prStrings[4]),prStrings[6],prStrings[7]);
            purchasesRequisition.setSuppleritem(new SupplierItem(prStrings[3],prStrings[2],Double.parseDouble(prStrings[5])));
            System.out.println("-".repeat(50));
            System.out.println(purchasesRequisition);
            System.out.println("1. Reject Purchases Requisition");
            System.out.println("2. Cancel");
            int decision=InputValidation.getMenuSelectValid(2);
            if (decision==1){
            //List<PurchasesRequisition> RejectprList=new ArrayList<>();
            for (String i : pr){
                String[]prString=i.split(",");
                if(i.split(",")[0].equals(purchasesRequisition.getPRID())){
                    PurchasesRequisition RejectPr=new PurchasesRequisition(prString[0],prString[1],Integer.parseInt(prString[4]),prString[6],prString[7]);
                    RejectPr.setSuppleritem(new SupplierItem(prString[3],prString[2],Double.parseDouble(prString[5])));
                    RejectPr.setStatus("Reject");
                    RejectPr.Edit();
                }
            }
            
        }
        else if (decision==2){System.out.println("Succesful to cancel");}
            
        }
        else{System.out.println("No Purchases Requisition");}
    }
    
    
}
