import java.util.ArrayList;
import java.util.List;

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

    public void addToInventory(User user, Item item){user.getInventory().add(item);}

    public void addToWishlist(User user, Item item) {
        user.getWishlist().add(item);
    }

    public void addToPendingRequests(User user, TradeRequest request){
        user.getPendingRequests().add(request);
    }

    public void removeFromPendingRequests(User user, TradeRequest request){
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

    public void updateTradeHistory(User x, Transaction y){
        x.addTradeHistory(y);
        this.updateTopTradingPartners(x);
    }

    public void updateTopTradingPartners(User x){
        List<Transaction> temp = x.getTradeHistory();

        for(Transaction trade: temp){

        }
    }
}
