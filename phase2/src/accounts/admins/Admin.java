package accounts.admins;

import accounts.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an accounts.admins.Admin with name and password
 */
public class Admin extends Account {
    private boolean isInitialAdmin;
    private List<String> SuperAdminStatusChangeNotifications = new ArrayList<>();

    /**
     * constructs an instance of accounts.admins.Admin with name and password
     *
     * @param name     of this accounts.admins.Admin
     * @param password of this accounts.admins.Admin
     */
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Getter for if the accounts.admins.Admin is a super admin as boolean. Only super accounts.admins can add Admins to the system
     *
     * @return the result of if this accounts.admins.Admin is the initial admin as boolean
     */
    public boolean getIsSuperAdmin() {
        return isInitialAdmin;
    }

    /**
     * Setter for if the accounts.admins.Admin is a super admin admin as boolean. Only super accounts.admins can add Admins to the system
     *
     * @param isInitialAdmin of this accounts.admins.Admin as boolean
     */
    public void setIsSuperAdmin(boolean isInitialAdmin) {
        this.isInitialAdmin = isInitialAdmin;
    }

    public List<String> getSuperAdminStatusChangeNotifications (){
        return this.SuperAdminStatusChangeNotifications;
    }
}
