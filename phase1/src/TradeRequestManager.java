import java.util.List;
import java.util.ArrayList;

public class TradeRequestManager {

    private List<TradeRequest> pending;
    private List<TradeRequest> denied;
    private List<TradeRequest> confirmed;

    public TradeRequestManager(){
        this.pending = new ArrayList<TradeRequest>();
        this.denied = new ArrayList<TradeRequest>();
        this.confirmed = new ArrayList<TradeRequest>();
    }

    public void receiveTradeRequest(UserManager userManager, TradeRequest request){
        if (request.getStatus() == 0) {
            this.pending.add(request);
            User temp = request.getReceiver();
            userManager.addToPendingRequests(temp , request);
        } else if (request.getStatus() == 1) {
            this.confirmed.add(request);
        } else {
            this.denied.add(request);
        }
    }


    public void updateRequestStatus(TransactionManager transactionManager, UserManager userManager, TradeRequest request, int status){
        if (request.getStatus() == 0) {
            this.pending.remove(request);
        } else if (request.getStatus() == 1) {
            this.confirmed.remove(request);
        } else {
            this.denied.remove(request);
        }

        if (status == 0) {
            this.pending.add(request);
        } else if (status == 1) {
            this.confirmed.add(request);
            this.handleConfirmedRequest(transactionManager, userManager, request);
        } else {
            this.denied.add(request);

        }

        request.setStatus(status);

    }


    public void handleConfirmedRequest(TransactionManager transactionManager, UserManager userManager, TradeRequest request){
        userManager.removeFromPendingRequests(request.getReceiver(), request);
        if(request.getRequestType() == 1){
            OneWay temp = new OneWay(request.getRequester(), request.getReceiverItem(), request.getTemp());
            transactionManager.receiveTransaction(temp);
        }else{
            TwoWay temp = new TwoWay(request.getRequesterItem(), request.getReceiverItem(), request.getTemp());
            transactionManager.receiveTransaction(temp);
        }
    }


}
