import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemManager implements Serializable {

    private List<Item> systemInventory;

    private List<Item> tradableItem;

    private List<Item> sellableItem;

    private List<Item> rentableItem;

    // private List<Item> deniedInventory;

    public ItemManager() {
        this.systemInventory = new ArrayList<>();
        this.tradableItem = new ArrayList<>();
        this.sellableItem = new ArrayList<>();
        this.rentableItem = new ArrayList<>();
    }

    public List<Item> getSystemInventory() {
        return systemInventory;
    }
    public List<Item> getTradableItem(){return this.tradableItem;}
    public List<Item> getSellableItem(){return this.sellableItem;}
    public List<Item> getRentableItem(){return this.rentableItem;}

    public void addItem(Item item) {
        systemInventory.add(item);
        this.sortItem(item);
    }

    public void setOwner(Item item, User user) {
        item.setOwner(user);
    }

    public void setCurrentHolder(Item item, User user) {
        item.setCurrentHolder(user);
    }

    public void setSystemInventory(List<Item> inventory) {
        this.systemInventory = inventory;
    }

    public void changeTradable(Item item, boolean tradable){
        item.changeTradable(tradable);
    }
    public void changeSellable(Item item, boolean sellable){
        item.changeSellable(sellable);
    }
    public void changeRentable(Item item, boolean rentable){
        item.changeRentable(rentable);
    }


    public void sortItem(Item item){
        if(item.getTradable()){
            this.tradableItem.add(item);
        }else if(item.getRentable()){
            this.rentableItem.add(item);
        }else{
            this.sellableItem.add(item);
        }
    }

}
