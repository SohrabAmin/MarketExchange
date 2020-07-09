/**
 * Superclass for OneWay and TwoWay. Stores essential values for a Transaction Object. Should never be instantiated.
 */


public class Transaction {

        private int tradeStatus = 0; // 0- in progress 1- completed 2- cancelled

        public boolean temp; //Set true is this transaction in temporary (must revert in a month) should be able to change easily for phase 2

        private Meeting initialMeeting;

        private Meeting returnMeeting;

    /**
     * Getter for trade status. 0: transaction in progress, 1: transaction completed, 2: transaction cancelled
     * @return this transaction's tradeStatus
     */


    public int getTradeStatus(){
            return this.tradeStatus;

        }

    /**
     * Setter for trade status.  0: transaction in progress, 1: transaction completed, 2: transaction cancelled
     * @param tradeStatus this transaction's trade status
     */


    public void setTradeStatus(int tradeStatus){
            this.tradeStatus = tradeStatus;
        }

    /**
     * getter for type of transaction; True: transaction is temporary. False: Transaction is permanent
     * @return If this transaction is temporary
     */

    public boolean getTemp(){
            return this.temp;
        }

    /**
     * Setter for temporary transaction. Should be set to True if temporary, False if permanent.
     * @param temp If this transaction is temporary.
     */

    public void setTemp(boolean temp) {

            this.temp = temp;
        }

        public Meeting getInitialMeeting(){
            return this.initialMeeting;
        }

        public void setInitialMeeting(Meeting meeting){
            this.initialMeeting = meeting;
        }

        public Meeting getReturnMeeting(){
            return this.returnMeeting;
        }

        public void setReturnMeeting(Meeting meeting){
            this.returnMeeting = meeting;
        }
}

