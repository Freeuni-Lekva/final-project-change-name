package bandfinder.serviceimplementations;
import bandfinder.dao.TagDAO;
import bandfinder.models.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLTagDAO implements TagDAO {
    private final Connection connection;

    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/bandfinder?user=root&password=rootroot";

    public SQLTagDAO() throws ClassNotFoundException, SQLException {
        Class.forName(CLASS_NAME);
        connection = DriverManager.getConnection(URL);
    }

    private static final String ADD_TAG_TO_BAND_QUERY = "INSERT INTO band_tags (band_id, tag_id) " +
            "VALUES (?, ?);";

    @Override
    public boolean addTagToBand(int tagId, int bandId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_TAG_TO_BAND_QUERY);
            statement.setInt(1, bandId);
            statement.setInt(2, tagId);
            int rowsAffected = statement.executeUpdate();

            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String ADD_TAG_TO_USER_QUERY = "INSERT INTO user_tags (user_id, tag_id) " +
            "VALUES (?, ?);";
    @Override
    public boolean addTagToUser(int tagId, int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_TAG_TO_USER_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, tagId);
            int rowsAffected = statement.executeUpdate();

            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }    }

    private static final String REMOVE_TAG_FROM_BAND_QUERY = "DELETE FROM band_tags" +
            "WHERE band_id = ? AND tag_id = ?;";

    @Override
    public boolean removeTagFromBand(int tagId, int bandId) {
        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_TAG_FROM_BAND_QUERY);
            statement.setInt(1, bandId);
            statement.setInt(2, tagId);
            int rowsAffected = statement.executeUpdate();

            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String REMOVE_TAG_FROM_USER_QUERY = "DELETE FROM user_tags" +
            "WHERE user_id = ? AND tag_id = ?;";

    @Override
    public boolean removeTagFromUser(int tagId, int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_TAG_FROM_USER_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, tagId);
            int rowsAffected = statement.executeUpdate();

            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String BAND_HAS_TAG_QUERY = "SELECT id FROM band_tags " +
            "WHERE band_id = ? AND tag_id = ?;";

    @Override
    public boolean bandHasTag(int tagId, int bandId) {
        try {
            PreparedStatement statement = connection.prepareStatement(BAND_HAS_TAG_QUERY);
            statement.setInt(1, bandId);
            statement.setInt(2, tagId);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                statement.close();
                return true;
            }

            statement.close();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String USER_HAS_TAG_QUERY = "SELECT id FROM user_tags " +
            "WHERE user_id = ? AND tag_id = ?;";

    @Override
    public boolean userHasTag(int tagId, int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(USER_HAS_TAG_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, tagId);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                statement.close();
                return true;
            }

            statement.close();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_BAND_TAG_IDS_QUERY = "SELECT tag_id FROM band_tags WHERE band_id = ?;";

    @Override
    public List<Integer> getBandTagIDs(int bandId) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BAND_TAG_IDS_QUERY);
            statement.setInt(1, bandId);
            ResultSet rs = statement.executeQuery();

            List<Integer> tagIds = new ArrayList<>();
            while(rs.next()) tagIds.add(rs.getInt(1));

            statement.close();
            return tagIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_USER_TAG_IDS_QUERY = "SELECT tag_id FROM user_tags WHERE band_id = ?;";

    @Override
    public List<Integer> getUserTagIDs(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_USER_TAG_IDS_QUERY);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            List<Integer> tagIds = new ArrayList<>();
            while(rs.next()) tagIds.add(rs.getInt(1));

            statement.close();
            return tagIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String CREATE_QUERY = "INSERT INTO tags (name) VALUES (?);";

    @Override
    public Tag create(Tag model) {
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getName());
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

    private static final String UPDATE_QUERY = "UPDATE tags SET name = ? WHERE id = ?;";

    @Override
    public Tag update(Tag model) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(2, model.getId());
            statement.setString(1, model.getName());
            statement.executeUpdate();

            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DELETE_QUERY = "DELETE FROM tags WHERE id = ?;";

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_BY_ID_QUERY = "SELECT name FROM tags WHERE id = ?;";

    @Override
    public Tag getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                Tag model = new Tag(id, rs.getString(1));
                statement.close();
                return model;
            }

            statement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_ALL_QUERY = "SELECT * FROM tags;";

    @Override
    public List<Tag> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_QUERY);

            List<Tag> tags = new ArrayList<>();
            while(rs.next()) {
                Tag tag = new Tag(rs.getInt(1), rs.getString(2));
                tags.add(tag);
            }

            statement.close();
            return tags;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
