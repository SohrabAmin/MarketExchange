import java.io.File;
import java.io.IOException;
 
public class DemoName {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InputGetter system1 = new InputGetter();
        UserManager allUsers = new UserManager();
        TransactionManager allTransactions = new TransactionManager();
        Admin initialAdmin = new Admin("Tina", "123456");
        AdminManager allAdmins = new AdminManager(initialAdmin);
        ItemManager AllItems = new ItemManager();
        MeetingManager allMeetings = new MeetingManager();
        //searchmanager maybe?

        //allUsers.createUser("Tina", "123");
        writing x = new writing();

        
      //  hello.wishlist.add(myitem);
        System.out.println("The current number of users in the file is:" + allUsers.getAllUsers().size());
        File findFile = new File("UserList3.ser");
        String filePath = findFile.getAbsolutePath();
        System.out.println("The file location is:" + filePath);
        x.demoUserRead("UserList3.ser", allUsers);
        System.out.println("The current number of users in the file is:" + allUsers.getAllUsers().size());
        System.out.println("the user manager contains the following:" + allUsers.getAllUsers());


        User hello = system1.authenticator(allUsers);

        Item myitem = new Item("sock" , hello, "its cute");
        Item myitem2 = new Item("sock3" , hello, "its 2cute");

        AllItems.addItem(myitem);
        system1.mainMenu(hello, AllItems, system1);

        x.demoUserSave("UserList3.ser", allUsers);
        System.out.println(allUsers.getAllUsers());
        System.out.println("The current number of users in the file is:" + allUsers.getAllUsers().size());
        System.out.println("the user manager contains the following:" + allUsers.getAllUsers());

    }

}
