import java.io.IOException;

public class DemoName {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InputGetter system1 = new InputGetter();
        UserManager allUsers = new UserManager();
        writing x = new writing();
        User hello = system1.authenticator(allUsers);
        x.demoUserRead("src/UserList3.ser", allUsers);
        System.out.println(allUsers.getAllUsers());
        allUsers.createUser("Tina", "123");
        x.demoUserSave(allUsers);
        System.out.println(allUsers.getAllUsers());
        //system1.mainMenu(hello);

    }

}
