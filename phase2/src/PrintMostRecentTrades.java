import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PrintMostRecentTrades implements userMainMenuOptions{

    /**
     * Prints out the User user's most recent trades.
     *
     * @param user     User that is requesting to see their most recent trades
     * @param allUsers UserManager which stores all Users in the system
     * @return returns a User so that user gets returned to the main menu
     */
    public User execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                        UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                        AdminManager allAdmins, Logger undoLogger) {
        List<Item> MostRecent = new ArrayList<>();
        MostRecent = allUsers.getRecentlyTradedItems(user);
        System.out.print("Here are your most recently traded-away items:\n");
        if (MostRecent.size() == 0) {
            System.out.print("\uD83D\uDE25 Sorry! You have no recent trades! You should do some trades!\n");
        }
        for (int i = 0; i < MostRecent.size(); i++) {
            System.out.print("\uD83D\uDC51" + (i + 1) + ". " + MostRecent.get(i).getName() + " : " +
                    MostRecent.get(i).getDescription() + "\n");
        }
        return user;
    }

}
