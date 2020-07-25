import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This is the initial screen. It will ask the user to input whether or not the user is an AdminUser or User. It
 * will then redirect it to to the correct authenticate method to authenticate the attempted login or signup.
 */
public class LogInSystem {
    public UserManager usermanager;
    public AdminManager adminmanager;

    public LogInSystem(UserManager allUsers, AdminManager allAdmins) {
        usermanager = allUsers;
        adminmanager = allAdmins;
    }

    public Object LogIn() {
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
                    return userAuthenticator(usermanager);
                }
                //if they are admin, it will prompt admin authenticator() method in AdminInputGetter
                else if (input.equals("admin")) {
                    Admin temp = adminAuthenticator(adminmanager);
                    //authenticator() returns null when user wants to go back to main welcome screen
                    if (temp == null) {
                        return LogIn();
                    }
                    return temp;
                } else {
                    return LogIn();
                }
            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

    /**
     * Method authenticator prompts the User to either login or signup for an Account; proceeds to prompt User for
     * username and password. If they login, authenticator will check the list of Users stored in UserManager allUsers
     * and will return the user to TradeSystem so that they can log in and see main menu if the credentials are correct
     * and User exists. Else, it will prompt them to log in or sign up again. If they sign up, it will create a new
     * User which will be added to the system and returned to TradeSystem so they can log in and see main menu. If
     * username exists, it will prompt to log in or sign up again.
     *
     * @param allUsers UserManager object which stores all the Users in the system
     * @return depending on what the User inputs it will return different objects
     * returns String "exit" to tell TradeSystem.run() to stop trying to log in and exit the system
     * returns User object to tell TradeSystem.run() to prompt main menu for the returned User
     */
    public Object userAuthenticator(UserManager allUsers) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //prompts contains a list which stores the input the user gives
        //used to store the username and password
        UserIterator prompts = new UserIterator();
        int curr = 0;

        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Type 'signup' to create an account or 'login' to access your account or 'exit' to " +
                "exit at anytime.");
        try {
            String input = br.readLine().toLowerCase(); //make sure that Login and login are both used
            //user wants to signup for an account
            if (input.equals("exit")) {
                return input;
            }
            if (input.equals("signup")) {
                //as long as they don't say 'exit', it will prompt them to enter user name
                while (!input.equals("exit") && !prompts.usergot/*&& curr < 2*/) {
                    if (prompts.hasNext()) {
                        System.out.println(prompts.next());
                    }
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        temp.add(input);
                        curr++;
                        if (curr == 2)
                            prompts.usergot = true;
                    }
                }
                if (input.equals("exit")) {
                    return "exit";
                }
                //loops through the list of allUsers in the system
                //checks if the entered username already exists
                for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
                    if (temp.get(0).equals(allUsers.getAllUsers().get(i).getName())) {
                        System.out.print("Username Already exists. Please choose a new username\n");
                        return userAuthenticator(allUsers);
                    }
                }
                //if username doesn't already exist, it will create a user and returns it
                allUsers.createUser(temp.get(0), temp.get(1));
                return allUsers.getAllUsers().get(allUsers.getAllUsers().size() - 1);

                //user wants to login to their account
            } else if (input.equals("login")) {
                //as long as they don't say 'exit', it will ask for their login credentials
                while (!input.equals("exit") && curr < 2) {
                    if (prompts.hasNext()) {
                        System.out.println(prompts.next());
                    }
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        temp.add(input);
                        curr++;
                    }
                }
                if (input.equals("exit")) {
                    return input;
                }
                //checks to see if the User exists in the System by checking it against
                //the list of users in UserManager allUsers by comparing username and passwords against
                //the inputted username and password
                //if credentials check out, it will return the User
                for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
                    if (allUsers.getAllUsers().get(i).getName().equals(temp.get(0)))
                        if (allUsers.getAllUsers().get(i).getPassword().equals(temp.get(1))) {
                            System.out.println("Successful Login");
                            return (allUsers.getAllUsers().get(i));
                        }
                }
                //if user doesn't exist, prompts them to try to login again
                System.out.println("Wrong username or password. Please try Again");
                return userAuthenticator(allUsers);
            } else {
                System.out.print("Invalid Command! Try again!\n");
                return userAuthenticator(allUsers);

            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return userAuthenticator(allUsers);
    }

    /**
     * Reads input from user and lets them 'log in' to their account by verifying their username and password.
     *
     * @param allAdmins AdminManager which stores all the admin in the system.
     * @return returns an Admin which will be used to prompt the main menu in LogIn System
     */
    public Admin adminAuthenticator(AdminManager allAdmins) {

        //we fill out allUsers with whatever is in ser file
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;

        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Hello Admin. Please enter 'login' to log in, 'back' to return to the main page or " +
                "'exit' to exit at anytime.");
        try {
            String input = br.readLine();
            if (input.equals("login")) {
                while (!input.equals("exit") && curr < 2) {
                    if (prompts.hasNext()) {
                        System.out.println(prompts.next());
                    }
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        temp.add(input);
                        curr++;
                    }
                }
                for (int i = 0; i < allAdmins.getAllAdmins().size(); i++) {
                    if (allAdmins.getAllAdmins().get(i).getName().equals(temp.get(0)))
                        if (allAdmins.getAllAdmins().get(i).getPassword().equals(temp.get(1))) {
                            System.out.println("Successful Login");
                            return (allAdmins.getAllAdmins().get(i));
                        }
                }
                //if Admin doesn't exist
                System.out.println("Wrong username or password. Please try Again");
                return adminAuthenticator(allAdmins);
            }
            if (input.equals("back")) {
                return null;
            } else {
                return adminAuthenticator(allAdmins);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

}
