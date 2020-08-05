import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages) {
        User Person = allUsers.getUser(user);
        List<TradeRequest> Trades = Person.getPendingRequests();
        if (Trades.size() == 0) {
            System.out.println("There are no pending trade requests!");
            return "back";
        }
        System.out.print("Here are your pending trade requests: \n");
        for (int i = 0; i < Trades.size(); i++) {
            String ext = "";
            if (Trades.get(i) instanceof typeOneRequest){
                typeOneRequest t = (typeOneRequest) Trades.get(i);
                //monitized or not
                String ext1 = "";
                //the person is looking to buy
                if (t.getMonetized()){
                    //its either buy or rent
                    if (t.getTemp()) { //renting over here
                        System.out.print("\uD83E\uDD1D" + (i + 1) + ". " + t.getFirstUser().getName() +
                                " Wants to rent " + t.getItem().getName()  + "\n");
                    }
                    else {
                        System.out.print("\uD83E\uDD1D" + (i + 1) + ". " + t.getFirstUser().getName() +
                                " Wants to buy " + t.getItem().getName()  + "\n");
                    }
                }


            }
            if (Trades.get(i) instanceof typeTwoRequest){
                typeTwoRequest t = (typeTwoRequest) Trades.get(i);
                //monitized or not
                System.out.print(t.getFirstUser().getName() + " wants item: " + t.getSecondItem().getName() + " in return for " + t.getFirstItem().getName() +
                        "\n");
            }

            //still need to print type 3 request






//            String ext = "";
//            if (Person.getPendingRequests().get(i).get() != null) {
//                ext = " With your item " + Person.getPendingRequests().get(i).getRequesterItem().getName();
//            }
//
//            System.out.print("\uD83E\uDD1D" + (i + 1) + ". " + Trades.get(i).getRequester().getName() +
//                    " Wants " + Trades.get(i).getReceiverItem().getName() + ext + "\n");
        }

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
        TradeRequest cTrade =  Trades.get(pendingRequestIndex);

        if (cTrade instanceof typeOneRequest) {
            //print the trade
            //then acccept or deny
            typeOneRequest t = (typeOneRequest) cTrade;
            boolean money = t.getMonetized();
            //if monetized
            System.out.print("Here is the trade you selected: " + t.getItem() + "\n");
            System.out.print("Press 1 to approve and press 2 to deny!\n");
            input = sc.next();
            if (input.equals("1")){ //approved
                allTradeRequests.updateRequestStatus(allUsers, cTrade, 1);
                //check if it is monetized
                //check if it is temporary
                OneWay final1;
                if (money){
                    if (t.getTemp()){//if temporary
                        final1 = new OneWayMonetized(user, t.getItem(), true, t.getVirtual());

                    }else { //sellin
                        final1 = new OneWayMonetized(user, t.getItem(), false, t.getVirtual());
                    }
                }
                else { //if not mon
                    if (t.getTemp()){//if temporary
                        final1 = new OneWay(user, t.getItem(), true, t.getVirtual());

                    }else { //selling
                        final1 = new OneWay(user, t.getItem(), false, t.getVirtual());
                    }
                }
                allTransactions.addToPendingTransactions(final1, allUsers);

                Meeting meeting = meetingInitiator(allMeetings);
                User temp1 = t.getFirstUser(); //initiating the trade
                User temp2 = t.getSecondUser(); //owns the item
                meeting.initialconfirm(temp1.getName(), temp2.getName()); //this line creates a meeting that hasnt been confirmed
                meeting.initialHistory(temp1.getName(), 0);
                meeting.initialHistory(temp2.getName(), 1);
                final1.setInitialMeeting(meeting);
                final1.getInitialMeeting().changeLastEdit(user.getName());
                System.out.print("Approved by you!\n");
                return null;
            }
            else if (input.equals("2")){//denied
                //pending 0
                //denied 2
                //approved 1
                allTradeRequests.updateRequestStatus(allUsers,cTrade, 2);
                System.out.print("Denied!\n");
                return null;
            }
        }
        if (cTrade instanceof typeTwoRequest){
            typeTwoRequest t = (typeTwoRequest) cTrade;
            System.out.print("Here is the trade you selected: " + "They want: " + t.getSecondItem() + "\n");
            System.out.print("Press 1 to approve and press 2 to deny and 3 to extend to a threeway\n");
            input = sc.next();
            if (input.equals("1")) { //approved
                allTradeRequests.updateRequestStatus(allUsers, cTrade, 1);
                TwoWay final1;
                if (t.getTemp()) {//if temporary
                    final1 = new TwoWay(t.getFirstItem(), t.getSecondItem(), true, t.getVirtual());

                } else { //selling
                    final1 = new TwoWay(t.getFirstItem(), t.getSecondItem(), false, t.getVirtual());
                }
                allTransactions.addToPendingTransactions(final1, allUsers);

                Meeting meeting = meetingInitiator(allMeetings);
                User temp1 = t.getFirstUser(); //initiating the trade
                User temp2 = t.getSecondUser(); //owns the item
                meeting.initialconfirm(temp1.getName(), temp2.getName()); //this line creates a meeting that hasnt been confirmed
                meeting.initialHistory(temp1.getName(), 0);
                meeting.initialHistory(temp2.getName(), 1);
                final1.setInitialMeeting(meeting);
                final1.getInitialMeeting().changeLastEdit(user.getName());

                System.out.print("Approved by you!\n");
                return null;

            }
            else if (input.equals("2")){//denied
                //pending 0
                //denied 2
                //approved 1
                allTradeRequests.updateRequestStatus(allUsers,cTrade, 2);
                System.out.print("Denied!\n");
                return null;
            }
            else { //briging in a 3rd person
                int index = 0;
                ArrayList <Item> theItems = new ArrayList<>();
                System.out.print("Here are other items you can choose from: \n");
                for (int i=0; i < allItems.getSystemInventory().size(); i++){
                    Item chosen = allItems.getSystemInventory().get(i);
                    if (chosen.getTradable()){//if item is tradable
                        if (chosen.getName().equals(((typeTwoRequest) cTrade).getFirstItem().getName()) || chosen.getName().equals(((typeTwoRequest) cTrade).getSecondItem().getName())){
                            continue;
                        }
                        else {
                            System.out.print( (index + 1) + " . " + chosen.getName() + "\n");
                            theItems.add(chosen);
                            index++;
                        }
                    }

                }
                        //so by now i have printed all items
                        System.out.print("Please choose which item you want to initiate the 3 way trade with!\n");
                        Object input2 = sc.next();
                        Integer index3way = Integer.parseInt((String) input2);
                        index3way = index3way -1;
                        System.out.print("Item you chose is: " + theItems.get(index3way).getName() + " and the owner: " + theItems.get(index3way).getOwner().getName() + " will be engaged\n");
                        System.out.print("Please choose another one of your items to trade!\n");
                        ArrayList <Item> userinventory = new ArrayList<>();
                        for (int i = 0; i < user.getInventory().size(); i++){
                            if (!user.getInventory().get(i).getName().equals(((typeTwoRequest) cTrade).getFirstItem().getName()) && !user.getInventory().get(i).getName().equals(((typeTwoRequest) cTrade).getSecondItem().getName()))
                            {
                               userinventory.add(user.getInventory().get(i));
                            }
                        }

                        for (int j=0; j < userinventory.size(); j++){
                            System.out.print((j+1) + " . " + userinventory.get(j).getName() + "\n");
                        }

                        if (userinventory.size()==0){
                            System.out.print("Sorry but you don't have enough items to engage in a 3 way trade! please add more items first!\n");
                            return user;
                        }
                        Object input3 = sc.next();
                        Integer item3way = Integer.parseInt((String) input3);

                        Item secondItem = userinventory.get(item3way-1);
                        System.out.print("Item you chose is: " + theItems.get(index3way).getName() + "\n");








                return user;







            }



        }




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
