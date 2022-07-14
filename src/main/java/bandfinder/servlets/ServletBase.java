package bandfinder.servlets;

import bandfinder.infrastructure.ServiceValueSetter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ServletBase extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServiceValueSetter.setAutoInjectableFieldValues(this);
    }
}
