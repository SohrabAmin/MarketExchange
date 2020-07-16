import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;


// NOTE: This class is based off of StudentManager from logging in Week 6 Modules on Quercus.

/**
 * Gateway class that manages the actual saving and loading of items to the external file.
 */
public class ItemReadWrite implements Serializable {
    /**
     * A List containing all items.
     */
    private List<Item> items;

    /**
     * Creates a new empty ItemReadWrite. Checks to see if file already exists; if it doesn't, it will
     * create a new file with the name fileName. If it does exist, it will read from the file.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ItemReadWrite(String fileName) throws ClassNotFoundException, IOException {
        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(fileName);
        String filePath = file.getAbsolutePath();
        File theFile = new File(filePath);
        if (theFile.exists()) {
            readFromFile(filePath);
        } else {
            theFile.createNewFile();
        }
    }

    /**
     * Stores the items from the file at path filePath.
     *
     * @param fileName the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */

    public void readFromFile(String fileName) throws ClassNotFoundException {
        File findFile = new File(fileName);
        String filepath = findFile.getAbsolutePath();
        try {
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the list in the file by reading
            List temp = (List<Item>) input.readObject();
            //closes the file
            input.close();
            // as long as the file is not empty, it will populate users with the list stored in the file
            if (temp != null){
                items = temp;
            }
        } catch (IOException ex) {
        }
    }

    /**
     * Writes the items from ItemManager itemManager to file at filePath.
     *
     * @param fileName the file to write the records to
     * @param itemManager the ItemManager object which stores the systemInventory
     * @throws IOException
     */
    public void saveToFile(String fileName, ItemManager itemManager) throws IOException {
        File findFile = new File(fileName);
        String filePath = findFile.getAbsolutePath();
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the list of all Users in UserManager userManager
        output.writeObject(itemManager.getSystemInventory());
        output.close();
    }

    /**
     * Populates the ItemManager of the system.
     *
     * @param im the itemManager that is to be populated.
     */
    public void populateUserManager(ItemManager im) {
        // As long as items is not null and contains a list of items from the file
        // (i.e. file is not empty), then populate the ItemManager of the system.
        if(items != null){
            im.setSystemInventory(items);
        }
    }
}

