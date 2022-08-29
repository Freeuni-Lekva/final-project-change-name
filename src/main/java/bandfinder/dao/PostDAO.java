package bandfinder.dao;

import bandfinder.models.Post;
import bandfinder.models.User;

import java.util.List;

public interface PostDAO extends DAO<Post>{
    String ATTRIBUTE_NAME = "PostDAO";
    Integer getPostOwnerId(int postId);
    List<Integer> getAllPostIdsByUser(int userId);
}
