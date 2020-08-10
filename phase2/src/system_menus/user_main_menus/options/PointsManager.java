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

import java.util.Scanner;
import java.util.logging.Logger;

public class PointsManager implements UserMainMenuOptions {
    /**
     * Displays the points of the user and allow user to become VIP user if they are eligible.
     *
     * @param user             the User that wants to view their pending transactions
     * @param allItems         ItemManager that stores the system's inventory
     * @param allTradeRequests TradeRequestManager that stores all trade requests
     * @param allUsers         UserManager that stores all the Users in the system
     * @param allMeetings      MeetingManager that deals with the creation of meetings
     * @param allTransactions  TransactionManager that stores all the information of all system transactions
     * @param allAdmins        AdminManager that stores all admins
     * @param allUserMessages  UserMessageManager that stores all user's messages to admin.
     * @param currencyManager  CurrencyManger which manages in-app currency for users
     * @return return null if the user selects to become VIP or if user enters wrong information that is not required by
     * the system; or return user so that user can go back to the main menu
     */
    @Override
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests, UserManager allUsers,
                          MeetingManager allMeetings, TransactionManager allTransactions, AdminManager allAdmins,
                          Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your points: " + user.getPoints());
        if (!(user.getIsVIP()) && user.getPoints() >= 20) {
            System.out.println("Hey " + user.getName() + ", you're eligible to become a \u2B50VIP\u2B50 \nPress 1 " +
                    "to use 20 of your " + user.getPoints() + " points to upgrade to VIP status.");
            System.out.println("Press 2 to go back to main menu.");
            String chosenOption = scanner.nextLine();
            if (chosenOption.equals("1")) {
                user.setPoints(user.getPoints() - 20);
                user.setIsVIP(true);
                System.out.println("Congrats, you're now a \u2B50VIP\u2B50");
                return null;
            } else if (chosenOption.equals("2")) {
                return user;
            } else {
                System.out.println("Invalid input. Please try again.");
                return null;
            }
        } else {
            System.out.println("Press 1 to go back to main menu.");
            if (scanner.nextLine().equals("1")) {
                return user;
            } else {
                System.out.println("Invalid input. Please try again.");
                return null;
            }
        }
    }
}
