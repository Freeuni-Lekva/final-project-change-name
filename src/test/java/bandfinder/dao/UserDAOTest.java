package bandfinder.dao;

import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.User;
import bandfinder.serviceimplementations.HashMapUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserDAOTest {
    private UserDAO dao;

    @BeforeEach
    public void setUp() {
        this.dao = new HashMapUserDAO();
    }

    @Test
    public void testCreate() {
        Assertions.assertEquals(0, dao.getAll().size());
        User user1 = new User(1, "1@", "p1", "u1", "s1", "sn1");
        Assertions.assertEquals(user1, dao.create(user1));
        Assertions.assertEquals(1, dao.getAll().size());
        Assertions.assertEquals(user1, dao.getById(1));
        Assertions.assertEquals(user1, dao.getUserByEmail("1@"));
        User user2 = new User("2@", "p2", "u2", "s2", "sn2");
        Assertions.assertEquals(user2, dao.create(user2));
        Assertions.assertEquals(2, dao.getAll().size());
        User returnedUser2 = dao.getUserByEmail("2@");
        Assertions.assertEquals(returnedUser2, user2);
        Assertions.assertNotEquals(-1, returnedUser2.getId());
        Assertions.assertNull(dao.getUserByEmail("__somewrongemailaddress@goes.here__"));
    }

    @Test
    public void testDelete() {
        Assertions.assertEquals(0, dao.getAll().size());
        Assertions.assertFalse(dao.delete(-1234));
        User user1 = new User(1, "1@", "p1", "u1", "s1", "sn1");
        dao.create(user1);

        User user2 = new User("2@", "p2", "u2", "s2", "sn2");
        dao.create(user2);
        int user2Id = user2.getId();

        Assertions.assertTrue(dao.delete(user2Id));
        Assertions.assertEquals(1, dao.getAll().size());
        Assertions.assertEquals(user1, dao.getById(user1.getId()));
        Assertions.assertNull(dao.getById(user2Id));
        Assertions.assertNull(dao.getUserByEmail("2@"));
        Assertions.assertFalse(dao.delete(user2Id));
    }

    @Test
    public void testUpdate() {
        User user1 = new User(1, "1@", "p1", "u1", "s1", "sn1");
        dao.create(user1);
        User user2 = new User("2@", "p2", "u2", "s2", "sn2");
        dao.create(user2);

        String newEmail1 = "1@_";
        user1.setEmail(newEmail1);
        String newPasswordHash1 = "p1_";
        user1.setPasswordHash(newPasswordHash1);
        String newFirstName1 = "u1_";
        user1.setFirstName(newFirstName1);
        String newSurname1 = "s1_";
        user1.setSurname(newSurname1);
        String newStageName1 = "sn1_";
        user1.setStageName(newStageName1);
        Assertions.assertEquals(user1, dao.update(user1));
        User returnedUser1 = dao.getById(1);
        Assertions.assertEquals(user1, returnedUser1);
        Assertions.assertEquals(newEmail1, returnedUser1.getEmail());
        Assertions.assertEquals(newPasswordHash1, returnedUser1.getPasswordHash());
        Assertions.assertEquals(newFirstName1, returnedUser1.getFirstName());
        Assertions.assertEquals(newSurname1, returnedUser1.getSurname());
        Assertions.assertEquals(newStageName1, returnedUser1.getStageName());

        user2.setEmail("2@_");
        Assertions.assertEquals(user2, dao.update(user2));
        User returnedUser2 = dao.getById(user2.getId());
        Assertions.assertEquals(user2, returnedUser2);
        Assertions.assertEquals("2@_", returnedUser2.getEmail());
        Assertions.assertEquals("p2", returnedUser2.getPasswordHash());
        Assertions.assertEquals("u2", returnedUser2.getFirstName());
        Assertions.assertEquals("s2", returnedUser2.getSurname());
        Assertions.assertEquals("sn2", returnedUser2.getStageName());
    }

    @Test
    public void testSearch() {
        User user1 = new User(1, "t@t.t", "tt.tt", "John", "Doe", "");
        dao.create(user1);
        User user2 = new User(2, "t@t.t", "tt.tt", "rAnDoM", "cAsE", "qUeRy");
        dao.create(user2);

        // Full name search
        List<User> list1 = dao.searchUsers("John Doe");
        Assertions.assertEquals(1, list1.size());
        Assertions.assertEquals(user1, list1.get(0));

        // No result search
        List<User> emptyList = dao.searchUsers("__wrong__query__");
        Assertions.assertTrue(emptyList.isEmpty());

        // Mixed case search
        List<User> list2 = dao.searchUsers("random case query");
        Assertions.assertEquals(1, list2.size());
        Assertions.assertEquals(user2, list2.get(0));
    }
}