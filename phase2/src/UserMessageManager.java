import java.util.ArrayList;
import java.util.List;


public class UserMessageManager {
    private ArrayList<List<String>> allUserMessage;
}
    public UserMessageManager(){
        this.allUserMessage.add(UserMessage.getUserMessage());
    }

    public static ArrayList<UserMessage> getAllUserMessage(){return this.allUserMessage;}
    public void removeFromAllUserMessage(UserMessage usermessage){this.allUserMessage.remove(usermessage);}
}

}