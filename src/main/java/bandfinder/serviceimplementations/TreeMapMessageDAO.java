package bandfinder.serviceimplementations;

import bandfinder.dao.MessageDAO;
import bandfinder.dao.UserDAO;
import bandfinder.models.Message;
import bandfinder.models.MessageViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TreeMapMessageDAO implements MessageDAO {
    private final TreeMap<Integer, Message> messages;
    private final UserDAO userDAO;
    public TreeMapMessageDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
        messages = new TreeMap<>();
    }

    @Override
    public Message create(Message model) {
        int id = model.getId();
        if(model.getId() == -1) {
            id = messages.size() + 1;
            model.setId(id);
        }
        messages.put(id, model);
        return model;
    }

    @Override
    public Message update(Message model) {
        messages.put(model.getId(), model);
        return model;
    }

    @Override
    public boolean delete(int id) {
        Message deletedMsg = messages.remove(id);
        if(deletedMsg != null) {
            deletedMsg.setId(-1); // Needed to behave correctly when re-adding elements with the same id
            return true;
        }
        return false;
    }

    @Override
    public Message getById(int id) {
        return messages.get(id);
    }

    @Override
    public List<Message> getAll() {
        return new ArrayList<>(messages.values());
    }

    @Override
    public MessageViewModel getMessageViewForId(int id) {
        Message currentMsg = messages.get(id);
        String senderName = userDAO.getById(currentMsg.getSenderId()).getFullName();
        String receiverName = userDAO.getById(currentMsg.getReceiverId()).getFullName();
        String formattedTimestamp = MessageViewModel.formatDate(currentMsg.getTimestamp());
        return new MessageViewModel(id, senderName, receiverName, currentMsg.getContent(), formattedTimestamp);
    }

    @Override
    public List<MessageViewModel> getNewMessages(int user1Id, int user2Id, int count) {
        List<MessageViewModel> result = new ArrayList<>();
        int i = 0;
        for(int id : messages.descendingKeySet()) {
            Message currentMsg = messages.get(id);
            if(!((currentMsg.getSenderId() == user1Id && currentMsg.getReceiverId() == user2Id) ||
                (currentMsg.getSenderId() == user2Id && currentMsg.getReceiverId() == user1Id)))
                continue;
            if(i >= count)
                break;
            result.add(getMessageViewForId(id));
            i++;
        }
        return result;
    }

    @Override
    public List<MessageViewModel> getMessagesBefore(int user1Id, int user2Id, int messageId, int count) {
        List<MessageViewModel> result = new ArrayList<>();
        int i = 0;
        for(int id : messages.descendingKeySet()) {
            Message currentMsg = messages.get(id);
            if(!((currentMsg.getSenderId() == user1Id && currentMsg.getReceiverId() == user2Id) ||
                    (currentMsg.getSenderId() == user2Id && currentMsg.getReceiverId() == user1Id)))
                continue;
            if(id >= messageId)
                continue;
            if(i >= count)
                break;
            result.add(getMessageViewForId(id));
            i++;
        }
        return result;
    }
}
