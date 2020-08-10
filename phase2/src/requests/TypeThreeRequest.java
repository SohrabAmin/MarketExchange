package requests;

import accounts.users.User;
import items.Item;
import meetings.AvailabilityChart;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Constructs a transactions.ThreeWay requests.TradeRequest, that a accounts.users.User can send to another accounts.users.User if the want to initiated a transactions.Transaction. Notice this difference from a transactions.Transaction, as
 * the system does not create an instance of transactions.Transaction unless the corresponding requests.TradeRequest has been approved by the accounts.users.User receiving the requests.TradeRequest. Notice we
 * assuming noMeeting Transactions are permanent, as it's illogical to "return" and emailed item.
 */
public class TypeThreeRequest extends TradeRequest {


    private User user1;
    private User user2;
    private User user3;
    private Item item1;
    private Item item2;
    private Item item3;
    private int approved = 0;
    private User firstApproved = null;
    private AvailabilityChart user1Availability;
    private AvailabilityChart user2Availability;
    private AvailabilityChart user3Availability;
    private ArrayList<String> firstUserLocation;
    private ArrayList<String> secondUserLocation;
    private String thirdUserLocation;





    /**
     * Constructs a transactions.ThreeWay requests.TradeRequest instance. This class is instantiated by the accounts.users.User that attempts to initiate the transactions.Transaction, and should be stored within the receiving accounts.users.User's
     * pendingRequest. If the receiving accounts.users.User accepts the conditions of the transactions.Transaction, they may accept the requests.TradeRequest, and an instance of transactions.Transaction is created. Notice this is only called
     * once a accounts.users.User has received a twoWayRequest, and would like to add in a third accounts.users.User (perhaps they want the items.Item user1 is offering, but doesn't want to give up the items.Item user1 wants).
     * @param item1 The items.Item belonging to User1, the accounts.users.User who requested the twoWay.
     * @param item2 The items.Item belonging to User2, the accounts.users.User requesting the threeWay.
     * @param item3 The items.Item belonging to User3.
     * @param message A String representation of any message user1 may want to send user2.
     * @param temp A boolean representing if the requests.TradeRequest will be temp or not.
     * @param date A Calender representing the date/time the requests.TradeRequest was sent.
     * @param virtual A boolean that determines the requests.TradeRequest will have no meeting (true: This transactions.Transaction will not have a meetings.Meeting, false: This transactions.Transaction will have a meetings.Meeting.)
     */
    public TypeThreeRequest(Item item1, Item item2, Item item3, String message, boolean temp, Calendar date, boolean virtual){
        super(message, temp, date, virtual);
        this.user1 = item1.getOwner();
        this.user2 = item2.getOwner();
        this.user3 = item3.getOwner();
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        user1Availability = new AvailabilityChart();
        user2Availability = new AvailabilityChart();
        user3Availability = new AvailabilityChart();
        firstUserLocation = new ArrayList<>();
        secondUserLocation = new ArrayList<>();
        thirdUserLocation = "";


    }

    /**
     * Gets a String representation of the firstUser's location. This is for ensuring participants in a transactions.Transaction
     * are within the same city.
     * @return String representation of the firstUser's location.
     */
    public ArrayList<String> getFirstUserLocation(){
        return firstUserLocation;
    }

    /**
     * Sets the location of the firstUser. This is for ensuring participants in a potential transactions.Transaction are within the same city.
     * @param location A String of the accounts.users.User's location.
     */
    public void addtoFirstUserLocation (String location){
        firstUserLocation.add(location);
    }

    /**
     * Gets a String representation of the secondUser's location. This is for ensuring participants in a transactions.Transaction
     * are within the same city.
     * @return String representation of the secondUser's location.
     */
    public ArrayList<String> getSecondUserLocation(){
        return secondUserLocation;
    }
    /**
     * Sets the location of the secondUser. This is for ensuring participants in a potential transactions.Transaction are within the same city.
     * @param location A String of the accounts.users.User's location.
     */
    public void addtoSecondUserLocation (String location){
        secondUserLocation.add(location);
    }
    /**
     * Gets a String representation of the thirdUser's location. This is for ensuring participants in a transactions.Transaction
     * are within the same city.
     * @return String representation of the thirdUser's location.
     */
    public String getthirdUserLocation(){
        return thirdUserLocation;
    }
    /**
     * Sets the location of the thirdUser. This is for ensuring participants in a potential transactions.Transaction are within the same city.
     * @param location A String of the accounts.users.User's location.
     */
    public void setThirdUserLocation (String location){
        thirdUserLocation = location;
    }
    /**
     * Gets the accounts.users.User who initially sent the requests.TypeTwoRequest to user2
     * @return accounts.users.User that initially sent the requests.TypeTwoRequest
     */
    public User getFirstUser(){
        return this.user1;
    }

    /**
     * Gets the accounts.users.User who initiated the requests.TypeThreeRequest
     * @return accounts.users.User that initiated the requests.TypeThreeRequest
     */
    public User getSecondUser(){
        return this.user2;
    }

    /**
     * Gets the accounts.users.User who user2 wanted to bring in for the requests.TypeThreeRequest
     * @return accounts.users.User that user2 wanted to bring in for the requests.TypeThreeRequest
     */
    public User getThirdUser(){
        return this.user3;
    }

    /**
     * Gets the items.Item that user1 would be giving to user2.
     * @return items.Item that user1 would be giving to user2.
     */
    public Item getFirstItem(){
        return this.item1;
    }

    /**
     * Gets the items.Item that user2 would be giving to user3.
     * @return items.Item that user2 would be giving to user3.
     */
    public Item getSecondItem(){
        return this.item2;
    }
    /**
     * Gets the items.Item that user3 would be giving to user1.
     * @return items.Item that user2 would be giving to user1.
     */

    public Item getThirdItem(){
        return this.item3;
    }

    /**
     * Increments the approve attribute by 1 if one of the remaining two Users has confirmed
     */
    public void userApproves(){
        this.approved += 1;
    }

    /**
     * Gets the number of people that have currently approved. Notice: 0: user1 & user3 have not approved yet. 1: One of user1 or user3 has approved. 2: All three
     * Users have approved. (we are assuming user2 approves, as they sent the request)
     * @return An int of the number of people that have approved this requests.TradeRequest.
     */
    public int getApproved(){
        return approved;
    }

    /**
     * Sets the first accounts.users.User to approve the ThreeWayRequest (either user1 or user3)
     * @param user The accounts.users.User who first approved the requests.TradeRequest
     */
    public void setFirstApproved(User user){
        this.firstApproved = user;
    }

    /**
     * Gets the first accounts.users.User to approve the ThreeWayRequest (either user1 or user3)
     * @return The accounts.users.User who first approved the requests.TradeRequest
     */
    public User getFirstApproved(){
        return this.firstApproved;
    }

    /**
     * Gets User1's availability for the next 7 days. This aids in planning the meetings.Meeting.
     * @return HashMap of user1 availability for the next 7 days.
     */
    public AvailabilityChart getUser1Availability() {
        return user1Availability;
    }
    /**
     * Gets User2's availability for the next 7 days. This aids in planning the meetings.Meeting.
     * @return HashMap of user2 availability for the next 7 days.
     */
    public AvailabilityChart getUser2Availability(){
        return user2Availability;
    }
    /**
     * Gets User3s availability for the next 7 days. This aids in planning the meetings.Meeting.
     * @return HasMap of user3 availability for the next 7 days.
     */
    public AvailabilityChart getUser3Availability(){
        return user3Availability;
    }
}
