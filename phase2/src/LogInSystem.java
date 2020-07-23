import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is the initial screen. It will ask the user to input whether or not the user is an AdminUser or User. It
 * will then redirect it to InputGetter if they are a User or AdminInputGetter if they are an AdminUser.
 */
public class LogInSystem {
    public UserManager usermanager;
    public AdminManager adminmanager;

    public LogInSystem(UserManager allUsers, AdminManager allAdmins) {
        usermanager = allUsers;
        adminmanager = allAdmins;
    }

    public Object LogIn(InputGetter inputgetter, AdminInputGetter admininputgetter) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\uD83D\uDECD Welcome To Trade Market! \uD83D\uDECD\nIf you are an admin, please enter 'admin'. If otherwise, please enter 'user'. " +
                "Enter 'demo' to enter demo mode. \n Enter 'exit' to exit the system.");
        try {
            String input = br.readLine();
            {
                //if the user wants to exit the program, it will return "exit"
                //so TradeSystem knows to exit the system
                if (input.equals("exit")) {
                    return new String("exit");
                }
                else if (input.equals("Demo") || input.equals("demo")){
                    User Demo = new User("Demo", "123");
                    return Demo;

                }
                //if they are a user, it will call the authenticator() method in InputGetter
                else if (input.equals("user")) {
                    return inputgetter.authenticator(usermanager);
                }
                //if they are admin, it will prompt admin authenticator() method in AdminInputGetter
                else if (input.equals("admin")) {
                    Admin temp = admininputgetter.authenticator(adminmanager);
                    //authenticator() returns null when user wants to go back to main welcome screen
                    if (temp == null) {
                        return LogIn(inputgetter, admininputgetter);
                    }
                    return temp;
                } else {
                    return LogIn(inputgetter, admininputgetter);
                }
            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

}
