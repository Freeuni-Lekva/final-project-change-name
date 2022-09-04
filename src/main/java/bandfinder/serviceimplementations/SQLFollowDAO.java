package bandfinder.serviceimplementations;

import bandfinder.dao.FollowDAO;
import bandfinder.models.Follow;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLFollowDAO implements FollowDAO {

    private final Connection connection;

    public SQLFollowDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/bandfinder?user=root&password=rootroot");
    }

    private static final String CREATE_QUERY = "INSERT INTO follows (follower, followee) VALUES (?, ?);";

    @Override
    public Follow create(Follow follow) {
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, follow.getFollowerID());
            statement.setInt(2, follow.getFolloweeID());
            statement.executeUpdate();

            ResultSet idResultSet = statement.getGeneratedKeys();
            idResultSet.next();
            int newId = idResultSet.getInt(1);
            follow.setId(newId);

            statement.close();
            return follow;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE_QUERY = "UPDATE follows SET follower = ?, " +
                                        "followee = ?, " +
                                        "WHERE id = ?;";

    @Override
    public Follow update(Follow follow) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, follow.getFollowerID());
            statement.setInt(2, follow.getFolloweeID());
            statement.setInt(3, follow.getId());
            statement.executeUpdate();
            statement.close();
            return follow;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DELETE_QUERY = "DELETE FROM follows WHERE id = ?;";

    @Override
    public boolean delete(int id) {
        try{
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_BY_ID_QUERY = "SELECT * FROM follows WHERE id = ?;";

    @Override
    public Follow getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();
                return null;
            }
            Follow follow = new Follow( id,
                    resultSet.getInt(2),
                    resultSet.getInt(3));
            statement.close();
            return follow;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_ALL_QUERY = "SELECT * FROM follows";

    @Override
    public List<Follow> getAll() {
        List<Follow> follows = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                int follower = resultSet.getInt(2);
                int followee = resultSet.getInt(3);
                Follow follow = new Follow(id, follower, followee);
                follows.add(follow);
            }
            statement.close();
            return follows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_FOLLOW_QUERY = "SELECT * FROM follows WHERE follower=? AND followee=?;";

    @Override
    public boolean followExists(Follow follow) {
        try{
            PreparedStatement statement = connection.prepareStatement(GET_FOLLOW_QUERY);
            statement.setInt(1, follow.getFollowerID());
            statement.setInt(2, follow.getFolloweeID());
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();

            if(resultSet.isBeforeFirst()){
                statement.close();
                return true;
            }

            statement.close();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DELETE_WITH_FOLLOWER_AND_FOLLOWEE = "DELETE FROM follows WHERE follower=? AND followee=?;";

    @Override
    public boolean delete(Follow follow) {
        try{
            PreparedStatement statement = connection.prepareStatement(DELETE_WITH_FOLLOWER_AND_FOLLOWEE);
            statement.setInt(1, follow.getFollowerID());
            statement.setInt(2, follow.getFolloweeID());
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_FOLLOWER_COUNT_QUERY = "SELECT COUNT(DISTINCT follower) FROM follows WHERE followee=?;";

    @Override
    public int getFollowerCount(int userId) {
        try{
            PreparedStatement statement = connection.prepareStatement(GET_FOLLOWER_COUNT_QUERY);
            statement.setInt(1, userId);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();

            resultSet.next();
            int count = resultSet.getInt(1);

            statement.close();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_FOLLOWEE_COUNT_QUERY = "SELECT COUNT(DISTINCT followee) FROM follows WHERE follower=?;";

    @Override
    public int getFolloweeCount(int userId) {
        try{
            PreparedStatement statement = connection.prepareStatement(GET_FOLLOWEE_COUNT_QUERY);
            statement.setInt(1, userId);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();

            resultSet.next();
            int count = resultSet.getInt(1);

            statement.close();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
