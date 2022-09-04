package bandfinder.serviceimplementations;

import bandfinder.dao.PostDAO;
import bandfinder.models.Post;

import java.sql.Timestamp;
import java.util.List;

public class SQLPostDAO implements PostDAO {

    @Override
    public Post create(Post model) {
        return null;
    }

    @Override
    public Post update(Post model) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Post getById(int id) {
        return null;
    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public List<Post> getUserFeedPosts(int userId) {
        return null;
    }

    @Override
    public List<Post> getUserPosts(int userId) {
        return null;
    }

    @Override
    public List<Post> getBandPosts(int bandId) {
        return null;
    }

    @Override
    public List<Post> getBandPostsByMember(int memberId) {
        return null;
    }
}
