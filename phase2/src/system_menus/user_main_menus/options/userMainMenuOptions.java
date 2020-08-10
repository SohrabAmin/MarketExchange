package system_menus.user_main_menus.options;

import java.util.logging.Logger;
import transactions.*;
import meetings.*;
import items.*;
import accounts.users.*;
import accounts.admins.*;
import requests.*;
import currency.*;
import system_menus.admin_main_menus.options.*;
import system_menus.user_main_menus.options.*;

public interface userMainMenuOptions {
    /**
     * Classes that implement this interface are the main menu options for accounts.users.User. All those classes should
     * have the 'execute' method which will execute the particular prompts and actions specific to whatever the
     * menu option is.
     *
     * @param user The accounts.users.User currently logged into the system
     * @param allItems items.ItemManager which stores the system's inventory
     * @param allTradeRequests requests.TradeRequestManager which stores and edits all the TradeRequests in the system
     * @param allUsers accounts.users.UserManager which stores all the Users in the system
     * @param allMeetings meetings.MeetingManager which deals with creating and editing meetings
     * @param allTransactions transactions.TransactionManager which stores and edits all Transactions in the system
     * @param allAdmins accounts.admins.AdminManager which holds all the information about Admins, system thresholds and FrozenRequests
     * @param undoLogger Logger that logs actions in the system
     * @param currencyManager
     * @return depending on what the accounts.users.User inputs it will return different objects:
     * returns null to tell mainmenu() to call execute() again
     * returns String "back" to tell mainmenu() to prompt main menu again so accounts.users.User can choose another
     * main menu option
     * returns String "exit" to prompt system.TradeSystem to save all the information and exit the System
     */

    Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                   UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                   AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager);
}