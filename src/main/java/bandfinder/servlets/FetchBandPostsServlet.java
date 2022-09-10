package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.PostDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.infrastructure.Injector;
import bandfinder.models.Post;
import bandfinder.models.PostWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "FetchBandPostsServlet", value = "/fetchBandPosts")
public class FetchBandPostsServlet extends HttpServlet {

    private final PostDAO postDAO = Injector.getImplementation(PostDAO.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bandId = Integer.parseInt(request.getParameter("bandId"));
        List<Post> posts = getBandPostsFromDatabase(bandId, request);
        List<PostWrapper> wrappedPosts = PostWrapper.wrapPosts(posts);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(out, wrappedPosts);
    }

    private List<Post> getBandPostsFromDatabase(int bandId, HttpServletRequest request) {
        List<Post> posts;
        if(request.getParameter("lastPostFetchedId") == null) {
            posts = postDAO.getBandNewestPosts(bandId, Constants.POSTS_TO_FETCH_MAX_NUM);
        }else {
            int lastPostFetchedId = Integer.parseInt(request.getParameter("lastPostFetchedId"));
            posts = postDAO.getBandPostsBeforeId(bandId, lastPostFetchedId, Constants.POSTS_TO_FETCH_MAX_NUM);
        }
        return posts;
    }
}
