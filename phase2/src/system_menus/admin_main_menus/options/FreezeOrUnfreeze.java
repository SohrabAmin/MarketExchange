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

public class FreezeOrUnfreeze implements adminMainMenuOptions {

    /**
     * Allows admin to either view unfreeze requests, freeze and unfreeze accounts.users in the system.
     *
     * @param allUsers accounts.users.UserManager which stores all the accounts.users in the system
     * @param admin the current accounts.admins.Admin logged into the system
     * @param allAdmins accounts.admins.AdminManager which holds all the Admins, FrozenRequests and Thresholds in the system
     * @param allItems items.ItemManager which holds the system inventory
     * @param allUserMessages accounts.users.UserMessageManager which stores all the Users messages to accounts.admins.Admin
     * @return depending on what the accounts.admins.Admin inputs it will return different objects:
     * returns null to tell mainmenu() to call execute() again
     * returns String "back" to tell mainmenu() to prompt main menu again so accounts.admins.Admin can choose another
     * main menu option
     */
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages, TransactionManager allTransactions,
                          TradeRequestManager allRequests, CurrencyManager allCurrency) {
        System.out.println("What would you like to do? Please select the number beside the option or enter " +
                "'back' to return to the main menu.");
        System.out.println("1.View unfreeze requests\n2.Unfreeze frozen accounts.users\n3.Freeze accounts.users");
        Scanner sc = new Scanner(System.in);
        Object input = sc.nextLine();
        if (input.equals("back")) {
            return "back";
        } else if (input.equals("1")) {
            Object temp = ViewUnfreezeRequests(allUsers, allAdmins);
            while (temp == null) {
                temp = ViewUnfreezeRequests(allUsers, allAdmins);
            } //else input was "back", displays Freeze or Unfreeze screen
            return null;
        } else if (input.equals("2")) {
            Object temp = Unfreeze(allUsers, allAdmins);
            while (temp == null) {
                temp = Unfreeze(allUsers, allAdmins);
            } //else input was "back", displays Freeze or Unfreeze screen
            return null;
        } else if (input.equals("3")) {
            Object temp = Freeze(allUsers, allAdmins);
            while (temp == null) {
                temp = Freeze(allUsers, allAdmins);
            } //else input was "back", displays Freeze or Unfreeze screen
            return null;
        }
        System.out.println("Invalid input. Please try again!");
        return null;
    }

    /**
     * Allows admin to view unfreeze requests they have and allows them to unfreeze any of those Users if they so choose.
     * If they unfreeze a accounts.users.User, it will remove their request from frozenRequests and change their status isPseudoFrozen and
     * isFrozen to false.
     *
     * @param allUsers accounts.users.UserManager which stores all the accounts.users in the system
     * @param allAdmins Adminmanager which stores the FrozenRequests
     * @return depending on what the accounts.admins.Admin inputs it will return different objects:
     * returns null to tell mainmenu() to call system_options.admin_main_menus.options.FreezeOrUnfreeze() again
     * returns String "back" to tell mainmenu() to prompt main menu again so accounts.admins.Admin can choose another
     * main menu option
     */
    public Object ViewUnfreezeRequests(UserManager allUsers, AdminManager allAdmins) {
        if (allAdmins.getFrozenRequests().size() == 0) {
            System.out.println("\nYou have no unfreeze requests!\n");
            return "back";
        }
        System.out.println("\nHere are freeze requests:");
        for (int i = 0; i < allAdmins.getFrozenRequests().size(); i++) {
            System.out.println((i + 1) + " . " + allAdmins.getFrozenRequests().get(i).getName());
        }
        System.out.println("Please enter the number of the user you would like to unfreeze or 'back' to go back.");
        Scanner sc = new Scanner(System.in);
        Object line = sc.nextLine();
        if (line.equals("back")) {
            return "back";
        } else {
            try {
                line = Integer.parseInt((String) line);
            } catch (NumberFormatException e) {
                return null;
            }
            allUsers.unfreeze(allAdmins.getFrozenRequests().get((Integer) line - 1));
            allUsers.unPseudoFreeze(allAdmins.getFrozenRequests().get((Integer) line - 1));
            System.out.println("\u2705 Successfully unfrozen user: " +
                    allUsers.getUser(allAdmins.getFrozenRequests().get((Integer) line - 1)).getName());
            allAdmins.removeFromFrozenRequest(allAdmins.getFrozenRequests().get((Integer) line - 1));
            return null; //brings them back to the list of unfreeze requests
        }
    }

    /**
     * Displays a list of all frozen and pseudo frozen Users in accounts.users.UserManager and prompts accounts.admins.Admin to input
     * which accounts.users.User they wish to unfreeze and unfreezes those Users
     *
     * @param allUsers accounts.users.UserManager which stores all the Users in the system
     * @param allAdmins accounts.admins.AdminManager which stores the FrozenRequests
     * @return depending on what the accounts.admins.Admin inputs it will return different objects:
     * returns null to tell system_options.admin_main_menus.options.FreezeOrUnfreeze() to call Unfreeze() again
     * returns String "back" to tell system_options.admin_main_menus.options.FreezeOrUnfreeze() to prompt main menu again so accounts.admins.Admin can choose another
     * main menu option
     */
    public Object Unfreeze(UserManager allUsers, AdminManager allAdmins) {
        List<User> frozenUsers = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
            if (allUsers.getAllUsers().get(i).getIsFrozen() || allUsers.getAllUsers().get(i).getIsPseudoFrozen()) {
                //if getIsFrozen returns true for frozen accounts
                // or if getIsPseudoFrozen returns true for pseudo frozen accounts.users
                frozenUsers.add(allUsers.getAllUsers().get(i));
            }
        }
        if (frozenUsers.size() == 0) {
            System.out.println("\nThere are no frozen accounts.users!\n");
            return "back";
        }
        System.out.println("\nHere are the current frozen accounts.users:");
        for (int i = 0; i < frozenUsers.size(); i++) {
            String reqUnfreeze = "";
            for (User frozenRequest : allAdmins.getFrozenRequests()) {
                if (frozenRequest.getName().equals(frozenUsers.get(i).getName())) {
                    reqUnfreeze = "[REQUESTING UNFREEZE]";
                }
            }
            System.out.println((i + 1) + ". " + frozenUsers.get(i).getName() + " " + reqUnfreeze);
        }
        System.out.println("Please enter the number of the user you would like to unfreeze or 'back' to go back.");
        Scanner sc = new Scanner(System.in);
        Object line = sc.nextLine();
        if (line.equals("back")) {
            return "back";
        } else {
            try {
                line = Integer.parseInt((String) line);
            } catch (NumberFormatException e) {
                return null;
            }
            User chosenUser = frozenUsers.get((Integer) line - 1);
            allUsers.unPseudoFreeze(chosenUser);
            allUsers.unfreeze(chosenUser);
            //if they requested to be unfrozen, they will be removed from the frozen request list
            for (int i = 0; i < allAdmins.getFrozenRequests().size(); i++) {
                if (allAdmins.getFrozenRequests().get(i).getName().equals(chosenUser.getName())) {
                    allAdmins.removeFromFrozenRequest(chosenUser);
                }
            }
            System.out.println("\n\u2705 Successfully unfrozen user: " +
                    allUsers.getUser(chosenUser).getName());
            if ((frozenUsers.size() - 1) == 0) {
                System.out.println("\nThere are no more frozen Users!\n");
                return "back";
            }
            return null;
        }
    }

    /**
     * Displays a list of all unfrozen and pseudo frozen Users in accounts.users.UserManager and prompts accounts.admins.Admin to input
     * which accounts.users.User they wish to freeze and freezes those Users
     *
     * @param allUsers accounts.users.UserManager which stores all the Users in the system
     * @param allAdmins accounts.admins.AdminManager which stores the FrozenRequests
     * @return depending on what the accounts.admins.Admin inputs it will return different objects:
     * returns null to tell system_options.admin_main_menus.options.FreezeOrUnfreeze() to call Freeze() again
     * returns String "back" to tell system_options.admin_main_menus.options.FreezeOrUnfreeze() to prompt main menu again so accounts.admins.Admin can choose another
     * main menu option
     */
    public Object Freeze(UserManager allUsers, AdminManager allAdmins) {
        List<User> unfrozenUsers = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
            if (!allUsers.getAllUsers().get(i).getIsFrozen() || allUsers.getAllUsers().get(i).getIsPseudoFrozen()) {
                //if getIsFrozen is not true or if getIsPseudoFrozen returns true for pseudo frozen accounts.users
                unfrozenUsers.add(allUsers.getAllUsers().get(i));
            }
        }
        if (unfrozenUsers.size() == 0) {
            System.out.println("\nThere are no non-frozen accounts.users!\n");
            return "back";
        }
        System.out.println("\nHere are the current non-frozen accounts.users:");
        for (int i = 0; i < unfrozenUsers.size(); i++) {
            String reqUnfreeze = "";
            for (User frozenRequest : allAdmins.getFrozenRequests()) {
                if (frozenRequest.getName().equals(unfrozenUsers.get(i).getName())) {
                    reqUnfreeze = "[REQUESTING UNFREEZE]";
                }
            }
            System.out.println((i + 1) + ". " + unfrozenUsers.get(i).getName() + " " + reqUnfreeze);
        }
        System.out.println("\nPlease enter the number of the user you would like to freeze or 'back' to go back.");
        Scanner sc = new Scanner(System.in);
        Object line = sc.nextLine();
        if (line.equals("back")) {
            return "back";
        } else {
            try {
                line = Integer.parseInt((String) line);
            } catch (NumberFormatException e) {
                return null;
            }
            User chosenUser = unfrozenUsers.get((Integer) line - 1);
            if (chosenUser.getIsPseudoFrozen()) { //user is pseudo frozen
                allUsers.unPseudoFreeze(chosenUser);
            }
            allUsers.freeze(chosenUser);
            //accounts.users will need to re-request to be unfrozen if they go from pseudo-frozen to actually frozen
            for (int i = 0; i < allAdmins.getFrozenRequests().size(); i++) {
                if (allAdmins.getFrozenRequests().get(i).getName().equals(chosenUser.getName())) {
                    allAdmins.removeFromFrozenRequest(chosenUser);
                }
            }
            System.out.println("\n\u2705 Successfully frozen user: " +
                    allUsers.getUser(chosenUser).getName());
            if ((unfrozenUsers.size() - 1) == 0) {
                System.out.println("\nThere are no more non-frozen Users!\n");
                return "back";
            }
            return null;
        }
    }

}
