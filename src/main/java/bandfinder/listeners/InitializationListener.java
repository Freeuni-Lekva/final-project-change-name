package bandfinder.listeners;

import bandfinder.infrastructure.DatabaseMigrator;
import bandfinder.serviceimplementations.HashMapUserDAO;
import bandfinder.dao.UserDAO;
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
//        DatabaseMigrator.migrate();
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
