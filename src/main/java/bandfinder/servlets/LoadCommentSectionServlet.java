package bandfinder.servlets;

import bandfinder.dao.PostDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Post;
import bandfinder.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/comments", name = "comments")
public class LoadCommentSectionServlet extends ServletBase{

    @AutoInjectable
    AuthenticationService authenticationService;

    @AutoInjectable
    PostDAO postDAO;

    @AutoInjectable
    UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = authenticationService.authenticate((String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME));
        if(user_id == Constants.NO_ID){
            req.setAttribute("logged_in", false);
        }else{
            req.setAttribute("logged_in", true);
        }

        String post_id = req.getParameter("post_id");
        Post post = postDAO.getById(Integer.parseInt(post_id));

        if(post == null){
            resp.sendRedirect("errorPage.html");
            return;
        }

        req.setAttribute("post", post);

        req.getRequestDispatcher("post.jsp?post_id=" + post_id).forward(req, resp);
    }
}
