package bandfinder.infrastructure;

import com.auth0.jwt.algorithms.Algorithm;

public interface Constants {
    int NO_ID = -1;

    String SECRET_KEY = "mm8rJEHNG-Kwdnqt";
    Algorithm ENCRYPT_ALGO = Algorithm.HMAC256(SECRET_KEY);

    // DB
    String JDBC_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost/bandfinder?user=root&password=R00tR**t";

    // Attribute names
    String LOGIN_TOKEN_ATTRIBUTE_NAME = "login-token";

    // URLs

    String URL_JOIN_BAND_REQUEST = "/joinBandRequest";
    String URL_BAND_PAGE = "/bandPage";
    String URL_RESPONSE_TO_JOIN_BAND_REQUEST = "/responseToJoinBandRequest";
    String URL_INVITE_MEMBER_TO_BAND_REQUEST = "/InviteMemberToBandServlet";
    String URL_RESPONSE_TO_INVITATION = "/ResponseToInvitationServlet";
}
