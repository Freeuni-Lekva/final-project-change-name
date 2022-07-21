package bandfinder.servlets;

import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.User;
import bandfinder.services.HashingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name="EditPasswordServlet", value="/EditPasswordServlet")
public class EditPasswordServlet extends ServletBase {

    @AutoInjectable
    private HashingService hashingService;

    @AutoInjectable
    private UserDAO userDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String curPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");

        User curUser = (User) req.getSession().getAttribute("user");

        if(curUser == null){
            System.out.println("User not available!");
            return;
        }

        try {
            if(hashingService.validateHash(curPassword, curUser.getPasswordHash())){
                String newPasswordHash = hashingService.hash(newPassword);
                curUser.setPasswordHash(newPasswordHash);
                userDAO.update(curUser);
            }
            resp.sendRedirect("editProfile.jsp");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
