

package POMSystem.Class;


import POMSystem.Interface.Manipulation;

public class DairySales implements Manipulation{
    private String Salesid;
    private Item item;
    private int SalesQuantity;
    private String userid;
    private String date;
    public DairySales(User user,Item item,int salesquantity,String date){
        this.userid=user.getId();
        this.item=item;
        this.SalesQuantity=salesquantity;
        this.date=date;
    }
    public DairySales(String salesid,User user,Item item,int salesquantity,String date){
        this.Salesid=salesid;
        this.userid=user.getId();
        this.item=item;
        this.SalesQuantity=salesquantity;
        this.date=date;
    }
    public DairySales(String salesid,String userid,String itemid,int salesquantity,String date){
        this.Salesid=salesid;
        this.userid=userid;
        this.item=new Item(itemid);
        this.SalesQuantity=salesquantity;
        this.date=date;
    }
    public void setSalesQuantity(int quantity){
        this.SalesQuantity=quantity;
    }
    public void setDate(String date){
        this.date=date;
    }
    public int getSalesQuanity(){
        return SalesQuantity;
    }
    public void setUser(User user){
        this.userid=user.getId();
    }
    @Override
    public void Add(){
        FileHandling fh= new FileHandling();
        /*Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);*/
        String id= Tools.id_Generate("SA", "dairysales");
        String saveString=String.join(",", id,userid,item.getID(),Integer.toString(SalesQuantity),date);
        if(fh.WriteFile(saveString, "dairysales")){
            System.out.println("Dairy Sales have succesful to add");
        }
    }
    @Override
    public void Edit(){
        FileHandling fh= new FileHandling();
        String editString=String.join(",",Salesid,userid, item.getID(),Integer.toString(SalesQuantity),date);
        if(fh.EditFile(editString, "dairysales")){
            System.out.println("Dairy Sales have succesfull edited");
        }else{
            System.out.println("Something Got wrong");
        }
    }
    @Override
    public void Delete(){
        FileHandling fh=new FileHandling();
        if(fh.DeleteFilecontent(Salesid, "dairysales")){
            System.out.println("Dairy Sales Successful to delete");
        }
    }
    @Override
    public String toString(){
        return String.join("\n", "SalesId: \t"+Salesid,"UserId: \t"+userid,"ItemId: \t"+item.getID(),"SalesQuantity: \t"+Integer.toString(SalesQuantity),"Date: \t\t"+date);
    }
}
