package system_menus.admin_main_menus.options;
import accounts.admins.Admin;
import accounts.admins.AdminManager;
import accounts.users.User;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import currency.CurrencyManager;
import items.Item;
import items.ItemManager;
import requests.TradeRequest;
import requests.TradeRequestManager;
import requests.typeOneRequest;
import requests.typeTwoRequest;
import system.ReadWrite;
import transactions.TransactionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *Display in Admin Main Menu, Allow Admin to undo actions of all users.
 */

public class UndoAction implements adminMainMenuOptions {
    /**
     *
     * @param admin admin who is currently in the system
     * @param allAdmins AdminManager which holds all the information about Admins, system thresholds and FrozenRequests
     * @param allUsers UserManager which stores all the Users in the system
     * @param allItems ItemManager which stores the system's inventory
     * @param allUserMessages UserMassageManager that stores all user massages
     * @param allTransactions TransactionManager which stores and edits all Transactions in the system
     * @param allRequests TradeRequestManager which store all tradeRequests
     * @param allCurrency
     * @return depend on admin's input
     * return "back" so that admin can go back to the main menu for other options.
     * return null if there is no actions to undo, or values of users not match, or admins finished the undo actions
     *
     */
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages, TransactionManager allTransactions,
                          TradeRequestManager allRequests, CurrencyManager allCurrency) {
        ReadWrite readFile = new ReadWrite();
        List<String> logList;
        try {
            logList = readFile.readLog("UndoLog.txt");
        } catch (IOException e) {
            System.out.println("An error has occurred. Something is wrong with 'UndoLog.txt'");
            return "back";
        }

        if (logList.size() == 0) {
            System.out.println("Nothing has happened in the system :( You will be redirected to the main menu.");
            return "back";
        }

        System.out.println("\nHere is the log of the system: ");
        for (int i = 0; i < logList.size(); i++) {
            System.out.println((i + 1) + ". " + logList.get(i));
        }
        Scanner sc = new Scanner(System.in);

        System.out.println("If you would like to undo an action, enter the number beside the log." +
                " If you would like to go back to the main menu, enter 'back'.");

        Object input = sc.nextLine();
        if (input.equals("back")) {
            return "back";
        }

        try {
            //will try to turn the input into an integer
            //if input is not an integer, returns null and recalls execute()
            input = Integer.parseInt((String) input);
        } catch (NumberFormatException e) {
            System.out.println("That is not a valid option, please try again!");
            return null;
        }

        String chosenLog = logList.get((int) input - 1);
        String[] brokenUp = chosenLog.split(" ");

        if (brokenUp[brokenUp.length - 1].equals("wishlist.\n")) {
            return undoWishlist(allUsers, chosenLog);
        } else if (brokenUp[0].equals("transactions.Transaction")) {
            return undoTransaction(allUsers, allTransactions, allCurrency, chosenLog);
        } else if (brokenUp[3].equals("trade") && (brokenUp[4].equals("request:") || brokenUp[4].equals("request"))) {
            return undoTradeRequest(allUsers, allRequests, chosenLog);
        }
        return "back";
    }

    public Object undoTradeRequest(UserManager allUsers, TradeRequestManager allRequests, String chosenLog) {
        String[] brokenUp = chosenLog.split(" ");
        if (brokenUp[2].equals("Two-way")) { //if the trade request is two-way
            return undoTwoWay(allUsers, allRequests, chosenLog);
        } else if (brokenUp[2].equals("One-way")) { //if the trade request is three way
            return undoOneWay(allUsers, allRequests, chosenLog);
        }
        return null;
    }

    public Object undoOneWay(UserManager allUsers, TradeRequestManager allRequests, String chosenLog) {
        String[] parseNames = chosenLog.split("'");
        String[] categories = chosenLog.split("\n");
        User user1 = null;
        User user2 = null;
        String itemName = parseNames[5];
        boolean temp = false;
        boolean virtual = true;
        TradeRequest request = null;
        String msg = categories[5].split(": ")[1];

        if (categories[3].split(": ")[1].equals("true; ")) {
            temp = true;
        }
        if (categories[4].split(": ")[1].equals("true; ")) {
            virtual = false;
        }
        //finds the accounts.users by comparing the name in the log to the Usernames in allUsers
        for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
            if (allUsers.getAllUsers().get(i).getName().equals(parseNames[1])) {
                user1 = allUsers.getAllUsers().get(i);
            }
            if (allUsers.getAllUsers().get(i).getName().equals(parseNames[3])) {
                user2 = allUsers.getAllUsers().get(i);
            }
        }
        if (user1 == null) {
            System.out.println("accounts.users.User no longer exists or has changed their name. No actions can be undone.");
            return null;
        } if (user2 == null) {
            System.out.println("accounts.users.User no longer exists or has changed their name. No actions can be undone.");
            return null;
        }

        //parses through all outbound requests in user1's list
        //user1 is the user that initiated the trade
        for (int j = 0; j < user1.getOutboundRequests().size(); j++) {
            if (user1.getOutboundRequests().get(j) instanceof typeOneRequest) {
                //if all the variables match, the request will be saved in variable request
                if (((typeOneRequest) user1.getOutboundRequests().get(j)).getFirstUser().equals(user1) &&
                        ((typeOneRequest) user1.getOutboundRequests().get(j)).getSecondUser().getName().equals(user2.getName()) &&
                        ((typeOneRequest) user1.getOutboundRequests().get(j)).getItem().getName().equals(itemName) &&
                        user1.getOutboundRequests().get(j).getTemp() == temp &&
                        user1.getOutboundRequests().get(j).getVirtual() == virtual &&
                        user1.getOutboundRequests().get(j).getMessage().equals(msg)) {
                    request = user1.getOutboundRequests().get(j);
                }
            }
        } if (request == null) { //if the trade request is not found in the user's outbound request list, prints error msg
            System.out.println("Trade request cannot be found. No actions can be undone.");
            return null;
        }
        //otherwise, cancels the trade request and notifies the accounts.users involved
        allRequests.updateRequestStatus(allUsers, request, 1);
        String string = "Your one-way trade request for <" + user2.getName() + ">'s item <" + itemName + "> has been cancelled by accounts.admins.Admin!";
        String string2 = "A one-way trade request for your item <" + itemName + "> from accounts.users.User <" + user1.getName() + "> has been cancelled by accounts.admins.Admin!";
        allUsers.addToNotifyUndo(user1, string);
        allUsers.addToNotifyUndo(user2, string2);
        System.out.println("Trade request has been successfully cancelled!");
        return null;
    }

    public Object undoTwoWay(UserManager allUsers, TradeRequestManager allRequests, String chosenLog) {
        String[] parseNames = chosenLog.split("'");
        String[] categories = chosenLog.split("\n");
        User user1 = null;
        User user2 = null;
        String itemName1 = parseNames[3];
        String itemName2 = parseNames[8];
        boolean temp = false;
        boolean virtual = true;
        TradeRequest request = null;

        String msg = categories[5].split(": ")[1];
        if (categories[3].split(": ")[1].equals("true; ")) {
            temp = true;
        }
        if (categories[4].split(": ")[1].equals("true; ")) {
            virtual = false;
        }

        for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
            if (allUsers.getAllUsers().get(i).getName().equals(parseNames[1])) {
                user1 = allUsers.getAllUsers().get(i);
            } else if (allUsers.getAllUsers().get(i).getName().equals(parseNames[5])) {
                user2 = allUsers.getAllUsers().get(i);
            }
        }
        //if user doesn't exist in allUsers, prints error message
        if (user1 == null) {
            System.out.println("accounts.users.User no longer exists or has changed their name. No actions can be undone.");
            return null;
        } if (user2 == null) {
            System.out.println("accounts.users.User no longer exists or has changed their name. No actions can be undone.");
            return null;
        }

        //parses through all outbound requests in user1's list
        //user1 is the user that initiated the trade
        for (int j = 0; j < user1.getOutboundRequests().size(); j++) {
            if (user1.getOutboundRequests().get(j) instanceof typeTwoRequest) {
                //if all the variables match, the request will be saved in variable request
                if (((typeTwoRequest) user1.getOutboundRequests().get(j)).getFirstUser().equals(user1) &&
                        ((typeTwoRequest) user1.getOutboundRequests().get(j)).getSecondUser().getName().equals(user2.getName()) &&
                        ((typeTwoRequest) user1.getOutboundRequests().get(j)).getFirstItem().getName().equals(itemName1) &&
                        ((typeTwoRequest) user1.getOutboundRequests().get(j)).getSecondItem().getName().equals(itemName2) &&
                        user1.getOutboundRequests().get(j).getTemp() == temp &&
                        user1.getOutboundRequests().get(j).getVirtual() == virtual &&
                        user1.getOutboundRequests().get(j).getMessage().equals(msg)) {
                    request = user1.getOutboundRequests().get(j);
                }
            }
        } if (request == null) { //if the trade request is not found in the user's outbound request list, prints error msg
            System.out.println("Trade request cannot be found. No actions can be undone.");
            return null;
        }
        //otherwise, cancels the trade request and notifies the accounts.users involved
        allRequests.updateRequestStatus(allUsers, request, 1);
        String string = "Your two-way trade request for <" + user2.getName() + ">'s item <" + itemName2 + "> has been cancelled by accounts.admins.Admin!";
        String string2 = "A two-way trade request for your item <" + itemName2 + "> from accounts.users.User <" + user1.getName() + "> has been cancelled by accounts.admins.Admin!";
        allUsers.addToNotifyUndo(user1, string);
        allUsers.addToNotifyUndo(user2, string2);
        System.out.println("Trade request has been successfully cancelled!");
        return null;
        }


    public Object undoTransaction(UserManager allUsers, TransactionManager allTransactions,
                                  CurrencyManager allCurrency, String chosenLog) {
    String[] stringSplit = chosenLog.split("; \n");
    List<String> attributes = new ArrayList<>();
    for (int i = 0; i < stringSplit.length; i++) {
        String[] temp = stringSplit[i].split(" ");
        attributes.add(temp[1]);
    }
    //attributes is now list formatted as follows:
        // [Status, is temporary?, is in-person?, initial meeting, return meeting]
        return null;
    }

    public Object undoWishlist(UserManager allUsers, String chosenLog) {
        System.out.println(chosenLog.split(" ")[2]);
        User temp = new User(chosenLog.split(" ")[2], "1");
        User user = allUsers.getUser(temp);
        if (user == null) {
            System.out.println("This accounts.users.User no longer exists.");
            return null;
        }
        String itemName = chosenLog.split("\"")[1];
        String description = chosenLog.split("\"")[3];
        Item item = user.findInWishlist(itemName, description);
        if (item == null) {
            System.out.println("items.Item has already been undone by either another accounts.admins.Admin or the accounts.users.User themselves!");
            return null;
        }
        allUsers.removeFromWishlist(user,item);
        System.out.println("You have successfully removed " + itemName + " from accounts.users.User " + user.getName() + "'s wishlist!");
        String string = itemName + " has been removed from your wishlist by accounts.admins.Admin!";
        allUsers.addToNotifyUndo(user, string);
        return null;
    }



}