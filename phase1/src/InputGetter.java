import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

public class InputGetter {


    public Object authenticator(UserManager allUsers) {


        //we fill out allUsers with whatever is in csv file
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;

        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Type 'signup' to create an account or 'login' to access your account or 'exit' to exit at anytime.");
        try {
            String input = br.readLine().toLowerCase();
            //user wants to signup for an account
            if (input.equals("exit")){
                return input;
            } if (input.equals("signup")) {
                //as long as they don't say 'exit', it will prompt them to enter user name
                while (!input.equals("exit") && !prompts.usergot/*&& curr < 2*/) {
                    if (prompts.hasNext()) {
                        System.out.println(prompts.next());
                    }
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        temp.add(input);
                        curr++;
                        if (curr == 2)
                            prompts.usergot = true;
                    }
                }
                if (input.equals("exit")){
                    return "exit";
                }
                //loops through the list of allUsers in the system
                for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
                    //checks if the entered username already exists
                    if (temp.get(0).equals(allUsers.getAllUsers().get(i).getName())) {
                        System.out.print("Username Already exists. Please choose a new username\n");
                        return authenticator(allUsers);
                    }
                }
                //if username doesn't already exist, it will create a user and returns it
                allUsers.createUser(temp.get(0), temp.get(1));
                return allUsers.getAllUsers().get(allUsers.getAllUsers().size() - 1);
                }
                //user wants to login to their account
            else if (input.equals("login")) {
                while (!input.equals("exit") && curr < 2) {
                    if (prompts.hasNext()) {
                        System.out.println(prompts.next());
                    }
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        temp.add(input);
                        curr++;
                    }
                }
                if (input.equals("exit")) {

                    return input;
                }
                //check if temp exists in allUsers
                for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
                    if (allUsers.getAllUsers().get(i).getName().equals(temp.get(0)))
                        if (allUsers.getAllUsers().get(i).getPassword().equals(temp.get(1))) {
                            System.out.println("Successful Login");
                            return (allUsers.getAllUsers().get(i));
                        }
                }
                //if user doesn't exist
                System.out.println("Wrong username or password. Please try Again");
                return authenticator(allUsers);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return authenticator(allUsers);
    }

    public Object wishlist(User user, UserManager allUsers) {
        List<Item> wishlist = user.getWishlist();
        if (wishlist.size() == 0)
            System.out.print("Your wishlist is empty!\n");
        else {
            System.out.print("\uD83C\uDF20Your wishlist: \n");
            for (int i = 0; i < wishlist.size(); i++) {
                System.out.println(Integer.toString(i+1) + ". " + wishlist.get(i).getName() + " : " + wishlist.get(i).getDescription());
            }
        }
        if (wishlist.size() == 0)
            return user;

        Scanner sc = new Scanner(System.in);
        System.out.print("If you would like to remove an item, please enter the ID of the item you would like to remove or type 'back'\n");

        Object input = sc.nextLine();
        if (input.equals("back")){
            return "back";
        }
        try {
            input = Integer.parseInt((String) input);
        } catch(NumberFormatException e) {
            return null;
        }





        //remove from wishlist
        allUsers.removeFromWishlist(allUsers.getUser(user), wishlist.get((Integer) input));




        return user;
    }

    public User inventory(User user,UserManager allUsers) {
        User me = allUsers.getUser(user);
        List<Item> in = me.getInventory();
        if (in.size() == 0)
            System.out.print("Your inventory is empty!\n\n");
        else {
            System.out.print("Your inventory: \n\n");
            for (int i = 0; i < in.size(); i++) {
                System.out.println("\uD83D\uDCE6" + in.get(i).getName());
            }
        }
        return user;
    }

    public Object Browse(User user, ItemManager allItems, UserManager allUsers) {
        // "1. Sock : white clear "

        List<Item> allItems2 = allItems.getSystemInventory();
        if (allItems2.size() == 0){
            System.out.println("There are no items to browse!");
            return "back";
        }
        for (int i = 0; i < allItems2.size(); i++) {
            System.out.println("\uD83D\uDCE6" + (i + 1) + ". " + allItems2.get(i).getName() + " : "
                    + allItems2.get(i).getDescription() + "\n");
        }
        System.out.print("Enter ID of the item you would like to add to your wishlist or type 'back' to get to main menu.\n");

        Scanner sc = new Scanner(System.in);
        Object input = sc.nextLine();
        if (input.equals("back")){
            return "back";
        }
        try {
            input = Integer.parseInt((String) input);
        } catch(NumberFormatException e) {
            return null;
        }

        Item item = allItems2.get((Integer) input -1);
        allUsers.addToWishlist (user, item);
        System.out.print ("Item has been added to your wishlist \uD83C\uDF20\n");

        return "back";
    }

    public Object Trade(User user, ItemManager allItems, TradeRequestManager allTradeRequests, UserManager allUsers) {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("Please type in the ID of the item you would like to trade or 'back' to return to the main menu.\n");
        Object inum = sc.nextLine();
        if (inum.equals("back")){
            return "back";
        }
        try {
            inum = Integer.parseInt((String) inum);
        } catch(NumberFormatException e) {
            return null;
        }

        Item tradeItem = allItems.getSystemInventory().get((Integer) inum - 1);
        List<Item> myList = new ArrayList<>();
        myList.add(tradeItem);

        System.out.println("Please type '1' for one way trade and '2' for two way trade or 'back' to cancel the " +
                "current trade and restart.");
        Object tType = sc.nextLine();
        if (tType.equals("back")){
            return null;
        }
        try {
            tType = Integer.parseInt((String) tType);
        } catch(NumberFormatException e) {
            //brings them back to the first prompt where it asks to type the ID of the item.
            System.out.print("Invalid response. Please try again.\n");
            return null;
        }

        System.out.println("Please type '1' for temporary trade and '2' for permanent trade or 'back' to cancel the " +
                "current trade and restart.");
        Object type = sc.nextLine();
        if (type.equals("back")){
            return null;
        }
        try {
            type = Integer.parseInt((String) type);
        } catch(NumberFormatException e) {
            System.out.print("Invalid response. Please try again.\n");
            return null;
        }
        boolean trade = false;

        if ((int) type == 1) {
            trade = true;
        } else if ((int) type == 2) {
            trade = false;
        } else {
            System.out.print("Invalid response. Please try again.\n");
            return null;
        }

        System.out.print("Please put a message for the owner of the item:\n");
        String message = sc.nextLine();
        if ((int) tType == 1) { //1 way trade, do the following
            System.out.print("\nYou have selected 1 way trade for item: " + tradeItem.getName() + "\nYour message was: " + message + "\n");
            System.out.print("\nPlease enter '1' to confirm this trade or '2' to cancel the current trade and restart.\n");
            String confirmation = sc.nextLine();
            if (confirmation.equals("1")) {
                TradeRequest trades = new TradeRequest(1, user, tradeItem.getOwner(), myList, message, trade);
                allTradeRequests.receiveTradeRequest(allUsers, trades);
                System.out.print("\nTrade request has been sent successfully.\n");
                return "back";
            } else if (confirmation.equals("2")){
                System.out.print("\nTrade has been cancelled.\n");
                return null;
            } else{
                System.out.print("Invalid response. Please try again.\n");
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
                System.out.print("Trade initiation has been cancelled.\n");
                return null;
            }

            System.out.print("Your inventory: \n");
            for (int i = 0; i < in.size(); i++) {
                System.out.println(i+1 + ". " + in.get(i).getName());
            }

            Object inum2 = sc.nextLine();
            //returns them to the first prompt where they enter the id of the item to trade
            if (inum2.equals("back")){
                return null;
            }
           //should be able to select what item
            try {
                inum2 = Integer.parseInt((String) inum2);
            } catch (NumberFormatException e) {
                System.out.print("Invalid response. Please try again.\n");
                return null;
            }
            Item salam = in.get((int) inum2-1);
            System.out.println("The item you want to trade is: " + in.get((int)inum2-1).getName() + "\n");
            System.out.print("Please enter '1' to confirm this trade or '2' to cancel the current trade and restart.");
            String confirmation = sc.nextLine();
            if (confirmation.equals("1")) {
                myList.add(salam);
                myList.add(tradeItem);
                TradeRequest request = new TradeRequest(2, user, tradeItem.getOwner(), myList, message, trade);
                allTradeRequests.receiveTradeRequest(allUsers, request);
                System.out.print("\nTrade request has been sent successfully.\n");
                return "back";
            } else if (confirmation.equals("2")){
                System.out.print("\nTrade has been cancelled.\n");
                return null;
            } else {
                System.out.print("Invalid response. Please try again.\n");
                return null;
            }

        } else {
            System.out.println("Invalid response. Please try again.\n");
            return null;
        }
    }

    public User Messages(User user,UserManager allUsers) {
        System.out.print("What would you like to see? Type 'Trade' for Trade Requests or " +
                "type 'Meeting' for meeting requests. Type 'back' to go back to the main menu.\n");
        Scanner sc1 = new Scanner(System.in);
        String input = sc1.next();

        if (input.equals("back"))
            return user;
        if (input.equals("Meeting")) {
            System.out.print("\nHere are your pending meetings: \n");
            //implement loop that gets pending meetings !!!
        } else if (input.equals("Trade")) {
            System.out.print("\nHere are your pending Trades: \n");
            User Person = allUsers.getUser(user);
            if (Person.getPendingRequests().size() == 0) {
                System.out.print("\nYou have no pending requests.\n");
                return user;
            } else {
            for (int i = 0; i < Person.getPendingRequests().size(); i++) {
                String ext = "";
                if (Person.getPendingRequests().get(i).getRequesterItem() != null){
                    ext = " With your item " + Person.getPendingRequests().get(i).getRequesterItem().getName();
                }
                System.out.print("\uD83E\uDD1D" + Person.getPendingRequests().get(i).getRequester().getName() +
                        " is requesting a trade for item: "
                        + Person.getPendingRequests().get(i).getReceiverItem().getName() + " With Message: " +
                        Person.getPendingRequests().get(i).getMessage() + ext+ "\n");
            }
        }
        } else {
            System.out.print("Invalid command.\n\n");
            return user;
        }
        return user;
    }

    public void RejectTrade(User user,UserManager allUsers /*, MeetingManager allMeetings*/){
        System.out.print("\u274E Rejected!\n");
    }

    public void ApprovedTrade(User user,UserManager allUsers, MeetingManager allMeetings, TradeRequest request, TransactionManager allTransactions){
//        System.out.print("\u2705 Approved!\n");
//        //if the trade request is approved, we should now start a trade and make a meeting
//        Meeting meeting = MeetingInitiator (user, allUsers, allMeetings);
//        User temp1 = request.getRequester();
//        User temp2 = request.getReceiver();
//        allUsers.removeFromPendingRequests(allUsers.getUser(user), request);
//        if (request.getRequestType() == 1){ //1 way'
//            OneWay on = new OneWay(temp1, request.getReceiverItem(), request.getTemp());
//            on.setInitialMeeting(meeting);
//            allTransactions.receiveTransaction(on);
//        }
//        else if (request.getRequestType() == 2) { //2way
//            TwoWay on = new TwoWay(request.getRequesterItem(), request.getReceiverItem(), request.getTemp());
//            on.setInitialMeeting(meeting);
//            allTransactions.receiveTransaction(on);
//        }
    }

    public Meeting MeetingInitiator (User user,UserManager allUsers, MeetingManager allMeetings){
        System.out.print("\uD83D\uDCC5 Please enter your proposed date for this trade in format dd-mm-yyyy\n");
        Scanner sc = new Scanner(System.in);
        String date = sc.nextLine();
        System.out.print("\uD83D\uDCC5 Please enter your proposed time for this trade in format hh:mm\n");
        String time = sc.nextLine();
        System.out.print("\uD83D\uDCC5 Please enter your proposed location for this trade\n");
        String location = sc.nextLine();
        Meeting meeting = allMeetings.createMeeting(date,  time,  location);

        return meeting;

    }

    public Object ApproveTrade(User user,UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions){
        User Person = allUsers.getUser(user);
        List <TradeRequest> Trades = Person.getPendingRequests();
        if (Trades.size() == 0){
            System.out.println("There are no pending trade requests!");
            return "back";
        }
        System.out.print("Here are your pending trade requests: \n");
        for (int i = 0; i < Trades.size(); i++){
            String ext = "";
            if (Person.getPendingRequests().get(i).getRequesterItem() != null){
                ext = " With your item " + Person.getPendingRequests().get(i).getRequesterItem().getName();
            }

            System.out.print("\uD83E\uDD1D" +(i+1) + ". " + Trades.get(i).getRequester().getName() +
                    " For your item: "+ Trades.get(i).getReceiverItem().getName() + ext+ "\n");
        }

        //printed all pending requests
        //select request
        System.out.print("\n");
        System.out.print("\u2754 Please type the ID of the trade you would like to view or 'back' to return to the main menu.\n");
        Scanner sc = new Scanner(System.in);
        Object input = sc.next();

        if (input.equals(new String ("back"))){
            return "back";
        }
        int pendingRequestIndex;

        try{ input = Integer.parseInt((String) input);
            pendingRequestIndex = (Integer) input - 1;
        } catch (NumberFormatException e) {
            return null;
        }

        System.out.print("You have selected the following pending trade: \n");
        String ext2 = "";
        String temp = "Permanent";
        if (Person.getPendingRequests().get(pendingRequestIndex).getRequesterItem() != null){
            ext2 = " With your item " + Person.getPendingRequests().get(pendingRequestIndex).getRequesterItem().getName();
        }

        if (Person.getPendingRequests().get(pendingRequestIndex).getTemp()) { //if temporary
            temp = "Temporary";
        }
        System.out.println("\uD83D\uDFE9 Requester: " + Trades.get(pendingRequestIndex).getRequester().getName() + "\n" +
                "For item: " + Trades.get(pendingRequestIndex).getReceiverItem().getName() + ext2 +
                "\n\uD83D\uDCACMessage:" + Trades.get(pendingRequestIndex).getMessage() +
                "\n\nThis trade will be " + temp + ".\n");

        TradeRequest request =  Person.getPendingRequests().get(pendingRequestIndex);

        System.out.println("\nPlease enter '1' to approve the trade or '2' to reject the trade. Enter 'back' to return to " +
                "your pending trade requests.");

        Object nextInput = sc.next();
        if (nextInput.equals("back")){
            return null;
        } else if (nextInput.equals("1")) { //if trade is approved
            ApprovedTrade( user, allUsers, allMeetings, request,  allTransactions);
            return null;
        } else if (nextInput.equals("2")) { //if item is rejected
            RejectTrade( user, allUsers);
            return null;
        } else {
            System.out.print("\uD83E\uDDD0 What was that? Please try again!\n");
            return null;
        }
        //select if you want to approve or reject
        //method that handles approve or reject
    }

    public User AddItem(User user, UserManager allUsers){
        System.out.print("Please enter the name of item you would like to add\n");
        Scanner sc = new Scanner(System.in);
        String itemName = sc.nextLine();
        System.out.print("Please enter the description of item you would like to add\n");
        String description = sc.nextLine();
        Item newItem = new Item(itemName, user, description);
        allUsers.addToDraftInventory(user, newItem);
        System.out.print("\n \u2705 Your request is sent to admin for approval!\n");
        allUsers.addToItemHistory(user, newItem);

        return user;
    }

    public User Top3TradingPartners(User user, UserManager allUsers) {
        List<User> top3TP = new ArrayList<>();
        top3TP = user.getTopTradingPartners();
        System.out.print("Here are your top 3 most frequent trading partners:\n");
        if (top3TP.size() == 0) {
            System.out.print("\uD83D\uDE25 Sorry! You have no trading partners! You should do some trades!\n");
        }
        for (int i = 0; i < top3TP.size(); i++) {
            System.out.print("\uD83D\uDC51" + Integer.toString(i + 1) + ". " + top3TP.get(i).getName() + "\n");

        }
    return user;
    }

    public User MostRecentTrades(User user, UserManager allUsers) {
        List<Item> MostRecent = new ArrayList<>();
        MostRecent = allUsers.getRecentlyTradedItems(user);
        System.out.print("Here are your most recently traded-away items:\n");
        if (MostRecent.size() == 0) {
            System.out.print("\uD83D\uDE25 Sorry! You have no recent trades! You should do some trades!\n");
        }
        for (int i = 0; i < MostRecent.size(); i++) {
            System.out.print("\uD83D\uDC51" + Integer.toString(i + 1) + ". " + MostRecent.get(i).getName() + " : " + MostRecent.get(i).getDescription() + "\n");

        }
        return user;
    }


    public User ViewItemHistory (User user, UserManager allUsers){
        if (allUsers.getUser(user).getItemHistory().size() == 0) {
            System.out.println ("\uD83D\uDE25 No items here! Please add an item to your inventory!");
            return user;

        }
        for(Map.Entry m:allUsers.getUser(user).getItemHistory().entrySet()){
            Item object = (Item) m.getKey();
            System.out.println(object.getName()+": "+m.getValue());
        }
        return user;

    }

    public User addToWishlist ( User user, UserManager allUsers){
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print ("Please enter the name of the item you would like to add to your wishlist:\n");
        String name = sc.nextLine();
        System.out.print ("Please enter the description of the item:\n");
        String Description = sc.nextLine();
        Item wishlistItem = new Item(name , null , Description);
        allUsers.addToWishlist(user, wishlistItem);
        System.out.print ("Item has been added to your wishlist \uD83C\uDF20\n");

    return user;
    }


    public Object mainMenu(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions ) {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + user.getName() + "!\n");


        //A frozen account is one where you can log in and look for items, but you cannot arrange any transactions.
        // A user who has been frozen can request that the administrative user unfreezes their account.
        boolean frozenAccount = user.getIsFrozen();

        if (frozenAccount) { //first off, tell them that they are frozen
            System.out.print("\uD83E\uDD76 Your account is frozen!" +
                    " You are not able to do any trades until you are unfrozen by admin.\n \uD83C\uDFC1 Please ask Admin to unfreeze your account!\n\n");

            System.out.print("Please select number from the following:\n1.View Wishlist\n2.View Inventory\n" +
                    "3.Browse Items\n" +
                    "4.Add Item to inventory\n5.View most recent trades\n6.View most frequent trading partners\n7. View status of my items\n8. Add Item to wishlist" +
                    "\n9. Request unfreeze!\n10. Logout" + "\nEnter 'exit' to exit the system at any time.\n");
            String a = sc.nextLine();
            if (!a.equals("exit")) {
                if (a.equals("1")) {
                    //view wishlist
                    return system1.wishlist(user,  allUsers);
                } else if (a.equals("2")) {
                    //view inventory
                    return system1.inventory(user, allUsers);
                } else if (a.equals("3")) {
                    //browse items
                    //return system1.Browse(user, allItems, allUsers);
                    Object temp = system1.Browse(user, allItems, allUsers);
                    while (temp == null) {
                        temp = system1.Browse(user, allItems, allUsers);
                    }
                    return user;
                }
                } else if (a.equals("5")) { //View most recent trades
                    MostRecentTrades(user, allUsers);
                } else if (a.equals("6")) { //View most frequent trading partners
                    return Top3TradingPartners(user, allUsers);


                    //else input was "back", returns to main menu

                } else if (a.equals("4")) {
                    //request to add new item
                    return system1.AddItem(user, allUsers);
                } else if (a.equals("7")) {
                    return system1.ViewItemHistory(user, allUsers);
                } else if (a.equals("8")) {
                    return system1.addToWishlist(user, allUsers);


                }
            else if (a.equals("9")){



            }
            else if (a.equals("10")) {
                    //logout
                    return null;
                }
            return user;
        }



         else {


            if (allUsers.getUser(user).getPendingRequests().size() > 0) {
                System.out.print("\uD83D\uDCE9 You have " + allUsers.getUser(user).getPendingRequests().size() +
                        " Pending Trade Requests!\n");
            }


            System.out.print("Please select number from the following:\n1.View Wishlist\n2.View Inventory\n" +
                    "3.Browse Items\n4.Initiate Trade\n5.View Messages\n6.Approve Pending Trades\n" +
                    "7.Add Item to inventory\n8.View most recent trades\n9.View most frequent trading partners\n10. View status of my items\n11. Add Item to wishlist" +
                    "12.View Approved Trades\n13. Logout" + "\nEnter 'exit' to exit the system at any time.\n");

            String a = sc.nextLine();
            if (!a.equals("exit")) {
                if (a.equals("1")) {
                    //view wishlist
                    return system1.wishlist(user, allUsers);
                } else if (a.equals("2")) {
                    //view inventory
                    return system1.inventory(user, allUsers);
                } else if (a.equals("3")) {
                    //browse items
                    //return system1.Browse(user, allItems, allUsers);
                    Object temp = system1.Browse(user, allItems, allUsers);
                    while (temp == null) {
                        temp = system1.Browse(user, allItems, allUsers);
                    }
                    return user;
                } else if (a.equals("4")) {
                    //choose the id?
                    Object temp = system1.Trade(user, allItems, allTradeRequests, allUsers);
                    while (temp == null) {
                        temp = system1.Trade(user, allItems, allTradeRequests, allUsers);
                    }
                    //else input was "back", returns to main menu
                    return user;
                } else if (a.equals("5")) {
                    //view messages
                    return system1.Messages(user, allUsers);
                } else if (a.equals("8")) { //View most recent trades
                    MostRecentTrades(user, allUsers);
                } else if (a.equals("9")) { //View most frequent trading partners
                    return Top3TradingPartners(user, allUsers);

                } else if (a.equals("6")) {
                    Object temp = system1.ApproveTrade(user, allUsers, allMeetings, allTransactions);
                    while (temp == null) {
                        temp = system1.ApproveTrade(user, allUsers, allMeetings, allTransactions);
                    }
                    //else input was "back", returns to main menu
                    return user;
                } else if (a.equals("7")) {
                    //request to add new item
                    return system1.AddItem(user, allUsers);
                } else if (a.equals("10")) {
                    return system1.ViewItemHistory(user, allUsers);
                } else if (a.equals("11")) {
                    return system1.addToWishlist(user, allUsers);


                } else if (a.equals("13")) {
                    //logout
                    return null;
                }
            }
            //input is "exit"
            else {
                System.out.print("Goodbye!\uD83D\uDEAA \n");
                return a;
            }
            return user;
        }
    }
        }


