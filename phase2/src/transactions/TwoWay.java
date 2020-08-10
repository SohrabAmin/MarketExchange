package transactions;

import accounts.users.User;
import items.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Subclass of transactions.Transaction, instantiated when a transactions.TwoWay transactions.Transaction is made (an exchange of Items).
 */

public class TwoWay extends Transaction implements Serializable {
    private User user1;
    private User user2;
    private List<Item> items;

    /**
     * Constructor of transactions.TwoWay. Requires the item of the first accounts.users.User, the items.Item of the second accounts.users.User, and the boolean regarding if it's temporary. True: transactions.TwoWay is temporary. False: transactions.TwoWay is permanent.
     *
     * @param item1 The items.Item the first accounts.users.User is giving to the second accounts.users.User.
     * @param item2 The items.Item the second accounts.users.User is giving to the first accounts.users.User.
     * @param temp  If the transactions.TwoWay is temporary.
     */
    public TwoWay(Item item1, Item item2, boolean temp, boolean virtual) {

        super(temp, virtual);
        this.user1 = item1.getOwner();
        this.user2 = item2.getOwner();
        this.items = new ArrayList<>();
        this.items.add(item1);
        this.items.add(item2);
    }

    /**
     * Getter for the firstTrader of a transactions.TwoWay; The accounts.users.User who initiates the transactions.TwoWay.
     *
     * @return The firstTrader; the accounts.users.User who initiates the transactions.TwoWay.
     */
    public User getFirstTrader() {
        return this.user1;
    }

    /**
     * Setter for the secondTrader of a transactions.TwoWay; the accounts.users.User who accepts the transactions.TwoWay.
     *
     * @return The secondTrader; the accounts.users.User who accepts the transactions.TwoWay.
     */
    public User getSecondTrader() {
        return this.user2;
    }

    /**
     * Getter for the items.Item the firstTrader is giving.
     *
     * @return The items.Item the firstTrader is giving.
     */
    public Item getFirstItem() {
        return this.items.get(0);
    }

    /**
     * Getter for the items.Item the secondTrader is giving.
     *
     * @return The items.Item the secondTrader is giving.
     */
    public Item getSecondItem() {
        return this.items.get(1);
    }
}
