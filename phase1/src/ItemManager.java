import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private List<Item> systemInventory;

    // private List<Item> deniedInventory;

    public ItemManager() {
        this.systemInventory = new ArrayList<>();
        // this.deniedInventory = new ArrayList<Item>();
    }

    public List<Item> getSystemInventory() {
        return systemInventory;
    }

    public void addItem(Item item) {
        systemInventory.add(item);
    }

    public void setOwner(Item item, User user) {
        item.setOwner(user);
    }

    public void setCurrentHolder(Item item, User user) {
        item.setCurrentHolder(user);
    }

}
