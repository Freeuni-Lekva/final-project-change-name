package bandfinder.dao;

import bandfinder.models.Post;

import java.sql.Timestamp;
import java.util.List;

public interface PostDAO extends DAO<Post> {
    String ATTRIBUTE = "PostDAO";

    List<Post> getUserFeedPosts(int userId, int postsToSkip, int postsToFetch);

    List<Post> getUserPosts(int userId, int postsToSkip, int postsToFetch);

    List<Post> getBandPosts(int bandId, int postsToSkip, int postsToFetch);
}
