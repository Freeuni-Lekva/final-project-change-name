package bandfinder.servlets;


import bandfinder.dao.BandDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.Band;
import bandfinder.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateBandServlet", value = "/newBand")
public class CreateBandServlet extends ServletBase{

    @AutoInjectable
    private BandDAO bandDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String bandName = req.getParameter("bandName");

        Band newBand = bandDAO.create(new Band(bandName));
        bandDAO.addMemberToBand(user.getId(), newBand.getId());
        resp.sendRedirect("bandPage.jsp?bandId="+newBand.getId());
        //req.getRequestDispatcher("/bandPage.jsp").forward(req, resp);
    }
}
