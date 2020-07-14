import java.io.IOException;

// NOTE: This class is based off of DemoManager from logging in Week 6 Modules on Quercus.

/**
 * Class deals with the reading, writing and populating the variables in the systems TransactionManager class based on
 * information from an external file.
 */
public class transactionWriting {

    public static TransactionManager demoTransactionRead(String fileName) throws IOException, ClassNotFoundException {
        // creates transactionReadWrite which manages the saving and loading of TransactionManager.
        transactionReadWrite tmRW = new transactionReadWrite(fileName);

        // Deserializes contents of the SER file
        TransactionManager temp = tmRW.readFromFile(fileName);
        if (temp == null){
            return new TransactionManager();
        }
        return temp;
    }

    /**
     *  Saves the TransactionManager tm into the external file.
     *
     * @param tm current TransactionManager object that needs to be saved to the external file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void demoTransactionSave(String fileName, TransactionManager tm) throws IOException, ClassNotFoundException {
        transactionReadWrite tmRW = new transactionReadWrite(fileName);
        tmRW.saveToFile(fileName, tm);
    }
}
