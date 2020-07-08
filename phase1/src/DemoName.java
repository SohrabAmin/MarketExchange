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





        allUsers.createUser("Tina", "123");
      //  writing x = new writing();
        User hello = system1.authenticator(allUsers);


        Item myitem = new Item("sock" , hello, "its cute");
        Item myitem2 = new Item("sock3" , hello, "its 2cute");

        AllItems.addItem(myitem);
        
      //  hello.wishlist.add(myitem);



       // x.demoUserRead("/Users/tina.tavallaeianibm.com/CSC207July7/CSC207July8/group_0041/phase1/src/UserList3.ser", allUsers);
        System.out.println(allUsers.getAllUsers());

       //x.demoUserSave(allUsers);
        System.out.println(allUsers.getAllUsers());
        system1.mainMenu(hello, AllItems, system1);

    }

}
