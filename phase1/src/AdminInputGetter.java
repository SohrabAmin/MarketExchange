import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Deals with the input of an Admin user-- particularly deals with the login system and displaying of main menu.
 */

public class AdminInputGetter {
    private List <User> frozenRequests = new ArrayList<User>();

    /**
     * Returns a list of all the Users in frozenRequest.
     * @return List<User> returns list of Users in frozen Request
     */
    public List<User> getFrozenRequests(){
        return frozenRequests;
    }
    /**
     * Reads input from user and lets them 'log in' to their account by verifying their username and password.
     *
     * @param allAdmins AdminManager which stores all the admin in the system.
     * @return returns an Admin which will be used to prompt the main menu in LogIn System
     */
    public Admin authenticator(AdminManager allAdmins) {

        //we fill out allUsers with whatever is in ser file
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;

        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Hello Admin. Please enter 'login' to log in, 'back' to return to the main page or " +
                "'exit' to exit at anytime.");
        try {
            String input = br.readLine();
            if (input.equals("login")) {
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
                for (int i = 0; i < allAdmins.getAllAdmins().size(); i++) {
                    if (allAdmins.getAllAdmins().get(i).getName().equals(temp.get(0)))
                        if (allAdmins.getAllAdmins().get(i).getPassword().equals(temp.get(1))) {
                            System.out.println("Successful Login");
                            return (allAdmins.getAllAdmins().get(i));
                        }
                }
                //if Admin doesn't exist
                System.out.println("Wrong username or password. Please try Again");
                return authenticator(allAdmins);
            }
            if (input.equals("back")) {
                return null;
            }
            else {
                return authenticator(allAdmins);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

    /**
     * Displays the main menu of an AdminUser and prompts the user for input.
     *
     * @param admin Account of the Admin.
     * @return depending on what the Admin inputs it will return different objects:
     *         returns Admin to TradeSystem() to either remain logged into the system and prompt mainMenu
     *         returns null to log out of the system and allow another Admin to log in
     *         returns String "exit" to tell TradeSystem() to end the program and save all the data before
     *         exiting the System
     */
    public Object mainMenu(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + admin.getName());

        List<Item> allPendingItems = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++){
            allPendingItems.addAll(allUsers.getAllUsers().get(i).getDraftInventory());
        }

        List<User> allFrozenUsers = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++){
            if (allUsers.getAllUsers().get(i).getIsFrozen() ) {
                //if getIsFrozen returns true for frozen accounts
                allFrozenUsers.add(allUsers.getAllUsers().get(i));
            }
        }
        List<User> possibleFrozenPeople = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++){
            if (allUsers.getAllUsers().get(i).getIsPseudoFrozen()) {
                //if getIsFrozen returns true for frozen accounts
                possibleFrozenPeople.add(allUsers.getAllUsers().get(i));
            }
        }
        if (frozenRequests.size() > 0)
            System.out.println("\uD83D\uDCF3 You have " + frozenRequests.size() + " Frozen user requests!");

        //if they have frozen users, show it here
        if (allFrozenUsers.size() > 0){
            System.out.println("\u2603 You have " + allFrozenUsers.size() + " Frozen users!");
        }

        //if they have pending items, show it here
        if (allPendingItems.size() > 0){
            System.out.println("\uD83D\uDCE9 You have " + allPendingItems.size() + " Pending Item Requests!");
        }
        System.out.println("Please select from the following by entering the number beside the option:" +
                " \n1.Add new admin\n2.Change system threshold \n" +
                "3.View items that need to be approved\n4.Freeze or unfreeze users\n5.Log out\n" +
                "Enter 'exit' to exit at any time.");

        try {
            String input = br.readLine();
            if (!input.equals("exit")) {
                if (input.equals("1")) {
                    Object temp = addAdmin(allAdmins);
                    //successful addition of a new admin
                    if (temp instanceof Admin) {
                        System.out.println("\nNew admin has been added successfully.\n");
                        return admin;
                        //user decided to exit
                    } else if (temp == null){
                        return null;
                        //user entered "back"
                    } else {
                        return mainMenu(admin, allAdmins, allUsers, allItems);
                    }
                } else if (input.equals("2")) {
                    Object temp = changeThreshold(allUsers, allAdmins);
                    while (temp == null){
                        temp = changeThreshold(allUsers, allAdmins);
                    }
                    //else input was "back", returns to main menu
                    return admin;
                } else if (input.equals("3")) {
                    Object temp = approvalPendingItems(allUsers, allItems);
                    while (temp == null){
                        temp = approvalPendingItems(allUsers, allItems);
                    }
                    //else input was "back", returns to main menu
                    return admin;
                } else if (input.equals("4")) { //freeze or unfreeze users
                    Object temp = FreezeOrUnfreeze(allUsers);
                    while (temp == null){
                        temp = FreezeOrUnfreeze(allUsers);
                    }
                    //else input was "back", returns to main menu
                    return admin;
                } else if (input.equals("5")) {
                    return null;
                } else {
                    return admin;
                }
            }
            return input;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        return admin;
    }

    /**
     * Allows admin to either view unfreeze requests, freeze and unfreeze users in the system.
     *
     * @param allUsers UserManager which stores all the users in the system
     * @return depending on what the Admin inputs it will return different objects:
     *         returns null to tell mainmenu() to call FreezeOrUnfreeze() again
     *         returns String "back" to tell mainmenu() to prompt main menu again so Admin can choose another
     *                 main menu option
     */
    public Object FreezeOrUnfreeze(UserManager allUsers){
        System.out.println("What would you like to do? Please select the number beside the option or enter " +
        "'back' to return to the main menu.");
        System.out.println("1.View unfreeze requests\n2.Unfreeze frozen users\n3.Freeze users");
        Scanner sc = new Scanner(System.in);
        Object input = sc.nextLine();
        if (input.equals("back")){
            return "back";
        } else if (input.equals("1")){
            Object temp = ViewUnfreezeRequests(allUsers);
            while (temp == null){
                temp = ViewUnfreezeRequests(allUsers);
            } //else input was "back", displays Freeze or Unfreeze screen
            return null;
        } else if (input.equals("2")){
            Object temp = Unfreeze(allUsers);
            while (temp == null){
                temp = Unfreeze(allUsers);
            } //else input was "back", displays Freeze or Unfreeze screen
            return null;
        } else if (input.equals("3")){
            Object temp = Freeze(allUsers);
            while (temp == null){
                temp = Freeze(allUsers);
            } //else input was "back", displays Freeze or Unfreeze screen
            return null;
        }
        System.out.println("Invalid input. Please try again!");
        return null;
    }

    /**
     * Allows admin to view unfreeze requests they have and allows them to unfreeze any of those Users if they so choose.
     * If they unfreeze a User, it will remove their request from frozenRequests and change their status isPseudoFrozen and
     * isFrozen to false.
     *
     * @param allUsers UserManager which stores all the users in the system
     * @return depending on what the Admin inputs it will return different objects:
     *         returns null to tell mainmenu() to call FreezeOrUnfreeze() again
     *         returns String "back" to tell mainmenu() to prompt main menu again so Admin can choose another
     *                 main menu option
     */
    public Object ViewUnfreezeRequests(UserManager allUsers){
        if (frozenRequests.size() == 0) {
            System.out.println("\nYou have no unfreeze requests!\n");
            return "back";
        }
        System.out.println("\nHere are freeze requests:");
        for (int i = 0 ; i < frozenRequests.size() ; i++){
            System.out.println((i+1) + " . " + frozenRequests.get(i).getName());
        }
        System.out.println("Please enter the number of the user you would like to unfreeze or 'back' to go back.");
        Scanner sc = new Scanner(System.in);
        Object line = sc.nextLine();
        if (line.equals("back")) {
            return "back";
        } else {
            try {
                line = Integer.parseInt((String) line);
            } catch (NumberFormatException e) {
                return null;
            }
            allUsers.unfreeze(frozenRequests.get((Integer) line - 1));
            allUsers.unPseudoFreeze(frozenRequests.get((Integer) line - 1));
            System.out.print("\u2705 Successfully unfrozen user: " +
                    allUsers.getUser(frozenRequests.get((Integer) line - 1)).getName() + "\n");
            frozenRequests.remove(frozenRequests.get((Integer) line - 1));
            return null; //brings them back to the list of unfreeze requests
        }
    }

    /**
     * Displays a list of all frozen and pseudo frozen Users in UserManager and prompts Admin to input
     * which User they wish to unfreeze and unfreezes those Users
     *
     * @param allUsers UserManager which stores all the Users in the system
     * @return depending on what the Admin inputs it will return different objects:
     *         returns null to tell FreezeOrUnfreeze() to call Unfreeze() again
     *         returns String "back" to tell FreezeOrUnfreeze() to prompt main menu again so Admin can choose another
     *                 main menu option
     */
    public Object Unfreeze(UserManager allUsers){
        List <User> frozenUsers = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++){
            if (allUsers.getAllUsers().get(i).getIsFrozen() || allUsers.getAllUsers().get(i).getIsPseudoFrozen()) {
                //if getIsFrozen returns true for frozen accounts
                // or if getIsPseudoFrozen returns true for pseudo frozen users
            frozenUsers.add(allUsers.getAllUsers().get(i));
            }
        } if (frozenUsers.size() == 0){
            System.out.println("\nThere are no frozen users!\n");
            return "back";
        }
        System.out.println("\nHere are the current frozen users:");
        for (int i = 0 ; i < frozenUsers.size(); i++){
            String reqUnfreeze = "";
            for (User frozenRequest : frozenRequests) {
                if (frozenRequest.getName().equals(frozenUsers.get(i).getName())) {
                    reqUnfreeze = "[REQUESTING UNFREEZE]";
                }
            }
            System.out.println((i + 1) + ". " + frozenUsers.get(i).getName() + " " + reqUnfreeze);
        }
        System.out.println("Please enter the number of the user you would like to unfreeze or 'back' to go back.");
        Scanner sc = new Scanner(System.in);
        Object line = sc.nextLine();
        if (line.equals("back")) {
            return "back";
        } else {
            try {
                line = Integer.parseInt((String) line);
            } catch (NumberFormatException e) {
                return null;
            }
            User chosenUser = frozenUsers.get((Integer) line - 1);
            allUsers.unPseudoFreeze(chosenUser);
            allUsers.unfreeze(chosenUser);
            //if they requested to be unfrozen, they will be removed from the frozen request list
            for (int i=0 ; i < frozenRequests.size(); i++){
                if (frozenRequests.get(i).getName().equals(chosenUser.getName())) {
                    removeFromfrozenRequest(chosenUser);
                }
            }
            System.out.println("\n\u2705 Successfully unfrozen user: " +
                    allUsers.getUser(chosenUser).getName());
            if ((frozenUsers.size() - 1) == 0){
                System.out.println("\nThere are no more frozen Users!\n");
                return "back";
            }
            return null;
        }
    }

    /**
     * Displays a list of all unfrozen and pseudo frozen Users in UserManager and prompts Admin to input
     * which User they wish to freeze and freezes those Users
     *
     * @param allUsers UserManager which stores all the Users in the system
     * @return depending on what the Admin inputs it will return different objects:
     *         returns null to tell FreezeOrUnfreeze() to call Freeze() again
     *         returns String "back" to tell FreezeOrUnfreeze() to prompt main menu again so Admin can choose another
     *                 main menu option
     */
    public Object Freeze(UserManager allUsers){
        List <User> unfrozenUsers = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++){
            if (!allUsers.getAllUsers().get(i).getIsFrozen() || allUsers.getAllUsers().get(i).getIsPseudoFrozen()) {
                //if getIsFrozen is not true or if getIsPseudoFrozen returns true for pseudo frozen users
                unfrozenUsers.add(allUsers.getAllUsers().get(i));
            }
        } if (unfrozenUsers.size() == 0){
            System.out.println("\nThere are no non-frozen users!\n");
            return "back";
        }
        System.out.println("\nHere are the current non-frozen users:");
        for (int i = 0 ; i < unfrozenUsers.size(); i++){
            String reqUnfreeze = "";
            for (User frozenRequest : frozenRequests) {
                if (frozenRequest.getName().equals(unfrozenUsers.get(i).getName())) {
                    reqUnfreeze = "[REQUESTING UNFREEZE]";
                }
            }
            System.out.println((i + 1) + ". " + unfrozenUsers.get(i).getName() + " " + reqUnfreeze);
        }
        System.out.println("\nPlease enter the number of the user you would like to freeze or 'back' to go back.");
        Scanner sc = new Scanner(System.in);
        Object line = sc.nextLine();
        if (line.equals("back")) {
            return "back";
        } else {
            try {
                line = Integer.parseInt((String) line);
            } catch (NumberFormatException e) {
                return null;
            }
            User chosenUser = unfrozenUsers.get((Integer) line - 1);
            if (chosenUser.getIsPseudoFrozen()) { //user is pseudo frozen
                allUsers.unPseudoFreeze(chosenUser);
                allUsers.freeze(chosenUser);
            } else { //user is not pseudo frozen
                allUsers.freeze(chosenUser);
            }
            //users will need to re-request to be unfrozen if they go from pseudo-frozen to actually frozen
            for (int i=0 ; i < frozenRequests.size(); i++){
                if (frozenRequests.get(i).getName().equals(chosenUser.getName())) {
                    removeFromfrozenRequest(chosenUser);
                }
            }
            System.out.println("\n\u2705 Successfully frozen user: " +
                    allUsers.getUser(chosenUser).getName() + "\n");
            if ((unfrozenUsers.size() - 1) == 0){
                System.out.print("\nThere are no more non-frozen Users!\n");
                return "back";
            }
            return null;
        }
    }

    /**
     * Adds the User requesting to be unfrozen to the list of frozenRequests
     *
     * @param user the User requesting to be unfrozen
     */
    public void addfrozenRequest (User user){
        frozenRequests.add(user);
    }

    /**
     * Removes the user from the frozenRequests list
     *
     * @param user the User that has been removed from the list of frozenRequests
     */
    public void removeFromfrozenRequest (User user){
        frozenRequests.remove(user);
    }

    /**
     * Checks for items pending approval from all Users in UserManager and displays them to admin
     * who can then decide to approve the item which will move the item to the User's inventory
     * or reject the item which will remove the item altogether.
     *
     * @param allUsers UserManager which holds all the Users in the system
     * @return depending on what the Admin inputs it will return different objects:
     *         returns null to tell mainmenu() to call approvalPendingItems() again
     *         returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     *                 main menu option
     */
    public Object approvalPendingItems(UserManager allUsers, ItemManager allItems) {
        //Creates a list of all pending items in the system
        List<Item> allPendingItems = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
            allPendingItems.addAll(allUsers.getAllUsers().get(i).getDraftInventory());
        }
        //no pending items
        if (allPendingItems.size() == 0){
            System.out.println("There are no pending item requests to approve!\n");
            return "back";
        }
        //prints out all the pending items in the system
        System.out.println("Here are the pending items:");
        for (int i = 0; i < allPendingItems.size(); i++) {
            String j = Integer.toString(i + 1);
            System.out.println(j + ". " + allPendingItems.get(i).getName() + " : " + allPendingItems.get(i).getDescription());
        }
        //admin needs to choose an item to approve or reject
        System.out.println("Please enter the number of the item you would like to approve or 'back' to return to the main menu.");
        Scanner sc = new Scanner(System.in);
        Object input = sc.nextLine();
        if (input.equals("back")) {
            return "back";
        }
        try {
            input = Integer.parseInt((String) input);
        } catch (NumberFormatException e) {
            System.out.print("\n \uD83E\uDDD0 What was that? Please try again!\n");
            return null;
        }

        int pendingItemIndex = (Integer) input - 1;
        Item chosenItem = allPendingItems.get(pendingItemIndex);
        System.out.print("Input '1' to approve or input '2' to reject or 'back' to return to the list of pending items.\n");

        Object nextInput = sc.nextLine();
        if (nextInput.equals("back")){
            return null;
        }
        try {
            nextInput = Integer.parseInt((String) nextInput);
        } catch (NumberFormatException e) {
            System.out.println("\uD83E\uDDD0 What was that? Please try again!");
            return null;
        }
        if ((Integer) nextInput == 1) { //if item is approved
            allUsers.approveDraftInventoryItem(allUsers.getUser(chosenItem.getOwner()), chosenItem, allItems);
            System.out.println("\u2705 Approved!");

            return null;
        } else if ((Integer) nextInput == 2) { //if item is rejected
            allUsers.rejectDraftInventoryItem(allUsers.getUser(chosenItem.getOwner()), chosenItem);
            System.out.println("\u274E Rejected!\n");
            return null;
        } else {
            System.out.println("\uD83E\uDDD0 What was that? Please try again!");
            return null;
            }
    }

    /**
     * Changes the lentMinusBorrowedThreshold which dictates how much more a user has to have lent than borrowed,
     * before trading. The threshold change affects all Users in the system.
     *
     * @param allUsers changes LentMinusBorrowedThreshold variable in the system's UserManager
     * @param allAdmins changes LentMinusBorrowedThreshold variable in the system's AdminManager
     * @return depending on what the Admin inputs it will return different objects:
     *         returns null to tell mainmenu() to call changeThreshold() again
     *         returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     *                 main menu option
     */
    public Object changeThreshold(UserManager allUsers, AdminManager allAdmins) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //prints the current thresholds of the system
        System.out.println("\nHere are the current thresholds:");
        System.out.println("Lent - borrow threshold: " + allUsers.getLentMinusBorrowedThreshold());
        System.out.println("Weekly transaction limit: " + allUsers.getWeeklyTransactionLimit());
        System.out.println("Incomplete transaction limit: " + allUsers.getIncompleteTransactionLimit());
        System.out.println("Meeting edits threshold: " + allUsers.getMeetingEditThreshold());
        //prompts user to enter what threshold they wish to edit
        System.out.println("\nWhich threshold would you like to edit? Please enter the number beside the option.");
        System.out.println("1. Lent - borrow threshold\n2. Weekly Transaction Limit\n3. Incomplete Transaction Limit" +
                "4. Meeting Edits Threshold\n");
        System.out.println("Enter 'back' to return to the main menu.");
        Object thresholdOption = null;
        //checks to see if they entered a valid input (one of the options)
        try {
            String option = br.readLine();
            if (option.equals("back")) {
                return "back";
            } else {
                try {
                    thresholdOption = Integer.parseInt(option);
                } catch (NumberFormatException e) {
                    thresholdOption = "boo!";
                }
            }
        } catch (IOException e) {
            System.out.println("Invalid input. Please try again.");
            return null;
        }
        //threshold option doesn't contain a number option
        if (!(thresholdOption instanceof Integer)) {
            System.out.println("Invalid input. Please try again.");
            return null;
        }
        //input was valid, now prompts admin to input what they want the new threshold to be
        System.out.println("Please enter the integer you wish to change the system threshold to or 'back' to the previous page.");
        Object newThreshold = null;
        //checks to see if they entered a valid input (one of the options)
        try {
            String input = br.readLine();
            if (input.equals("back")) {
                return null;
            } else {
                try {
                    newThreshold = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    newThreshold = "boo!";
                }
            }
        } catch (IOException e) {
            System.out.println("Invalid input. Please try again.");
            return null;
        }
        if (newThreshold instanceof Integer) {
            if ((Integer) thresholdOption == 1) {
                allUsers.setLentMinusBorrowedThreshold((Integer) newThreshold);
                allAdmins.setLentMinusBorrowedThreshold((Integer) newThreshold);
                System.out.println("\nThe threshold has been changed successfully. The lend - borrow threshold is now: " +
                        newThreshold + ".");
            } else if ((Integer) thresholdOption == 2) {
                allUsers.setWeeklyTransactionLimit((Integer) newThreshold);
                allAdmins.setWeeklyTransactionLimit((Integer) newThreshold);
                System.out.println("\nThe threshold has been changed successfully. The weekly transaction limit " +
                        "threshold is now: " + newThreshold + ".");
            } else if ((Integer) thresholdOption == 3) {
                allUsers.setIncompleteTransactionLimit((Integer) newThreshold);
                allAdmins.setIncompleteTransactionLimit((Integer) newThreshold);
                System.out.println("\nThe threshold has been changed successfully. The incomplete transaction limit " +
                        "threshold is now: " + newThreshold + ".");
            } else { //thresholdOption == 4
                allUsers.setMeetingEditThreshold((Integer) newThreshold);
                allAdmins.setMeetingEditThreshold((Integer) newThreshold);
                System.out.println("\nThe threshold has been changed successfully. The meeting edits " +
                        "threshold is now: " + newThreshold + ".");
            }
        } else {
                System.out.println("\nThe threshold has been changed unsuccessfully. Please try again.");
                return null;
        }
        return null;
    }

    /**
     * Adds a new admin to the list of all Admins in AdminManager.
     *
     * @param allAdmins AdminManager stores all the Admin information.
     * @return depending on what the Admin inputs it will return different objects:
     *         returns null to tell mainmenu() to call addAdmin() again
     *         returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     *                 main menu option
     *         returns String "exit" to prompt TradeSystem to save all the information and exit the System
     */
    public Object addAdmin(AdminManager allAdmins) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;

        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Please enter 'continue' to proceed to adding a new Admin or 'back' to return to the " +
                "Admin menu." + " Enter 'exit' to exit the system at any time.");
        try {
            String input = br.readLine();
            if (input.equals("exit")) {
                return input;
            }
            if (input.equals("continue")) {
                while (!input.equals("exit") && !prompts.usergot) {
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
                //loops through the list of allUsers in the system
                for (int i = 0; i < allAdmins.getAllAdmins().size(); i++) {
                    //checks if the entered username already exists
                    if (temp.get(0).equals(allAdmins.getAllAdmins().get(i).getName())) {
                        System.out.print("Username already exists. Please choose a new username\n");
                        return addAdmin(allAdmins);
                    }
                }
                allAdmins.addAdmin(temp.get(0), temp.get(1));
                return (allAdmins.getAllAdmins().get(allAdmins.getAllAdmins().size() - 1));
            }
            else if(input.equals("back")){
                return "back";
            }
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}
