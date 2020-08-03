import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class changeThreshold implements adminMainMenuOptions{

    /**
     * Changes the lentMinusBorrowedThreshold which dictates how much more a user has to have lent than borrowed,
     * before trading. The threshold change affects all Users in the system.
     *
     * @param allUsers  changes LentMinusBorrowedThreshold variable in the system's UserManager
     * @param allAdmins changes LentMinusBorrowedThreshold variable in the system's AdminManager
     * @param admin the current Admin logged into the system
     * @param allItems ItemManager which holds the system inventory
     * @return depending on what the Admin inputs it will return different objects:
     * returns null to tell mainmenu() to call execute() again
     * returns String "back" to tell mainmenu() to prompt main menu again so Admin can choose another
     * main menu option
     */
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //prints the current thresholds of the system
        System.out.println("\nHere are the current thresholds:");
        System.out.println("Lent - borrow threshold: " + allAdmins.getLentMinusBorrowedThreshold());
        System.out.println("Weekly transaction limit: " + allAdmins.getWeeklyTransactionLimit());
        System.out.println("Incomplete transaction limit: " + allAdmins.getIncompleteTransactionLimit());
        System.out.println("Meeting edits threshold: " + allAdmins.getMeetingEditThreshold());
        //prompts user to enter what threshold they wish to edit
        System.out.println("\nWhich threshold would you like to edit? Please enter the number beside the option.");
        System.out.println("1.Lent - borrow threshold\n2.Weekly Transaction Limit\n3.Incomplete Transaction Limit" +
                "\n4.Meeting Edits Threshold\n");
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
                allAdmins.setLentMinusBorrowedThreshold((Integer) newThreshold);
                System.out.println("\nThe threshold has been changed successfully. The lend - borrow threshold is now: " +
                        newThreshold + ".");
            } else if ((Integer) thresholdOption == 2) {
                allAdmins.setWeeklyTransactionLimit((Integer) newThreshold);
                System.out.println("\nThe threshold has been changed successfully. The weekly transaction limit " +
                        "threshold is now: " + newThreshold + ".");
            } else if ((Integer) thresholdOption == 3) {
                allAdmins.setIncompleteTransactionLimit((Integer) newThreshold);
                System.out.println("\nThe threshold has been changed successfully. The incomplete transaction limit " +
                        "threshold is now: " + newThreshold + ".");
            } else { //thresholdOption == 4
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
}
