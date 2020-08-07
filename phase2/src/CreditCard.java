import java.util.Calendar;
public class CreditCard {

    private Integer cardNumber;
    private String cardName;
    private Calendar expiration;
    private User cardHolder;
    private int CVV;


    public CreditCard(int cardNumber, String cardName, Calendar expiration, User cardHolder, int CVV ){
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

    public String returnEndNumbers(){
        StringBuilder end = new StringBuilder();
        String all = this.cardNumber.toString();

        for(int i = all.length() - 5; i < all.length(); i++){
            end.append(all.charAt(i));

        }

        return end.toString();
    }

    public String getExpiration(){
        return this.expiration.toString();
    }

    public Integer getCardNumber(){
        return this.cardNumber;
    }

}
