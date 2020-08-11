package notifications;

import accounts.users.User;
import accounts.users.UserManager;

public interface UserNotification {
    void notify(User user, UserManager allUsers);
}
