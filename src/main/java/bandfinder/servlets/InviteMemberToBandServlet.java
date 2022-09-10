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

@WebServlet(name = "InviteMemberToBandServlet", value = Constants.URL_INVITE_MEMBER_TO_BAND_REQUEST)
public class InviteMemberToBandServlet extends ServletBase{
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
        int bandId = Integer.parseInt(req.getParameter("bandId"));
        int targetId = Integer.parseInt(req.getParameter("targetId"));
        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int userId = authenticationService.authenticate(loginToken);

        if(!bandDAO.isUserInBand(userId,bandId) || bandDAO.isUserInBand(targetId,bandId)){
            resp.sendRedirect("/profile.jsp?userId=" + targetId);
            return;
        }
        int invitationId = invitationDAO.getId(targetId,bandId);
        if(invitationId==Constants.NO_ID){ //invitation does not exist, create new one
            invitationDAO.create(new Invitation(targetId,bandId));
        }else{
            Invitation invitation = invitationDAO.getById(invitationId);
            if(invitation.isProcessed()){
                invitation.setProcessed(false); //set to unprocessed
                invitationDAO.update(invitation);
            }else{ //unprocessed invitation already exists, meaning invitation is pending
            }
        }
        resp.sendRedirect("/profile.jsp?id=" + targetId);
    }
}
