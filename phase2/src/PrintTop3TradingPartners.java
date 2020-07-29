import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PrintTop3TradingPartners implements userMainMenuOptions {

    /**
     * Prints out the User user's top 3 trading partners.
     *
     * @param user the User who is requesting to see their top 3 trading partners
     * @return returns a User so that user gets returned to the main menu
     */
    public User execute(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests,
                        UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                        AdminInputGetter admininputgetter, AdminManager allAdmins, Logger undoLogger) {
        List<User> top3TP = new ArrayList<>();
        top3TP = user.getTopTradingPartners();
        System.out.print("Here are your top 3 most frequent trading partners:\n");
        if (top3TP.size() == 0) {
            System.out.print("\uD83D\uDE25 Sorry! You have no trading partners! You should do some trades!\n");
        }
        for (int i = 0; i < top3TP.size(); i++) {
            System.out.print("\uD83D\uDC51" + (i + 1) + ". " + top3TP.get(i).getName() + "\n");

        }
        return user;
    }

}
