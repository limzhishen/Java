

package POMSystem.Page;

import POMSystem.Class.FileHandling;
import POMSystem.Class.Tools;
import java.util.List;




public class ViewUtility extends EditProfileUtility{
    //Method to retrieve the list of items from the "item" file.
    public List<String> getItemList(){
        // Create a new instance of FileHandling for reading files
        FileHandling fh= new FileHandling();
        return fh.Readfile("item");//Return the list of items.
    }
    //Method to retrieve the list of suppliers from the "supplier" file.
    public List<String> getsupplierList(){
        FileHandling fh=new FileHandling();
        return fh.Readfile("supplier");//Return the list of suppliers.
    }
    //Method to retrieve the list of PRs (Purchase Requests) from the "pr" file.
    public List<String> getPrList(){
        FileHandling fh=new FileHandling();
        return fh.Readfile("pr");//Return the list of PRs.
    }
    //Method to retrieve the list of POs (Purchase Orders) from the "po" file.
    public List<String> getPOList(){
        FileHandling fh= new FileHandling();
        return fh.Readfile("po");//Return the list of POs.
    }
    //Method to retrieve the list of supplier items from the "supplieritem" file.
    public List<String> getSupplieritemList(){
        FileHandling fh=new FileHandling();
        return fh.Readfile("supplieritem");//Return the list of supplier items.
    }
    //Method to view the list of items by using the Tools.View() method.
    public void ViewItem(){
        // Get the list of items
        List<String> item=getItemList();
        // Use the Tools.View() method to display the list of items
        Tools.View(item);
    }
    //Method to view the list of suppliers by using the Tools.View() method.
    public void ViewSupplier(){
        List<String> supplier=getsupplierList();
        Tools.View(supplier);
    }
    //Method to view the list of PRs (Purchase Requests) by using the Tools.View() method.
    public void ViewPR(){
        Tools.View(getPrList());
    }
    //Method to view the list of POs (Purchase Orders) by using the Tools.View() method.
    public void ViewPo(){
        Tools.View(getPOList());
    }
}
