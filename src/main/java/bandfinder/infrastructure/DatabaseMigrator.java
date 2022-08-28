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
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost?user=root&password=R00tR**t";

    public static void migrate() {
        try {
            Class.forName(CLASS_NAME);
            Connection connection = DriverManager.getConnection(URL);

            ScriptRunner sr = new ScriptRunner(connection);
            String path = Paths.get(System.getProperty("user.dir"), "src", "main", "db.sql").toString();
            Reader reader = new BufferedReader(new FileReader(path));
            sr.runScript(reader);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
