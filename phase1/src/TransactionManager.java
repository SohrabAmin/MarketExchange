
import java.util.*;

/**
 * Manages all Transactions. Changing their values by accessing their setters/getters. Stores all instances of Transactions. Should only be instantiated once.
 */
public class TransactionManager {

    private List<Transaction> inProgressTransaction;
    private List<Transaction> pendingSecondExchange;
    private List<Transaction> completedTransaction;
    private List<Transaction> cancelledTransaction;
    private List<Transaction> finalizedMeeting;

    /**
     * Constructs TransactionManager. Creates three ArrayList which store all instances of Transaction: inProgressTransaction, completedTransaction, cancelledTransaction.
     * Does not require argument for instantiation.
     */
    public TransactionManager() {
        inProgressTransaction = new ArrayList<>();
        completedTransaction = new ArrayList<>();
        cancelledTransaction = new ArrayList<>();
        pendingSecondExchange = new ArrayList<>();
        finalizedMeeting = new ArrayList<>();



    }

    /**
     * Updates the tradeStatus of a given Transaction. 0: In progress, 1: Pending Second Exchange (Temp only), 2: Completed 3: Cancelled. Notice by updating the tradeStatus,
     * the instance of Transaction is being moved to a new attribute list within TransactionManager. Cancelled implies one of the following: User(s) did not show up to the Meeting or User(s) altered
     * meeting too many times, and completed means the Transaction was successful, and the Item(s) have been officially swapped.
     *
     * @param itemManager Class that manages Items
     * @param userManager Class that manages Users
     * @param transaction The given Transaction
     * @param tradeStatus The current status of a given Transaction
     */
    public void updateTransactionStatus(ItemManager itemManager, UserManager userManager, Transaction transaction, int tradeStatus) {
        if (transaction.getTradeStatus() == 0) {
            inProgressTransaction.remove(transaction);
            if (tradeStatus == 1) {
                pendingSecondExchange.add(transaction);
                handleFirstExchange(userManager, transaction, itemManager);

            } else if (tradeStatus == 2) {
                completedTransaction.add(transaction);
                handleCompletedTrade(itemManager,  userManager, transaction);

            }else {
                cancelledTransaction.add(transaction);
                handleCancelledTrade(userManager, transaction);
            }
        }else {
            pendingSecondExchange.remove(transaction);
            if (tradeStatus == 2){
                handleCompletedTrade(itemManager, userManager, transaction);
                completedTransaction.add(transaction);
            }else{
                //handleCancelledTrade(userManager, transaction); //probably will need a better way to handle this. Specs are not clear about this circumstance.
                cancelledTransaction.add(transaction);
            }
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
     * Adds a given Transaction to the attribute list pendingTransaction held in TransactionManager.
     * Pending implies the Transaction is in progress.
     * @param transaction The specified Transaction that has been created.
     */
    public void addToPendingTransactions(Transaction transaction, UserManager userManager) {
        User user1;
        User user2;
        if (transaction instanceof OneWay){
            user1 = userManager.getUser(((OneWay) transaction).getBorrower());
            user2 = userManager.getUser(((OneWay) transaction).getBorrower());

        }else{
            user1 = userManager.getUser(((TwoWay) transaction).getFirstTrader());
            user2 = userManager.getUser(((TwoWay) transaction).getSecondTrader());
        }
        userManager.addToPendingTrades(user1, transaction);
        userManager.addToPendingTrades(user2, transaction);


        inProgressTransaction.add(transaction);


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
        User temp1;
        User temp2;
        if (transaction instanceof OneWay) {
            temp1 = userManager.getUser(((OneWay) transaction).getBorrower());
            temp2 = userManager.getUser(((OneWay) transaction).getLender());

        } else {
            temp1 = userManager.getUser(((TwoWay) transaction).getFirstTrader());
            temp2 = userManager.getUser(((TwoWay) transaction).getSecondTrader());

        }
        userManager.addToCancelledTransactions(temp1, transaction);
        userManager.addToCancelledTransactions(temp2, transaction);
        if (temp1.getCancelledTransactions().size() == userManager.getIncompleteTransactionLimit()) {
            userManager.pseudoFreeze(temp1);
        }
        if (temp2.getCancelledTransactions().size() == userManager.getIncompleteTransactionLimit()) {
            userManager.pseudoFreeze(temp2);
        }


    }

    /**
     * Handles the completion of a temporary Transaction. Notice the second exchange is returning objects back to original owner.
     * @param userManager The instance of UserManager
     * @param transaction The given temporary Transaction
     * @param itemManager The instance of ItemManager.
     */
    public void handleSecondExchange(UserManager userManager, Transaction transaction, ItemManager itemManager){
        User temp1;
        User temp2;
        Item item1;
        if (transaction instanceof OneWay) {
            item1 = ((OneWay) transaction).getLenderItem();
            temp1 = userManager.getUser(((OneWay) transaction).getBorrower());
            temp2 = userManager.getUser(((OneWay) transaction).getLender());
            userManager.removeFromInventory(temp1, item1);
            userManager.addToInventory(temp2, item1);
            itemManager.setCurrentHolder(item1,temp2);

        } else {
            temp1 = userManager.getUser(((TwoWay) transaction).getFirstTrader());
            temp2 = userManager.getUser(((TwoWay) transaction).getSecondTrader());
            item1 = ((TwoWay) transaction).getSecondItem();
            Item item2 = ((TwoWay) transaction).getFirstItem();
            userManager.removeFromInventory(temp2,item1);
            userManager.removeFromInventory(temp1, item2);
            userManager.addToInventory(temp1, item1);
            userManager.addToInventory(temp2, item2);
            itemManager.setCurrentHolder(item1, temp1);
            itemManager.setCurrentHolder(item2, temp2);

        }userManager.updateTradeHistory(temp1, transaction);
         userManager.updateTradeHistory(temp2, transaction);

    }

    public void handleFirstExchange(UserManager userManager, Transaction transaction, ItemManager itemManager){
        User temp1;
        User temp2;
        Item item1;
        if (transaction instanceof OneWay) {
            item1 = ((OneWay) transaction).getLenderItem();
            temp1 = userManager.getUser(((OneWay) transaction).getBorrower());
            temp2 = userManager.getUser(((OneWay) transaction).getLender());
            userManager.removeFromInventory(temp2, item1);
            userManager.addToInventory(temp1, item1);
            itemManager.setCurrentHolder(item1,temp1);

        } else {
            temp1 = userManager.getUser(((TwoWay) transaction).getFirstTrader());
            temp2 = userManager.getUser(((TwoWay) transaction).getSecondTrader());
            item1 = ((TwoWay) transaction).getFirstItem();
            Item item2 = ((TwoWay) transaction).getSecondItem();
            userManager.removeFromInventory(temp1,item1);
            userManager.removeFromInventory(temp2, item2);
            userManager.addToInventory(temp2, item1);
            userManager.addToInventory(temp1, item2);
            itemManager.setCurrentHolder(item1, temp2);
            itemManager.setCurrentHolder(item2, temp1);

        }

    }
}
