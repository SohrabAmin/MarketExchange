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
public class PrintUserMessages implements userMainMenuOptions {

    /**
     * Prints the messages that accounts.users.User user has. Messages include Trade Requests and meetings.Meeting requests.
     *  @param user     the accounts.users.User who wishes to view their messages
     * @param allUsers accounts.users.UserManager which stores accounts.users.User user
     * @param currencyManager
     * @return
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        //no error checking needed!
        System.out.print("\nHere are your pending Trades: \n");
        User Person = allUsers.getUser(user);
        if (Person.getPendingRequests().size() == 0) {
            System.out.print("\nYou have no pending requests.\n");
            return user;
        } else {




        }
        return user;
    }

}
