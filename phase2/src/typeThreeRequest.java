import java.util.Calendar;

public class typeThreeRequest extends TradeRequest {


    private User user1;
    private User user2;
    private User user3;
    private Item item1;
    private Item item2;
    private Item item3;


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
