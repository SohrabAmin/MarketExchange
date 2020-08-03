import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class InventoryManager implements userMainMenuOptions {
    /**
     * Prints out the User user's inventory; also calls UserManager to remove inventory Items upon user's request
     *  @param user     the User that wants to see their inventory
     * @param allUsers the UserManager which stores the User user
     * @return null if the current menu is to be reprinted; User user if the user is to be redirected to the main menu;
     * String "exit" if the user is to be logged out.
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages) {
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
}
