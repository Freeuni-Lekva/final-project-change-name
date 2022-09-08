package bandfinder.servlets;

import bandfinder.dao.MessageDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.Constants;
import bandfinder.models.MessageViewModel;
import bandfinder.models.User;
import bandfinder.services.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetMessagesAsJSONServlet", value = "/GetMessagesAsJSONServlet")
public class GetMessagesAsJSONServlet extends ServletBase {
    @AutoInjectable
    private AuthenticationService authenticationService;
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private MessageDAO messageDAO;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginToken = (String) request.getSession().getAttribute(Constants.LOGIN_TOKEN_ATTRIBUTE_NAME);
        int loggedInUserId = authenticationService.authenticate(loginToken);
        User currentUser = userDAO.getById(loggedInUserId);

        int recipientId = Integer.parseInt(request.getParameter("recipientId"));

        boolean messagesStartFromInit = Boolean.parseBoolean(request.getParameter("initmsgs"));
        int msgCount = Integer.parseInt(request.getParameter("msgcount"));
        List<MessageViewModel> resultList;
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
