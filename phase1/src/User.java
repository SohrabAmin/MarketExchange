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

    public List<Transaction> getTradeHistory() {
        return this.tradeHistory;
    }

    public void addTradeHistory(Transaction transaction) {
        this.tradeHistory.add(transaction);
    }

    public List<TradeRequest> getPendingRequests() {
        return this.pendingRequests;

    }

    public void updatePendingRequests(TradeRequest request) {
        this.pendingRequests.add(request);
    }

    public void setTopTradingPartners(User partner1, User partner2, User partner3) {
        this.topTradingPartners.clear();
        this.topTradingPartners.add(partner1);
        this.topTradingPartners.add(partner2);
        this.topTradingPartners.add(partner3);
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
