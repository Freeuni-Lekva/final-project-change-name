package bandfinder.servlets;

import bandfinder.infrastructure.AutoInjectable;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var password = "password";
        try {
            System.out.println(System.nanoTime());
            var hash = hashingService.hash(password);
            System.out.println(System.nanoTime());
            var valid = hashingService.validateHash(password, hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
