import java.io.*;

// NOTE: This class is based off of StudentManager from logging in Week 6 Modules on Quercus.

/**
 * Gateway class that manages the actual saving and loading of Admins to the external file.
 */
public class AdminReadWrite implements Serializable {

    /**
     * Creates a new empty AdminReadWrite. Checks to see if file already exists; if it doesn't, it will
     * create a new file with the name fileName. If it does exist, it will read from the file.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public AdminReadWrite(String fileName) throws ClassNotFoundException, IOException {
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
     * Stores the accounts.admins from the file at path filePath.
     *
     * @param fileName the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */

    public AdminManager readFromFile(String fileName) throws ClassNotFoundException {
        File findFile = new File(fileName);
        String filepath = findFile.getAbsolutePath();
        try {
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the list in the file by reading
            AdminManager temp = (AdminManager) input.readObject();
            //closes the file
            input.close();
            // as long as the file is not empty, it will populate accounts.admins with the list stored in the file
            if (temp != null) {
                return temp;
            }
        } catch (
                IOException ex) {
        }
        return null;
    }

    /**
     * Writes adminManager object to external file at filePath.
     *
     * @param fileName the file to write the records to
     * @throws IOException
     */
    public void saveToFile(String fileName, AdminManager adminManager) throws IOException {
        File findFile = new File(fileName);
        String filePath = findFile.getAbsolutePath();
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize AdminManager adminManager
        output.writeObject(adminManager);
        output.close();
    }
}

