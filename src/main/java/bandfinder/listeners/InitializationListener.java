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
    public void contextDestroyed(ServletContextEvent sce) {}

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("token", null);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {}

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {}

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {}

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {}
}
