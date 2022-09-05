package bandfinder.serviceimplementations;
import bandfinder.dao.TagDAO;
import bandfinder.models.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLTagDAO implements TagDAO {
    private final Connection connection;

    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/bandfinder?user=root&password=R00tR**t";

    public SQLTagDAO() throws ClassNotFoundException, SQLException {
        Class.forName(CLASS_NAME);
        connection = DriverManager.getConnection(URL);
    }

    private boolean addTagToObject(int tagId, int objectId, String query){
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, objectId);
            statement.setInt(2, tagId);
            int rowsAffected = statement.executeUpdate();

            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addTagToBand(int tagId, int bandId) {
        return addTagToObject(tagId,bandId,"INSERT INTO band_tags (band_id, tag_id) " + "VALUES (?, ?);");
    }

    @Override
    public boolean addTagToUser(int tagId, int userId) {
        return addTagToObject(tagId,userId,"INSERT INTO user_tags (user_id, tag_id) " +  "VALUES (?, ?);");
    }

    private boolean removeTagFromObject(int tagId, int objectId,String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, objectId);
            statement.setInt(2, tagId);
            int rowsAffected = statement.executeUpdate();

            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean removeTagFromBand(int tagId, int bandId) {
        return removeTagFromObject(tagId,bandId,"DELETE FROM band_tags " + "WHERE band_id = ? AND tag_id = ?;");
    }

    @Override
    public boolean removeTagFromUser(int tagId, int userId) {
        return removeTagFromObject(tagId,userId,"DELETE FROM user_tags " + "WHERE user_id = ? AND tag_id = ?;");
    }

    private boolean objectHasTag(int tagId, int objectId, String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, objectId);
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

    @Override
    public boolean bandHasTag(int tagId, int bandId) {
        return objectHasTag(tagId,bandId,"SELECT id FROM band_tags " + "WHERE band_id = ? AND tag_id = ?;");
    }

    @Override
    public boolean userHasTag(int tagId, int userId) {
        return objectHasTag(tagId,userId,"SELECT id FROM user_tags " + "WHERE user_id = ? AND tag_id = ?;");
    }

    public List<Integer> getObjectTagIDs(int objectId,String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, objectId);
            ResultSet rs = statement.executeQuery();

            List<Integer> tagIds = new ArrayList<>();
            while(rs.next()) tagIds.add(rs.getInt(1));

            statement.close();
            return tagIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Integer> getBandTagIDs(int bandId) {
        return  getObjectTagIDs(bandId,"SELECT tag_id FROM band_tags WHERE band_id = ?;");
    }

    @Override
    public List<Integer> getUserTagIDs(int userId) {
        return  getObjectTagIDs(userId,"SELECT tag_id FROM user_tags WHERE user_id = ?;");
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
