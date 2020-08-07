import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.Calendar;

public class ManagePaymentOptions implements userMainMenuOptions {

    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {

        System.out.println("Your current funds: $" + user.getCapital() + "\n");

        if(user.getCreditCards().size() == 0){
            System.out.println("You do not currently have a card on your account. Press 1 to add a new card and 2 to return to the main menu.");
            Scanner sc = new Scanner(System.in);
            String inum = sc.nextLine();
            if(inum.equals("1")){
                this.addANewCard(sc, currencyManager, user);
            }
            return user;
        }
        System.out.println("Your default credit ends in ***" + user.getDefaultCreditCard().returnEndNumbers() + "\n");
        System.out.println("Press 1 to add a new card, press 2 to remove an existing card, 3 to add funds or 4 to change your default credit card. " +
                "Note that funds can only be added from your default credit card.");

        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        String inum = sc.nextLine();



        if(inum.equals("1")){
            this.addANewCard(sc, currencyManager, user);
            return user;
        } else if(inum.equals("2")){
            List<CreditCard> cards = user.getCreditCards();
            for(int i = 0; i < cards.size(); i++){
                System.out.println((i + 1) + ". Card ending in "+ cards.get(i).returnEndNumbers() + ". " +
                        "This cards expires on " + cards.get(i).getExpiration());
            }
            System.out.println("Here are your existing cards, select the associated list number to remove a card.\n");
            inum = sc.nextLine();
            int index = Integer.parseInt(inum) - 1;
            CreditCard remove = user.getCreditCards().get(index);
            if(remove.getCardNumber().equals(user.getDefaultCreditCard().getCardNumber())){
                if(user.getCreditCards().size() == 0){
                    user.getCreditCards().remove(remove);
                    System.out.println("The selected card has been removed. You have no other cards on your account. \n");
                }else {
                    user.getCreditCards().remove(remove);
                    user.setDefaultCreditCard(user.getCreditCards().get(0));
                    System.out.println("The selected card has been removed and another card is set as your default.\n");
                }
            }
            return user;
        }else if(inum.equals("3")){
            if(user.getCapital() >= 5000){
                System.out.println("Sorry, your funds are maximized at $5000!.");
            }else{
                System.out.println("Current account balance: $" + user.getCapital()+ "\n");
                System.out.println("Your current default credit card (ending in ***" +
                        user.getDefaultCreditCard().returnEndNumbers()+ ") will be charged."
                        +" Please enter the amount you would like added to your account. \n");
                inum = sc.nextLine();
                double amount = Integer.parseInt(inum);
                double total = amount + user.getCapital();


                if(total > 5000){
                    double amount2 = 5000 - user.getCapital();
                    boolean check = currencyManager.addFunds(user, user.getDefaultCreditCard(), amount2);
                    if(check){
                        System.out.println("The requested amount exceeds the $5000 limit. Your card was instead charged" +
                                "$" + amount2 + ". Your new account balance is $5000 \n");
                    }else{
                        System.out.println("Sorry, your card is expired. Please navigate back and select a new default credit card.");
                    }
                    return user;
                }else{
                    boolean check = currencyManager.addFunds(user, user.getDefaultCreditCard(), amount);
                    if(check){
                        System.out.println("Your funds have been deposited into your account. Your new balance is $"+ user.getCapital() + "\n");
                    }else{
                        System.out.println("Sorry, your card is expired. Please navigate back and select a new default credit card.");
                    }
                    return user;
                }
            }
        }
        return user;
    }


    public void addANewCard(Scanner sc1, CurrencyManager currencyManager, User user){

        Scanner sc = new Scanner(System.in);

        int temp1 = 0;
        String temp2;
        Calendar temp3;
        int temp4;
        boolean check = true;
        Object inum;
        while(check){
            System.out.println("Please enter your 16 digit credit card number. \n");
            inum = sc.nextLine();
            if(((String) inum).length() != 16){
                System.out.println("Incorrect input, please try again! \n");
            } else{
                inum = Integer.parseInt((String) inum);
                temp1 = (int) inum;
                check = false;
            }
        }
        System.out.println("Please enter the name of the card holder \n");
        inum = sc.nextLine();
        temp2 = (String) inum;
        System.out.println("Please enter the expiration date of your card in the format mm-yy. \n");
        inum = sc.nextLine();
        temp3 = currencyManager.getDate((String) inum);
        System.out.println("Please enter the 3 digit CVV on the back of your card. \n");
        inum = sc.nextLine();
        temp4 = Integer.parseInt((String) inum);
        CreditCard card = new CreditCard(temp1, temp2, temp3, user, temp4);
        user.addCreditCard(card);
        if(user.getCreditCards().size() == 1){
            user.setDefaultCreditCard(card);
            System.out.println("Congratulations, you have added a new card to your account. Note that this card is" +
                    " automatically set as your default.\n");
        }else{
            System.out.println("Congratulations, you have added a new card to your account. Note that this has " +
                    "not changed your default card. \n");
        }
    }
}