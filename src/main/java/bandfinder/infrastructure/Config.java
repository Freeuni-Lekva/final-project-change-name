package bandfinder.infrastructure;

import bandfinder.dao.BandDAO;
import bandfinder.serviceimplementations.HashMapUserDAO;
import bandfinder.dao.UserDAO;
import bandfinder.serviceimplementations.HashingServiceImpl;
import bandfinder.serviceimplementations.HashmapBandDAO;
import bandfinder.services.HashingService;

public class Config {
    private static boolean isConfigured = false;
    public static void ConfigureServices() {
        if(isConfigured){
            return;
        }
        Injector.injectSingleton(HashingService.class, HashingServiceImpl.class);
        Injector.injectSingleton(UserDAO.class, HashMapUserDAO.class);
        Injector.injectSingleton(BandDAO.class, HashmapBandDAO.class);
        isConfigured = true;
    }
}
