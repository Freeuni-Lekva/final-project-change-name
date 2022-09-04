package bandfinder.listeners;

import bandfinder.infrastructure.Config;
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
    public void attributeAdded(HttpSessionBindingEvent event) {
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
