package bandfinder.dao;

import bandfinder.models.Message;
import bandfinder.models.MessageViewModel;

import java.util.List;

public interface MessageDAO extends DAO<Message>{
    MessageViewModel getMessageViewForId(int id);
    List<MessageViewModel> getNewMessages(int user1Id, int user2Id, int count);
    List<MessageViewModel> getMessagesBefore(int user1Id, int user2Id, int messageId, int count);
}
