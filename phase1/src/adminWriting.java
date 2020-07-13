import java.io.IOException;
import java.util.List;

// NOTE: This class is based off of DemoManager from logging in Week 6 Modules on Quercus.

/**
 * Class deals with the reading, writing and populating of Users in the systems UserManager class based on
 * external an inputted external file.
 */
public class adminWriting {

    public static AdminManager demoAdminRead(String fileName) throws IOException, ClassNotFoundException {
        // creates AdminReadWrite which manages the saving and loading users.
        AdminReadWrite adminRW = new AdminReadWrite(fileName);

        // Deserializes contents of the SER file
        Object aM = adminRW.readFromFile(fileName);
        if (aM == null) {
            Admin initialAdmin = new Admin("Tina", "123456");
            return new AdminManager(initialAdmin);
        }
        return (AdminManager) aM;
    }


    /**
     *  Saves the admins from adminManager into the AdminList file.
     *
     * @param adminManager
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void demoAdminSave(String fileName, AdminManager adminManager) throws IOException, ClassNotFoundException {
        AdminReadWrite adminRW = new AdminReadWrite(fileName);
        adminRW.saveToFile(fileName, adminManager);
    }
}
