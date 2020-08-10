package requests;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Constructs a trade request, that a User can send to another User if the want to initiated a transactions.Transaction. Notice this difference from a transactions.Transaction, as
 * the system does not create an instance of transactions.Transaction unless the corresponding requests.TradeRequest has been approved by the User receiving the requests.TradeRequest. Notice we
 * assuming noMeeting Transactions are permanent, as it's illogical to "return" and emailed item.
 */
public class TradeRequest implements Serializable {
    private String message;
    private boolean temp;
    private int status;
    private Calendar date;
    private boolean virtual;

    /**
     * Constructs a requests.TradeRequest instance. This class is instantiated by the User that attempts to initiate the transactions.Transaction, and should be stored within the receiving User's
     * pendingRequest. If the receiving User accepts the conditions of the transactions.Transaction, they may accept the requests.TradeRequest, and an instance of transactions.Transaction is created.
     * @param date        A Calender representing the date/time the requests.TradeRequest was initiated.
     * @param virtual   A boolean representing if a meeting will be required or not.
     * @param message     A string representation of any message the requester User may want to send to the receiver User.
     * @param temp        A boolean regarding if the User requesting a requests.TradeRequest wants a temporary transactions.Transaction or not.
     */
    public TradeRequest(String message, boolean temp, Calendar date, boolean virtual) {
        this.message = message;
        this.status = 0;
        this.temp = temp;
        this.date = date;
        this.virtual = virtual;
    }

    /**
     * Gets the message the User initiating the requests.TradeRequest sent to the User receiving the requests.TradeRequest.
     *
     * @return String representation of the message.
     */
    public String getMessage() {
        return message;
    }


    /**
     * Gets the current status of a requests.TradeRequest.
     *
     * @return An integer, which represents the status of a requests.TradeRequest. 0: In progress, 1: Declined, 2: Accepted
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Sets the current status of a requests.TradeRequest. Should only be used when a User either declines or accepts a requests.TradeRequest.
     *
     * @param status An integer, which represents the status of a requests.TradeRequest. 0: In progress, 1: Declined, 2: Accepted
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets whether the requester User would like to temporarily exchange items, or permanently exchange items. True: Temporary, False: Permanent.
     *
     * @return If the potential transactions.Transaction would be temporary.
     */
    public boolean getTemp() {
        return this.temp;
    }

    /**
     * Gets the date for which the requests.TradeRequest was initiated.
     * @return A Calender that represents the date for which the requests.TradeRequest was sent.
     */
    public Calendar getDate() {
        return this.date;
    }

    /**
     * Gets whether the User would like the potential transactions.Transaction to be "online" or not. Notice: Only certain Items
     * will be eligible to be involved in a Virtual trade. These Transactions also do not require any meeting, and
     * is always a permanent transactions.Transaction.
     * @return Boolean of whether the potential transactions.Transaction will be virtual or not.
     */
    public Boolean getVirtual() {
        return this.virtual;
    }

    /**
     * Gets a String representation of this requests.TradeRequest's date (which is of type Calender). It is of format yyyy-mm-dd.
     * @return A String representation of the date.
     */
    public String dateToString() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy MM dd");
        String formatted = format1.format(this.date.getTime());
        return formatted;
    }
}
