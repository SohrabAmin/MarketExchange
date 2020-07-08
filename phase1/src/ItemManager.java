import java.util.ArrayList;
import java.util.List;
public class ItemManager {

    private List<Item> systemInventory;

    private List<Item> deniedInventory;


    public ItemManager(){
        this.systemInventory = new ArrayList<Item>();
        this.deniedInventory = new ArrayList<Item>();
    }



}
