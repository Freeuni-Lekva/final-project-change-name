package bandfinder.dao;

import bandfinder.models.Notification;

import java.sql.Timestamp;
import java.util.List;

public interface NotificationDAO extends DAO<Notification> {
    String ATTRIBUTE = "NotificationDAO";

    List<Notification> getUserNotifsByStatusAndDate(int userId, Boolean isRead, Timestamp startDate, Timestamp endDate);

    List<Notification> getUserNotifsByStatus(int userId, Boolean isRead);

    List<Notification> getUserNotifsByDate(int userId, Timestamp startDate, Timestamp endDate);
}
