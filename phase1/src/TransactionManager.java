
import java.io.Serializable;
import java.util.*;

/**
 * Manages all Transactions. Changing their values by accessing their setters/getters. Stores all instances of Transactions. Should only be instantiated once.
 */
public class TransactionManager implements Serializable {

    private List<Transaction> inProgressTransaction; //0
    private List<Transaction> finalizedMeeting; //1
    private List<Transaction> pendingSecondExchange; //2
    private List<Transaction> completedTransaction; //3
    private List<Transaction> cancelledTransaction; //4


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
     * Updates the tradeStatus of a given Transaction.
     * 0: In progress.
     * 1: Finalized Meeting (the initialMeeting has been set!).
     * 2: Pending Second Exchange (only for temporary Transaction).
     * 3: Completed
     * 4: Cancelled.
     * Notice by updating the tradeStatus the instance of Transaction is being moved to a new attribute list within TransactionManager.
     * finalizedMeeting implies the initialMeeting has been set.
     * pendingSecondExchange means the first exchange was successful, but the User(s) must return their Item a month after the initialMeeting.
     * Cancelled implies one of the following: User(s) did not show up to the Meeting or User(s) altered
     * meeting too many times, and completed means the Transaction was successful, and the Item(s) have been officially swapped.
     * Completed means either the finalizedMeeting had occurred (for permanent Transaction), or the secondExchange has occurred.
     *
     * @param itemManager Class that manages Items.
     * @param userManager Class that manages Users.
     * @param transaction The given Transaction.
     * @param tradeStatus The current status of a given Transaction.
     */
    public void updateTransactionStatus(ItemManager itemManager, UserManager userManager, Transaction transaction, int tradeStatus) {
        User user1;
        User user2;
        if (transaction.getTradeStatus() == 0){
            this.inProgressTransaction.remove(transaction);
        } else if(transaction.getTradeStatus() == 1){
            this.finalizedMeeting.remove(transaction);
        }else if(transaction.getTradeStatus() == 2){
            this.pendingSecondExchange.remove(transaction);
        }else if(transaction.getTradeStatus() == 3){
            this.completedTransaction.remove(transaction);
        }else{
            this.cancelledTransaction.remove(transaction);
        }

        if (tradeStatus == 0){
            this.inProgressTransaction.add(transaction);
        } else if(tradeStatus == 1){
            this.finalizedMeeting.add(transaction);
            if(transaction instanceof OneWay){
                user1 = userManager.getUser(((OneWay) transaction).getBorrower());
                user2 = userManager.getUser(((OneWay) transaction).getLender());
            }else{
                user1 = userManager.getUser(((TwoWay) transaction).getFirstTrader());
                user2 = userManager.getUser(((TwoWay) transaction).getSecondTrader());
            }
            userManager.removeFromPendingTrades(user1, transaction);
            userManager.removeFromPendingTrades(user2, transaction);
            userManager.addToAgreedUponMeetings(user1, transaction);
            userManager.addToAgreedUponMeetings(user2, transaction);
        }else if(tradeStatus == 2){
            this.pendingSecondExchange.add(transaction);
            handleFirstExchange(userManager, transaction, itemManager);
        }else if(tradeStatus == 3){
            this.completedTransaction.add(transaction);
            if(transaction.getTemp()){
                handleSecondExchange(userManager, itemManager, transaction);
            }else{
                handleCompletedPerm(userManager, itemManager, transaction);
            }
        }else{
            this.cancelledTransaction.add(transaction);
            handleCancelledTrade(userManager, transaction);
        }

        transaction.setTradeStatus(tradeStatus);
        transaction.getInitialMeeting().setConfirmedTrue();

    }

    /**
     * Initiates and stores the initialMeeting of a given Transaction. Required for all types of Transactions.
     *
     * @param transaction The given Transaction.
     * @param meeting     The given Meeting.
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
     * Adds a given new Transaction to the attribute list pendingTransaction held in TransactionManager.
     * Pending implies the Transaction is in progress.
     * @param transaction The specified Transaction that has been created.
     * @param userManager The instance of UserManager.
     */
    public void addToPendingTransactions(Transaction transaction, UserManager userManager) {
        User user1;
        User user2;
        if (transaction instanceof OneWay){
            user1 = userManager.getUser(((OneWay) transaction).getBorrower());
            user2 = userManager.getUser(((OneWay) transaction).getLender());

        }else{
            user1 = userManager.getUser(((TwoWay) transaction).getFirstTrader());
            user2 = userManager.getUser(((TwoWay) transaction).getSecondTrader());
        }
        userManager.addToPendingTrades(user1, transaction);
        userManager.addToPendingTrades(user2, transaction);


        inProgressTransaction.add(transaction);


    }



    /**
     * Executes the back-end results of a Transaction being cancelled. A Transaction being cancelled can result in being frozen (unable to participate in trading).
     *
     * @param userManager The instance of UserManager.
     * @param transaction The given instance of Transaction.
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
        if(transaction.getTradeStatus() == 0){
            userManager.removeFromPendingTrades(temp1, transaction);
            userManager.removeFromPendingTrades(temp2, transaction);
        }else if(transaction.getTradeStatus() == 1){
            userManager.removeFromAgreedUponMeetings(temp1, transaction);
            userManager.removeFromAgreedUponMeetings(temp2, transaction);
        }
    }

    /**
     * Handles the back-end completion of a temporary Transaction. Notice the second exchange is returning objects back to original owner. Only called in updateTransactionStatus.
     * @param userManager The instance of UserManager.
     * @param transaction The given temporary Transaction.
     * @param itemManager The instance of ItemManager.
     */
    public void handleSecondExchange(UserManager userManager, ItemManager itemManager, Transaction transaction){
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
            userManager.removeFromInventory(temp2,item2);
            userManager.removeFromInventory(temp1, item1);
            userManager.addToInventory(temp1, item2);
            userManager.addToInventory(temp2, item1);
            itemManager.setCurrentHolder(item1, temp2);
            itemManager.setCurrentHolder(item2, temp1);

        }userManager.updateTradeHistory(temp1, transaction);
         userManager.updateTradeHistory(temp2, transaction);
         userManager.removeFromSecondAgreedUponMeeting(temp1, transaction);
         userManager.removeFromSecondAgreedUponMeeting(temp2, transaction);
    }
    /**
     * Handles the back-end completion of a temporary Transaction. Notice the first exchange is giving Item(s) to the User(s). Only called in updateTransactionStatus.
     * @param userManager The instance of UserManager.
     * @param transaction The given temporary Transaction.
     * @param itemManager The instance of ItemManager.
     */
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
            temp1.increaseEligibility();
            temp2.decreaseEligibility();
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
        userManager.removeFromAgreedUponMeetings(temp1, transaction);
        userManager.removeFromAgreedUponMeetings(temp2, transaction);
        userManager.addToSecondAgreedUponMeeting(temp1, transaction);
        userManager.addToSecondAgreedUponMeeting(temp2, transaction);
    }

    /**
     * Handles the back-end results of a permanent Transaction occurring. This is only called by updateTransactionStatus.
     * @param userManager The instance of UserManager.
     * @param itemManager The instance of ItemManager.
     * @param transaction The given temporary Transaction.
     */
    public void handleCompletedPerm(UserManager userManager, ItemManager itemManager, Transaction transaction) {
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
            itemManager.setOwner(item1, temp1);
            temp1.decreaseEligibility();
            temp2.increaseEligibility();

        }else{
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
            itemManager.setOwner(item1, temp2);
            itemManager.setOwner(item2, temp1);
        }
        userManager.updateTradeHistory(temp1, transaction);
        userManager.updateTradeHistory(temp2, transaction);
        userManager.removeFromAgreedUponMeetings(temp1, transaction);
        userManager.removeFromAgreedUponMeetings(temp2, transaction);

    }

}
