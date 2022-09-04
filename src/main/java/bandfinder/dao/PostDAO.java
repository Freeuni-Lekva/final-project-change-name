package bandfinder.dao;

import bandfinder.models.Post;

import java.sql.Timestamp;
import java.util.List;

public interface PostDAO extends DAO<Post> {
    String ATTRIBUTE = "PostDAO";

    List<Post> getUserFeedPosts(int userId);

    List<Post> getUserPosts(int userId);

    List<Post> getBandPosts(int bandId);

    List<Post> getBandPostsByMember(int memberId);
}
