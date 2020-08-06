import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PromoteOrDemoteAdmin implements adminMainMenuOptions {

    /**
     * Allows a super admin to:
     * promote an admin to super admin, or
     * demote a super admin to admin.
     * <p>
     * Prevents a non-super admin from accessing this menu.
     *
     * @param admin     Admin logged in to the system
     * @param allAdmins The instance of AdminManager
     * @param allUsers  The instance of UserManager
     * @param allItems  The instance of ItemManager
     * @return null if the current menu is to be reprinted; Admin admin if the admin is to be redirected to the main menu.
     */
    public Object execute(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems,
                          UserMessageManager allUserMessages, TransactionManager allTransactions,
                          TradeRequestManager allRequests, CurrencyManager allCurrency) {

        if (!admin.getIsSuperAdmin()) {
            System.out.println("Sorry, but only super admins can access this menu!");
            return admin;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 to promote an admin to super admin. Press 2 to demote a super admin to an admin. " +
                "Press 3 to cancel.");
        switch (scanner.nextLine()) {
            case "1":
                List<String> listOfAdminNames = new ArrayList<>();
                System.out.println("Type the name of the admin to promote to super admin.");

                for (Admin indexedAdmin : allAdmins.getAllAdmins()) {

                    // the list of admins that will be displayed to the logged in super admin
                    if (!indexedAdmin.getIsSuperAdmin()) {
                        listOfAdminNames.add(indexedAdmin.getName());
                    }
                }

                if (listOfAdminNames.isEmpty()) {
                    System.out.println("No non-super admins found!");
                    return null;
                } else {
                    for (String adminName : listOfAdminNames) {
                        System.out.println(adminName);
                    }

                    String nameOfAdminChosenForPromotion = scanner.nextLine();

                    // first check whether the super admin typed in an actual admin name
                    if (listOfAdminNames.contains(nameOfAdminChosenForPromotion)) {

                        // loop through the list of admins to find the admin to promote
                        for (Admin indexedAdmin : allAdmins.getAllAdmins()) {
                            if (indexedAdmin.getName().equals(nameOfAdminChosenForPromotion)) {
                                indexedAdmin.setIsSuperAdmin(true);
                                System.out.println("Admin " + nameOfAdminChosenForPromotion + " was promoted to super admin!");
                            }
                        }
                        return admin;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                        return null;
                    }
                }

            case "2":
                List<String> listOfSuperAdminNames = new ArrayList<>();
                System.out.println("Type the name of the super admin to demote to admin.");

                for (Admin indexedAdmin : allAdmins.getAllAdmins()) {

                    // the list of super admins that will be displayed to the logged in super admin
                    // add super admins to the list of super admins, but do not add the logged in super admin
                    if (indexedAdmin.getIsSuperAdmin() && !indexedAdmin.getName().equals(admin.getName())) {
                        listOfSuperAdminNames.add(indexedAdmin.getName());
                    }
                }

                if (listOfSuperAdminNames.isEmpty()) {
                    System.out.println("No other super admins found!");
                    return null;
                } else {
                    for (String superAdminName : listOfSuperAdminNames) {
                        System.out.println(superAdminName);
                    }

                    String nameOfSuperAdminChosenForDemotion = scanner.nextLine();

                    // first check whether the super admin typed in an actual super admin name
                    if (listOfSuperAdminNames.contains(nameOfSuperAdminChosenForDemotion)) {

                        // loop through the list of admins to find the super admin to demote
                        for (Admin indexedAdmin : allAdmins.getAllAdmins()) {
                            if (indexedAdmin.getName().equals(nameOfSuperAdminChosenForDemotion)) {
                                indexedAdmin.setIsSuperAdmin(false);
                                System.out.println("Super admin " + nameOfSuperAdminChosenForDemotion + " was demoted to admin!");
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
