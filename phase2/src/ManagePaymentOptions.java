import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.Calendar;

public class ManagePaymentOptions implements userMainMenuOptions {

    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {

        System.out.println("Your current funds: $" + user.getCapital() + "\n");
        System.out.println("Your default credit ends in ***" + user.getDefaultCreditCard().returnEndNumbers() + "\n");
        System.out.println("Press 1 to add a new card, press 2 to remove an existing card, 3 to add funds or 4 to change your default credit card. " +
                "Note that funds can only be added from your default credit card.");

        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        String inum = sc.nextLine();

        int temp1;
        String temp2;
        Calendar temp3;
        int temp4;
        if(inum.equals("1")){
            boolean check = true;
            while(check){
                System.out.println("Please enter your 16 digit credit card number. \n");
                inum = sc.nextLine();
                if(inum.length() != 16){
                    System.out.println("Incorrect input, please try again! \n"); //please remove prior to submission
                } else{
                    temp1 = Integer.parseInt(inum);
                    check = false;
                }

            }
            check = true;
            System.out.println("Please enter the name of the card holder \n");
            inum = sc.nextLine();
            temp2 = inum;
            System.out.println("Please enter the expiration date of your card in the format mm-yy. \n");
            inum = sc.nextLine();
            temp3 = currencyManager.getDate(inum);

        }
        return user;
    }


    public boolean dateValidate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}