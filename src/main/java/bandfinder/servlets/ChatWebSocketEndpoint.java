package bandfinder.servlets;

import bandfinder.dao.MessageDAO;
import bandfinder.infrastructure.Injector;
import bandfinder.services.AuthenticationService;
import bandfinder.models.Message;
import bandfinder.models.MessageViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@ServerEndpoint("/chatWebSocket")
public class ChatWebSocketEndpoint {
    private AuthenticationService authenticationService;
    private MessageDAO messageDAO;

    private record MessageJSONRequest(boolean isInfoMsg, String senderToken, int receiverId, String content){}

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        messageDAO = Injector.getImplementation(MessageDAO.class);
        authenticationService = Injector.getImplementation(AuthenticationService.class);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        ObjectMapper messageMapper = new ObjectMapper();
        MessageJSONRequest newMessageRequest;

        try {
            newMessageRequest = messageMapper.readValue(msg, MessageJSONRequest.class);
            if(!newMessageRequest.isInfoMsg) {
                sendMessage(session, newMessageRequest);
            } else {
                registerInfo(session, newMessageRequest);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(Session session, MessageJSONRequest newMessageRequest) throws IOException {
        int senderId = authenticationService.authenticate(newMessageRequest.senderToken);
        Message newMessage = new Message(senderId,
                newMessageRequest.receiverId,
                newMessageRequest.content,
                Timestamp.from(Instant.now()));
        messageDAO.create(newMessage);
        MessageViewModel responseMsg = messageDAO.getMessageViewForId(newMessage.getId());

        ObjectMapper messageMapper = new ObjectMapper();
        String messageJson = messageMapper.writeValueAsString(responseMsg);
        session.getOpenSessions()
                        .stream()
                        .filter((s -> messageRequestBelongsToUser(s, newMessageRequest)))
                        .forEach(s -> {
                            if(s.isOpen())
                                s.getAsyncRemote().sendText(messageJson);
                        });
    }

    private boolean messageRequestBelongsToUser(Session s, MessageJSONRequest msgRequest) {
        int senderId = authenticationService.authenticate(msgRequest.senderToken);
        int sessionUserId = (int) s.getUserProperties().get("userId");
        int sessionRecipientId = (int) s.getUserProperties().get("recipientId");
        return (sessionUserId == senderId && sessionRecipientId == msgRequest.receiverId) ||
                (sessionUserId == msgRequest.receiverId && sessionRecipientId == senderId);
    }
    private void registerInfo(Session session, MessageJSONRequest message) {
        int userId = authenticationService.authenticate(String.valueOf(message.senderToken));
        session.getUserProperties().put("userId", userId);
        session.getUserProperties().put("recipientId", message.receiverId);
    }
}
