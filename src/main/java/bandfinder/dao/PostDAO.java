package bandfinder.dao;

import bandfinder.models.Post;

import java.util.List;

public interface PostDAO extends DAO<Post> {
    String ATTRIBUTE = "PostDAO";

    List<Post> feed(int userId, int lastPostFetchedId, int numPosts);

    List<Post> newestFeed(int userId, int numPosts);

    List<Post> nextSetFeed(int userId, int numPosts);

    List<Post> userPosts(int userId, int lastPostFetchedId, int numPosts);

    List<Post> newestUserPosts(int userId, int numPosts);

    List<Post> nextSetUserPosts(int userId, int numPosts);

    List<Post> bandPosts(int bandId, int lastPostFetchedId, int numPosts);

    List<Post> newestBandPosts(int bandId, int numPosts);

    List<Post> nextSetBandPosts(int bandId, int numPosts);
}
