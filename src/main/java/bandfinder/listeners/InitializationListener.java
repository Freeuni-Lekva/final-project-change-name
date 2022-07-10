package bandfinder.listeners;

import bandfinder.dao.HashMapUserDAO;
import bandfinder.dao.UserDAO;
import bandfinder.models.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.DriverManager;

@WebListener
public class InitializationListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
    public InitializationListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDAO currentUserDAO = new HashMapUserDAO();
        currentUserDAO.create(new User(1, "guja@gmail.com", "pass", "gujaboi", "sth", "none"));
        sce.getServletContext().setAttribute(UserDAO.ATTRIBUTE_NAME, currentUserDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
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
