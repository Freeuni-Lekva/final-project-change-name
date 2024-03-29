package bandfinder.dao;

import bandfinder.models.Follow;

public interface FollowDAO extends DAO<Follow>{

    boolean followExists(Follow follow);

    boolean delete(Follow follow);

    int getFollowerCount(int userId);

    int getFolloweeCount(int userId);
}
