package bandfinder.serviceimplementations;

import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.Constants;
import bandfinder.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapUserDAO implements UserDAO {
    private final HashMap<Integer, User> map;

    public HashMapUserDAO() {
        this.map = new HashMap<>();
    }

    @Override
    public synchronized User create(User model) {
        int id = model.getId();
        if(model.getId() == Constants.NO_ID) {
            id = map.size() + 1;
            model.setId(id);
        }
        map.put(id, model);
        return model;
    }

    @Override
    public User update(User model) {
        map.put(model.getId(), model);
        return map.get(model.getId());
    }

    @Override
    public boolean delete(int id) {
        User deletedUser = map.remove(id);
        if(deletedUser != null) {
            deletedUser.setId(Constants.NO_ID); // Needed to behave correctly when re-adding elements with the same id
            return true;
        }
        return false;
    }

    @Override
    public User getById(int id) {
        return map.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        for(int i : map.keySet()) {
            userList.add(map.get(i));
        }
        return userList;
    }

    @Override
    public User getUserByEmail(String email) {
        for(int i : map.keySet()) {
            User curr = map.get(i);
            if(curr.getEmail().equals(email)) {
                return curr;
            }
        }
        return null;
    }

    @Override
    public List<User> searchUsers(String query) {
        query = query.toLowerCase();
        List<User> users = new ArrayList<>();
        for(User u : map.values()) {
            String fullName = u.getFirstName() + " " + u.getSurname() + " " + u.getStageName();
            fullName = fullName.toLowerCase();
            if(fullName.contains(query)) {
                users.add(u);
            }
        }
        return users;
    }
}
