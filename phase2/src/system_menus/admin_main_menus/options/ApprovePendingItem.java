package system_menus.admin_main_menus.options;

import accounts.admins.Admin;
import accounts.admins.AdminManager;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import currency.CurrencyManager;
import items.Item;
import items.ItemManager;
import requests.TradeRequestManager;
import transactions.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ApprovePendingItem implements AdminMainMenuOptions {

    /**
     * Checks for items pending approval from all Users in accounts.users.UserManager and displays them to admin
     * who can then decide to approve the item which will move the item to the accounts.users.User's inventory
     * or reject the item which will remove the item altogether.
     *
     * @param admin the current accounts.admins.Admin logged into the system
     * @param allAdmins accounts.admins.AdminManager which holds all the Admins, FrozenRequests and Thresholds in the system
     * @param allUsers accounts.users.UserManager which holds all the Users in the system
     * @param allItems items.ItemManager which holds the system inventory
     * @return depending on what the accounts.admins.Admin inputs it will return different objects:
     * returns null to tell mainmenu() to call execute() again
     * returns String "back" to tell mainmenu() to prompt main menu again so accounts.admins.Admin can choose another
     * main menu option
     */
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages, TransactionManager allTransactions,
                          TradeRequestManager allRequests, CurrencyManager allCurrency, Logger undoLogger) {
        //Creates a list of all pending items in the system
        List<Item> allPendingItems = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
            allPendingItems.addAll(allUsers.getAllUsers().get(i).getDraftInventory());
        }
        //no pending items
        if (allPendingItems.size() == 0) {
            System.out.println("There are no pending item requests to approve!\n");
            return "back";
        }
        //prints out all the pending items in the system
        System.out.println("Here are the pending items:");
        for (int i = 0; i < allPendingItems.size(); i++) {
            String j = Integer.toString(i + 1);
            System.out.println(j + ". " + allPendingItems.get(i).getName() + " : " + allPendingItems.get(i).getDescription());
        }
        //admin needs to choose an item to approve or reject
        System.out.println("Please enter the number of the item you would like to approve or 'back' to return to the main menu.");
        Scanner sc = new Scanner(System.in);
        Object input = sc.nextLine();
        if (input.equals("back")) {
            return "back";
        }
        try {
            input = Integer.parseInt((String) input);
        } catch (NumberFormatException e) {
            System.out.print("\n \uD83E\uDDD0 What was that? Please try again!\n");
            return null;
        }

        int pendingItemIndex = (Integer) input - 1;
        Item chosenItem = allPendingItems.get(pendingItemIndex);
        System.out.print("Input '1' to approve or input '2' to reject or 'back' to return to the list of pending items.\n");

        Object nextInput = sc.nextLine();
        if (nextInput.equals("back")) {
            return null;
        }
        try {
            nextInput = Integer.parseInt((String) nextInput);
        } catch (NumberFormatException e) {
            System.out.println("\uD83E\uDDD0 What was that? Please try again!");
            return null;
        }
        if ((Integer) nextInput == 1) { //if item is approved
            allUsers.approveDraftInventoryItem(allUsers.getUser(chosenItem.getOwner()), chosenItem, allItems);

            System.out.println("\u2705 Approved!");

            return null;
        } else if ((Integer) nextInput == 2) { //if item is rejected
            allUsers.rejectDraftInventoryItem(allUsers.getUser(chosenItem.getOwner()), chosenItem);
            System.out.println("\u274E Rejected!\n");
            return null;
        } else {
            System.out.println("\uD83E\uDDD0 What was that? Please try again!");
            return null;
        }
    }
}
