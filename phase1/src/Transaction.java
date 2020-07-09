/**
 * Superclass for OneWay and TwoWay. Stores essential attributes for a Transaction Object. Should never be instantiated.
 */
public class Transaction {


    private int tradeStatus = 0; // 0- in progress 1- completed 2- cancelled

    private boolean temp; //Set true is this transaction in temporary (must revert in a month) should be able to change easily for phase 2

    private Meeting initialMeeting;

    private Meeting returnMeeting;

    /**
     * Transaction constructor, requires boolean: True if transaction is temporary, False if permanent.
      * @param temp If this transaction is temporary
     */
    public Transaction(boolean temp){
            this.temp = temp;
            this.initialMeeting = null;
            this.returnMeeting = null;
        }

    /**
     * Getter for a given Transaction's tradeStatus. 0: In progress, 1: Cancelled, 2: Completed
     * @return The trade status of a Transaction
     */
    public int getTradeStatus(){
            return this.tradeStatus;
        }

    /**
     * Changes a Transaction's trade status: 0: In progress, 1: Cancelled, 2: Completed
     * @param tradeStatus The trade status of a Transaction
     */
    public void setTradeStatus(int tradeStatus){
            this.tradeStatus = tradeStatus;
        }

    /**
     * Getter for whether a Transaction is temporary or not. True: Transaction is temporary. False: Transaction is permanent
     * @return If a Transaction is temporary
     */

    public boolean getTemp(){
            return this.temp;
        }

    /**
     * Setter for whether a Transaction is temporary or not. True: Transaction is temporary. False: Transaction is permanent
     * @param temp If a Transaction is temporary
     */
    public void setTemp(boolean temp) {

            this.temp = temp;
        }

    /**
     * Getter for the InitialMeeting of the Transaction. InitialMeeting will contain the time, date and place of exchanging items.
     * @return The InitialMeeting of a given Transaction
     */
    public Meeting getInitialMeeting(){
            return this.initialMeeting;
        }

    /**
     * Setter of the initialMeeting of a Transaction. InitialMeeting will contain the time, date and palce of exchanging items.
     * @param meeting The InitialMeeting of a given Transaction
     */
    public void setInitialMeeting(Meeting meeting){
            this.initialMeeting = meeting;
        }

    /**
     * Getter of the returnMeeting of a Transaction. A Transaction should only have a returnMeeting if the Transaction is temporary. Otherwise, set to Null.
     * @return The returnMeeting of a given Transaction
     */
        public Meeting getReturnMeeting(){
            return this.returnMeeting;
        }

    /**
     * Setter of the returnMeeting of a Transaction. A Transaction should only have a returnMeeting if the Transaction is temporary. Otherwise, set to Null.
     * @param meeting The returnMeeting of a given Transaction
     */
    public void setReturnMeeting(Meeting meeting){
            this.returnMeeting = meeting;
        }
}

