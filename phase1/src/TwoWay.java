import java.util.ArrayList;
import java.util.List;

/**
 *  Subclass of Transaction, instantiated when a two way trade is made (an exchange of Items)
 */

public class TwoWay extends Transaction {
    User firstTrader;
    User secondTrader;
    List<Item> items;

    /**
     * Constructor of TwoWay. Requires the item of the first User, the Item of the second User, and the boolean regarding if it's temporary. True: TwoWay is temporary. False: TwoWay is permanent
     * @param item1 The Item the first User is giving to the second User
     * @param item2 The Item the second User is giving to the first User
     * @param temp If the TwoWay is temporary
     */
    public TwoWay(Item item1, Item item2, boolean temp) {

        super(temp);
        this.firstTrader = item1.getOwner();
        this.secondTrader = item2.getOwner();
        items = new ArrayList<>();
        this.items.add(item1);
        this.items.add(item2);
    }

    /**
     * Getter for the first trader of a TwoWay; The User who initiates the TwoWay
     * @return The first trader; the User who initiates the TwoWay
     */
    public User getFirstTrader(){
        return this.firstTrader;
    }

    /**
     * Setter for the second trader of a TwoWay; the User who accepts the TwoWay
     * @return The second trader; the User who accepts the TwoWay
     */
    public User getSecondTrader() {
        return this.secondTrader;
    }

    /**
     * Getter for the Item the first trader is giving
     * @return The Item the first trader is giving
     */
    public Item getFirstItem(){
        return this.items.get(0);
    }

    /**
     * Getter for the Item the second trader is giving
     * @return The Item the second trader is giving
     */
    public Item getSecondItem() {
        return this.items.get(1);
    }
}
