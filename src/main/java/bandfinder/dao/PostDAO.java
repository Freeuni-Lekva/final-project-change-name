package bandfinder.dao;

import bandfinder.models.Post;

import java.util.List;

public interface PostDAO extends DAO<Post> {
    String ATTRIBUTE = "PostDAO";

    List<Post> getUserFeedPostsBeforeId(int userId, int lastPostFetchedId, int numPosts);

    List<Post> getUserFeedNewestPosts(int userId, int numPosts);

    List<Post> getUserPostsBeforeId(int userId, int lastPostFetchedId, int numPosts);

    List<Post> getUserNewestPosts(int userId, int numPosts);

    List<Post> getBandPostsBeforeId(int bandId, int lastPostFetchedId, int numPosts);

    List<Post> getBandNewestPosts(int bandId, int numPosts);

}
