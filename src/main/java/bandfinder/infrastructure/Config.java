package bandfinder.infrastructure;

import bandfinder.dao.*;
import bandfinder.services.*;
import bandfinder.serviceimplementations.*;

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
        Injector.injectSingleton(AuthenticationService.class, JWTAuthenticationServiceImpl.class);
        Injector.injectSingleton(UserDAO.class, SQLUserDAO.class);
        Injector.injectSingleton(NotificationDAO.class, SQLNotificationDAO.class);
        Injector.injectSingleton(BandDAO.class, SQLBandDAO.class);
        Injector.injectSingleton(FollowDAO.class, SQLFollowDAO.class);
        Injector.injectSingleton(TagDAO.class, SQLTagDAO.class);
        Injector.injectSingleton(PostDAO.class, SQLPostDAO.class);
        Injector.injectSingleton(MessageDAO.class, SQLMessageDAO.class);
        Injector.injectSingleton(CommentDAO.class, SQLCommentDAO.class);
        Injector.injectSingleton(RequestDAO.class, SQLRequestDAO.class);
        Injector.injectSingleton(InvitationDAO.class, SQLInvitationDAO.class);

        isConfigured = true;
    }
}
