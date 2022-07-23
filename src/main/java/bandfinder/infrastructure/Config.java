package bandfinder.infrastructure;

import bandfinder.dao.BandDAO;
import bandfinder.serviceimplementations.SQLBandDAO;
import bandfinder.serviceimplementations.HashMapUserDAO;
import bandfinder.dao.UserDAO;
import bandfinder.serviceimplementations.HashingServiceImpl;
import bandfinder.serviceimplementations.SQLUserDAO;
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
        Injector.injectSingleton(BandDAO.class, SQLBandDAO.class);
        isConfigured = true;
    }
}
