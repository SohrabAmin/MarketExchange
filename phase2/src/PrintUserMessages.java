import java.util.logging.Logger;

public class PrintUserMessages implements userMainMenuOptions {

    /**
     * Prints the messages that User user has. Messages include Trade Requests and Meeting requests.
     *  @param user     the User who wishes to view their messages
     * @param allUsers UserManager which stores User user
     * @return
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger) {
        //no error checking needed!
        System.out.print("\nHere are your pending Trades: \n");
        User Person = allUsers.getUser(user);
        if (Person.getPendingRequests().size() == 0) {
            System.out.print("\nYou have no pending requests.\n");
            return user;
        } else {
//            for (int i = 0; i < Person.getPendingRequests().size(); i++) {
//                String ext = "";
//                //RequesterItem : Item of the requester
//                //ReceiverItem: Item of the receiver
//
//                if (Person.getPendingRequests().get(i).getRequesterItem() != null) {
//                    ext = " With your item " + Person.getPendingRequests().get(i).getRequesterItem().getName();
//                }
//                System.out.print("\uD83E\uDD1D" + Person.getPendingRequests().get(i).getRequester().getName() +
//                        " is requesting a trade for item: "
//                        + Person.getPendingRequests().get(i).getReceiverItem().getName() + " With Message: " +
//                        Person.getPendingRequests().get(i).getMessage() + ext + "\n");
//            }
        }
        return user;
    }

}
