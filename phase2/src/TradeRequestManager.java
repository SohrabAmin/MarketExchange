import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all TradeRequests. Changes values in TradeRequest by accessing getters/setters. Should only be instantiated once.
 */
public class TradeRequestManager implements Serializable {

    private List<TradeRequest> pending;
    private List<TradeRequest> denied;
    private List<TradeRequest> confirmed;
    /**
     * Constructs an instance of TradeRequestManager. Does not require an arguments for instantiation, however, this constructor
     * will initialize three list attributes: pending, denied and confirmed.
     */
    public TradeRequestManager() {
        this.pending = new ArrayList<TradeRequest>();
        this.denied = new ArrayList<TradeRequest>();
        this.confirmed = new ArrayList<TradeRequest>();
    }

    /**
     * Adds an instance of TradeRequest to one of three attributes in TradeRequestManager: pending, confirmed or cancelled, depending on
     * the User receiving the Trade Request's response. Also, adds to User's pendingRequests if request is not yet acted upon by receiving User.
     *
     * @param userManager The instance of UserManager, to access addToPendingRequest.
     * @param request     The given instance of TradeRequest.
     */
    public void receiveTradeRequest(UserManager userManager, TradeRequest request) {

        User user1;
        User user2;
        User user3;


        if(request instanceof typeOneRequest){
            user1 = userManager.getUser(((typeOneRequest) request).getFirstUser());
            user2 = userManager.getUser(((typeOneRequest) request).getSecondUser());
            user3 = null;
        }else if(request instanceof typeTwoRequest){
            user1 = userManager.getUser(((typeTwoRequest) request).getFirstUser());
            user2 = userManager.getUser(((typeTwoRequest) request).getSecondUser());
            user3 = null;
        }else{
            user1 = userManager.getUser(((typeThreeRequest) request).getFirstUser());
            user2 = userManager.getUser(((typeThreeRequest) request).getSecondUser());
            user3 = userManager.getUser(((typeThreeRequest) request).getThirdUser());
        }


    }


    /**
     * Updates attribute list(s) (pending/denied/confirmed) depending on the wishes of the User receiving the TradeRequest.
     *
     * @param transactionManager The instance of transactionManager, required for accessing handleConfirmedRequest.
     * @param userManager        The instance of userManager, required for accessing handleConfirmedRequest.
     * @param request            The given TradeRequest that the receiving User intends to act upon.
     * @param status             The status the system intends to give to this TradeRequest.
     */
    public void updateRequestStatus(TransactionManager transactionManager, UserManager userManager, TradeRequest request, int status) {
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

    /**
     * Accepts the TradeRequest within a User's pendingList, creates an instance of Transaction.
     *
     * @param transactionManager The instance of TransactionManager.
     * @param userManager        The instance of UserManager.
     * @param request            The status the system intends to give to this TradeRequest.
     */
    public void handleConfirmedRequest(TransactionManager transactionManager, UserManager userManager, TradeRequest request) { /* require Manager classes for accessing method receiveTransaction */
        userManager.removeFromPendingRequests(request.getReceiver(), request);
        if (request.getRequestType() == 1) {
            OneWay temp = new OneWay(request.getRequester(), request.getReceiverItem(), request.getTemp());
            // transactionManager.receiveTransaction(temp);
        } else {
            TwoWay temp = new TwoWay(request.getRequesterItem(), request.getReceiverItem(), request.getTemp());
            // transactionManager.receiveTransaction(temp);
        }
    }


}
