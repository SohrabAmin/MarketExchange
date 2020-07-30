import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PendingTransactionProcess implements userMainMenuOptions {

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

        List<Transaction> pendingTransactions = user.getPendingTrades();

        if (pendingTransactions.size() == 0) {
            System.out.print("You have no pending Transactions!\n");
            return user;
        }
        System.out.print("Here are your pending transactions:\n");

        //Show one way transactions
        for (int i = 0; i < pendingTransactions.size(); i++) {
            String hey = "";
            if (pendingTransactions.get(i) instanceof OneWay) {
                OneWay t = (OneWay) pendingTransactions.get(i);
                User b = t.getLender();
                if (user.getName().equals(b.getName())) {
                    b = t.getBorrower();
                }
                System.out.print((i + 1) + " . Item " + t.getLenderItem().getName() + " with " + b.getName() + "\n");
                System.out.print("[One Way] " + Integer.toString(i + 1) + " . Item " + t.getLenderItem().getName() +
                        " with " + b.getName() + "\n");
            }
            if (pendingTransactions.get(i) instanceof TwoWay) {
                TwoWay t = (TwoWay) pendingTransactions.get(i);
                User b = t.getFirstTrader(); //user b is the other party of this trade
                if (user.getName().equals(b.getName())) {
                    b = t.getSecondTrader();
                }
                System.out.print("[Two Way] " + Integer.toString(i + 1) + " . Item " + t.getFirstItem().getName() +
                        " with " + b.getName() + "\n");
            }
        }

        System.out.print("Select the transaction you would like to edit/approve\n");

        Scanner sc1 = new Scanner(System.in);
        Integer transactionID = Integer.parseInt(sc1.nextLine());
        if (transactionID > pendingTransactions.size()) {
            System.out.print("Oops! This is out of bound! Please try again \n");
            return user;
        }
        Transaction selectedT = pendingTransactions.get(transactionID - 1);

        if (selectedT instanceof OneWay) {

            OneWay tt = (OneWay) selectedT;
            User b = tt.getLender();
            if (user.getName().equals(b.getName())) {
                b = tt.getBorrower();
            }
            if (tt.getInitialMeeting().geteditHistory(user.getName()) > tt.getInitialMeeting().geteditHistory(b.getName()) || tt.getInitialMeeting().viewLastEdit().equals(user.getName())) {
                //if they have already made an edit and we are waiting on the other person to approve/suggest a new meeting
                System.out.print("This pending transaction is currently waiting on the other party! Please try again later\n");
            } else {
                //the person can now approve or propose a new time
                System.out.print("You have selected: ");
                System.out.print(tt.getLenderItem().getName() + " with " + b.getName() + "\n");
                System.out.print("Here is the proposed meeting: " + tt.getInitialMeeting() + "\n");
                System.out.print("Press 1 to approve. Press 2 to propose a new meeting. Press 3 to cancel\n");
                String input = sc1.nextLine();
                if (input.equals("1")) { //if they approve
                    //need another method for usermanager so that transactions in progress but meeting is set
                    System.out.print("Your meeting has been set!\n");
                    allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedT, 1);

                    undoLogger.log(Level.INFO, selectedT.toString());

                } else if (input.equals(("2"))) {
                    //they want to propose a new time
                    //provide warning if the is at their 3rd strike

                    //Daniel EDIT MEEEEEEE
                    if (tt.getInitialMeeting().geteditHistory(user.getName()) + 1 == allAdmins.getMeetingEditThreshold()) {
                        System.out.print("\u2622 This is the last time you can propose a meeting.\nIf rejected, this transaction will be cancelled\n");
                    }
                    //here is where the transaction gets cancelled because they couldnt make up their mind
                    if (tt.getInitialMeeting().geteditHistory(user.getName()) == allAdmins.getMeetingEditThreshold()) {
                        //one person reached 3 edits, its time to delete this transaction
                        allTransactions.handleCancelledTrade(allAdmins, allUsers, selectedT);
                        allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedT, 4);

                        undoLogger.log(Level.INFO, selectedT.toString());

                        System.out.print("\uD83D\uDE22 Sorry! You couldn't agree on a time so we deleted the transaction!\n" +
                                "Please try again!\n");
                        return user;
                    } else {
                        System.out.print("\uD83D\uDCC5 Please enter your proposed date for this trade in format dd-mm-yyyy\n");
                        Scanner sc2 = new Scanner(System.in);
                        String date = sc2.nextLine();
                        System.out.print("\uD83D\uDD5B Please enter your proposed time for this trade in format hh:mm\n");
                        String time = sc2.nextLine();
                        System.out.print("\uD83D\uDCCD Please enter your proposed location for this trade\n");
                        String location = sc2.nextLine();
                        allMeetings.editMeeting(tt.getInitialMeeting(), date, time, location);
                        //get the last time they edited the meeting
                        Integer numOfEdits = tt.getInitialMeeting().geteditHistory(user.getName());
                        tt.getInitialMeeting().changeHistory(user, numOfEdits + 1);
                        tt.getInitialMeeting().changeLastEdit(user.getName());
                    }
                } else if (input.equals("3")) {
                    //need another method for usermanager so that transactions in progress but meeting is set
                    System.out.print("Your meeting has been cancelled!\n");
                    allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedT, 4);

                    undoLogger.log(Level.INFO, selectedT.toString());
                }
            }
        }
        if (selectedT instanceof TwoWay) {
            TwoWay tt2 = (TwoWay) selectedT;
            User b2 = tt2.getFirstTrader(); //user b is the other party of this trade
            if (user.getName().equals(b2.getName())) {
                b2 = tt2.getSecondTrader();
            }
            if (tt2.getInitialMeeting().geteditHistory(user.getName()) > tt2.getInitialMeeting().geteditHistory(b2.getName()) || tt2.getInitialMeeting().viewLastEdit().equals(user.getName())) {
                //if they have already made an edit and we are waiting on the other person to approve/suggest a new meeting
                System.out.print("This pending transaction is currently waiting on the other party! Please try again later\n");
            } else {
                //the person can now approve or propose a new time
                System.out.print("You have selected: ");

                System.out.print(tt2.getFirstItem().getName() + " with " + b2.getName() + "\n");
                System.out.print("Here is the proposed meeting: " + tt2.getInitialMeeting() + "\n");
                System.out.print("Press 1 to approve. Press 2 to propose a new meeting. Press 3 to cancel\n");
                String input = sc1.nextLine();
                if (input.equals("1")) { //if they approve
                    //need another method for usermanager so that transactions in progress but meeting is set
                    allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedT, 1);

                    undoLogger.log(Level.INFO, selectedT.toString());

                } else if (input.equals(("2"))) { //they want to propose a new time
                    //provide warning if the is at their 3rd strike
                    if (tt2.getInitialMeeting().geteditHistory(user.getName()) + 1 == 3) {
                        System.out.print("\u2622 This is the last time you can propose a meeting.\nIf rejected, this transaction will be cancelled\n");
                    }
                    //here is where the transaction gets cancelled because they couldnt make up their mind
                    if (tt2.getInitialMeeting().geteditHistory(user.getName()) == 3) {
                        //one person reached 3 edits, its time to delete this transaction
                        allTransactions.handleCancelledTrade(allAdmins, allUsers, selectedT);
                        allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedT, 4);

                        undoLogger.log(Level.INFO, selectedT.toString());

                        System.out.print("\uD83D\uDE22 Sorry! You couldn't agree on a time so we deleted the transaction!\n" +
                                "Please try again!\n");
                        return user;
                    } else {
                        System.out.print("\uD83D\uDCC5 Please enter your proposed date for this trade in format dd-mm-yyyy\n");
                        Scanner sc2 = new Scanner(System.in);
                        String date = sc2.nextLine();
                        System.out.print("\uD83D\uDD5B Please enter your proposed time for this trade in format hh:mm\n");
                        String time = sc2.nextLine();
                        System.out.print("\uD83D\uDCCD Please enter your proposed location for this trade\n");
                        String location = sc2.nextLine();
                        allMeetings.editMeeting(tt2.getInitialMeeting(), date, time, location);
                        //get the last time they edited the meeting
                        Integer numOfEdits = tt2.getInitialMeeting().geteditHistory(user.getName());
                        tt2.getInitialMeeting().changeHistory(user, numOfEdits + 1);
                        tt2.getInitialMeeting().changeLastEdit(user.getName());
                    }
                }
            }
        }
        return user;
    }

}