import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class InputGetter {


    public User authenticator(UserManager allUsers) {


        //we fill out allUsers with whatever is in csv file
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;


        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Type 'signup' to create an account or 'login' to access your account or 'exit' to exit at anytime.");
        try {
            String input = br.readLine();
            if (input.equals("signup")) {
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
                for (int i =0; i < allUsers.getAllUsers().size(); i++){
                    if (temp.get(0).equals(allUsers.getAllUsers().get(i).getName())){
                        System.out.print("Username Already exists. Please choose a new username\n");
                        return authenticator(allUsers);
                    }
                    else{
                        allUsers.createUser(temp.get(0), temp.get(1));
                        return (allUsers.getAllUsers().get(allUsers.getAllUsers().size() - 1));}
                }
            } else if (input.equals("login")) {
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
                User TempUser = new User(temp.get(0), temp.get(1));
                boolean iExist = false;
                //check if temp exists in allusers
                for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
                    if (allUsers.getAllUsers().get(i).getName().equals(temp.get(0)))
                        if (allUsers.getAllUsers().get(i).getPassword().equals(temp.get(1))) {
                            iExist = true;
                            System.out.println("Successful Login");
                            return (allUsers.getAllUsers().get(i));
                        }
                }
                //if user doesnt exist
                System.out.println("Wrong username or password. Please try Again");
                //curr = 0;
                return authenticator(allUsers);
            }
            return authenticator(allUsers);
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
//it will only get here if the person pressed exit
        return null;
    }

    public void wishlist(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        List<Item> wishlist = user.getWishlist();
        if (wishlist.size() == 0)
            System.out.print("List is empty!\n\n");
        else {
            System.out.print("Your wishlist: \n\n");
            for (int i = 0; i < wishlist.size(); i++) {
                System.out.println(wishlist.get(i).getName());
            }
        }
        system1.mainMenu(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
    }


    public void inventory(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        List<Item> in = user.getInventory();
        if (in.size() == 0)
            System.out.print("List is empty!\n\n");
        else {
            System.out.print("Your inventory: \n\n");
            for (int i = 0; i < in.size(); i++) {
                System.out.println(in.get(i).getName());
            }
        }
        system1.mainMenu(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
    }

    public void Browse(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        // "1. Sock : white clear "
        List<Item> allItems2 = allItems.getSystemInventory();
        for (int i = 0; i < allItems2.size(); i++) {
            System.out.println(i + 1 + ". " + allItems2.get(i).getName() + " : " + allItems2.get(i).getDescription() + "\n");
        }
        system1.mainMenu(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
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


        if (tType == 1) {
            System.out.println("You have selected 1 way trade for item: " + tradeItem.getName() + "\nyour message was:\n" + message);
            TradeRequest trades = new TradeRequest(1, user, tradeItem.getOwner(), mylist, message, trade);
            allTradeRequests.receiveTradeRequest(allUsers, trades);
            //trade request is made and it is pending in user's pendingRequests


        } else if (tType == 2) {
            System.out.println("You have selected 2 way trade for item: " + tradeItem.getName() + "\nyour message was:\n" + message);

        } else {
            System.out.println("Invalid Request");

        }

    }

    public void Messages(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        System.out.print("\nWhat would you like to see? Type 'Trade' for Trade Requests and type 'Meeting' for meeting requests\n");
        Scanner sc1 = new Scanner(System.in);
        String input = sc1.next();
        if (input.equals("Meeting")) {
            System.out.print("\nHere are your pending meetings");
        } else if (input.equals("Trade")) {
            System.out.print("\nHere are your pending Trades\n");
            User Person = allUsers.getUser(user);
            if (Person.getPendingRequests().size() == 0)
                return;
            else {
            for (int i = 0; i < Person.getPendingRequests().size(); i++) {

                //QUESTION : WHICH ONE SHOULD I USE GET RECIEVER OR GET REQUESTER
                System.out.print("Person: " + Person.getPendingRequests().get(i).getRequester().getName() + " Is requesting for item: "
                        + Person.getPendingRequests().get(i).getReceiverItem().getName() + " With Message: " + Person.getPendingRequests().get(i).getMessage());
            }
        } } else {
            System.out.print("Invalid command.  \n\n");
            mainMenu(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
        }
    }

    public User mainMenu(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests, UserManager allUsers, AdminManager allAdmins) {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("----------------------------------------------------------------------------------------------\nWelcome " + user.getName() + "\n");
        System.out.print("Please select from the following: \n 'View Wishlist' \n 'View Inventory' \n 'Browse' \n 'Trade' \n 'Messages'\n 'Log out' \n");
        String a = sc.nextLine();
        while (!a.equals("Exit")) {
            if (a.equals("View Wishlist")) {
                system1.wishlist(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            }
            else if (a.equals("View Inventory")) {
                system1.inventory(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            } else if (a.equals("Browse")) {
                system1.Browse(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            } else if (a.equals("Log out")) {
                //implement logout
                break;
            } else if (a.equals("Trade")) {
                //choose the id?
                system1.Trade(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
                //mainMenu(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
                return user;
            } else if (a.equals("Messages")) {
                system1.Messages(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            }
            //mainMenu(user, allItems, system1, allTradeRequests, allUsers, allAdmins);
            return user;
        }
        System.out.print("your command was " + a + "\n");
        a = "";
        return null;


    }


    }

