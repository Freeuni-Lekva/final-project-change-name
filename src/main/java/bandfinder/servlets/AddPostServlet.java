package bandfinder.servlets;

import bandfinder.dao.PostDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Post;
import bandfinder.services.AuthenticationService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "AddPostServlet", value = "/addPost")
public class AddPostServlet extends ServletBase {
    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private PostDAO postDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = authenticationService.authenticate((String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME));
        String postContent = request.getParameter("post-content");

        String postType = request.getParameter("post-type");
        if(!postContent.isBlank()) {
            Post newPost;
            if(postType.equals("user")) {
                newPost = new Post(userId, null, postContent, new Timestamp(System.currentTimeMillis()));
            } else if (postType.equals("band")){
                int authorBandId = Integer.parseInt(request.getParameter("band-id"));
                newPost = new Post(userId, authorBandId, postContent, new Timestamp(System.currentTimeMillis()));
            } else {
                throw new RuntimeException("No valid \"post-type\" parameter.");
            }
            postDAO.create(newPost);
        }
        request.getRequestDispatcher("/userFeed.jsp").forward(request, response);
    }
}
