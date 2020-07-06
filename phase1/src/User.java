import java.util.ArrayList;

public class User {

    private String name;
    private String password;
    private ArrayList<Item> wishlist;
    private ArrayList<Item> InventoryList;
    private ArrayList<Item> DraftInventoryList;

    public User(String name,String password){
        this.name = name;
        this.password = password;
        wishlist = new ArrayList<Item>();
        InventoryList = new ArrayList<Item>();
        DraftInventoryList = new ArrayList<Item>();
    }

    public ArrayList<Item> getWishlist(){
        return wishlist;
    }
    public void addToWishlist(Item item){
        wishlist.add(item);
    }
    public void removeFromWishlist(Item item){
        wishlist.remove(item);
    }

    public ArrayList<Item> getInventoryList{ return InventoryList;}
    public ArrayList<Item> getDraftInventoryList{ return DraftInventoryList; }
}

