import java.util.Calendar;

/**
 * Constructs a OneWay TradeRequest, that a User can send to another User if the want to initiated a Transaction. Notice this difference from a Transaction, as
 * the system does not create an instance of Transaction unless the corresponding TradeRequest has been approved by the User receiving the TradeRequest. Notice we
 * assuming noMeeting Transactions are permanent, as it's illogical to "return" and emailed item.
 */
public class typeOneRequest extends TradeRequest {


    private User user1;
    private User user2;
    private Item item;
    private boolean monetized;

    /**
     * Constructs a OneWay TradeRequest instance. This class is instantiated by the User that attempts to initiate the Transaction, and should be stored within the receiving User's
     * pendingRequest. If the receiving User accepts the conditions of the Transaction, they may accept the TradeRequest, and an instance of Transaction is created.
     * @param user1 The User initiated the TradeRequest
     * @param item The Item User1 currently wants.
     * @param message A String representation of any message user1 may want to send user2.
     * @param temp A boolean representing if the TradeRequest will be temp or not.
     * @param date A Calender representing the date/time the TradeRequest was sent.
     * @param noMeeting A boolean that determines the TradeRequest will have no meeting (true: This Transaction will not have a Meeting, false: This Transaction will have a Meeting.)
     */
    public typeOneRequest(User user1, Item item, String message, boolean temp, Calendar date, boolean noMeeting, boolean monetized) {
        super(message, temp, date, noMeeting);
        this.user1 = user1;
        this.user2 = item.getOwner();
        this.item = item;
        this.monetized = monetized;
    }

    public User getFirstUser() {
        return this.user1;
    }


    public User getSecondUser() {
        return this.user2;
    }


    public Item getItem() {
        return this.item;
    }

    /**
     * Returns a String representation of a TypeOneRequest, with nicely formatted attributes
     *
     * @return String representation of this TypeOneRequest
     */
    public String toString() {
        return "One-way trade request " +
                "from " + this.user1.getName() + " to " + this.user2.getName() +
                " for item " + this.item.getName() +
                "; \nStatus: " + this.getStatus() +
                "; \nDate: " + this.getDate() +
                "; \nIs for a temporary trade?: " + this.getTemp() +
                "; \nIs meeting in-person?: " + !this.getNoMeeting() +
                "; \nMessage: " + this.getMessage() + "\n";
    }

}
