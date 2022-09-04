package bandfinder.serviceimplementations;

import bandfinder.dao.MessageDAO;
import bandfinder.models.Message;
import bandfinder.models.MessageViewModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLMessageDAO implements MessageDAO {
    private final Connection connection;

    public SQLMessageDAO() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/bandfinder?user=root&password=rootroot");
    }

    @Override
    public Message create(Message model) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO messages (sender_id, receiver_id, content, time) VALUES (?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, model.getSenderId());
            statement.setInt(2, model.getReceiverId());
            statement.setString(3, model.getContent());
            Timestamp sqlTimestamp = new Timestamp(model.getTimestamp().getTime());
            statement.setTimestamp(4, sqlTimestamp);
            statement.executeUpdate();

            ResultSet idResultSet = statement.getGeneratedKeys();
            idResultSet.next();
            int newId = idResultSet.getInt(1);
            model.setId(newId);

            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Message update(Message model) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE messages SET sender_id = ?, " +
                            "receiver_id = ?, " +
                            "content = ?, " +
                            "time = ? WHERE id = ?;");
            statement.setInt(5, model.getId());
            statement.setInt(1, model.getSenderId());
            statement.setInt(2, model.getReceiverId());
            statement.setString(3, model.getContent());
            statement.setTimestamp(4, new Timestamp(model.getTimestamp().getTime()));
            statement.executeUpdate();
            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM messages WHERE id = ?;");

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM messages WHERE id = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();
                return null;
            }
            Message resultMsg = new Message(id,
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getTimestamp(5));
            statement.close();
            return resultMsg;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> getAll() {
        List<Message> msgList = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM messages;"
            );
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                Message nextMsg = new Message(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5));
                msgList.add(nextMsg);
            }
            return msgList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFullName(String firstName, String surname, String stageName) {
        if(stageName.isBlank()) {
            return firstName + ' ' + surname;
        }
        return firstName + " \"" + stageName + "\" " + surname;
    }


    @Override
    public MessageViewModel getMessageViewForId(int id) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT m.id, m.content, m.time, " +
                            "s.first_name, s.surname, s.stage_name, " +
                            "r.first_name, r.surname, r.stage_name FROM messages m " +
                            "LEFT JOIN users s ON m.sender_id = s.id " +
                            "LEFT JOIN users r ON m.receiver_id = r.id " +
                            "WHERE m.id = ?;"
            );
            statement.setInt(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            String senderFullName = getFullName(resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            String receiverFullName = getFullName(resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));
            Timestamp timestamp = resultSet.getTimestamp(3);
            String time = MessageViewModel.formatTimestamp(timestamp);
            return new MessageViewModel(
                    resultSet.getInt(1),
                    senderFullName,
                    receiverFullName,
                    resultSet.getString(2),
                    time
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MessageViewModel> getNewMessages(int user1Id, int user2Id, int count) {
        List<MessageViewModel> msgList = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT m.id, m.content, m.time, " +
                       "s.first_name, s.surname, s.stage_name, " +
                       "r.first_name, r.surname, r.stage_name FROM messages m " +
                       "LEFT JOIN users s ON m.sender_id = s.id " +
                       "LEFT JOIN users r ON m.receiver_id = r.id " +
                       "WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) " +
                       "ORDER BY time DESC LIMIT ?;"
            );
            statement.setInt(1, user1Id);
            statement.setInt(2, user2Id);
            statement.setInt(3, user2Id);
            statement.setInt(4, user1Id);
            statement.setInt(5, count);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                String senderFullName = getFullName(resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
                String receiverFullName = getFullName(resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));
                Timestamp timestamp = resultSet.getTimestamp(3);
                String time = MessageViewModel.formatTimestamp(timestamp);
                MessageViewModel msg = new MessageViewModel(
                    resultSet.getInt(1),
                    senderFullName,
                    receiverFullName,
                    resultSet.getString(2),
                    time
                );
                msgList.add(msg);
            }
            return msgList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MessageViewModel> getMessagesBefore(int user1Id, int user2Id, int messageId, int count) {
        List<MessageViewModel> msgList = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT m.id, m.content, m.time, " +
                    "s.first_name, s.surname, s.stage_name, " +
                    "r.first_name, r.surname, r.stage_name FROM messages m " +
                    "LEFT JOIN users s ON m.sender_id = s.id " +
                    "LEFT JOIN users r ON m.receiver_id = r.id " +
                    "WHERE ((sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)) AND m.id < ? " +
                    "ORDER BY time DESC LIMIT ?;"
            );
            statement.setInt(1, user1Id);
            statement.setInt(2, user2Id);
            statement.setInt(3, user2Id);
            statement.setInt(4, user1Id);
            statement.setInt(5, messageId);
            statement.setInt(6, count);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                String senderFullName = getFullName(resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
                String receiverFullName = getFullName(resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));
                Timestamp timestamp = resultSet.getTimestamp(3);
                String time = MessageViewModel.formatTimestamp(timestamp);
                MessageViewModel msg = new MessageViewModel(
                        resultSet.getInt(1),
                        senderFullName,
                        receiverFullName,
                        resultSet.getString(2),
                        time
                );
                msgList.add(msg);
            }
            return msgList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
