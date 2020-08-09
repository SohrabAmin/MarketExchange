import java.util.Scanner;
import java.util.logging.Logger;

public class PointsManager implements userMainMenuOptions{
    Scanner scanner = new Scanner(System.in);

    @Override
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests, UserManager allUsers,
                          MeetingManager allMeetings, TransactionManager allTransactions, AdminManager allAdmins,
                          Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        System.out.println("Your points: " + user.getPoints());
        if (!(user.getIsVIP()) && user.getPoints() >= 20) {
            System.out.println("Hey " + user.getName() + ", you're eligible to become a \u2B50 VIP \u2B50 \npress 1 " +
                    "to use 20 of your " + user.getPoints() + " points to upgrade to VIP status");
        }
        System.out.println("Press 2 to go back to main menu");
        String chosenOption = scanner.nextLine();
        if (chosenOption.equals("1")) {
            allUsers.setUserVIP(user);
            System.out.println("Congrats, you're a \u2B50 VIP \u2B50");
            return null;
        }
        else if (chosenOption.equals("2")) {
            return user;
        }
        else {
            System.out.println("Invalid Input Please Try Again");
            return null;
        }
    }
}
