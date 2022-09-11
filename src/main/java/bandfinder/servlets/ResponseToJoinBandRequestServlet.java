package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.RequestDAO;
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

@WebServlet(name = "ResponseToJoinBandRequestServlet", value = Constants.URL_RESPONSE_TO_JOIN_BAND_REQUEST)
public class ResponseToJoinBandRequestServlet extends ServletBase{

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
        //when user clicks accept/reject to received band joining request notification from user B
        int bandId = Integer.parseInt(req.getParameter("bandId"));
        int requesterId = Integer.parseInt(req.getParameter("userId"));
        boolean answer = Boolean.parseBoolean((req.getParameter("answer")));
        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int userId = authenticationService.authenticate(loginToken);

        if(!bandDAO.isUserInBand(userId,bandId)){
            resp.sendRedirect("/notifications.jsp");//user is not in band, therefore he can't manage requests, RELOAD PAGE
            return;
        }
        if(bandDAO.isUserInBand(requesterId,bandId)){
            resp.sendRedirect("/notifications.jsp");//requester is already in band, no point in reaccepting him, RELOAD PAGE
            return;
        }

        int reqId = requestDAO.getId(requesterId,bandId);
        if(reqId!=Constants.NO_ID){
            Request request = requestDAO.getById(reqId);
            if(request.isProcessed()){
                resp.sendRedirect("/notifications.jsp");//request was already processed by other band members, RELOAD PAGE
            }else{
                request.setProcessed(true);
                requestDAO.update(request);
                if(answer){
                    //join request was accepted
                    bandDAO.addMemberToBand(requesterId,bandId);
                    resp.sendRedirect(Constants.URL_BAND_PAGE+".jsp?bandId=" + bandId); //send to band page
                }else{
                    resp.sendRedirect("/notifications.jsp");//join request was rejected
                }
            }
        }else{
            resp.sendRedirect("/notifications.jsp");//there was no request at all, RELOAD PAGE
        }
    }
}
