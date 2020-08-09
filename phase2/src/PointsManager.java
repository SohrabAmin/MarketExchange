import java.util.Scanner;
import java.util.logging.Logger;

public class PointsManager implements userMainMenuOptions{
    Scanner scanner = new Scanner(System.in);

    @Override
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests, UserManager allUsers,
                          MeetingManager allMeetings, TransactionManager allTransactions, AdminManager allAdmins,
                          Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        System.out.println("Your points: " + user.getPoints());
        System.out.println("Press 1 to go back");
        String confirmation = scanner.nextLine();
        if (confirmation.equals("1")) {
            return user;
        }
        else {
            System.out.println("Invalid Input Please Try Again");
            return null;
        }
    }
}
