import java.io.File;
import java.io.IOException;

// NOTE: This class is based off of DemoManager from logging in Week 6 Modules on Quercus.


public class writing {

    /**
     * Read and writes the UserList file in order to populate UserManager.
     */
    public static void demoUserRead(String s, UserManager u) throws IOException, ClassNotFoundException {

        String serializedUserInfo = "src/UserList3.ser";
        // creates UserReadWrite which manages the saving and loading users.
        UserReadWrite userRW = new UserReadWrite(serializedUserInfo);


//    // Deserializes contents of the SER file
        userRW.readFromFile(serializedUserInfo);
        userRW.populateUserManager(u);

    }

    /**
     *  Saves the users from userManager into the UserList file.
     *
     * @param userManager
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void demoUserSave(UserManager userManager) throws IOException, ClassNotFoundException {
        String serializedUserInfo = "src/UserList3.ser";
        UserReadWrite userRW = new UserReadWrite(serializedUserInfo);
        userRW.saveToFile(serializedUserInfo, userManager);
    }
}
