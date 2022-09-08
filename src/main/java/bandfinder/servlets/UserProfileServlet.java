package bandfinder.servlets;

import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.User;
import bandfinder.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="EditProfileServlet", value="/EditProfileServlet")
public class UserProfileServlet extends ServletBase {
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private AuthenticationService authenticationService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("field-email");
        String name = req.getParameter("field-name");
        String surname = req.getParameter("field-surname");
        String stageName = req.getParameter("field-stageName");

        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int loggedInUserId = authenticationService.authenticate(loginToken);
        User user = userDAO.getById(loggedInUserId);

        if(user == null){
            System.out.println("User object not available!");
            return;
        }

        user.setEmail(email);
        user.setFirstName(name);
        user.setSurname(surname);
        user.setStageName(stageName);

        userDAO.update(user);

        if(req.getSession().getAttribute("passwordIncorrect") != null){
            req.getSession().removeAttribute("passwordIncorrect");
        }

        resp.sendRedirect("editProfile.jsp");
    }
}
