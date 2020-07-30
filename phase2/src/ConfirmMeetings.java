import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ConfirmMeetings implements userMainMenuOptions {

    /**
     * Displays the pending transactions that the User user has. It will allow users to approve or edit the transactions
     * they currently have pending.
     *
     * @param user            the User that wants to view their pending transactions
     * @param allItems        ItemManager that stores the system's inventory
     * @param allUsers        UserManager that stores all the Users in the system
     * @param allMeetings     MeetingManager that deals with the creation of meetings
     * @param allTransactions TransactionManager that stores all the information of all system transactions
     * @return depending on what the User inputs it will return different objects:
     * returns null to tell mainmenu() to call PendingTransactionProcess() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger) {
        System.out.print("Please select 1 for all your initial pending meetings and 2 for all return meetings\n");
        Scanner sc4 = new Scanner(System.in);    //System.in is a standard input stream
        String selection = sc4.nextLine();

        if (selection.equals("1")) {
            List<Transaction> userTransactions = new ArrayList<>();
            userTransactions = user.getAgreedUponMeeting();
            if (userTransactions.size() == 0) {
                System.out.print("No initial pending trade for you to confirm!\n");
                return user;
            }
            System.out.print("Here are your pending meetings ready to be confirmed!\n");
            //prints the pending meetings
            for (int i = 0; i < userTransactions.size(); i++) {
                String otherSide = "";
                Integer confirmed = userTransactions.get(i).getInitialMeeting().userconfirmed(user.getName());
                String status = "";
                if (confirmed == 1) {
                    status = " [CONFIRMED BY YOU] ";
                }
                System.out.print((i + 1) + " . " + userTransactions.get(i).getInitialMeeting() + " With: " + userTransactions.get(i).getInitialMeeting().getOtherSide(user.getName()) + status + "\n");
            }
            System.out.print("Please enter the ID of the meeting you would like to confirm.\n");
            Scanner sc11 = new Scanner(System.in);
            int meetingIndex = (Integer.parseInt(sc11.nextLine())) - 1;
            Transaction selectedTransaction = userTransactions.get(meetingIndex);
            System.out.print("You have selected:\n");
            Integer confirmed = selectedTransaction.getInitialMeeting().userconfirmed(user.getName());
            if (confirmed == 1) {
                System.out.print("You have already confirmed this meeting!\n");
                return user;
            }
            System.out.print(selectedTransaction.getInitialMeeting() + " With: " + selectedTransaction.getInitialMeeting().getOtherSide(user.getName()) + "\n");
            System.out.print("Press 1 to confirm that the meeting is done! Press 2 to cancel the meeting and press 3 if you got stood up\n");


            String action = sc11.nextLine();
            if (action.equals("1")) {
                //confirm meeting by the user
                selectedTransaction.getInitialMeeting().meetingConfirmed(user.getName());

                System.out.print("Confirmed that the meeting occurred on " + selectedTransaction.getInitialMeeting() + "\n");

                //lets check if both people have confirmed meeting
                if (selectedTransaction.getInitialMeeting().confirmedByBothSides()) {
                    //looks like the meeting was confirmed by both parties!
                    System.out.print("\uD83E\uDD29 Looks like the meeting was confirmed by both sides!\n ");

                    if (!selectedTransaction.getTemp()) { //if it was a permenant transaction
                        allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 3);

                    } else if (selectedTransaction.getTemp()) {
                        //if it was a temporary meeting, then I need to set up a second meeting
                        allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 2);
                        //by now, the second agreed upon meeting is set for both users
                        Calendar date = selectedTransaction.getInitialMeeting().getDate();
                        date.add(Calendar.MONTH, 1);
                        Meeting returnMeeting = new Meeting(date, selectedTransaction.getInitialMeeting().getPlace());
                        returnMeeting.initialconfirm(user.getName(), selectedTransaction.getInitialMeeting().getOtherSide(user.getName()));
                        System.out.print("REMINDER: You need to return the borrowed item(s) back by " + returnMeeting.toString() + "\n");
                        //need to add return meeting to transactions
                        allTransactions.setFinalMeeting(selectedTransaction, returnMeeting);

                    }
                }
            } else if (selection.equals("2") || selection.equals("3")) { //cancelling
                System.out.print("\u2639 We are sorry to hear that! Better luck next time!\n");
                allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 4);
            }
        } else if (selection.equals("2")) {
            System.out.print("Here are your meetings to return items:\n");
            List<Transaction> userTransactions = new ArrayList<>();
            userTransactions = user.getSecondAgreedUponMeeting();

            if (userTransactions.size() == 0) {
                System.out.print("No pending trade for you to confirm!\n");
                return user;
            }
            System.out.print("Here are your return pending meetings ready to be confirmed!\n");
            //prints the pending meetings
            for (int i = 0; i < userTransactions.size(); i++) {
                String otherSide = "";
                Integer confirmed = userTransactions.get(i).getReturnMeeting().userconfirmed(user.getName());
                String status = "";
                if (confirmed == 1) {
                    status = " [CONFIRMED BY YOU] ";
                }
                System.out.print(Integer.toString(i + 1) + " . " + userTransactions.get(i).getInitialMeeting() + " With: " + userTransactions.get(i).getInitialMeeting().getOtherSide(user.getName()) + status + "\n");
            }
            System.out.print("Please enter the ID of the meeting you would like to confirm.\n");
            Scanner sc11 = new Scanner(System.in);
            int meetingIndex = (Integer.parseInt(sc11.nextLine())) - 1;
            Transaction selectedTransaction = userTransactions.get(meetingIndex);
            System.out.print("You have selected:\n");
            Integer confirmed = selectedTransaction.getInitialMeeting().userconfirmed(user.getName());
//
            System.out.print(selectedTransaction.getInitialMeeting() + " With: " + selectedTransaction.getInitialMeeting().getOtherSide(user.getName()) + "\n");
            System.out.print("Press 1 to confirm that the meeting is done!\n");
            String action = sc11.nextLine();
            if (action.equals("1")) {
                //confirm meeting by the user
                selectedTransaction.getReturnMeeting().meetingConfirmed(user.getName());

                System.out.print("Confirmed that the meeting occurred on " + selectedTransaction.getReturnMeeting() + "\n");

                //lets check if both people have confirmed meeting
                if (selectedTransaction.getReturnMeeting().confirmedByBothSides()) {
                    //looks like the meeting was confirmed by both parties!
                    System.out.print("\uD83E\uDD29 Looks like the meeting was confirmed by both sides!\n ");
                    allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 3);
                }
            }
        }
        return user;
    }

}
