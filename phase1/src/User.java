import java.util.ArrayList;
import java.util.List;

public class User extends Account {

    public ArrayList<Item> wishlist;
    private ArrayList<Item> inventory;
    private ArrayList<Item> draftInventory;
    private boolean isFrozen;
    private int eligibility;
    private List<Transaction> tradeHistory;
    private List<User> topTradingPartners;
    private List<TradeRequest> pendingRequests;

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        wishlist = new ArrayList<>();
        inventory = new ArrayList<>();
        draftInventory = new ArrayList<>();
        this.tradeHistory = new ArrayList<>();
        this.topTradingPartners = new ArrayList<>();
        this.pendingRequests = new ArrayList<>();

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

    public List<Transaction> getTradeHistory(){
        return this.tradeHistory;
    }

    public void addTradeHistory(Transaction x){
        this.tradeHistory.add(x);
    }

    public List<TradeRequest> getPendingRequests(){
        return this.pendingRequests;

    }

    public void setTopTradingPartners(User x, User y, User z){
        this.topTradingPartners.clear();
        this.topTradingPartners.add(x);
        this.topTradingPartners.add(y);
        this.topTradingPartners.add(z);
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
