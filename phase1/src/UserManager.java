import java.util.*;

/**
 * Creates, keeps track of, and changes values of Users.
 */
public class UserManager {
    private List<User> userList;

    /**
     * Constructs the instance of UserManager with an empty list of Users
     */
    public UserManager() {
        userList = new ArrayList<>();
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
     * Adds an Item to a User's itemHistory (list of items that have been submitted to the system)
     *
     * @param user User whose itemHistory will be added to
     * @param item Item to add to this User's itemHistory
     */
    public void addToItemHistory(User user, Item item) {
        user.getItemHistory().put(item, "Pending");
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
     * Checks the number of Transactions a User has requested in a week against the weekly Transaction limit
     *
     * @param adminManager The instance of AdminManager
     * @param user         User whose number of requested Transactions in a week will be checked against the weekly
     *                     Transaction limit
     * @param date         A Calendar representing the date, in the week in question
     * @return True if and only if the number of Transactions this User requested in the given week is less than the
     * weekly transaction limit
     */
    public boolean checkWeeklyRequestLimit(AdminManager adminManager, User user, Calendar date) {
        Integer temp = date.get(Calendar.WEEK_OF_YEAR);
        List<TradeRequest> temp2 = user.getWeeklyRequestLimit().get(temp);

        return temp2.size() < adminManager.getWeeklyTransactionLimit();
    }

    /**
     * Adds a Transaction to a User's list of Transactions requested in a given week
     *
     * @param user    User whose list of Transactions requested in a given week will be added to
     * @param request TradeRequest to add to this User's list of Transactions requested in a given week
     */
    public void addToWeeklyRequestLimit(User user, TradeRequest request) {
        Integer temp = request.getDate().get(Calendar.WEEK_OF_YEAR);
        user.getWeeklyRequestLimit().get(temp).add(request);
    }

    /**
     * Adds a TradeRequest to a User's list of outbound TradeRequests
     *
     * @param user    User whose list of outbound TradeRequests will be added to
     * @param request TradeRequest to add to this User's list of outbound TradeRequests
     */
    public void addToOutboundRequests(User user, TradeRequest request) {
        user.getOutboundRequests().add(request);
    }

    /**
     * Removes a TradeRequest from a User's list of outbound TradeRequests
     *
     * @param user    User who will have a TradeRequest removed from their list of outbound TradeRequests
     * @param request TradeRequest to remove from this User's list of outbound TradeRequests
     */
    public void removeFromOutboundRequests(User user, TradeRequest request) {
        user.getOutboundRequests().remove(request);
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
     * Adds a Transaction to a User's list of pending trades
     *
     * @param user        User whose list of pending trades will be added to
     * @param transaction Transaction to add to this User's list of pending trades
     */
    public void addToPendingTrades(User user, Transaction transaction) {
        user.getPendingTrades().add(transaction);
    }

    /**
     * Removes a Transaction from a User's list of pending trades
     *
     * @param user        User who will have a Transaction removed from their list of pending trades
     * @param transaction Transaction to remove from this User's list of pending trades
     */
    public void removeFromPendingTrades(User user, Transaction transaction) {
        user.getPendingTrades().remove(transaction);
    }

    /**
     * Adds a Transaction to a User's list of agreedUponMeetings (Transactions containing a meeting to which both User
     * and their trading partner have agreed, but the meeting has not transpired in real life)
     *
     * @param user        User whose list of agreedUponMeetings list will be added to
     * @param transaction Transaction to add to this User's list of agreedUponMeetings
     */
    public void addToAgreedUponMeetings(User user, Transaction transaction) {
        user.getAgreedUponMeeting().add(transaction);
    }

    /**
     * Removes a Transaction from a User's list of agreedUponMeetings (Transactions containing a meeting to which both
     * User and their trading partner have agreed, but the meeting has not transpired in real life)
     *
     * @param user        User who will have a Transaction removed from their list of agreedUponMeetings
     * @param transaction Transaction to remove from this User's list of agreedUponMeetings
     */
    public void removeFromAgreedUponMeetings(User user, Transaction transaction) {
        user.getAgreedUponMeeting().remove(transaction);
    }

    /**
     * Adds a Transaction to a User's list of trade-back Transactions
     *
     * @param user        User whose list of trade-back Transactions will be added to
     * @param transaction Transaction to add to this User's list of trade-back Transactions
     */
    public void addToSecondAgreedUponMeetings(User user, Transaction transaction) {
        user.getSecondAgreedUponMeeting().add(transaction);
    }

    /**
     * Removes a Transaction from a User's list of of trade-back Transactions
     *
     * @param user        User who will have a Transaction removed from their list of trade-back Transactions
     * @param transaction Transaction to remove from this User's list of trade-back Transactions
     */
    public void removeFromSecondAgreedUponMeetings(User user, Transaction transaction) {
        user.getSecondAgreedUponMeeting().remove(transaction);
    }

    /**
     * Updates a User's trade history
     *
     * @param user        User whose trade history is to be updated
     * @param transaction Transaction to be added to this User's trade history
     */
    public void updateTradeHistory(User user, Transaction transaction) {
        user.getTradeHistory().add(transaction);
        this.updateTopTradingPartners(user);
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
     * Adds a cancelled transaction to a User's list of cancelled transactions
     *
     * @param user                 User whose list of cancelled transactions will be added to
     * @param cancelledTransaction cancelled transaction to be added to this User's list of cancelled transactions
     */
    public void addToCancelledTransactions(User user, Transaction cancelledTransaction) {
        user.getCancelledTransactions().add(cancelledTransaction);
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

}
