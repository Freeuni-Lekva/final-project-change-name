package bandfinder.dao;

import bandfinder.models.Band;
import bandfinder.models.User;

import java.util.List;

public interface BandDAO extends DAO<Band>{
    String ATTRIBUTE_NAME = "BandDAO";
    boolean isUserInBand(User user,Band band);
    boolean addMemberToBand(User member, Band band);
    boolean removeMemberFromBand(User member, Band band);
    List<User> getBandMembers(Band band);
    List<Band> getAllBandsForUser(User user);
}
