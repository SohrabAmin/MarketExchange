package system_menus.user_main_menus.options;

import java.util.List;
import java.util.logging.Logger;
import transactions.*;
import meetings.*;
import items.*;
import accounts.users.*;
import accounts.admins.*;
import requests.*;
import currency.*;

public class PrintTop3TradingPartners implements UserMainMenuOptions {

    /**
     * Prints out the accounts.users.User user's top 3 trading partners.
     *
     * @param user the accounts.users.User who is requesting to see their top 3 trading partners
     * @param currencyManager
     * @return null if the current menu is to be reprinted; accounts.users.User user if the user is to be redirected to the main menu;
     * String "exit" if the user is to be logged out.
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        List<User> top3TP;
        top3TP = user.getTopTradingPartners();
        System.out.print("Here are your top 3 most frequent trading partners:\n");
        if (top3TP.size() == 0) {
            System.out.print("\uD83D\uDE25 Sorry! You have no trading partners! You should do some trades!\n");
        }
        for (int i = 0; i < top3TP.size(); i++) {
            System.out.print("\uD83D\uDC51" + (i + 1) + ". " + top3TP.get(i).getName() + "\n");

        }
        return user;
    }

}
