package bandfinder.infrastructure;

import bandfinder.serviceimplementations.*;
import bandfinder.services.DefaultTagsService;
import bandfinder.services.HashingService;
import bandfinder.services.SearchService;
import org.apache.ibatis.jdbc.SQL;
import bandfinder.dao.UserDAO;
import bandfinder.dao.BandDAO;
import bandfinder.dao.TagDAO;

public class Config {
    private static boolean isConfigured = false;
    public static void ConfigureServices() {
        if(isConfigured){
            return;
        }
        Injector.injectSingleton(HashingService.class, HashingServiceImpl.class);
        Injector.injectSingleton(SearchService.class, SimpleSearchServiceImpl.class);
        Injector.injectSingleton(DefaultTagsService.class,DefaultTagsServiceImpl.class);
        Injector.injectSingleton(UserDAO.class, SQLUserDAO.class);
        Injector.injectSingleton(BandDAO.class, SQLBandDAO.class);
        Injector.injectSingleton(TagDAO.class, SQLTagDAO.class);
        isConfigured = true;
    }
}
