package system;

import accounts.users.User;
import accounts.users.UserManager;


public class NotifyUserOfAdminUndo {

    public void notify(User user, UserManager allUsers) {
        if (user.getNotifyUndo().size() > 0) {
            for (int i = 0; i < user.getNotifyUndo().size(); i++) {
                System.out.println(user.getNotifyUndo().get(i));
                allUsers.addToAdminActionHistory(user, user.getNotifyUndo().get(i));
                allUsers.removeFromNotifyUndo(user, user.getNotifyUndo().get(i));
            }
            System.out.println("For further information, please message accounts.admins.Admin. To read these notifications again, please" +
                    " go to accounts.Account Settings to view accounts.admins.Admin Change Log.\n");
        }
    }
}