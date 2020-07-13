import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User extends Account {

    private ArrayList<Item> wishlist;
    private ArrayList<Item> inventory;
    private ArrayList<Item> draftInventory;
    private HashMap<Item, String> ItemHistory;
    private boolean isFrozen;
    private int eligibility;
    private List<Transaction> tradeHistory;
    private List<User> topTradingPartners;
    private List<TradeRequest> pendingRequests;
    private List<TradeRequest> outboundRequests;
    private List<Meeting> pendingTrades;
    private boolean isPseudoFrozen;
    private List<Transaction> cancelledTransactions;


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
        this.ItemHistory = new HashMap<>();//Creating HashMap
        outboundRequests = new ArrayList<>();
        pendingTrades = new ArrayList<>();
        cancelledTransactions = new ArrayList<>();

    }

    public HashMap<Item, String> getItemHistory() {
        return ItemHistory;
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

    public List<User> getTopTradingPartners() {
        return topTradingPartners;
    }

    public List<TradeRequest> getOutboundRequests() {
        return outboundRequests;
    }

    public List<Meeting> getPendingTrades() {
        return pendingTrades;
    }

    public void addOutboundRequest(TradeRequest outboundRequest) {
        outboundRequests.add(outboundRequest);
    }

    public void addPendingTrade(Meeting meeting) {
        pendingTrades.add(meeting);
    }

    public List<Transaction> getCancelledTransactions() {
        return cancelledTransactions;
    }

    public void setTopTradingPartners(List<User> topTradingPartners) {
        this.topTradingPartners.clear();
        this.topTradingPartners.addAll(topTradingPartners);
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;

    }

    public boolean getIsPseudoFrozen() {
        return isPseudoFrozen;
    }

    public void setIsPseudoFrozen(boolean isPseudoFrozen) {
        this.isPseudoFrozen = isPseudoFrozen;
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

    //TODO: consider overriding the equals method inherited from Object
}
