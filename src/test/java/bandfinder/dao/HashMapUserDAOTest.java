package bandfinder.dao;

import bandfinder.serviceimplementations.HashMapUserDAO;
import org.junit.jupiter.api.BeforeEach;

public class HashMapUserDAOTest extends UserDAOTest {
    @Override
    @BeforeEach
    public void setUp() {
        this.dao = new HashMapUserDAO();
    }
}