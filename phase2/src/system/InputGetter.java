package system;

import accounts.admins.AdminManager;
import currency.CurrencyManager;
import items.ItemManager;
import meetings.MeetingManager;
import requests.TradeRequestManager;
import system_menus.user_main_menus.*;
import accounts.users.User;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import transactions.TransactionManager;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//Note: how to put emojis in the code was found here: http://dplatz.de/blog/2019/emojis-for-java-commandline.html
public class InputGetter {
    private static final Logger undoLogger = Logger.getLogger(AdminInputGetter.class.getName());
    private File undoLog = new File("UndoLog.txt");
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("UndoLog.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to initialize FileHandler.");
        }
    }

    public InputGetter() {
        undoLogger.setLevel(Level.ALL);
        fileHandler.setLevel(Level.ALL);
        undoLogger.addHandler(fileHandler);

        // Prevent fileHandler from printing to the console:
        undoLogger.setUseParentHandlers(false);
        // Credit for the above line goes to
        // https://stackoverflow.com/questions/2533227/how-can-i-disable-the-default-console-handler-while-using-the-java-logging-api
    }

    /**
     * Displays the main menu, and prompts user for input depending on what they want to do.
     * <p>
     * Unfrozen Users are able to do the following:
     * View wishlist, view inventory, browse items, initiate trade, view messages, approve pending trades, add
     * items to inventory, view recent trades, view most frequent trading partners, view item statuses, add items to
     * wishlists, view approved trades, approve meetings, confirm meetings, and log out.
     * <p>
     * Frozen accounts.users are able to do the following:
     * View wishlist, view inventory, browse items, add items to inventory, view most recent trades, view most
     * frequent trading partners, view item statuses, add items to wishlist, request unfreeze and log out.
     *
     * @param user             the user that is currently logged in to the system
     * @param allItems         items.ItemManager that stores the system's inventory
     * @param allTradeRequests requests.TradeRequestManager that stores all the Trade Requests in the system
     * @param allUsers         accounts.users.UserManager that stores all the Users in the system
     * @param allMeetings      meetings.MeetingManager that deals with the creation of meetings
     * @param allTransactions  transactions.TransactionManager that stores all the Transactions in the system
     * @param allUserMessages  accounts.users.UserMessageManager which stores all the Users messages to accounts.admins.Admin
     * @param allAdmins        accounts.admins.AdminManager which stores all the accounts.admins in the system
     * @return depending on what the accounts.users.User inputs it will return different objects:
     * returns accounts.users.User to system.TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another accounts.users.User to log in
     * returns String "exit" to tell system.TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object callMainMenu(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                               UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                               AdminManager allAdmins, UserMessageManager allUserMessages, CurrencyManager allCurrency) {
        //A frozen account is one where you can log in and look for items, but you cannot arrange any transactions.
        // A user who has been frozen can request that the administrative user unfreezes their account.
        boolean frozenAccount = user.getIsFrozen();
        boolean pseudoFrozenAccount = user.getIsPseudoFrozen();
        CorrespondingMainMenu menu = new CorrespondingMainMenu();

        if (frozenAccount || pseudoFrozenAccount) {//if they are frozen-- first off, tell them that they are frozen
            menu.setCorrectMenu(new FrozenUserMainMenu());
        } else if (user.getName().equals("Demo")) {//if they are a demo user without an account
            menu.setCorrectMenu(new DemoUserMainMenu());
        } else if (user.getIsOnVacation()) {// if they are on vacation
            menu.setCorrectMenu(new VacationUserMainMenu());
        }
        // the following three lines are commented out because VIP Users are currently shown the same menu as Users
        //else if (user.getIsVIP()) {// if they are a VIP
        //    menu.setCorrectMenu(new VIPUserMainMenu());
        //}
        else {
            menu.setCorrectMenu(new NormalUserMainMenu());
        }
        return menu.runMenu(user, allItems, allTradeRequests, allUsers, allMeetings, allTransactions, allAdmins, undoLogger, allUserMessages, allCurrency);
    }
}