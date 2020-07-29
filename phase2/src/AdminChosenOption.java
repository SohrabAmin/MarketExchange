public class AdminChosenOption {
    public adminMainMenuOptions chosenOption;

    public void setChosenOption(adminMainMenuOptions option){
        this.chosenOption = option;
    }

    public Object executeOption(Admin admin, AdminManager allAdmins, UserManager allUsers, ItemManager allItems) {
        return chosenOption.execute(admin, allAdmins, allUsers, allItems);
    }
}
