import java.util.Scanner;
import java.util.logging.Logger;

public class VacationUserMainMenu implements DifferentUserMainMenu {

    public Object mainMenu(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                            UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                            AdminManager allAdmins, Logger undoLogger) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + user.getName() + "!\n");
        System.out.println("Are you still on vacation? If so, press 1. If not, press 2.");
        String confirmation = scanner.nextLine();
        if (confirmation.equals("1")) {
            System.out.println("Go enjoy your vacation!");
            return "exit";
        }
        else if (confirmation.equals("2")) {
            System.out.println("We hope you enjoyed your vacation!");
            user.setIsOnVacation(false);
            //TODO: use undoLogger to add items back to user's inventory
            return user;
        }
        else {
            System.out.println("Invalid option. Please try again.");
            return user;
        }
    }
}
