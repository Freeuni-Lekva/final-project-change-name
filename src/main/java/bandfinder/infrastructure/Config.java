package bandfinder.infrastructure;

import bandfinder.dao.BandDAO;
import bandfinder.dao.NotificationDAO;
import bandfinder.models.Notification;
import bandfinder.serviceimplementations.*;
import bandfinder.dao.UserDAO;
import bandfinder.services.HashingService;
import org.apache.ibatis.jdbc.SQL;

public class Config {
    private static boolean isConfigured = false;
    public static void ConfigureServices() {
        if(isConfigured){
            return;
        }
        Injector.injectSingleton(HashingService.class, HashingServiceImpl.class);
        Injector.injectSingleton(UserDAO.class, SQLUserDAO.class);
        Injector.injectSingleton(NotificationDAO.class, SQLNotificationDAO.class);
        Injector.injectSingleton(BandDAO.class, SQLBandDAO.class);
        isConfigured = true;
    }
}
