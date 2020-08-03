import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UndoAction implements adminMainMenuOptions {

    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages) {
        ReadWrite readFile = new ReadWrite();
        List<String> logList;
        try{
            logList = readFile.readLog("UndoLog.txt");
        } catch (IOException e) {
            System.out.println("An error has occurred. Something is wrong with 'UndoLog.txt'");
            return "back";
        }

        if (logList.size() == 0) {
            System.out.println("Nothing has happened in the system :( You will be redirected to the main menu.");
            return "back";
        }

        System.out.println("\nHere is the log of the system: ");
        for (int i = 0; i < logList.size(); i++ ) {
            System.out.println((i+1) + ". " + logList.get(i));
        }
        Scanner sc = new Scanner(System.in);

        System.out.println("If you would like to undo an action, enter the number beside the log." +
                " If you would like to go back to the main menu, enter 'back'.");

        Object input = sc.nextLine();
        if (input.equals("back")) {
            return "back";
        }

        try {
            //will try to turn the input into an integer
            //if input is not an integer, returns null and recalls execute()
            input = Integer.parseInt((String) input);
        } catch (NumberFormatException e) {
            System.out.println("That is not a valid option, please try again!");
            return null;
        }

        String chosenLog = logList.get((int) input - 1);

        String[] brokenUp = chosenLog.split(" ");

        if (brokenUp[brokenUp.length - 1].equals("wishlist.\n")) {
            return undoWishlist(allUsers, chosenLog);
        }

        return "back";
    }

    public Object undoWishlist(UserManager allUsers, String chosenLog) {
        System.out.println(chosenLog.split(" ")[2]);
        User temp = new User(chosenLog.split(" ")[2], "1");
        User user = allUsers.getUser(temp);
        if (user == null) {
            System.out.println("This User no longer exists.");
            return null;
        }
        String itemName = chosenLog.split("\"")[1];
        String description = chosenLog.split("\"")[3];
        Item item = user.findInWishlist(itemName, description);
        if (item == null) {
            System.out.println("Item has already been undone by either another Admin or the User themselves!");
            return null;
        }
        allUsers.removeFromWishlist(user,item);
        System.out.println("You have successfully removed " + itemName + " from User " + user.getName() + "'s wishlist!");
        allUsers.addToUndoWishListAction(user, itemName);
        return null;
    }



}