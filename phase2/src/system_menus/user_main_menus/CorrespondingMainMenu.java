package system_menus.user_main_menus;

import accounts.admins.AdminManager;
import currency.CurrencyManager;
import items.ItemManager;
import meetings.MeetingManager;
import requests.TradeRequestManager;
import system_menus.user_main_menus.DifferentUserMainMenu;
import accounts.users.User;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import transactions.TransactionManager;

import java.util.logging.Logger;

public class CorrespondingMainMenu {
    public DifferentUserMainMenu correctMenu;

    /**
     * Stores the menu to be run depending on the type of accounts.users.User.
     *
     * @param menu the menu that needs to be run.
     */
    public void setCorrectMenu(DifferentUserMainMenu menu){
        this.correctMenu = menu;
    }

    /**
     * Calls and runs the correct main menu depending if the user is a regular user, frozen user, pseudo frozen user,
     * vacationing user or demo user.
     *
     * @param user The accounts.users.User currently logged into the system
     * @param allAdmins accounts.admins.AdminManager which holds all the information about Admins, system thresholds and FrozenRequests
     * @param allItems items.ItemManager which stores the system's inventory
     * @param allMeetings meetings.MeetingManager which deals with creating and editing meetings
     * @param allTradeRequests requests.TradeRequestManager which stores and edits all the TradeRequests in the system
     * @param allTransactions transactions.TransactionManager which stores and edits all Transactions in the system
     * @param allUsers accounts.users.UserManager which stores all the Users in the system
     * @param undoLogger Logger that logs actions in the system
     * @param allUserMessages stores all the accounts.users.User messages to accounts.admins.Admin
     * @return depending on what the accounts.users.User inputs it will return different objects:
     * returns accounts.users.User to system.TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another accounts.users.User to log in
     * returns String "exit" to tell system.TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object runMenu(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        return correctMenu.mainMenu(user, allItems, allTradeRequests, allUsers, allMeetings, allTransactions, allAdmins, undoLogger, allUserMessages, currencyManager);
    }
}