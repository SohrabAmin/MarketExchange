import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class UserMessage implements DifferentUserMainMenu{
    private ArrayList<String> userMessage;

    public UserMessage(){
        this.userMessage = new ArrayList<>();
    }


    public Object execute(User user){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please leave a message to Admin.");
        String user_message = sc.nextLine();
        System.out.println("Your message is: \n" + user_message);
        this.userMessage.add(user.getName());
        this.userMessage.add(user_message);
        return userMessage;
    }


    public static ArrayList<String> getUserMessage() { return this.userMessage;}
}

