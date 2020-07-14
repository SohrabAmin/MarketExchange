import java.io.IOException;

// NOTE: This class is based off of DemoManager from logging in Week 6 Modules on Quercus.

/**
 * Class deals with the reading and writing the systems AdminManager class. Retrieves data
 * by calling gateway AdminReadWrite which handles the actual reading and writing of information from the external
 * file and send it back to the TradeSystem class which stores AdminManager.
 */
public class tradeRequestWriting {

    public static TransactionManager demoTransactionRead(String fileName) throws IOException, ClassNotFoundException {
        // creates transactionReadWrite which manages the saving and loading users.
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
     * @param tm TransactionManager
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void demoTransactionSave(String fileName, TransactionManager tm) throws IOException, ClassNotFoundException {
        transactionReadWrite tmRW = new transactionReadWrite(fileName);
        tmRW.saveToFile(fileName, tm);
    }
}
