import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of and adds Admins
 */
public class AdminManager extends AccountManager {
    private List<Admin> allAdmins;

    /**
     * Constructs the instance of AdminManager without needing an initial admin. It will
     * be populated with an initial admin and list of admins through the reading
     * and writing from an external file.
     */
    public AdminManager(){
        allAdmins = new ArrayList<>();
    }
    /**
     * Constructs the instance of AdminManager with an initial Admin and a list of Admins
     *
     * @param initialAdmin first Admin in the system
     */
    public AdminManager(Admin initialAdmin) {
        allAdmins = new ArrayList<>();
        initialAdmin.setIsInitialAdmin(true);
        allAdmins.add(initialAdmin);
    }

    /**
     * Adds a new Admin to the list of all Admins
     *
     * @param newAdminUsername new Admin's account username
     * @param newAdminPassword new Admin's account password
     */
    public void addAdmin(String newAdminUsername, String newAdminPassword) {
        Admin newAdmin = new Admin(newAdminUsername, newAdminPassword);
        allAdmins.add(newAdmin);
    }

    /**
     * Getter for the list of all Admins
     *
     * @return list of all Admins
     */
    public List<Admin> getAllAdmins() {
        return allAdmins;
    }

    /**
     * Setter for the list of all Users in the system. Only AdminReadWrite should access this method.
     *
     * @param adminList new list of all Users
     */
    void setAllAdmins(List<Admin> adminList) {
        this.allAdmins = adminList;
    }
}
