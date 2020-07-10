import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 *  This is the initial screen. It will ask the user to input whether or not the user is an AdminUser or User. It
 *  will then redirect it to InputGetter if they are a User or AdminInputGetter if they are an AdminUser.
 *
 */
public class LogInSystem {

    public Account LogInSystem(UserManager usermanager, AdminManager adminmanager){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome. If you are an admin, please enter 'admin'. If otherwise, please enter 'user'." +
                "Enter 'exit' to exit the system.");
        try {
            String input = br.readLine();
            while (input != "exit") {
                if (input.equals("user")) {
                    InputGetter newUser = new InputGetter();
                    return newUser.authenticator(usermanager);
                } else if (input.equals("admin")) {
                    AdminInputGetter newAdmin = new AdminInputGetter();
                    return newAdmin.authenticator(adminmanager);
                }
            }
            //input is 'exit'
            System.exit(0);

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

}
