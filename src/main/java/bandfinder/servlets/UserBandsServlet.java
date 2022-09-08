package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Band;
import bandfinder.models.User;
import bandfinder.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "UserBandsServlet", value = "/myBands")
public class UserBandsServlet extends ServletBase{
    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private BandDAO bandDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int loggedInUserId = authenticationService.authenticate(loginToken);
        User user = userDAO.getById(loggedInUserId);

        ArrayList<Integer> userBandIds = (ArrayList<Integer>) bandDAO.getAllBandIDsForUser(user.getId());
        ArrayList<Band> userBandsList = new ArrayList<>();
        for(Integer bandId : userBandIds){
            userBandsList.add(bandDAO.getById(bandId));
        }
        req.setAttribute("UserBandsList",userBandsList);
        req.getRequestDispatcher("/myBands.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/newBand.jsp").forward(req, resp);
    }
}
