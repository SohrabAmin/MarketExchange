import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VacationPrompter implements userMainMenuOptions {
    Scanner scanner = new Scanner(System.in);

    /**
     * Tells a User that their account status will be changed to "on vacation". If they confirm, this method removes
     * all Items from this User's inventory. If they cancel, User is returned to the main menu.
     * <p>
     * Note: removed inventory Items are logged so they can be restored when this User returns from vacation.
     *
     * @param user             User currently logged into the system
     * @param allItems         The instance of ItemManager
     * @param allTradeRequests The instance of TradeRequestManager
     * @param allUsers         The instance of UserManager
     * @param allMeetings      The instance of MeetingManager
     * @param allTransactions  The instance of TransactionManager
     * @param allAdmins        The instance of AdminManager
     * @param undoLogger       Logger that logs actions in the system
     * @return null if the current menu is to be reprinted; User user if the user is to be redirected to the main menu;
     * String "exit" if the user is to be logged out.
     */
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
        } else if (confirmation.equals("2")) {
            return user;
        } else {
            System.out.println("Invalid input. Please try again.");
            return null;
        }
    }

}
