package bandfinder.dao;

import bandfinder.models.Post;

import java.sql.Timestamp;
import java.util.List;

public interface PostDAO extends DAO<Post> {
    String ATTRIBUTE = "PostDAO";

    List<Post> getUser(int userId);

    List<Post> getUserPostsByDate(int userId, Timestamp startDate, Timestamp endDate);
}
