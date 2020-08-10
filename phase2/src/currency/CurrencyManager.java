package currency;

import accounts.users.User;
import accounts.users.UserManager;
import transactions.OneWayMonetized;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that manages the in-app currency of a accounts.users.User.
 */
public class CurrencyManager {


    private Map<OneWayMonetized, Double> inProgressSale;

    /**
     * Always instantiated when the main method is initially called. Creates a HashMap that stores all the sales in progress, where the key
     * is the transactions.OneWayMonetized requests.TradeRequest, and value is the Double.
     */
    public CurrencyManager(){
        this.inProgressSale = new HashMap<>();
    }

    /**
     * Adds in-app currency to the accounts.users.User. It requires that the currency.CreditCard is not expired. It is assumed that the accounts.users.User always has enough to upload.
     * @param user The accounts.users.User that is uploading in-app currency
     * @param card The associated currency.CreditCard
     * @param amount The amount the accounts.users.User would like to upload
     * @return a boolean. True: The funds were successfully added. False: The card is expired)
     */
    public boolean addFunds(User user, CreditCard card, double amount){
        if(card.checkExpiration()){
            user.updateCapital(amount);
            return true;
        }
        return false;
    }

    /**
     * Takes the appropriate amount of in-app currency from user1, and stores it. When the transactions.Transaction has been confirmed, the amount will be deposited into
     * user2's account. If the transactions.Transaction has been cancelled, the currency will be returned to user1.
     * @param trade The given transactions.OneWayMonetized request.
     * @param userManager The instance of accounts.users.UserManager, to call getUser()
     */
    public void holdFunds(OneWayMonetized trade, UserManager userManager){
        this.inProgressSale.put(trade, trade.getCost());
        User user1 = userManager.getUser(trade.getFirstTrader());
        user1.updateCapital(-trade.getCost());
    }

    /**
     * Returns the "held" currency into user1's account.
     * @param trade The given transactions.OneWayMonetized request.
     * @param userManager The instance of accounts.users.UserManager, to call getUser()
     */
    public void reverseHold(OneWayMonetized trade, UserManager userManager){
        User user1 = userManager.getUser(trade.getFirstTrader());
        user1.updateCapital(trade.getCost());
        this.inProgressSale.remove(trade);
    }

    /**
     * Deposits the "held" funds into user2's account
     * @param trade The given transactions.OneWayMonetized request.
     * @param userManager The instance of accounts.users.UserManager, to call getUser()
     */
    public void completeSale(OneWayMonetized trade, UserManager userManager){
        User user2 = userManager.getUser(trade.getSecondTrader());
        user2.updateCapital(trade.getCost());
        this.inProgressSale.remove(trade);

    }

    /**
     * Come back to this
     * @param date
     * @return
     */
    public Calendar getDate(String date) {
        Calendar cal = Calendar.getInstance();
        String[] temp = date.split("-");
        cal.set(Integer.parseInt(temp[1]), Integer.parseInt(temp[0]), 30);
        return cal;
    }

    public String dateString(Calendar date) {

        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        return dateFormat.format(date.getTime());
    }
}