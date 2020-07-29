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


        if(request instanceof typeOneRequest){
            user1 = userManager.getUser(((typeOneRequest) request).getFirstUser());
            user2 = userManager.getUser(((typeOneRequest) request).getSecondUser());
            userManager.addToOutboundRequests(user1, request);
            userManager.addToWeeklyRequestLimit(user1, request);
            userManager.addToPendingRequests(user2, request);

        }else if(request instanceof typeTwoRequest){
            user1 = userManager.getUser(((typeTwoRequest) request).getFirstUser());
            user2 = userManager.getUser(((typeTwoRequest) request).getSecondUser());
            userManager.addToOutboundRequests(user1, request);
            userManager.addToWeeklyRequestLimit(user1, request);
            userManager.addToPendingRequests(user2, request);
        }else{
            user1 = userManager.getUser(((typeThreeRequest) request).getFirstUser());
            user2 = userManager.getUser(((typeThreeRequest) request).getSecondUser());
            User user3 = userManager.getUser(((typeThreeRequest) request).getThirdUser());
            userManager.addToOutboundRequests(user2, request);
            userManager.addToWeeklyRequestLimit(user2, request);
            userManager.addToPendingRequests(user1, request);
            userManager.addToPendingRequests(user3, request);
        }

        this.pending.add(request);

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
            this.handleRequestResponse(transactionManager, userManager, request);
        } else {
            this.denied.add(request);
            this.handleRequestResponse(transactionManager, userManager, request);
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
    public void handleRequestResponse(TransactionManager transactionManager, UserManager userManager, TradeRequest request) {
        User user1;
        User user2;


        if(request instanceof typeOneRequest){
            user1 = userManager.getUser(((typeOneRequest) request).getFirstUser());
            user2 = userManager.getUser(((typeOneRequest) request).getSecondUser());
            userManager.removeFromOutboundRequests(user1, request);
            userManager.removeFromPendingRequests(user2, request);

        }else if(request instanceof typeTwoRequest){
            user1 = userManager.getUser(((typeTwoRequest) request).getFirstUser());
            user2 = userManager.getUser(((typeTwoRequest) request).getSecondUser());
            userManager.removeFromOutboundRequests(user1, request);
            userManager.removeFromPendingRequests(user2, request);
        }else{
            user1 = userManager.getUser(((typeThreeRequest) request).getFirstUser());
            user2 = userManager.getUser(((typeThreeRequest) request).getSecondUser());
            User user3 = userManager.getUser(((typeThreeRequest) request).getThirdUser());
            userManager.removeFromOutboundRequests(user2, request);
            userManager.removeFromPendingRequests(user1, request);
            userManager.removeFromPendingRequests(user3, request);
        }

    }
}
