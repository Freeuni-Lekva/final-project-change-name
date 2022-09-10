package bandfinder.servlets;

import bandfinder.dao.PostDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Post;
import bandfinder.models.PostWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "FetchUserPostsServlet", value = "/fetchUserPosts")
public class FetchUserPostsServlet extends HttpServlet {

    @AutoInjectable
    private PostDAO postDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        List<Post> posts = getUserPostsFromDatabase(userId, request);
        List<PostWrapper> wrappedPosts = PostWrapper.wrapPosts(posts);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(out, wrappedPosts);
    }

    private List<Post> getUserPostsFromDatabase(int userId, HttpServletRequest request) {
        List<Post> posts;
        if(request.getParameter("lastPostFetchedId") == null) {
            posts = postDAO.getUserNewestPosts(userId, Constants.POSTS_TO_FETCH_MAX_NUM);
        }else {
            int lastPostFetchedId = Integer.parseInt(request.getParameter("lastPostFetchedId"));
            posts = postDAO.getUserPostsBeforeId(userId, lastPostFetchedId, Constants.POSTS_TO_FETCH_MAX_NUM);
        }
        return posts;
    }
}
