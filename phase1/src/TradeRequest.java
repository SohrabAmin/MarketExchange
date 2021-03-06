import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Constructs a trade request, that a User can send to another User if the want to initiated a Transaction. Notice this difference from a Transaction, as
 * the system does not create an instance of Transaction unless the corresponding TradeRequest has been approved by the User receiving the TradeRequest.
 */
public class TradeRequest implements Serializable {
    private User requester;
    private User receiver;
    private int requestType;//1 way or 2 way
    private Item requesterItem;
    private Item receiverItem;
    private String message;
    private boolean temp;
    private int status;
    private Calendar date;

    /**
     * Constructs a TradeRequest instance. This class is instantiated by the User that attempts to initiate the Transaction, and should be stored within the receiving User's
     * pendingRequest. If the receiving User accepts the conditions of the Transaction, they may accept the TradeRequest, and an instance of Transaction is created.
     *
     * @param requestType An integer that determines whether the trade is temporary or not: 1- Permanent Transaction, 2- Temporary Transaction.
     * @param requester   The User who initiates the Transaction.
     * @param receiver    The User who receives the TradeRequest.
     * @param itemList    An ArrayList of Items containing the Items being exchanged. If this is a TwoWay, the requester User's item should be in the first index,
     *                    and the receiver User's item to give in the second index. If this is a OneWay, simply place the Item in the first index.
     * @param message     A string representation of any message the requester User may want to send to the receiver User.
     * @param temp        A boolean regarding if the User requesting a TradeRequest wants a temporary Transaction or not.
     */
    public TradeRequest(int requestType, User requester, User receiver, List<Item> itemList, String message, boolean temp, Calendar date) {
        this.requestType = requestType;
        this.requester = requester;
        this.receiver = receiver;
        this.message = message;
        this.status = 0;
        this.temp = temp;
        this.date = date;
        if (this.requestType == 1) {
            this.requesterItem = null;
            this.receiverItem = itemList.get(0);
        } else {
            this.requesterItem = itemList.get(0);
            this.receiverItem = itemList.get(1);
        }
    }

    /**
     * Gets the message the User initiating the TradeRequest sent to the User receiving the TradeRequest.
     *
     * @return String representation of the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets whether the User who initiated the TradeRequest wants a OneWay or TwoWay Transaction.
     *
     * @return Integer representing initiating User's requestType. 1: OneWay, 2: TwoWay.
     */
    public int getRequestType() {
        return this.requestType;
    }

    /**
     * Sets whether the User who initiated the TradeRequest wants a OneWay or TwoWay Transaction.
     *
     * @param requestType Integer representing initiating User's requestType. 1: OneWay, 2: TwoWay.
     */
    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    /**
     * Gets the current status of a TradeRequest.
     *
     * @return An integer, which represents the status of a TradeRequest. 0: In progress, 1: Declined, 2: Accepted
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Sets the current status of a TradeRequest. Should only be used when a User either declines or accepts a TradeRequest.
     *
     * @param status An integer, which represents the status of a TradeRequest. 0: In progress, 1: Declined, 2: Accepted
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the User who initiates the TradeRequest. Notice this is the User that will have an instance of TradeRequest in pendingRequests, unless otherwise
     * acted upon.
     *
     * @return The User who initiates the TradeRequest.
     */
    public User getRequester() {
        return this.requester;
    }

    /**
     * Gets the User who receives the TradeRequest. Notice this is the User that will have an instance of TradeRequest in pendingRequests, unless otherwise
     * acted upon.
     *
     * @return The User who receives the TradeRequest.
     */
    public User getReceiver() {
        return this.receiver;
    }

    /**
     * Gets the Item that is to be given, belonging to the User who initiates the TradeRequest
     *
     * @return The User who sends TradeRequest
     */
    public Item getRequesterItem() {
        return this.requesterItem;
    }

    /**
     * Gets the Item that the requesting User wants, belonging to the User who receives the TradeRequest
     *
     * @return The User who receives TradeRequest
     */
    public Item getReceiverItem() {
        return this.receiverItem;
    }

    /**
     * Gets whether the requester User would like to temporarily exchange items, or permanently exchange items. True: Temporary, False: Permanent.
     *
     * @return If the potential Transaction would be temporary.
     */
    public boolean getTemp() {
        return this.temp;
    }

    public Calendar getDate() {
        return this.date;
    }
}
