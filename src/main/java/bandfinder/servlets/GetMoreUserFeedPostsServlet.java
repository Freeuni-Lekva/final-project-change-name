package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.PostDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.Post;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetMoreUserFeedPostsServlet", value = "/getMoreUserFeedPosts")
public class GetMoreUserFeedPostsServlet extends ServletBase {

    @AutoInjectable
    private PostDAO postDAO;
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private BandDAO bandDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int lastPostFetchedId = Integer.parseInt(request.getParameter("lastPostFetchedId"));
        List<Post> posts;
        if(lastPostFetchedId == -1) {
            posts = postDAO.getUserFeedNewestPosts(userId, 10);
        }else {
            posts = postDAO.getUserFeedPostsBeforeId(userId, lastPostFetchedId, 10);
        }
        PrintWriter out = response.getWriter();
        for(Post post : posts) {
            String postAuthor;
            if(post.getAuthorBand() == null) {
                postAuthor = userDAO.getById(post.getAuthorUser()).getStageName();
            }else {
                postAuthor = bandDAO.getById(post.getAuthorBand()).getName();
            }
            out.println("<div class=\"post\" id=\"" + post.getId() + "\">");
            out.println("<div class=\"bandProperties\">" + postAuthor + "</div>");
            out.println("<div class=\"postText\">" + post.getText() + "</div>");
            out.println("</div>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
