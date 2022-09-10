package bandfinder.serviceimplementations;

import bandfinder.dao.CommentDAO;
import bandfinder.models.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCommentDAO implements CommentDAO{

    private Connection connection;

    public SQLCommentDAO() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/bandfinder?user=root&password=rootroot");
    }

    private static final String QUERY_PRIORITY =
            "SELECT * FROM comments WHERE post_id = ? " +
                    "ORDER BY CASE " +
                        "WHEN user_id = ? THEN 1 " +
                        "ELSE 2 END asc, ";
    @Override
    public List<Comment> getCommentBatchPriorityLikes(int postId, int userId, int limit, int offset) {
        String QUERY_PRIORITY_LIKES = QUERY_PRIORITY + "likes desc " + "LIMIT " + limit + " OFFSET " + offset + ";";
        return getBatch(QUERY_PRIORITY_LIKES, postId, userId);
    }


    @Override
    public List<Comment> getCommentBatchPriorityDate(int postId, int userId, int limit, int offset) {
        String QUERY_PRIORITY_DATE = QUERY_PRIORITY + "date desc " + "LIMIT " + limit + " OFFSET " + offset + ";";
        return getBatch(QUERY_PRIORITY_DATE, postId, userId);
    }

    private List<Comment> getBatch(String sql, int postId, int userId){
        List<Comment> l = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, postId);
            statement.setInt(2, userId);

            statement.executeQuery();
            ResultSet rs = statement.getResultSet();

            while(rs.next()){
                Comment c = new Comment(rs.getInt(1),
                        rs.getTimestamp(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6));

                l.add(c);
            }

            statement.close();
            return l;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String CREATE_QUERY = "INSERT INTO comments (date, content, user_id, post_id, likes) VALUES (?, ?, ?, ?, ?);";

    @Override
    public Comment create(Comment model) {
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, new Timestamp(model.getDate().getTime()));
            statement.setString(2, model.getText());
            statement.setInt(3, model.getAuthorId());
            statement.setInt(4, model.getPostId());
            statement.setInt(5, model.getLikes());

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

    private static final String UPDATE_QUERY = "UPDATE comments " +
                                                "SET post_id = ?, user_id = ?, date = ?, content = ?, likes = ? " +
                                                "WHERE id = ?;";
    @Override
    public Comment update(Comment model) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, model.getPostId());
            statement.setInt(2, model.getAuthorId());
            statement.setTimestamp(3, new Timestamp(model.getDate().getTime()));
            statement.setString(4, model.getText());
            statement.setInt(5, model.getId());

            statement.executeUpdate();
            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DELETE_QUERY = "DELETE FROM comments WHERE id = ?;";
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_BY_ID_QUERY = "SELECT * FROM comments WHERE id = ?;";
    @Override
    public Comment getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(!rs.next()){
                statement.close();
                return null;
            }

            Comment comment = new Comment(id,
                    rs.getTimestamp(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getInt(5),
                    rs.getInt(6));
            statement.close();
            return comment;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> cmtList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments;");
            statement.executeQuery();
            ResultSet rs = statement.getResultSet();

            while(rs.next()){
                Comment c = new Comment(rs.getInt(1),
                                    rs.getTimestamp(2),
                                    rs.getString(3),
                                    rs.getInt(4),
                                    rs.getInt(5),
                                    rs.getInt(6));
                cmtList.add(c);
            }
            statement.close();
            return cmtList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
