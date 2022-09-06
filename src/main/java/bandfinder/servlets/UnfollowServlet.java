package bandfinder.servlets;

import bandfinder.dao.FollowDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.Follow;
import bandfinder.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="UnfollowServlet", value="/UnfollowServlet")
public class UnfollowServlet extends ServletBase{

    @AutoInjectable
    FollowDAO followDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int followeeId = Integer.valueOf(req.getParameter("user_id"));
        int followerId = ((User)req.getSession().getAttribute("user")).getId();

        Follow oldFollow = new Follow(followerId, followeeId);

        if(followDAO.followExists(oldFollow)){
            followDAO.delete(oldFollow);
        }

        resp.sendRedirect("profile.jsp?id=" + followeeId);
    }
}
