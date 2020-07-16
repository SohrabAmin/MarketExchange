/**
 * Superclass for UserManager and AdminManager; also stores and edits system thresholds
 */
public abstract class AccountManager {
    private int lentMinusBorrowedThreshold = 1;
    private int meetingEditThreshold = 1;

    // TODO: decide on a default value for the following thresholds
    private int weeklyTransactionLimit = 7;
    private int incompleteTransactionLimit = 3;

    /**
     * Verifies inputted login credentials
     *
     * @param username username of the Account attempting to log in
     * @param password password of the Account attempting to log in
     * @return whether the login attempt was successful
     */
    public boolean attemptLogin(Account username, String password) {
        return password.equals(username.getPassword());
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
