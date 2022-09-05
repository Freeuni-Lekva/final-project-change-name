package bandfinder.dao;

import bandfinder.models.Post;

import java.sql.Timestamp;
import java.util.List;

public interface PostDAO extends DAO<Post> {
    String ATTRIBUTE = "PostDAO";

    Integer getLastPostId();

    List<Post> getUserFeedPosts(int userId, int lastPostFetchedId, int numPosts);

    List<Post> getUserPosts(int userId, int lastPostFetchedId, int numPosts);

    List<Post> getBandPosts(int bandId, int lastPostFetchedId, int numPosts);
}
