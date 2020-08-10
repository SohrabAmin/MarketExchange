package system_menus.admin_main_menus.options;

import accounts.admins.Admin;
import accounts.admins.AdminManager;
import accounts.users.UserIterator;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import currency.CurrencyManager;
import items.ItemManager;
import requests.TradeRequestManager;
import transactions.TransactionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AddAdmin implements AdminMainMenuOptions {

    /**
     * Adds a new admin to the list of all Admins in accounts.admins.AdminManager. Only the initial admin should be able to add new
     * accounts.admins.
     *
     * @param admin the current accounts.admins.Admin logged into the system
     * @param allAdmins accounts.admins.AdminManager which holds all the Admins, FrozenRequests and Thresholds in the system
     * @param allUsers accounts.users.UserManager which holds all the Users in the system
     * @param allItems items.ItemManager which holds the system inventory
     * @return depending on what the accounts.admins.Admin inputs it will return different objects:
     * returns null to tell mainmenu() to call execute() again
     * returns String "back" to tell mainmenu() to prompt main menu again so accounts.admins.Admin can choose another
     * main menu option
     * returns String "exit" to prompt system.TradeSystem to save all the information and exit the System
     */
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages, TransactionManager allTransactions,
                          TradeRequestManager allRequests, CurrencyManager allCurrency) {
        if (!admin.getIsSuperAdmin()) {
            System.out.println("Sorry but you do not have the authorization to add new accounts.admins!");
            return "back";
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;

        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Please press 1 to proceed to adding a new accounts.admins.Admin or press 2 to return to " +
                "the accounts.admins.Admin menu." + " Enter 'exit' to exit the system at any time.");
        try {
            String input = br.readLine();
            if (input.equals("exit")) {
                return input;
            }
            if (input.equals("1")) {
                while (!input.equals("exit") && !prompts.usergot) {
                    if (prompts.hasNext()) {
                        System.out.println(prompts.next());
                    }
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        temp.add(input);
                        curr++;
                        if (curr == 2)
                            prompts.usergot = true;
                    }
                }
                if (input.equals("exit")) {
                    return "exit";
                }
                //loops through the list of allUsers in the system
                for (int i = 0; i < allAdmins.getAllAdmins().size(); i++) {
                    //checks if the entered username already exists
                    if (temp.get(0).equals(allAdmins.getAllAdmins().get(i).getName())) {
                        System.out.print("Username already exists. Please choose a new username\n");
                        return execute(admin, allAdmins, allUsers, allItems, allUserMessages, allTransactions, allRequests, allCurrency);
                    }
                }
                allAdmins.addAdmin(temp.get(0), temp.get(1));
                System.out.println("\nNew admin has been added successfully.\n");
                return "back";
            } else if (input.equals("2")) {
                return "back";
            }
            else {
                System.out.println("Invalid input. Please try again.");
                return null;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}
