package bandfinder.dao;

import bandfinder.models.Band;
import bandfinder.models.User;

import java.util.List;

public interface BandDAO extends DAO<Band>{
    String ATTRIBUTE_NAME = "BandDAO";
    boolean isUserInBand(int userId,int bandId);
    boolean addMemberToBand(int memberId, int bandId);
    boolean removeMemberFromBand(int memberId, int bandId);
    List<Integer> getBandMemberIDs(int bandId);
    List<Integer> getAllBandIDsForUser(int userId);
}
