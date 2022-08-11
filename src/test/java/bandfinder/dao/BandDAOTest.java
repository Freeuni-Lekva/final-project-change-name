package bandfinder.dao;

import bandfinder.models.Band;
import bandfinder.models.User;
import bandfinder.serviceimplementations.HashmapBandDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BandDAOTest {

    private BandDAO dao;

    @BeforeEach
    public void setUp() {
        this.dao = new HashmapBandDAO();
    }
    @Test
    void testPack1() {
        //this testpack tests the general functions of the dao class:
        //create,update,delete,getByID,getall

        Band band = new Band("Blink-182");

        //testing dao.create
        dao.create(band);
        int id = band.getId();
        Assertions.assertEquals(dao.getById(id),band);

        //testing dao.update
        band.setName("Quchis bichebi");
        dao.update(band);
        Assertions.assertEquals(dao.getById(id).getName(),"Quchis bichebi");

        //testing dao.delete
        Assertions.assertFalse(dao.delete(id-1)); //such band should not exist in datastore
        Assertions.assertTrue(dao.delete(id));
        Assertions.assertNull(dao.getById(id)); //quchis bichebi should not be in the datastore anymore

        //testing dao getall
        Assertions.assertEquals(dao.getAll().size(),0);

        dao.create(new Band("Meshuggah"));
        dao.create(new Band("Lamb of God"));
        Band lastBand = dao.create(new Band("Korn"));
        dao.delete(lastBand.getId());

        Assertions.assertEquals(dao.getAll().size(),2);
    }

    @Test
    void testPack2() {
        //this testpack tests user-band managament
        Band band1 = new Band("Blink-182");
        Band band2 = new Band("Erisioni");

        User user1 = new User(1,"mail1", "abcd", "Tope", "top123", "stagetop");
        User user2 = new User(2,"mail2", "abcde", "Tanju", "tanj123", "tanjeks");

        dao.create(band1);
        dao.create(band2);

        Assertions.assertEquals(0,dao.getAllBandIDsForUser(user1.getId()).size()); //user 1 is in no bands
        Assertions.assertFalse(dao.isUserInBand(user1.getId(),band1.getId())); //check for each combination below
        Assertions.assertFalse(dao.isUserInBand(user2.getId(),band1.getId()));
        Assertions.assertFalse(dao.isUserInBand(user1.getId(),band2.getId()));
        Assertions.assertFalse(dao.isUserInBand(user2.getId(),band2.getId()));

        dao.addMemberToBand(user1.getId(),band1.getId());
        Assertions.assertTrue(dao.isUserInBand(user1.getId(),band1.getId())); //user1 now should be in band1
        dao.addMemberToBand(user2.getId(),band1.getId());
        Assertions.assertEquals(2,dao.getBandMemberIDs(band1.getId()).size()); //band1 now should have user1 AND user 2
        Assertions.assertTrue(dao.removeMemberFromBand(user1.getId(),band1.getId())); //user1 should be succesfuly removed from band1
        Assertions.assertFalse(dao.addMemberToBand(user2.getId(),band1.getId())); //user2 should not be added to band1, as its already there
        Assertions.assertEquals(1,dao.getBandMemberIDs(band1.getId()).size()); //band1 only has user2


        Assertions.assertFalse(dao.removeMemberFromBand(user1.getId(),band2.getId())); //User1 should not be able to be removed from band2, cause its not there
        Assertions.assertTrue(dao.addMemberToBand(user1.getId(),band2.getId()));
        Assertions.assertTrue(dao.addMemberToBand(user2.getId(),band2.getId()));

        Assertions.assertEquals(1,dao.getAllBandIDsForUser(user1.getId()).size()); //user 1 is in band 2
        Assertions.assertEquals(2,dao.getAllBandIDsForUser(user2.getId()).size()); //user 2 is in band 1 and band 2
    }

    @Test
    public void testSearch() {
        Band band1 = new Band(1, "Gojira");
        dao.create(band1);
        Band band2 = new Band(2, "Guns N' Roses");
        dao.create(band2);

        List<Band> list1 = dao.searchBands("j");
        Assertions.assertEquals(1, list1.size());
        Assertions.assertEquals(band1, list1.get(0));

        List<Band> emptyList = dao.searchBands("Trio Mandili");
        Assertions.assertTrue(emptyList.isEmpty());

        List<Band> list2 = dao.searchBands("Ns n' rO");
        Assertions.assertEquals(1, list2.size());
        Assertions.assertEquals(band2, list2.get(0));

    }
}