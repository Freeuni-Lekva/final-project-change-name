package bandfinder.servlets;

import bandfinder.dao.MessageDAO;
import bandfinder.infrastructure.Injector;
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
    private MessageDAO messageDAO;

    private record MessageJSONRequest(boolean isInfoMsg, int senderId, int receiverId, String content){}

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        messageDAO = Injector.getImplementation(MessageDAO.class);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        //TODO Extremely insecure
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

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    private void sendMessage(Session session, MessageJSONRequest newMessageRequest) throws IOException {
        Message newMessage = new Message(newMessageRequest.senderId,
                newMessageRequest.receiverId,
                newMessageRequest.content,
                Timestamp.from(Instant.now()));
        messageDAO.create(newMessage);
        MessageViewModel responseMsg = messageDAO.getMessageViewForId(newMessage.getId());

        ObjectMapper messageMapper = new ObjectMapper();
        String messageJson = messageMapper.writeValueAsString(responseMsg);
        for(Session s : session.getOpenSessions()
                        .stream()
                        .filter((s -> messageRequestBelongsToUser(s, newMessageRequest)))
                        .toList()) {
            if(s.isOpen())
                s.getAsyncRemote().sendText(messageJson);
        }
    }

    private boolean messageRequestBelongsToUser(Session s, MessageJSONRequest msgRequest) {
        int sessionUserId = (int) s.getUserProperties().get("userId");
        int sessionRecipientId = (int) s.getUserProperties().get("recipientId");
        return (sessionUserId == msgRequest.senderId && sessionRecipientId == msgRequest.receiverId) ||
                (sessionUserId == msgRequest.receiverId && sessionRecipientId == msgRequest.senderId);
    }
    private void registerInfo(Session session, MessageJSONRequest message) {
        session.getUserProperties().put("userId", message.senderId);
        session.getUserProperties().put("recipientId", message.receiverId);
    }
}
