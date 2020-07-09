import java.util.ArrayList;
import java.util.List;
public class ItemManager {

    private List<Item> systemInventory;

  //  private List<Item> deniedInventory;


    public ItemManager(){
        this.systemInventory = new ArrayList<Item>();
       // this.deniedInventory = new ArrayList<Item>();
    }

    
    public List<Item> getSystemInventory() {
        return systemInventory; 
    }

    
    public void addItem (Item item){
        systemInventory.add(item);
        
    }


    public void setOwner(Item y, User x){
        y.setOwner(x);
    }

    public void setCurrentHolder(Item y, User x){
        y.setCurrentHolder(x);
    }

    
}
