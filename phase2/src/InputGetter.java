import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//Note: how to put emojis in the code was found here: http://dplatz.de/blog/2019/emojis-for-java-commandline.html
public class InputGetter {
    private static final Logger undoLogger = Logger.getLogger(AdminInputGetter.class.getName());
    private File undoLog = new File("UndoLog.txt");
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("UndoLog.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to initialize FileHandler.");
        }
    }

    public InputGetter() {
        undoLogger.setLevel(Level.ALL);
        fileHandler.setLevel(Level.ALL);
        undoLogger.addHandler(fileHandler);

        // Prevent fileHandler from printing to the console:
        undoLogger.setUseParentHandlers(false);
        // Credit for the above line goes to
        // https://stackoverflow.com/questions/2533227/how-can-i-disable-the-default-console-handler-while-using-the-java-logging-api
    }

    /**
     * Displays the main menu, and prompts user for input depending on what they want to do.
     * <p>
     * Unfrozen Users are able to do the following:
     * View wishlist, view inventory, browse items, initiate trade, view messages, approve pending trades, add
     * items to inventory, view recent trades, view most frequent trading partners, view item statuses, add items to
     * wishlists, view approved trades, approve meetings, confirm meetings, and log out.
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
                            AdminManager allAdmins) {
        //A frozen account is one where you can log in and look for items, but you cannot arrange any transactions.
        // A user who has been frozen can request that the administrative user unfreezes their account.
        boolean frozenAccount = user.getIsFrozen();
        boolean pseudoFrozenAccount = user.getIsPseudoFrozen();
        //if they are frozen
        if (frozenAccount || pseudoFrozenAccount) { //first off, tell them that they are frozen
            return frozenMainMenu(user, allItems, allTradeRequests, allUsers, allMeetings, allTransactions, allAdmins);
        }
        //if they are a demo user without an account
        else if (user.getName().equals("Demo")) {
            return demoMainMenu(user, allItems, allTradeRequests, allUsers, allMeetings, allTransactions, allAdmins);
        }
        // if they are on vacation
        else if (user.getIsOnVacation()) {
            return vacationMainMenu(user);
        }
        else {
            return userMainMenu(user, allItems, allTradeRequests, allUsers, allMeetings, allTransactions, allAdmins);
        }
    }

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
    public Object userMainMenu(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                               UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                               AdminManager allAdmins) {
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
                if (result.equals("exit")) {
                    return "exit";
                }

                return user;
            }
            return input;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        return user;
    }

    /**
     * Displays the main menu for a demo user and prompts user for input depending on what they want to do.
     * <p>
     * Demo users are able only able to browse items and log out.
     * However, they can choose from the following and it will show them what those menu options do if they create
     * an account:
     * View wishlist, view inventory, initiate trade, view messages, approve pending trades, add
     * items to inventory, view recent trades, view most frequent trading partners, view item statuses, add items to
     * wishlists, view approved trades, approve meetings, confirm meetings, and change account settings.
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
    public Object demoMainMenu(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                               UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                               AdminManager allAdmins) {
        System.out.print("-------------------------------------------------------\n\uD83E\uDD16 Hello Demo User \uD83E\uDD16\n");
        System.out.print("Please select number from the following:\n" +
                "\uD83C\uDD94 Indicates that signup/login as user is required!\n1.View Wishlist\n2.View Inventory \uD83C\uDD94 \n" +
                "3.Browse Items\n4.Initiate Trade \uD83C\uDD94 \n5.View Pending Trade Requests \uD83C\uDD94\n6.Approve Pending Trade Requests \uD83C\uDD94\n" +
                "7.Add Item to inventory \uD83C\uDD94\n8.View most recent trades \uD83C\uDD94\n9.View most frequent trading partners \uD83C\uDD94\n" +
                "10.View status of my items \uD83C\uDD94\n11.Add Item to wishlist\n" +
                "12.Approve Meeting \uD83C\uDD94\n13.Confirm Meetings for Approved Trades \uD83C\uDD94\n" +
                "14.View status of outbound requests \uD83C\uDD94\n" + "15.Change Account Settings \uD83C\uDD94\n" +
                "16.Logout" + "\nEnter 'exit' to exit the system at any time.\n");

        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        if (!a.equals("exit")) {
            if (a.equals("1")) { //view wishlist
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 Your wishlist are items that you want to have in the future!\n");
                ChosenOption option = new ChosenOption();
                option.setChosenOption(new WishlistManager());
                Object temp = option.executeOption(user, allItems, allTradeRequests, allUsers, allMeetings,
                        allTransactions, allAdmins, InputGetter.undoLogger);
                while (temp == null) {
                    temp = option.executeOption(user, allItems, allTradeRequests, allUsers, allMeetings,
                            allTransactions, allAdmins, InputGetter.undoLogger);
                }
                return user;
            } else if (a.equals("2")) { //view inventory
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can add items to be added to your inventory. " +
                        "Once you add an item, the item is sent to approval to Admin.\n");
                return user;
            } else if (a.equals("3")) { //browse items
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see all items available in the system to trade. " +
                        "Each item is approved by an admin.\n");
                ChosenOption option = new ChosenOption();
                option.setChosenOption(new Browse());
                Object temp = option.executeOption(user, allItems, allTradeRequests, allUsers, allMeetings,
                        allTransactions, allAdmins, InputGetter.undoLogger);
                while (temp == null) {
                    temp = option.executeOption(user, allItems, allTradeRequests, allUsers, allMeetings,
                            allTransactions, allAdmins, InputGetter.undoLogger);
                }
                return user;
            } else if (a.equals("4")) { //choose the id?
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can choose an item and initiate a 1way or 2way " +
                        "permanent/temporary trade.\n");
                //else input was "back", returns to main menu
                return user;
            } else if (a.equals("5")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see any pending trade requests.\n");
                return user;
            } else if (a.equals("6")) {
                System.out.print("-------------------------------------------------------\n" +
                        "\uD83D\uDC81 As a user, you can look through all trade requests and their details and " +
                        "decide if you want to confirm or reject the trade.\n");
                return user;
            } else if (a.equals("7")) { //request to add new item
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can add items to your inventory. However, once added, " +
                        "the request will be sent to admin for approval. \n" +
                        "Once approved, the item will show up in your inventory and it will be visible to other " +
                        "users when browsing.\n");
                return user;
            } else if (a.equals("8")) { //View most recent trades
                System.out.print("-------------------------------------------------------\n" +
                        "\uD83D\uDC81 As a user, you can see information about your 3 most recent trades.\n");
                return user;
            } else if (a.equals("9")) { //View most frequent trading partners
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see who your most frequently trading partners are.\n");
            } else if (a.equals("10")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see a list of all items you have ever submitted to the " +
                        "system and whether they were approved/rejected by admin or still pending on admin's approval.\n");
                return user;
            } else if (a.equals("11")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can add items to your wishlist that you would " +
                        "like to purchase.\n");
                return user;
            } else if (a.equals("12")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, once a trade is accepted by the other party, " +
                        "you can see the suggested meeting, approve meeting or suggest an alternative " +
                        "meeting here.\n");
                return user;
            } else if (a.equals("13")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, here you can confirm a meeting happened or if it did " +
                        "not happen. If the meeting did not happen, both parties may be penalized.\n");
                return user;
            } else if (a.equals("14")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see the status of the outbound trade requests " +
                        "you have sent.\n");
            } else if (a.equals("15")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can change your account settings; this includes changing" +
                        " your username, password and set location.\n");
            } else if (a.equals("16")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 Logging out as Demo!\n");
                //logout
                return null;
            } else { //if they input invalid response
                System.out.print("\uD83E\uDDD0 Invalid Response!\n");
                return user;
            }
        } else {//input is "exit"
            return a;
        }
        return user;
    }

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
    public Object frozenMainMenu(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                                 UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                                 AdminManager allAdmins) {
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

    public Object vacationMainMenu (User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + user.getName() + "!\n");
        System.out.println("Are you still on vacation? If so, press 1. If not, press 2.");
        String confirmation = scanner.nextLine();
        if (confirmation.equals("1")) {
            System.out.println("Go enjoy your vacation!");
            return "exit";
        }
        else if (confirmation.equals("2")) {
            System.out.println("We hope you enjoyed your vacation!");
            user.setIsOnVacation(false);
            //TODO: use undoLogger to add items back to user's inventory
            return user;
        }
        else {
            System.out.println("Invalid option. Please try again.");
            return user;
        }
    }

}
