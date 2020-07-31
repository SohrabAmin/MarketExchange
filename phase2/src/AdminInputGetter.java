import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Deals with the input of an Admin user-- particularly deals with the login system and displaying of main menu.
 */

public class AdminInputGetter {

    /**
     * Displays the main menu of an AdminUser and prompts the user for input.
     *
     * @param admin Account of the Admin.
     * @return depending on what the Admin inputs it will return different objects:
     * returns Admin to TradeSystem() to either remain logged into the system and prompt mainMenu
     * returns null to log out of the system and allow another Admin to log in
     * returns String "exit" to tell TradeSystem() to end the program and save all the data before
     * exiting the System
     */
    public Object mainMenu(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("----------------------------------------------------------------------------------------------" +
                "\n\uD83D\uDC4B Welcome back, " + admin.getName());

        List<Item> allPendingItems = new ArrayList<>();
        for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
            allPendingItems.addAll(allUsers.getAllUsers().get(i).getDraftInventory());
        }

        if (allAdmins.getFrozenRequests().size() > 0) {
            System.out.println("\uD83D\uDCF3 You have " + allAdmins.getFrozenRequests().size() + " Frozen user requests!");
        }
        //if they have frozen users, show it here
        if (allUsers.getAllFrozenUsers().size() > 0) {
            System.out.println("\u2603 You have " + allUsers.getAllFrozenUsers().size() + " Frozen users!");
        }

        //if they have pending items, show it here
        if (allPendingItems.size() > 0) {
            System.out.println("\uD83D\uDCE9 You have " + allPendingItems.size() + " Pending Item Requests!");
        }
        System.out.println("Please select from the following by entering the number beside the option:" +
                " \n1. Add new admin\n2. Change system threshold\n3. View items that need to be approved\n" +
                "4. Freeze or unfreeze users\n5. Promote an admin or demote a super admin\n6. Log out\n" +
                "Enter 'exit' to exit at any time.");
        ChosenOption option = new ChosenOption();
        try {
            String input = br.readLine();
            if (!input.equals("exit")) {
                if (input.equals("1")) { //add new admin
                    option.setChosenOption(new addAdmin());
                } else if (input.equals("2")) { //change system threshold
                    option.setChosenOption(new changeThreshold());
                } else if (input.equals("3")) { //view items that need to be approved
                    option.setChosenOption(new approvePendingItem());
                } else if (input.equals("4")) { //freeze or unfreeze users
                    option.setChosenOption(new FreezeOrUnfreeze());
                } else if (input.equals("5")) { //promote an admin or demote a super admin
                    option.setChosenOption(new PromoteOrDemoteAdmin());
                } else if (input.equals("6")) { //logout
                    return null;
                } else { //returns to main menu
                    System.out.println("That is not a valid option. Please try again.");
                    return admin;
                }
                Object result = option.executeOption(admin, allAdmins, allUsers, allItems);
                while (result == null) {
                    result = option.executeOption(admin, allAdmins, allUsers, allItems);
                }
                return admin;
            }
            return input;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        return admin;
    }

}
