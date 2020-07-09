
import java.util.*;

public class TransactionManager {

    private List<Transaction> inProgressTransaction;
    private List<Transaction> completedTransaction;
    private List<Transaction> cancelledTransaction;


    public TransactionManager() {
        this.inProgressTransaction = new ArrayList<>();
        this.completedTransaction = new ArrayList<>();
        this.cancelledTransaction = new ArrayList<>();

    }

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

    public void setInitialMeeting(Transaction transaction, Meeting meeting) {
        transaction.setInitialMeeting(meeting);
    }

    public void setFinalMeeting(Transaction transaction, Meeting meeting) {
        transaction.setReturnMeeting(meeting);
    }

    public void receiveTransaction(Transaction transaction){
        if (transaction.getTradeStatus() == 0) {
            this.inProgressTransaction.add(transaction);
        } else if (transaction.getTradeStatus() == 1) {
            this.completedTransaction.add(transaction);
        } else {
            this.cancelledTransaction.add(transaction);
        }
    }

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
