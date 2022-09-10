package bandfinder.infrastructure;

import com.auth0.jwt.algorithms.Algorithm;

public interface Constants {
    int NO_ID = -1;

    String SECRET_KEY = "mm8rJEHNG-Kwdnqt";
    Algorithm ENCRYPT_ALGO = Algorithm.HMAC256(SECRET_KEY);

    // DB
    String JDBC_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost/bandfinder?user=root&password=rootroot";

    // Attribute names
    String LOGIN_TOKEN_ATTRIBUTE_NAME = "login-token";

    int POSTS_TO_FETCH_MAX_NUM = 5;
}
