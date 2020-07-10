import java.util.*;

/**
 * Creates, keeps track of, and changes values of Users.
 */
public class UserManager extends AccountManager {
    public List<User> allUsers;

    /**
     * Constructs the instance of UserManager with an empty list of Users
     * <p>
     * TODO: Consider implementing a constructor with an ArrayList<User> parameter
     */
    public UserManager() {
        allUsers = new ArrayList<>();
    }

    /**
     * Getter for the list of all Users in the system
     *
     * @return list of all Users
     */
    public List<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Creates a User and adds them to the list of all Users
     *
     * @param newUserUsername new User's account username
     * @param newUserPassword new User's account password
     */
    public void createUser(String newUserUsername, String newUserPassword) {
        User newUser = new User(newUserUsername, newUserPassword);
        allUsers.add(newUser);

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
        user.updatePendingRequests(request);
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
     * Moves an Item from a User's draft inventory to their inventory (after an Admin approves this Item)
     *
     * @param user User who will have a draft inventory Item approved
     * @param item Item approved by an Admin
     */
    public void approveDraftInventoryItem(User user, Item item) {
        user.getDraftInventory().remove(item);
        user.getInventory().add(item);
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
     * Updates a User's list of top three trading partners.
     * <p>
     * Please note: the websites https://www.geeksforgeeks.org/count-occurrences-elements-list-java/ and
     * https://stackoverflow.com/questions/21054415/how-to-sort-a-hashmap-by-the-integer-value were referenced to
     * implement this method.
     *
     * @param user User whose list of top three trading partners is to be updated
     */
    public void updateTopTradingPartners(User user) {
        List<Transaction> tradeHistoryCopy = user.getTradeHistory();

        // the following hashmap maps user's trading partners to the number of times user and partner have traded
        Map<User, Integer> partnerToFrequencyMap = new HashMap<>();

        // populate the hashmap
        for (Transaction transaction : tradeHistoryCopy) {
            if (transaction instanceof OneWay) {
                if (user.equals(((OneWay) transaction).getBorrower())) {
                    Integer currentFrequency = partnerToFrequencyMap.get(((OneWay) transaction).getLender());
                    partnerToFrequencyMap.put(((OneWay) transaction).getLender(),
                            (currentFrequency == null) ? 1 : currentFrequency + 1);
                    // Reference for the above three lines:
                    // https://www.geeksforgeeks.org/count-occurrences-elements-list-java/
                } else if (user.equals(((OneWay) transaction).getLender())) {
                    Integer currentFrequency = partnerToFrequencyMap.get(((OneWay) transaction).getBorrower());
                    partnerToFrequencyMap.put(((OneWay) transaction).getBorrower(),
                            (currentFrequency == null) ? 1 : currentFrequency + 1);
                }
            } else if (transaction instanceof TwoWay) {
                if (user.equals(((TwoWay) transaction).getFirstTrader())) {
                    Integer currentFrequency = partnerToFrequencyMap.get(((TwoWay) transaction).getSecondTrader());
                    partnerToFrequencyMap.put(((TwoWay) transaction).getSecondTrader(),
                            (currentFrequency == null) ? 1 : currentFrequency + 1);
                } else if (user.equals(((TwoWay) transaction).getSecondTrader())) {
                    Integer currentFrequency = partnerToFrequencyMap.get(((TwoWay) transaction).getFirstTrader());
                    partnerToFrequencyMap.put(((TwoWay) transaction).getFirstTrader(),
                            (currentFrequency == null) ? 1 : currentFrequency + 1);
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

        user.setTopTradingPartners(((Map.Entry<User, Integer>) setOfMapEntries[0]).getKey(),
                ((Map.Entry<User, Integer>) setOfMapEntries[1]).getKey(),
                ((Map.Entry<User, Integer>) setOfMapEntries[2]).getKey());
    }

    public User getUser(User person) {
        for (User allUser : allUsers) {
            if (person.getName().equals(allUser.getName())) {
                return allUser;
            }
        }
        return null;
    }
}
