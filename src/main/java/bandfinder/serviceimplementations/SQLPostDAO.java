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
    public List<Post> getUserPosts(int userId) {
        return null;
    }

    @Override
    public List<Post> getUserPostsByDate(int userId, Timestamp startDate, Timestamp endDate) {
        return null;
    }
}
