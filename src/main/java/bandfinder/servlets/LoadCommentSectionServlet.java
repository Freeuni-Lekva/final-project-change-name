package bandfinder.servlets;

import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = authenticationService.authenticate((String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME));
        if(user_id == Constants.NO_ID){
            req.setAttribute("logged_in", false);
        }else{
            req.setAttribute("logged_in", true);
        }

        String post_id = req.getParameter("post_id");
        req.getRequestDispatcher("post.jsp?post_id=" + post_id).forward(req, resp);
    }
}
