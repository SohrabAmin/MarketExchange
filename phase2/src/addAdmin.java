import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class addAdmin implements adminMainMenuOptions{

    /**
     * Adds a new admin to the list of all Admins in AdminManager. Only the initial admin should be able to add new
     * admins.
     *
     * @param admin the current Admin logged into the system
     * @param allAdmins AdminManager which holds all the Admins, FrozenRequests and Thresholds in the system
     * @param allUsers UserManager which holds all the Users in the system
     * @param allItems ItemManager which holds the system inventory
     * @return depending on what the Admin inputs it will return different objects:
     * returns null to tell mainmenu() to call execute() again
     * returns String "back" to tell mainmenu() to prompt main menu again so Admin can choose another
     * main menu option
     * returns String "exit" to prompt TradeSystem to save all the information and exit the System
     */
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems) {
        if (!admin.getIsInitialAdmin()) {
            System.out.println("Sorry but you do not have the authorization to add new admins!");
            return "back";
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;

        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Please enter 'continue' to proceed to adding a new Admin or 'back' to return to the " +
                "Admin menu." + " Enter 'exit' to exit the system at any time.");
        try {
            String input = br.readLine();
            if (input.equals("exit")) {
                return input;
            }
            if (input.equals("continue")) {
                while (!input.equals("exit") && !prompts.usergot) {
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
                        return execute(admin, allAdmins, allUsers, allItems);
                    }
                }
                allAdmins.addAdmin(temp.get(0), temp.get(1));
                System.out.println("\nNew admin has been added successfully.\n");
                return "back";
            } else if (input.equals("back")) {
                return "back";
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}