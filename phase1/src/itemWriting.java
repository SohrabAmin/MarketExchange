import java.io.File;
import java.io.IOException;

// NOTE: This class is based off of DemoManager from logging in Week 6 Modules on Quercus.

/**
 * Class deals with the reading, writing and populating of systemInventory in the systems ItemManager class based on
 * information from an external file.
 */
public class itemWriting {

    public static void demoItemRead(String fileName, ItemManager itemManager) throws IOException, ClassNotFoundException {
        // creates itemReadWrite which manages the saving and loading items in the inventory.
        itemReadWrite itemRW = new itemReadWrite(fileName);

        // Deserializes contents of the SER file
        itemRW.readFromFile(fileName);
        itemRW.populateUserManager(itemManager);
    }

    /**
     *  Saves the items from itemManager's system inventory into the external file.
     *
     * @param itemManager
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void demoItemSave(String fileName, ItemManager itemManager) throws IOException, ClassNotFoundException {
        itemReadWrite itemRW = new itemReadWrite(fileName);
        itemRW.saveToFile(fileName, itemManager);
    }
}
