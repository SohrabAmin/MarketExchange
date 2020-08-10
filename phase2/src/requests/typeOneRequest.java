package requests;
import accounts.users.User;
import items.Item;

import java.util.Calendar;

/**
 * Constructs a transactions.OneWay requests.TradeRequest, that a accounts.users.User can send to another accounts.users.User if the want to initiated a transactions.Transaction. Notice this difference from a transactions.Transaction, as
 * the system does not create an instance of transactions.Transaction unless the corresponding requests.TradeRequest has been approved by the accounts.users.User receiving the requests.TradeRequest. Notice we
 * assuming noMeeting Transactions are permanent, as it's illogical to "return" and emailed item.
 */
public class typeOneRequest extends TradeRequest {


    private User user1;
    private User user2;
    private Item item;
    private boolean monetized;

    /**
     * Constructs a transactions.OneWay requests.TradeRequest instance. This class is instantiated by the accounts.users.User that attempts to initiate the transactions.Transaction, and should be stored within the receiving accounts.users.User's
     * pendingRequest. If the receiving accounts.users.User accepts the conditions of the transactions.Transaction, they may accept the requests.TradeRequest, and an instance of transactions.Transaction is created.
     * @param user1 The accounts.users.User who initiated the requests.TradeRequest
     * @param item The items.Item User1 currently wants.
     * @param message A String representation of any message user1 may want to send user2.
     * @param temp A boolean representing if the requests.TradeRequest will be temp or not.
     * @param date A Calender representing the date/time the requests.TradeRequest was sent.
     * @param virtual A boolean that determines the requests.TradeRequest will have no meeting (true: This transactions.Transaction will not have a meetings.Meeting, false: This transactions.Transaction will have a meetings.Meeting.)
     */
    public typeOneRequest(User user1, Item item, String message, boolean temp, Calendar date, boolean virtual, boolean monetized) {
        super(message, temp, date, virtual);
        this.user1 = user1;
        this.user2 = item.getOwner();
        this.item = item;
        this.monetized = monetized;
    }
    /**
     * Gets the accounts.users.User who initiates the requests.typeOneRequest
     * @return accounts.users.User that initiated the requests.typeOneRequest
     */
    public User getFirstUser() {
        return this.user1;
    }

    /**
     * Gets the accounts.users.User who receives the requests.typeTwoRequest
     * @return accounts.users.User that received requests.typeTwoRequest
     */
    public User getSecondUser() {
        return this.user2;
    }

    /**
     * Gets the items.Item that the accounts.users.User initiating the requests.TradeRequest (user1) wants from another accounts.users.User (user2).
     * @return items.Item that user1 wants from user2
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Returns a boolean if a OneWayRequest is monetized or not.
     * @return A boolean if the given OneWayRequest is monetized or not.
     */
    public boolean getMonetized(){
        return this.monetized;
    }

    /**
     * Returns a String representation of a TypeOneRequest, with nicely formatted attributes
     *
     * @return String representation of this TypeOneRequest
     */
    public String toString() {
        return "One-way trade request " +
                "from '" + this.user1.getName() + "' to '" + this.user2.getName() +
                "' for item '" + this.item.getName() +
                "'; \nStatus: " + this.getStatus() +
                "; \nDate: " + this.dateToString() +
                "; \nIs for a temporary trade?: " + this.getTemp() +
                "; \nIs meeting in-person?: " + !this.getVirtual() +
                "; \nMessage: " + this.getMessage() + "\n";
    }
}
