package accounts.users;

import accounts.admins.AdminManager;
import items.Item;
import items.ItemManager;
import requests.TradeRequest;
import requests.typeThreeRequest;
import transactions.*;

import java.io.Serializable;
import java.util.*;

/**
 * Creates, keeps track of, and changes values of Users.
 */
public class UserManager implements Serializable {
    private List<User> userList;

    /**
     * Constructs the instance of accounts.users.UserManager with an empty list of Users
     */
    public UserManager() {
        userList = new ArrayList<>();
    }

    /**
     * Creates a accounts.users.User and adds them to the list of all Users
     *
     * @param newUserUsername new accounts.users.User's account username
     * @param newUserPassword new accounts.users.User's account password
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
     * Setter for the list of all Users in the system. Only system.ReadWrite should access this method.
     *
     * @param userList new list of all Users
     */
    public void setAllUsers(List<User> userList) {
        this.userList = userList;
    }

    /**
     * Freezes a accounts.users.User account
     *
     * @param user accounts.users.User account to be frozen
     */
    public void freeze(User user) {
        user.setIsFrozen(true);
    }

    /**
     * Unfreezes a accounts.users.User account
     *
     * @param user accounts.users.User account to be unfrozen
     */
    public void unfreeze(User user) {
        user.setIsFrozen(false);
    }

    /**
     * Pseudo-freezes a accounts.users.User account. A pseudo-frozen accounts.users.User is prevented from conducting transactions until an accounts.admins.Admin
     * decides to either freeze the accounts.users.User or let the accounts.users.User slide
     *
     * @param user accounts.users.User account to be pseudo-frozen
     */
    public void pseudoFreeze(User user) {
        user.setIsPseudoFrozen(true);
    }

    /**
     * Un-pseudo-freezes a accounts.users.User account. A pseudo-frozen accounts.users.User is prevented from conducting transactions until an accounts.admins.Admin
     * decides to either freeze the accounts.users.User or let the accounts.users.User slide
     *
     * @param user accounts.users.User account to be un-pseudo-frozen
     */
    public void unPseudoFreeze(User user) {
        user.setIsPseudoFrozen(false);
    }

    public void goOnVacation(User user) {
        user.setIsOnVacation(true);
    }

    public void returnFromVacation(User user) {
        user.setIsOnVacation(false);
    }

    /**
     * Adds an items.Item to a accounts.users.User's draft inventory
     *
     * @param user accounts.users.User whose draft inventory will be added to
     * @param item items.Item to be added to this accounts.users.User's draft inventory
     */
    public void addToDraftInventory(User user, Item item) {
        user.getDraftInventory().add(item);
    }

    /**
     * Adds an item to a accounts.users.User's inventory
     *
     * @param user accounts.users.User whose inventory will be added to
     * @param item items.Item to be added to this accounts.users.User's inventory
     */
    public void addToInventory(User user, Item item) {
        user.getInventory().add(item);
    }

    /**
     * Removes an items.Item from a accounts.users.User's inventory
     *
     * @param user accounts.users.User whose inventory will have an items.Item removed from it
     * @param item items.Item to be removed from this accounts.users.User's inventory
     */
    public void removeFromInventory(User user, Item item) {
        user.getInventory().remove(item);
    }

    /**
     * Adds an items.Item to a accounts.users.User's wishlist
     *
     * @param user accounts.users.User whose wishlist will be added to
     * @param item items.Item to be added to this accounts.users.User's wishlist
     */
    public void addToWishlist(User user, Item item) {
        user.getWishlist().add(item);
    }

    /**
     * Removes an items.Item from a accounts.users.User's wishlist
     *
     * @param user accounts.users.User whose wishlist will have an items.Item removed from it
     * @param item items.Item to be removed from this accounts.users.User's wishlist
     */
    public void removeFromWishlist(User user, Item item) {
        user.getWishlist().remove(item);
    }

    /**
     * Moves an items.Item from a accounts.users.User's draft inventory to their inventory (after an accounts.admins.Admin approves this items.Item)
     *
     * @param user accounts.users.User who will have a draft inventory items.Item approved
     * @param item items.Item approved by an accounts.admins.Admin
     */
    public void approveDraftInventoryItem(User user, Item item, ItemManager allItems) {
        user.getDraftInventory().remove(item);
        user.getInventory().add(item);
        allItems.addItem(item);
        this.changeItemStatus(user, item, "Approved");
    }

    /**
     * Removes an items.Item from a accounts.users.User's draft inventory (after an accounts.admins.Admin rejects this items.Item)
     *
     * @param user accounts.users.User who will have a draft inventory items.Item rejected
     * @param item items.Item rejected by an accounts.admins.Admin
     */
    public void rejectDraftInventoryItem(User user, Item item) {
        user.getDraftInventory().remove(item);
        this.changeItemStatus(user, item, "Rejected");
    }

    /**
     * Adds an items.Item to a accounts.users.User's itemHistory (list of items that have been submitted to the system)
     *
     * @param user accounts.users.User whose itemHistory will be added to
     * @param item items.Item to add to this accounts.users.User's itemHistory
     */
    public void addToItemHistory(User user, Item item) {
        user.getItemHistory().put(item, "Pending");
    }

    /**
     * Changes an items.Item's status (either "Pending", "Approved", or "Rejected") in a accounts.users.User's itemHistory (list of items
     * that have been submitted to the system). Only an accounts.admins.Admin should change an items.Item's status
     *
     * @param user   accounts.users.User whose items.Item will have its status changed
     * @param item   items.Item whose status is to be changed
     * @param status this items.Item's new status
     */
    public void changeItemStatus(User user, Item item, String status) {
        user.getItemHistory().replace(item, status);
    }

    /**
     * Checks the number of Transactions a accounts.users.User has requested in a week against the weekly transactions.Transaction limit
     *
     * @param adminManager The instance of accounts.admins.AdminManager
     * @param user         accounts.users.User whose number of requested Transactions in a week will be checked against the weekly
     *                     transactions.Transaction limit
     * @param date         A Calendar representing the date, in the week in question
     * @return True if and only if the number of Transactions this accounts.users.User requested in the given week is less than the
     * weekly transaction limit
     */
    public boolean checkWeeklyRequestLimit(AdminManager adminManager, User user, Calendar date) {
        Integer temp = date.get(Calendar.WEEK_OF_YEAR);
        List<TradeRequest> temp2 = user.getWeeklyRequestLimit().get(temp);

        return temp2.size() < adminManager.getWeeklyTransactionLimit();
    }

    /**
     * Adds a transactions.Transaction to a accounts.users.User's list of Transactions requested in a given week
     *
     * @param user    accounts.users.User whose list of Transactions requested in a given week will be added to
     * @param request requests.TradeRequest to add to this accounts.users.User's list of Transactions requested in a given week
     */
    public void addToWeeklyRequestLimit(User user, TradeRequest request) {
        Integer temp = request.getDate().get(Calendar.WEEK_OF_YEAR);
        user.getWeeklyRequestLimit().get(temp).add(request);
    }

    /**
     * Adds a requests.TradeRequest to a accounts.users.User's list of outbound TradeRequests
     *
     * @param user    accounts.users.User whose list of outbound TradeRequests will be added to
     * @param request requests.TradeRequest to add to this accounts.users.User's list of outbound TradeRequests
     */
    public void addToOutboundRequests(User user, TradeRequest request) {
        user.getOutboundRequests().add(request);
    }

    /**
     * Removes a requests.TradeRequest from a accounts.users.User's list of outbound TradeRequests
     *
     * @param user    accounts.users.User who will have a requests.TradeRequest removed from their list of outbound TradeRequests
     * @param request requests.TradeRequest to remove from this accounts.users.User's list of outbound TradeRequests
     */
    public void removeFromOutboundRequests(User user, TradeRequest request) {
        user.getOutboundRequests().remove(request);
    }

    /**
     * Adds a requests.TradeRequest to a accounts.users.User's pending trade requests
     *
     * @param user    accounts.users.User who will receive a new requests.TradeRequest
     * @param request requests.TradeRequest to be received by this accounts.users.User
     */
    public void addToPendingRequests(User user, TradeRequest request) {
        user.getPendingRequests().add(request);
    }

    /**
     * Removes a requests.TradeRequest from a accounts.users.User's pending trade requests
     *
     * @param user    accounts.users.User who will have a requests.TradeRequest removed from their pending trade requests
     * @param request requests.TradeRequest to be removed from this accounts.users.User's pending trade requests
     */
    public void removeFromPendingRequests(User user, TradeRequest request) {
        user.getPendingRequests().remove(request);
    }

    /**
     * Adds a transactions.Transaction to a accounts.users.User's list of pending trades
     *
     * @param user        accounts.users.User whose list of pending trades will be added to
     * @param transaction transactions.Transaction to add to this accounts.users.User's list of pending trades
     */
    public void addToPendingTrades(User user, Transaction transaction) {
        user.getPendingTrades().add(transaction);
    }

    /**
     * Removes a transactions.Transaction from a accounts.users.User's list of pending trades
     *
     * @param user        accounts.users.User who will have a transactions.Transaction removed from their list of pending trades
     * @param transaction transactions.Transaction to remove from this accounts.users.User's list of pending trades
     */
    public void removeFromPendingTrades(User user, Transaction transaction) {
        user.getPendingTrades().remove(transaction);
    }

    /**
     * Adds a transactions.Transaction to a accounts.users.User's list of agreedUponMeetings (Transactions containing a meeting to which both accounts.users.User
     * and their trading partner have agreed, but the meeting has not transpired in real life)
     *
     * @param user        accounts.users.User whose list of agreedUponMeetings list will be added to
     * @param transaction transactions.Transaction to add to this accounts.users.User's list of agreedUponMeetings
     */
    public void addToAgreedUponMeetings(User user, Transaction transaction) {
        user.getAgreedUponMeeting().add(transaction);
    }

    /**
     * Removes a transactions.Transaction from a accounts.users.User's list of agreedUponMeetings (Transactions containing a meeting to which both
     * accounts.users.User and their trading partner have agreed, but the meeting has not transpired in real life)
     *
     * @param user        accounts.users.User who will have a transactions.Transaction removed from their list of agreedUponMeetings
     * @param transaction transactions.Transaction to remove from this accounts.users.User's list of agreedUponMeetings
     */
    public void removeFromAgreedUponMeetings(User user, Transaction transaction) {
        user.getAgreedUponMeeting().remove(transaction);
    }

    /**
     * Adds a transactions.Transaction to a accounts.users.User's list of trade-back Transactions
     *
     * @param user        accounts.users.User whose list of trade-back Transactions will be added to
     * @param transaction transactions.Transaction to add to this accounts.users.User's list of trade-back Transactions
     */
    public void addToSecondAgreedUponMeetings(User user, Transaction transaction) {
        user.getSecondAgreedUponMeeting().add(transaction);
    }

    /**
     * Removes a transactions.Transaction from a accounts.users.User's list of of trade-back Transactions
     *
     * @param user        accounts.users.User who will have a transactions.Transaction removed from their list of trade-back Transactions
     * @param transaction transactions.Transaction to remove from this accounts.users.User's list of trade-back Transactions
     */
    public void removeFromSecondAgreedUponMeetings(User user, Transaction transaction) {
        user.getSecondAgreedUponMeeting().remove(transaction);
    }

    /**
     * Updates a accounts.users.User's trade history
     *
     * @param user        accounts.users.User whose trade history is to be updated
     * @param transaction transactions.Transaction to be added to this accounts.users.User's trade history
     */
    public void updateTradeHistory(User user, Transaction transaction) {
        user.getTradeHistory().add(transaction);
        this.updateTopTradingPartners(user);
        updateUserPoints(user, transaction);
    }

    /**
     * Gets called in updateTradeHistory so that as you make a transaction your
     * points can be updated accordingly depending on what type of transaction it was
     * and if you were the borrower or lender
     *
     * @param user        accounts.users.User whose points are to be updated
     * @param transaction Most recent transaction of user
     */
    private void updateUserPoints(User user, Transaction transaction) {
        if (transaction instanceof OneWayMonetized) {
            OneWayMonetized onewaymonetized = (OneWayMonetized) transaction;
            if (onewaymonetized.getFirstTrader() == user) {
                user.setPoints(user.getPoints() + 1);
            }
        }
        else if (transaction instanceof OneWay){
            OneWay oneway = (OneWay) transaction;
            if (oneway.getSecondTrader() == user) {
                user.setPoints(user.getPoints() + 1);
            }
        }
        else if (transaction instanceof TwoWay){
            user.setPoints(user.getPoints() + 2);
        }
        else if (transaction instanceof ThreeWay){
            user.setPoints(user.getPoints() + 3);
        }
        else {
            user.setPoints(user.getPoints());
        }
    }

    public void setUserVIP(User user){
            user.setIsVIP(true);
            user.setPoints(user.getPoints() - 20);
    }

    /**
     * Updates a accounts.users.User's list of top three trading partners.
     * <p>
     * Please note: the websites https://www.geeksforgeeks.org/count-occurrences-elements-list-java/ and
     * https://stackoverflow.com/questions/21054415/how-to-sort-a-hashmap-by-the-integer-value were referenced in order
     * to implement this method.
     *
     * @param user accounts.users.User whose list of top three trading partners is to be updated
     */
    public void updateTopTradingPartners(User user) {
//        List<transactions.Transaction> tradeHistoryCopy = user.getTradeHistory();
//        List<accounts.users.User> topTradingPartners = new ArrayList<>();
//
//        // the following hashmap maps each trading partner of user to the number of times user and partner have traded
//        Map<accounts.users.User, Integer> partnerToFrequencyMap = new HashMap<>();
//
//        // populate the hashmap
//        for (transactions.Transaction transaction : tradeHistoryCopy) {
//            if (transaction instanceof transactions.OneWay) {
//                accounts.users.User borrower = this.getUser(((transactions.OneWay) transaction).getUser1());
//                accounts.users.User lender = this.getUser(((transactions.OneWay) transaction).getUser2());
//                if (user.equals(borrower)) {
//                    Integer currentFrequency = partnerToFrequencyMap.get(lender);
//                    partnerToFrequencyMap.put(lender, (currentFrequency == null) ? 1 : currentFrequency + 1);
//                    // Reference for the above two lines:
//                    // https://www.geeksforgeeks.org/count-occurrences-elements-list-java/
//                } else if (user.equals(lender)) {
//                    Integer currentFrequency = partnerToFrequencyMap.get(borrower);
//                    partnerToFrequencyMap.put(borrower, (currentFrequency == null) ? 1 : currentFrequency + 1);
//                }
//            } else if (transaction instanceof transactions.TwoWay) {
//                accounts.users.User firstTrader = this.getUser(((transactions.TwoWay) transaction).getUser1());
//                accounts.users.User secondTrader = this.getUser(((transactions.TwoWay) transaction).getUser2());
//                if (user.equals(firstTrader)) {
//                    Integer currentFrequency = partnerToFrequencyMap.get(secondTrader);
//                    partnerToFrequencyMap.put(secondTrader, (currentFrequency == null) ? 1 : currentFrequency + 1);
//                } else if (user.equals(secondTrader)) {
//                    Integer currentFrequency = partnerToFrequencyMap.get(firstTrader);
//                    partnerToFrequencyMap.put(firstTrader, (currentFrequency == null) ? 1 : currentFrequency + 1);
//                }
//            }
//        }
//
//        // sort (in descending order) the hashmap's entries by value
//        Object[] setOfMapEntries = partnerToFrequencyMap.entrySet().toArray();
//        Arrays.sort(setOfMapEntries,
//                (mapEntry1, mapEntry2) ->
//                        ((Map.Entry<accounts.users.User, Integer>) mapEntry2).getValue().
//                                compareTo(((Map.Entry<accounts.users.User, Integer>) mapEntry1).getValue()));
//        // Reference for the above five lines:
//        // https://stackoverflow.com/questions/21054415/how-to-sort-a-hashmap-by-the-integer-value
//
//        int loopCount = 0;
//        for (Object mapEntry : setOfMapEntries) {
//            if (loopCount == 3) {
//                break;
//            }
//            topTradingPartners.add(((Map.Entry<accounts.users.User, Integer>) mapEntry).getKey());
//            loopCount++;
//        }
//        user.setTopTradingPartners(topTradingPartners);
    }

    /**
     * Getter for the most recent three items that a accounts.users.User traded away
     *
     * @param user the accounts.users.User in question
     * @return list of most recent three items that this accounts.users.User traded away
     */
    public List<Item> getRecentlyTradedItems(User user) {
        List<Item> recentlyTradedItems = new ArrayList<>();
        int itemsAddedToArrayList = 0;
        List<Transaction> tradeHistoryCopy = user.getTradeHistory();
//
//        for (int i = tradeHistoryCopy.size() - 1; i >= 0; i--) {
//            if (itemsAddedToArrayList < 3) {
//                if (tradeHistoryCopy.get(i) instanceof transactions.OneWay) {
//                    accounts.users.User lender = this.getUser(((transactions.OneWay) tradeHistoryCopy.get(i)).getUser2());
//                    if (user.equals(lender)) {
//                        recentlyTradedItems.add(((transactions.OneWay) tradeHistoryCopy.get(i)).getLenderItem());
//                        itemsAddedToArrayList++;
//                    }
//                } else if (tradeHistoryCopy.get(i) instanceof transactions.TwoWay) {
//                    accounts.users.User firstTrader = this.getUser(((transactions.TwoWay) tradeHistoryCopy.get(i)).getUser1());
//                    accounts.users.User secondTrader = this.getUser(((transactions.TwoWay) tradeHistoryCopy.get(i)).getUser2());
//                    if (user.equals(firstTrader)) {
//                        recentlyTradedItems.add(((transactions.TwoWay) tradeHistoryCopy.get(i)).getFirstItem());
//                        itemsAddedToArrayList++;
//                    } else if (user.equals(secondTrader)) {
//                        recentlyTradedItems.add(((transactions.TwoWay) tradeHistoryCopy.get(i)).getSecondItem());
//                        itemsAddedToArrayList++;
//                    }
//                }
//            }
//        }

        return recentlyTradedItems;
    }

    /**
     * Adds a cancelled transaction to a accounts.users.User's list of cancelled transactions
     *
     * @param user                 accounts.users.User whose list of cancelled transactions will be added to
     * @param cancelledTransaction cancelled transaction to be added to this accounts.users.User's list of cancelled transactions
     */
    public void addToCancelledTransactions(User user, Transaction cancelledTransaction) {
        user.getCancelledTransactions().add(cancelledTransaction);
    }

    /**
     * Used to associate a accounts.users.User that is an attribute of another object with the corresponding accounts.users.User in the system's list
     * of all Users
     *
     * @param userAsAnAttribute accounts.users.User that is an attribute of another object
     * @return accounts.users.User in the system's list of all Users
     */
    public User getUser(User userAsAnAttribute) {
        for (User originalUser : userList) {
            if (userAsAnAttribute.getName().equals(originalUser.getName())) {
                return originalUser;
            }
        }
        return null;
    }
    /**
     * Setter for  User become VIP
     * @param user who becomes VIP
     * the points of this user -20
     */
    public void addToFC (String category, User user){
        Integer old = user.getFrequentCategory().get(category);
        Integer score = old +1;
        user.getFrequentCategory().replace(category, score);
    }
    /**
     * Getter for all Frozen Users
     * @return all frozen users in system as a List of User
     */
    public List<User> getAllFrozenUsers() {
        List<User> allFrozenUsers = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getIsFrozen()) {
                //if getIsFrozen returns true for frozen accounts
                allFrozenUsers.add(userList.get(i));
            }
        }
        return allFrozenUsers;
    }
    /**
     * Getter for all PseudoFrozenUsers
     * @return all PsudoFrozenUsers as a List of User
     */
    public List<User> getAllPseudoFrozenUsers() {
        List<User> possibleFrozenPeople = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getIsPseudoFrozen()) {
                //if getIsFrozen returns true for frozen accounts
                possibleFrozenPeople.add(userList.get(i));
            }
        }
        return possibleFrozenPeople;
    }

    /**
     * Adds items.Item item to accounts.users.User user's VacationStorage when they go on vacation.
     *
     * @param user the accounts.users.User that is going on vacation and needs to store their items.
     * @param item the items.Item from their inventory that is going to be stored.
     */
    public void addToVacationStorage(User user, Item item) {
        user.getVacationStorage().add(item);
    }

    /**
     * Removes items.Item item from accounts.users.User user's VacationStorage when they return from vacation.
     *
     * @param user the accounts.users.User that has returned from vacation and needs to remove items from their VacationStorage
     * @param item the items.Item that is to be removed.
     */
    public void removeFromVacationStorage(User user, Item item) {
        user.getVacationStorage().remove(item);
    }

    /**
     * Setter for notifyUndo, add Admin's undo actions to the List of Undo actions for the user who's action is undone by admin.
     * @param user the user who's action is undone by admin
     * @param action this user's action that is undone by admin
     *(used to notify user what is undone by admin)
     */
    public void addToNotifyUndo(User user, String action){user.getNotifyUndo().add(action);}

    /**
     * Setter for AdminActionHistory, add admin's undo action to AdminAction History
     * @param user the user that has action being undone by admin
     * @param action the action that is being undone by admin
     */
    public void addToAdminActionHistory(User user, String action) {user.getAdminActionHistory().add(action);}

    /**
     * remove admin's undo action notification(as string)from the List of all undo actions when user logged in and saw the message.
     * @param user the user who logged in and saw this message
     * @param undone the messages that is seen by this user when logging in.
     */
    public void removeFromNotifyUndo(User user, String undone){user.getNotifyUndo().remove(undone);}

    /**
     * Setter for messages sent by this user to the Admin
     * @param user the user who sent message to Admin
     * @param message the message sent by this user
     */
    public void addToAdminMessages(User user, String message) {user.getAdminMessages().add(message);}

    /**
     * remove the message from the List of all messages sent by this user
     * @param user the user who has sent message to Admin
     * @param message message sent by this user
     */
    public void removeFromAdminMessages(User user, String message) {user.getAdminMessages().remove(message);}

    /**
     * Setter for ApprovedThreeWay, add approved three way trade request to the List of all this user's three way trade request.
     * @param user the user who has approved three way trade request
     * @param request request that is approved by this user
     */
    public void addToApprovedThreeWay(User user, typeThreeRequest request){
        user.getApprovedThreeWay().add(request);
    }

    /**
     * Remove the three way trade request from the List of all approved three way trade for a specific user.
     * @param user the user who
     * @param request
     */
    public void removeFromApprovedThreeway(User user, typeThreeRequest request){
        user.getApprovedThreeWay().remove(request);
    }
}