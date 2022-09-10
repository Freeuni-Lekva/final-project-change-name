package bandfinder.infrastructure;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMigrator {
    public static void migrate() {
        try {
            Class.forName(Constants.JDBC_CLASS_NAME);
            Connection connection = DriverManager.getConnection(Constants.DB_URL);

            ScriptRunner sr = new ScriptRunner(connection);
            String path = Paths.get(System.getProperty("user.dir"), "src", "main", "db.sql").toString();
            Reader reader = new BufferedReader(new FileReader(path));
            sr.runScript(reader);
        } catch (SQLException | ClassNotFoundException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
