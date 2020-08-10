package system;

import accounts.users.User;
import accounts.users.UserManager;

public class NotifyUserOfVIPStatusChange {
    /**
     * Notify user of a change to their VIP status when they log in
     * @param user user who logged in
     * @param allUsers the instance of UserManager
     */
    public void notify(User user, UserManager allUsers) {
        if (user.getVIPStatusChangeNotifications().size() > 0) {
            for (int i = 0; i < user.getVIPStatusChangeNotifications().size(); i++) {
                System.out.println(user.getVIPStatusChangeNotifications().get(i));
                allUsers.addToVIPStatusChangeHistory(user, user.getNotifyUndo().get(i));
                allUsers.removeFromVIPStatusChangeNotifications(user, user.getVIPStatusChangeNotifications().get(i));
            }
            System.out.println("For further information, please message Admin. To read these notifications again, " +
                    "please go to Account Settings to view Admin Change Log.\n");
        }
    }
}