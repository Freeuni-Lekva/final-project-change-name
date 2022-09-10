package bandfinder.servlets;

import bandfinder.dao.CommentDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Comment;
import bandfinder.services.AuthenticationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.json.JsonObject;
import javax.management.IntrospectionException;
import javax.management.remote.JMXAuthenticator;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

@WebServlet(value = "/SubmitComment", name = "SubmitComment")
public class SubmitCommentServlet extends ServletBase{

    @AutoInjectable
    UserDAO userDAO;
    @AutoInjectable
    CommentDAO commentDAO;
    @AutoInjectable
    AuthenticationService authenticationService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = authenticationService.authenticate((String) req.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME));

        if(user_id == Constants.NO_ID){
            return;
        }


        ObjectMapper objectMapper = new ObjectMapper();
        Comment.CommentData commentData = objectMapper.readValue(req.getReader(), Comment.CommentData.class);

        String text = commentData.getText();
        int post_id = Integer.parseInt(commentData.getPost_id());

        Date comm_time = new Date();
        comm_time.setTime(Long.parseLong(commentData.getDate()));

        Comment com = new Comment(comm_time, text, user_id, post_id, 0);
        commentDAO.create(com);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter pw = resp.getWriter();

        String username = userDAO.getById(user_id).getStageName();
        Timestamp date = new Timestamp(comm_time.getTime());
        Comment.CommentDisplay commentDisplay = new Comment.CommentDisplay(username, date.toString(), text, 0);

        objectMapper.writeValue(pw, commentDisplay);
    }
}
