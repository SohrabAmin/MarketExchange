import java.util.Calendar;

public class typeTwoRequest extends TradeRequest {


    private User user1;
    private User user2;
    private Item item1;
    private Item item2;


    public typeTwoRequest(Item item1, Item item2, String message, boolean temp, Calendar date, boolean noMeeting) {
        super(message, temp, date, noMeeting);
        this.user1 = item1.getOwner();
        this.user2 = item2.getOwner();
        this.item1 = item1;
        this.item2 = item2;
    }


    public User getFirstUser() {
        return this.user1;
    }


    public User getSecondUser() {
        return this.user2;
    }


    public Item getFirstItem() {
        return this.item1;
    }


    public Item getSecondItem() {
        return this.item2;
    }


    /**
     * Returns a String representation of a TypeTwoRequest, with nicely formatted attributes
     *
     * @return String representation of this TypeTwoRequest
     */
    public String toString() {
        return "Two-way trade request: " +
                this.user1.getName() + " offers " + this.item1.getName() +
                " for " + this.user2.getName() + "'s " + this.item2.getName() +
                ". \nStatus: " + this.getStatus() +
                "; \nDate: " + this.getDate() +
                "; \nIs for a temporary trade?: " + this.getTemp() +
                "; \nIs meeting in-person?: " + !this.getNoMeeting() +
                "; \nMessage: " + this.getMessage() + "\n";
    }

}
