import java.util.Calendar;

/**
 * Constructs a ThreeWay TradeRequest, that a User can send to another User if the want to initiated a Transaction. Notice this difference from a Transaction, as
 * the system does not create an instance of Transaction unless the corresponding TradeRequest has been approved by the User receiving the TradeRequest. Notice we
 * assuming noMeeting Transactions are permanent, as it's illogical to "return" and emailed item.
 */
public class typeThreeRequest extends TradeRequest {


    private User user1;
    private User user2;
    private User user3;
    private Item item1;
    private Item item2;
    private Item item3;

    /**
     * Constructs a ThreeWay TradeRequest instance. This class is instantiated by the User that attempts to initiate the Transaction, and should be stored within the receiving User's
     * pendingRequest. If the receiving User accepts the conditions of the Transaction, they may accept the TradeRequest, and an instance of Transaction is created. Notice this is only called
     * once a User has received a twoWayRequest, and would like to add in a third User (perhaps they want the Item user1 is offering, but doesn't want to give up the Item user1 wants).
     * @param item1 The Item belonging to User1, the User who requested the twoWay.
     * @param item2 The Item belonging to User2, the User requesting the threeWay.
     * @param item3 The Item belonging to User3.
     * @param message A String representation of any message user1 may want to send user2.
     * @param temp A boolean representing if the TradeRequest will be temp or not.
     * @param date A Calender representing the date/time the TradeRequest was sent.
     * @param noMeeting A boolean that determines the TradeRequest will have no meeting (true: This Transaction will not have a Meeting, false: This Transaction will have a Meeting.)
     */
    public typeThreeRequest(Item item1, Item item2, Item item3,  String message, boolean temp, Calendar date, boolean noMeeting){
        super(message, temp, date, noMeeting);
        this.user1 = item1.getOwner();
        this.user2 = item2.getOwner();
        this.user3 = item3.getOwner();
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }

    public User getFirstUser(){
        return this.user1;
    }


    public User getSecondUser(){
        return this.user2;
    }


    public User getThirdUser(){
        return this.user3;
    }

    public Item getFirstItem(){
        return this.item1;
    }


    public Item getSecondItem(){
        return this.item2;
    }


    public Item getThirdItem(){
        return this.item3;
    }
}
