package bandfinder.serviceimplementations;

import bandfinder.dao.NotificationDAO;
import bandfinder.models.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLNotificationDAO implements NotificationDAO {

    private final Connection connection;
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/bandfinder?user=root&password=rootroot";

    public SQLNotificationDAO() throws ClassNotFoundException, SQLException {
        Class.forName(CLASS_NAME);
        connection = DriverManager.getConnection(URL);
    }

    public static final String CREATE_NOTIF = "INSERT INTO notifications (user_id, is_read, message, date) " +
                                              "VALUES (?, ?, ?, ?);";

    @Override
    public Notification create(Notification model) {
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_NOTIF, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, model.getUserId());
            statement.setBoolean(2, model.isRead());
            statement.setString(3, model.getMessage());
            statement.setTimestamp(4, model.getDate());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            model.setId(id);

            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String UPDATE_NOTIF = "UPDATE notifications " +
                                              "SET user_id = ?, is_read = ?, message = ?, date = ? " +
                                              "WHERE id = ?;";

    @Override
    public Notification update(Notification model) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_NOTIF);
            statement.setInt(1, model.getUserId());
            statement.setBoolean(2, model.isRead());
            statement.setString(3, model.getMessage());
            statement.setInt(4, model.getId());
            statement.setTimestamp(5, model.getDate());
            statement.executeUpdate();

            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static final String DELETE_NOTIF = "DELETE FROM notifications WHERE id = ?;";

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_NOTIF);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String NOTIF_BY_ID = "SELECT * FROM notifications WHERE id = ?;";

    @Override
    public Notification getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(NOTIF_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                Notification model = new Notification(id, rs.getInt(2),
                                                          rs.getBoolean(3),
                                                          rs.getString(4),
                                                          rs.getTimestamp(5));
                statement.close();
                return model;
            }

            statement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private static final String ALL_NOTIFS = "SELECT * FROM notifications;";

    @Override
    public List<Notification> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(ALL_NOTIFS);
            List<Notification> notifications = getNotifsFromResultSet(rs);
            statement.close();
            return notifications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Notification> getNotifsFromResultSet(ResultSet rs) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        while(rs.next()) {
            Notification notification = new Notification(rs.getInt(1),
                    rs.getInt(2),
                    rs.getBoolean(3),
                    rs.getString(4),
                    rs.getTimestamp(5));
            notifications.add(notification);
        }
        return notifications;
    }


    public static final String USER_NOTIFS_QUERY_BASE = "SELECT * FROM notifications WHERE user_id = ?";

    public static final String USER_NOTIFS = USER_NOTIFS_QUERY_BASE + ";";
    public static final String USER_NOTIFS_BY_END_DATE = USER_NOTIFS_QUERY_BASE + " AND date <= ?;";
    public static final String USER_NOTIFS_BY_START_DATE = USER_NOTIFS_QUERY_BASE + " AND date >= ?;";
    public static final String USER_NOTIFS_BY_TIME_INTERVAL = USER_NOTIFS_QUERY_BASE + " AND date BETWEEN ? AND ?;";
    public static final String USER_NOTIFS_BY_STATUS = USER_NOTIFS_QUERY_BASE + " AND is_read = ?;";
    public static final String USER_NOTIFS_BY_STATUS_AND_END_DATE = USER_NOTIFS_QUERY_BASE + " AND is_read = ? AND date <= ?;";
    public static final String USER_NOTIFS_BY_STATUS_AND_START_DATE = USER_NOTIFS_QUERY_BASE + " AND is_read = ? AND date >= ?;";
    public static final String USER_NOTIFS_BY_STATUS_AND_TIME_INTERVAL = USER_NOTIFS_QUERY_BASE + " AND is_read = ? AND date BETWEEN ? AND ?;";

    @Override
    public List<Notification> getUserNotifsByStatusAndDate(int userId, Boolean isRead, Timestamp startDate, Timestamp endDate) {
        try {
            PreparedStatement statement = null;

            if(isRead == null && startDate == null && endDate == null) {
                statement = connection.prepareStatement(USER_NOTIFS);
            }else if(isRead == null && startDate == null && endDate != null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_END_DATE);
            }else if(isRead == null && startDate != null && endDate == null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_START_DATE);
            }else if(isRead == null && startDate != null && endDate != null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_TIME_INTERVAL);
            }else if(isRead != null && startDate == null && endDate == null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_STATUS);
            }else if(isRead != null && startDate == null && endDate != null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_STATUS_AND_END_DATE);
            }else if(isRead != null && startDate != null && endDate == null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_STATUS_AND_START_DATE);
            }else if(isRead != null && startDate != null && endDate != null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_STATUS_AND_TIME_INTERVAL);
            }

            ResultSet rs = statement.executeQuery();

            List<Notification> notifications = getNotifsFromResultSet(rs);

            statement.close();
            return notifications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Notification> getUserNotifsByStatus(int userId, Boolean isRead) {
        return getUserNotifsByStatusAndDate(userId, isRead, null, null);
    }

    @Override
    public List<Notification> getUserNotifsByDate(int userId, Timestamp startDate, Timestamp endDate) {
        return getUserNotifsByStatusAndDate(userId, null, startDate, endDate);
    }
}
