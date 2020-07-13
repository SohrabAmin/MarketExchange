
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
     * Updates the tradeStatus of a given Transaction. 0: In progress, 1: Completed, 2: Cancelled
     *
     * @param itemManager Class that manages Items
     * @param userManager Class that manages Users
     * @param transaction The given Transaction
     * @param tradeStatus The current status of a given Transaction
     */
    public void updateTransactionStatus(ItemManager itemManager, UserManager userManager, Transaction transaction, int tradeStatus) {
        this.inProgressTransaction.remove(transaction);

        if (tradeStatus == 0) {
            this.inProgressTransaction.add(transaction);
        } else if (tradeStatus == 1) {
            this.completedTransaction.add(transaction);
            this.handleCompletedTrade(itemManager, userManager, transaction);

        } else {
            this.cancelledTransaction.add(transaction);
            handleCancelledTrade(userManager, transaction);
        }
        transaction.setTradeStatus(tradeStatus);
    }

    /**
     * Initiates and stores the InitialMeeting of a given Transaction. Required for all types of Transactions.
     *
     * @param transaction The given Transaction
     * @param meeting     The given Meeting
     */
    public void setInitialMeeting(Transaction transaction, Meeting meeting) {
        transaction.setInitialMeeting(meeting);
    }

    /**
     * Initiates and stores the finalMeeting within Transaction. Only accessed if Transaction temp = True, otherwise is kept Null
     *
     * @param transaction The given transaction. InitialMeeting is stored in this instance
     * @param meeting     The initial meeting, which is stored in the given Transaction
     */
    public void setFinalMeeting(Transaction transaction, Meeting meeting) {
        transaction.setReturnMeeting(meeting);
    }

    /**
     * Adds a given Transaction to one of three list attributes held in TransactionManager: pending, cancelled or completed.
     * Pending implies the Transaction is in progress, cancelled implies one of the following: User(s) did not show up to the Meeting or User(s) altered
     * Meeting to many times, and completed means the Transaction was successful, and the Item(s) have been officially swapped.
     *
     * @param transaction The specified Transaction that has been created.
     */
    public void receiveNewTransaction(Transaction transaction) {
        if (transaction.getTradeStatus() == 0) {
            this.inProgressTransaction.add(transaction);

        }
    }

    /**
     * Executes the back-end results of a Transaction being completed.
     *
     * @param itemManager The instance of class ItemManager
     * @param userManager The instance of class UserManager
     * @param transaction The given instance of Transaction
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


    /**
     * Executes the back-end results of a Transaction being cancelled. A Transaction being cancelled can result in being frozen (unable to participate in trading).
     *
     * @param userManager The instance of UserManager
     * @param transaction The given instance of Transaction
     */
    public void handleCancelledTrade(UserManager userManager, Transaction transaction) {
        if (transaction instanceof OneWay) {
            User temp1 = userManager.getUser(((OneWay) transaction).getBorrower());
            User temp2 = userManager.getUser(((OneWay) transaction).getLender());
            userManager.addToCancelledTransactions(temp1, transaction);
            userManager.addToCancelledTransactions(temp2, transaction);
            if (temp1.getCancelledTransactions().size() == userManager.getIncompleteTransactionLimit()) {
                userManager.pseudoFreeze(temp1);
            }

            if (temp2.getCancelledTransactions().size() == userManager.getIncompleteTransactionLimit()) {
                userManager.pseudoFreeze(temp2);
            }
        } else {
            User temp1 = userManager.getUser(((TwoWay) transaction).getFirstTrader());
            User temp2 = userManager.getUser(((TwoWay) transaction).getSecondTrader());
            userManager.addToCancelledTransactions(temp1, transaction);
            userManager.addToCancelledTransactions(temp2, transaction);
            if (temp1.getCancelledTransactions().size() == userManager.getIncompleteTransactionLimit()) {
                userManager.pseudoFreeze(temp1);
            }

            if (temp2.getCancelledTransactions().size() == userManager.getIncompleteTransactionLimit()) {
                userManager.pseudoFreeze(temp2);
            }
        }


    }
}
