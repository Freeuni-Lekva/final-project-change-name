package bandfinder.dao;

import bandfinder.models.Notification;
import java.sql.Timestamp;
import java.util.List;

public interface NotificationDAO extends DAO<Notification> {
    String ATTRIBUTE = "NotificationDAO";

    List<Notification> getNotifsByStatusAndDate(Integer userId, Integer bandId, Boolean isRead,
                                                Timestamp startDate, Timestamp endDate);

    List<Notification> getNotifsByStatus(Integer userId, Integer bandId, Boolean isRead);

    List<Notification> getNotifsByDate(Integer userId, Integer bandId, Timestamp startDate, Timestamp endDate);
}
