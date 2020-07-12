import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
                    return new String("exit");
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

    public User wishlist(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        List<Item> wishlist = user.getWishlist();
        if (wishlist.size() == 0)
            System.out.print("List is empty!\n\n");
        else {
            System.out.print("Your wishlist: \n\n");
            for (int i = 0; i < wishlist.size(); i++) {
                System.out.println(wishlist.get(i).getName());
            }
        }
        //system1.mainMenu(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
        return user;
    }


    public User inventory(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        User me = allUsers.getUser(user);
        List<Item> in = me.getInventory();
        if (in.size() == 0)
            System.out.print("List is empty!\n\n");
        else {
            System.out.print("Your inventory: \n\n");
            for (int i = 0; i < in.size(); i++) {
                System.out.println("\uD83D\uDCE6" + in.get(i).getName());
            }
        }
        //system1.mainMenu(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
        return user;
    }

    public User Browse(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        // "1. Sock : white clear "
        List<Item> allItems2 = allItems.getSystemInventory();
        for (int i = 0; i < allItems2.size(); i++) {
            System.out.println("\uD83D\uDCE6" + Integer.toString(i+1) + ". " + allItems2.get(i).getName() + " : " + allItems2.get(i).getDescription() + "\n");
        }
        return user;
    }

    public void Trade(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("Please type in the ID of the item you would like to trade\n");
        int inum = Integer.parseInt(sc.next());
        Item tradeItem = allItems.getSystemInventory().get(inum - 1);
        List<Item> mylist = new ArrayList<>();
        mylist.add(tradeItem);

        System.out.print("\nPlease type '1' for one way trade and '2' for two way trade. \n");
        int tType = Integer.parseInt(sc.next());

        System.out.print("\nPlease type '1' for temporary trade and '2' for permanent trade' \n");
        int type = Integer.parseInt(sc.next());
        boolean trade = false;

        if (type == 1) {
            trade = true;
        } else if (type == 2) {
            trade = false;
        } else {
            System.out.println("Invalid Request");

        }
        System.out.print("Please put a message for the owner of the item\n");
        String message = sc.next();
        if (tType == 1) { //1 way trade, do the following
            System.out.println("You have selected 1 way trade for item: " + tradeItem.getName() + "\nyour message was:\n" + message);
            TradeRequest trades = new TradeRequest(1, user, tradeItem.getOwner(), mylist, message, trade);
            allTradeRequests.receiveTradeRequest(allUsers, trades);
            //trade request is made and it is pending in user's pendingRequests



        } else if (tType == 2) { //if its a two way trade, do the following:
            System.out.println("You have selected 2 way trade for item: " + tradeItem.getName() + "\nyour message was:\n" + message + "\n");
            System.out.println("Which item would you like to trade?\n Type in the number \n");

            User me = allUsers.getUser(user);
            List<Item> in = me.getInventory();
            if (in.size() == 0)
                System.out.print("List is empty!\n\n");
            else {
                System.out.print("Your inventory: \n\n");
                for (int i = 0; i < in.size(); i++) {
                    System.out.println(i+1 + ". " + in.get(i).getName());
                }
            }
           //should be able to select what item
            int inum2 = Integer.parseInt(sc.next());
            Item salam = in.get(inum2-1);
            System.out.println("the item you want to trade is: " + in.get(inum2-1).getName() + "\n");
            mylist.add(salam);
            mylist.add(tradeItem);
            TradeRequest request = new TradeRequest(2, user, tradeItem.getOwner(), mylist, message, trade);
            allTradeRequests.receiveTradeRequest(allUsers, request);



        } else {
            System.out.println("Invalid Request");
        }
    }

    public User Messages(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        System.out.print("What would you like to see? Type 'Trade' for Trade Requests and type 'Meeting' for meeting requests\n");
        Scanner sc1 = new Scanner(System.in);
        String input = sc1.next();
        if (input.equals("Meeting")) {
            System.out.print("\nHere are your pending meetings:\n");
        } else if (input.equals("Trade")) {
            System.out.print("\nHere are your pending Trades:\n");
            User Person = allUsers.getUser(user);
            if (Person.getPendingRequests().size() == 0) {
                System.out.print("\nYou have no pending requests.\n");
                return user;
            }
            else {
            for (int i = 0; i < Person.getPendingRequests().size(); i++) {
                String ext = "";
                if (Person.getPendingRequests().get(i).getRequesterItem() != null){
                    ext = " With your item " + Person.getPendingRequests().get(i).getRequesterItem().getName();
                }

                System.out.print("\uD83E\uDD1D" + Person.getPendingRequests().get(i).getRequester().getName() + " is requesting a trade for item: "
                        + Person.getPendingRequests().get(i).getReceiverItem().getName() + " With Message: " + Person.getPendingRequests().get(i).getMessage() + ext+ "\n");
            }
        } } else {
            System.out.print("Invalid command.  \n\n");
            return user;
        }
        return user;
    }

    public void RejectTrade (){
        System.out.print("\u274E Rejected!\n");
        return;
    }

    public void ApproveTrade (){
        System.out.print("\u2705 Approved!\n");
        return;
    }

    public User ApproveTrade(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins){
        User Person = allUsers.getUser(user);
        List <TradeRequest> Trades = Person.getPendingRequests();
        System.out.print("Here are your pending requests. Please select the request you would like to approve.\n");
        for (int i = 0; i < Trades.size(); i++){
            String ext = "";
            if (Person.getPendingRequests().get(i).getRequesterItem() != null){
                ext = " With your item " + Person.getPendingRequests().get(i).getRequesterItem().getName();
            }

            System.out.print("\uD83E\uDD1D" +Integer.toString(i+1) + ". " + Trades.get(i).getRequester().getName() + " For your item: "+ Trades.get(i).getReceiverItem().getName() + ext+ "\n");


        }
        //printed all pending requets
        //select request
        System.out.print("\n");
        System.out.print("\u2754 Please type the ID of the trade you would like to view\n");
        Scanner sc = new Scanner(System.in);
        Integer pendingTrade = Integer.parseInt(sc.next());
        int pendingRequestIndex = pendingTrade-1;

        System.out.print("You have selected the following pending trade:\n");
        String ext2 = "";
        String temp = "Permanent";
        if (Person.getPendingRequests().get(pendingRequestIndex).getRequesterItem() != null){
            ext2 = " With your item " + Person.getPendingRequests().get(pendingRequestIndex).getRequesterItem().getName();
        }

        if (Person.getPendingRequests().get(pendingRequestIndex).getTemp()) //if temporary
        temp = "Temporary";

        System.out.print("\uD83D\uDFE9 Requester: "+  Trades.get(pendingRequestIndex).getRequester().getName() + "\n" +
                "For item: " + Trades.get(pendingRequestIndex).getReceiverItem().getName()  + ext2 + "\n\uD83D\uDCACMessage:" + Trades.get(pendingRequestIndex).getMessage() +
                "\nThis trade will be " + temp);

        System.out.print("\nPlease select 1 for Approve and 2 for Reject\n"); //hey tina! you should prob implement a back option!


        int decision = 0;

        while (decision!= 1 && decision!= 2) {
            decision = Integer.parseInt(sc.next());
            if (decision == 1) { //if trade is approved
                ApproveTrade();
            } else if (decision == 2) { //if trade is rejected
                RejectTrade();
            } else {
                System.out.print("\uD83E\uDDD0 What was that? Please try again!\n" + "Enter 1 for Approve and 2 for Reject\n");
            }
        }
        //select if you want to approve or reject
        //method that handles approve or reject

        System.out.print("\n");
        return user;

    }

    public User AddItem(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins){

        System.out.print("Please enter the name of item you would like to add\n");
        Scanner sc = new Scanner(System.in);
        String itemName = sc.nextLine();
        System.out.print("Please enter the description of item you would like to add\n");
        String description = sc.nextLine();
        Item newItem = new Item(itemName, user, description);
        allUsers.addToDraftInventory(user, newItem);

        System.out.print("\n \u2705 Your request is sent to admin for approval!\n");
        return user;


    }




        public Object mainMenu(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("----------------------------------------------------------------------------------------------\n\uD83D\uDC4B Welcome " + user.getName() + "\n");
        if (allUsers.getUser(user).getPendingRequests().size() > 0){
            System.out.print("\uD83D\uDCE9 You have " + allUsers.getUser(user).getPendingRequests().size() + " Pending Trade Requests!\n");

        }
        System.out.print("Please select number from the following:\n1.View Wishlist     2.View Inventory     " +
                "3.Browse Items     \n4.Initiate Trade     5.View Messages     6.Approve Pending Trades\n" +
                "7. Add Item to inventory     8.Logout" + "\nor Enter 'exit' to exit the system at any time.\n");
        String a = sc.nextLine();
        if (!a.equals("exit")) {
            if (a.equals("1")) {
                return system1.wishlist(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            }
            else if (a.equals("2")) {
                return system1.inventory(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            } else if (a.equals("3")) {
                return system1.Browse(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            } else if (a.equals("8")) {
                //implement logout
                return null;

            } else if (a.equals("4")) {
                //choose the id?
                system1.Trade(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
                return user;
            } else if (a.equals("5")) {
                return system1.Messages(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            }
            else if (a.equals("6")){
                return system1.ApproveTrade(user, allItems, system1, allTradeRequests, allUsers, allAdmins);

            }
            else if (a.equals("7")){
                return system1.AddItem (user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            }

            return user;
        }
        //input is "exit"
        else {
            System.out.print("Goodbye!\uD83D\uDEAA\n");
            return a;
        }

    }


    }

