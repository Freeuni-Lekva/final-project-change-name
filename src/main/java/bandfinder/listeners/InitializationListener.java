package bandfinder.listeners;

import bandfinder.infrastructure.Config;
import bandfinder.models.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebListener
public class InitializationListener implements
        ServletContextListener,
        HttpSessionListener,
        HttpSessionAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        DatabaseMigrator.migrate(); Temporarily disabled, - changing delimiters not supported
        Config.ConfigureServices();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
