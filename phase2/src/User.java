import java.util.ArrayList;
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
    private List<typeThreeRequest> approvedThreeWay;
    private List<TradeRequest> outboundRequests;
    private List<Transaction> pendingTrades;
    private boolean isPseudoFrozen;
    private List<Transaction> cancelledTransactions;
    private List<Transaction> agreedUponMeetings;
    private List<Transaction> secondAgreedUponMeetings;
    private Map<Integer, List<TradeRequest>> weeklyRequestLimit;
    private Map<String, Integer> FrequentCategory;
    private String location;
    private Integer points = 0;
    private boolean isOnVacation;
    private boolean isVIP;
    private List<Item> vacationStorage;
    private ArrayList<String> notifyUndo;
    private ArrayList<String> adminActionHistory;
    private ArrayList<String> AdminMessages;
    private double capital = 250; //US dollars
    private List<CreditCard> creditCards;
    private CreditCard defaultCreditCard;

    //potential hash map for outbound requests to help admins undo


    public User() {
    }

    /**
     * constructs an instance of User with name and password
     *
     * @param name     of this user as a string
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
        this.approvedThreeWay = new ArrayList<>();
        this.ItemHistory = new HashMap<>();//Creating HashMap
        outboundRequests = new ArrayList<>();
        pendingTrades = new ArrayList<>();
        cancelledTransactions = new ArrayList<>();
        agreedUponMeetings = new ArrayList<>();
        secondAgreedUponMeetings = new ArrayList<>();
        this.weeklyRequestLimit = new HashMap<>();
        this.FrequentCategory = new HashMap<>();
        notifyUndo = new ArrayList<>();
        adminActionHistory = new ArrayList<>();
        AdminMessages = new ArrayList<>();
        vacationStorage = new ArrayList<>();
        this.creditCards = new ArrayList<>();

        for (int i = 1; i < 53; i++) {
            List<TradeRequest> temp = new ArrayList<>();
            this.weeklyRequestLimit.put(i, temp);
        }

        //initializing the frequentCategories
        this.FrequentCategory.put("Electronics", 0);
        this.FrequentCategory.put("Automotive and car accessories", 0);
        this.FrequentCategory.put("Baby", 0);
        this.FrequentCategory.put("Beauty, Health and Personal Care", 0);
        this.FrequentCategory.put("Books", 0);
        this.FrequentCategory.put("Home and Kitchen Supplies", 0);
        this.FrequentCategory.put("Clothing", 0);
        this.FrequentCategory.put("Movies, music and TV", 0);
        this.FrequentCategory.put("Office Supplies", 0);
        this.FrequentCategory.put("Gaming", 0);
    }


    public Map<String, Integer> getFrequentCategory() {
        return FrequentCategory;
    }

    /**
     * getter for itemHistory
     *
     * @return all the items that has been added to inventory no matter that item is traded or not.
     */

    public HashMap<Item, String> getItemHistory() {
        return ItemHistory;
    }

    /**
     * getter for user's wishlist
     *
     * @return this user's wishlist as a list
     */
    public ArrayList<Item> getWishlist() {
        return wishlist;
    }

    /**
     * getter for user's inventory
     *
     * @return this user's inventory as a ArrayList
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * getter for user's draft inventory which including the items the user add to the inventory but waiting for admin's approval.
     *
     * @return this user's draft inventory as a ArrayList
     */
    public ArrayList<Item> getDraftInventory() {
        return draftInventory;
    }

    /**
     * getter for user's successful Trade History
     *
     * @return this user's past successful trade as a list of transactions.
     */
    public List<Transaction> getTradeHistory() {
        return this.tradeHistory;
    }

    /**
     * setter for user's successful trade history
     *
     * @param transaction to add a transaction to this user's trade history
     */
    public void addTradeHistory(Transaction transaction) {
        this.tradeHistory.add(transaction);
    }

    /**
     * getter for user's pending requests that user receives
     *
     * @return this user's pending requests as a List.
     */
    public List<TradeRequest> getPendingRequests() {
        return this.pendingRequests;
    }

    /**
     * getter for the top 3 most frequent trading partners
     *
     * @return the 3 most frequent trading partners as a List of User
     */
    public List<User> getTopTradingPartners() {
        return topTradingPartners;
    }

    /**
     * getter for trade requests that user send out for a trade
     *
     * @return trade requests sent by this user as a List.
     */
    public List<TradeRequest> getOutboundRequests() {
        return outboundRequests;
    }

    /**
     * getter for the trades that is not completed but the trade is not finished yet.
     *
     * @return List of transactions for this user's trade that is not yet completed
     */
    public List<Transaction> getPendingTrades() {
        return pendingTrades;
    }

    /**
     * getter for cancelled transactions
     *
     * @return the cancelled transactions as a List
     */
    public List<Transaction> getCancelledTransactions() {
        return cancelledTransactions;
    }

    /**
     * setter for topTradingPartners
     *
     * @param topTradingPartners to add or change the 3 most frequent trading partners to the list
     */
    public void setTopTradingPartners(List<User> topTradingPartners) {
        this.topTradingPartners.clear();
        this.topTradingPartners.addAll(topTradingPartners);
    }

    /**
     * getter for agreedUponMeeting
     *
     * @return a list of  transactions of which the meeting is finalized but not happened
     */
    public List<Transaction> getAgreedUponMeeting() {
        return agreedUponMeetings;
    }

    /**
     * getter for if user is frozen,
     *
     * @return this user's frozen status( frozen or not frozen)
     */
    public boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     * setter for if user is frozen
     *
     * @param isFrozen to change the user's status of frozen or not frozen as boolean
     */
    public void setIsFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;

    }

    /**
     * getter for if user is PseudoFrozen, A pseudo-frozen User is prevented from conducting transactions until
     * an Admin decides to either freeze the User or let the User slide
     *
     * @return the status if this user is PseudoFrozen as a boolean
     */
    public boolean getIsPseudoFrozen() {
        return isPseudoFrozen;
    }

    /**
     * setter for isPseudoFrozen
     *
     * @param isPseudoFrozen to this user as if he is pseudo-frozen
     */
    public void setIsPseudoFrozen(boolean isPseudoFrozen) {
        this.isPseudoFrozen = isPseudoFrozen;
    }

    /**
     * Getter for whether this User is on vacation. A User on vacation cannot participate in the trade system, and will
     * not have their items listed in the system, until they return from vacation.
     *
     * @return whether this User is on vacation
     */
    public boolean getIsOnVacation() {
        return this.isOnVacation;
    }

    /**
     * Setter for whether this User in on vacation. A User on vacation cannot participate in the trade system, and will
     * not have their items listed in the system, until they return from vacation.
     *
     * @param isOnVacation whether this User should be on vacation
     */
    public void setIsOnVacation(boolean isOnVacation) {
        this.isOnVacation = isOnVacation;
    }

    /**
     * Getter for whether this User is a VIP
     *
     * @return whether this User is a VIP
     */
    public boolean getIsVIP() {
        return this.isVIP;
    }

    /**
     * Setter for whether this User is a VIP
     *
     * @param isVIP whether this User should be a VIP
     */
    public void setIsVIP(boolean isVIP) {
        // new VIP privilege: increase lent - borrowed ratio
        if (!this.isVIP && isVIP) {
            for (int i = 0; i < 10; i++) {
                this.increaseEligibility();
            }
        }

        // undo VIP privilege
        if (this.isVIP && !isVIP) {
            for (int i = 0; i < 10; i++) {
                // do not set lent - borrowed ratio below 0
                if (this.getEligibility() == 0) {
                    break;
                }
                this.decreaseEligibility();
            }
        }

        this.isVIP = isVIP;
    }

    /**
     * getter for eligibility. eligibility is the number of items this user can trade
     *
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

    /**
     * Getter for this User's list of trade-back Transactions
     *
     * @return This User's list of trade-back Transactions
     */
    public List<Transaction> getSecondAgreedUponMeeting() {
        return this.secondAgreedUponMeetings;
    }

    /**
     * Getter for this User's map of (week of the year) --> (Transactions requested)
     *
     * @return This User's map of (week of the year) --> (Transactions requested)
     */
    public Map<Integer, List<TradeRequest>> getWeeklyRequestLimit() {
        return this.weeklyRequestLimit;
    }

    //TODO: consider overriding the equals method inherited from Object

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(int newPoints) {
        this.points = newPoints;
    }

    /**
     * Helper function to only be used by UndoAction.java. Returns the first Item that matches the name and description
     * in the User's wishlist
     *
     * @param name        name of the Item
     * @param description description of the Item
     * @return Item object if name and description match; returns null if it doesn't
     */
    public Item findInWishlist(String name, String description) {
        for (Item item : wishlist) {
            if (item.getName().equals(name) && item.getDescription().equals(description)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Helper function only to be used by VacationUserMainMenu. Returns a list of Items stored in VacationStorage
     *
     * @return List of Items that were stored in VacationStorage
     */
    public List<Item> getVacationStorage() {
        return vacationStorage;
    }


    public List<String> getNotifyUndo(){return this.notifyUndo;}

    public ArrayList<String> getAdminActionHistory() {return adminActionHistory;}

    public ArrayList<String> getAdminMessages() {return AdminMessages;}

    public void updateCapital(double x){
        this.capital += x;
    }

    public void addCreditCard(CreditCard card){
        this.creditCards.add(card);
    }
    public List<CreditCard> getCreditCards(){
        return this.creditCards;
    }

    public void setDefaultCreditCard(CreditCard card){
        this.defaultCreditCard = card;
    }

    public double getCapital(){
        return this.capital;
    }

    public CreditCard getDefaultCreditCard(){
        return this.defaultCreditCard;
    }

    public List<typeThreeRequest> getApprovedThreeWay(){
        return this.approvedThreeWay;
    }


}
