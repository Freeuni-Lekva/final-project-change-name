package bandfinder.dao;

import bandfinder.models.Band;
import bandfinder.models.User;

import java.util.List;

public interface BandDAO extends DAO<Band>{
    String ATTRIBUTE_NAME = "BandDAO";
    boolean isUserInBand(int user_id,int band_id);
    boolean addMemberToBand(int member_id, int band_id);
    boolean removeMemberFromBand(int member_id, int band_id);
    List<Integer> getBandMemberIDs(int band_id);
    List<Integer> getAllBandIDsForUser(int user_id);
}
