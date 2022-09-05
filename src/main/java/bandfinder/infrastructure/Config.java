package bandfinder.infrastructure;

import bandfinder.dao.BandDAO;
import bandfinder.dao.FollowDAO;
import bandfinder.serviceimplementations.*;
import bandfinder.services.*;
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
        Injector.injectSingleton(TagAutoComplete.class, AllTagsAutocompleteServiceImpl.class);
        Injector.injectSingleton(UserAutoComplete.class, AllUsersAutocompleteServiceImpl.class);
        Injector.injectSingleton(UserDAO.class, SQLUserDAO.class);
        Injector.injectSingleton(BandDAO.class, SQLBandDAO.class);
        Injector.injectSingleton(FollowDAO.class, SQLFollowDAO.class);
        Injector.injectSingleton(TagDAO.class, SQLTagDAO.class);
        isConfigured = true;
    }
}
