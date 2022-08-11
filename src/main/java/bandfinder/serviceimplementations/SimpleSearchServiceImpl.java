package bandfinder.serviceimplementations;

import bandfinder.dao.BandDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.infrastructure.ServiceValueSetter;
import bandfinder.models.Band;
import bandfinder.models.User;
import bandfinder.services.SearchService;

import java.util.List;

public class SimpleSearchServiceImpl implements SearchService {
    @AutoInjectable
    private UserDAO userDAO;
    @AutoInjectable
    private BandDAO bandDAO;

    public SimpleSearchServiceImpl() {
        ServiceValueSetter.setAutoInjectableFieldValues(this);
    }

    private boolean searchPredicate(String query, String toCompare) {
        return toCompare.contains(query);
    }
    @Override
    public List<Band> searchBands(String query) {
        return bandDAO.getAll().stream().filter((band) -> searchPredicate(query, band.getName())).toList();
    }

    @Override
    public List<User> searchUsers(String query) {
        return userDAO.searchUsers(query);
    }
}
