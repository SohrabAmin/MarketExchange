import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;


public class NotifyUserOfAdminUndo implements userMainMenuOptions{

    public Object execute(User user,  AdminManager allAdmins,) {
        int i;
        for (i=0; i < allAdmins.getListOfUndoWishList().size();i++){
            if (allAdmins.getListOfUndoWishList().get(i).get(1).contains(user.getName()) {
                List notification = allAdmins.getListOfUndoWishList().get(i);
                String item = allAdmins.getListOfUndoWishList().get(i).get(0);
                System.out.println(item +" is removed from you wishlist by Admin.");
                allAdmins.removeFromListOfUndoWishList(notification);
            }

        }

    }