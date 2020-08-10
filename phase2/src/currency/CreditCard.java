package currency;

import accounts.users.User;

import java.util.Calendar;

/**
 * Constructs a currency.CreditCard, that is used so a accounts.users.User can upload cash for in-app currency, so they can request a transactions.OneWayMonetized requests.TradeRequest.
 */
public class CreditCard {

    private long cardNumber;
    private String cardName;
    private Calendar expiration;
    private User cardHolder;
    private int CVV;

    /**
     * Constructs a credit card object. The constructor requires all relevant details about the card, in order to charge them.
     * @param cardNumber a long of the accounts.users.User's currency.CreditCard's number
     * @param cardName a String of the accounts.users.User's name on the currency.CreditCard
     * @param expiration a Calender of the expiration date of the Card
     * @param cardHolder the accounts.users.User who currently holds the currency.CreditCard
     * @param CVV an int of the CVV of the currency.CreditCard
     */
    public CreditCard(long cardNumber, String cardName, Calendar expiration, User cardHolder, int CVV ){
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.expiration = expiration;
        this.cardHolder = cardHolder;
        this.CVV = CVV;
    }

    /**
     * Gets the expiration date of the card
     * @return Calender of the expiration date of the card
     */

    public boolean checkExpiration(){
        Calendar today = Calendar.getInstance();
        return expiration.after(today);
    }

    /**
     * Gets the last four cardnumbers of the given currency.CreditCard.
     * @return int of the last four numbers of the currency.CreditCard.
     */
    public String returnEndNumbers(){
        StringBuilder end = new StringBuilder();
        String all = Long.toString(this.cardNumber);

        for(int i = all.length() - 5; i < all.length(); i++){
            end.append(all.charAt(i));

        }

        return end.toString();
    }

    /**
     * Gets the expiration date of the given currency.CreditCard.
     * @return String of the expiration date
     */
    public Calendar getExpiration(){
        return this.expiration;
    }

    /**
     * Gets the entire card number of a given currency.CreditCard
     * @return long of the card number
     */
    public Long getCardNumber(){
        return this.cardNumber;
    }

}
