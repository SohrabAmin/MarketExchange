package transactions;

import accounts.users.User;
import transactions.Transaction;
import transactions.*;
import meetings.*;
import items.*;
import accounts.users.*;
import accounts.admins.*;
import requests.*;
import currency.*;
import system_menus.admin_main_menus.options.*;
import system_menus.user_main_menus.options.*;

import java.io.Serializable;

/**
 * Subclass of transactions.Transaction, instantiated when a OnwWay transactions.Transaction is made (an attempt to strictly borrow/lend).
 */
public class OneWay extends Transaction implements Serializable {

    private final User user1;

    private final User user2;

    private final Item item;

    /**
     * Constructor for transactions.OneWay. Requires a borrower of type accounts.users.User, an items.Item for the accounts.users.User to attain, and a boolean temp which specifies if transactions.OneWay is temporary.
     *
     * @param user1 The accounts.users.User that intends to borrow an item from another accounts.users.User.
     * @param item     The items.Item the borrower intends to attain.
     * @param temp     If the transactions.OneWay is temporary or not.
     */
    public OneWay(User user1, Item item, boolean temp, boolean virtual) {
        super(temp, virtual);
        this.user1 = user1;
        this.user2 = item.getOwner();
        this.item = item;
    }

    /**
     * Getter for the borrower of the transactions.OneWay.
     *
     * @return The borrower of a given transactions.OneWay.
     */
    public User getFirstTrader() {
        return this.user1;
    }

    /**
     * Getter for the lender, or the accounts.users.User giving an items.Item to another accounts.users.User.
     *
     * @return The lender of a given transactions.OneWay.
     */
    public User getSecondTrader() {
        return this.user2;
    }

    /**
     * Setter for the lender, or the accounts.users.User giving an items.Item to another accounts.users.User.
     *
     * @return The lender of a given transactions.OneWay.
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Returns a String representation of a transactions.Transaction, with nicely formatted attributes
     *
     * @return String representation of this transactions.Transaction
     */
    public String toString() {

        return "Transaction; One-Way" +
                "; \nTrader 1: " + getFirstTrader().getName() +
                "; \nTrader 2: " + getSecondTrader().getName() + " Item: " + getItem().getName() +
                "; \nStatus: " + getTradeStatus() +
                "; \nIs temporary?: " + getTemp() +
                "; \nIs in-person?: " + !getVirtual() +
                "; \nInitial meeting: " + getInitialMeeting().toString() +
                "; \nReturn meeting: " + getReturnMeeting().toString() + ".\n";
    }
}