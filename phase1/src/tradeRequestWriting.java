import java.io.IOException;

// NOTE: This class is based off of DemoManager from logging in Week 6 Modules on Quercus.

/**
 * Class deals with the reading and writing the systems AdminManager class. Retrieves data
 * by calling gateway AdminReadWrite which handles the actual reading and writing of information from the external
 * file and send it back to the TradeSystem class which stores AdminManager.
 */
public class tradeRequestWriting {

    public static TradeRequestManager demoTradeRequestRead(String fileName) throws IOException, ClassNotFoundException {
        // creates transactionReadWrite which manages the saving and loading users.
        tradeRequestReadWrite trRW = new tradeRequestReadWrite(fileName);

        // Deserializes contents of the SER file
        TradeRequestManager temp = trRW.readFromFile(fileName);
        if (temp == null){
            return new TradeRequestManager();
        }
        return temp;
    }

    /**
     *  Saves the TradeRequestManager trM into the external file.
     *
     * @param trM current TradeRequestManager object that needs to be saved to the external file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void demoTradeRequestSave(String fileName, TradeRequestManager trM) throws IOException, ClassNotFoundException {
        tradeRequestReadWrite trRW = new tradeRequestReadWrite(fileName);
        trRW.saveToFile(fileName, trM);
    }
}
