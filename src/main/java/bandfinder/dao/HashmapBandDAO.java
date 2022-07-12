package bandfinder.dao;

import bandfinder.models.Band;
import bandfinder.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashmapBandDAO implements BandDAO{
    private final HashMap<Integer, Band> map;
    private final HashMap<Integer, ArrayList<User>> bandMembersMap; // band id -> list of members

    public HashmapBandDAO() {
        this.map = new HashMap<>();
        this.bandMembersMap = new HashMap<>();
    }

    @Override
    public Band create(Band model) {
        int newId = map.size();
        model.setId(newId);
        map.put(newId,model);
        bandMembersMap.put(model.getId(),new ArrayList<User>());
        return map.get(model.getId());
    }

    @Override
    public Band update(Band model) {
        map.put(model.getId(), model);
        return map.get(model.getId());
    }

    @Override
    public boolean delete(int id) {
        return map.remove(id) != null;
    }

    @Override
    public Band getById(int id) {
        return map.get(id);
    }

    @Override
    public List<Band> getAll() {
        List<Band> bandList = new ArrayList<>();
        for(int i : map.keySet()) {
            bandList.add(map.get(i));
        }
        return bandList;
    }

    @Override
    public boolean isUserInBand(User user, Band band) {
        if(!bandMembersMap.containsKey(band.getId())) return false; //band does not exist in database
        return bandMembersMap.get(band.getId()).contains(user);
    }

    @Override
    public boolean addMemberToBand(User member, Band band) {
        if(isUserInBand(member,band)) return false;
        bandMembersMap.get(band.getId()).add(member);
        return true;
    }

    @Override
    public boolean removeMemberFromBand(User member, Band band) {
        if(!isUserInBand(member,band)) return false;
        bandMembersMap.get(band.getId()).remove(member); //band stays in cache if all users are gone
        return true;
    }

    @Override
    public List<User> getBandMembers(Band band) {
        return bandMembersMap.get(band.getId());
    }

    @Override
    public List<Band> getAllBandsForUser(User user) {
        ArrayList<Band> res = new ArrayList<>();
        for(int i : bandMembersMap.keySet()) {
            if(bandMembersMap.get(i).contains(user)){
                res.add(getById(i));
            }
        }
        return res;
    }
}
