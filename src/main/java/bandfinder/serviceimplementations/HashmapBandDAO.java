package bandfinder.serviceimplementations;

import bandfinder.dao.BandDAO;
import bandfinder.models.Band;
import bandfinder.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashmapBandDAO implements BandDAO {
    private final HashMap<Integer, Band> map;
    private final HashMap<Integer, ArrayList<Integer>> bandMembersMap; // band id -> list of members

    public HashmapBandDAO() {
        this.map = new HashMap<>();
        this.bandMembersMap = new HashMap<>();
    }

    @Override
    public Band create(Band model) {
        int id = model.getId();
        if(model.getId() == -1) {
            id = map.size() + 1;
            model.setId(id);
        }
        map.put(id, model);
        bandMembersMap.put(model.getId(),new ArrayList<>());
        return model;
    }

    @Override
    public Band update(Band model) {
        map.put(model.getId(), model);
        return map.get(model.getId());
    }

    @Override
    public boolean delete(int id) {
        Band deletedBand = map.remove(id);
        if(deletedBand != null) {
            deletedBand.setId(-1);
            return true;
        }
        return false;
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
    public boolean isUserInBand(int userId, int bandId) {
        if(!bandMembersMap.containsKey(bandId)) {
            return false; //band does not exist in database
        }
        return bandMembersMap.get(bandId).contains(userId);
    }

    @Override
    public boolean addMemberToBand(int memberId, int bandId) {
        if(isUserInBand(memberId,bandId)) {
            return false;
        }
        bandMembersMap.get(bandId).add(memberId);
        return true;
    }

    @Override
    public boolean removeMemberFromBand(int memberId, int bandId) {
        if(!isUserInBand(memberId,bandId)) {
            return false;
        }
        bandMembersMap.get(bandId).remove(Integer.valueOf(memberId)); //band stays in cache if all users are gone
        return true;
    }

    @Override
    public List<Integer> getBandMemberIDs(int bandId) {
        return bandMembersMap.get(bandId);
    }

    @Override
    public List<Integer> getAllBandIDsForUser(int userId) {
        ArrayList<Integer> res = new ArrayList<>();
        for(int i : bandMembersMap.keySet()) {
            if(bandMembersMap.get(i).contains(userId)){
                res.add(i);
            }
        }
        return res;
    }

    @Override
    public List<Band> searchBands(String query) {
        query = query.toLowerCase();
        List<Band> bands = new ArrayList<>();
        for(Band b : map.values()) {
            if(b.getName().toLowerCase().contains(query)) {
                bands.add(b);
            }
        }
        return bands;
    }
}
