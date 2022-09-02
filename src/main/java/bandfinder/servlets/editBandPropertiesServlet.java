package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.Band;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "editBandPropertiesServlet", value = "/editBandProperties")
public class editBandPropertiesServlet extends ServletBase {
    @AutoInjectable
    private BandDAO bandDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bandId = Integer.parseInt(request.getParameter("bandId"));
        String bandNewName = request.getParameter("bandNewName");

        Band band = bandDAO.getById(bandId);
        band.setName(bandNewName);
        bandDAO.update(band);

        response.sendRedirect("/bandProperties.jsp?bandId=" + bandId);
    }
}
