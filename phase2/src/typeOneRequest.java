import java.util.Calendar;

public class typeOneRequest extends TradeRequest {


    private User user1;
    private User user2;
    private Item item;


    public typeOneRequest(User user1, Item item, String message, boolean temp, Calendar date){
        super(message, temp, date);
        this.user1 = user1;
        this.user2 = item.getOwner();
        this.item = item;
    }

    public User getFirstUser(){
        return this.user1;
    }


    public User getSecondUser(){
        return this.user2;
    }


    public Item getItem(){
        return this.item;
    }


}
