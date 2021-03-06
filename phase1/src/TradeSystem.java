import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TradeSystem initializes all the controllers and use cases in the system.
 */
public class TradeSystem {
    public InputGetter inputgetter = new InputGetter();
    public AdminInputGetter admininputgetter = new AdminInputGetter();
    public UserManager allUsers = new UserManager();
    public TransactionManager allTransactions;
    public AdminManager allAdmins;
    public ItemManager allItems = new ItemManager();
    public MeetingManager allMeetings = new MeetingManager();
    public TradeRequestManager allTradeRequests;
    public Object currentUser;

    /**
     * Run calls multiple gateways in order to populate the System with saved information pertaining Transactions,
     * Admins, Users, Meetings, Items and TradeRequests. It will call methods that allow Users and Admins to log in
     * and view the main menu as long as an Admin or User doesn't want to exit the system. If they choose to exit,
     * the gateways will be called again and saved the information for future use.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void run() throws IOException, ClassNotFoundException {
        //either returns the saved AdminManager object with all the stored admin information
        //or creates a new AdminManager object with a default admin with user name Tina and password 123456
        //if the file AdminList.ser is empty
        allAdmins = AdminWriting.demoAdminRead("AdminList.ser");
        //either returns the saved TransactionManager object with all the stored Transactions
        //or creates a new Transaction object if file TransactionList.ser is empty
        allTransactions = TransactionWriting.demoTransactionRead("TransactionList.ser");
        //either returns the saved TradeRequest object with all the stored Trade Requests
        //or creates a new TradeRequest object if file TradeRequestList.ser is empty
        allTradeRequests = TradeRequestWriting.demoTradeRequestRead("TradeRequestList.ser");
        //populates the system inventory in the initialized ItemManager AllItems with all the saved
        //items in the file ItemList.ser
        ItemWriting.demoItemRead("ItemList.ser", allItems);
        //reads the file and populates UserManager allUsers with the Users stored in UserList.ser
        UserWriting.demoUserRead("UserList.ser", allUsers);



        //If there is no current User, prompts log in and prompts the correct menu depending on the type of Account
        while (currentUser == null) {
            LogInSystem system1 = new LogInSystem(allUsers, allAdmins);
            Object loggedIn = system1.LogIn(inputgetter, admininputgetter);
            //if they decide to exit at any point, loggedIn will equal "exit" and it will break the loop
            //and proceed to save the current information and exit the system
            if (loggedIn != null && loggedIn.equals("exit")) {
                break;
            }
            currentUser = loggedIn;
            while (loggedIn != null) {
                if (loggedIn instanceof User) {
                    loggedIn = inputgetter.mainMenu((User) loggedIn, allItems, inputgetter, allTradeRequests,
                            allUsers, allMeetings, allTransactions, admininputgetter, allAdmins);
                } else if (loggedIn instanceof Admin) {
                    loggedIn = admininputgetter.mainMenu((Admin) loggedIn, allAdmins, allUsers, allItems);
                } else if (loggedIn.equals("exit")) {
                    //loop will break if user decides to exit at any point while they are logged in
                    break;
                }
            }
            //loop will break if user decides to exit at any point while they are logged in
            //will then proceed to save current system information and exit the system
            if (loggedIn != null && loggedIn.equals("exit")) {
                break;
            }
            //if the user logs out, it will reset currentUser to null and the while loop will continue to run
            //and it will redirect back to the log in page
            currentUser = null;
        }

        System.out.print("Goodbye!\uD83D\uDEAA \n");

        //saves all the accounts.users in UserManager to an external file
        UserWriting.demoUserSave("UserList.ser", allUsers);
        //saves current AdminManager object allAdmins to external file
        AdminWriting.demoAdminSave("AdminList.ser", allAdmins);
        //saves current TransactionManager object allTransactions to an external file
        TransactionWriting.demoTransactionSave("TransactionList.ser", allTransactions);
        //saves the systemInventory in AllItems to an external file
        ItemWriting.demoItemSave("ItemList.ser", allItems);
        //saves current TradeRequest object allTradeRequests to an external file
        TradeRequestWriting.demoTradeRequestSave("TradeRequestList.ser", allTradeRequests);
    }
}
