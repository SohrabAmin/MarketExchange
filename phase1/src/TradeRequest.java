import java.util.List;

public class TradeRequest {
    private User requester;
    private User receiver;
    private int requestType;
    private Item requesterItem;
    private Item receiverItem;
    private String message;
    private Boolean pending;
    private int status;

    public TradeRequest(int requestType, User requester, User receiver, List<Item> itemList, String message){
        this.pending = true;
        this.requestType = requestType;
        this.requester = requester;
        this.receiver = receiver;
        this.message = message;
        if(this.requestType == 1){
           this.requesterItem = null;
           this.receiverItem = itemList.get(0);
        }else{
            this.requesterItem = itemList.get(0);
            this.receiverItem = itemList.get(1);

        }
    }


    public String getRequestType(){
        if(this.requestType == 1){
            return "OneWay";
        }else{
            return "TwoWay";
        }
    }

    public void setRequestType(int requestType){
        this.requestType = requestType;
    }

    public Boolean getPending(){
        return this.pending;
    }

    public void setPending(Boolean pending){
        this.pending = pending;
    }


}
