import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ApproveTrade implements userMainMenuOptions {

    /**
     * Displays User user's pending requests and deals with approving and rejecting any pending Trade Requests.
     *
     *  @param user           User that wishes to view and approve or reject their pending Trade requests
     * @param allUsers        UserManager that stores all Users
     * @param allMeetings     MeetingManager that deals with creating meetings
     * @param allTransactions TransactionManager that deals with the System's Transactions
     * @return null if the current menu is to be reprinted; User user if the user is to be redirected to the main menu;
     * String "exit" if the user is to be logged out.
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger) {
        User Person = allUsers.getUser(user);
        List<TradeRequest> Trades = Person.getPendingRequests();
        if (Trades.size() == 0) {
            System.out.println("There are no pending trade requests!");
            return "back";
        }
        System.out.print("Here are your pending trade requests: \n");
//        for (int i = 0; i < Trades.size(); i++) {
//            String ext = "";
//            if (Person.getPendingRequests().get(i).getRequesterItem() != null) {
//                ext = " With your item " + Person.getPendingRequests().get(i).getRequesterItem().getName();
//            }
//
//            System.out.print("\uD83E\uDD1D" + (i + 1) + ". " + Trades.get(i).getRequester().getName() +
//                    " Wants " + Trades.get(i).getReceiverItem().getName() + ext + "\n");
//        }

        //printed all pending requests
        //select request
        System.out.print("\n");
        System.out.print("\u2754 Please type the ID of the trade you would like to view or 'back' to return to the main menu.\n");
        Scanner sc = new Scanner(System.in);
        Object input = sc.next();

        if (input.equals(new String("back"))) {
            return "back";
        }
        int pendingRequestIndex;

        try {
            input = Integer.parseInt((String) input);
            pendingRequestIndex = (Integer) input - 1;
        } catch (NumberFormatException e) {
            return null;
        }

        System.out.print("You have selected the following pending trade: \n");
        String ext2 = "";
        String temp = "Permanent";
//        if (Person.getPendingRequests().get(pendingRequestIndex).getRequesterItem() != null) {
//            ext2 = " For your item " + Person.getPendingRequests().get(pendingRequestIndex).getRequesterItem().getName();
//        }
//
//        if (Person.getPendingRequests().get(pendingRequestIndex).getTemp()) { //if temporary
//            temp = "Temporary";
//        }
//        System.out.println("\uD83D\uDFE9 Requester: " + Trades.get(pendingRequestIndex).getRequester().getName() + "\n" +
//                "Wants item: " + Trades.get(pendingRequestIndex).getReceiverItem().getName() + ext2 +
//                "\n\uD83D\uDCACMessage:" + Trades.get(pendingRequestIndex).getMessage() +
//                "\n\nThis trade will be " + temp + ".\n");
//
//        TradeRequest request = Person.getPendingRequests().get(pendingRequestIndex);
//
//        System.out.println("\nPlease enter '1' to approve the trade or '2' to reject the trade. Enter 'back' to return to " +
//                "your pending trade requests.");
//
//        Object nextInput = sc.next();
//        if (nextInput.equals("back")) {
//            return null;
//        } else if (nextInput.equals("1")) { //if trade is approved
//            acceptTrade(user, allUsers, allMeetings, request, allTransactions);
//            allUsers.removeFromOutboundRequests(user, request);
//            return null;
//        } else if (nextInput.equals("2")) { //if item is rejected
//            rejectTrade(user, allUsers, request);
//            allUsers.removeFromOutboundRequests(user, request);
//            return null;
//        } else {
//            System.out.print("\uD83E\uDDD0 What was that? Please try again!\n");
//            return null;
//        }
        //select if you want to approve or reject
        //method that handles approve or reject
    return null;
    }

    /**
     * Helper function. If User approves of the trade, it will proceed to create a Transaction and Meeting.
     *
     * @param user            User that approves of the trade
     * @param allUsers        UserManager that stores all Users
     * @param allMeetings     MeetingManager that deals with creating meetings
     * @param request         the Trade Request that is approved
     * @param allTransactions TransactionManager which deals with all system Transactions
     */
    public void acceptTrade(User user, UserManager allUsers, MeetingManager allMeetings, TradeRequest request, TransactionManager allTransactions) {
//        System.out.print("\u2705 Approved!\n");
//        //if the trade request is approved, we should now start a trade and make a meeting
//        Meeting meeting = meetingInitiator(allMeetings);
//
//        User temp1 = request.getRequester();
//        User temp2 = request.getReceiver();
//
//        meeting.initialconfirm(temp1.getName(), temp2.getName()); //this line creates a meeting that hasnt been confirmed
//        meeting.initialHistory(temp1.getName(), 0);
//        meeting.initialHistory(temp2.getName(), 1);
//
//        allUsers.removeFromPendingRequests(allUsers.getUser(user), request);
//        if (request.getRequestType() == 1) { //1 way
//            OneWay on = new OneWay(temp1, request.getReceiverItem(), request.getTemp());
//            on.setInitialMeeting(meeting);
//            on.getInitialMeeting().changeLastEdit(user.getName());
//            allTransactions.addToPendingTransactions(on, allUsers);
//        } else if (request.getRequestType() == 2) { //2way
//            TwoWay on = new TwoWay(request.getRequesterItem(), request.getReceiverItem(), request.getTemp());
//            on.setInitialMeeting(meeting);
//            allTransactions.addToPendingTransactions(on, allUsers);
//            on.getInitialMeeting().changeLastEdit(user.getName());
//        }
    }

    /**
     * Helper function. If User user rejects the trade, it will remove the Trade Request from the user's pending
     * request list.
     *
     * @param user     the User that rejected the trade and wishes to have it removed from their pending requests
     * @param allUsers UserManager that contains User user
     * @param request  the Trade Request that is to be rejected and removed
     */
    public void rejectTrade(User user, UserManager allUsers, TradeRequest request) {
        allUsers.removeFromPendingRequests(user, request);
        System.out.print("\u274E Rejected!\n");
    }

    /**
     * Initiates a Meeting by asking the User for a proposed date, time and location for the meeting.
     *
     * @param allMeetings MeetingManager that deals with creating meetings
     * @return returns Meeting object containing all information of the proposed meeting
     */
    public Meeting meetingInitiator(MeetingManager allMeetings) {
        System.out.print("\uD83D\uDCC5 Please enter your proposed date for this trade in format dd-mm-yyyy\n");
        Scanner sc = new Scanner(System.in);
        String date = sc.nextLine();
        System.out.print("\uD83D\uDD5B Please enter your proposed time for this trade in format hh:mm\n");
        String time = sc.nextLine();
        if (!dateValidate(date, time)) {
            System.out.println("Invalid date and/or time. Please try again.\n");
            return meetingInitiator(allMeetings);
        }
        System.out.print("\uD83D\uDCCD Please enter your proposed location for this trade\n");
        String location = sc.nextLine();
        Meeting meeting = allMeetings.createMeeting(date, time, location);

        System.out.println("\nThis is your proposed date for this trade:");
        System.out.println(meeting.toString());
        System.out.println("\nIf this is correct, please enter '1'. If you would like to change the proposed date, " +
                "please enter '2'.");
        String confirmation = sc.nextLine();
        if (!confirmation.equals("1")) {
            if (!confirmation.equals("2")) {
                System.out.println("Invalid input. Please try proposing a date for this trade again.");
            }
            return meetingInitiator(allMeetings);
        }
        return meeting;
    }

    /**
     * Checks to see if the String date is a valid date in the calendar.
     * <p>
     * NOTE: This code is based off the code from the following website:
     * https://stackoverflow.com/questions/33968333/how-to-check-if-a-string-is-date
     *
     * @param date the date in the format dd-mm-yyyy
     * @param time the time in the format hh:mm
     * @return returns false if it is not a valid date, returns true if it is valid
     */
    public boolean dateValidate(String date, String time) {
        String dateTime = date + " " + time;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateTime.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}
