import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Deals with the input of an Admin user-- particularly deals with the login system and displaying of main menu.
 */

public class AdminInputGetter {

    public Admin authenticator(AdminManager allAdmins) {

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
                Admin tempAdmin = new Admin(temp.get(0), temp.get(1));
                for (int i = 0; i < allAdmins.getAllAdmins().size(); i++) {
                    if (allAdmins.getAllAdmins().get(i).getName().equals(temp.get(0)))
                        if (allAdmins.getAllAdmins().get(i).getPassword().equals(temp.get(1))) {
                            System.out.println("Successful Login");
                            return (allAdmins.getAllAdmins().get(i));
                        }
                }
                //if Admin doesn't exist
                System.out.println("Wrong username or password. Please try Again");
                //curr = 0;
                return authenticator(allAdmins);
            }
            if (input.equals("back")) {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

    /**
     * Displays the main menu of an AdminUser and prompts the user for input.
     *
     * @param admin Account of the Admin.
     */
    public Object mainMenu(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please select from the following by entering the number beside the option:" +
                " \n 1. Add new admin \n 2. Change system threshold \n" +
                " 3. View items that need to be approved \n 4. Freeze and unfreeze users \n 5. Log out \n" +
                "Enter 'exit' to exit at any time.");

        try {
            String input = br.readLine();
            if (!input.equals("exit")) {
                if (input.equals("1")) {
                    Object temp = addAdmin(allAdmins);
                    //successful addition of a new admin
                    if (temp != null && temp instanceof Admin) {
                        System.out.println("\n New admin has been added successfully.\n");
                        return admin;
                        //user decided to exit
                    } else if (temp == null){
                        return null;
                        //user entered "back"
                    } else {
                        return mainMenu(admin, allAdmins, allUsers, allItems);
                    }
                } else if (input.equals("2")) {
                    changeThreshold(allUsers, allAdmins);
                    return admin;
                } else if (input.equals("3")) {
                    return admin;
                } else if (input.equals("4")) {
                    return admin;
                } else if (input.equals("5")) {
                    return null;
                } else {
                    return admin;
                }
            }
            return input;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        return admin;
    }

    /**
     * Changes the lentMinusBorrowedThreshold which dictates how much more a user has to have lent than borrowed,
     * before trading. The threshold change affects all Users in the system.
     *
     * @param allUsers  changes LentMinusBorrowedThreshold variable in the system's UserManager
     * @param allAdmins changes LentMinusBorrowedThreshold variable in the system's AdminManager
     */
    public void changeThreshold(UserManager allUsers, AdminManager allAdmins) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the integer you wish to change the system threshold to or 'back' to go back.");
        Object newThreshold = null;
        try {
            String input = br.readLine();
            if (input.equals("back")) {
                return;
            } else {
                try {
                    newThreshold = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    newThreshold = "boo!";
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        if (newThreshold instanceof Integer) {
            allUsers.setLentMinusBorrowedThreshold((Integer) newThreshold);
            allAdmins.setLentMinusBorrowedThreshold((Integer) newThreshold);
            System.out.println("\nThe threshold has been changed successfully. The lend - borrow threshold is now: " +
                    newThreshold + ". \n");
        } else {
            System.out.println("\nThe threshold has been changed unsuccessfully. The lend - borrow threshold is: " +
                    allUsers.getLentMinusBorrowedThreshold() + ". \n");
        }
    }

    /**
     * Adds a new admin to the list of all Admins in AdminManager.
     *
     * @param allAdmins
     */
    public Object addAdmin(AdminManager allAdmins) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;

        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Please enter 'continue' to proceed to adding a new Admin or 'back' to return to the Admin menu." +
                "Enter 'exit' to exit the system at any time.");
        try {
            String input = br.readLine();
            if (input.equals("exit")) {
                return input;
            }
            if (input.equals("continue")) {
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
                //loops through the list of allUsers in the system
                for (int i = 0; i < allAdmins.getAllAdmins().size(); i++) {
                    //checks if the entered username already exists
                    if (temp.get(0).equals(allAdmins.getAllAdmins().get(i).getName())) {
                        System.out.print("Username already exists. Please choose a new username\n");
                        return addAdmin(allAdmins);
                    }
                }
                allAdmins.addAdmin(temp.get(0), temp.get(1));
                return (allAdmins.getAllAdmins().get(allAdmins.getAllAdmins().size() - 1));
            }
            else if(input.equals("back")){
                return "back";
            }
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}
