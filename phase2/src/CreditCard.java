import java.util.Calendar;
public class CreditCard {

    private int cardNumber;
    private String cardName;
    private Calendar expiration;
    private User cardHolder;
    private int CVV;


    public CreditCard(int cardNumber, String cardName, Calendar expiration, String currency, User cardHolder, int CVV ){
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.expiration = expiration;
        this.cardHolder = cardHolder;
        this.CVV = CVV;
    }

    public boolean checkExpiration(){
        Calendar today = Calendar.getInstance();
        return expiration.after(today);
    }

}
