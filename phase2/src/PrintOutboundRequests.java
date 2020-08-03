import java.util.List;
import java.util.logging.Logger;

public class PrintOutboundRequests implements userMainMenuOptions {

    /**
     * Prints a User's outbound TradeRequests
     *
     * @param user User whose outbound TradeRequests are to be printed
     * @return null if the current menu is to be reprinted; User user if the user is to be redirected to the main menu;
     * String "exit" if the user is to be logged out.
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages) {
        System.out.print("Here is the status of your pending outbound requests:\n");
        List<TradeRequest> outbound = user.getOutboundRequests();

        if (outbound.size() == 0) {
            System.out.print("You do not have any pending outbound requests!\n");
            return user;
        }
//        for (int i = 0; i < outbound.size(); i++) {
//            String extension = " ";
//            if (outbound.get(i).getRequestType() == 2) {
//                extension = " in exchange for: " + outbound.get(i).getRequesterItem().getName();
//            }
//            System.out.print((i + 1) + " . You requested for : " + outbound.get(i).getReceiverItem().getName() + extension + "\n");
//        }
        return user;
    }

}
