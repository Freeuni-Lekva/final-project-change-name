package bandfinder.servlets;

import bandfinder.dao.UserDAO;
import bandfinder.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="EditProfileServlet", value="/EditProfileServlet")
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("user");

        UserDAO userDao = (UserDAO) req.getSession().getAttribute(UserDAO.ATTRIBUTE_NAME);
        userDao.update(user);
    }
}
