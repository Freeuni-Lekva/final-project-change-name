package bandfinder.servlets;

import bandfinder.dao.FollowDAO;
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

@WebServlet(name="FollowServlet", value="/FollowServlet")
public class FollowServlet extends ServletBase{
    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private FollowDAO followDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int followeeId = Integer.parseInt(req.getParameter("user_id"));
        String loginToken = (String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int followerId = authenticationService.authenticate(loginToken);

        Follow newFollow = new Follow(followerId, followeeId);

        if(!followDAO.followExists(newFollow)){
            followDAO.create(newFollow);
        }

        resp.sendRedirect("profile.jsp?id=" + followeeId);
    }
}
