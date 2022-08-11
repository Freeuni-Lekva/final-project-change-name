package bandfinder.services;

import bandfinder.models.Band;
import bandfinder.models.User;

import java.util.List;

public interface SearchService {
    List<Band> searchBands(String query);
    List<User> searchUsers(String query);
}
