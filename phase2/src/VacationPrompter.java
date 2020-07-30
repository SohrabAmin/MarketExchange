import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VacationPrompter implements userMainMenuOptions {
    Scanner scanner = new Scanner(System.in);

    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests, UserManager allUsers,
                          MeetingManager allMeetings, TransactionManager allTransactions, AdminManager allAdmins,
                          Logger undoLogger) {
        System.out.println("Your account will be taken down for the duration of your vacation. \n" +
                "You will not be able to trade until you return from your vacation. \n" +
                "Press 1 to confirm or 2 to cancel.");
        String confirmation = scanner.nextLine();
        if (confirmation.equals("1")) {
            System.out.println("Enjoy your vacation!");
            user.setIsOnVacation(true);
            for (Item indexedItem : user.getInventory()) {
                user.getInventory().remove(indexedItem);
                undoLogger.log(Level.INFO, "User " + user.getName() + " went on vacation; " +
                        "removed Item " + indexedItem.getName() +
                        " from " + user.getName() + "'s inventory.\n");
            }
            return "exit";
        }
        else if (confirmation.equals("2")) {
            return user;
        }
        else {
            System.out.println("Invalid input. Please try again.");
            return null;
        }
    }

}
