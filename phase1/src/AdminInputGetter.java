import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Deals with the input of an Admin user-- particularly deals with the login system and displaying of main menu.
 */

public class AdminInputGetter{

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
     *  Displays the main menu of an AdminUser and prompts the user for input.
     *
     * @param admin Account of the Admin.
     */
    public void mainMenu(Admin admin) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please select from the following by entering the number beside the option:" +
                " \n '1. Add new admin' \n '2. Change system threshold' \n" +
                "'3. View items that need to be approved' \n '4. Freeze and unfreeze users' \n '5. Log out'" +
                "Enter 'exit' to exit at any time.");
        try {
            String input = br.readLine();
            if (input.equals('1')) {

            }


        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }
}
