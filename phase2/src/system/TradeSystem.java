package system;

import accounts.users.User;
import accounts.users.UserManager;
import accounts.users.UserMessageManager;
import accounts.admins.Admin;
import accounts.admins.AdminManager;
import currency.CurrencyManager;
import items.Item;
import items.ItemManager;
import meetings.MeetingManager;
import requests.TradeRequestManager;
import transactions.TransactionManager;

import java.io.IOException;

/**
 * system.TradeSystem initializes all the controllers and use cases in the system.
 */
public class TradeSystem {
    public InputGetter inputgetter = new InputGetter();
    public AdminInputGetter admininputgetter = new AdminInputGetter();
    public MeetingManager allMeetings = new MeetingManager();
    public UserManager allUsers = new UserManager();
    public ItemManager allItems = new ItemManager();
    public TransactionManager allTransactions;
    public AdminManager allAdmins;
    public TradeRequestManager allTradeRequests;
    public ReadWrite readwrite = new ReadWrite();
    public Object currentUser;
    public UserMessageManager allUserMessages;
    public CurrencyManager allCurrency = new CurrencyManager();


    /**
     * Run calls multiple gateways in order to populate the System with saved information pertaining Transactions,
     * Admins, Users, Meetings, Items and TradeRequests. It will call methods that allow Users and Admins to log in
     * and view the main menu as long as an accounts.admins.Admin or accounts.users.User doesn't want to exit the system. If they choose to exit,
     * the gateways will be called again and saved the information for future use.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void run() throws IOException, ClassNotFoundException {
        //either returns the saved accounts.admins.AdminManager object with all the stored admin information
        //or creates a new accounts.admins.AdminManager object with a default admin with user name Tina and password 123456
        //if the file AdminList.ser is empty
        allAdmins = readwrite.adminPopulate("AdminList.ser");
        //either returns the saved transactions.TransactionManager object with all the stored Transactions
        //or creates a new transactions.Transaction object if file TransactionList.ser is empty
        allTransactions = readwrite.transactionManagerPopulate("TransactionList.ser");
        //either returns the saved requests.TradeRequest object with all the stored Trade Requests
        //or creates a new requests.TradeRequest object if file TradeRequestList.ser is empty
        allTradeRequests = readwrite.tradeRequestPopulate("TradeRequestList.ser");
        //populates the system inventory in the initialized items.ItemManager AllItems with all the saved
        //items in the file ItemList.ser
        readwrite.itemPopulate(allItems, "ItemList.ser");
        //reads the file and populates accounts.users.UserManager allUsers with the Users stored in UserList.ser
        readwrite.userPopulate(allUsers, "UserList.ser");
        //either returns the saved accounts.users.UserMessageManager object with all the stored user messages
        //or creates a new accounts.users.UserMessageManager object if file UserMessages.ser is empty
        allUserMessages = readwrite.userMessagePopulate("UserMessages.ser");


        //jsut for testing broo
        allUsers.createUser ("Tina", "123");
        allUsers.createUser("Mo", "123");
        allUsers.createUser("A", "123");


        User Tina = new User("Tina", "123");
        User Mo = new User ("Mo", "123");
        User A = new User ("A", "123");

        Item  shoe = new Item("shoe", allUsers.getUser(Tina) , "hi" , "Electronics" , false, false, true, false, 2.0, null, null);
        allUsers.addToInventory(allUsers.getUser(Tina), shoe);
        allItems.addItem(shoe);


        Item  ebook = new Item("ebook", allUsers.getUser(Tina) , "hi" , "Electronics" , false, true, true, false, 2.0, null, null);
        allUsers.addToInventory(allUsers.getUser(Tina), ebook);
        allItems.addItem(ebook);


        Item sock = new Item("sock", allUsers.getUser(Tina), "hi", "Electronics", false, true, true, true,
                2.0, 2.0, 2);
        allUsers.addToInventory(allUsers.getUser(Tina), sock);
        allItems.addItem(sock);



        Item book = new Item("book", allUsers.getUser(Tina), "hi", "Electronics", false, true, true, true,
                2.0, 2.0, 2);
        allUsers.addToInventory(allUsers.getUser(Tina), book);
        allItems.addItem(book);

        Item pen = new Item("pen", allUsers.getUser(Mo), "hi", "Electronics", false, true, true, true,
                2.0, 2.0, 2);
        allUsers.addToInventory(allUsers.getUser(Mo), pen);
        allItems.addItem(pen);

        Item phone = new Item("phone", allUsers.getUser(A), "hi", "Electronics", false, true, true, true,
                2.0, 2.0, 2);
        allUsers.addToInventory(allUsers.getUser(A), phone);
        allItems.addItem(phone);















        //If there is no current accounts.users.User, prompts log in and prompts the correct menu depending on the type of accounts.Account
        while (currentUser == null) {
            LogInSystem system1 = new LogInSystem(allUsers, allAdmins);
            Object loggedIn = system1.LogIn();
            //if they decide to exit at any point, loggedIn will equal "exit" and it will break the loop
            //and proceed to save the current information and exit the system
            if (loggedIn != null && loggedIn.equals("exit")) {
                break;
            }
            currentUser = loggedIn;
            while (loggedIn != null) {
                if (loggedIn instanceof User) {
                    loggedIn = inputgetter.callMainMenu((User) loggedIn, allItems, allTradeRequests,
                            allUsers, allMeetings, allTransactions, allAdmins, allUserMessages, allCurrency);
                } else if (loggedIn instanceof Admin) {
                    loggedIn = admininputgetter.mainMenu((Admin) loggedIn, allAdmins, allUsers, allItems,
                            allUserMessages, allTransactions, allTradeRequests, allCurrency);
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

        //saves all the accounts.users in accounts.users.UserManager to an external file
        readwrite.saveToFile("UserList.ser", allUsers);
        //saves current accounts.admins.AdminManager object allAdmins to external file
        readwrite.saveToFile("AdminList.ser", allAdmins);
        //saves current transactions.TransactionManager object allTransactions to an external file
        readwrite.saveToFile("TransactionList.ser", allTransactions);
        //saves the systemInventory in AllItems to an external file
        readwrite.saveToFile("ItemList.ser", allItems);
        //saves current requests.TradeRequest object allTradeRequests to an external file
        readwrite.saveToFile("TradeRequestList.ser", allTradeRequests);
        //saves current accounts.users.UserMessageManager object allTradeRequests to an external file
        readwrite.saveToFile("UserMessages.ser", allUserMessages);
    }
}

