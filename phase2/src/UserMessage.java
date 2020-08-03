import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserMessage implements userMainMenuOptions{

    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages){

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter '1' to view message replies or '2' to send Admin a message. Enter 'back' to return to the main menu.");
        String input = sc.nextLine();

        if (input.equals("back")) {
            return "back";
        } else if (input.equals("1")) {
            if (user.getAdminMessages().size() == 0) {
                System.out.println("You have no messages!");
                return null;
            }
            System.out.println("Here are your message replies: ");
            for (int i = 0; i < user.getAdminMessages().size(); i++) {
                System.out.println((i+1) + ". " + user.getAdminMessages().get(i));
            }
            return null;
        } else if (input.equals("2")) {
            Object temp = messageAdmin(user, allUserMessages);
            while (temp == null) {
                temp = messageAdmin(user, allUserMessages);
            }
            return null;
        } else {
            System.out.println("That is not a valid option! Please try again.");
            return null;
        }
    }

    public Object messageAdmin(User user, UserMessageManager allUserMessages) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please leave a message to Admin:");
        String user_message = sc.nextLine();

        System.out.println("Your message is: \n" + user_message);

        System.out.println("Please enter '1' to confirm or '2' to cancel this message.");
        String confirmation = sc.nextLine();
        if (confirmation.equals("1")) {
            ArrayList<String> userMessage = new ArrayList<>();
            userMessage.add(user.getName());
            userMessage.add(user_message);
            allUserMessages.addUserMessage(userMessage);
            System.out.println("Your message has been sent successfully!");
            return "back";
        } else if (confirmation.equals("2")) {
            System.out.println("Your message has been cancelled.");
            return "back";
        } else {
            System.out.println("That is not a valid option! Please try again.");
            return null;
        }
    }
}

