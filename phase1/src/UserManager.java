import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates, keeps track of, and changes the frozen status of Users
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
     * Freezes this User account
     *
     * @param user User account to be frozen
     */
    public void freeze(User user) {
        user.setIsFrozen(true);
    }

    /**
     * Unfreezes this User account
     *
     * @param user User account to be unfrozen
     */
    public void unfreeze(User user) {
        user.setIsFrozen(false);
    }

    public void addToDraftInventory(User user, Item item) {
        user.getDraftInventory().add(item);
    }

    public void removeFromInventory(User user, Item item) {
        user.getInventory().remove(item);
    }

    public void addToInventory(User user, Item item) {
        user.getInventory().add(item);
    }

    public void addToWishlist(User user, Item item) {
        user.getWishlist().add(item);
    }

    public void addToPendingRequests(User user, TradeRequest request) {
        user.updatePendingRequests(request);
    }

    public void removeFromPendingRequests(User user, TradeRequest request) {
        user.getPendingRequests().remove(request);
    }

    public void removeFromDraftInventory(User user, Item item) {
        user.getDraftInventory().remove(item);
    }

    public void removeFromWishlist(User user, Item item) {
        user.getWishlist().remove(item);
    }

    public void approveDraftInventoryItem(User user, Item item) {
        user.getDraftInventory().remove(item);
        user.getInventory().add(item);
    }

    public void updateTradeHistory(User x, Transaction y) {
        x.addTradeHistory(y);
        this.updateTopTradingPartners(x);
    }

    /**
     * Updates a User's top three trading partners. Please note: the websites
     * https://www.geeksforgeeks.org/count-occurrences-elements-list-java/ and
     * https://stackoverflow.com/questions/21054415/how-to-sort-a-hashmap-by-the-integer-value
     * were referenced to implement this method.
     *
     * @param user User whose top three trading partners are to be updated
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
                    partnerToFrequencyMap.put(((OneWay) transaction).getLender(), (currentFrequency == null) ? 1 : currentFrequency + 1);
                    // Reference for the above two lines:
                    // https://www.geeksforgeeks.org/count-occurrences-elements-list-java/
                } else if (user.equals(((OneWay) transaction).getLender())) {
                    Integer currentFrequency = partnerToFrequencyMap.get(((OneWay) transaction).getBorrower());
                    partnerToFrequencyMap.put(((OneWay) transaction).getBorrower(), (currentFrequency == null) ? 1 : currentFrequency + 1);
                }
            } else if (transaction instanceof TwoWay) {
                if (user.equals(((TwoWay) transaction).getFirstTrader())) {
                    Integer currentFrequency = partnerToFrequencyMap.get(((TwoWay) transaction).getSecondTrader());
                    partnerToFrequencyMap.put(((TwoWay) transaction).getSecondTrader(), (currentFrequency == null) ? 1 : currentFrequency + 1);
                } else if (user.equals(((TwoWay) transaction).getSecondTrader())) {
                    Integer currentFrequency = partnerToFrequencyMap.get(((TwoWay) transaction).getFirstTrader());
                    partnerToFrequencyMap.put(((TwoWay) transaction).getFirstTrader(), (currentFrequency == null) ? 1 : currentFrequency + 1);
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

        user.setTopTradingPartners(((Map.Entry<User, Integer>) setOfMapEntries[0]).getKey(), ((Map.Entry<User, Integer>) setOfMapEntries[1]).getKey(), ((Map.Entry<User, Integer>) setOfMapEntries[2]).getKey());
    }


    public User getUser(User person) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (person.getName().equals(allUsers.get(i).getName())) {
                return allUsers.get(i);
            }


        }
        return null;

    }
}
