
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

    public void updateTransactionStatus(Transaction x, int y){
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
        }else{
            this.cancelledTransaction.add(x);

        }

        x.setTradeStatus(y);

    }

    public void setInitialMeeting(Transaction a, Meeting x){
        a.setInitialMeeting(x);
    }

    public void setFinalMeeting(Transaction a, Meeting x){a.setReturnMeeting(x);}








}
