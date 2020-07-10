import jdk.internal.util.xml.impl.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  This is the initial screen. It will ask the user to input whether or not the user is an AdminUser or User. It
 *  will then redirect it to InputGetter if they are a User or AdminInputGetter if they are an AdminUser.
 *
 */
public class LogInSystem {
    public UserManager usermanager;
    public AdminManager adminmanager;

    public LogInSystem(UserManager allUsers, AdminManager allAdmins) {
        usermanager = allUsers;
        adminmanager = allAdmins;
    }

    public Account LogIn(InputGetter inputgetter, AdminInputGetter admininputgetter){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome. If you are an admin, please enter 'admin'. If otherwise, please enter 'user'. " +
                "Enter 'exit' to exit the system.");
        try {
            String input = br.readLine();{
                if (input.equals("exit")){
                    System.exit(0);
                }
                else if (input.equals("user")) {
                    InputGetter newUser = new InputGetter();
                    return newUser.authenticator(usermanager);
                } else if (input.equals("admin")) {
                    AdminInputGetter newAdmin = new AdminInputGetter();
                    Admin temp = newAdmin.authenticator(adminmanager);
                    if (temp == null){
                        LogIn(inputgetter, admininputgetter);
                    }
                    return temp;
                }
                else {
                    LogIn(inputgetter, admininputgetter);
                }
            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

}
