package bandfinder.dao;

import bandfinder.models.Post;

import java.sql.Timestamp;
import java.util.List;

public interface PostDAO extends DAO<Post> {
    String ATTRIBUTE = "PostDAO";

    public Timestamp timeNow();

    List<Post> getUserFeedPosts(int userId, Timestamp olderThan, int numPosts);

    List<Post> getUserPosts(int userId, Timestamp olderThan, int numPosts);

    List<Post> getBandPosts(int bandId, Timestamp olderThan, int numPosts);
}
