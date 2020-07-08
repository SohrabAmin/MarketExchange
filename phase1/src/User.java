import java.util.ArrayList;
import java.util.List;

public class User extends Account{

    public ArrayList<Item> wishlist;
    private ArrayList<Item> inventory;
    private ArrayList<Item> draftInventory;
    private boolean isFrozen;
    private int eligibility;
    private List<Transaction> tradeHistory;


    public User(String name,String password){
        this.name = name;
        this.password = password;
        wishlist = new ArrayList<Item>();
        inventory = new ArrayList<Item>();
        draftInventory= new ArrayList<Item>();
        this.tradeHistory = new ArrayList<Transaction>();
    }


    public ArrayList<Item> getWishlist(){
        return wishlist;
    }

    public ArrayList<Item> getInventory(){ return inventory;}

    public ArrayList<Item> getDraftInventory (){ return draftInventory; }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(boolean f) {
        this.isFrozen = f;

    }

    public int getEligibility(){
        return eligibility;

    }

    public void increaseEligibility(int itemsLent){
        this.eligibility += itemsLent;
    }

    public void decreaseEligibility(int itemsBorrowed){
        this.eligibility -= itemsBorrowed;
    }

}


