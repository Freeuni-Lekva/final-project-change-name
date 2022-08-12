package bandfinder.dao;

import bandfinder.models.User;
import java.util.List;

public interface UserDAO extends DAO<User>{
    String ATTRIBUTE_NAME = "UserDAO";
    User getUserByEmail(String email);
    List<User> searchUsers(String query);
}
