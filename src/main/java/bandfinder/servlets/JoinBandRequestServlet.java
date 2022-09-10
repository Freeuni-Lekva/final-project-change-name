package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.RequestDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Request;
import bandfinder.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

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
            resp.sendRedirect(Constants.URL_BAND_PAGE+".jsp?bandId=" + bandId); //reload the page, user is already in band
            return;
        }
        try {
            int requestId = requestDAO.getId(userId,bandId);
            if(requestId==Constants.NO_ID){ //request does not exist, create new one
                requestDAO.create(new Request(userId,bandId));
            }else{
                Request request = requestDAO.getById(requestId);
                if(request.isProcessed()){ //processed request already exists in db, means user had requested join before, was accepted, then kicked, now sending again
                    request.setProcessed(false); //set to unprocessed
                    requestDAO.update(request);
                }else{ //unprocessed request already exists, meaning request is pending
                    //do nothing
                }
            }
            resp.sendRedirect(Constants.URL_BAND_PAGE+".jsp?bandId=" + bandId); //reload the page
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
