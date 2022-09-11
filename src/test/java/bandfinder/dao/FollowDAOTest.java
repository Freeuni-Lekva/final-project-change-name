package bandfinder.dao;

import bandfinder.models.Follow;
import bandfinder.serviceimplementations.HashMapFollowDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FollowDAOTest {
    private FollowDAO dao;

    @BeforeEach
    public void setUp() {
        this.dao = new HashMapFollowDAO();
    }

    @Test
    public void testSimple() {
        List<Follow> follows = List.of(new Follow(1, 1, 2),
            new Follow( 2, 3));
        for(Follow f : follows) {
            Assertions.assertEquals(f, dao.create(f));
        }

        for(Follow f : follows) {
            Assertions.assertTrue(dao.followExists(f));
        }

        List<Follow> daoFollows = dao.getAll();
        Assertions.assertEquals(2, daoFollows.size());

        Assertions.assertEquals(follows.get(1), dao.getById(2));
        Assertions.assertTrue(dao.delete(2));
        Assertions.assertFalse(dao.delete(2));
        Assertions.assertFalse(dao.followExists(follows.get(1)));

        Assertions.assertTrue(dao.delete(new Follow(1, 2)));
        Assertions.assertEquals(0, dao.getAll().size());
    }

    @Test
    public void testCounts() {
        List<Follow> follows = List.of(new Follow(1, 1, 2),
                new Follow( 2, 3));
        for(Follow f : follows) {
            dao.create(f);
        }

        Assertions.assertEquals(1, dao.getFolloweeCount(1));
        Assertions.assertEquals(0, dao.getFollowerCount(1));
        Assertions.assertEquals(1, dao.getFolloweeCount(2));
        Assertions.assertEquals(1, dao.getFollowerCount(2));
        Assertions.assertEquals(0, dao.getFolloweeCount(3));
        Assertions.assertEquals(1, dao.getFollowerCount(3));

        Follow updated = new Follow(2, 2, 4);
        Assertions.assertEquals(updated, dao.update(updated));
        Assertions.assertEquals(0, dao.getFollowerCount(3));
    }
}
