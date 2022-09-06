package bandfinder.serviceimplementations;

import bandfinder.dao.PostDAO;
import bandfinder.models.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;

public class SQLPostDAO implements PostDAO {

    private final Connection connection;

    private Integer lastFeedPostFetchedId;
    private Integer lastUserPostFetchedId;
    private Integer lastBandPostFetchedId;

    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/bandfinder?user=root&password=rootroot";

    public SQLPostDAO() throws ClassNotFoundException, SQLException {
        Class.forName(CLASS_NAME);
        connection = DriverManager.getConnection(URL);

        lastFeedPostFetchedId = null;
        lastUserPostFetchedId = null;
        lastBandPostFetchedId = null;
    }

    private static final String CREATE = "INSERT INTO posts (author_user, author_band, text, date) " +
            "VALUES (?, ?, ?, ?);";

    @Override
    public Post create(Post model) {
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setObject(1, model.getAuthorUser(), Types.INTEGER);
            statement.setObject(2, model.getAuthorBand(), Types.INTEGER);
            statement.setString(3, model.getText());
            statement.setTimestamp(4, model.getDate());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            model.setId(rs.getInt(1));
            statement.close();

            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE = "UPDATE posts SET author_user=?, author_band=?, text=?, date=? WHERE id=?;";

    @Override
    public Post update(Post model) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setObject(1, model.getAuthorUser(), Types.INTEGER);
            statement.setObject(2, model.getAuthorBand(), Types.INTEGER);
            statement.setString(3, model.getText());
            statement.setTimestamp(4, model.getDate());
            statement.setInt(5, model.getId());
            statement.executeUpdate();
            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DELETE = "DELETE FROM posts WHERE id=?;";

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_BY_ID = "SELECT * FROM posts WHERE id=?;";

    @Override
    public Post getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Integer authorUser = rs.getInt(2) == 0 ? null : rs.getInt(2);
                Integer authorBand = rs.getInt(3) == 0 ? null : rs.getInt(3);
                String text = rs.getString(4);
                Timestamp date = rs.getTimestamp(5);
                Post post = new Post(id, authorUser, authorBand, text, date);
                statement.close();
                return post;
            }
            statement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Post> createPostsFromResultSet(ResultSet rs) throws SQLException {
        List<Post> posts = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            Integer authorUser = rs.getInt(2) == 0 ? null : rs.getInt(2);
            Integer authorBand = rs.getInt(3) == 0 ? null : rs.getInt(3);
            String text = rs.getString(4);
            Timestamp date = rs.getTimestamp(5);
            posts.add(new Post(id, authorUser, authorBand, text, date));
        }
        return posts;
    }

    private static final String GET_ALL = "SELECT * FROM posts;";

    @Override
    public List<Post> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL);
            List<Post> posts = createPostsFromResultSet(rs);
            statement.close();
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FEED_POSTS = """
                        SELECT * FROM posts p
                        LEFT JOIN follows uf ON p.author_user = uf.followee
                        LEFT JOIN band_follows bf ON p.author_band = bf.followee_band
                        WHERE ((uf.follower = ? AND p.author_band is null) OR (bf.follower_user = ?)) AND p.id < ?
                        ORDER BY p.id DESC LIMIT ?;""";

    @Override
    public List<Post> feed(int userId, int lastPostFetchedId, int numPosts) {
        try {
            PreparedStatement statement = connection.prepareStatement(FEED_POSTS);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            statement.setInt(3, lastPostFetchedId);
            statement.setInt(4, numPosts);
            ResultSet rs = statement.executeQuery();
            List<Post> feedPosts = createPostsFromResultSet(rs);
            lastFeedPostFetchedId = feedPosts.get(feedPosts.size() - 1).getId();
            statement.close();
            return feedPosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> newestFeed(int userId, int numPosts) {
        return feed(userId, MAX_VALUE, numPosts);
    }

    @Override
    public List<Post> nextSetFeed(int userId, int numPosts) {
        if(lastFeedPostFetchedId == null) return newestFeed(userId, numPosts);
        return feed(userId, lastFeedPostFetchedId, numPosts);
    }

    private static final String USER_POSTS =
                    "SELECT * FROM posts WHERE author_user=? AND author_band IS NULL " +
                    "AND id < ? ORDER BY id DESC LIMIT ?;";

    @Override
    public List<Post> userPosts(int userId, int lastPostFetchedId, int numPosts) {
        try {
            PreparedStatement statement = connection.prepareStatement(USER_POSTS);
            statement.setInt(1, userId);
            statement.setInt(2, lastPostFetchedId);
            statement.setInt(3, numPosts);
            ResultSet rs = statement.executeQuery();
            List<Post> userPosts = createPostsFromResultSet(rs);
            lastUserPostFetchedId = userPosts.get(userPosts.size() - 1).getId();
            statement.close();
            return userPosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> newestUserPosts(int userId, int numPosts) {
        return userPosts(userId, MAX_VALUE, numPosts);
    }

    @Override
    public List<Post> nextSetUserPosts(int userId, int numPosts) {
        if(lastUserPostFetchedId == null) return newestUserPosts(userId, numPosts);
        return feed(userId, lastUserPostFetchedId, numPosts);
    }

    private static final String BAND_POSTS = "SELECT * FROM posts WHERE author_band=? AND id < ? ORDER BY id LIMIT ?;";

    @Override
    public List<Post> bandPosts(int bandId, int lastPostFetchedId, int numPosts) {
        try {
            PreparedStatement statement = connection.prepareStatement(BAND_POSTS);
            statement.setInt(1, bandId);
            statement.setInt(2, lastPostFetchedId);
            statement.setInt(3, numPosts);
            ResultSet rs = statement.executeQuery();
            List<Post> bandPosts = createPostsFromResultSet(rs);
            lastBandPostFetchedId = bandPosts.get(bandPosts.size() - 1).getId();
            statement.close();
            return bandPosts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> newestBandPosts(int bandId, int numPosts) {
        return bandPosts(bandId, MAX_VALUE, numPosts);
    }

    @Override
    public List<Post> nextSetBandPosts(int bandId, int numPosts) {
        if(lastBandPostFetchedId == null) return newestBandPosts(bandId, numPosts);
        return bandPosts(bandId, lastBandPostFetchedId, numPosts);
    }
}
