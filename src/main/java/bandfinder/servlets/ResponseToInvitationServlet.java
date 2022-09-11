package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.InvitationDAO;
import bandfinder.dao.RequestDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Invitation;
import bandfinder.models.Request;
import bandfinder.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ResponseToInvitationServlet", value = Constants.URL_RESPONSE_TO_INVITATION)
public class ResponseToInvitationServlet extends ServletBase{

    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private BandDAO bandDAO;
    @AutoInjectable
    private InvitationDAO invitationDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //when user(receiverId) clicks accept/reject to received invitation from a band
        int bandId = Integer.parseInt(req.getParameter("bandId"));
        boolean answer = Boolean.parseBoolean((req.getParameter("answer")));
        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int userId = authenticationService.authenticate(loginToken);

        if(bandDAO.isUserInBand(userId,bandId)){
            resp.sendRedirect("/notifications.jsp");
            //user is already in band, RELOAD PAGE
            return;
        }

        int invId = invitationDAO.getId(userId,bandId);
        if(invId!=Constants.NO_ID){
            Invitation invitation = invitationDAO.getById(invId);
            if(invitation.isProcessed()){
                resp.sendRedirect("/notifications.jsp");//invitation was already processed by other browser for example, RELOAD PAGE
            }else{
                invitation.setProcessed(true);
                invitationDAO.update(invitation);
                if(answer){
                    //invitation was accepted
                    bandDAO.addMemberToBand(userId,bandId);
                    resp.sendRedirect(Constants.URL_BAND_PAGE+".jsp?bandId=" + bandId); //send to band page
                }else{
                    resp.sendRedirect("/notifications.jsp");//invitation was rejected
                }
            }
        }else{
            resp.sendRedirect("/notifications.jsp");//there was no invitation at all, RELOAD PAGE
        }

    }
}
