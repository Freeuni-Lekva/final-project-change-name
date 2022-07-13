package bandfinder.dao;

import bandfinder.models.Band;
import bandfinder.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashmapBandDAO implements BandDAO{
    private final HashMap<Integer, Band> map;
    private final HashMap<Integer, ArrayList<Integer>> bandMembersMap; // band id -> list of members

    public HashmapBandDAO() {
        this.map = new HashMap<>();
        this.bandMembersMap = new HashMap<>();
    }

    @Override
    public Band create(Band model) {
        int newId = map.size();
        model.setId(newId);
        map.put(newId,model);
        bandMembersMap.put(model.getId(),new ArrayList<>());
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
    public boolean isUserInBand(int user_id, int band_id) {
        if(!bandMembersMap.containsKey(band_id)) {
            return false; //band does not exist in database
        }
        return bandMembersMap.get(band_id).contains(user_id);
    }

    @Override
    public boolean addMemberToBand(int member_id, int band_id) {
        if(isUserInBand(member_id,band_id)) {
            return false;
        }
        bandMembersMap.get(band_id).add(member_id);
        return true;
    }

    @Override
    public boolean removeMemberFromBand(int member_id, int band_id) {
        if(!isUserInBand(member_id,band_id)) {
            return false;
        }
        bandMembersMap.get(band_id).remove(Integer.valueOf(member_id)); //band stays in cache if all users are gone
        return true;
    }

    @Override
    public List<Integer> getBandMemberIDs(int band_id) {
        return bandMembersMap.get(band_id);
    }

    @Override
    public List<Integer> getAllBandIDsForUser(int user_id) {
        ArrayList<Integer> res = new ArrayList<>();
        for(int i : bandMembersMap.keySet()) {
            if(bandMembersMap.get(i).contains(user_id)){
                res.add(i);
            }
        }
        return res;
    }
}
