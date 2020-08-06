import java.io.Serializable;
import java.util.ArrayList;


public class UserMessageManager implements Serializable {
    private ArrayList<ArrayList<String>> allUserMessage = new ArrayList<>();

    public void addUserMessage(ArrayList<String> userMessage) {
        allUserMessage.add(userMessage);
    }

    public ArrayList<ArrayList<String>> getAllUserMessage(){
        return allUserMessage;
    }

    public void removeFromAllUserMessage(ArrayList<String> usermessage){
        this.allUserMessage.remove(usermessage);
    }
}

