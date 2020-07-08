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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


/**
 * Manages the saving and loading of User.
 */
public class UserReadWrite implements Serializable {

    // All instances of StudentManager will share one Logger, so it's
    // static.
    // We use StudentManager.class.getName() to get the fully-qualified
    // name of this class, which happens to be "managers.StudentManager".
    // We use this name as the name of our Logger object.
    // Using the class class is called "reflection".
    private static final Logger logger = Logger.getLogger(UserReadWrite.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** A mapping of username to Users. */
    private Map<String, User> users;

    /**
     * Creates a new empty StudentManager.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public UserReadWrite(String filePath) throws ClassNotFoundException, IOException {
        users = new HashMap<String, User>();

        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(filePath);
        if (file.exists()) {
            readFromFile(filePath);
        } else {
            file.createNewFile();
        }
    }

    /**
     * Populates the records map from the file at path filePath.
     *
     * @param filepath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */

    public void readFromFile(String filepath) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            users = (Map<String, User>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

    /**
     * Adds record to this UserReadWrite.
     *
     * @param record a record to be added.
     */
    public void add(User record) {
        users.put(record.getName(), record);

        // Log the addition of a student.
        logger.log(Level.INFO, "Added a new user " + record.toString());
    }

    /**
     * Writes the users to file at filePath.
     *
     * @param filePath the file to write the records to
     * @throws IOException
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(users);
        output.close();
    }

    //@Override
    public String toString(UserManager input) {
        String result = "";
        UserManager nm = new UserManager();
        for (User r : users.values()) {
            for (int i = 0 ; i < r.getWishlist().size();i++){
                result += r.getWishlist().get(i).getName() + "\n";
                input.allUsers.add(r);
            }
        }
        return result;
    }
}

