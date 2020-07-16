import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * represent a User with name and password. store and getter for all values of a user.
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
    private List<Transaction> secondAgreedUponMeeting;
    private Map<Integer, List<TradeRequest>> weeklyRequestLimit;



    public User() {
    }
    /**
     * constructs an instance of User with name and password and a list of wishlist, inventory
     * draftInventory means items being added to inventory but waiting for admin's approval,
     * tradeHistory: past 3 trades
     * pendingRequest: trade requests that are received but not replied yet.
     * ItemHistory: all the items being added to the inventory( even though it is traded).
     * outboundRequests: trade requests that are sent out by the user
     * pendingTrades: user and his trading partner have agreed to trade but trade is not finished.
     * cancelledTransactions: transactions that are cancelled by the users
     * agreedUponMeeting: user and his partner have agreed on the final meeting but the meeting has not happened yet
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
        secondAgreedUponMeeting = new ArrayList<>();
        this.weeklyRequestLimit = new HashMap<>();

        for (int i =1; i<53; i++){
            List<TradeRequest> temp = new ArrayList<>();
            this.weeklyRequestLimit.put(i, temp);
        }

    }

    /**
     * getter for itemHistory
     * @return all the items that has been added to inventory no matter that item is traded or not.
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
     * setter for user's successful trade history
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
     * getter for the top 3 most frequent trading partners
     * @return the 3 most frequent trading partners as a List of User
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
     * getter for the trades that is not completed but the trade is not finished yet.
     * @return List of transactions for this user's trade that is not yet completed
     */
    public List<Transaction> getPendingTrades() {
        return pendingTrades;
    }

//    /**
//     * setter for the requests sent out by the user
//     * @param outboundRequest to add Trade requests to the list of requests sent out by this user
//     */
//    public List <TradeRequest>  getOutboundRequest(TradeRequest outboundRequest) {
//        return outboundRequests;
//    }

    /**
     * getter for cancelled transactions
     * @return the cancelled transactions as a List
     */
    public List<Transaction> getCancelledTransactions() {
        return cancelledTransactions;
    }

    /**
     * setter for topTradingPartners
     * @param topTradingPartners to add or change the 3 most frequent trading partners to the list
     */
    public void setTopTradingPartners(List<User> topTradingPartners) {
        this.topTradingPartners.clear();
        this.topTradingPartners.addAll(topTradingPartners);
    }

    /**
     * getter for agreedUponMeeting
     * @return a list of  transactions of which the meeting is finalized but not happened
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
     * gettter for if user is PseudoFrzoen, A pseudo-frozen User is prevented from conducting transactions until
     * an Admin decides to either freeze the User or let the User slide
     * @return the status if this user is PseudoFrozen as a boolean
     */
    public boolean getIsPseudoFrozen() {
        return isPseudoFrozen;
    }
    /**
     * setter for isPseodoFrozen
     * @param isPseudoFrozen to this user as if he is pseudofrozen
     */
    public void setIsPseudoFrozen(boolean isPseudoFrozen) {
        this.isPseudoFrozen = isPseudoFrozen;
    }
    /**
     * getter for eligibility. eligibility is the number of items this user can trade
     * @return the number of items this user can trade as int
     */
    public int getEligibility() {
        return eligibility;
    }
    /**
     * set the eligibility increase one unit per item this user lent
     */
    public void increaseEligibility() {
        this.eligibility += 1;
    }
    /**
     * set the eligibility decreases one unit per item this user borrowed
     */
    public void decreaseEligibility() {
        this.eligibility -= 1;
    }

    //TODO: consider overriding the equals method inherited from Object

    public List<Transaction> getSecondAgreedUponMeeting(){
        return this.secondAgreedUponMeeting;
    }

    public Map<Integer, List<TradeRequest>> getWeeklyRequestLimit(){
        return this.weeklyRequestLimit;
    }

}
