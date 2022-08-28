package bandfinder.dao;

import bandfinder.models.Notification;

import java.util.List;

public interface NotificationDAO extends DAO<Notification> {
    String ATTRIBUTE = "NotificationDAO";

    List<Notification> getReadNotifications(int userId);

    List<Notification> getUnreadNotifications(int userId);
}
