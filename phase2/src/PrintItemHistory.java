import java.util.Map;
import java.util.logging.Logger;

public class PrintItemHistory implements userMainMenuOptions {

    /**
     * Allows the User to view their item history and it's status. It will display whether it is
     * "Pending", "Approved", or "Rejected".
     *
     * @param user     the User who is requesting to see their item history
     * @param allUsers UserManager that stores all the Users in the system
     * @return returns a User so that user gets returned to the main menu
     */
    public User execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                        UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                        AdminManager allAdmins, Logger undoLogger) {
        if (allUsers.getUser(user).getItemHistory().size() == 0) {
            System.out.println("\uD83D\uDE25 No items here! Please add an item to your inventory!");
            return user;

        }
        for (Map.Entry m : allUsers.getUser(user).getItemHistory().entrySet()) {
            Item object = (Item) m.getKey();
            System.out.println(object.getName() + ": " + m.getValue());
        }
        return user;
    }

}
