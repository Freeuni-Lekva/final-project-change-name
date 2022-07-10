package bandfinder.dao;

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
    public User create(User model) {
        int newId = map.size();
        model.setId(newId);
        map.put(newId, model);
        return map.get(model.getId());
    }

    @Override
    public User update(User model) {
        map.put(model.getId(), model);
        return map.get(model.getId());
    }

    @Override
    public boolean delete(int id) {
        return map.remove(id) != null;
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
}
