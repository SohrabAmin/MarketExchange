import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManager {


    private Map<OneWayMonetized, Double> inProgressSale;

    public CurrencyManager(){
        this.inProgressSale = new HashMap<>();
    }

    public boolean addFunds(User user, CreditCard card, double amount){
        if(!card.checkExpiration()){
            user.updateCapital(amount);
            return true;
        }
        return false;
    }
    public void holdFunds(OneWayMonetized trade, UserManager userManager){
        this.inProgressSale.put(trade, trade.getCost());
        User user1 = userManager.getUser(trade.getFirstTrader());
        user1.updateCapital(-trade.getCost());
    }
    public void reverseHold(OneWayMonetized trade, UserManager userManager){
        User user1 = userManager.getUser(trade.getFirstTrader());
        user1.updateCapital(trade.getCost());
        this.inProgressSale.remove(trade);
    }
    public void completeSale(OneWayMonetized trade, UserManager userManager){
        User user2 = userManager.getUser(trade.getSecondTrader());
        user2.updateCapital(trade.getCost());
        this.inProgressSale.remove(trade);

    }
    public Calendar getDate(String date) {
        Calendar cal = Calendar.getInstance();
        String[] temp = date.split("-");
        cal.set(Integer.parseInt(temp[1]), Integer.parseInt(temp[0]), 30);
        return cal;
    }
}
