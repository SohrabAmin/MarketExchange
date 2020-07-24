import java.util.Calendar;

public class typeTwoRequest extends TradeRequest {


    private User user1;
    private User user2;
    private Item item1;
    private Item item2;


    public typeTwoRequest(Item item1, Item item2,  String message, boolean temp, Calendar date){
        super(message, temp, date);
        this.user1 = item1.getOwner();
        this.user2 = item2.getOwner();
        this.item1 = item1;
        this.item2 = item2;
    }


    public User getFirstUser(){
        return this.user1;
    }


    public User getSecondUser(){
        return this.user2;
    }


    public Item getFirstItem(){
        return this.item1;
    }


    public Item getSecondItem(){
        return this.item2;
    }


}
