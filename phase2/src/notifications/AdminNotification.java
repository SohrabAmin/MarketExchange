package notifications;

import accounts.admins.Admin;
import accounts.admins.AdminManager;

public interface AdminNotification {
    void notify(Admin admin, AdminManager allAdmins);
}
