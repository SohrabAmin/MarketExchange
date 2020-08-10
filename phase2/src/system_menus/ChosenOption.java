package system_menus;

import accounts.admins.Admin;
import accounts.admins.AdminManager;
import accounts.users.User;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import currency.CurrencyManager;
import items.ItemManager;
import meetings.MeetingManager;
import requests.TradeRequestManager;
import system_menus.admin_main_menus.options.AdminMainMenuOptions;
import system_menus.user_main_menus.options.UserMainMenuOptions;
import transactions.TransactionManager;

import java.util.logging.Logger;

public class ChosenOption {
    public Object chosenOption;

    /**
     * Stores the main menu option chosen by the accounts.admins.Admin or accounts.users.User.
     *
     * @param option the option chosen by the accounts.admins.Admin or accounts.users.User
     */
    public void setChosenOption(Object option){
        this.chosenOption = option;
    }

    /**
     * Classes that implement this interface are the main menu options for accounts.users.User. All those classes should
     * have the 'execute' method which will execute the particular prompts and actions specific to whatever the
     * menu option is.
     *
     * @param user The accounts.users.User currently logged into the system
     * @param allAdmins accounts.admins.AdminManager which holds all the information about Admins, system thresholds and FrozenRequests
     * @param allItems items.ItemManager which stores the system's inventory
     * @param allMeetings meetings.MeetingManager which deals with creating and editing meetings
     * @param allTradeRequests requests.TradeRequestManager which stores and edits all the TradeRequests in the system
     * @param allTransactions transactions.TransactionManager which stores and edits all Transactions in the system
     * @param allUsers accounts.users.UserManager which stores all the Users in the system
     * @param undoLogger Logger that logs actions in the system
     * @return depending on what the accounts.users.User inputs it will return different objects:
     * returns null to tell mainmenu() to call execute() again
     * returns String "back" to tell mainmenu() to prompt main menu again so accounts.users.User can choose another
     * main menu option
     * returns String "exit" to prompt system.TradeSystem to save all the information and exit the System
     */
    public Object executeOption(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                                UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                                AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager allCurrency) {

        return ((UserMainMenuOptions) chosenOption).execute(user, allItems, allTradeRequests, allUsers,
                allMeetings, allTransactions, allAdmins, undoLogger, allUserMessages, allCurrency );
    }

    /**
     * Calls the method stored in chosenOption and returns an Object.
     *
     * @param
     * admin the current accounts.admins.Admin logged into the system
     * @param allAdmins accounts.admins.AdminManager which holds all the Admins, FrozenRequests and Thresholds in the system
     * @param allUsers accounts.users.UserManager which holds all the Users in the system
     * @param allItems items.ItemManager which holds the system inventory
     * @return depending on what the accounts.admins.Admin inputs it will return different objects:
     * returns null to tell mainmenu() to call system_options.admin_main_menus.options.AddAdmin() again
     * returns String "back" to tell mainmenu() to prompt main menu again so accounts.users.User can choose another
     * main menu option
     * returns String "exit" to prompt system.TradeSystem to save all the information and exit the System
     */
    public Object executeOption(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                                UserMessageManager allUserMessages, TransactionManager allTransactions,
                                TradeRequestManager allRequests, CurrencyManager allCurrency, Logger undoLogger) {
        return ((AdminMainMenuOptions) chosenOption).execute(admin, allAdmins, allUsers, allItems, allUserMessages, allTransactions,
                allRequests, allCurrency, undoLogger);
    }
}
