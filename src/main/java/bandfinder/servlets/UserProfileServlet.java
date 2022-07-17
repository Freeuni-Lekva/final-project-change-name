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
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String stageName = req.getParameter("stageName");

        //TODO: Change this later, as we don't store the user inside the session yet!
        User user = (User) req.getSession().getAttribute("user");
        user.setEmail(email);
        user.setFirstName(name);
        user.setSurname(surname);
        user.setStageName(stageName);

        UserDAO userDao = (UserDAO) req.getSession().getAttribute(UserDAO.ATTRIBUTE_NAME);
        userDao.update(user);
    }
}
