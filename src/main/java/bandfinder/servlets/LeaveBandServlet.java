package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Injector;
import bandfinder.models.User;
import bandfinder.serviceimplementations.SQLBandDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LeaveBandServlet", value = "/leaveBand")
public class LeaveBandServlet extends ServletBase {

    @AutoInjectable
    private BandDAO bandDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bandId = Integer.parseInt(request.getParameter("bandId"));
        int userId = ((User) request.getSession().getAttribute("user")).getId();

        bandDAO.removeMemberFromBand(userId, bandId);
        response.sendRedirect("/myBands");
    }
}
