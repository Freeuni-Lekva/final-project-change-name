package bandfinder.servlets;

import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.User;
import bandfinder.services.HashingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "RegistrationServlet", value = "/register")
public class RegistrationServlet extends ServletBase {
    @AutoInjectable
    private HashingService hashingService;

    @AutoInjectable
    private UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        boolean userExists = userDAO.getUserByEmail(email) != null;
        if(userExists){
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String surname = request.getParameter("surname");
        String stageName = request.getParameter("stageName");

        if(email == null || password == null || firstName == null || surname == null || stageName == null){
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            String passwordHash = hashingService.hash(password);
            User user = new User(email, passwordHash, firstName, surname, stageName);
            userDAO.create(user);
            request.getRequestDispatcher("/login").forward(request, response);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
