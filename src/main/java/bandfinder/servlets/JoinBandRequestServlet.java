package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.RequestDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "JoinBandRequestServlet", value = Constants.URL_JOIN_BAND_REQUEST)
public class JoinBandRequestServlet extends ServletBase{

    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private BandDAO bandDAO;
    @AutoInjectable
    private RequestDAO requestDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //when user clicks "request to join band" button on band page
        int bandId = Integer.parseInt(req.getParameter("bandId"));
        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int userId = authenticationService.authenticate(loginToken);
        if(bandDAO.isUserInBand(userId,bandId)){
            resp.sendRedirect(Constants.URL_BAND_PAGE+"?bandId=" + bandId); //reload the page
            return;
        }
        //if not processed request in dao already exists
    }
}
