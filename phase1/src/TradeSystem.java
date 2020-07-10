import java.io.File;
import java.io.IOException;

public class TradeSystem {
    public InputGetter inputgetter = new InputGetter();
    public AdminInputGetter admininputgetter = new AdminInputGetter();
    public UserManager allUsers = new UserManager();
    public TransactionManager allTransactions = new TransactionManager();
;   public AdminManager allAdmins;
    public ItemManager AllItems = new ItemManager();
    public MeetingManager allMeetings = new MeetingManager();
    public TradeRequestManager allTradeRequests = new TradeRequestManager();

    public TradeSystem() {
        Admin initialAdmin = new Admin("Tina", "123456");
        allAdmins = new AdminManager(initialAdmin);
    }

    public void run() throws IOException, ClassNotFoundException {
        System.out.println("UserManager is initiated. There should be no Users. There are: "
                + allUsers.getAllUsers().size() + " users");

        //prints where the file is located
        File findFile = new File("UserList4.ser");
        String filePath = findFile.getAbsolutePath();
        System.out.println("The file location is:" + filePath);
        //reads the file and prints the current users.
        userWriting.demoUserRead("UserList4.ser", allUsers);
        System.out.println("UserManager is now populated." +
                "The current number of users in the file is:" + allUsers.getAllUsers().size());
        System.out.println("the user manager contains the following users:" + allUsers.getAllUsers());

        //prompts log in and prompts the correct menu depending on the type of account
        LogInSystem system1 = new LogInSystem(allUsers, allAdmins);
        Object loggedIn = system1.LogIn(inputgetter,admininputgetter);
        if (loggedIn instanceof User){
            inputgetter.mainMenu((User) loggedIn, AllItems, inputgetter, allTradeRequests, allUsers);
        }
        else if (loggedIn instanceof Admin){
            admininputgetter.mainMenu((Admin) loggedIn);
        }

        //creating users to test
        allUsers.createUser("Tina", "123");
        allUsers.createUser("Mo", "123");

        User hello = new User("Tina", "123");
        User hi = new User("Mo", "123");
        Item myitem = new Item("sock" , hi, "its cute");
        Item myitem2 = new Item("sock3" , hello, "its 2cute");

        AllItems.addItem(myitem);


        //saves all the users in UserManager
        userWriting.demoUserSave("UserList4.ser", allUsers);
        System.out.println(allUsers.getAllUsers());
        System.out.println("The current number of users in the file is:" + allUsers.getAllUsers().size());
        System.out.println("the user manager contains the following:" + allUsers.getAllUsers());
    }
}
