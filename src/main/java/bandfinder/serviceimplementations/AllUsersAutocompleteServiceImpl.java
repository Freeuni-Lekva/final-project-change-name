package bandfinder.serviceimplementations;

import bandfinder.dao.TagDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.ServiceValueSetter;
import bandfinder.models.Tag;
import bandfinder.models.User;
import bandfinder.services.TagAutoComplete;
import bandfinder.services.UserAutoComplete;

import java.util.List;

public class AllUsersAutocompleteServiceImpl implements UserAutoComplete {
    private List<User> users;

    @AutoInjectable
    private UserDAO userDAO;

    public AllUsersAutocompleteServiceImpl() { //loads users in website
        ServiceValueSetter.setAutoInjectableFieldValues(this);
        users = userDAO.getAll();
    }

    @Override
    public List<User> get() {
        return users;
    }
}
