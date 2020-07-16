import java.io.Serializable;

/**
 * Subclass of Transaction, instantiated when a OnwWay Transaction is made (an attempt to strictly borrow/lend).
 */
public class OneWay extends Transaction implements Serializable {

    private final User borrower;

    private final User lender;

    private final Item item;

    /**
     * Constructor for OneWay. Requires a borrower of type User, an Item for the User to attain, and a boolean temp which specifies if OneWay is temporary.
     *
     * @param borrower The User that intends to borrow an item from another User.
     * @param item     The Item the borrower intends to attain.
     * @param temp     If the OneWay is temporary or not.
     */
    public OneWay(User borrower, Item item, boolean temp) {
        super(temp);
        this.borrower = borrower;
        this.lender = item.getOwner();
        this.item = item;
    }

    /**
     * Getter for the borrower of the OneWay.
     *
     * @return The borrower of a given OneWay.
     */
    public User getBorrower() {
        return this.borrower;
    }

    /**
     * Getter for the lender, or the User giving an Item to another User.
     *
     * @return The lender of a given OneWay.
     */
    public User getLender() {
        return this.lender;
    }

    /**
     * Setter for the lender, or the User giving an Item to another User.
     *
     * @return The lender of a given OneWay.
     */
    public Item getLenderItem() {
        return this.item;
    }

}