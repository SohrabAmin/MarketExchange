import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * represent a User with name and password. Show user's wishlist, inventory, draft inventory( items added but not
 * approved or declined by admin),
 */
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
    private List<Transaction> pendingTrades;
    private boolean isPseudoFrozen;
    private List<Transaction> cancelledTransactions;
    private List<Transaction> agreedUponMeeting;


    public User() {
    }
    /**
     * constructs an instance of User with name and password and a list of wishlist, inventory
     * draft inventory( items waiting for admin's approval), trade history, pending request (received but not approve
     * declined),
     *
     * @param name of this user as a string
     * @param password of this user as a string
     */

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
        agreedUponMeeting = new ArrayList<>();

    }

    /**
     * getter for
     * @return
     */

    public HashMap<Item, String> getItemHistory() {
        return ItemHistory;
    }
    /**
     * getter for user's wishlist
     * @return this user's withlist as a list
     */
    public ArrayList<Item> getWishlist() {
        return wishlist;
    }

    /**
     * getter for user's inventory
     * @return this user's inventory as a ArrayList
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * getter for user's draft inventory which invluding the items the user add to the inventory but waiting for admin's approval.
     * @return this user's draft inventory as a ArrayList
     */
    public ArrayList<Item> getDraftInventory() {
        return draftInventory;
    }

    /**
     * getter for user's sucessful Trade History
     * @return this user's past sucessful trade as a list of transactions.
     */
    public List<Transaction> getTradeHistory() {
        return this.tradeHistory;
    }

    /**
     * setter for user's sucessful trade history
     * @param transaction to add a transaction to this user's trade history
     */
    public void addTradeHistory(Transaction transaction) {
        this.tradeHistory.add(transaction);
    }

    /**
     * getter for user's pending requests that user receives
     * @return this user's pending requests as a List.
     */
    public List<TradeRequest> getPendingRequests() {
        return this.pendingRequests;
    }

    /**
     * getter
     * @return
     */
    public List<User> getTopTradingPartners() {
        return topTradingPartners;
    }

    /**
     * getter for trade requests that user send out for a trade
     * @return trade requests sent by this user as a List.
     */
    public List<TradeRequest> getOutboundRequests() {
        return outboundRequests;
    }

    /**
     * getter for the trades that is not completed but the meeting has been set up
     * @return List of transactions for this user's trade that is not yet completed
     */
    public List<Transaction> getPendingTrades() {
        return pendingTrades;
    }

    /**
     * setter for the requests sent out by the user
     * @param outboundRequest to add Trade requests to the list of requests sent out by this user
     */
    public void addOutboundRequest(TradeRequest outboundRequest) {
        outboundRequests.add(outboundRequest);
    }

    /**
     * getter for cancelled transactions
     * @return the cancelled transactions as a List
     */
    public List<Transaction> getCancelledTransactions() {
        return cancelledTransactions;
    }

    public void setTopTradingPartners(List<User> topTradingPartners) {
        this.topTradingPartners.clear();
        this.topTradingPartners.addAll(topTradingPartners);
    }

    /**
     * getter for
     * @return
     */
    public List<Transaction> getAgreedUponMeeting(){ return agreedUponMeeting;}

    /**
     * getter for if user is frozen,
     * @return this user's frozen status( frozen or not frozen)
     */
    public boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     * setter for if user is frozen
     * @param isFrozen to change the user's status of frozen or not frozen as boolean
     */
    public void setIsFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;

    }

    /**
     * gettter for if user is PseudoFrzoen, PseuodoFrzoen means that the user's trade  is over the limit of weekly trades
     * or incom
     * @return
     */
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
