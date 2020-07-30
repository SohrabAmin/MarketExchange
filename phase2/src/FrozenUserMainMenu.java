import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Logger;

public class FrozenUserMainMenu implements  DifferentUserMainMenu{
    /**
     * Displays the main menu for a frozen or pseudoFrozen user and prompts user for input depending on what they want to do.
     * <p>
     * Frozen users are able to do the following:
     * View wishlist, view inventory, browse items, add items to inventory, view most recent trades, view most
     * frequent trading partners, view item statuses, add items to wishlist, request unfreeze and log out.
     *
     * @param user             the user that is currently logged in to the system
     * @param allItems         ItemManager that stores the system's inventory
     * @param allTradeRequests TradeRequestManager that stores all the Trade Requests in the system
     * @param allUsers         UserManager that stores all the Users in the system
     * @param allMeetings      MeetingManager that deals with the creation of meetings
     * @param allTransactions  TransactionManager that stores all the Transactions in the system
     * @return depending on what the User inputs it will return different objects:
     * returns User to TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another User to log in
     * returns String "exit" to tell TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object mainMenu(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                                 UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                                 AdminManager allAdmins, Logger undoLogger) {
        Scanner sc = new Scanner(System.in);

        System.out.print("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + user.getName() + "!\n");
        String actionTaker = "by Admin.";
        if (user.getIsPseudoFrozen())
            actionTaker = "by System.";

        System.out.print("\uD83E\uDD76 Your account is frozen " + actionTaker +
                " You are not able to do any trades until you are unfrozen by admin.\n" +
                "\uD83C\uDFC1 Please ask Admin to unfreeze your account!\n\n");

        System.out.print("Please select number from the following:\n1.View and edit Wishlist\n2.View Inventory\n" +
                "3.Browse Items\n" +
                "4.Add Item to inventory\n5.View most recent trades\n6.View most frequent trading partners\n" +
                "7.View status of my items" +
                "\n8.Request unfreeze!\n9.Change Account Settings\n10.Logout" + "\nEnter 'exit' to exit the system at any time.\n");

        ChosenOption option = new ChosenOption();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String input = br.readLine();
            if (!input.equals("exit")) {
                if (input.equals("1")) { //view and edit wishlist
                    option.setChosenOption(new WishlistManager());
                } else if (input.equals("2")) { //view inventory
                    option.setChosenOption(new InventoryManager());
                } else if (input.equals("3")) { //browse
                    option.setChosenOption(new Browse());
                } else if (input.equals("4")) { //add item to inventory
                    option.setChosenOption(new AddItemToSystem());
                } else if (input.equals("5")) { //view recent trades
                    option.setChosenOption(new PrintMostRecentTrades());
                } else if (input.equals("6")) { //view most freq trading partners
                    option.setChosenOption(new PrintTop3TradingPartners());
                } else if (input.equals("7")){  //view item status
                    option.setChosenOption(new PrintItemHistory());
                } else if (input.equals("8")) {
                    option.setChosenOption(new NotifyAdminOfUnfreezeRequest());
                } else if (input.equals("9")) { //change account settings
                    option.setChosenOption(new AccountSettingsManager());
                } else if (input.equals("10")) { //logout
                    return null;
                } else { //returns to main menu
                    System.out.println("That is not a valid option. Please try again.");
                    return user;
                }
                Object result = option.executeOption(user, allItems, allTradeRequests, allUsers, allMeetings,
                        allTransactions, allAdmins, undoLogger);
                while (result == null) {
                    result = option.executeOption(user, allItems, allTradeRequests, allUsers, allMeetings,
                            allTransactions, allAdmins, undoLogger);
                }
                return user;
            }
            return input;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        return user;
    }

}
