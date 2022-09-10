package bandfinder.dao;

import bandfinder.models.Request;

import java.sql.SQLException;

public interface RequestDAO extends DAO<Request> {
    String ATTRIBUTE = "RequestDAO";

    int getId(int userId,int bandId) throws SQLException;
}
