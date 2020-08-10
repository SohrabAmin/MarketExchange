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

import java.util.Scanner;


public class ViewUserMessages implements adminMainMenuOptions {
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages, TransactionManager allTransactions,
                          TradeRequestManager allRequests, CurrencyManager allCurrency) {

        if (allUserMessages.getAllUserMessage().size() == 0) {
            System.out.println("You have no messages.");
            return "back";
        }

        System.out.println("Here are your messages:");

        for (int i = 0; i < allUserMessages.getAllUserMessage().size(); i++) {
            String person = allUserMessages.getAllUserMessage().get(i).get(0);
            String mess = allUserMessages.getAllUserMessage().get(i).get(1);
            System.out.println((i+1) + ". " + "From user:" + person + "\nMessage: " + mess);
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter '1' if you'd like to delete a message or enter '2' to reply to a message. Enter 'back' to return to the main menu.");
        String input = sc.nextLine();

        if (input.equals("back")) {
            return "back";
        } else if (input.equals("1")) {
            System.out.println("Warning: Deleting a message will delete it for all accounts.admins!");
            System.out.println("Please enter the number beside the message you'd like to delete or enter 'back' to return" +
                    " to the main menu");
            Object chosen = sc.nextLine();
            if (chosen.equals("back")) {
                return "back";
            }
            try {
                //will try to turn the input into an integer
                //if input is not an integer, returns null
                chosen = Integer.parseInt((String) chosen);
            } catch (NumberFormatException e) {
                return null;
            }
            allUserMessages.removeFromAllUserMessage(allUserMessages.getAllUserMessage().get((int) chosen - 1));
            System.out.println("\nMessage has been removed successfully!");
            return null;
        } else if (input.equals("2")) {
            System.out.println("Please enter the number beside the message you'd like to reply to or enter 'back' to return" +
                    " to the main menu");
            Object replyTo = sc.nextLine();
            if (replyTo.equals("back")) {
                return "back";
            }
            try {
                //will try to turn the input into an integer
                //if input is not an integer, returns null
                replyTo = Integer.parseInt((String) replyTo);
            } catch (NumberFormatException e) {
                return null;
            }
            User person = null;
            for (int j = 0; j < allUsers.getAllUsers().size(); j++) {
                if (allUsers.getAllUsers().get(j).getName().equals(allUserMessages.getAllUserMessage().get((Integer) replyTo - 1).get(0))) {
                    person = allUsers.getAllUsers().get(j);
                }
            }
            if (person == null) {
                System.out.println("Something went wrong. accounts.users.User may have changed their username!");
                allUserMessages.removeFromAllUserMessage(allUserMessages.getAllUserMessage().get((int) replyTo - 1));
                return null;
            }
            System.out.println("Please leave a message to user " + person.getName() + " or enter 'back' to return to the main menu.");
            String reply = sc.nextLine();
            if (reply.equals("back")) {
                return "back";
            }
            System.out.println("Your message is: \n" + reply);
            System.out.println("Please enter '1' to confirm your message or '2' to cancel the message.");
            String confirmation = sc.nextLine();
            if (confirmation.equals("1")) {
                allUsers.addToAdminMessages(person, reply + "\nRE: " + allUserMessages.getAllUserMessage().get((Integer) replyTo - 1).get(1));
                System.out.println("Your message has been sent successfully!");
            } else if(confirmation.equals("2")) {
                System.out.println("Your message has been cancelled.");
                return null;
            } else {
                System.out.println("That is not a valid response! Please try again.");
                return null;
            }
            return null;
        } else {
            System.out.println("That is not a valid response! Please try again.");
            return null;
        }
    }
}