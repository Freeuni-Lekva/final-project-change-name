package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.infrastructure.AutoInjectable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "RemoveMembersServlet", value = "/removeMembers")
public class RemoveMembersServlet extends ServletBase {
    @AutoInjectable
    private BandDAO bandDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bandId = Integer.parseInt(request.getParameter("bandId"));
        Enumeration<String> emails = request.getParameterNames();

        while(emails.hasMoreElements()) {
            String email = emails.nextElement();
            if(email.equals("bandId")) continue;
            int memberId = Integer.parseInt(request.getParameter(email));
            bandDAO.removeMemberFromBand(memberId, bandId);
        }

        response.sendRedirect("/manageMembers.jsp?bandId=" + bandId);
    }
}
