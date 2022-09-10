package bandfinder.dao;

import bandfinder.models.Invitation;
import bandfinder.models.Request;

import java.sql.SQLException;

public interface InvitationDAO extends DAO<Invitation> {
    String ATTRIBUTE = "InvitationDAO";

    int getId(int userId,int bandId) throws SQLException;
}
