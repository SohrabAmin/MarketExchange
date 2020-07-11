
import java.util.*;

/**
 * Manages all Transactions. Changing their values by accessing their setters/getters. Stores all instances of Transactions. Should only be instantiated once.
 */
public class TransactionManager {

    private List<Transaction> inProgressTransaction;
    private List<Transaction> completedTransaction;
    private List<Transaction> cancelledTransaction;

    /**
     * Constructs TransactionManager. Creates three ArrayList which store all instances of Transaction: inProgressTransaction, completedTransaction, cancelledTransaction.
     * Does not require argument for instantiation.
     */
    public TransactionManager() {
        this.inProgressTransaction = new ArrayList<>();
        this.completedTransaction = new ArrayList<>();
        this.cancelledTransaction = new ArrayList<>();

    }

    /**
     * Updates the tradeStatus of a given Transaction. 0: In progress, 1: Cancelled, 2: Completed
     * @param itemManager Class that manages Items
     * @param userManager Class that manages Users
     * @param transaction The given Transaction
     * @param tradeStatus The current status of a given Transaction
     */
    public void updateTransactionStatus(ItemManager itemManager, UserManager userManager, Transaction transaction, int tradeStatus) {
        if (transaction.getTradeStatus() == 0) {
            this.inProgressTransaction.remove(transaction);
        } else if (transaction.getTradeStatus() == 1) {
            this.completedTransaction.remove(transaction);
        } else {
            this.cancelledTransaction.remove(transaction);
        }

        if (tradeStatus == 0) {
            this.inProgressTransaction.add(transaction);
        } else if (tradeStatus == 1) {
            this.completedTransaction.add(transaction);
            this.handleCompletedTrade(itemManager, userManager, transaction);
        } else {
            this.cancelledTransaction.add(transaction);

        }

        transaction.setTradeStatus(tradeStatus);

    }

    /**
     * Initiates and stores the InitialMeeting of a given Transaction. Required for all types of Transactions.
     * @param transaction The given Transaction
     * @param meeting The given Meeting
     */
    public void setInitialMeeting(Transaction transaction, Meeting meeting) { /* yo*/
        transaction.setInitialMeeting(meeting);
    }

    /**
     * Initiates and stores the finalMeeting within Transaction. Only accessed if Transaction temp = True, otherwise is kept Null
     * @param transaction The given transaction. InitialMeeting is stored in this instance
     * @param meeting The intial meeting, which is stored in the given Transaction
     */
    public void setFinalMeeting(Transaction transaction, Meeting meeting) {
        transaction.setReturnMeeting(meeting);
    }

    /**
     * Adds a given Transaction to one of three list attributes held in TransactionManager: pending, cancelled or completed.
     * Pending implies the Transaction is in progress, cancelled implies one of the following: User(s) did not show up to the Meeting or User(s) altered
     * Meeting to many times, and completed means the Transaction was successful, and the Item(s) have been officially swapped.
     * @param transaction The specified Transaction that has been created.
     */
    public void receiveTransaction(Transaction transaction){
        if (transaction.getTradeStatus() == 0) {
            this.inProgressTransaction.add(transaction);
        } else if (transaction.getTradeStatus() == 1) {
            this.completedTransaction.add(transaction);
        } else {
            this.cancelledTransaction.add(transaction);
        }
    }

    /**
     * Executes the back-end results of a Transaction being completed.
     * @param itemManager
     * @param userManager
     * @param transaction
     */
    public void handleCompletedTrade(ItemManager itemManager, UserManager userManager, Transaction transaction) {

        if (transaction instanceof OneWay) {

            ((OneWay) transaction).getBorrower().decreaseEligibility();
            ((OneWay) transaction).getLender().increaseEligibility();
            userManager.removeFromInventory(((OneWay) transaction).getLender(), ((OneWay) transaction).getLenderItem());
            userManager.addToInventory(((OneWay) transaction).getBorrower(), ((OneWay) transaction).getLenderItem());

            userManager.updateTradeHistory(((OneWay) transaction).getLender(), transaction);
            userManager.updateTradeHistory(((OneWay) transaction).getBorrower(), transaction);

            if (transaction.getTemp()) {
                itemManager.setCurrentHolder(((OneWay) transaction).getLenderItem(), ((OneWay) transaction).getBorrower());
            } else {
                itemManager.setCurrentHolder(((OneWay) transaction).getLenderItem(), ((OneWay) transaction).getBorrower());
                itemManager.setOwner(((OneWay) transaction).getLenderItem(), ((OneWay) transaction).getBorrower());
            }

        } else {
            userManager.removeFromInventory(((TwoWay) transaction).getFirstTrader(), ((TwoWay) transaction).getFirstItem());
            userManager.removeFromInventory(((TwoWay) transaction).getSecondTrader(), ((TwoWay) transaction).getSecondItem());
            userManager.addToInventory(((TwoWay) transaction).getFirstTrader(), ((TwoWay) transaction).getSecondItem());
            userManager.addToInventory(((TwoWay) transaction).getSecondTrader(), ((TwoWay) transaction).getFirstItem());

            userManager.updateTradeHistory(((TwoWay) transaction).getFirstTrader(), transaction);
            userManager.updateTradeHistory(((TwoWay) transaction).getSecondTrader(), transaction);

            if (transaction.getTemp()) {
                itemManager.setCurrentHolder(((TwoWay) transaction).getFirstItem(), ((TwoWay) transaction).getSecondTrader());
                itemManager.setCurrentHolder(((TwoWay) transaction).getSecondItem(), ((TwoWay) transaction).getFirstTrader());
            } else {
                itemManager.setCurrentHolder(((TwoWay) transaction).getFirstItem(), ((TwoWay) transaction).getSecondTrader());
                itemManager.setCurrentHolder(((TwoWay) transaction).getSecondItem(), ((TwoWay) transaction).getFirstTrader());
                itemManager.setOwner(((TwoWay) transaction).getFirstItem(), ((TwoWay) transaction).getSecondTrader());
                itemManager.setOwner(((TwoWay) transaction).getSecondItem(), ((TwoWay) transaction).getFirstTrader());
            }


        }


    }
    
}
