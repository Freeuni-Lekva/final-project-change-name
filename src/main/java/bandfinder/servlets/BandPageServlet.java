package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="bandPage", value="/bandPage")
public class BandPageServlet extends ServletBase {

    @AutoInjectable
    BandDAO bandDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User curUser = (User) req.getSession().getAttribute("user");
        int bandId = Integer.parseInt(req.getParameter("bandId"));

        if(curUser != null && bandDAO.isUserInBand(curUser.getId(), bandId)){
            req.setAttribute("extra_display", true);
        }else{
            req.setAttribute("extra_display", false);
        }

        RequestDispatcher rd = req.getRequestDispatcher("bandPage.jsp");
        rd.forward(req, resp);
    }
}
