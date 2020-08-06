import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;


public class NotifyUserOfAdminUndo {

    public void notify(User user, UserManager allUsers) {
        if (user.getNotifyUndo().size() > 0) {
            for (int i = 0; i < user.getNotifyUndo().size(); i++) {
                System.out.println(user.getNotifyUndo().get(i));
                allUsers.addToAdminActionHistory(user, user.getNotifyUndo().get(i));
                allUsers.removeFromNotifyUndo(user, user.getNotifyUndo().get(i));
            }
            System.out.println("For further information, please message Admin. To read these notifications again, please" +
                    " go to Account Settings to view Admin Change Log.\n");
        }
    }
}