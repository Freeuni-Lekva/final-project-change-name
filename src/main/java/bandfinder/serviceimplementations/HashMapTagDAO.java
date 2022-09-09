package bandfinder.serviceimplementations;

import bandfinder.dao.TagDAO;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Tag;

import java.util.*;

public class HashMapTagDAO implements TagDAO {
    private final HashMap<Integer, Tag> tags;
    private final HashMap<Integer, Set<Integer>> bandTags;
    private final HashMap<Integer, Set<Integer>> userTags;

    public HashMapTagDAO() {
        tags = new HashMap<>();
        bandTags = new HashMap<>();
        userTags = new HashMap<>();
    }

    @Override
    public Tag create(Tag model) {
        int id = model.getId();
        if(model.getId() == Constants.NO_ID) {
            id = tags.size() + 1;
            model.setId(id);
        }
        tags.put(id, model);
        return model;
    }

    @Override
    public Tag update(Tag model) {
        tags.put(model.getId(), model);
        return model;
    }

    @Override
    public boolean delete(int id) {
        Tag deletedTag = tags.remove(id);
        if(deletedTag != null) {
            deletedTag.setId(Constants.NO_ID); // Needed to behave correctly when re-adding elements with the same id
            return true;
        }
        return false;
    }

    @Override
    public Tag getById(int id) {
        return tags.get(id);
    }

    @Override
    public List<Tag> getAll() {
        List<Tag> tagList = new ArrayList<>(tags.values());
        return tagList;
    }

    @Override
    public boolean addTagToBand(int tagId, int bandId) {
        if(!tags.containsKey(tagId))
            return false;
        if(!bandTags.containsKey(bandId))
            bandTags.put(bandId, new HashSet<>());
        if(!bandTags.get(bandId).contains(tagId)) {
            bandTags.get(bandId).add(tagId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addTagToUser(int tagId, int userId) {
        if(!tags.containsKey(tagId))
            return false;
        if(!userTags.containsKey(userId))
            userTags.put(userId, new HashSet<>());
        if(!userTags.get(userId).contains(tagId)) {
            userTags.get(userId).add(tagId);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTagFromBand(int tagId, int bandId) {
        if(!bandTags.containsKey(bandId)) return false;
        if(bandTags.get(bandId).contains(tagId)) {
            bandTags.get(bandId).remove(tagId);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTagFromUser(int tagId, int userId) {
        if(!userTags.containsKey(userId)) return false;
        if(userTags.get(userId).contains(tagId)) {
            userTags.get(userId).remove(tagId);
            return true;
        }
        return false;
    }

    @Override
    public boolean bandHasTag(int tagId, int bandId) {
        if(!bandTags.containsKey(bandId)) return false;
        return bandTags.get(bandId).contains(tagId);
    }

    @Override
    public boolean userHasTag(int tagId, int userId) {
        if(!userTags.containsKey(userId)) return false;
        return userTags.get(userId).contains(tagId);
    }

    @Override
    public List<Integer> getBandTagIDs(int bandId) {
        return bandTags.get(bandId).stream().toList();
    }

    @Override
    public List<Integer> getUserTagIDs(int userId) {
        return userTags.get(userId).stream().toList();
    }
}
