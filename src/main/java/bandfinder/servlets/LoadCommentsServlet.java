package bandfinder.servlets;

import bandfinder.dao.CommentDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Comment;
import bandfinder.serviceimplementations.SQLCommentDAO;
import bandfinder.services.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/LoadMoreComments", name = "LoadMoreComments")
public class LoadCommentsServlet extends ServletBase{


    CommentDAO commentDAO = new SQLCommentDAO();

    @AutoInjectable
    UserDAO userDAO;

    @AutoInjectable
    AuthenticationService authenticationService;

    private static final int MAX_BATCH_SIZE = 10;

    public LoadCommentsServlet() throws SQLException, ClassNotFoundException {
    }

    private List<Comment.CommentDisplay> CommentsToDisplayComments(List<Comment> l){
        List<Comment.CommentDisplay> list = new ArrayList<>();
        for (Comment c : l){
            Timestamp t = new Timestamp(c.getDate().getTime());
            String date = t.toString();

            String username = userDAO.getById(c.getAuthorId()).getStageName();


            Comment.CommentDisplay commentDisplay = new Comment.CommentDisplay(username, date, c.getText(), c.getLikes());
            list.add(commentDisplay);
        }
        return list;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int post_id = Integer.parseInt(req.getParameter("post_id"));

        int num_comments = Integer.parseInt(req.getParameter("num_comments"));

        int user_id = authenticationService.authenticate((String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME));

        List<Comment> l = new ArrayList<>();

        if(req.getParameter("sort_type").equals("sort_by_likes")){
            l = commentDAO.getCommentBatchPriorityLikes(post_id, user_id, MAX_BATCH_SIZE, num_comments);
        }else if(req.getParameter("sort_type").equals("sort_by_date")){
            l = commentDAO.getCommentBatchPriorityDate(post_id, user_id, MAX_BATCH_SIZE, num_comments);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Comment.CommentDisplay> convertedList = CommentsToDisplayComments(l);

        PrintWriter pw = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(pw, convertedList);
    }
}
