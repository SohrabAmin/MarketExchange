
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

    public void updateTransactionStatus(ItemManager z, UserManager v,Transaction x, int y){
        if(x.getTradeStatus() == 0){
            this.inProgressTransaction.remove(x);
        }else if(x.getTradeStatus() == 1){
            this.completedTransaction.remove(x);
        }else{
            this.cancelledTransaction.remove(x);
        }

        if(y == 0){
            this.inProgressTransaction.add(x);
        }else if(y == 1){
            this.completedTransaction.add(x);
            this.handleCompletedTrade(z, v, x);
        }else{
            this.cancelledTransaction.add(x);

        }

        x.setTradeStatus(y);

    }

    public void setInitialMeeting(Transaction a, Meeting x){
        a.setInitialMeeting(x);
    }

    public void setFinalMeeting(Transaction a, Meeting x){a.setReturnMeeting(x);}


    public void handleCompletedTrade(ItemManager z, UserManager y, Transaction x){

        if(x instanceof OneWay){

            ((OneWay) x).getBorrower().decreaseEligibility();
            ((OneWay) x).getLender().increaseEligibility();
            y.removeFromInventory(((OneWay) x).getLender(), ((OneWay) x).getLenderItem());
            y.addToInventory(((OneWay) x).getBorrower(), ((OneWay) x).getLenderItem());

            y.updateTradeHistory(((OneWay) x).getLender(), x);
            y.updateTradeHistory(((OneWay) x).getBorrower(),x);

            if(x.getTemp()){
                z.setCurrentHolder(((OneWay) x).getLenderItem(), ((OneWay) x).getBorrower());
            }else{
                z.setCurrentHolder(((OneWay) x).getLenderItem(), ((OneWay) x).getBorrower());
                z.setOwner(((OneWay) x).getLenderItem(), ((OneWay) x).getBorrower());
            }

        }else{
            y.removeFromInventory(((TwoWay)x).getFirstTrader(),((TwoWay)x).getFirstItem());
            y.removeFromInventory(((TwoWay)x).getSecondTrader(), ((TwoWay)x).getSecondItem());
            y.addToInventory(((TwoWay)x).getFirstTrader(), ((TwoWay)x).getSecondItem());
            y.addToInventory(((TwoWay)x).getSecondTrader(), ((TwoWay)x).getFirstItem());

            y.updateTradeHistory(((TwoWay) x).getFirstTrader(), x);
            y.updateTradeHistory(((TwoWay) x).getSecondTrader(),x);

            if(x.getTemp()){
                z.setCurrentHolder(((TwoWay)x).getFirstItem(), ((TwoWay)x).getSecondTrader());
                z.setCurrentHolder(((TwoWay)x).getSecondItem(), ((TwoWay)x).getFirstTrader());
            }else{
                z.setCurrentHolder(((TwoWay)x).getFirstItem(), ((TwoWay)x).getSecondTrader());
                z.setCurrentHolder(((TwoWay)x).getSecondItem(), ((TwoWay)x).getFirstTrader());
                z.setOwner(((TwoWay)x).getFirstItem(), ((TwoWay)x).getSecondTrader());
                z.setOwner(((TwoWay)x).getSecondItem(), ((TwoWay)x).getFirstTrader());
            }


        }
    }






}
