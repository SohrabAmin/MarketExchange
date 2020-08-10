package transactions;

import meetings.Meeting;

/**
 * The super class for all types of Transactions. Should never be instantiated.
 */

public class Transaction {


    private int tradeStatus = 0; // 0: In progress. 1: Finalized Meeting (the initialMeeting has been set) 2: Pending Second Exchange (only for temporary transactions.Transaction). 3: Completed 4: Cancelled.

    private boolean temp; //Set true is this transactions.Transaction in temporary (must revert in a month).

    private Meeting initialMeeting;

    private Meeting returnMeeting;

    private boolean virtual;


    /**
     * transactions.Transaction constructor, requires boolean: True if transaction is temporary, False if permanent.
     *
     * @param temp If this transaction is temporary
     */
    public Transaction(boolean temp, boolean virtual) {
        this.temp = temp;
        this.virtual = virtual;
        this.initialMeeting = null;
        this.returnMeeting = null;
    }

    /**
     * Gets if the Transaction is virtual or not
     * @return boolean if the Transaction is virtual or not
     */
    public boolean getVirtual(){
        return virtual;
    }

    /**
     * Getter for a given transactions.Transaction's tradeStatus. 0: In progress. 1: Finalized Meeting (the initialMeeting has been set) 2: Pending Second Exchange (only for temporary transactions.Transaction). 3: Completed 4: Cancelled.
     *
     * @return The trade status of a transactions.Transaction
     */
    public int getTradeStatus() {
        return this.tradeStatus;
    }

    /**
     * Changes a transactions.Transaction's trade status: 0: In progress. 1: Finalized Meeting (the initialMeeting has been set) 2: Pending Second Exchange (only for temporary transactions.Transaction). 3: Completed 4: Cancelled.
     *
     * @param tradeStatus The trade status of a transactions.Transaction
     */
    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    /**
     * Getter for whether a transactions.Transaction is temporary or not. True: transactions.Transaction is temporary. False: transactions.Transaction is permanent
     *
     * @return If a transactions.Transaction is temporary
     */

    public boolean getTemp() {
        return this.temp;
    }

    /**
     * Setter for whether a transactions.Transaction is temporary or not. True: transactions.Transaction is temporary. False: transactions.Transaction is permanent
     *
     * @param temp If a transactions.Transaction is temporary
     */
    public void setTemp(boolean temp) {

        this.temp = temp;
    }

    /**
     * Getter for the InitialMeeting of the transactions.Transaction. InitialMeeting will contain the time, date and place of exchanging items.
     *
     * @return The InitialMeeting of a given transactions.Transaction
     */
    public Meeting getInitialMeeting() {
        return this.initialMeeting;
    }

    /**
     * Setter of the initialMeeting of a transactions.Transaction. InitialMeeting will contain the time, date and palce of exchanging items.
     *
     * @param meeting The InitialMeeting of a given transactions.Transaction
     */
    public void setInitialMeeting(Meeting meeting) {
        this.initialMeeting = meeting;
    }

    /**
     * Getter of the returnMeeting of a transactions.Transaction. A transactions.Transaction should only have a returnMeeting if the transactions.Transaction is temporary. Otherwise, set to Null.
     *
     * @return The returnMeeting of a given transactions.Transaction
     */
    public Meeting getReturnMeeting() {
        return this.returnMeeting;
    }

    /**
     * Setter of the returnMeeting of a transactions.Transaction. A transactions.Transaction should only have a returnMeeting if the transactions.Transaction is temporary. Otherwise, set to Null.
     *
     * @param meeting The returnMeeting of a given transactions.Transaction
     */
    public void setReturnMeeting(Meeting meeting) {
        this.returnMeeting = meeting;
    }

}
