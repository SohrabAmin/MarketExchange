package system_menus.user_main_menus;

import accounts.admins.AdminManager;
import accounts.users.User;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import currency.CurrencyManager;
import items.Item;
import items.ItemManager;
import meetings.MeetingManager;
import requests.TradeRequestManager;
import transactions.TransactionManager;

import java.util.Scanner;
import java.util.logging.Logger;

public class VacationUserMainMenu implements DifferentUserMainMenu {

    /**
     * Displays the main menu for a accounts.users.User on vacation. Users on vacation can either stay on vacation or declare they have
     * returned from vacation, in which case their inventory Items are restored and they can participate in the trade
     * system again.
     *
     * @param user             accounts.users.User currently logged into the system
     * @param allItems         The instance of items.ItemManager
     * @param allTradeRequests The instance of requests.TradeRequestManager
     * @param allUsers         The instance of accounts.users.UserManager
     * @param allMeetings      The instance of meetings.MeetingManager
     * @param allTransactions  The instance of transactions.TransactionManager
     * @param allAdmins        The instance of accounts.admins.AdminManager
     * @param undoLogger       Logger that logs actions in the system
     * @param allUserMessages  The instance of accounts.users.UserMessageManager
     * @return null if the current menu is to be reprinted; accounts.users.User user if the user is to be redirected to the main menu;
     * String "exit" if the user is to be logged out.
     */
    public Object mainMenu(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                           UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                           AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + user.getName() + "!\n");
        System.out.println("Are you still on vacation? If so, press 1. If not, press 2.");
        String confirmation = scanner.nextLine();
        if (confirmation.equals("1")) {
            System.out.println("Go enjoy your vacation!");
            return null;
        } else if (confirmation.equals("2")) {
            System.out.println("We hope you enjoyed your vacation!");
            user.setIsOnVacation(false);
            for (int i = 0; i < user.getVacationStorage().size(); i++) {
                Item item = user.getVacationStorage().get(i);
                allUsers.addToInventory(user, item);
                allUsers.removeFromVacationStorage(user, item);
            }
            return user;
        } else {
            System.out.println("Invalid option. Please try again.");
            return null;
        }
    }
}