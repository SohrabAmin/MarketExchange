import java.util.List;

public class TradeRequest {
    private User requester;
    private User receiver;
    private int requestType;
    private Item requesterItem;
    private Item receiverItem;
    private String message;
    private Boolean pending;


    public TradeRequest(int f, User a, User b, List<Item> c, String e){
        this.pending = true;
        this.requestType = f;
        this.requester = a;
        this.receiver = b;
        this.message = e;
        if(this.requestType == 1){
           this.requesterItem = null;
           this.receiverItem = c.get(0);
        }else{
            this.requesterItem = c.get(0);
            this.receiverItem = c.get(1);

        }
    }


    public String getRequestType(){
        if(this.requestType == 1){
            return "OneWay";
        }else{
            return "TwoWay";
        }
    }

    public void setRequestType(int x){
        this.requestType = x;
    }

    public Boolean getPending(){
        return this.pending;
    }

    public void setPending(Boolean x){
        this.pending = x;
    }


}
