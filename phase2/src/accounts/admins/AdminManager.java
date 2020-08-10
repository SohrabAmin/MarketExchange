package accounts.admins;

import accounts.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of and adds Admins; stores and edits system thresholds
 */
public class AdminManager implements Serializable {
    private List<Admin> allAdmins;

    // system thresholds
    private int lentMinusBorrowedThreshold = 1;
    private int meetingEditThreshold = 3;
    private int weeklyTransactionLimit = 7;
    private int incompleteTransactionLimit = 3;

    private List<User> frozenRequests = new ArrayList<>();

    /**
     * Constructs the instance of accounts.admins.AdminManager with an initial accounts.admins.Admin and a list of Admins
     *
     * @param initialAdmin first accounts.admins.Admin in the system
     */
    public AdminManager(Admin initialAdmin) {
        allAdmins = new ArrayList<>();
        initialAdmin.setIsSuperAdmin(true);
        allAdmins.add(initialAdmin);
    }

    /**
     * Adds a new accounts.admins.Admin to the list of all Admins
     *
     * @param newAdminUsername new accounts.admins.Admin's account username
     * @param newAdminPassword new accounts.admins.Admin's account password
     */
    public void addAdmin(String newAdminUsername, String newAdminPassword) {
        Admin newAdmin = new Admin(newAdminUsername, newAdminPassword);
        allAdmins.add(newAdmin);
    }

    /**
     * Getter for the list of all Admins
     *
     * @return list of all Admins
     */
    public List<Admin> getAllAdmins() {
        return allAdmins;
    }

    /**
     * Getter for the threshold that dictates how much more a user has to have lent than borrowed, before trading
     *
     * @return current threshold
     */
    public int getLentMinusBorrowedThreshold() {
        return lentMinusBorrowedThreshold;
    }

    /**
     * Setter for the threshold that dictates how much more a user has to have lent than borrowed, before trading;
     * only an accounts.admins.Admin should change this threshold
     *
     * @param lentMinusBorrowedThreshold new threshold
     */
    public void setLentMinusBorrowedThreshold(int lentMinusBorrowedThreshold) {
        this.lentMinusBorrowedThreshold = lentMinusBorrowedThreshold;
    }

    /**
     * Getter for the threshold that dictates how many times each user can edit a meeting before the meeting is
     * cancelled
     *
     * @return current threshold
     */
    public int getMeetingEditThreshold() {
        return meetingEditThreshold;
    }

    /**
     * Setter for the threshold that dictates how many times each user can edit a meeting before the meeting is
     * cancelled; only an accounts.admins.Admin should change this threshold
     *
     * @param meetingEditThreshold new threshold
     */
    public void setMeetingEditThreshold(int meetingEditThreshold) {
        this.meetingEditThreshold = meetingEditThreshold;
    }

    /**
     * Getter for the threshold that dictates the number of transactions any one accounts.users.User can conduct in one week
     *
     * @return current threshold
     */
    public int getWeeklyTransactionLimit() {
        return weeklyTransactionLimit;
    }

    /**
     * Setter for the threshold that dictates the number of transactions any one accounts.users.User can conduct in one week; only an
     * accounts.admins.Admin should change this threshold
     *
     * @param weeklyTransactionLimit new threshold
     */
    public void setWeeklyTransactionLimit(int weeklyTransactionLimit) {
        this.weeklyTransactionLimit = weeklyTransactionLimit;
    }

    /**
     * Getter for the threshold that dictates how many transactions a accounts.users.User can leave incomplete before their account is
     * frozen
     *
     * @return current threshold
     */
    public int getIncompleteTransactionLimit() {
        return incompleteTransactionLimit;
    }

    /**
     * Setter for the threshold that dictates how many transactions a accounts.users.User can leave incomplete before their account is
     * frozen
     *
     * @param incompleteTransactionLimit new threshold
     */
    public void setIncompleteTransactionLimit(int incompleteTransactionLimit) {
        this.incompleteTransactionLimit = incompleteTransactionLimit;
    }

    /**
     * Returns a list of all the Users in frozenRequest.
     *
     * @return List<accounts.users.User> returns list of Users in frozen Request
     */
    public List<User> getFrozenRequests() {
        return frozenRequests;
    }

    /**
     * Adds the accounts.users.User requesting to be unfrozen to the list of frozenRequests
     *
     * @param user the accounts.users.User requesting to be unfrozen
     */
    public void addFrozenRequest(User user) {
        frozenRequests.add(user);
    }

    /**
     * Removes the user from the frozenRequests list
     *
     * @param user the accounts.users.User that has been removed from the list of frozenRequests
     */
    public void removeFromFrozenRequest(User user) {
        frozenRequests.remove(user);
    }
}

