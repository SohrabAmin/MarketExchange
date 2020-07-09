import java.util.List;

public class TradeRequest {
    private User requester;
    private User receiver;
    private int requestType;
    private Item requesterItem;
    private Item receiverItem;
    private String message;
    private boolean temp;
    private int status;

    public TradeRequest(int requestType, User requester, User receiver, List<Item> itemList, String message, boolean temp){
        this.requestType = requestType;
        this.requester = requester;
        this.receiver = receiver;
        this.message = message;
        this.status = 0;
        this.temp = temp;
        if(this.requestType == 1){
           this.requesterItem = null;
           this.receiverItem = itemList.get(0);
        }else{
            this.requesterItem = itemList.get(0);
            this.receiverItem = itemList.get(1);
        }
    }

    public int getRequestType(){
       return this.requestType;
    }

    public void setRequestType(int requestType){
        this.requestType = requestType;
    }

   public int getStatus(){
        return this.status;
   }

   public void setStatus(int status){
        this.status = status;
   }

   public User getRequester(){
        return this.requester;
   }

   public User getReceiver(){
        return this.receiver;
   }

   public Item getRequesterItem(){
        return this.requesterItem;
   }

   public Item getReceiverItem(){
        return this.receiverItem;
   }

   public boolean getTemp(){
        return this.temp;
   }
}
