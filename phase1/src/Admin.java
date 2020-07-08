
public class Admin extends Account {
    private boolean isInitialAdmin;

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean getIsInitialAdmin() {
        return isInitialAdmin;
    }

    public void setIsInitialAdmin(boolean isInitialAdmin) {
        this.isInitialAdmin = isInitialAdmin;
    }
}
