
package POMSystem.Class;


public class FileLocation{
    enum Filepath {
        user("User.txt"),
        supplier("Supplier.txt"),
        item("Item.txt"),
        supplieritem("SupplierItem.txt"),
        dairysales("DairySales.txt"),
        pr("Purchases_requisition.txt"),
        po("Purchases_order.txt");
        private String path;
        Filepath(String path){
            this.path=path;
        }
        public String getPath(){
            return path;
        }
    }
        
}
