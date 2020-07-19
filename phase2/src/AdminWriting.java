import java.io.IOException;

// NOTE: This class is based off of DemoManager from logging in Week 6 Modules on Quercus.

/**
 * Class deals with the reading and writing the systems AdminManager class. Retrieves data
 * by calling gateway AdminReadWrite which handles the actual reading and writing of information from the external
 * file and send it back to the TradeSystem class which stores AdminManager.
 */
public class AdminWriting {
    /**
     * Deals with reading from the external file fileName and if it is not empty, it will return the AdminManager
     * object stored in fileName; if it is empty, it will create an initial Admin and return a new AdminManager
     *
     * @param fileName the name of the file the method is reading from
     * @return returns an AdminManager object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static AdminManager demoAdminRead(String fileName) throws IOException, ClassNotFoundException {
        // creates AdminReadWrite which manages the saving and loading users.
        AdminReadWrite adminRW = new AdminReadWrite(fileName);

        // Deserializes contents of the SER file
        AdminManager aM = adminRW.readFromFile(fileName);
        if (aM == null) {
            Admin initialAdmin = new Admin("Tina", "123456");
            return new AdminManager(initialAdmin);
        }
        return aM;
    }

    /**
     * Saves the admins from adminManager into the AdminList file.
     *
     * @param adminManager AdminManager object which stores all Admin information
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void demoAdminSave(String fileName, AdminManager adminManager) throws IOException, ClassNotFoundException {
        AdminReadWrite adminRW = new AdminReadWrite(fileName);
        adminRW.saveToFile(fileName, adminManager);
    }
}
