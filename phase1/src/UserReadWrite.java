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
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


/**
 * Manages the saving and loading of User.
 */
public class UserReadWrite implements Serializable {

    // All instances of UserReadWrite will share one Logger, so it's
    // static.
    // We use this name as the name of our Logger object.
    // Using the class class is called "reflection".
    private static final Logger logger = Logger.getLogger(UserReadWrite.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /**
     * A List containing all users.
     */
    private List<User> users;

    /**
     * Creates a new empty UserReadWrite.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public UserReadWrite(String filePath) throws ClassNotFoundException, IOException {
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
     * Stores the users from the file at path filePath.
     *
     * @param filepath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */

    public void readFromFile(String filepath) throws ClassNotFoundException, IOException {

        try {
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the list
            List temp = (List<User>) input.readObject();
            input.close();
            // as long as the file is not empty, it will populate users with the list stored in the file
            if (temp != null){
                users = temp;
            }
            else{
                return;
            }
        } catch (
                IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

        /**
     * Adds record to this UserReadWrite.
     *
     * @param record a record to be added.
     */
    public void add(User record) {
        users.add(record);

        // Log the addition of a student.
        logger.log(Level.INFO, "Added a new user " + record.toString());
    }

    /**
     * Writes the users from UserManager u to file at filePath.
     *
     * @param filePath the file to write the records to
     * @throws IOException
     */
    public void saveToFile(String filePath, UserManager u) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the list of all Users in UserManager u
        output.writeObject(u.allUsers);
        output.close();
    }

    /**
     * Populates the UserManager of the system.
     *
     * @param um the UserManager that is to be populated.
     */
    public void populateUserManager(UserManager um) {
        if(users != null){
            um.allUsers = users;
        }
    }
}

