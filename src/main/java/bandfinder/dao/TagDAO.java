package bandfinder.dao;

import bandfinder.models.Tag;

import java.util.List;

public interface TagDAO extends DAO<Tag> {
    String ATTRIBUTE_NAME = "TagDAO";
    boolean addTagToBand(int tagId, int bandId);
    boolean addTagToUser(int tagId, int userId);
    boolean removeTagFromBand(int tagId, int bandId);
    boolean removeTagFromUser(int tagId, int userId);
    boolean bandHasTag(int tagId, int bandId);
    boolean userHasTag(int tagId, int userId);
    List<Integer> getBandTagIDs(int bandId);
    List<Integer> getUserTagIDs(int userId);
}
