import java.io.File;
import java.io.IOException;
 
public class DemoName {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //InputGetter system1 = new InputGetter();
        UserManager allUsers = new UserManager();
        TransactionManager allTransactions = new TransactionManager();
        Admin initialAdmin = new Admin("Tina", "123456");
        AdminManager allAdmins = new AdminManager(initialAdmin);
        ItemManager AllItems = new ItemManager();
        MeetingManager allMeetings = new MeetingManager();
        TradeRequestManager allTradeRequests = new TradeRequestManager();
        //searchmanager maybe?

        allUsers.createUser("Tina", "123");
        writing x = new writing();
        allUsers.createUser("Mo", "123");

        LogInSystem system1 = new LogInSystem(allUsers, allAdmins);
        system1.LogIn();
        //system1.authenticator(allUsers);

        //  hello.wishlist.add(myitem);
        System.out.println("UserManager is initiated. There should be no Users. There are: "
                + allUsers.getAllUsers().size() + " users");
        File findFile = new File("UserList4.ser");
        String filePath = findFile.getAbsolutePath();
        System.out.println("The file location is:" + filePath);
        x.demoUserRead("UserList4.ser", allUsers);
        System.out.println("UserManager is now populated." +
                "The current number of users in the file is:" + allUsers.getAllUsers().size());
        System.out.println("the user manager contains the following users:" + allUsers.getAllUsers());


       // User hello = system1.authenticator(allUsers);
        User hello = new User("Tina", "123");
        User hi = new User("Mo", "123");
        Item myitem = new Item("sock" , hello, "its cute");
        Item myitem2 = new Item("sock3" , hello, "its 2cute");

        AllItems.addItem(myitem);
        //system1.mainMenu(hi, AllItems, system1, allTradeRequests, allUsers);

        x.demoUserSave("UserList4.ser", allUsers);
        System.out.println(allUsers.getAllUsers());
        System.out.println("The current number of users in the file is:" + allUsers.getAllUsers().size());
        System.out.println("the user manager contains the following:" + allUsers.getAllUsers());

    }

}
