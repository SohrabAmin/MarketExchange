package transactions;

import accounts.users.User;

/** Constructs a transactions.OneWayMonetized requests.TradeRequest, that a accounts.users.User can send to another accounts.users.User if they want to initiate a transactions.Transaction. Notice this difference from a transactions.Transaction, as
 * the system does not create an instance of transactions.Transaction unless the corresponding requests.TradeRequest has been approved by the accounts.users.User receiving the requests.TradeRequest. Also
 * Notice that this type of transactions.Transaction will involve in-app currency, which distinguishes it from a transactions.OneWay. Notice we are
 * assuming noMeeting Transactions are permanent, as it's illogical to "return" and emailed item.
 *
 */
public class OneWayMonetized extends OneWay {


    private final double cost;
    private int rentDuration;
    private boolean person1EmailSent ;
    private boolean person2EmailSent ;
    private String email;


    /**
     * Constructs a transactions.OneWayMonetized object. This is the subclass of OneWayRequest, as it contains all attributes,
     * yet has an additional cost attribute, depending of if the transactions.Transaction is permanent or temporary. If the transactions.Transaction will
     * be temporary, then there is a rentDuration attribute that represents the amount of time the owner would like to rent out the items.Item.
     * @param user1 The accounts.users.User who initiated the requests.TradeRequest
     * @param item The items.Item User1 currently wants
     * @param temp A boolean representing if the requests.TradeRequest will be temp or not.
     * @param virtual A boolean that determines the requests.TradeRequest will have no meeting (true: This transactions.Transaction will not have a meetings.Meeting, false: This transactions.Transaction will have a meetings.Meeting.)
     */
    public OneWayMonetized(User user1, Item item, boolean temp, boolean virtual, String email){
        super(user1, item, temp, virtual);
        person1EmailSent = false;
        person2EmailSent = false;
        this.email = email;
        if(this.getTemp()){
            this.cost = item.getRentPrice();
        }else{
            this.cost = item.getSellPrice();
        }
    }

    public  String getEmail(){
        return email;

    }

    public void Person1Confirmed(){
        person1EmailSent = true;
    }

   public boolean getPerson1Confirmed(){
        return person1EmailSent;
   }

   public void Person2Confirmed(){
        person2EmailSent = true;
   }

    public boolean getPerson2Confirmed(){
        return person2EmailSent;
    }

    /**
     * Gets the cost of this items.Item. Note: If the requests.TradeRequest is temporary, the cost will represent the cost to rent. If the requests.TradeRequest
     * is permanent, it will represent the cost to buy.
     * @return A double of the current cost of the item.
     */
    public double getCost(){
        return this.cost;
    }

    /**
     * Sets the duration for which the owner of the items.Item will allow the receiver of the items.Item to borrow it for. Notice this is only
     * initiated if this requests.TradeRequest is temporary.
     * @param days Integer of the number of days the owner of the items.Item will allow the receiver of the items.Item to borrow it for.
     */
    public void setRentDuration(int days){
        this.rentDuration = days;
    }

    /**
     * Gets the duration for which the owner of the items.Item will allow the receiver of the items.Item to borrow it for. Notice this is only
     * initiated if this requests.TradeRequest is temporary.
     * @return Integer of the number of days the owner of the items.Item will allow the receiver of the items.Item to borrow it for.
     */
    public int getRentDuration(){
        return this.rentDuration;
    }
}
