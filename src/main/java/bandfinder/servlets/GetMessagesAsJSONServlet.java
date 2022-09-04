package bandfinder.servlets;

import bandfinder.dao.MessageDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.MessageViewModel;
import bandfinder.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetMessagesAsJSONServlet", value = "/GetMessagesAsJSONServlet")
public class GetMessagesAsJSONServlet extends ServletBase {
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private MessageDAO messageDAO;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        int recipientId = Integer.parseInt(request.getParameter("recipientId"));
        User recipientUser = userDAO.getById(recipientId);

        boolean messagesStartFromInit = Boolean.parseBoolean(request.getParameter("initmsgs"));
        int msgCount = Integer.parseInt(request.getParameter("msgcount"));
        List<MessageViewModel> resultList = null;
        if(messagesStartFromInit) {
            resultList = messageDAO.getNewMessages(currentUser.getId(), recipientId, msgCount);
        } else {
            int lastMessageId = Integer.parseInt(request.getParameter("lastmsgid"));
            resultList = messageDAO.getMessagesBefore(currentUser.getId(), recipientId, lastMessageId, msgCount);
        }
        ObjectMapper messageMapper = new ObjectMapper();
        String jsonString = messageMapper.writeValueAsString(resultList);

        response.setContentType("application/json");
        response.getWriter().print(jsonString);
    }
}
