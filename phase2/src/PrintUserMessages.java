import java.util.logging.Logger;

public class PrintUserMessages implements userMainMenuOptions {

    /**
     * Prints the messages that User user has. Messages include Trade Requests and Meeting requests.
     *  @param user     the User who wishes to view their messages
     * @param allUsers UserManager which stores User user
     * @param currencyManager
     * @return
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        //no error checking needed!
        System.out.print("\nHere are your pending Trades: \n");
        User Person = allUsers.getUser(user);
        if (Person.getPendingRequests().size() == 0) {
            System.out.print("\nYou have no pending requests.\n");
            return user;
        } else {




        }
        return user;
    }

}
