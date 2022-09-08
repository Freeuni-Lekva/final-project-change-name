package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.User;
import bandfinder.services.AuthenticationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="bandPage", value="/bandPage")
public class BandPageServlet extends ServletBase {
    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private BandDAO bandDAO;
    @AutoInjectable
    private UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int loggedInUserId = authenticationService.authenticate(loginToken);
        User currUser = userDAO.getById(loggedInUserId);

        int bandId = Integer.parseInt(req.getParameter("bandId"));

        if (currUser != null && bandDAO.isUserInBand(currUser.getId(), bandId)){
            req.setAttribute("extra_display", true);
        } else{
            req.setAttribute("extra_display", false);
        }

        RequestDispatcher rd = req.getRequestDispatcher("bandPage.jsp");
        rd.forward(req, resp);
    }
}
