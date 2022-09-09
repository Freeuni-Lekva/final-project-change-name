package bandfinder.servlets;

import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.User;
import bandfinder.services.AuthenticationService;
import bandfinder.services.HashingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name="EditPasswordServlet", value="/EditPasswordServlet")
public class EditPasswordServlet extends ServletBase {
    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private HashingService hashingService;
    @AutoInjectable
    private UserDAO userDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String curPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");

        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int loggedInUserId = authenticationService.authenticate(loginToken);
        User currUser = userDAO.getById(loggedInUserId);

        if(currUser == null){
            System.out.println("User not available!");
            return;
        }

        try {
            if(hashingService.validateHash(curPassword, currUser.getPasswordHash())){
                String newPasswordHash = hashingService.hash(newPassword);
                currUser.setPasswordHash(newPasswordHash);
                userDAO.update(currUser);

                if(req.getSession().getAttribute("passwordIncorrect") != null){
                    req.getSession().removeAttribute("passwordIncorrect");
                }
            }else{
                req.getSession().setAttribute("passwordIncorrect", true);
            }
            resp.sendRedirect("editProfile.jsp");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
