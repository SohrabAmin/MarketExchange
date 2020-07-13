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
 * Manages the saving and loading of User to the external file.
 */
public class UserReadWrite implements Serializable {
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
    public UserReadWrite(String fileName) throws ClassNotFoundException, IOException {
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
     * Stores the users from the file at path filePath.
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
            List temp = (List<User>) input.readObject();
            //closes the file
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
        }
    }

    /**
     * Writes the users from UserManager u to file at filePath.
     *
     * @param fileName the file to write the records to
     * @throws IOException
     */
    public void saveToFile(String fileName, UserManager userManager) throws IOException {
        File findFile = new File(fileName);
        String filePath = findFile.getAbsolutePath();
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the list of all Users in UserManager userManager
        output.writeObject(userManager.getAllUsers());
        output.close();
    }

    /**
     * Populates the UserManager of the system.
     *
     * @param um the UserManager that is to be populated.
     */
    public void populateUserManager(UserManager um) {
        // As long as users is not null and contains a list of Users from the file
        // (i.e. file is not empty), then populate the UserManager of the system.
        if(users != null){
            um.setAllUsers(users);
        }
    }
}

