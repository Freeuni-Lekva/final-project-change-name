package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.RequestDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Request;
import bandfinder.models.User;
import bandfinder.services.AuthenticationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="bandPage", value=Constants.URL_BAND_PAGE)
public class BandPageServlet extends ServletBase {
    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private BandDAO bandDAO;
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private RequestDAO requestDAO;

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

        req.setAttribute("isPending",false);
        if(currUser != null && !bandDAO.isUserInBand(currUser.getId(), bandId)){
            try {
                int reqId = requestDAO.getId(loggedInUserId,bandId);
                if(reqId!=Constants.NO_ID){
                    Request request = requestDAO.getById(reqId);
                    req.setAttribute("isPending",!request.isProcessed());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        RequestDispatcher rd = req.getRequestDispatcher("bandPage.jsp");
        rd.forward(req, resp);
    }
}
