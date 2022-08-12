package bandfinder.infrastructure;

import bandfinder.dao.BandDAO;
import bandfinder.dao.FollowDAO;
import bandfinder.serviceimplementations.*;
import bandfinder.dao.UserDAO;
import bandfinder.services.HashingService;
import bandfinder.services.SearchService;
import org.apache.ibatis.jdbc.SQL;

public class Config {
    private static boolean isConfigured = false;
    public static void ConfigureServices() {
        if(isConfigured){
            return;
        }
        Injector.injectSingleton(HashingService.class, HashingServiceImpl.class);
        Injector.injectSingleton(SearchService.class, SimpleSearchServiceImpl.class);
        Injector.injectSingleton(UserDAO.class, SQLUserDAO.class);
        Injector.injectSingleton(BandDAO.class, SQLBandDAO.class);
        Injector.injectSingleton(FollowDAO.class, SQLFollowDAO.class);
        isConfigured = true;
    }
}
