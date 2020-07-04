/**
 * Superclass for UserManager and AdminManager; also stores and edits system thresholds
 */
public abstract class AccountManager {
    private int lentMinusBorrowedThreshold = 1;
    private int meetingEditThreshold = 3;

    /**
     * Verifies inputted login credentials
     *
     * @param username username of the Account attempting to log in
     * @param password password of the Account attempting to log in
     * @return whether the login attempt was successful
     */
    public boolean attemptLogin(Account username, String password) {
        return password.equals(Account.getPassword);
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

}
