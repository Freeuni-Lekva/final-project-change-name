package bandfinder.servlets;

import bandfinder.dao.FollowDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Follow;
import bandfinder.models.User;
import bandfinder.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="LoadUserProfileServlet", value = "/LoadUserProfileServlet")
public class LoadUserProfileServlet extends ServletBase{
    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private FollowDAO followDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int loggedInUserId = authenticationService.authenticate(loginToken);
        User loggedUser = userDAO.getById(loggedInUserId);
        User user = userDAO.getById(Integer.parseInt(req.getParameter("id")));

        Follow follow = new Follow(Constants.NO_ID, user.getId());

        if(loggedUser != null){
            req.setAttribute("loggedUser", true);
            follow.setFollowerID(loggedUser.getId());
            req.setAttribute("sameUser", loggedUser.equals(user));
        }else{
            req.setAttribute("loggedUser", false);
        }


        req.setAttribute("following", followDAO.followExists(follow));
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }
}
