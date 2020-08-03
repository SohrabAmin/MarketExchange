import java.util.ArrayList;
import java.util.List;


public class NotifyAdminOfUserMessage implements adminMainMenuOptions {
    public Object execute(UserMessageManager allUserMessage,UserMessage usermessage) {
        if (allUserMessage.getAllUserMessage().size() > 0){
            int i;
            for (i = 0; i < UserMessageManager.getAllUserMessage().size(); i++) {
                String person = UserMessageManager.getAllUserMessage().get(i).get(0);
                String mess = UserMessageManager.getAllUserMessage().get(i).get(1);
                System.out.println("You have a message from user," + person + " as below!  \n" + mess);

            }
        }
    }
}