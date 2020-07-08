import java.io.File;
import java.io.IOException;

/**
 * Read and writes the UserList file in order to populate UserManager. This code is in the Week 6 Module from logging.
 */
public class writing {

    //public static void main(String[] args) throws IOException, ClassNotFoundException {

    //}

    public static void demoUserRead(String s, UserManager u) throws IOException, ClassNotFoundException {

        String serializedUserInfo = "src/UserList3.ser";
        // creates UserReadWrite which manages the saving and loading users.
        UserReadWrite userRW = new UserReadWrite(serializedUserInfo);
        //System.out.println("Initial state:\n" + userRW);


//    // Deserializes contents of the SER file
        userRW.readFromFile(serializedUserInfo);
        userRW.populateUserManager(u);

        // adds a new student to UserReadWrite sm's records
        //userRW.add(new User("Tina", "123"));
        //userRW.add(new User("yoyoo", "12322"));
        //System.out.println("After:\n" + userRW);

        // Writes the existing Student objects to file.
        // This data is serialized and written to file as a sequence of bytes.
        //userRW.saveToFile(serializedUserInfo);
    }

    public static void demoUserSave(UserManager u) throws IOException, ClassNotFoundException {
        String serializedUserInfo = "src/UserList3.ser";
        UserReadWrite userRW = new UserReadWrite(serializedUserInfo);
        userRW.saveToFile(serializedUserInfo, u);
    }
}
