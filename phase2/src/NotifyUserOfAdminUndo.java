import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;


public class NotifyUserOfAdminUndo {

    public void notify(User user, UserManager allUsers) {
        if (user.getUndoWishListAction().size() > 0) {
            for (int i = 0; i < user.getUndoWishListAction().size(); i++) {
                System.out.println(user.getUndoWishListAction().get(i) + " has been removed from your wishlist by Admin.");
                allUsers.addToAdminActionHistory(user, user.getUndoWishListAction().get(i) + " has been removed from your wishlist by Admin.");
                allUsers.removeFromUndoWishListAction(user, user.getUndoWishListAction().get(i));
            }
            System.out.println("For further information, please message Admin. To read these notifications again, please" +
                    " go to Account Settings to view Admin Change Log.");
        }
    }
}