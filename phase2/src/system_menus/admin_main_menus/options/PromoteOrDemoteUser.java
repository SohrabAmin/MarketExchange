package system_menus.admin_main_menus.options;

import accounts.admins.Admin;
import accounts.admins.AdminManager;
import accounts.users.User;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import currency.CurrencyManager;
import items.ItemManager;
import requests.TradeRequestManager;
import transactions.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.logging.Logger;

public class PromoteOrDemoteUser implements AdminMainMenuOptions {

    /**
     * Allows an admin to:
     * promote a user to VIP, or
     * demote a VIP to user.
     *
     * @param admin     accounts.admins.Admin logged in to the system
     * @param allAdmins The instance of accounts.admins.AdminManager
     * @param allUsers  The instance of UserManager
     * @param allItems  The instance of items.ItemManager
     * @return null if the current menu is to be reprinted; accounts.admins.Admin admin if the admin is to be redirected to the main menu.
     */
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages, TransactionManager allTransactions,
                          TradeRequestManager allRequests, CurrencyManager allCurrency, Logger undoLogger) {

        // the following four lines are commented out because currently any admin can promote accounts.users or demote VIPs
        //if (!admin.getIsSuperAdmin()) {
        //    System.out.println("Sorry, but only super accounts.admins can access this menu!");
        //    return admin;
        //}

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 to promote a user to VIP user. Press 2 to demote a VIP user to a user. " +
                "Press 3 to cancel.");
        switch (scanner.nextLine()) {
            case "1":
                List<String> listOfUserNames = new ArrayList<>();
                System.out.println("Type the number beside the name of the user to promote to VIP user.");

                for (User indexedUser : allUsers.getAllUsers()) {
                    // the list of accounts.users that will be displayed to the logged in admin
                    if (!indexedUser.getIsVIP()) {
                        listOfUserNames.add(indexedUser.getName());
                    }
                }

                if (listOfUserNames.isEmpty()) {
                    System.out.println("No non-VIP users found!");
                    return admin;
                } else {
                    for (int i = 0; i < listOfUserNames.size(); i++) {
                        System.out.println((i + 1) + ". " + listOfUserNames.get(i));
                    }

                    Object idOfUserChosenForPromotion = scanner.nextLine();

                    try {
                        //will try to turn the input into an integer
                        //if input is not an integer, returns null and recalls execute()
                        idOfUserChosenForPromotion = Integer.parseInt((String) idOfUserChosenForPromotion);
                    } catch (NumberFormatException e) {
                        System.out.println("That is not a valid option, please try again!");
                        return null;
                    }
                    // first check whether the admin typed in an valid number
                    if (listOfUserNames.size() >= (int) idOfUserChosenForPromotion - 1) {

                        // loop through the list of accounts.users to find the user to promote
                        for (User indexedUser : allUsers.getAllUsers()) {
                            if (indexedUser.getName().equals(listOfUserNames.get((int) idOfUserChosenForPromotion - 1))) {
                                indexedUser.setIsVIP(true);
                                allUsers.addToVIPStatusChangeNotifications(indexedUser,
                                        "An admin promoted you to VIP!");
                                System.out.println("User " + (listOfUserNames.get((int) idOfUserChosenForPromotion - 1)) + " was promoted to VIP!");
                            }
                        }
                        return admin;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                        return null;
                    }
                }

            case "2":
                List<String> listOfVIPNames = new ArrayList<>();
                System.out.println("Type the number beside the name of the VIP to demote to user.");

                for (User indexedUser : allUsers.getAllUsers()) {

                    // the list of VIP accounts.users that will be displayed to the logged in admin
                    if (indexedUser.getIsVIP()) {
                        listOfVIPNames.add(indexedUser.getName());
                    }
                }

                if (listOfVIPNames.isEmpty()) {
                    System.out.println("No VIPs found!");
                    return admin;
                } else {
                    for (int j = 0; j < listOfVIPNames.size(); j++) {
                        System.out.println((j + 1) + ". " + listOfVIPNames.get(j));
                    }

                    Object idOfVIPChosenForDemotion = scanner.nextLine();

                    try {
                        //will try to turn the input into an integer
                        //if input is not an integer, returns null and recalls execute()
                        idOfVIPChosenForDemotion = Integer.parseInt((String) idOfVIPChosenForDemotion);
                    } catch (NumberFormatException e) {
                        System.out.println("That is not a valid option, please try again!");
                        return null;
                    }

                    // first check whether the admin typed in an actual user name
                    if (listOfVIPNames.size() >= ((int) idOfVIPChosenForDemotion - 1)) {

                        // loop through the list of accounts.users to find the VIP user to demote
                        for (User indexedUser : allUsers.getAllUsers()) {
                            if (indexedUser.getName().equals(listOfVIPNames.get((int) idOfVIPChosenForDemotion - 1))) {
                                indexedUser.setIsVIP(false);
                                allUsers.addToVIPStatusChangeNotifications(indexedUser,
                                        "An admin demoted you to user!");
                                System.out.println("VIP " + listOfVIPNames.get((int) idOfVIPChosenForDemotion - 1) + " was demoted to user!");
                            }
                        }
                        return admin;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                        return null;
                    }
                }
            case "3":
                return admin;
        }
        System.out.println("Invalid input. Please try again.");
        return null;
    }
}
