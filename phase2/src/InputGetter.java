import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


//Note: how to put emojis in the code was found here: http://dplatz.de/blog/2019/emojis-for-java-commandline.html
public class InputGetter {

    /**
     * Prints out the wishlist of the User user showing the name and description of the items. Also in charge of
     * removing items from wishlist as per User input.
     *
     * @param user     the User that is requesting to see/edit their wishlist.
     * @param allUsers UserManager which stores the User user.
     * @return depending on what the User inputs it will return different objects
     * returns null to tell mainmenu() to call wishlist() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object wishlist(User user, UserManager allUsers) {
        List<Item> wishlist = user.getWishlist();
        //if wishlist is empty, returns "back" which will bring them back to main menu
        if (wishlist.size() == 0) {
            System.out.print("Your wishlist is empty!\n");
            return "back";
        } else { //if wishlist is not empty, prints out all the items in the wishlist and the description of the item
            System.out.print("\uD83C\uDF20Your wishlist: \n");
            for (int i = 0; i < wishlist.size(); i++) {
                System.out.println((i + 1) + ". " + wishlist.get(i).getName() + " : " + wishlist.get(i).getDescription());
            }
        }
        if (wishlist.size() == 0)
            return "back";
        Scanner sc = new Scanner(System.in);
        //asks if the User wants to remove an item from their wishlist
        System.out.print("If you would like to remove an item, please enter the ID of the item you would like to remove " +
                "or type 'back'\n");
        Object input = sc.nextLine();
        //returns them to the main menu if they wish to go "back"
        if (input.equals("back")) {
            return "back";
        }
        try {
            //will try to turn the input into an integer
            //if input is not an integer, returns null and recalls wishlist()
            input = Integer.parseInt((String) input);
        } catch (NumberFormatException e) {
            return null;
        }
        //remove the item they requested from wishlist
        allUsers.removeFromWishlist(allUsers.getUser(user), wishlist.get((Integer) input - 1));
        System.out.println("Item has been removed successfully!");
        return null;
    }

    /**
     * Prints out the User user's inventory.
     *
     * @param user     the User that wants to see their inventory
     * @param allUsers the UserManager which stores the User user
     * @return returns a User which will prompt the main menu
     */
    public Object inventory(User user, UserManager allUsers) {
        User me = allUsers.getUser(user);
        List<Item> in = me.getInventory();
        //if the user's inventory is empty
        if (in.size() == 0) {
            System.out.println("\nYour inventory is empty!\n");
            return "back";//if the user's inventory is not empty
        }
        System.out.println("Your inventory:");
        for (int i = 0; i < in.size(); i++) {
            System.out.println("\uD83D\uDCE6 " + (i + 1) + " . " + in.get(i).getName());
        }
        Scanner sc = new Scanner(System.in);
        //asks if the User wants to remove an item from their wishlist
        System.out.println("If you would like to remove an item, please enter the ID of the item you would like to remove " +
                "or type 'back'");
        Object input = sc.nextLine();
        //returns them to the main menu if they wish to go "back"
        if (input.equals("back")) {
            return "back";
        }
        try {
            //will try to turn the input into an integer
            //if input is not an integer, returns null and recalls wishlist()
            input = Integer.parseInt((String) input);
        } catch (NumberFormatException e) {
            return null;
        }

        //make sure this item is not borrowed
        if (!in.get((Integer) input - 1).getOwner().getName().equals(user.getName())) {
            System.out.print("You cannot remove item you have borrowed! Not removing!\n");
            return null;


        }
        //remove the item they requested from inventory
        allUsers.removeFromInventory(allUsers.getUser(user), in.get((Integer) input - 1));
        System.out.println("Item has been removed successfully!");
        return null;
    }

    /**
     * Prints out all items that we have in the program
     *
     * @param user     the User that wants to see their inventory
     * @param allItems stores all the items in the system
     * @return returns a User which will prompt the main menu
     */

    public List<Item> DisplayBrowse(User user, ItemManager allItems) {

        List<Item> allItems2 = allItems.getSystemInventory();
        if (user.getLocation() != null) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter '1' to view only items from users in the same location as you or "
            + "'2' to view all items in the system.");
            String input = sc.nextLine();
            if (input.equals('1')){
                List<Item> temp = new ArrayList<>();
                for (int i = 0; i < allItems.getSystemInventory().size(); i++) {
                    if (allItems.getSystemInventory().get(i).getOwner().getLocation().equals(user.getLocation())) {
                        temp.add(allItems.getSystemInventory().get(i));
                    }
                } if (temp.size() == 0) {
                    System.out.println("There are no items from other users in the same location as you. "
                    + "The system will display all current items regardless of location instead.");
                } else { //temp.size() != 0
                    allItems2 = temp;
                }
            } else if (!input.equals('2')){
                System.out.println("That is not a valid option, please try again!");
                DisplayBrowse(user, allItems);
            }
        }
        //Display every item we have. Make sure if the item is owned by the user, show that it is owned by user
        //shows the price if it is for sale
        for (int i = 0; i < allItems2.size(); i++) {
            String selfowned = "";
            String tradeOrSell = "  [TRADE]";
            String emoji = "\uD83D\uDCE6 ";
            if (allItems2.get(i).getOwner().getName().equals(user.getName())) { //change the emoji and add OWNED BY YOU if the user is the owner of the item
                selfowned = "  [OWNED BY YOU] ";
                emoji = "\u2714 ";
            }
            if (!allItems2.get(i).getToSell()) {
                tradeOrSell = "  [SELLING for " + allItems2.get(i).getPrice() + "]";
            }
            System.out.println(emoji + (i + 1) + ". " + allItems2.get(i).getName() + ": "
                    + allItems2.get(i).getDescription() + tradeOrSell + selfowned + "\n");
        }
        return allItems2;
    }

    /**
     * Allows User user to browse the System's inventory and add any of the items in the System's inventory to
     * their wishlist.
     *
     * @param user     the User that wishes to browse the inventory and add items to their wishlist
     * @param allItems the ItemManager that stores the system's inventory
     * @param allUsers the UserManager that stores the User user
     * @return depending on what the User inputs it will return different objects
     * returns null to tell mainmenu() to call wishlist() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object Browse(User user, ItemManager allItems, UserManager allUsers) {
        List<Item> allItems2 = allItems.getSystemInventory();
        //if there are no items in the system inventory
        if (allItems2.size() == 0) {
            System.out.println("There are no items to browse!");
            return "back";
        }
        System.out.println("\nHere are the current items in the system's inventory:\n");
        allItems2 = DisplayBrowse(user, allItems);
        //asks the user if they want to add an item to their wishlist
        System.out.println("Enter ID of the item you would like to add to your wishlist or type 'back' to get to main menu.");

        Scanner sc = new Scanner(System.in);
        Object input = sc.nextLine();
        if (input.equals("back")) {
            return "back";
        }
        try { input = Integer.parseInt((String) input);
        } catch (NumberFormatException e) {
            System.out.println("\n\uD83E\uDDD0 This ID is invalid. Please try again!");
            return null;
        }
        //checks to see if the ID of the item actually exists
        if ((Integer) input <= allItems2.size()) {
            Item item = allItems2.get((Integer) input - 1);
            //checks to see if the item is already in the user's wishlist
            for (int i = 0; i < user.getWishlist().size(); i++) {
                if (item.equals(user.getWishlist().get(i))) {
                    System.out.println("\nItem is already in your wishlist!");
                    return null;
                }
            }
            allUsers.addToFC (item.getCategory(), user);
            allUsers.addToWishlist(user, item);
            System.out.println("\nItem has been added to your wishlist \uD83C\uDF20");
            return null;
        } else {
            System.out.println("\n\uD83E\uDDD0 This ID is invalid. Please try again!");
        }
        return "back";
    }
    
    //taken from https://www.geeksforgeeks.org/how-to-find-the-entry-with-largest-value-in-a-java-map/
    // Find the entry with highest value
    public static <K, V extends Comparable<V> > Map.Entry<K, V> getMaxEntryInMapBasedOnValue(Map<K, V> map)
    {// To store the result
        Map.Entry<K, V> entryWithMaxValue = null;
        // Iterate in the map to find the required entry
        for (Map.Entry<K, V> currentEntry : map.entrySet()) {
            if (
                // If this is the first entry, set result as this
                    entryWithMaxValue == null

                            // If this entry's value is more than the max value
                            // Set this entry as the max
                            || currentEntry.getValue()
                            .compareTo(entryWithMaxValue.getValue())
                            > 0) {
                entryWithMaxValue = currentEntry;
            }
        }
        // Return the entry with highest value
        return entryWithMaxValue;
    }

    /**
     * Initiates a one-way or two-way trade between two Users. Prompts user for details of the trade.
     *
     * @param user             the User currently logged in and the user that is initiating the trade
     * @param allItems         ItemManager which stores a system inventory containing all the items in the system
     * @param allTradeRequests TradeRequestManager which deals with sending Trade requests to users
     * @param allUsers         UserManager which stores all the Users in the system
     * @return depending on what the User inputs it will return different objects:
     * returns null to tell mainmenu() to call Trade() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object Trade(User user, ItemManager allItems, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream

        if (user.getEligibility() >= allAdmins.getLentMinusBorrowedThreshold()) {
            System.out.print("You are not eligible to do a trade! Sorry!\n");
            return user;
        }

        //if eligible

        Calendar today = Calendar.getInstance(); //get today's date
        boolean limit = allUsers.checkWeeklyRequestLimit(allAdmins, user, today);
        if (!limit) {
            System.out.print("You have exceeded the weekly request limit! Can't make another trade! Sorry! \n");
            return user;
        }

        System.out.println("Here are the available items!:");
        DisplayBrowse(user, allItems);

        System.out.print("Please type in the ID of the item you would like to trade or 'back' to return to the main menu.\n");
        Object inum = sc.nextLine();
        if (inum.equals("back")) {
            return "back";
        }
        try {
           inum = Integer.parseInt((String) inum);
        } catch (NumberFormatException e) {
            return null;
        }

        if (allItems.getSystemInventory().size() < (Integer) inum) {
            System.out.print("\uD83E\uDDD0 Item does not exist! Please try again!\n");
            return null;
        }

        System.out.print(inum);


        //the item they have selected
        Item tradeItem = allItems.getSystemInventory().get((Integer) inum - 1);

        //block owner from making trades with themselves
        if (tradeItem.getOwner().getName().equals(user.getName())) {
            System.out.print("\uD83E\uDDD0 You are the owner so you cannot trade with yourself!\n");
            return null;
        }
        //myList is the list of items used to feed into the 1way or 2 way trades
        List<Item> myList = new ArrayList<>();
        myList.add(tradeItem);

        System.out.println("Please type '1' for one way trade and '2' for two way trade or 'back' to cancel the " +
                "current trade and restart.");

        //trade type
        Object tType = sc.nextLine();

        if (tType.equals("back")) {
            return null;
        }
        //input error handling
        if (!tType.equals("1") && !tType.equals("2")) {
            System.out.print("\uD83E\uDDD0 Invalid response. Please try again.\n");
            return null;
        }
        try {
            tType = Integer.parseInt((String) tType);
        } catch (NumberFormatException e) {
            //brings them back to the first prompt where it asks to type the ID of the item.
            System.out.print("\uD83E\uDDD0 Invalid response. Please try again.\n");
            return null;
        }

        System.out.println("Please type '1' for temporary trade and '2' for permanent trade or 'back' to cancel the " +
                "current trade and restart.");
        Object type = sc.nextLine();
        //type is type of the trade temporary or permenant
        if (type.equals("back")) {
            return null;
        }
        try {
            type = Integer.parseInt((String) type);
        } catch (NumberFormatException e) {
            System.out.print("\uD83E\uDDD0 Invalid response. Please try again.\n");
            return null;
        }
        boolean trade = false;

        if ((int) type == 1) { //we determine if it is a 1 way or 2 way trade
            trade = true;
        } else if ((int) type == 2) {
            trade = false;
        } else {
            System.out.print("\uD83E\uDDD0 Invalid response. Please try again.\n");
            return null;
        }

        System.out.print("Please put a message for the owner of the item:\n");
        String message = sc.nextLine();
        if ((int) tType == 1) { //1 way trade, do the following
            System.out.print("\nYou have selected 1 way trade for item: " + tradeItem.getName() + "\nYour message was: " + message + "\n");
            System.out.print("\nPlease enter '1' to confirm this trade or '2' to cancel the current trade and restart.\n");
            String confirmation = sc.nextLine();
            if (confirmation.equals("1")) {
                TradeRequest trades = new TradeRequest(1, user, tradeItem.getOwner(), myList, message, trade, today);
                allUsers.addToWeeklyRequestLimit(user, trades);
                allUsers.addToOutboundRequests(user, trades);
                allTradeRequests.receiveTradeRequest(allUsers, trades);
                System.out.print("\nTrade request has been sent successfully.\n");
                return "back";
            } else if (confirmation.equals("2")) {
                System.out.print("\n\u274E Trade has been cancelled.\n");
                return null;
            } else {
                System.out.print("\uD83E\uDDD0 Invalid response. Please try again.\n");
                return null;
            }
            //trade request is made and it is pending in user's pendingRequests

        } else if ((int) tType == 2) { //if its a two way trade, do the following:
            System.out.print("You have selected 2 way trade for item: " + tradeItem.getName() + "\nYour message was: " + message + "\n");
            System.out.print("Which item would you like to trade?\n Please type in the number of the item you " +
                    "would like to trade or 'back' to cancel the current trade and restart.");

            User me = allUsers.getUser(user);
            List<Item> in = me.getInventory();
            if (in.size() == 0) {
                System.out.print("Your inventory is empty!\n");
                System.out.print("\u274E Trade initiation has been cancelled.\n");
                return null;
            }
            System.out.print("\nYour inventory: \n");

            //Here is where we print the person's inventory
            boolean suggested = false;
            List <Item> otherPersonWL= new ArrayList<>();
            otherPersonWL= allUsers.getUser(tradeItem.getOwner()).getWishlist();
            for (int i = 0; i < in.size(); i++) {
                String recom = "\n";
//                for (int j = 0; j < otherPersonWL.size(); j++){
//                    if (in.get(i).getName().equals(otherPersonWL.get(j).getName())){
//                        recom = " <- [RECOMMENDED] item is in item owner's wishlist!\n";
//                        suggested = true;
//                    }
//
//                }
                System.out.println(i + 1 + ". " + in.get(i).getName() + recom);
            }

            //system can make its suggestions here if nothing from the owner's wishlist was in inventory :(
            if (!suggested){
                String Recommend = "";
                User otherSide = allUsers.getUser(tradeItem.getOwner());
                //finding the category that the person is most fan of
                Map.Entry<String, Integer> category = getMaxEntryInMapBasedOnValue(otherSide.getFrequentCategory());
                //now ive found the most frequently wanted category or if they are all zero, i recommend the first one
                //now i need to scan through the inventory to see if i can recommend anything from there

                for (int i=0; i< in.size();i++){
                    if (category.getKey().equals(in.get(i).getCategory())) {
                        Recommend = in.get(i).getName();
                        break;
                    }
                    else Recommend = in.get(0).getName(); //if you cant find anything, just grab the oldest item in the inventory to trade

                }

            System.out.print("System Suggestion based on other party's interest: " + Recommend +"\n");
            }


            Object inum2 = sc.nextLine();
            //returns them to the first prompt where they enter the id of the item to trade
            if (inum2.equals("back")) {
                return null;
            }
            //should be able to select what item
            try {
                inum2 = Integer.parseInt((String) inum2);
                if ((int) inum2 > in.size() || (int) inum2 < 1) { //if out of range
                    System.out.print("\uD83E\uDDD0 Invalid response. Please try again.\n");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.print("\uD83E\uDDD0 Invalid response. Please try again.\n");
                return null;
            }
            Item salam = in.get((int) inum2 - 1);
            System.out.println("The item you want to trade is: " + in.get((int) inum2 - 1).getName() + "\n");
            System.out.print("Please enter '1' to confirm this trade or '2' to cancel the current trade and restart.");
            String confirmation = sc.nextLine();
            if (confirmation.equals("1")) {
                myList.add(salam);
                myList.add(tradeItem);
                TradeRequest request = new TradeRequest(2, user, tradeItem.getOwner(), myList, message, trade, today);
                allTradeRequests.receiveTradeRequest(allUsers, request);
                allUsers.addToOutboundRequests(user, request);
                allUsers.addToWeeklyRequestLimit(user, request);
                System.out.print("\nTrade request has been sent successfully.\n");
                return "back";
            } else if (confirmation.equals("2")) {
                System.out.print("\nTrade has been cancelled.\n");
                return null;
            } else {
                System.out.print("\uD83E\uDDD0 Invalid response. Please try again.Request cancelled.\n");
                return null;
            }
        } else {
            System.out.println("Invalid response. Please try again.Request cancelled.\n");
            return null;
        }
    }

    /**
     * Prints the messages that User user has. Messages include Trade Requests and Meeting requests.
     *
     * @param user     the User who wishes to view their messages
     * @param allUsers UserManager which stores User user
     * @return returns the User user so that they can go back to the main menu after viewing their messages.
     */
    public User PendingTrades(User user, UserManager allUsers) {
//no error checking needed!
        System.out.print("\nHere are your pending Trades: \n");
        User Person = allUsers.getUser(user);
        if (Person.getPendingRequests().size() == 0) {
            System.out.print("\nYou have no pending requests.\n");
            return user;
        } else {
            for (int i = 0; i < Person.getPendingRequests().size(); i++) {
                String ext = "";
                //RequesterItem : Item of the requester
                //ReceiverItem: Item of the receiver

                if (Person.getPendingRequests().get(i).getRequesterItem() != null) {
                    ext = " With your item " + Person.getPendingRequests().get(i).getRequesterItem().getName();
                }
                System.out.print("\uD83E\uDD1D" + Person.getPendingRequests().get(i).getRequester().getName() +
                        " is requesting a trade for item: "
                        + Person.getPendingRequests().get(i).getReceiverItem().getName() + " With Message: " +
                        Person.getPendingRequests().get(i).getMessage() + ext + "\n");
            }
        }
        return user;
    }

    /**
     * Helper function. If User user rejects the trade, it will remove the Trade Request from the user's pending
     * request list.
     *
     * @param user     the User that rejected the trade and wishes to have it removed from their pending requests
     * @param allUsers UserManager that contains User user
     * @param request  the Trade Request that is to be rejected and removed
     */
    public void RejectTrade(User user, UserManager allUsers, TradeRequest request) {
        allUsers.removeFromPendingRequests(user, request);
        System.out.print("\u274E Rejected!\n");
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
    public void ApprovedTrade(User user, UserManager allUsers, MeetingManager allMeetings, TradeRequest request, TransactionManager allTransactions) {
        System.out.print("\u2705 Approved!\n");
        //if the trade request is approved, we should now start a trade and make a meeting
        Meeting meeting = MeetingInitiator(allMeetings);

        User temp1 = request.getRequester();
        User temp2 = request.getReceiver();

        meeting.initialconfirm(temp1.getName(), temp2.getName()); //this line creates a meeting that hasnt been confirmed
        meeting.initialHistory(temp1.getName(), 0);
        meeting.initialHistory(temp2.getName(), 1);

        allUsers.removeFromPendingRequests(allUsers.getUser(user), request);
        if (request.getRequestType() == 1) { //1 way
            OneWay on = new OneWay(temp1, request.getReceiverItem(), request.getTemp());
            on.setInitialMeeting(meeting);
            on.getInitialMeeting().changeLastEdit(user.getName());
            allTransactions.addToPendingTransactions(on, allUsers);
        } else if (request.getRequestType() == 2) { //2way
            TwoWay on = new TwoWay(request.getRequesterItem(), request.getReceiverItem(), request.getTemp());
            on.setInitialMeeting(meeting);
            allTransactions.addToPendingTransactions(on, allUsers);
            on.getInitialMeeting().changeLastEdit(user.getName());
        }
    }

    /**
     * Initiates a Meeting by asking the User for a proposed date, time and location for the meeting.
     *
     * @param allMeetings MeetingManager that deals with creating meetings
     * @return returns Meeting object containing all information of the proposed meeting
     */
    public Meeting MeetingInitiator(MeetingManager allMeetings) {
        System.out.print("\uD83D\uDCC5 Please enter your proposed date for this trade in format dd-mm-yyyy\n");
        Scanner sc = new Scanner(System.in);
        String date = sc.nextLine();
        System.out.print("\uD83D\uDD5B Please enter your proposed time for this trade in format hh:mm\n");
        String time = sc.nextLine();
        if (!dateValidate(date, time)) {
            System.out.println("Invalid date and/or time. Please try again.\n");
            return MeetingInitiator(allMeetings);
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
            return MeetingInitiator(allMeetings);
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

    /**
     * Displays User user's pending requests and deals with approving and rejecting any pending Trade Requests.
     *
     * @param user            User that wishes to view and approve or reject their pending Trade requests
     * @param allUsers        UserManager that stores all Users
     * @param allMeetings     MeetingManager that deals with creating meetings
     * @param allTransactions TransactionManager that deals with the System's Transactions
     * @return depending on what the User inputs it will return different objects:
     * returns null to tell mainmenu() to call ApproveTrade() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object ApproveTrade(User user, UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions) {
        User Person = allUsers.getUser(user);
        List<TradeRequest> Trades = Person.getPendingRequests();
        if (Trades.size() == 0) {
            System.out.println("There are no pending trade requests!");
            return "back";
        }
        System.out.print("Here are your pending trade requests: \n");
        for (int i = 0; i < Trades.size(); i++) {
            String ext = "";
            if (Person.getPendingRequests().get(i).getRequesterItem() != null) {
                ext = " With your item " + Person.getPendingRequests().get(i).getRequesterItem().getName();
            }

            System.out.print("\uD83E\uDD1D" + (i + 1) + ". " + Trades.get(i).getRequester().getName() +
                    " Wants " + Trades.get(i).getReceiverItem().getName() + ext + "\n");
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
        if (Person.getPendingRequests().get(pendingRequestIndex).getRequesterItem() != null) {
            ext2 = " For your item " + Person.getPendingRequests().get(pendingRequestIndex).getRequesterItem().getName();
        }

        if (Person.getPendingRequests().get(pendingRequestIndex).getTemp()) { //if temporary
            temp = "Temporary";
        }
        System.out.println("\uD83D\uDFE9 Requester: " + Trades.get(pendingRequestIndex).getRequester().getName() + "\n" +
                "Wants item: " + Trades.get(pendingRequestIndex).getReceiverItem().getName() + ext2 +
                "\n\uD83D\uDCACMessage:" + Trades.get(pendingRequestIndex).getMessage() +
                "\n\nThis trade will be " + temp + ".\n");

        TradeRequest request = Person.getPendingRequests().get(pendingRequestIndex);

        System.out.println("\nPlease enter '1' to approve the trade or '2' to reject the trade. Enter 'back' to return to " +
                "your pending trade requests.");

        Object nextInput = sc.next();
        if (nextInput.equals("back")) {
            return null;
        } else if (nextInput.equals("1")) { //if trade is approved
            ApprovedTrade(user, allUsers, allMeetings, request, allTransactions);
            allUsers.removeFromOutboundRequests(user, request);
            return null;
        } else if (nextInput.equals("2")) { //if item is rejected
            RejectTrade(user, allUsers, request);
            allUsers.removeFromOutboundRequests(user, request);
            return null;
        } else {
            System.out.print("\uD83E\uDDD0 What was that? Please try again!\n");
            return null;
        }
        //select if you want to approve or reject
        //method that handles approve or reject
    }

    /**
     * Deals with requesting to add a new item to the system's inventory. Prompts user for details of the item
     * and sends a request to the Admin for approval. Adds the item to the User's item history so they can
     * view it's current status.
     *
     * @param user     the User requesting to add a new item to the system
     * @param allUsers UserManager which stores all Users
     * @return depending on what the User inputs it will return different objects:
     * returns null to tell mainmenu() to call AddItem() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object AddItem(User user, UserManager allUsers) {
        System.out.print("Please enter the name of item you would like to add or 'back' to go back to the main menu.\n");
        Scanner sc = new Scanner(System.in);
        String itemName = sc.nextLine();
        if (itemName.equals("back")) {
            return "back";
        }
        System.out.print("Please enter the description of item you would like to add or 'back' to go back to the main menu.\n");
        String description = sc.nextLine();
        if (description.equals("back")) {
            return "back";
        }

        System.out.print("Please enter '1' if you want to trade this item or enter '2' if you wish to sell this item.\n"
                + "Enter 'back' to go back to the main menu.");
        String option = sc.nextLine();
        String tradeOrSell = "Undefined";
        if (option.equals("back")){
            return "back";
        }
        while (tradeOrSell.equals("Undefined")) {
            if (option.equals("1")) {
                tradeOrSell = "trade";
            } else if (option.equals("2")) {
                tradeOrSell = "sell";
            } else {
                System.out.println("That is not a valid option! Please try again.");
            }
        }
        Double price = null;
        if (tradeOrSell.equals("sell")) {
            while (price == null) {
                System.out.println("Please enter the price you would like to sell this item for or 'back' " +
                        "to return to the main menu.");
                String priceInput = sc.nextLine();
                if (priceInput.equals("back")) {
                    return "back";
                }
                try {
                    Double temp = Double.parseDouble(priceInput);
                    price = (double) Math.round(temp * 100) / 100;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price! Please try again.");
                }
            }
        }

        System.out.print("Please choose the category of item you would like to add " +
                "by typing the number or 'back' to go back to the main menu.\n");
        System.out.print("1.Electronics\n2.Automotive and car accessories\n3.Baby\n4.Beauty, Health and Personal Care" +
                "\n5.Books\n6.Home and Kitchen Supplies\n" +
                "7.Clothing\n8.Movies, music and TV\n9.Office Supplies\n10.Gaming\n");

        String ID = sc.nextLine();

        String category = "Undefined";
        while (category.equals("Undefined")){
            if (ID.equals("back")) {
                return "back";
            } else if (ID.equals("1")){
                category = "Electronics";
            } else if (ID.equals("2")){
                category = "Automotive and car accessories";
            } else if (ID.equals("3")){
                category = "Baby";
            } else if (ID.equals("4")){
                category = "Beauty, Health and Personal Care";
            } else if (ID.equals("5")){
                category = "Books";
            } else if (ID.equals("6")){
                category = "Home and Kitchen supplies";
            } else if (ID.equals("7")){
                category = "Clothing";
            } else if (ID.equals("8")){
                category = "Movies, music and TV";
            } else if (ID.equals("9")){
                category = "Office Supplies";
            } else if (ID.equals("10")){
                category = "Gaming";
            } else {
                System.out.print("Please enter a category for the product!\n");
            }
        }
        Item newItem;
        if (tradeOrSell.equals("sell")){
            newItem = new Item(itemName, user, description, category, price);
            System.out.println("The item you wish to add is the following: ");
            System.out.println("Item name: " + newItem.getName() + "\n" + "Item description: " +
                    newItem.getDescription() + "\n" +  "Price: " + newItem.getPrice() + "\n"
                    + "Item Category:" + newItem.getCategory());
        } else {
            newItem = new Item(itemName, user, description, category);
            System.out.println("The item you wish to add is the following: ");
            System.out.println("Item name: " + newItem.getName() + "\n" + "Item description: " +
                    newItem.getDescription() + "\n Item Category:" + newItem.getCategory());
        }
        System.out.println("\nIf this is correct, please enter '1'. If you would like to change the item, " +
                "please enter '2'.");
        String confirmation = sc.nextLine();
        if (!confirmation.equals("1")) {
            if (!confirmation.equals("2")) {
                System.out.println("Invalid input. Please try adding the item again.");
                return null;
            }
            return null;
        }

        allUsers.addToDraftInventory(user, newItem);
        System.out.print("\n \u2705 Your request is sent to admin for approval!\n");
        allUsers.addToItemHistory(user, newItem);
        return null;
    }

    /**
     * Prints out the User user's top 3 trading partners.
     *
     * @param user the User who is requesting to see their top 3 trading partners
     * @return returns a User so that user gets returned to the main menu
     */
    public User Top3TradingPartners(User user) {
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

    /**
     * Prints out the User user's most recent trades.
     *
     * @param user     User that is requesting to see their most recent trades
     * @param allUsers UserManager which stores all Users in the system
     * @return returns a User so that user gets returned to the main menu
     */
    public User MostRecentTrades(User user, UserManager allUsers) {
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

    /**
     * Allows the User to view their item history and it's status. It will display whether it is
     * "Pending", "Approved", or "Rejected".
     *
     * @param user     the User who is requesting to see their item history
     * @param allUsers UserManager that stores all the Users in the system
     * @return returns a User so that user gets returned to the main menu
     */
    public User ViewItemHistory(User user, UserManager allUsers) {
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

    /**
     * Deals with adding a new item to the User's wishlist. Prompts user for details of the item
     * and sends a request to the Admin for approval.
     *
     * @param user     the User requesting to add a new item to their wishlist
     * @param allUsers UserManager which stores all Users
     * @return depending on what the User inputs it will return different objects:
     * returns null to tell mainmenu() to call addtoWishlist() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object addToWishlist(User user, UserManager allUsers) {
        System.out.print("Please enter the name of item you would like to add to your wishlist " +
                "or 'back' to go back to the main menu.\n");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        if (name.equals("back")) {
            return "back";
        }
        System.out.print("Please enter the description of item or 'back' to go back to the main menu.\n");
        String description = sc.nextLine();
        if (name.equals("back")) {
            return "back";
        }
        System.out.print("Please choose the category of item by typing the number or 'back' to go back to the main menu.\n");
        System.out.print("1.Electronics\n2.Automotive and car accessories\n3.Baby\n4.Beauty, Health and Personal Care\n5.Books\n6.Home and Kitchen Supplies\n" +
                "7.Clothing\n8.Movies, music and TV\n9.Office Supplies\n10.Gaming\n");

        String ID = sc.nextLine();

        String category = "Undefined";
        while (category.equals("Undefined")){
        if (name.equals("back")) {
            return "back";
        }
        else if (ID.equals("1")){
            category = "Electronics";
        }
        else if (ID.equals("2")){
            category = "Automotive and car accessories";
           }
        else if (ID.equals("3")){
            category = "Baby";
                   }
        else if (ID.equals("4")){
            category = "Beauty, Health and Personal Care";
                 }
        else if (ID.equals("5")){
            category = "Books";
                  }
        else if (ID.equals("6")){
            category = "Home and Kitchen supplies";

        }
        else if (ID.equals("7")){
            category = "Clothing";

        }
        else if (ID.equals("8")){
            category = "Movies, music and TV";

        }
        else if (ID.equals("9")){
            category = "Office Supplies";

        }
        else if (ID.equals("10")){
            category = "Gaming";
                   }
        else
        {
            System.out.print("Please enter a category for the product!\n");
        }
        }




        Item wishlistItem = new Item(name, null, description, category);
        System.out.println("The item you wish to add to your wishlist is the following: ");
        System.out.println("Item name: " + wishlistItem.getName() + "\n" + "Item description: " + wishlistItem.getDescription() + "\nItem category: " + wishlistItem.getCategory());
        System.out.println("\nIf this is correct, please enter '1'. If you would like to change the item, " +
                "please enter '2'.");

        String confirmation = sc.nextLine();
        if (!confirmation.equals("1")) {
            if (!confirmation.equals("2")) {
                System.out.println("Invalid input. Please try adding the item again.");
                return null;
            }
            return null;
        }


        //adding item to the person's
        allUsers.addToWishlist(user, wishlistItem);
        if (ID.equals("1")){
            allUsers.addToFC("Electronics", user);
        }
        else if (ID.equals("2")){
            allUsers.addToFC("Automotive and car accessories", user);
        }
        else if (ID.equals("3")){
            allUsers.addToFC("Baby", user);
        }
        else if (ID.equals("4")){
            allUsers.addToFC("Beauty, Health and Personal Care", user);
        }
        else if (ID.equals("5")){
            allUsers.addToFC("Books", user);
        }
        else if (ID.equals("6")){
            allUsers.addToFC("Home and Kitchen Supplies", user);
        }
        else if (ID.equals("7")){
            allUsers.addToFC("Clothing", user);
        }
        else if (ID.equals("8")){
            allUsers.addToFC("Movies, music and TV", user);
        }
        else if (ID.equals("9")){
            allUsers.addToFC("Office Supplies", user);
        }
        else if (ID.equals("10")){
            allUsers.addToFC("Gaming", user);
        }
        System.out.print("Item has been added to your wishlist \uD83C\uDF20\n");

        return "back";
    }

    /**
     * Notifies the Admin of a Request to Unfreeze from User user.
     *
     * @param user             frozen user sending the request to admin to be unfrozen
     * @param adminInputGetter contains the method for adding frozen requests
     * @return returns User so that they can be redirected to the main menu
     */
    public Object NotifyAdmin(User user, AdminInputGetter adminInputGetter) {
        adminInputGetter.addfrozenRequest(user);
        System.out.print("Your request is successfully submitted!\n");
        return user;
    }

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
    public Object PendingTransactionProcess(User user, ItemManager allItems, UserManager allUsers, MeetingManager
            allMeetings, TransactionManager allTransactions, AdminManager allAdmins) {

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
            } if (tt.getInitialMeeting().geteditHistory(user.getName()) > tt.getInitialMeeting().geteditHistory(b.getName()) || tt.getInitialMeeting().viewLastEdit().equals(user.getName())) {
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
    public Object confirmMeetings(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests,
                                  UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                                  AdminInputGetter admininputgetter, AdminManager allAdmins) {
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

    /**
     * Prints pending outbound requests
     *
     * @param user frozen user sending the request to admin to be unfrozen
     * @return returns User so that they can be redirected to the main menu
     */
    public Object PrintOutboundRequest(User user) {
        System.out.print("Here is the status of your pending outbound requests:\n");
        List<TradeRequest> outbound = user.getOutboundRequests();

        if (outbound.size() == 0) {
            System.out.print("You do not have any pending outbound requests!\n");
            return user;
        }
        for (int i = 0; i < outbound.size(); i++) {
            String extension = " ";
            if (outbound.get(i).getRequestType() == 2) {
                extension = " in exchange for: " + outbound.get(i).getRequesterItem().getName();
            }
            System.out.print((i + 1) + " . You requested for : " + outbound.get(i).getReceiverItem().getName() + extension + "\n");
        }
        return user;
    }

    /**
     * Displays the main menu, and prompts user for input depending on what they want to do.
     * <p>
     * Unfrozen Users are able to do the following:
     * View wishlist, view inventory, browse items, initiate trade, view messages, approve pending trades, add
     * items to inventory, view recent trades, view most frequent trading partners, view item statuses, add items to
     * wishlists, view approved trades, approve meetings, confirm meetings, and log out.
     * <p>
     * Frozen users are able to do the following:
     * View wishlist, view inventory, browse items, add items to inventory, view most recent trades, view most
     * frequent trading partners, view item statuses, add items to wishlist, request unfreeze and log out.
     *
     * @param user             the user that is currently logged in to the system
     * @param allItems         ItemManager that stores the system's inventory
     * @param system1          InputGetter which contains all the methods that are in charge of each option in the main menu
     * @param allTradeRequests TradeRequestManager that stores all the Trade Requests in the system
     * @param allUsers         UserManager that stores all the Users in the system
     * @param allMeetings      MeetingManager that deals with the creation of meetings
     * @param allTransactions  TransactionManager that stores all the Transactions in the system
     * @param admininputgetter AdminInputGetter which contains all the methods for tasks that Admins can do
     * @return depending on what the User inputs it will return different objects:
     * returns User to TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another User to log in
     * returns String "exit" to tell TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object mainMenu(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests,
                           UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                           AdminInputGetter admininputgetter, AdminManager allAdmins) {
        //A frozen account is one where you can log in and look for items, but you cannot arrange any transactions.
        // A user who has been frozen can request that the administrative user unfreezes their account.
        boolean frozenAccount = user.getIsFrozen();
        boolean pseudoFrozenAccount = user.getIsPseudoFrozen();
//if they are frozen
        if (frozenAccount || pseudoFrozenAccount) { //first off, tell them that they are frozen
            return frozenMainMenu(user, allItems, system1, allTradeRequests, allUsers, allMeetings,
                    allTransactions, admininputgetter, allAdmins);
        }
       //if they are not frozen
       //they are a demo user without an account
        else if (user.getName().equals("Demo")){
            return demoMainMenu(user, allItems, system1, allTradeRequests, allUsers, allMeetings,
                    allTransactions, admininputgetter, allAdmins);
        } else {
            return userMainMenu(user, allItems, system1, allTradeRequests, allUsers, allMeetings,
                    allTransactions, admininputgetter, allAdmins);
        }
    }
    /**
     * Displays the main menu for a normal, unfrozen user and prompts user for input depending on what
     * they want to do.
     * <p>
     * Unfrozen Users are able to do the following:
     * View wishlist, view inventory, browse items, initiate trade, view messages, approve pending trades, add
     * items to inventory, view recent trades, view most frequent trading partners, view item statuses, add items to
     * wishlists, view approved trades, approve meetings, confirm meetings, and log out.
     *
     * @param user             the user that is currently logged in to the system
     * @param allItems         ItemManager that stores the system's inventory
     * @param system1          InputGetter which contains all the methods that are in charge of each option in the main menu
     * @param allTradeRequests TradeRequestManager that stores all the Trade Requests in the system
     * @param allUsers         UserManager that stores all the Users in the system
     * @param allMeetings      MeetingManager that deals with the creation of meetings
     * @param allTransactions  TransactionManager that stores all the Transactions in the system
     * @param admininputgetter AdminInputGetter which contains all the methods for tasks that Admins can do
     * @return depending on what the User inputs it will return different objects:
     * returns User to TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another User to log in
     * returns String "exit" to tell TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object userMainMenu(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests,
                               UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                               AdminInputGetter admininputgetter, AdminManager allAdmins){
        System.out.print("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + user.getName() + "!\n");
        if (allUsers.getUser(user).getPendingRequests().size() > 0) {
            System.out.print("\uD83D\uDCE9 You have " + allUsers.getUser(user).getPendingRequests().size() +
                    " Pending Trade Requests!\n");
        }
        if (allUsers.getUser(user).getPendingTrades().size() > 0) {
            System.out.print("\u23F3 You have " + allUsers.getUser(user).getPendingTrades().size() +
                    " Pending Transactions!\n");
        }
        System.out.print("Please select number from the following:\n1.View Wishlist\n2.View Inventory\n" +
                "3.Browse Items\n4.Initiate Trade\n5.View Pending Trade Requests\n6.Approve Pending Trade Requests\n" +
                "7.Add Item to inventory\n8.View most recent trades\n9.View most frequent trading partners\n" +
                "10.View status of my items\n11.Add Item to wishlist\n" +
                "12.Approve Meeting\n13.Confirm Meetings for Approved Trades\n14.View status of outbound requests\n" +
                "15.Change Account Settings\n16.Logout" +
                "\nEnter 'exit' to exit the system at any time.\n");

        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        if (!a.equals("exit")) {
            if (a.equals("1")) { //view wishlist
                Object temp = system1.wishlist(user, allUsers);
                while (temp == null) {
                    temp = system1.wishlist(user, allUsers);
                }
                return user;
            } else if (a.equals("2")) { //view inventory
                Object temp = system1.inventory(user, allUsers);
                while (temp == null) {
                    temp = system1.inventory(user, allUsers);
                }
                return user;
            } else if (a.equals("3")) { //browse items
                Object temp = system1.Browse(user, allItems, allUsers);
                while (temp == null) {
                    temp = system1.Browse(user, allItems, allUsers);
                }
                return user;
            } else if (a.equals("4")) { //choose the id?
                Object temp = system1.Trade(user, allItems, allTradeRequests, allUsers, allAdmins);
                while (temp == null) {
                    temp = system1.Trade(user, allItems, allTradeRequests, allUsers, allAdmins);
                }
                //else input was "back", returns to main menu
                return user;
            } else if (a.equals("5")) {
                //view messages
                return system1.PendingTrades(user, allUsers);
            } else if (a.equals("6")) {
                Object temp = system1.ApproveTrade(user, allUsers, allMeetings, allTransactions);
                while (temp == null) {
                    temp = system1.ApproveTrade(user, allUsers, allMeetings, allTransactions);
                }
                //else input was "back", returns to main menu
                return user;
            } else if (a.equals("7")) { //request to add new item
                Object temp = system1.AddItem(user, allUsers);
                while (temp == null) {
                    temp = system1.AddItem(user, allUsers);
                }
                return user;
            } else if (a.equals("8")) { //View most recent trades
                MostRecentTrades(user, allUsers);
            } else if (a.equals("9")) { //View most frequent trading partners
                return Top3TradingPartners(user);
            } else if (a.equals("10")) {
                return system1.ViewItemHistory(user, allUsers);
            } else if (a.equals("11")) {
                Object temp = system1.addToWishlist(user, allUsers);
                while (temp == null) {
                    temp = system1.addToWishlist(user, allUsers);
                }
                return user;
            } else if (a.equals("12")) {
                return system1.PendingTransactionProcess(user, allItems, allUsers, allMeetings, allTransactions, allAdmins);
            } else if (a.equals("13")) { //confirm that the meeting went through
                //first print all the meeting that are pending for this user
                Object temp = system1.confirmMeetings(user, allItems, system1, allTradeRequests,
                        allUsers, allMeetings, allTransactions,
                        admininputgetter, allAdmins);
                while (temp == null) {
                    temp = system1.confirmMeetings(user, allItems, system1, allTradeRequests,
                            allUsers, allMeetings, allTransactions,
                            admininputgetter, allAdmins);
                }
                return user;
            } else if (a.equals("14")) {
                return PrintOutboundRequest(user);
            } else if(a.equals("15")){
                //change account settings
                Object temp = system1.accountSettings(user, allUsers);
                while (temp == null) {
                    temp = system1.accountSettings(user, allUsers);
                }
                return user;
            }else if (a.equals("16")) {
                //logout
                return null;
            }
        } else {//input is "exit"
            return a;
        }
        return user;
    }

    /**
     * Deals with changing the account settings of User-- they are able to change their username,
     * password and/or location.
     * @param user the User who wants to change their account settings
     * @param allUsers UserManager which stores all the users in the system
     * @return depending on what the User inputs it will return different objects:
     * returns null to tell mainmenu() to call accountSettings() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object accountSettings(User user, UserManager allUsers) {
        System.out.println("Please select the number beside the option you would like to change or 'back'" +
                "to return to the main menu.");
        System.out.println("1.Change username");
        System.out.println("2.Change password");
        System.out.println("3.Change location");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.equals("back")) {
            return "back";
        } else if (input.equals("1")) {
            Object temp = userNameChange(user, allUsers);
            while (temp == null) {
                temp = userNameChange(user, allUsers);
            }
            if (temp.equals("back")){
                return "back";
            }
            return null;
        } else if (input.equals("2")) {
            Object temp = passwordChange(user);
            while (temp == null) {
                temp = passwordChange(user);
            }
            if (temp.equals("back")){
                return "back";
            }
            return null;
        } else if(input.equals("3")) {
            Object temp = locationChange(user);
            while (temp == null) {
                temp = locationChange(user);
            }
            if (temp.equals("back")){
                return "back";
            }
            return null;
        }
        System.out.println("\nThat is not a valid option, please try again.\n");
        return null;
        }

    /**
     * Deals with changing the User user's location.
     * @param user the User that wants to change their location
     * @return returns different objects depending on the User user's input
     *      returns "back" to return to the main menu
     *      returns null to tell the accountSettings() to call locationChange() again
     *      returns "account settings" to tell main menu to call accountSettings() again
     */
    public Object locationChange(User user) {
        if (user.getLocation() == null) {
            System.out.println("\nYou currently have no set location.");
        } else {
            System.out.println("\nYour current set location is " + user.getLocation());
        }
        System.out.println("\nHot tip: By setting a location, you will have the option to sort browse items" +
                " so you will only see items from users in the same location as you!\n");
        System.out.println("Please select from the following options:");
        System.out.println("1.Set location");
        System.out.println("2.Remove location");
        System.out.println("3.Return to account settings");
        System.out.println("Enter 'back' to return to the main menu.");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.equals("back")) {
            return "back";
        } else if (input.equals("1")){
            if (user.getLocation() == null) {
                System.out.println("\nYou currently have no set location.\n");
            } else {
                System.out.println("\nYour current set location is " + user.getLocation() + "\n");
            }
            System.out.println("Please enter the name of the new location:");
            user.setLocation(sc.nextLine());
            System.out.println("You have successfully changed your location to " + user.getLocation() + ".");
            return null;
        } else if (input.equals("2")){
            user.setLocation(null);
            System.out.println("You have successfully removed your location.");
        } else if (input.equals("3")){
            return "account settings";
        }System.out.println("\nYou selected an invalid option, please try again.\n");
        return null;
    }

    /**
     * Deals with changing the User user's password.
     * @param user the User that wants to change their password
     * @return returns different objects depending on the User user's input
     *      returns "back" to return to the main menu
     *      returns null to tell the accountSettings() to call passwordChange() again
     *      returns "account settings" to tell main menu to call accountSettings() again
     */
    public Object passwordChange(User user) {
        System.out.println("If you like to continue with changing your password, please enter '1'. " +
                "Otherwise, please enter '2' to go back to the" +
                " other account settings.\nEnter 'back' to return to the main menu.");
        Scanner sc = new Scanner(System.in);
        String input2 = sc.nextLine();
        if (input2.equals("back")) {
            return "back";
        } else if (input2.equals("1")) {
            System.out.println("Please enter your current password:");
            String oldPassword = sc.nextLine();
            if (oldPassword.equals(user.getPassword())){
                System.out.println("Please enter the new password:");
                String newPassword = sc.nextLine();
                user.setPassword(newPassword);
                System.out.println("\nYou have successfully changed your password.\n");
                return "account settings";
            }
            System.out.println("Incorrect password. Please try again.");
            return null;
        } else if (input2.equals("2")) {
            return "account settings";
        }
        System.out.println("\nYou did not select a valid option. Please try again.\n");
        return null;
    }

    /**
     * Deals with changing the User user's username.
     * @param user the User that wants to change their username
     * @param allUsers UserManager that stores all the users in the system
     * @return returns different objects depending on the User user's input
     *      returns "back" to return to the main menu
     *      returns null to tell the accountSettings() to call userNameChange() again
     *      returns "account settings" to tell main menu to call accountSettings() again
     */
    public Object userNameChange(User user, UserManager allUsers) {
        System.out.println("\nYour current username is " + user.getName() + "\n");
        System.out.println("If you like to continue with changing your username, please enter '1'. " +
                "Otherwise, please enter '2' to go back to the" +
        " other account settings.\nEnter 'back' to return to the main menu.");
        Scanner sc = new Scanner(System.in);
        String input2 = sc.nextLine();
        if (input2.equals("back")) {
            return "back";
        } else if (input2.equals("1")) {
            System.out.println("Please enter a new username.");
            String newName = sc.nextLine();
            for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
                if (allUsers.getAllUsers().get(i).getName().equals(newName)) {
                    System.out.println("Username " + newName + "is already taken! Please try again.");
                    return null;
                }
            }
            //if it makes it through without finding the same username, it will set newName as the
            //user's new username
            user.setName(newName);
            System.out.println("\nYou have successfully changed your username. Your username is now " +
                    user.getName() + ".\n");
            return "account settings";
        } else if (input2.equals("2")) {
            return "account settings";
            }
        System.out.println("\nYou did not select a valid option. Please try again.\n");
        return null;
        }

    /**
     * Displays the main menu for a demo user and prompts user for input depending on what they want to do.
     * <p>
     * Demo users are able only able to browse items and log out.
     * However, they can choose from the following and it will show them what those menu options do if they create
     * an account:
     * View wishlist, view inventory, initiate trade, view messages, approve pending trades, add
     * items to inventory, view recent trades, view most frequent trading partners, view item statuses, add items to
     * wishlists, view approved trades, approve meetings, confirm meetings, and change account settings.
     *
     * @param user             the user that is currently logged in to the system
     * @param allItems         ItemManager that stores the system's inventory
     * @param system1          InputGetter which contains all the methods that are in charge of each option in the main menu
     * @param allTradeRequests TradeRequestManager that stores all the Trade Requests in the system
     * @param allUsers         UserManager that stores all the Users in the system
     * @param allMeetings      MeetingManager that deals with the creation of meetings
     * @param allTransactions  TransactionManager that stores all the Transactions in the system
     * @param admininputgetter AdminInputGetter which contains all the methods for tasks that Admins can do
     * @return depending on what the User inputs it will return different objects:
     * returns User to TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another User to log in
     * returns String "exit" to tell TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object demoMainMenu(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests,
                               UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                               AdminInputGetter admininputgetter, AdminManager allAdmins){
        System.out.print("-------------------------------------------------------\n\uD83E\uDD16 Hello Demo User \uD83E\uDD16\n");
        System.out.print("Please select number from the following:\n" +
                "\uD83C\uDD94 Indicates that signup/login as user is required!\n1.View Wishlist\n2.View Inventory \uD83C\uDD94 \n" +
                "3.Browse Items\n4.Initiate Trade \uD83C\uDD94 \n5.View Pending Trade Requests \uD83C\uDD94\n6.Approve Pending Trade Requests \uD83C\uDD94\n" +
                "7.Add Item to inventory \uD83C\uDD94\n8.View most recent trades \uD83C\uDD94\n9.View most frequent trading partners \uD83C\uDD94\n" +
                "10.View status of my items \uD83C\uDD94\n11.Add Item to wishlist\n" +
                "12.Approve Meeting \uD83C\uDD94\n13.Confirm Meetings for Approved Trades \uD83C\uDD94\n" +
                "14.View status of outbound requests \uD83C\uDD94\n" + "15.Change Account Settings \uD83C\uDD94\n" +
                "16.Logout" + "\nEnter 'exit' to exit the system at any time.\n");

        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        if (!a.equals("exit")) {
            if (a.equals("1")) { //view wishlist
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 Your wishlist are items that you want to have in the future!\n");
                Object temp = system1.wishlist(user, allUsers);
                while (temp == null) {
                    temp = system1.wishlist(user, allUsers);
                }
                return user;
            } else if (a.equals("2")) { //view inventory
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can add items to be added to your inventory. " +
                        "Once you add an item, the item is sent to approval to Admin.\n");
                return user;
            } else if (a.equals("3")) { //browse items
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see all items available in the system to trade. " +
                        "Each item is approved by an admin.\n");
                Object temp = system1.Browse(user, allItems, allUsers);
                while (temp == null) {
                    temp = system1.Browse(user, allItems, allUsers);
                }
                return user;
            } else if (a.equals("4")) { //choose the id?
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can choose an item and initiate a 1way or 2way " +
                        "permanent/temporary trade.\n");
                //else input was "back", returns to main menu
                return user;
            } else if (a.equals("5")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see any pending trade requests.\n");
                return user;
            } else if (a.equals("6")) {
                System.out.print("-------------------------------------------------------\n" +
                        "\uD83D\uDC81 As a user, you can look through all trade requests and their details and " +
                        "decide if you want to confirm or reject the trade.\n");
                return user;
            } else if (a.equals("7")) { //request to add new item
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can add items to your inventory. However, once added, " +
                        "the request will be sent to admin for approval. \n" +
                        "Once approved, the item will show up in your inventory and it will be visible to other " +
                        "users when browsing.\n");
                return user;
            }  else if (a.equals("8")) { //View most recent trades
                System.out.print("-------------------------------------------------------\n" +
                        "\uD83D\uDC81 As a user, you can see information about your 3 most recent trades.\n");
                return user;
            } else if (a.equals("9")) { //View most frequent trading partners
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see who your most frequently trading partners are.\n");
            } else if (a.equals("10")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see a list of all items you have ever submitted to the " +
                        "system and whether they were approved/rejected by admin or still pending on admin's approval.\n");
                return user;
            } else if (a.equals("11")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can add items to your wishlist that you would " +
                        "like to purchase.\n");
                return user;
            }
            else if (a.equals("12")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, once a trade is accepted by the other party, " +
                        "you can see the suggested meeting, approve meeting or suggest an alternative " +
                        "meeting here.\n");
                return user;
            } else if (a.equals("13")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, here you can confirm a meeting happened or if it did " +
                        "not happen. If the meeting did not happen, both parties may be penalized.\n");
                return user;
            } else if (a.equals("14")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can see the status of the outbound trade requests " +
                        "you have sent.\n");
            } else if (a.equals("15")){
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 As a user, you can change your account settings; this includes changing" +
                        " your username, password and set location.\n");
            } else if (a.equals("16")) {
                System.out.print("-------------------------------------------------------" +
                        "\n\uD83D\uDC81 Logging out as Demo!\n");
                //logout
                return null;
            }
            else { //if they input invalid response
                System.out.print("\uD83E\uDDD0 Invalid Response!\n");
                return user;
            }
        } else {//input is "exit"
            return a;
        }
        return user;
    }

    /**
     * Displays the main menu for a frozen or pseudoFrozen user and prompts user for input depending on what they want to do.
     * <p>
     * Frozen users are able to do the following:
     * View wishlist, view inventory, browse items, add items to inventory, view most recent trades, view most
     * frequent trading partners, view item statuses, add items to wishlist, request unfreeze and log out.
     *
     * @param user             the user that is currently logged in to the system
     * @param allItems         ItemManager that stores the system's inventory
     * @param system1          InputGetter which contains all the methods that are in charge of each option in the main menu
     * @param allTradeRequests TradeRequestManager that stores all the Trade Requests in the system
     * @param allUsers         UserManager that stores all the Users in the system
     * @param allMeetings      MeetingManager that deals with the creation of meetings
     * @param allTransactions  TransactionManager that stores all the Transactions in the system
     * @param admininputgetter AdminInputGetter which contains all the methods for tasks that Admins can do
     * @return depending on what the User inputs it will return different objects:
     * returns User to TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another User to log in
     * returns String "exit" to tell TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object frozenMainMenu(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests,
                           UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                           AdminInputGetter admininputgetter, AdminManager allAdmins){
        Scanner sc = new Scanner(System.in);

        System.out.print("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + user.getName() + "!\n");
        String actionTaker = "by Admin.";
        if (user.getIsPseudoFrozen())
            actionTaker = "by System.";

        System.out.print("\uD83E\uDD76 Your account is frozen " + actionTaker +
                " You are not able to do any trades until you are unfrozen by admin.\n" +
                "\uD83C\uDFC1 Please ask Admin to unfreeze your account!\n\n");

        System.out.print("Please select number from the following:\n1.View Wishlist\n2.View Inventory\n" +
                "3.Browse Items\n" +
                "4.Add Item to inventory\n5.View most recent trades\n6.View most frequent trading partners\n" +
                "7.View status of my items\n8.Add Item to wishlist" +
                "\n9.Request unfreeze!\n10.Change Account Settings\n11.Logout" + "\nEnter 'exit' to exit the system at any time.\n");

        String a = sc.nextLine();
        if (a.equals("exit")){
            return a;
        } else {
            if (a.equals("1")) {//view wishlist
                Object temp = system1.wishlist(user, allUsers);
                while (temp == null) {
                    temp = system1.wishlist(user, allUsers);
                }
                return user;
            } else if (a.equals("2")) { //view inventory
                Object temp = system1.inventory(user, allUsers);
                while (temp == null) {
                    temp = system1.inventory(user, allUsers);
                }
                return user;
            } else if (a.equals("3")) { //browse items
                Object temp = system1.Browse(user, allItems, allUsers);
                while (temp == null) {
                    temp = system1.Browse(user, allItems, allUsers);
                }
                return user;
            } else if (a.equals("4")) {
                //request to add new item
                Object temp = system1.AddItem(user, allUsers);
                while (temp == null) {
                    temp = system1.AddItem(user, allUsers);
                }
                return user;
            } else if (a.equals("5")) { //View most recent trades
                MostRecentTrades(user, allUsers);
            } else if (a.equals("6")) { //View most frequent trading partners
                return Top3TradingPartners(user);
                //else input was "back", returns to main menu
            } else if (a.equals("7")) {
                return system1.ViewItemHistory(user, allUsers);
            } else if (a.equals("8")) {
                Object temp = system1.addToWishlist(user, allUsers);
                while (temp == null) {
                    temp = system1.addToWishlist(user, allUsers);
                }
                return user;
            } else if (a.equals("9")) {
                if (admininputgetter.getFrozenRequests().contains(user)) {
                    System.out.println("You have already requested to be unfrozen! Please be patient.");
                    return user;
                }
                NotifyAdmin(user, admininputgetter);
                return user;
            } else if (a.equals("10")){
                Object temp = system1.accountSettings(user, allUsers);
                while (temp == null) {
                    temp = system1.accountSettings(user, allUsers);
                }
                return user;
            }
            else if (a.equals("11")) { //logout
                return null;
            }
        }
        return user;
    }
}




