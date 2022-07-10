package bandfinder.dao;

import bandfinder.models.User;

public interface UserDAO extends DAO<User>{
    String ATTRIBUTE_NAME = "UserDAO";

    // Debatable
    User getUserByEmail(String mail);
}
