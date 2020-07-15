import java.util.*;


/**
 * Creates, keeps track of, and changes values of Users.
 */
public class UserManager extends AccountManager {
    private List<User> userList;

    /**
     * Constructs the instance of UserManager with an empty list of Users
     * <p>
     * TODO: Consider implementing a constructor with an ArrayList<User> parameter
     */
    public UserManager() {
        userList = new ArrayList<>();
    }

    /**
     * Getter for the list of all Users in the system
     *
     * @return list of all Users
     */
    public List<User> getAllUsers() {
        return userList;
    }

    /**
     * Setter for the list of all Users in the system. Only UserReadWrite should access this method.
     *
     * @param userList new list of all Users
     */
    void setAllUsers(List<User> userList) {
        this.userList = userList;
    }

    /**
     * Creates a User and adds them to the list of all Users
     *
     * @param newUserUsername new User's account username
     * @param newUserPassword new User's account password
     */
    public void createUser(String newUserUsername, String newUserPassword) {
        User newUser = new User(newUserUsername, newUserPassword);
        userList.add(newUser);
    }

    /**
     * Freezes a User account
     *
     * @param user User account to be frozen
     */
    public void freeze(User user) {
        user.setIsFrozen(true);
    }

    /**
     * Unfreezes a User account
     *
     * @param user User account to be unfrozen
     */
    public void unfreeze(User user) {
        user.setIsFrozen(false);
    }

    /**
     * Pseudo-freezes a User account. A pseudo-frozen User is prevented from conducting transactions until an Admin
     * decides to either freeze the User or let the User slide
     *
     * @param user User account to be pseudo-frozen
     */
    public void pseudoFreeze(User user) {
        user.setIsPseudoFrozen(true);
    }

    /**
     * Un-pseudo-freezes a User account. A pseudo-frozen User is prevented from conducting transactions until an Admin
     * decides to either freeze the User or let the User slide
     *
     * @param user User account to be un-pseudo-frozen
     */
    public void unPseudoFreeze(User user) {
        user.setIsPseudoFrozen(false);
    }

    /**
     * Adds an Item to a User's draft inventory
     *
     * @param user User whose draft inventory will be added to
     * @param item Item to be added to this User's draft inventory
     */
    public void addToDraftInventory(User user, Item item) {
        user.getDraftInventory().add(item);
    }

    /**
     * Removes an Item from a User's draft inventory
     *
     * @param user User whose draft inventory will have an Item removed from it
     * @param item Item to be removed from this User's draft inventory
     */
    public void removeFromDraftInventory(User user, Item item) {
        user.getDraftInventory().remove(item);
    }

    /**
     * Adds an item to a User's inventory
     *
     * @param user User whose inventory will be added to
     * @param item Item to be added to this User's inventory
     */
    public void addToInventory(User user, Item item) {
        user.getInventory().add(item);
    }

    /**
     * Removes an Item from a User's inventory
     *
     * @param user User whose inventory will have an Item removed from it
     * @param item Item to be removed from this User's inventory
     */
    public void removeFromInventory(User user, Item item) {
        user.getInventory().remove(item);
    }

    /**
     * Adds an Item to a User's wishlist
     *
     * @param user User whose wishlist will be added to
     * @param item Item to be added to this User's wishlist
     */
    public void addToWishlist(User user, Item item) {
        user.getWishlist().add(item);
    }

    /**
     * Removes an Item from a User's wishlist
     *
     * @param user User whose wishlist will have an Item removed from it
     * @param item Item to be removed from this User's wishlist
     */
    public void removeFromWishlist(User user, Item item) {
        user.getWishlist().remove(item);
    }

    /**
     * Adds a TradeRequest to a User's pending trade requests
     *
     * @param user    User who will receive a new TradeRequest
     * @param request TradeRequest to be received by this User
     */
    public void addToPendingRequests(User user, TradeRequest request) {
        user.getPendingRequests().add(request);
    }

    /**
     * Removes a TradeRequest from a User's pending trade requests
     *
     * @param user    User who will have a TradeRequest removed from their pending trade requests
     * @param request TradeRequest to be removed from this User's pending trade requests
     */
    public void removeFromPendingRequests(User user, TradeRequest request) {
        user.getPendingRequests().remove(request);
    }

    /**
     * Adds a cancelled transaction to a User's list of cancelled transactions
     *
     * @param user                 User whose list of cancelled transactions will be added to
     * @param cancelledTransaction cancelled transaction to be added to this User's list of cancelled transactions
     */
    public void addToCancelledTransactions(User user, Transaction cancelledTransaction) {
        user.getCancelledTransactions().add(cancelledTransaction);
    }

    /**
     * Moves an Item from a User's draft inventory to their inventory (after an Admin approves this Item)
     *
     * @param user User who will have a draft inventory Item approved
     * @param item Item approved by an Admin
     */
    public void approveDraftInventoryItem(User user, Item item, ItemManager allItems) {
        user.getDraftInventory().remove(item);
        user.getInventory().add(item);
        allItems.addItem(item);
        this.changeItemStatus(user, item, "Approved");
    }

    /**
     * Removes an Item from a User's draft inventory (after an Admin rejects this Item)
     *
     * @param user User who will have a draft inventory Item rejected
     * @param item Item rejected by an Admin
     */
    public void rejectDraftInventoryItem(User user, Item item) {
        user.getDraftInventory().remove(item);
        this.changeItemStatus(user, item, "Rejected");
    }

    /**
     * Updates a User's trade history
     *
     * @param user        User whose trade history is to be updated
     * @param transaction Transaction to be added to this User's trade history
     */
    public void updateTradeHistory(User user, Transaction transaction) {
        user.addTradeHistory(transaction);
        this.updateTopTradingPartners(user);
    }

    /**
     * Adds an Item to a User's itemHistory (list of items that have been submitted to the system)
     *
     * @param user User whose itemHistory will be added to
     * @param item Item to add to this User's itemHistory
     */
    public void addToItemHistory(User user, Item item) {
        user.getItemHistory().put(item, "Pending");
    }

    // TODO: update javadoc
    /**
     * setter for the pending trade.
     *
     * @param transaction to add meeting information to the trade that is not completed.
     */
    public void addToPendingTrades(User user, Transaction transaction) {
        user.getPendingTrades().add(transaction);
    }

    // TODO: write javadoc
    public void removeFromPendingTrades(User user, Transaction transaction) {
        user.getPendingTrades().remove(transaction);
    }

    // TODO: finish javadoc
    /**
     * Adds a Transaction to a User's list of Transactions in
     *
     * @param user
     * @param transaction Transaction containing the Meeting in question
     */
    public void addToAgreedUponMeetings (User user, Transaction transaction) {
        user.getAgreedUponMeeting().add(transaction);
    }

    public void removeFromAgreedUponMeetings(User user, Transaction transaction) {
        user.getAgreedUponMeeting().remove(transaction);
    }

    /**
     * Changes an Item's status (either "Pending", "Approved", or "Rejected") in a User's itemHistory (list of items
     * that have been submitted to the system). Only an Admin should change an Item's status
     *
     * @param user   User whose Item will have its status changed
     * @param item   Item whose status is to be changed
     * @param status this Item's new status
     */
    public void changeItemStatus(User user, Item item, String status) {
        user.getItemHistory().replace(item, status);
    }

    /**
     * Updates a User's list of top three trading partners.
     * <p>
     * Please note: the websites https://www.geeksforgeeks.org/count-occurrences-elements-list-java/ and
     * https://stackoverflow.com/questions/21054415/how-to-sort-a-hashmap-by-the-integer-value were referenced in order
     * to implement this method.
     *
     * @param user User whose list of top three trading partners is to be updated
     */
    public void updateTopTradingPartners(User user) {
        List<Transaction> tradeHistoryCopy = user.getTradeHistory();
        List<User> topTradingPartners = new ArrayList<>();

        // the following hashmap maps each trading partner of user to the number of times user and partner have traded
        Map<User, Integer> partnerToFrequencyMap = new HashMap<>();

        // populate the hashmap
        for (Transaction transaction : tradeHistoryCopy) {
            if (transaction instanceof OneWay) {
                User borrower = this.getUser(((OneWay) transaction).getBorrower());
                User lender = this.getUser(((OneWay) transaction).getLender());
                if (user.equals(borrower)) {
                    Integer currentFrequency = partnerToFrequencyMap.get(lender);
                    partnerToFrequencyMap.put(lender, (currentFrequency == null) ? 1 : currentFrequency + 1);
                    // Reference for the above two lines:
                    // https://www.geeksforgeeks.org/count-occurrences-elements-list-java/
                } else if (user.equals(lender)) {
                    Integer currentFrequency = partnerToFrequencyMap.get(borrower);
                    partnerToFrequencyMap.put(borrower, (currentFrequency == null) ? 1 : currentFrequency + 1);
                }
            } else if (transaction instanceof TwoWay) {
                User firstTrader = this.getUser(((TwoWay) transaction).getFirstTrader());
                User secondTrader = this.getUser(((TwoWay) transaction).getSecondTrader());
                if (user.equals(firstTrader)) {
                    Integer currentFrequency = partnerToFrequencyMap.get(secondTrader);
                    partnerToFrequencyMap.put(secondTrader, (currentFrequency == null) ? 1 : currentFrequency + 1);
                } else if (user.equals(secondTrader)) {
                    Integer currentFrequency = partnerToFrequencyMap.get(firstTrader);
                    partnerToFrequencyMap.put(firstTrader, (currentFrequency == null) ? 1 : currentFrequency + 1);
                }
            }
        }

        // sort (in descending order) the hashmap's entries by value
        Object[] setOfMapEntries = partnerToFrequencyMap.entrySet().toArray();
        Arrays.sort(setOfMapEntries,
                (mapEntry1, mapEntry2) ->
                        ((Map.Entry<User, Integer>) mapEntry2).getValue().
                                compareTo(((Map.Entry<User, Integer>) mapEntry1).getValue()));
        // Reference for the above five lines:
        // https://stackoverflow.com/questions/21054415/how-to-sort-a-hashmap-by-the-integer-value

        int loopCount = 0;
        for (Object mapEntry : setOfMapEntries) {
            if (loopCount == 3) {
                break;
            }
            topTradingPartners.add(((Map.Entry<User, Integer>) mapEntry).getKey());
            loopCount++;
        }
        user.setTopTradingPartners(topTradingPartners);
    }

    /**
     * Getter for the most recent three items that a User traded away
     *
     * @param user the User in question
     * @return list of most recent three items that this User traded away
     */
    public List<Item> getRecentlyTradedItems(User user) {
        List<Item> recentlyTradedItems = new ArrayList<>();
        int itemsAddedToArrayList = 0;
        List<Transaction> tradeHistoryCopy = user.getTradeHistory();

        for (int i = tradeHistoryCopy.size() - 1; i >= 0; i--) {
            if (itemsAddedToArrayList < 3) {
                if (tradeHistoryCopy.get(i) instanceof OneWay) {
                    User lender = this.getUser(((OneWay) tradeHistoryCopy.get(i)).getLender());
                    if (user.equals(lender)) {
                        recentlyTradedItems.add(((OneWay) tradeHistoryCopy.get(i)).getLenderItem());
                        itemsAddedToArrayList++;
                    }
                } else if (tradeHistoryCopy.get(i) instanceof TwoWay) {
                    User firstTrader = this.getUser(((TwoWay) tradeHistoryCopy.get(i)).getFirstTrader());
                    User secondTrader = this.getUser(((TwoWay) tradeHistoryCopy.get(i)).getSecondTrader());
                    if (user.equals(firstTrader)) {
                        recentlyTradedItems.add(((TwoWay) tradeHistoryCopy.get(i)).getFirstItem());
                        itemsAddedToArrayList++;
                    } else if (user.equals(secondTrader)) {
                        recentlyTradedItems.add(((TwoWay) tradeHistoryCopy.get(i)).getSecondItem());
                        itemsAddedToArrayList++;
                    }
                }
            }
        }

        return recentlyTradedItems;
    }

    /**
     * Used to associate a User that is an attribute of another object with the corresponding User in the system's list
     * of all Users
     *
     * @param userAsAnAttribute User that is an attribute of another object
     * @return User in the system's list of all Users
     */
    public User getUser(User userAsAnAttribute) {
        for (User originalUser : userList) {
            if (userAsAnAttribute.getName().equals(originalUser.getName())) {
                return originalUser;
            }
        }
        return null;
    }

    public void addToSecondAgreedUponMeeting(User user, Transaction trade){
        user.getSecondAgreedUponMeeting().add(trade);
    }

    public void removeFromSecondAgreedUponMeeting(User user, Transaction trade){
        user.getSecondAgreedUponMeeting().remove(trade);
    }

   public boolean checkWeeklyRequestLimit(User user, TradeRequest request){
        Integer temp = request.getDate().get(Calendar.WEEK_OF_YEAR);
        List<TradeRequest> temp2 = user.getWeeklyRequestLimit().get(temp);

        if(temp2.size() >= this.getWeeklyTransactionLimit()){
            return false;
        }else{
            temp2.add(request);
            return true;
        }
   }
}
