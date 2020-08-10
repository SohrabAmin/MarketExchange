package system;

import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import accounts.admins.Admin;
import accounts.admins.AdminManager;
import currency.CurrencyManager;
import items.Item;
import items.ItemManager;
import requests.TradeRequestManager;
import system_menus.ChosenOption;
import system_menus.admin_main_menus.options.*;
import transactions.TransactionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Deals with the input of an accounts.admins.Admin user-- particularly deals with the login system and displaying of main menu.
 */

public class AdminInputGetter {

    /**
     * Displays the main menu of an AdminUser and prompts the user for input.
     *
     * @param admin accounts.Account of the accounts.admins.Admin.
     * @return depending on what the accounts.admins.Admin inputs it will return different objects:
     * returns accounts.admins.Admin to system.TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another accounts.admins.Admin to log in
     * returns String "exit" to tell system.TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object mainMenu(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                           UserMessageManager allUserMessages, TransactionManager allTransactions,
                           TradeRequestManager allRequests, CurrencyManager allCurrency) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + admin.getName());

        List<Item> allPendingItems = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
            allPendingItems.addAll(allUsers.getAllUsers().get(i).getDraftInventory());
        }

        if (allAdmins.getFrozenRequests().size() > 0) {
            System.out.println("\uD83D\uDCF3 You have " + allAdmins.getFrozenRequests().size() + " Frozen user requests!");
        }
        //if they have frozen accounts.users, show it here
        if (allUsers.getAllFrozenUsers().size() > 0) {
            System.out.println("\u2603 You have " + allUsers.getAllFrozenUsers().size() + " Frozen accounts.users!");
        }

        //if they have pending items, show it here
        if (allPendingItems.size() > 0) {
            System.out.println("\uD83D\uDCE9 You have " + allPendingItems.size() + " Pending items.Item Requests!");
        }
        System.out.println("Please select from the following by entering the number beside the option:" +
                " \n1. Add new admin\n2. Change system threshold\n3. View items that need to be approved\n" +
                "4. Freeze or unfreeze accounts.users\n5. Promote a user or demote a VIP user\n" +
                "6. Promote an admin or demote a super admin\n7. View Messages from Users\n8. View and edit System Log\n9. Log out\n" +
                "Enter 'exit' to exit at any time.");
        ChosenOption option = new ChosenOption();
        try {
            String input = br.readLine();
            if (!input.equals("exit")) {
                switch (input) {
                    //depending on their input, the correct "strategy" will be created and stored in the system_options.ChosenOption class
                    case "1":  //add new admin
                        option.setChosenOption(new addAdmin());
                        break;
                    case "2":  //change system threshold
                        option.setChosenOption(new changeThreshold());
                        break;
                    case "3":  //view items that need to be approved
                        option.setChosenOption(new approvePendingItem());
                        break;
                    case "4":  //freeze or unfreeze accounts.users
                        option.setChosenOption(new FreezeOrUnfreeze());
                        break;
                    case "5": //promote a user or demote a VIP user
                        option.setChosenOption(new PromoteOrDemoteUser());
                        break;
                    case "6":  //promote an admin or demote a super admin
                        option.setChosenOption(new PromoteOrDemoteAdmin());
                        break;
                    case "7":
                        option.setChosenOption(new ViewUserMessages());
                        break;
                    case "8":  //view and/or edit system log
                        option.setChosenOption(new UndoAction());
                        break;
                    case "9":  //logout
                        return null;
                    default:  //returns to main menu
                        System.out.println("That is not a valid option. Please try again.");
                        return admin;
                }
                //the option that is chosen by the accounts.admins.Admin will be run
                Object result = option.executeOption(admin, allAdmins, allUsers, allItems, allUserMessages, allTransactions, allRequests, allCurrency);
                //if the execute() method of the option returns null, the option will be run again until the accounts.admins.Admin
                //specifies that they want to return to the main menu
                while (result == null) {
                    result = option.executeOption(admin, allAdmins, allUsers, allItems, allUserMessages, allTransactions, allRequests, allCurrency);
                }
                return admin;
            }
            return input;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        return admin;
    }

}