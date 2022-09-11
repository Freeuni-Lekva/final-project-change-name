package bandfinder.serviceimplementations;

import bandfinder.dao.FollowDAO;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Follow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapFollowDAO implements FollowDAO {
    private final HashMap<Integer, Follow> follows = new HashMap<>();

    @Override
    public Follow create(Follow model) {
        int id = model.getId();
        if(model.getId() == Constants.NO_ID) {
            id = follows.size() + 1;
            model.setId(id);
        }
        follows.put(id, model);
        return model;
    }

    @Override
    public Follow update(Follow model) {
        follows.put(model.getId(), model);
        return model;
    }

    @Override
    public boolean delete(int id) {
        Follow deletedFollow = follows.remove(id);
        if(deletedFollow != null) {
            deletedFollow.setId(Constants.NO_ID);
            return true;
        }
        return false;
    }

    @Override
    public Follow getById(int id) {
        return follows.get(id);
    }

    @Override
    public List<Follow> getAll() {
        return new ArrayList<>(follows.values());
    }

    @Override
    public boolean followExists(Follow follow) {
        for(Follow f : follows.values()) {
            if(f.equals(follow))
                return true;
        }
        return false;
    }

    @Override
    public boolean delete(Follow follow) {
        List<Integer> deletedIds = new ArrayList<>();
        for(int i : follows.keySet()) {
            if(follows.get(i).equals(follow)) {
                deletedIds.add(i);
            }
        }
        for(int id : deletedIds) {
            follows.remove(id);
        }
        return !deletedIds.isEmpty();
    }

    @Override
    public int getFollowerCount(int userId) {
        int count = 0;
        for(Follow f : follows.values()) {
            if(f.getFolloweeID() == userId) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getFolloweeCount(int userId) {
        int count = 0;
        for(Follow f : follows.values()) {
            if(f.getFollowerID() == userId) {
                count++;
            }
        }
        return count;
    }
}
