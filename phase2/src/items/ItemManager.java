package items;

import accounts.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates, keeps track of, and changes values of Users.
 */

public class ItemManager implements Serializable {

    private List<Item> systemInventory;

    private List<Item> tradableItem;

    private List<Item> sellableItem;

    private List<Item> rentableItem;

    // private List<Item> deniedInventory;

    /**
     * create inventory of all items in the system , and create separate list of tradable, sellable, rentable items
     */
    public ItemManager() {
        this.systemInventory = new ArrayList<>();
        this.tradableItem = new ArrayList<>();
        this.sellableItem = new ArrayList<>();
        this.rentableItem = new ArrayList<>();
    }

    /**
     * Getter for the symtem inventory
     *
     * @return all the item in the system as List
     */
    public List<Item> getSystemInventory() {
        return systemInventory;
    }

    /**
     * Getter for all tradable items
     *
     * @return all tradable items in the system as List
     */
    public List<Item> getTradableItem() {
        return this.tradableItem;
    }

    /**
     * Getter for all sellable items
     *
     * @return all sellable items in the system as List
     */
    public List<Item> getSellableItem() {
        return this.sellableItem;
    }

    /**
     * Getter for all rentable items
     *
     * @return all rentable items in the system as List
     */
    public List<Item> getRentableItem() {
        return this.rentableItem;
    }

    /**
     * add item to system inventory, tradable list , rentable list and sellable list
     *
     * @param item add item to inventory list
     *             if tradable, add to tradable list
     *             if rentable, add to rentable list
     *             if sellable, add to sellable list
     */
    public void addItem(Item item) {
        systemInventory.add(item);
        if (item.getTradable()) {
            this.tradableItem.add(item);
        } else if (item.getRentable()) {
            this.rentableItem.add(item);
        } else {
            this.sellableItem.add(item);
        }
    }

    /**
     * setter for item's owner
     *
     * @param item
     * @param user
     */
    public void setOwner(Item item, User user) {
        item.setOwner(user);
    }

    /**
     * setter for current holder( who current have this item, e.g. the borrower)
     *
     * @param item
     * @param user
     */
    public void setCurrentHolder(Item item, User user) {
        item.setCurrentHolder(user);
    }

    /**
     * setter for systemInventory
     *
     * @param inventory
     */
    public void setSystemInventory(List<Item> inventory) {
        this.systemInventory = inventory;
    }

    public void changeTradable(Item item, boolean tradable) {
        item.changeTradable(tradable);
    }

    public void changeSellable(Item item, boolean sellable) {
        item.changeSellable(sellable);
    }

    public void changeRentable(Item item, boolean rentable) {
        item.changeRentable(rentable);
    }

}