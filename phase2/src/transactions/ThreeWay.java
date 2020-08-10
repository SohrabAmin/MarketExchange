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

import java.util.*;
/**
 * Represents threeway transactions, store and getter for all values of threeway transactions
 */
public class ThreeWay extends Transaction {


    private User firstTrader;

    private User secondTrader;

    private User thirdTrader;

    private List<Item> items;
    /**
     * construct instance of threeway transaction
     * @param item1 one of the three items to be traded
     * @param item2 one of the three items to be traded
     * @param item3 one of the three items to be traded
     * @param temp
     * @param virtual
     */

    public ThreeWay(Item item1, Item item2, Item item3, boolean temp, boolean virtual){
        super(temp, virtual);
        this.firstTrader = item1.getOwner();
        this.secondTrader = item2.getOwner();
        this.thirdTrader = item3.getOwner();
        this.items = new ArrayList<>();
        this.items.add(item1);
        this.items.add(item2);
        this.items.add(item3);
    }
    /**
     * Setter for the firstTrader of a ThreeWay; the User who accepts the Threeway
     *
     * @return The firstTrader; the User who accepts the ThreeWay.
     */
    public User getFirstTrader() {
        return this.firstTrader;
    }

    /**
     * Setter for the secondTrader of a transactions.TwoWay; the accounts.users.User who accepts the transactions.TwoWay.
     *
     * @return The secondTrader; the accounts.users.User who accepts the transactions.TwoWay.
     */
    public User getSecondTrader() {
        return this.secondTrader;
    }

    /**
     * Setter for the thirdTrader of a ThreeWay; the User who accepts the ThreeWay.
     *
     * @return The thirdTrader; the User who accepts the ThreeWay.
     */
    public User getThirdTrader(){return this.thirdTrader;}

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
    /**
     * Getter for the Item the thridTrader is giving.
     *
     * @return The Item the thirdTrader is giving.
     */
    public Item getThirdItem(){return this.items.get(2);}
}
