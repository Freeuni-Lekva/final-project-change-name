package bandfinder.dao;

import bandfinder.serviceimplementations.SQLUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLUserDAOTest extends UserDAOTest {
    @Override
    @BeforeEach
    public void setUp() {
        Assertions.assertDoesNotThrow(() -> {
            this.dao = SQLUserDAO.getInstance();
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/bandfinder?user=root&password=pass");
            connection.createStatement().executeUpdate("TRUNCATE users");
        });

    }
}
