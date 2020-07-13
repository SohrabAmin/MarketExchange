import java.io.File;
import java.io.IOException;

// NOTE: This class is based off of DemoManager from logging in Week 6 Modules on Quercus.

/**
 * Class deals with the reading, writing and populating of Users in the systems UserManager class based on
 * information from an external file.
 */
public class userWriting {

    public static void demoUserRead(String fileName, UserManager u) throws IOException, ClassNotFoundException {
        // creates UserReadWrite which manages the saving and loading users.
        UserReadWrite userRW = new UserReadWrite(fileName);

        // Deserializes contents of the SER file
        userRW.readFromFile(fileName);
        userRW.populateUserManager(u);
    }

    /**
     *  Saves the users from userManager into the UserList file.
     *
     * @param userManager
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void demoUserSave(String fileName, UserManager userManager) throws IOException, ClassNotFoundException {
        UserReadWrite userRW = new UserReadWrite(fileName);
        userRW.saveToFile(fileName, userManager);
    }
}
