package bandfinder.serviceimplementations;

import bandfinder.dao.NotificationDAO;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLNotificationDAO implements NotificationDAO {

    private final Connection connection;

    public SQLNotificationDAO() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_CLASS_NAME);
        connection = DriverManager.getConnection(Constants.DB_URL);
    }

    public static final String CREATE_NOTIF = "INSERT INTO notifications (user_id, band_id, is_read, message, date) " +
                                              "VALUES (?, ?, ?, ?, ?);";

    @Override
    public Notification create(Notification model) {
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_NOTIF, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setObject(1, model.getUserId(), Types.INTEGER);
            statement.setObject(2, model.getBandId(), Types.INTEGER);
            statement.setBoolean(3, model.isRead());
            statement.setString(4, model.getMessage());
            statement.setTimestamp(5, model.getDate());
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
                                              "SET user_id = ?, band_id = ?, is_read = ?, message = ?, date = ? " +
                                              "WHERE id = ?;";

    @Override
    public Notification update(Notification model) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_NOTIF);
            statement.setObject(1, model.getUserId(), Types.INTEGER);
            statement.setObject(2, model.getBandId(), Types.INTEGER);
            statement.setBoolean(3, model.isRead());
            statement.setString(4, model.getMessage());
            statement.setTimestamp(5, model.getDate());
            statement.setInt(6, model.getId());
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
                Integer userId = rs.getInt(1) == 0 ? null : rs.getInt(1);
                Integer bandId = rs.getInt(2) == 0 ? null : rs.getInt(2);
                boolean isRead = rs.getBoolean(3);
                String message = rs.getString(4);
                Timestamp date = rs.getTimestamp(5);
                statement.close();
                return new Notification(id, userId, bandId, isRead, message, date);
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
            int id = rs.getInt(1);
            Integer userId = rs.getInt(2) == 0 ? null : rs.getInt(2);
            Integer bandId = rs.getInt(3) == 0 ? null : rs.getInt(3);
            boolean isRead = rs.getBoolean(4);
            String message = rs.getString(5);
            Timestamp date = rs.getTimestamp(6);
            notifications.add(new Notification(id, userId, bandId, isRead, message, date));
        }
        return notifications;
    }


    public static final String USER_NOTIFS_QUERY_BASE = "SELECT * FROM notifications WHERE user_id = ? AND band_id = ? ";
    public static final String USER_NOTIFS = USER_NOTIFS_QUERY_BASE + ";";
    public static final String USER_NOTIFS_BY_END_DATE = USER_NOTIFS_QUERY_BASE + "AND date <= ?;";
    public static final String USER_NOTIFS_BY_START_DATE = USER_NOTIFS_QUERY_BASE + "AND date >= ?;";
    public static final String USER_NOTIFS_BY_TIME_INTERVAL = USER_NOTIFS_QUERY_BASE + "AND date BETWEEN ? AND ?;";
    public static final String USER_NOTIFS_BY_STATUS = USER_NOTIFS_QUERY_BASE + "AND is_read = ?;";
    public static final String USER_NOTIFS_BY_STATUS_AND_END_DATE = USER_NOTIFS_QUERY_BASE + "AND is_read = ? AND date <= ?;";
    public static final String USER_NOTIFS_BY_STATUS_AND_START_DATE = USER_NOTIFS_QUERY_BASE + "AND is_read = ? AND date >= ?;";
    public static final String USER_NOTIFS_BY_STATUS_AND_TIME_INTERVAL = USER_NOTIFS_QUERY_BASE + "AND is_read = ? AND date BETWEEN ? AND ?;";

    @Override
    public List<Notification> getNotifsByStatusAndDate(Integer userId, Integer bandId, Boolean isRead,
                                                       Timestamp startDate, Timestamp endDate) {
        try {
            PreparedStatement statement = null;

            if(isRead == null && startDate == null && endDate == null) {
                statement = connection.prepareStatement(USER_NOTIFS);

            }else if(isRead == null && startDate == null && endDate != null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_END_DATE);
                statement.setTimestamp(3, endDate);

            }else if(isRead == null && startDate != null && endDate == null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_START_DATE);
                statement.setTimestamp(3, startDate);

            }else if(isRead == null && startDate != null && endDate != null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_TIME_INTERVAL);
                statement.setTimestamp(3, startDate);
                statement.setTimestamp(4, endDate);

            }else if(isRead != null && startDate == null && endDate == null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_STATUS);
                statement.setBoolean(3, isRead);

            }else if(isRead != null && startDate == null && endDate != null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_STATUS_AND_END_DATE);
                statement.setBoolean(3, isRead);
                statement.setTimestamp(4, endDate);

            }else if(isRead != null && startDate != null && endDate == null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_STATUS_AND_START_DATE);
                statement.setBoolean(3, isRead);
                statement.setTimestamp(4, startDate);

            }else if(isRead != null && startDate != null && endDate != null) {
                statement = connection.prepareStatement(USER_NOTIFS_BY_STATUS_AND_TIME_INTERVAL);
                statement.setBoolean(3, isRead);
                statement.setTimestamp(4, startDate);
                statement.setTimestamp(5, endDate);
            }

            statement.setObject(1, userId, Types.INTEGER);
            statement.setObject(2, bandId, Types.INTEGER);

            ResultSet rs = statement.executeQuery();

            List<Notification> notifications = getNotifsFromResultSet(rs);

            statement.close();
            return notifications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Notification> getNotifsByStatus(Integer userId, Integer bandId, Boolean isRead) {
        return getNotifsByStatusAndDate(userId, bandId, isRead, null, null);
    }

    @Override
    public List<Notification> getNotifsByDate(Integer userId, Integer bandId, Timestamp startDate, Timestamp endDate) {
        return getNotifsByStatusAndDate(userId, bandId,null, startDate, endDate);
    }
}
