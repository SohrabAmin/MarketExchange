import java.util.Calendar;

public class typeOneRequest extends TradeRequest {


    private User user1;
    private User user2;
    private Item item;


    public typeOneRequest(User user1, Item item, String message, boolean temp, Calendar date, boolean noMeeting) {
        super(message, temp, date, noMeeting);
        this.user1 = user1;
        this.user2 = item.getOwner();
        this.item = item;
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
