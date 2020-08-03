import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PromoteOrDemoteUser implements adminMainMenuOptions {

    /**
     * Allows an admin to:
     * promote a user to VIP, or
     * demote a VIP to user.
     *
     * @param admin     Admin logged in to the system
     * @param allAdmins The instance of AdminManager
     * @param allUsers  The instance of UserManager
     * @param allItems  The instance of ItemManager
     * @return null if the current menu is to be reprinted; Admin admin if the admin is to be redirected to the main menu.
     */
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages) {

        // the following four lines are commented out because currently any admin can promote users or demote VIPs
        //if (!admin.getIsSuperAdmin()) {
        //    System.out.println("Sorry, but only super admins can access this menu!");
        //    return admin;
        //}

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 to promote a user to VIP user. Press 2 to demote a VIP user to a user. " +
                "Press 3 to cancel.");
        switch (scanner.nextLine()) {
            case "1":
                List<String> listOfUserNames = new ArrayList<>();
                System.out.println("Type the name of the user to promote to VIP user.");

                for (User indexedUser : allUsers.getAllUsers()) {

                    // the list of users that will be displayed to the logged in admin
                    if (!indexedUser.getIsVIP()) {
                        listOfUserNames.add(indexedUser.getName());
                    }
                }

                if (listOfUserNames.isEmpty()) {
                    System.out.println("No non-VIP users found!");
                    return null;
                } else {
                    for (String userName : listOfUserNames) {
                        System.out.println(userName);
                    }

                    String nameOfUserChosenForPromotion = scanner.nextLine();

                    // first check whether the admin typed in an actual user name
                    if (listOfUserNames.contains(nameOfUserChosenForPromotion)) {

                        // loop through the list of users to find the user to promote
                        for (User indexedUser : allUsers.getAllUsers()) {
                            if (indexedUser.getName().equals(nameOfUserChosenForPromotion)) {
                                indexedUser.setIsVIP(true);
                                System.out.println("User " + nameOfUserChosenForPromotion + " was promoted to VIP!");
                            }
                        }
                        return admin;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                        return null;
                    }
                }

            case "2":
                List<String> listOfVIPNames = new ArrayList<>();
                System.out.println("Type the name of the VIP to demote to user.");

                for (User indexedUser : allUsers.getAllUsers()) {

                    // the list of VIP users that will be displayed to the logged in admin
                    if (indexedUser.getIsVIP()) {
                        listOfVIPNames.add(indexedUser.getName());
                    }
                }

                if (listOfVIPNames.isEmpty()) {
                    System.out.println("No VIPs found!");
                    return null;
                } else {
                    for (String VIPName : listOfVIPNames) {
                        System.out.println(VIPName);
                    }

                    String nameOfVIPChosenForDemotion = scanner.nextLine();

                    // first check whether the admin typed in an actual user name
                    if (listOfVIPNames.contains(nameOfVIPChosenForDemotion)) {

                        // loop through the list of users to find the VIP user to demote
                        for (User indexedUser : allUsers.getAllUsers()) {
                            if (indexedUser.getName().equals(nameOfVIPChosenForDemotion)) {
                                indexedUser.setIsVIP(false);
                                System.out.println("VIP " + nameOfVIPChosenForDemotion + " was demoted to user!");
                            }
                        }
                        return admin;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                        return null;
                    }
                }
            case "3":
                return admin;
        }
        System.out.println("Invalid input. Please try again.");
        return null;
    }
}
