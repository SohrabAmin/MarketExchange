import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class NormalUserMainMenu implements DifferentUserMainMenu {
    /**
     * Displays the main menu for a normal, unfrozen user and prompts user for input depending on what
     * they want to do.
     * <p>
     * Unfrozen Users are able to do the following:
     * View wishlist, view inventory, browse items, initiate trade, view messages, approve pending trades, add
     * items to inventory, view recent trades, view most frequent trading partners, view item statuses, add items to
     * wishlists, view approved trades, approve meetings, confirm meetings, and log out.
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
        System.out.print("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + user.getName() + "!\n");
        if (allUsers.getUser(user).getPendingRequests().size() > 0) {
            System.out.print("\uD83D\uDCE9 You have " + allUsers.getUser(user).getPendingRequests().size() +
                    " Pending Trade Requests!\n");
        }
        if (allUsers.getUser(user).getPendingTrades().size() > 0) {
            System.out.print("\u23F3 You have " + allUsers.getUser(user).getPendingTrades().size() +
                    " Pending Transactions!\n");
        }
        System.out.print("Please select number from the following:\n1. View and edit Wishlist\n2. View Inventory\n" +
                "3. Browse Items\n4. Initiate Trade\n5. View Pending Trade Requests\n6. Approve Pending Trade Requests\n" +
                "7. Add Item to inventory\n8. View most recent trades\n9. View most frequent trading partners\n" +
                "10. View status of my items\n" +
                "11. Approve Meeting\n12. Confirm Meetings for Approved Trades\n13. View status of outbound requests\n" +
                "14. Change Account Settings\n15. Go on vacation\n16. Logout" +
                "\nEnter 'exit' to exit the system at any time.\n");

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
                } else if (input.equals("4")) { //initiate trade
                    option.setChosenOption(new TradeInitiator());
                } else if (input.equals("5")) { //view pending trade requests
                    option.setChosenOption(new PrintUserMessages());
                } else if (input.equals("6")) { //approve trade reqs
                    option.setChosenOption(new ApproveTrade());
                } else if (input.equals("7")) { //add item to inventory
                    option.setChosenOption(new AddItemToSystem());
                } else if (input.equals("8")) { //view recent trades
                    option.setChosenOption(new PrintMostRecentTrades());
                } else if (input.equals("9")) { //view most freq trading partners
                    option.setChosenOption(new PrintTop3TradingPartners());
                } else if (input.equals("10")){  //view item status
                    option.setChosenOption(new PrintItemHistory());
                } else if (input.equals("11")) { //approve meeting
                    option.setChosenOption(new PendingTransactionProcess());
                } else if (input.equals("12")) { //confirm meeting for approved trades
                    option.setChosenOption(new ConfirmMeetings());
                } else if (input.equals("13")) { //view outbound req status
                    option.setChosenOption(new PrintOutboundRequests());
                } else if (input.equals("14")) { //change account settings
                    option.setChosenOption(new AccountSettingsManager());
                } else if (input.equals("15")) { //go on vacation
                    option.setChosenOption(new VacationPrompter());
                }
                else if (input.equals("16")) { //logout
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

                // this check is used to log user out when they confirm that they are going on vacation
                if (result.equals("leave")) {
                    return null;
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
