package bandfinder.infrastructure;

import bandfinder.dao.*;
import bandfinder.services.*;
import bandfinder.serviceimplementations.*;
import bandfinder.dao.BandDAO;
import bandfinder.dao.NotificationDAO;
import bandfinder.dao.UserDAO;
import bandfinder.services.HashingService;
import bandfinder.servlets.JWTAuthenticationService;

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
        Injector.injectSingleton(AuthenticationService.class, JWTAuthenticationService.class);
        Injector.injectSingleton(UserDAO.class, SQLUserDAO.class);
        Injector.injectSingleton(NotificationDAO.class, SQLNotificationDAO.class);
        Injector.injectSingleton(BandDAO.class, SQLBandDAO.class);
        Injector.injectSingleton(FollowDAO.class, SQLFollowDAO.class);
        Injector.injectSingleton(TagDAO.class, SQLTagDAO.class);
        Injector.injectSingleton(PostDAO.class, SQLPostDAO.class);
        isConfigured = true;
    }
}
