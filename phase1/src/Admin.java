/**
 * Represents an Admin with name and password
 */
public class Admin extends Account {
    private boolean isInitialAdmin;
    /**
     * constructs an instance of Admin with name and password
     * @param name of this Admin
     * @param password of this Admin
     */
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }
    /**
     * getter for if the Admin is the initial admin as boolean
     * @return the result of if this Admin is the inital admin as boolean
     */
    public boolean getIsInitialAdmin() {
        return isInitialAdmin;
    }
    /**
     * setter for if the Admin is the initial admin as boolean
     * @param isInitialAdmin of this Admin as boolean
     */
    public void setIsInitialAdmin(boolean isInitialAdmin) {
        this.isInitialAdmin = isInitialAdmin;
    }
}
