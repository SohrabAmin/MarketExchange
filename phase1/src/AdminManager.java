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

    /**
     * Constructs the instance of AdminManager with an initial Admin and a list of Admins
     *
     * @param initialAdmin first Admin in the system
     */
    public AdminManager(Admin initialAdmin) {
        allAdmins = new ArrayList<>();
        initialAdmin.setIsInitialAdmin(true);
        allAdmins.add(initialAdmin);
    }

    /**
     * Adds a new Admin to the list of all Admins
     *
     * @param newAdminUsername new Admin's account username
     * @param newAdminPassword new Admin's account password
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
     * only an Admin should change this threshold
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
     * cancelled; only an Admin should change this threshold
     *
     * @param meetingEditThreshold new threshold
     */
    public void setMeetingEditThreshold(int meetingEditThreshold) {
        this.meetingEditThreshold = meetingEditThreshold;
    }

    /**
     * Getter for the threshold that dictates the number of transactions any one User can conduct in one week
     *
     * @return current threshold
     */
    public int getWeeklyTransactionLimit() {
        return weeklyTransactionLimit;
    }

    /**
     * Setter for the threshold that dictates the number of transactions any one User can conduct in one week; only an
     * Admin should change this threshold
     *
     * @param weeklyTransactionLimit new threshold
     */
    public void setWeeklyTransactionLimit(int weeklyTransactionLimit) {
        this.weeklyTransactionLimit = weeklyTransactionLimit;
    }

    /**
     * Getter for the threshold that dictates how many transactions a User can leave incomplete before their account is
     * frozen
     *
     * @return current threshold
     */
    public int getIncompleteTransactionLimit() {
        return incompleteTransactionLimit;
    }

    /**
     * Setter for the threshold that dictates how many transactions a User can leave incomplete before their account is
     * frozen
     *
     * @param incompleteTransactionLimit new threshold
     */
    public void setIncompleteTransactionLimit(int incompleteTransactionLimit) {
        this.incompleteTransactionLimit = incompleteTransactionLimit;
    }
}
