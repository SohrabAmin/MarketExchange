package system_menus.user_main_menus.options;

import java.util.Map;
import java.util.logging.Logger;
import transactions.*;
import meetings.*;
import items.*;
import accounts.users.*;
import accounts.admins.*;
import requests.*;
import currency.*;

public class PrintItemHistory implements UserMainMenuOptions {

    /**
     * Allows the accounts.users.User to view their item history and it's status. It will display whether it is
     * "Pending", "Approved", or "Rejected".
     *  @param user     the accounts.users.User who is requesting to see their item history
     * @param allUsers accounts.users.UserManager that stores all the Users in the system
     * @param currencyManager
     * @return null if the current menu is to be reprinted; accounts.users.User user if the user is to be redirected to the main menu;
     * String "exit" if the user is to be logged out.
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        if (allUsers.getUser(user).getItemHistory().size() == 0) {
            System.out.println("\uD83D\uDE25 No items here! Please add an item to your inventory!");
            return user;

        }
        for (Map.Entry m : allUsers.getUser(user).getItemHistory().entrySet()) {
            Item object = (Item) m.getKey();
            System.out.println(object.getName() + ": " + m.getValue());
        }
        return user;
    }

}
