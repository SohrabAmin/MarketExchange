package system_menus.user_main_menus.options;

import accounts.admins.AdminManager;
import accounts.users.User;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import currency.CurrencyManager;
import items.ItemManager;
import meetings.MeetingManager;
import requests.TradeRequestManager;
import transactions.TransactionManager;

import java.util.logging.Logger;

public class NotifyAdminOfUnfreezeRequest implements UserMainMenuOptions {

    /**
     * Notifies the Admin of a Request to Unfreeze from User user.
     *
     * @param user      frozen user sending the request to admin to be unfrozen
     * @param allAdmins contains the method for adding frozen requests
     * @param currencyManager
     * @return returns User so that they can be redirected to the main menu
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        if (allAdmins.getFrozenRequests().contains(user)) {
            System.out.println("You have already requested to be unfrozen! Please be patient.");
            return user;
        }
        allAdmins.addFrozenRequest(user);
        System.out.print("Your request is successfully submitted!\n");
        return user;
    }

}
