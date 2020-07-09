import java.util.ArrayList;
import java.util.List;

public class User extends Account {

    public ArrayList<Item> wishlist;
    private ArrayList<Item> inventory;
    private ArrayList<Item> draftInventory;
    private boolean isFrozen;
    private int eligibility;
    private List<Transaction> tradeHistory;

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        wishlist = new ArrayList<>();
        inventory = new ArrayList<>();
        draftInventory = new ArrayList<>();
        this.tradeHistory = new ArrayList<>();
    }

    
    
    
    public ArrayList<Item> getWishlist() {
        return wishlist;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Item> getDraftInventory() {
        return draftInventory;
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;

    }

    public int getEligibility() {
        return eligibility;

    }

    public void increaseEligibility() {
        this.eligibility += 1;
    }

    public void decreaseEligibility() {
        this.eligibility -= 1;
    }

}


