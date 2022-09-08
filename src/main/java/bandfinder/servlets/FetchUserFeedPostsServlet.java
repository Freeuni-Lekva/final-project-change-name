package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.PostDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.Post;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FetchUserFeedPostsServlet", value = "/fetchUserFeedPosts")
public class FetchUserFeedPostsServlet extends ServletBase {

    @AutoInjectable
    private PostDAO postDAO;
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private BandDAO bandDAO;

    private record PostWrapper(int id, Integer authorUserId, String authorUserName,
                               Integer authorBandId, String authorBandName,
                               String text, String date) {}

    private static final int maxNumPostsToFetch = 2;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        List<Post> posts = getPostsFromDatabase(userId, request);
        List<PostWrapper> wrappedPosts = wrapPosts(posts);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        out.print(objectMapper.writeValueAsString(wrappedPosts));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private List<Post> getPostsFromDatabase(int userId, HttpServletRequest request) {
        List<Post> posts;
        if(request.getParameter("lastPostFetchedId") == null) {
            posts = postDAO.getUserFeedNewestPosts(userId, maxNumPostsToFetch);
        }else {
            int lastPostFetchedId = Integer.parseInt(request.getParameter("lastPostFetchedId"));
            posts = postDAO.getUserFeedPostsBeforeId(userId, lastPostFetchedId, maxNumPostsToFetch);
        }
        return posts;
    }

    private List<PostWrapper> wrapPosts(List<Post> posts) {
        List<PostWrapper> wrappedPosts = new ArrayList<>();
        for(Post post : posts) {
            int id = post.getId();
            Integer authorUserId = post.getAuthorUser();
            String authorUserName = userDAO.getById(authorUserId).getStageName();
            Integer authorBandId = post.getAuthorBand();
            String text = post.getText();
            String date = post.getDate().toString();
            PostWrapper postWrapper;
            if(authorBandId == null) {
                postWrapper = new PostWrapper(id, authorUserId, authorUserName,
                        null, null, text, date);
            }else {
                String authorBandName = bandDAO.getById(authorBandId).getName();
                postWrapper = new PostWrapper(id, authorUserId, authorUserName,
                        authorBandId, authorBandName, text, date);
            }
            wrappedPosts.add(postWrapper);
        }
        return wrappedPosts;
    }
}
