package system_menus.admin_main_menus.options;

import accounts.admins.Admin;
import accounts.admins.AdminManager;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import currency.CurrencyManager;
import items.ItemManager;
import requests.TradeRequestManager;
import transactions.TransactionManager;
import java.util.logging.Logger;

public interface AdminMainMenuOptions {
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages, TransactionManager allTransactions,
                          TradeRequestManager allRequests, CurrencyManager allCurrency, Logger undoLogger);
}
