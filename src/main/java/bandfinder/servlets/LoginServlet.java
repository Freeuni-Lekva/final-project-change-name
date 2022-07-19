package bandfinder.servlets;

import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.User;
import bandfinder.services.HashingService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends ServletBase {

    @AutoInjectable
    private UserDAO userDAO;

    @AutoInjectable
    private HashingService hashingService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User foundUser = userDAO.getUserByEmail(email);

        try {
            if (foundUser != null && hashingService.validateHash(password, foundUser.getPasswordHash())) {
                request.getSession().setAttribute("user", foundUser);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
