package system_menus.user_main_menus;

import accounts.admins.AdminManager;
import accounts.users.User;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import currency.CurrencyManager;
import items.ItemManager;
import meetings.MeetingManager;
import requests.TradeRequestManager;
import system.NotifyUserOfAdminUndo;
import system.NotifyUserOfVIPStatusChange;
import system_menus.ChosenOption;
import system_menus.user_main_menus.options.*;
import transactions.TransactionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class NormalUserMainMenu implements DifferentUserMainMenu {


    /**
     * Displays the main menu for a normal, unfrozen user and prompts user for input depending on what
     * they want to do.
     * <p>
     * Unfrozen Users are able to do the following:
     * View wishlist, view inventory, browse items, initiate trade, view messages, approve pending trades, add
     * items to inventory, view recent trades, view most frequent trading partners, view item statuses, add items to
     * wishlists, view approved trades, approve meetings, confirm meetings, and log out.
     *
     * @param user             the user that is currently logged in to the system
     * @param allItems         items.ItemManager that stores the system's inventory
     * @param allTradeRequests requests.TradeRequestManager that stores all the Trade Requests in the system
     * @param allUsers         UserManager that stores all the Users in the system
     * @param allMeetings      meetings.MeetingManager that deals with the creation of meetings
     * @param allTransactions  transactions.TransactionManager that stores all the Transactions in the system
     * @param allUserMessages  UserMessageManager which stores all the Users messages to Admin
     * @return depending on what the User inputs it will return different objects:
     * returns User to system.TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another User to log in
     * returns String "exit" to tell system.TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object mainMenu(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                           UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                           AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        if (user.getIsVIP()) {
            System.out.print("----------------------------------------------------------------------------------------------" +
                    "\n\uD83D\uDC4B Welcome back, \u2B50VIP\u2B50 " + user.getName() + "!\n");
        } else {
            System.out.print("----------------------------------------------------------------------------------------------" +
                    "\n\uD83D\uDC4B Welcome back, " + user.getName() + "!\n");
        } if (allUsers.getUser(user).getPendingRequests().size() > 0) {
            System.out.print("\uD83D\uDCE9 You have " + allUsers.getUser(user).getPendingRequests().size() +
                    " Pending Trade Requests!\n");
        } if (allUsers.getUser(user).getPendingTrades().size() > 0) {
            System.out.print("\u23F3 You have " + allUsers.getUser(user).getPendingTrades().size() +
                    " Pending Transactions!\n");
        } if (allUsers.getUser(user).getAdminMessages().size() > 0) {
            System.out.println("You have " + allUsers.getUser(user).getAdminMessages().size() + " messages from Admin!");
        }

        // if admin has undone any actions on user's account, a String will be printed when user logs in
        NotifyUserOfAdminUndo notifyActions = new NotifyUserOfAdminUndo();
        notifyActions.notify(user, allUsers);

        // if admin has changed user's VIP status, a String will be printed when user logs in
        NotifyUserOfVIPStatusChange notification = new NotifyUserOfVIPStatusChange();
        notification.notify(user, allUsers);

        System.out.print("Please select number from the following:\n" +
                "1. View and edit Wishlist\n" +
                "2. View Inventory\n" +
                "3. Browse Items\n" +
                "4. Initiate Trade\n" +
                "5. Manage Payment Options\n" +
                "6. Approve Pending Trade Requests\n" +
                "7. Add Item to inventory\n" +
                "8. View most recent trades\n" +
                "9. View most frequent trading partners\n" +
                "10. View status of my items\n" +
                "11. Approve meetings\n" +
                "12. Confirm trade is done from your side\n" +
                "13. View status of outbound requests\n" +
                "14. Message Admin and view replies\n" +
                "15. Change Account Settings\n" +
                "16. Go on vacation\n" +
                "17. Check my points\n" +
                "18. Logout\n" +
                "Enter 'exit' to exit the system at any time.\n");


        ChosenOption option = new ChosenOption(); //stores, sets and runs the menu option that the user has chosen
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String input = br.readLine();
            if (!input.equals("exit")) {
                //depending on their input, the correct "strategy" will be created and stored in the system_options.ChosenOption class
                switch (input) {
                    case "1":  //view and edit wishlist
                        option.setChosenOption(new WishlistManager());
                        break;
                    case "2":  //view inventory
                        option.setChosenOption(new InventoryManager());
                        break;
                    case "3":  //browse
                        option.setChosenOption(new Browse());
                        break;
                    case "4":  //initiate trade
                        option.setChosenOption(new TradeInitiator());
                        break;
                    case "5":  //view pending trade requests
                        option.setChosenOption(new ManagePaymentOptions());
                        break;
                    case "6":  //approve trade reqs
                        option.setChosenOption(new ApproveTrade());
                        break;
                    case "7":  //add item to inventory
                        option.setChosenOption(new AddItemToSystem());
                        break;
                    case "8":  //view recent trades
                        option.setChosenOption(new PrintMostRecentTrades());
                        break;
                    case "9":  //view most freq trading partners
                        option.setChosenOption(new PrintTop3TradingPartners());
                        break;
                    case "10":   //view item status
                        option.setChosenOption(new PrintItemHistory());
                        break;
                    case "11":  //approve meeting
                        option.setChosenOption(new PendingTransactionProcess());
                        break;
                    case "12":  //confirm meeting for approved trades
                        option.setChosenOption(new ConfirmMeetings());
                        break;
                    case "13":  //view outbound req status
                        option.setChosenOption(new PrintOutboundRequests());
                        break;
                    case "14":  //message admin
                        option.setChosenOption(new UserMessage());
                        break;
                    case "15":  //change account settings
                        option.setChosenOption(new AccountSettingsManager());
                        break;
                    case "16":  //go on vacation
                        option.setChosenOption(new VacationPrompter());
                        break;
                    case "17":  //points
                        option.setChosenOption(new PointsManager());
                        break;
                    case "18":  //logout
                        return null;
                    default:  //returns to main menu
                        System.out.println("That is not a valid option. Please try again.");
                        return user;
                }
                //the option that is chosen by the user will be run
                Object result = option.executeOption(user, allItems, allTradeRequests, allUsers, allMeetings,
                        allTransactions, allAdmins, undoLogger, allUserMessages, currencyManager);
                //if the execute() method of the option returns null, the option will be run again until the user
                //specifies that they want to return to the main menu
                while (result == null) {
                    result = option.executeOption(user, allItems, allTradeRequests, allUsers, allMeetings,
                            allTransactions, allAdmins, undoLogger, allUserMessages, currencyManager);
                }

                // this check is used to log user out when they confirm that they are going on vacation
                if (result.equals("leave")) {
                    return null;
                }
                //tells system.TradeSystem() to stay logged in to this user's account; helps with looping the main menu
                return user;
            }
            //tells system.TradeSystem() to log out and bring the user back to the login screen
            return input;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            return user;
        }
    }

}
