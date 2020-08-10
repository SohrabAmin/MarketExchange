package system_menus.user_main_menus.options;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import transactions.*;
import meetings.*;
import items.*;
import accounts.users.*;
import accounts.admins.*;
import requests.*;
import currency.*;

public class Browse implements UserMainMenuOptions {

    /**
     * Allows accounts.users.User user to browse the System's inventory and add any of the items in the System's inventory to
     * their wishlist.
     *  @param user     the accounts.users.User that wishes to browse the inventory and add items to their wishlist
     * @param allItems the items.ItemManager that stores the system's inventory
     * @param allUsers the accounts.users.UserManager that stores the accounts.users.User user
     * @param currencyManager
     * @return null if the current menu is to be reprinted; accounts.users.User user if the user is to be redirected to the main menu;
     * String "exit" if the user is to be logged out.
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        List<Item> allItems2 = allItems.getSystemInventory();
        //if there are no items in the system inventory
        if (allItems2.size() == 0) {
            System.out.println("There are no items to browse!");
            return "back";
        }

        allItems2 = DisplayBrowse(user, allItems);
        while (allItems2 == null) {
            allItems2 = DisplayBrowse(user, allItems);
        }
        //asks the user if they want to add an item to their wishlist
        System.out.println("Enter ID of the item you would like to add to your wishlist or type 'back' to get to main menu.");

        Scanner sc = new Scanner(System.in);
        Object input = sc.nextLine();
        if (input.equals("back")) {
            return "back";
        }
        try {
            input = Integer.parseInt((String) input);
        } catch (NumberFormatException e) {
            System.out.println("\n\uD83E\uDDD0 This ID is invalid. Please try again!");
            return null;
        }
        //checks to see if the ID of the item actually exists
        if ((Integer) input <= allItems2.size()) {
            Item item = allItems2.get((Integer) input - 1);
            //checks to see if the item is already in the user's wishlist
            for (int i = 0; i < user.getWishlist().size(); i++) {
                if (item.equals(user.getWishlist().get(i))) {
                    System.out.println("\nitems.Item is already in your wishlist!");
                    return null;
                }
            }
            allUsers.addToFC(item.getCategory(), user);
            allUsers.addToWishlist(user, item);
            System.out.println("\nitems.Item has been added to your wishlist \uD83C\uDF20");
            return null;
        } else {
            System.out.println("\n\uD83E\uDDD0 This ID is invalid. Please try again!");
        }
        return "back";
    }

    /**
     * Prints out all items that we have in the program
     *
     * @param user     the accounts.users.User that wants to see their inventory
     * @param allItems stores all the items in the system
     * @return returns a accounts.users.User which will prompt the main menu
     */

    public List<Item> DisplayBrowse(User user, ItemManager allItems) {

        List<Item> allItems2 = allItems.getSystemInventory();
        if (user.getLocation() != null) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter '1' to view only items from accounts.users in the same location as you or "
                    + "'2' to view all items in the system.");
            String input = sc.nextLine();
            if (input.equals('1')) {
                List<Item> temp = new ArrayList<>();
                for (int i = 0; i < allItems.getSystemInventory().size(); i++) {
                    if (allItems.getSystemInventory().get(i).getOwner().getLocation().equals(user.getLocation())) {
                        temp.add(allItems.getSystemInventory().get(i));
                    }
                }
                if (temp.size() == 0) {
                    System.out.println("There are no items from other accounts.users in the same location as you. "
                            + "The system will display all current items regardless of location instead.");
                } else { //temp.size() != 0
                    allItems2 = temp;
                }
            } else if (!input.equals('2')) {
                System.out.println("That is not a valid option, please try again!");
                return null;
            }
        }

        System.out.println("\nHere are the current items in the system's inventory:\n");

        //Display every item we have. Make sure if the item is owned by the user, show that it is owned by user
        //shows the price if it is for sale
        for (int i = 0; i < allItems2.size(); i++) {
            String selfowned = "";
            String sell = "";
            String rent = "";
            String trade = "";


            if (allItems2.get(i).getSellable()) sell = " [SELLABLE] price: " + allItems2.get(i).getSellPrice() + " ";
            if (allItems2.get(i).getRentable()) rent = " [RENTABLE] price: " + allItems2.get(i).getRentPrice() + " per " + allItems2.get(i).getRentDuration() + " days ";
            if (allItems2.get(i).getTradable()) trade = " [TRADABLE] ";



            String emoji = "\uD83D\uDCE6 ";
            if (allItems2.get(i).getOwner().getName().equals(user.getName())) { //change the emoji and add OWNED BY YOU if the user is the owner of the item
                selfowned = "  [OWNED BY YOU] ";
                emoji = "\u2714 ";
            }

            System.out.println(emoji + (i + 1) + ". " + allItems2.get(i).getName() + ": "
                    + allItems2.get(i).getDescription() + selfowned + sell + rent + trade + "\n");
        }
        return allItems2;
    }
}
